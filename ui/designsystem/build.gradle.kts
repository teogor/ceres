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
}

android {
  namespace = "dev.teogor.ceres.ui.designsystem"
  defaultConfig {
    consumerProguardFiles("consumer-proguard-rules.pro")
  }
}

dependencies {
  api(project(":ui:foundation"))
  api(project(":ui:icons"))
  api(project(":ui:spectrum"))
  api(project(":ui:theme"))

  api(libs.google.material)

  // Image Loading
  api(libs.landscapist.glide)
  api(libs.landscapist.placeholder)
  api(libs.coil.kt.compose)

  // Scrollbar
  implementation(libs.androidx.constraintlayout.compose)
}

ceresLibrary {
  name = "Ceres UI Design-System"
}
