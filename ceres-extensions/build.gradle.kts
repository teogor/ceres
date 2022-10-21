import dev.teogor.ceres.Configuration

plugins {
  // android
  id("com.android.library")
  // kotlin
  id("kotlin-android")
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
}

dependencies {
  implementation("androidx.core:core-ktx:1.9.0")
  implementation("androidx.appcompat:appcompat:1.5.1")
  implementation("com.google.android.material:material:1.8.0-alpha01")
  implementation("androidx.navigation:navigation-runtime-ktx:2.5.2")
  implementation("androidx.fragment:fragment-ktx:1.5.3")
  implementation("androidx.navigation:navigation-fragment-ktx:2.5.2")
}
