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
  id("dev.teogor.ceres.android.hilt")
}

android {
  namespace = "dev.teogor.ceres.firebase.crashlytics"
  defaultConfig {
    consumerProguardFiles("consumer-proguard-rules.pro")
  }
}

dependencies {
  implementation(project(":core:foundation"))

  implementation(libs.firebase.crashlytics)
  implementation(platform(libs.firebase.bom))

  implementation(libs.startup.runtime)
  implementation(libs.androidx.compose.runtime)
}

ceresLibrary {
  name = "Ceres Firebase Crashlytics"
}
