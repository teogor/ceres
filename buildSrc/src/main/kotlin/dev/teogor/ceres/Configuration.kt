package dev.teogor.ceres

import org.gradle.api.JavaVersion

object Configuration {

  /**
   * * * * * * * * * * * * * * * * *
   *         Library Names         *
   * * * * * * * * * * * * * * * * *
   */
  const val artifactGroup = "dev.teogor"
  const val baseArtifactId = "ceres"
  const val baseNamespace = "$artifactGroup.$baseArtifactId"

  /**
   * * * * * * * * * * * * * * * * *
   *          Android SDK          *
   * * * * * * * * * * * * * * * * *
   */
  const val compileSdk = 33
  const val targetSdk = 33
  const val minSdk = 21

  /**
   * * * * * * * * * * * * * * * * *
   *        Java Environment       *
   * * * * * * * * * * * * * * * * *
   */
  const val jvmTarget = "11"
  val javaVersion = JavaVersion.VERSION_11

  /**
   * * * * * * * * * * * * * * * * *
   *            Version            *
   * * * * * * * * * * * * * * * * *
   */
  private const val majorVersion = 1
  private const val minorVersion = 0
  private const val patchVersion = 0
  private const val preReleaseVersion = 4
  const val versionCode = 1
  val versionFlags = VersionFlags.ALPHA
  val versionName = ConfigurationImpl.versionNameImpl()
  val snapshotVersionName = ConfigurationImpl.snapshotVersionNameImpl()

  /**
   * Internal helper class for [Configuration]
   *
   * @hide
   */
  internal object ConfigurationImpl {

    /**
     * Build a version name based on [majorVersion], [minorVersion],
     * [patchVersion] and [preReleaseVersion]
     *
     * @return versionName based on version flags
     */
    internal fun versionNameImpl(): String {
      val baseVersionName = "$majorVersion.$minorVersion.$patchVersion"
      return when (versionFlags) {
        VersionFlags.NO -> baseVersionName
        VersionFlags.ALPHA -> "$baseVersionName-alpha${preReleaseVersionImpl()}"
        VersionFlags.BETA -> "$baseVersionName-beta${preReleaseVersionImpl()}"
      }
    }

    /**
     * Build a snapshot version name based on [majorVersion],
     * [minorVersion], [patchVersion] and [preReleaseVersion]
     *
     * @return snapshotVersionName based on version flags
     */
    internal fun snapshotVersionNameImpl(): String {
      val baseVersionName = "$majorVersion.$minorVersion.${patchVersion + 1}-SNAPSHOT"
      return when (versionFlags) {
        VersionFlags.NO -> baseVersionName
        VersionFlags.ALPHA -> "$baseVersionName-alpha${preReleaseVersionImpl()}"
        VersionFlags.BETA -> "$baseVersionName-beta${preReleaseVersionImpl()}"
      }
    }

    private fun preReleaseVersionImpl(): String {
      return if (preReleaseVersion < 10) {
        "0$preReleaseVersion"
      } else {
        "$preReleaseVersion"
      }
    }
  }

}
