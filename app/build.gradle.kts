import dev.teogor.ceres.Configuration
import dev.teogor.ceres.Versions

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
  // hilt
  id("com.google.dagger.hilt.android")
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
  implementation(project(":ceres-ads"))
  implementation(project(":ceres-bindings"))
  implementation(project(":ceres-components"))
  implementation(project(":ceres-core"))
  implementation(project(":ceres-extensions"))
  implementation(project(":ceres-firebase"))
  implementation(project(":ceres-m3"))
  implementation(project(":ceres-widget"))
  implementation(project(":ceres-wear-os"))

  //----------------------------- ZEOFLOW -------------------------------
  implementation("com.zeoflow.startup:startup-ktx:${Versions.ZeoFlowStartUp}")
  implementation("com.zeoflow.memo:memo:${Versions.ZeoFlowMemo}")
  implementation("com.zeoflow.memo:memo-runtime:${Versions.ZeoFlowMemo}")
  kapt("com.zeoflow.memo:memo-compiler-ktx:${Versions.ZeoFlowMemo}")
  //---------------------------------------------------------------------

  //----------------------------- DEFAULT -------------------------------
  implementation("androidx.constraintlayout:constraintlayout:2.1.4")
  implementation("androidx.appcompat:appcompat:1.5.1")
  implementation("androidx.core:core-ktx:1.9.0")
  implementation("androidx.fragment:fragment-ktx:${Versions.AndroidXFragment}")
  implementation("androidx.navigation:navigation-fragment-ktx:${Versions.AndroidXNavigation}")
  implementation("androidx.navigation:navigation-ui-ktx:${Versions.AndroidXNavigation}")
  implementation("androidx.multidex:multidex:2.0.1")
  implementation("com.google.android.material:material:${Versions.GoogleMaterial}")
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
  implementation(platform("com.google.firebase:firebase-bom:31.0.1"))
  //---------------------------------------------------------------------

  //------------------------------ DAGGER -------------------------------
  implementation("com.google.dagger:hilt-android:${Versions.DaggerHilt}")
  kapt("com.google.dagger:hilt-compiler:${Versions.DaggerHilt}")
  //---------------------------------------------------------------------

  implementation("dev.chrisbanes.insetter:insetter:0.6.1")

  debugImplementation("com.squareup.leakcanary:leakcanary-android:2.9.1")
}
