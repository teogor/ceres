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
  alias(libs.plugins.querent)
  alias(libs.plugins.teogor.winds)
}

querent {
  buildFeatures {
    // TODO querent issue - https://github.com/teogor/querent/issues/16
    xmlResources = false
  }
}

android {
  namespace = "dev.teogor.ceres.screen.ui"
  defaultConfig {
    consumerProguardFiles("consumer-proguard-rules.pro")
  }

  // This block configures code analysis (linting) for your project.
  lint {
    // Temporarily disables the "ResourceName" lint check
    // TODO based on ceres.lint plugin
    disable.add("ResourceName")
  }
}

dependencies {
  api(projects.core.register)
  api(projects.core.runtime)
  api(projects.framework.frameworkCore)
  api(projects.ui.designsystem)
  api(projects.ui.compose)
  api(projects.ui.icons)
  api(projects.ui.theme)
  api(projects.screen.builder)
  api(projects.navigation.events)

  // Xenoglot BoM
  api(platform(libs.xenoglot.bom))
  // Xenoglot Libraries
  api(libs.xenoglot.android)

  api(libs.androidx.compose.foundation)

  // Feature :: About
  implementation(libs.about.libraries.core)
  // used for toImmutableList
  implementation(libs.kotlinx.collections)

  // TODO move to :core:$module
  implementation(libs.accompanist.permissions)
}

winds {
  moduleMetadata {
    artifactDescriptor {
      name = "UI"
    }
  }
}
