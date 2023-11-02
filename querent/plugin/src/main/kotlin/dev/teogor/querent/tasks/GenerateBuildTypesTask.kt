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

import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.TypeSpec
import dev.teogor.querent.api.gradle.BaseTask
import dev.teogor.querent.utils.capitalize
import org.gradle.api.provider.ListProperty
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

abstract class GenerateBuildTypesTask : BaseTask() {

  @get:Input
  abstract val buildTypes: ListProperty<String>

  @TaskAction
  fun taskAction() {
    val enumClassName = "BuildVariant"
    fileSpec(enumClassName) {
      addType(
        TypeSpec.enumBuilder(enumClassName)
          .primaryConstructor(
            FunSpec.constructorBuilder()
              .addParameter("type", String::class)
              .build(),
          )
          .apply {
            buildTypes.get().forEach { buildType ->
              addEnumConstant(
                buildType.capitalize(),
                TypeSpec.anonymousClassBuilder()
                  .addSuperclassConstructorParameter("%S", buildType)
                  .build(),
              )
            }
          }
          .build(),
      )
    }
  }
}
