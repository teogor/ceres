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

import com.android.build.gradle.LibraryExtension
import dev.teogor.ceres.configureGradleManagedDevices
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

class AndroidFeatureConventionPlugin : Plugin<Project> {
  override fun apply(target: Project) {
    with(target) {
      pluginManager.apply {
        apply("dev.teogor.ceres.android.library")
        apply("dev.teogor.ceres.android.hilt")
      }
      extensions.configure<LibraryExtension> {
        defaultConfig {
          testInstrumentationRunner =
            "dev.teogor.ceres.core.testing.CeresTestRunner"
        }
        configureGradleManagedDevices(this)
      }

      val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

      dependencies {
        // add("implementation", project(":ceres:firebase:analytics"))
        // add("implementation", project(":ceres:core:backup"))
        // add("implementation", project(":ceres:core:common"))
        // add("implementation", project(":ceres:firebase:crashlytics"))
        // add("implementation", project(":ceres:core:data"))
        // add("implementation", project(":ceres:core:designsystem"))
        // add("implementation", project(":ceres:core:datastore"))
        // add("implementation", project(":ceres:core:database"))
        // add("implementation", project(":ceres:core:domain"))
        // add("implementation", project(":ceres:core:framework"))
        // add("implementation", project(":ceres:core:material"))
        // add("implementation", project(":ceres:core:model"))
        // add("implementation", project(":ceres:core:navigation"))
        // add("implementation", project(":ceres:core:ui"))
        //
        // add("testImplementation", kotlin("test"))
        // add("testImplementation", project(":ceres:core:testing"))
        // add("androidTestImplementation", kotlin("test"))
        // add("androidTestImplementation", project(":ceres:core:testing"))
        //
        // add("implementation", libs.findLibrary("coil.kt").get())
        // add("implementation", libs.findLibrary("coil.kt.compose").get())
        //
        // add("implementation", libs.findLibrary("androidx.hilt.navigation.compose").get())
        // add("implementation", libs.findLibrary("androidx.lifecycle.runtimeCompose").get())
        // add("implementation", libs.findLibrary("androidx.lifecycle.viewModelCompose").get())
        //
        // add("implementation", libs.findLibrary("kotlinx.coroutines.android").get())
      }
    }
  }
}
