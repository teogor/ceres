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
  alias(libs.plugins.teogor.winds)
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
  api(libs.google.play.services.ads)

  implementation(libs.farimarwat.admobnative.compose)

  implementation(libs.androidx.lifecycle.process)

  api(projects.monetisation.ads)
  implementation(projects.core.register)
  implementation(projects.core.runtime)
  implementation(projects.core.foundation)
  implementation(projects.ui.designsystem)
  implementation(projects.ui.theme)

  implementation(libs.androidx.compose.foundation)
  implementation(libs.androidx.compose.runtime)
  implementation(libs.androidx.compose.ui.viewbinding)
  implementation(libs.startup.runtime)
}

winds {
  moduleMetadata {
    artifactDescriptor {
      name = "AdMob"
    }
  }
}
