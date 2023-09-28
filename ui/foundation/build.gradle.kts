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
  id("kotlinx-serialization")
  // required by *.lib.tools
  id("dev.teogor.ceres.android.hilt")
}

android {
  namespace = "dev.teogor.ceres.ui.foundation"
  defaultConfig {
    consumerProguardFiles("consumer-proguard-rules.pro")
  }
}

dependencies {
  api(project(":core:startup"))

  api(libs.androidx.compose.foundation)
  api(libs.androidx.compose.material)
  api(libs.androidx.compose.runtime)
  api(libs.androidx.compose.ui.tooling.preview)
  api(libs.androidx.compose.ui.util)

  // *.window
  api(libs.androidx.core.ktx)
  api(libs.google.material)

  // *.lib
  // *.tools
  api(libs.androidx.metrics)

  debugApi(libs.androidx.compose.ui.tooling)
}

ceresLibrary {
  name = "Ceres UI Foundation"
}
