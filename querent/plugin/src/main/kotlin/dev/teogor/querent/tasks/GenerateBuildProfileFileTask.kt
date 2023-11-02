/*
 * Copyright 2023 teogor (Teodor Grigor)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dev.teogor.querent.tasks

import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.asClassName
import com.squareup.kotlinpoet.asTypeName
import dev.teogor.querent.api.gradle.BaseTask
import dev.teogor.querent.utils.capitalize
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.TaskAction
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset

abstract class GenerateBuildProfileFileTask : BaseTask() {

  private val BuildVariant by lazy { packageName.get() type "BuildVariant" }
  private val BuildProfiler by lazy { "dev.teogor.ceres.core.register" type "BuildProfiler" }

  @get:Input
  abstract val debug: Property<Boolean>

  @get:Input
  abstract val buildType: Property<String>

  @get:Input
  abstract val packageId: Property<String>

  @get:Input
  abstract val gitHashProvider: Property<String>

  @get:Input
  @get:Optional
  abstract val ceresDependency: Property<String>

  @get:Input
  abstract val versionName: Property<String>

  @get:Input
  abstract val versionCode: Property<Long>

  @TaskAction
  fun taskAction() {
    val name = "BuildProfile"
    fileSpec(name) {
      addType(
        TypeSpec.objectBuilder(name)
          .addSuperinterface(BuildProfiler)
          .apply {
            addProperty(
              PropertySpec.builder("isDebuggable", Boolean::class)
                .addModifiers(KModifier.OVERRIDE)
                .addKdoc(
                  """
                  A debuggable build is one that has been compiled with debugging
                  information enabled. This information can be used to debug the app.
                  """.trimIndent(),
                )
                .initializer("${debug.get()}")
                .build(),
            )
            addProperty(
              PropertySpec.builder("versionName", String::class)
                .addModifiers(KModifier.OVERRIDE)
                .initializer("%S", versionName.get())
                .build(),
            )
            addProperty(
              PropertySpec.builder("versionCode", Long::class)
                .addModifiers(KModifier.OVERRIDE)
                .initializer("%L", versionCode.get())
                .build(),
            )
            addProperty(
              PropertySpec.builder("packageName", String::class)
                .addModifiers(KModifier.OVERRIDE)
                .addKdoc(
                  """
                  The package name of the app.
                  """.trimIndent(),
                )
                .initializer("%S", packageId.get())
                .build(),
            )
            addProperty(
              PropertySpec.builder("buildType", BuildVariant)
                .initializer("%T.${buildType.get().capitalize()}", BuildVariant)
                .build(),
            )
            addProperty(systemZoneOffset())
            addProperty(buildTime())
            addProperty(buildLocalDateTime())
            addProperty(
              PropertySpec
                .builder(
                  "gitCommitHash",
                  String::class.asClassName().copy(nullable = true),
                )
                .addModifiers(KModifier.OVERRIDE)
                .initializer("%S", gitHashProvider.get())
                .build(),
            )

            val ceresDependencyValue = if (ceresDependency.isPresent) {
              ceresDependency.get()
            } else {
              null
            }
            addProperty(
              PropertySpec
                .builder(
                  "ceresBomVersion",
                  String::class.asTypeName().copy(nullable = true),
                )
                .addModifiers(KModifier.OVERRIDE)
                .initializer("%S", ceresDependencyValue)
                .build(),
            )
          }
          .build(),
      )
    }
  }

  private fun buildTime(): PropertySpec {
    val now = Instant.now()
    val buildDate = now.atOffset(ZoneOffset.UTC).toLocalDate()
    val buildTime = now.atOffset(ZoneOffset.UTC).toLocalTime()
    val buildDateTime = LocalDateTime.of(buildDate, buildTime)
    val utcDateTime = buildDateTime.toEpochSecond(ZoneOffset.UTC)
    return PropertySpec.builder("buildTime", Long::class)
      .addModifiers(KModifier.OVERRIDE)
      .addKdoc(
        """
        The time at which the app was built, in milliseconds since the Unix epoch.
        """.trimIndent(),
      )
      .initializer(CodeBlock.builder().add("%L", utcDateTime).build())
      .build()
  }

  private fun systemZoneOffset() = PropertySpec
    .builder(
      name = "systemZoneOffset",
      type = ZoneOffset::class,
    )
    .addModifiers(KModifier.OVERRIDE)
    .addKdoc(
      """
      The offset from UTC of the system's current default time zone.

      This offset can be used to convert times between UTC and the system's
      current default time zone.
      """.trimIndent(),
    )
    .getter(
      FunSpec.getterBuilder()
        .addCode(
          "return %T.systemDefault().rules.getOffset(%T.now())",
          ZoneId::class,
          LocalDateTime::class,
        )
        .build(),
    ).build()

  private fun buildLocalDateTime() = PropertySpec
    .builder(
      name = "buildLocalDateTime",
      type = LocalDateTime::class,
    )
    .addModifiers(KModifier.OVERRIDE)
    .addKdoc(
      """
      The local date and time at which the app was built, including the system's
      current time zone.
      """.trimIndent(),
    )
    .getter(
      FunSpec.getterBuilder()
        .addCode(
          "return %T.ofEpochSecond(buildTime, 0, systemZoneOffset)",
          LocalDateTime::class,
        )
        .build(),
    ).build()
}
