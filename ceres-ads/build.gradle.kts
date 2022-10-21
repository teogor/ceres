import dev.teogor.ceres.Configuration

plugins {
  // android
  id("com.android.library")
  // kotlin
  id("kotlin-android")
  id("kotlin-kapt")
  id("kotlin-parcelize")
}

android {
  namespace = "${Configuration.baseNamespace}.extensions"
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
    viewBinding = true
  }
}

dependencies {
  implementation(project(":ceres-core"))
  implementation(project(":ceres-extensions"))
  implementation(project(":ceres-bindings"))

  implementation("com.zeoflow.startup:startup-ktx:1.1.0")

  implementation("com.google.android.gms:play-services-ads:21.3.0")

  implementation("javax.inject:javax.inject:1")

  implementation("androidx.core:core-ktx:1.9.0")
  implementation("androidx.appcompat:appcompat:1.5.1")
  implementation("com.google.android.material:material:1.8.0-alpha01")
}
