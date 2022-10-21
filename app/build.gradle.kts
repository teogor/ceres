import dev.teogor.ceres.Configuration
import dev.teogor.ceres.Dependencies

plugins {
  // android
  id("com.android.application")
  // kotlin
  id("kotlin-android")
  id("kotlin-kapt")
  id("kotlin-parcelize")
  // google services
  id("com.google.gms.google-services")
  // firebase suite
  id("com.google.firebase.crashlytics")
  id("com.google.firebase.firebase-perf")
  id("com.google.firebase.appdistribution")
  // dagger
  // todo once a higher version of ´dagger´ accepts the new
  //  plugin id apply it -> id("com.google.dagger.hilt.android")
  id("dagger.hilt.android.plugin")
  // safe args
  id("androidx.navigation.safeargs.kotlin")
}

android {
  namespace = "${Configuration.baseNamespace}"
  compileSdk = Configuration.compileSdk

  defaultConfig {
    applicationId = "${Configuration.baseNamespace}"
    minSdk = Configuration.minSdk
    targetSdk = Configuration.targetSdk
    versionCode = Configuration.versionCode
    versionName = Configuration.versionName

    multiDexEnabled = true
  }

  buildTypes {
    release {
      isDebuggable = false
      isMinifyEnabled = true
      isShrinkResources = true
      proguardFiles(
        getDefaultProguardFile("proguard-android-optimize.txt"),
        "proguard-rules.pro"
      )

      resValue(
        "string",
        "GMS_APPLICATION_ID",
        "\"ca-app-pub-3940256099942544~3347511713\""
      )
    }

    debug {
      isDebuggable = true
      versionNameSuffix = " [debug]"
      applicationIdSuffix = ".dev"

      resValue(
        "string",
        "GMS_APPLICATION_ID",
        "\"ca-app-pub-3940256099942544~3347511713\""
      )
    }
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
  implementation(project(":ceres-firebase"))
  implementation(project(":ceres-components"))
  implementation(project(":ceres-extensions"))
  implementation(project(":ceres-bindings"))
  implementation(project(":ceres-m3"))
  implementation(project(":ceres-ads"))
  implementation(project(":ceres-widget"))
  implementation(project(":ceres-wear-os"))

  //----------------------------- ZEOFLOW -------------------------------
  implementation("com.zeoflow.startup:startup-ktx:1.1.0")
  implementation("com.zeoflow.memo:memo:1.3.1")
  implementation("com.zeoflow.memo:memo-runtime:1.3.1")
  kapt("com.zeoflow.memo:memo-compiler-ktx:1.3.1")
  //---------------------------------------------------------------------

  //----------------------------- DEFAULT -------------------------------
  implementation(Dependencies.GoogleMaterial)
  implementation("androidx.constraintlayout:constraintlayout:2.1.4")
  implementation("androidx.appcompat:appcompat:1.5.1")
  implementation("androidx.core:core-splashscreen:1.0.0")
  implementation("androidx.core:core-ktx:1.9.0")
  implementation("androidx.fragment:fragment-ktx:1.5.3")
  implementation("androidx.navigation:navigation-fragment-ktx:2.5.2")
  implementation("androidx.navigation:navigation-ui-ktx:2.5.2")
  implementation("androidx.multidex:multidex:2.0.1")
  //---------------------------------------------------------------------

  //--------------------------- PLAY SERVICES ---------------------------
  implementation("com.google.android.gms:play-services-base:18.1.0")
  implementation("com.google.android.gms:play-services-ads:21.3.0")
  implementation("com.google.android.gms:play-services-gcm:17.0.0")
  implementation("com.google.android.gms:play-services-analytics:18.0.2")
  implementation("com.google.android.ump:user-messaging-platform:2.0.0")
  implementation("com.google.android.play:core:1.10.3")
  //---------------------------------------------------------------------

  //----------------------------- FIREBASE ------------------------------
  implementation(platform("com.google.firebase:firebase-bom:31.0.0"))
  implementation("com.google.firebase:firebase-analytics-ktx")
  implementation("com.google.firebase:firebase-appcheck-safetynet")
  implementation("com.google.firebase:firebase-config-ktx")
  implementation("com.google.firebase:firebase-crashlytics-ktx")
  implementation("com.google.firebase:firebase-dynamic-links-ktx")
  implementation("com.google.firebase:firebase-messaging-ktx")
  implementation("com.google.firebase:firebase-perf-ktx")
  //---------------------------------------------------------------------

  //------------------------------ DAGGER -------------------------------
  //noinspection GradleDependency todo ´dagger´ conflicts with memo
  implementation("com.google.dagger:hilt-android:2.40.5")
  //noinspection GradleDependency todo ´dagger´ conflicts with memo
  kapt("com.google.dagger:hilt-compiler:2.40.5")
  //---------------------------------------------------------------------

  implementation("dev.chrisbanes.insetter:insetter:0.6.1")

  debugImplementation("com.squareup.leakcanary:leakcanary-android:2.9.1")
}
