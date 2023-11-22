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

import com.android.build.api.variant.LibraryAndroidComponentsExtension
import com.android.build.gradle.LibraryExtension
import dev.teogor.ceres.configureFlavors
import dev.teogor.ceres.configureGradleManagedDevices
import dev.teogor.ceres.configureKotlinAndroid
import dev.teogor.ceres.configurePrintApksTask
import dev.teogor.ceres.disableUnnecessaryAndroidTests
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.kotlin

class AndroidLibraryConventionPlugin : Plugin<Project> {
  override fun apply(target: Project) {
    with(target) {
      with(pluginManager) {
        apply("com.android.library")
        apply("org.jetbrains.kotlin.android")
      }

      extensions.configure<LibraryExtension> {
        configureKotlinAndroid(this)
        // TODO remove ??? deprecated
        defaultConfig.targetSdk = 34
        configureFlavors(this@configure)
        configureGradleManagedDevices(this)
      }
      extensions.configure<LibraryAndroidComponentsExtension> {
        configurePrintApksTask(this)
        disableUnnecessaryAndroidTests(target)
      }
      val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
      configurations.configureEach {
        resolutionStrategy {
          force(libs.findLibrary("junit4").get())
          // Temporary workaround for https://issuetracker.google.com/174733673
          force("org.objenesis:objenesis:2.6")
        }
      }
      dependencies {
        add("androidTestImplementation", kotlin("test"))
        add("testImplementation", kotlin("test"))
      }
    }
  }
}
