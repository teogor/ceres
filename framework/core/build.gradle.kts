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
  id("kotlinx-serialization")
  alias(libs.plugins.winds)
}

android {
  namespace = "dev.teogor.ceres.framework.core"
  defaultConfig {
    consumerProguardFiles("consumer-proguard-rules.pro")
  }
}

dependencies {
  api(project(":core:common"))
  api(project(":core:foundation"))
  api(project(":core:runtime"))
  api(project(":core:register"))

  api(project(":data:compose"))
  // required for theme config only
  api(project(":data:datastore"))

  api(project(":navigation:core"))

  api(project(":ui:designsystem"))
  api(project(":ui:foundation"))
  api(project(":ui:theme"))
  api(project(":ui:compose"))

  api(project(":firebase:analytics"))
  api(project(":firebase:crashlytics"))

  api(project(":monetisation:ads"))

  implementation(libs.androidx.activity.compose)
  implementation(libs.androidx.lifecycle.runtimeCompose)
  implementation(libs.androidx.core.splashscreen)

  // in this module required for *WindowSizeClass
  implementation(libs.androidx.compose.material3.windowSizeClass)

  // required for JankStats
  implementation(libs.androidx.metrics)
}

winds {
  mavenPublish {
    displayName = "Core"
    name = "core"
  }
}
