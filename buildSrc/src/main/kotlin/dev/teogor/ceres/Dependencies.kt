package dev.teogor.ceres

object Dependencies {
  const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.KOTLIN}"
  const val spotlessGradlePlugin =
    "com.diffplug.spotless:spotless-plugin-gradle:${Versions.ANDROID_GRADLE_SPOTLESS}"

  const val GoogleMaterial = "com.google.android.material:material:${Versions.GoogleMaterial}"
}
