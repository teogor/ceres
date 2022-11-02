import dev.teogor.ceres.Configuration
import dev.teogor.ceres.Versions

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
  implementation("androidx.fragment:fragment-ktx:${Versions.AndroidXFragment}")
  implementation("androidx.navigation:navigation-runtime-ktx:${Versions.AndroidXNavigation}")
  implementation("androidx.navigation:navigation-fragment-ktx:${Versions.AndroidXNavigation}")

  implementation("com.google.android.material:material:${Versions.GoogleMaterial}")
}
