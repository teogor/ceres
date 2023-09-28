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
import org.gradle.api.Plugin
import org.gradle.api.Project

class BuildDocsPlugin : Plugin<Project> {
  override fun apply(project: Project) {
    val modulesByComponent = mutableMapOf<String, MutableList<Project>>()
    val modulesCeres = project.allprojects.filter {
      it.path.count { char -> char == ':' } == 2
    }
    modulesCeres.forEach { module ->
      val pathComponents = module.path.split(":")
      if (pathComponents.size >= 2) {
        val component = pathComponents[1]
        val componentModules = modulesByComponent.getOrDefault(component, mutableListOf())
        componentModules.add(module)
        modulesByComponent[component] = componentModules
      }
    }
    project.tasks.create("generateCeresDocs") {
      doLast {
        modulesByComponent.forEach { (component, _) ->
          dependsOn(":$component:generateCeresDocs")
        }
      }
    }
  }
}
