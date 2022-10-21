import dev.teogor.ceres.Configuration

plugins {
  // android
  id("com.android.library")
  // kotlin
  id("kotlin-android")
  id("kotlin-kapt")
  id("kotlin-parcelize")
  // dagger
  // todo once a higher version of ´dagger´ accepts the new
  //  plugin id apply it -> id("com.google.dagger.hilt.android")
  id("dagger.hilt.android.plugin")
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
  implementation("com.zeoflow.startup:startup-ktx:1.1.0")
  implementation("com.zeoflow.memo:memo:1.3.1")
  implementation("com.zeoflow.memo:memo-runtime:1.3.1")
  //---------------------------------------------------------------------

  //----------------------------- FIREBASE ------------------------------
  implementation(platform("com.google.firebase:firebase-bom:31.0.0"))
  implementation("com.google.firebase:firebase-analytics-ktx")
  implementation("com.google.firebase:firebase-appcheck-safetynet")
  implementation("com.google.firebase:firebase-config-ktx")
  implementation("com.google.firebase:firebase-crashlytics-ktx")
  implementation("com.google.firebase:firebase-perf-ktx")
  //---------------------------------------------------------------------

  //------------------------------ DAGGER -------------------------------
  //noinspection GradleDependency todo ´dagger´ conflicts with memo
  implementation("com.google.dagger:hilt-android:2.40.5")
  //noinspection GradleDependency todo ´dagger´ conflicts with memo
  kapt("com.google.dagger:hilt-compiler:2.40.5")
  //---------------------------------------------------------------------

  implementation("androidx.core:core-ktx:1.9.0")
  implementation("androidx.appcompat:appcompat:1.5.1")
}
