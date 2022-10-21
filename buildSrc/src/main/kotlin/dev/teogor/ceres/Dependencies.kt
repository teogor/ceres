package dev.teogor.ceres

object Versions {
  internal const val KOTLIN = "1.7.20"
  internal const val ANDROID_GRADLE_SPOTLESS = "6.7.0"

  internal const val GoogleMaterial = "1.8.0-alpha01"
}

object Dependencies {
  const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.KOTLIN}"
  const val spotlessGradlePlugin =
    "com.diffplug.spotless:spotless-plugin-gradle:${Versions.ANDROID_GRADLE_SPOTLESS}"

  const val GoogleMaterial = "com.google.android.material:material:${Versions.GoogleMaterial}"
}
