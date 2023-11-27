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
import dev.teogor.ceres.CeresBuildType
import dev.teogor.ceres.Version
import dev.teogor.xenoglot.Country
import dev.teogor.xenoglot.Language
import dev.teogor.xenoglot.territorialize

plugins {
  id("dev.teogor.ceres.android.application")
  id("dev.teogor.ceres.android.application.compose")
  id("dev.teogor.ceres.android.application.jacoco")
  id("dev.teogor.ceres.android.application.firebase")
  id("dev.teogor.ceres.android.hilt")
  id("dev.teogor.ceres.android.room")
  id("kotlinx-serialization")
  id("jacoco")

  alias(libs.plugins.querent)

  // Feature :: About
  alias(libs.plugins.about.libraries) apply true
}

querent {
  buildFeatures {
    buildProfile = true
    xmlResources = true
    languagesSchema = true
  }

  languagesSchemaOptions {
    unqualifiedResLocale = Language.English territorialize Country.UnitedStates
    addSupportedLanguages {
      +(Language.Romanian territorialize Country.Romania)
      +(Language.English territorialize Country.UnitedKingdom)
      +(Language.Korean territorialize Country.SouthKorea)
      +(Language.Dutch territorialize Country.Netherlands)
      +(Language.German territorialize Country.Germany)
      +(Language.Chinese territorialize Country.China)
      +Language.Japanese
      +Language.Spanish
      +Language.Hindi
      +Language.Arabic
    }
  }
}

roomOptions {
  enableSchemaProvider = true
}

android {
  namespace = "dev.teogor.ceres"
  defaultConfig {
    applicationId = "dev.teogor.ceres"
    versionCode = Version.code
    versionName = Version.name

    // Custom test runner to set up Hilt dependency graph
    testInstrumentationRunner = "dev.teogor.ceres.core.testing.CeresTestRunner"
    vectorDrawables {
      useSupportLibrary = true
    }
  }

  buildFeatures {
    resValues = true
  }

  buildTypes {
    debug {
      applicationIdSuffix = CeresBuildType.DEBUG.applicationIdSuffix

      resValue("string", "app_name", "❉ Ceres")
    }
    val release by getting {
      isMinifyEnabled = true
      applicationIdSuffix = CeresBuildType.RELEASE.applicationIdSuffix
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")

      // To publish on the Play store a private signing key is required, but to allow anyone
      // who clones the code to sign and run the release variant, use the debug signing key.
      // TODO: Abstract the signing configuration to a separate file to avoid hardcoding this.
      signingConfig = signingConfigs.getByName("debug")

      resValue("string", "app_name", "Ceres")
    }
    // todo error crashes the ./gradlew build
    //  create("benchmark") {
    //    // Enable all the optimizations from release build through initWith(release).
    //    initWith(release)
    //    matchingFallbacks.add("release")
    //    // Debug key signing is available to everyone.
    //    signingConfig = signingConfigs.getByName("debug")
    //    // Only use benchmark proguard rules
    //    proguardFiles("benchmark-rules.pro")
    //    isMinifyEnabled = true
    //    applicationIdSuffix = CeresBuildType.BENCHMARK.applicationIdSuffix
    //  }
  }

  packaging {
    resources {
      excludes.add("/META-INF/{AL2.0,LGPL2.1}")
    }
  }
  testOptions {
    unitTests {
      isIncludeAndroidResources = true
    }
  }
}

dependencies {
  // application configuration
  implementation(project(":framework:core"))

  // screen builder
  implementation(project(":screen:builder"))
  implementation(project(":screen:core"))

  // default screens
  // TODO split into locale and ui
  implementation(project(":screen:ui"))

  // theme config
  implementation(project(":ui:theme"))

  // monetisation
  implementation(project(":monetisation:admob"))
  implementation(project(":monetisation:messaging"))

  implementation(libs.kotlinx.serialization.json)
  implementation(libs.kotlinx.serialization.protobuf)

  implementation(libs.androidx.activity.compose)
  implementation(libs.androidx.appcompat)
  implementation(libs.androidx.core.ktx)
  implementation(libs.androidx.core.splashscreen)
  implementation(libs.androidx.compose.runtime)
  implementation(libs.androidx.lifecycle.runtimeCompose)
  implementation(libs.androidx.compose.runtime.tracing)
  implementation(libs.androidx.compose.material3.windowSizeClass)
  implementation(libs.androidx.hilt.navigation.compose)
  implementation(libs.androidx.navigation.compose)
  implementation(libs.androidx.window.manager)
  implementation(libs.androidx.profileinstaller)

  implementation(libs.ui)
  implementation(libs.ui.graphics)
  implementation(libs.ui.tooling.preview)
  implementation(libs.androidx.compose.material3)

  // Feature :: About
  implementation(libs.about.libraries.core)
  // used for toImmutableList
  implementation(libs.kotlinx.collections)
}

// androidx.test is forcing JUnit, 4.12. This forces it to use 4.13
configurations.configureEach {
  resolutionStrategy {
    force(libs.junit4)
    // Temporary workaround for https://issuetracker.google.com/174733673
    force("org.objenesis:objenesis:2.6")
  }
}
