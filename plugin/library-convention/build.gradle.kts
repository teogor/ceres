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

@file:Suppress("UnstableApiUsage")

import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  `kotlin-dsl`
  alias(libs.plugins.gradle.publish)
  alias(libs.plugins.build.config)
}

group = "dev.teogor.ceres.plugin"
version = "1.0.0-alpha04"

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
  compileOnly(libs.gradle.plugin.android)
  compileOnly(libs.gradle.plugin.kotlin)
  compileOnly(libs.gradle.plugin.ksp)
  compileOnly(libs.firebase.crashlytics.gradle)
  compileOnly(libs.firebase.performance.gradle)
}

gradlePlugin {
  website.set("https://source.teogor.dev/ceres")
  vcsUrl.set("https://github.com/teogor/ceres")

  plugins {
    register("androidApplicationCompose") {
      id = "dev.teogor.ceres.android.application.compose"
      implementationClass = "AndroidApplicationComposeConventionPlugin"
      displayName = "Android Compose Configuration Plugin | Ceres Plugin"
      description = "Configures Android Compose-specific options for your Android application. Enhance your app's UI with the power of Jetpack Compose for modern Android development."
      tags.set(listOf("ceres", "android", "integration", "performance", "development", "android-library", "kmp", "build-logic"))
    }

    register("androidApplication") {
      id = "dev.teogor.ceres.android.application"
      implementationClass = "AndroidApplicationConventionPlugin"
      displayName = "Android Application Plugin | Ceres Plugin"
      description = "Configures Android application-specific options for your project."
      tags.set(listOf("android", "application", "android-library", "android-development"))
    }

    register("androidApplicationJacoco") {
      id = "dev.teogor.ceres.android.application.jacoco"
      implementationClass = "AndroidApplicationJacocoConventionPlugin"
      displayName = "Android Application Jacoco Plugin | Ceres Plugin"
      description = "Configures Android application-specific options with Jacoco code coverage for your project."
      tags.set(listOf("android", "application", "jacoco", "android-library", "android-development"))
    }

    register("androidLibraryCompose") {
      id = "dev.teogor.ceres.android.library.compose"
      implementationClass = "AndroidLibraryComposeConventionPlugin"
      displayName = "Android Library Compose Plugin | Ceres Plugin"
      description = "Configures Android library projects with Jetpack Compose for modern UI development."
      tags.set(listOf("android", "library", "compose", "android-library", "android-development"))
    }

    register("androidLibrary") {
      id = "dev.teogor.ceres.android.library"
      implementationClass = "AndroidLibraryConventionPlugin"
      displayName = "Android Library Plugin | Ceres Plugin"
      description = "Configures Android library projects for your project."
      tags.set(listOf("android", "library", "android-library", "android-development"))
    }

    register("androidFeature") {
      id = "dev.teogor.ceres.android.feature"
      implementationClass = "AndroidFeatureConventionPlugin"
      displayName = "Android Feature Plugin | Ceres Plugin"
      description = "Configures Android feature module projects for your project."
      tags.set(listOf("android", "feature", "android-library", "android-development"))
    }

    register("androidLibraryJacoco") {
      id = "dev.teogor.ceres.android.library.jacoco"
      implementationClass = "AndroidLibraryJacocoConventionPlugin"
      displayName = "Android Library Jacoco Plugin | Ceres Plugin"
      description = "Configures Android library projects with Jacoco code coverage for your project."
      tags.set(listOf("android", "library", "jacoco", "android-library", "android-development"))
    }

    register("androidLibraryConfig") {
      id = "dev.teogor.ceres.android.library.config"
      implementationClass = "AndroidLibraryConfigConventionPlugin"
      displayName = "Android Library Configuration Plugin | Ceres Plugin"
      description = "Configures Android library projects with custom configurations for your project."
      tags.set(listOf("android", "library", "config", "android-library", "android-development"))
    }

    register("androidTest") {
      id = "dev.teogor.ceres.android.test"
      implementationClass = "AndroidTestConventionPlugin"
      displayName = "Android Test Plugin | Ceres Plugin"
      description = "Configures Android test-related options for your project."
      tags.set(listOf("android", "test", "android-library", "android-development"))
    }

    register("androidHilt") {
      id = "dev.teogor.ceres.android.hilt"
      implementationClass = "AndroidHiltConventionPlugin"
      displayName = "Android Hilt Plugin | Ceres Plugin"
      description = "Configures Android projects with Hilt dependency injection for your project."
      tags.set(listOf("android", "hilt", "android-library", "android-development"))
    }

    register("androidRoom") {
      id = "dev.teogor.ceres.android.room"
      implementationClass = "AndroidRoomConventionPlugin"
      displayName = "Android Room Plugin | Ceres Plugin"
      description = "Configures Android projects with Room database integration for your project."
      tags.set(listOf("android", "room", "android-library", "android-development"))
    }

    register("androidFirebase") {
      id = "dev.teogor.ceres.android.application.firebase"
      implementationClass = "AndroidApplicationFirebaseConventionPlugin"
      displayName = "Android Firebase Plugin | Ceres Plugin"
      description = "Configures Android application projects with Firebase services for your project."
      tags.set(listOf("android", "firebase", "android-library", "android-development"))
    }

    register("androidFlavors") {
      id = "dev.teogor.ceres.android.application.flavors"
      implementationClass = "AndroidApplicationFlavorsConventionPlugin"
      displayName = "Android Flavors Plugin | Ceres Plugin"
      description = "Configures Android application projects with multiple flavor variants for your project."
      tags.set(listOf("android", "flavors", "android-library", "android-development"))
    }

    register("buildDocs") {
      id = "dev.teogor.ceres.docs"
      implementationClass = "BuildDocsPlugin"
      displayName = "Documentation Builder Plugin"
      description = "Builds project documentation and reports for your project."
      tags.set(listOf("documentation", "report", "build-logic"))
    }

    register("kotlinLibrary") {
      id = "dev.teogor.ceres.kotlin.library"
      implementationClass = "KotlinLibraryConventionPlugin"
      displayName = "Kotlin Library Convention"
      description = "Streamline Kotlin Library Development with a Standardized Convention"
      tags.set(listOf("kotlin", "convention", "build-logic", "library"))
    }
  }
}

buildConfig {
  packageName("dev.teogor.ceres.plugin")

  buildConfigField("String", "NAME", "\"${group}\"")
  buildConfigField("String", "VERSION", "\"${version}\"")
}
