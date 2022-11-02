import dev.teogor.ceres.Configuration
import dev.teogor.ceres.Versions

plugins {
  // android
  id("com.android.library")
  // kotlin
  id("kotlin-android")
  id("kotlin-kapt")
  id("kotlin-parcelize")
  // safe args
  id("androidx.navigation.safeargs.kotlin")
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
    viewBinding = true
  }

  lint {
    abortOnError = false
    checkReleaseBuilds = false
  }

  kapt {
    correctErrorTypes = true
  }
}

dependencies {
  implementation(project(":ceres-bindings"))
  implementation(project(":ceres-core"))
  implementation(project(":ceres-components"))
  implementation(project(":ceres-extensions"))

  //----------------------------- ZEOFLOW -------------------------------
  implementation("com.zeoflow.startup:startup-ktx:${Versions.ZeoFlowStartUp}")
  implementation("com.zeoflow.memo:memo:${Versions.ZeoFlowMemo}")
  implementation("com.zeoflow.memo:memo-runtime:${Versions.ZeoFlowMemo}")
  kapt("com.zeoflow.memo:memo-compiler-ktx:${Versions.ZeoFlowMemo}")
  //---------------------------------------------------------------------

  //--------------------------- ANDROID X ------------------------------
  implementation("androidx.constraintlayout:constraintlayout:2.1.4")
  implementation("androidx.appcompat:appcompat:1.5.1")
  implementation("androidx.core:core-ktx:1.9.0")
  implementation("androidx.fragment:fragment-ktx:${Versions.AndroidXFragment}")
  implementation("androidx.navigation:navigation-fragment-ktx:${Versions.AndroidXNavigation}")
  implementation("androidx.navigation:navigation-ui-ktx:${Versions.AndroidXNavigation}")
  implementation("com.google.android.material:material:${Versions.GoogleMaterial}")
  //---------------------------------------------------------------------

  implementation("javax.inject:javax.inject:1")
}
