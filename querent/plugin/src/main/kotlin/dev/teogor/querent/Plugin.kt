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

package dev.teogor.querent

import dev.teogor.querent.api.codegen.impl.initializePlugin
import dev.teogor.querent.api.impl.QuerentConfiguratorExtension
import dev.teogor.querent.structures.BuildProfile
import dev.teogor.querent.structures.LanguagesSchema
import dev.teogor.querent.structures.XmlResources
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.create

class Plugin : Plugin<Project> {
  override fun apply(target: Project) {
    with(target) {
      extensions.create<QuerentConfiguratorExtension>(
        name = "querenet",
      )

      initializePlugin<BuildProfile>()
      initializePlugin<XmlResources>()
      initializePlugin<LanguagesSchema>()
    }
  }
}
