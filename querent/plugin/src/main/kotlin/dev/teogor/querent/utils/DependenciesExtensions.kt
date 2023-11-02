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

package dev.teogor.querent.utils

import org.gradle.api.Project
import org.gradle.api.artifacts.ConfigurationContainer
import org.gradle.api.artifacts.Dependency
import org.gradle.kotlin.dsl.DependencyHandlerScope
import org.gradle.kotlin.dsl.dependencies

internal fun ConfigurationContainer.findSpecificDependency(
  group: String,
  name: String,
): Dependency? = flatMap { it.dependencies }
  .firstOrNull { it.group == group && it.name == name }

fun Project.ceresBomDependency() = project.configurations
  .findSpecificDependency(
    group = "dev.teogor.ceres",
    name = "bom",
  )

inline fun Project.dependencies(
  crossinline block: DependencyHandlerScope.() -> Unit,
) {
  dependencies {
    block(this)
  }
}
