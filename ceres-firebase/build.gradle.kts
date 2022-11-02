import dev.teogor.ceres.Configuration
import dev.teogor.ceres.Versions

plugins {
  // android
  id("com.android.library")
  // kotlin
  id("kotlin-android")
  id("kotlin-kapt")
  id("kotlin-parcelize")
  // dagger
  id("com.google.dagger.hilt.android")
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
  implementation(project(":ceres-core"))

  //----------------------------- ZEOFLOW -------------------------------
  implementation("com.zeoflow.startup:startup-ktx:${Versions.ZeoFlowStartUp}")
  implementation("com.zeoflow.memo:memo:${Versions.ZeoFlowMemo}")
  implementation("com.zeoflow.memo:memo-runtime:${Versions.ZeoFlowMemo}")
  //---------------------------------------------------------------------

  //----------------------------- FIREBASE ------------------------------
  implementation(platform("com.google.firebase:firebase-bom:31.0.1"))
  implementation("com.google.firebase:firebase-analytics-ktx")
  implementation("com.google.firebase:firebase-appcheck-safetynet")
  implementation("com.google.firebase:firebase-config-ktx")
  implementation("com.google.firebase:firebase-crashlytics-ktx")
  implementation("com.google.firebase:firebase-perf-ktx")
  //---------------------------------------------------------------------

  //------------------------------ DAGGER -------------------------------
  implementation("com.google.dagger:hilt-android:${Versions.DaggerHilt}")
  kapt("com.google.dagger:hilt-compiler:${Versions.DaggerHilt}")
  //---------------------------------------------------------------------

  implementation("androidx.core:core-ktx:1.9.0")
  implementation("androidx.appcompat:appcompat:1.5.1")
}
