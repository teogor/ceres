package dev.teogor.ceres

object Configuration {
  private const val majorVersion = 1
  private const val minorVersion = 0
  private const val patchVersion = 0

  const val compileSdk = 33
  const val targetSdk = 33
  const val minSdk = 21
  const val versionName = "$majorVersion.$minorVersion.$patchVersion"
  const val versionCode = 1
  const val snapshotVersionName = "$majorVersion.$minorVersion.${patchVersion + 1}-SNAPSHOT"
  const val artifactGroup = "dev.teogor"
  const val baseArtifactId = "ceres"
  const val baseNamespace = "dev.teogor.ceres"
}
