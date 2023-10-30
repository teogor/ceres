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

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.gradle.LibraryExtension
import dev.teogor.ceres.configureAndroidBuildConfig
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.findByType

class AndroidLibraryConfigConventionPlugin : Plugin<Project> {
  override fun apply(target: Project) {
    with(target) {
      extensions.findByType<ApplicationExtension>()?.let { applicationExtension ->
        // It's an Android application
        configureAndroidBuildConfig(applicationExtension)
      }

      extensions.findByType<LibraryExtension>()?.let { libraryExtension ->
        // It's an Android library
        configureAndroidBuildConfig(libraryExtension)
      }
    }
  }
}
