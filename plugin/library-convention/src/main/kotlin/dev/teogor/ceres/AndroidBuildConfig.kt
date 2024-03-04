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

package dev.teogor.ceres

import com.android.build.api.dsl.CommonExtension
import java.io.ByteArrayOutputStream
import java.nio.charset.Charset
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset
import javax.inject.Inject
import org.gradle.api.Project
import org.gradle.api.artifacts.ConfigurationContainer
import org.gradle.api.artifacts.Dependency
import org.gradle.api.provider.ValueSource
import org.gradle.api.provider.ValueSourceParameters
import org.gradle.kotlin.dsl.of
import org.gradle.process.ExecOperations

abstract class GitHashValueSource : ValueSource<String, ValueSourceParameters.None> {

  @get:Inject
  abstract val execOperations: ExecOperations

  // Define a variable to store the Git hash
  private var gitHash: String? = null

  override fun obtain(): String {
    // If gitHash is already calculated, return it
    gitHash?.let { return it }

    val output = ByteArrayOutputStream()
    return try {
      execOperations.exec {
        commandLine("git", "rev-parse", "HEAD")
        standardOutput = output
      }
      // Calculate and store the Git hash
      gitHash = String(output.toByteArray(), Charset.defaultCharset()).trim()
      gitHash ?: "N/A"
    } catch (e: RuntimeException) {
      "N/A"
    }
  }
}

internal fun ConfigurationContainer.findSpecificDependency(
  group: String,
  name: String,
): Dependency? = flatMap { it.dependencies }
  .firstOrNull { it.group == group && it.name == name }


/**
 * Configure BuildConfig-specific options for the Android project.
 *
 * @param commonExtension The common extension of the Android project.
 */
internal fun Project.configureAndroidBuildConfig(
  commonExtension: CommonExtension<*, *, *, *, *>,
) {
  val now = Instant.now()
  val buildDate = now.atOffset(ZoneOffset.UTC).toLocalDate()
  val buildTime = now.atOffset(ZoneOffset.UTC).toLocalTime()
  val buildDateTime = LocalDateTime.of(buildDate, buildTime)

  val gitHashProvider = providers.of(GitHashValueSource::class) {}

  // Enable BuildConfig generation
  commonExtension.apply {
    buildFeatures {
      buildConfig = true
    }

    defaultConfig {
      // Add a field for BUILD_DATE_TIME in BuildConfig
      buildConfigField(
        type = "String",
        name = "BUILD_DATE_TIME",
        value = "\"${buildDateTime.toEpochSecond(ZoneOffset.UTC)}\"",
      )

      // Add a field for BUILD_HASH in BuildConfig
      buildConfigField(
        type = "String",
        name = "GIT_HASH",
        value = "\"${gitHashProvider.get()}\"",
      )

      // Add a field for BUILD_HASH in BuildConfig
      buildConfigField(
        type = "String",
        name = "CERES_FRAMEWORK_VERSION",
        value = "\"${Version.name}\"",
      )
    }
  }
}
