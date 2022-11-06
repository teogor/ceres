import dev.teogor.ceres.Configuration
import dev.teogor.ceres.Versions

plugins {
  // android-library
  id("com.android.library")
  // kotlin
  id("kotlin-android")
  id("kotlin-kapt")
  id("kotlin-parcelize")
}

android {
  namespace = "${Configuration.baseNamespace}.${extra.get("moduleName")}"
  compileSdk = Configuration.compileSdk

  defaultConfig {
    minSdk = Configuration.minSdk
    targetSdk = Configuration.targetSdk
  }

  compileOptions {
    sourceCompatibility = Configuration.javaVersion
    targetCompatibility = Configuration.javaVersion
  }
  kotlinOptions {
    jvmTarget = Configuration.jvmTarget
  }

  buildFeatures {
    dataBinding = true
  }
}

dependencies {
  implementation(project(":ceres-extensions"))

  implementation("com.zeoflow.startup:startup-ktx:${Versions.ZeoFlowStartUp}")

  implementation("androidx.core:core-ktx:1.9.0")
  implementation("androidx.appcompat:appcompat:1.5.1")

  implementation("com.google.android.material:material:${Versions.GoogleMaterial}")
}
