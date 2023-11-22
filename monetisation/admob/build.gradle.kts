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
  alias(libs.plugins.winds)
}

android {
  namespace = "dev.teogor.ceres.monetisation.admob"
  defaultConfig {
    consumerProguardFiles("consumer-proguard-rules.pro")
  }
  buildFeatures {
    viewBinding = true
  }
}

dependencies {
  api("com.google.android.gms:play-services-ads:22.4.0")

  implementation("io.github.farimarwat:admobnative-compose:1.2")

  implementation(libs.androidx.lifecycle.process)

  api(project(":monetisation:ads"))
  implementation(project(":core:register"))
  implementation(project(":core:runtime"))
  implementation(project(":core:foundation"))
  implementation(project(":ui:designsystem"))

  implementation(libs.androidx.compose.foundation)
  implementation(libs.androidx.compose.runtime)
  implementation(libs.androidx.compose.ui.viewbinding)
  implementation(libs.startup.runtime)
}

winds {
  mavenPublish {
    displayName = "AdMob"
    name = "admob"
  }
}
