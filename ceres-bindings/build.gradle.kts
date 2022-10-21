import dev.teogor.ceres.Configuration
import dev.teogor.ceres.Dependencies

plugins {
  // android-library
  id("com.android.library")
  // kotlin
  id("kotlin-android")
  id("kotlin-kapt")
  id("kotlin-parcelize")
}

android {
  namespace = "${Configuration.baseNamespace}.bindings"
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
  implementation("com.zeoflow.startup:startup-ktx:1.1.0")

  implementation("androidx.core:core-ktx:1.9.0")
  implementation("androidx.appcompat:appcompat:1.5.1")

  implementation(Dependencies.GoogleMaterial)
}