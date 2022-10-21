import dev.teogor.ceres.Configuration
import dev.teogor.ceres.Dependencies

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
  implementation(project(":ceres-core"))
  implementation(project(":ceres-extensions"))

  //----------------------------- ZEOFLOW -------------------------------
  implementation("com.zeoflow.startup:startup-ktx:1.1.0")
  implementation("com.zeoflow.memo:memo:1.3.1")
  implementation("com.zeoflow.memo:memo-runtime:1.3.1")
  kapt("com.zeoflow.memo:memo-compiler-ktx:1.3.1")
  //---------------------------------------------------------------------

  //--------------------------- ANDROID X ------------------------------
  implementation(Dependencies.GoogleMaterial)
  implementation("androidx.constraintlayout:constraintlayout:2.1.4")
  implementation("androidx.appcompat:appcompat:1.5.1")
  implementation("androidx.core:core-splashscreen:1.0.0")
  implementation("androidx.core:core-ktx:1.9.0")
  implementation("androidx.fragment:fragment-ktx:1.5.3")
  implementation("androidx.navigation:navigation-fragment-ktx:2.5.2")
  implementation("androidx.navigation:navigation-ui-ktx:2.5.2")
  //---------------------------------------------------------------------

  //------------------------------ DAGGER -------------------------------
  //noinspection GradleDependency todo ´dagger´ conflicts with memo
  implementation("com.google.dagger:hilt-android:2.40.5")
  //noinspection GradleDependency todo ´dagger´ conflicts with memo
  kapt("com.google.dagger:hilt-compiler:2.40.5")
  //---------------------------------------------------------------------
}
