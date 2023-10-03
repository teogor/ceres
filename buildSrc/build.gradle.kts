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

import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  `kotlin-dsl`
  alias(libs.plugins.vanniktech.maven)
  kotlin("plugin.serialization") version "1.6.0"
}

java {
  // Up to Java 11 APIs are available through desugaring
  // https://developer.android.com/studio/write/java11-minimal-support-table
  sourceCompatibility = JavaVersion.VERSION_11
  targetCompatibility = JavaVersion.VERSION_11
}

tasks.withType<KotlinCompile>().configureEach {
  kotlinOptions {
    jvmTarget = JavaVersion.VERSION_11.toString()
  }
}

dependencies {
  api(gradleApi())
  implementation(libs.kotlinx.serialization.json)
  implementation(libs.gson)
}

gradlePlugin {
  plugins {
    register("ceresModulePlugin") {
      id = "dev.teogor.ceres.module"
      implementationClass = "dev.teogor.ceres.gradle.plugins.CeresModulePlugin"
    }
    register("ceresLibraryPublishPlugin") {
      id = "dev.teogor.ceres.library.publish"
      implementationClass = "dev.teogor.ceres.gradle.plugins.CeresLibraryPublishPlugin"
    }
  }
}
