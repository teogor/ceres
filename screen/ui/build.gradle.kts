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
plugins {
  id("dev.teogor.ceres.android.library")
  id("dev.teogor.ceres.android.library.compose")
  id("dev.teogor.ceres.android.library.jacoco")
}

android {
  namespace = "dev.teogor.ceres.screen.ui"
  defaultConfig {
    consumerProguardFiles("consumer-proguard-rules.pro")
  }
}

dependencies {
  api(project(":core:runtime"))
  api(project(":framework:core"))
  api(project(":ui:designsystem"))
  api(project(":ui:theme"))
  api(project(":ui:compose"))
  api(project(":screen:builder"))
  api(project(":navigation:events"))

  api(libs.androidx.compose.foundation)

  // Feature :: About
  implementation(libs.about.libraries.core)
  // used for toImmutableList
  implementation(libs.kotlinx.collections)
}

ceresLibrary {
  name = "Ceres Screen UI"
}
