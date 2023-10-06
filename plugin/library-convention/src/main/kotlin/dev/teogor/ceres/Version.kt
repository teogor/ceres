/*
 * Copyright 2023 teogor (Teodor Grigor)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dev.teogor.ceres

object Version {
  private const val majorVersion = 1
  private const val minorVersion = 0
  private const val patchVersion = 0
  private const val preReleaseVersion = 3
  const val code = 1

  val flags = Flags.ALPHA
  val name = VersionImplementation.versionNameImpl()
  val snapshotName = VersionImplementation.snapshotVersionNameImpl()

  enum class Flags {
    NO,
    ALPHA,
    BETA,
  }

  /**
   * Internal helper class for [Version].
   *
   * @hide
   */
  internal object VersionImplementation {

    /**
     * Build a version name based on [majorVersion], [minorVersion],
     * [patchVersion], and [preReleaseVersion].
     *
     * @return The version name based on version flags.
     */
    internal fun versionNameImpl(): String {
      val baseVersionName = "$majorVersion.$minorVersion.$patchVersion"
      return when (flags) {
        Flags.NO -> baseVersionName
        Flags.ALPHA -> "$baseVersionName-alpha${preReleaseVersionImpl()}"
        Flags.BETA -> "$baseVersionName-beta${preReleaseVersionImpl()}"
      }
    }

    /**
     * Build a snapshot version name based on [majorVersion],
     * [minorVersion], [patchVersion], and [preReleaseVersion].
     *
     * @return The snapshot version name based on version flags.
     */
    internal fun snapshotVersionNameImpl(): String {
      val baseVersionName = "$majorVersion.$minorVersion.${patchVersion + 1}-SNAPSHOT"
      return when (flags) {
        Flags.NO -> baseVersionName
        Flags.ALPHA -> "$baseVersionName-alpha${preReleaseVersionImpl()}"
        Flags.BETA -> "$baseVersionName-beta${preReleaseVersionImpl()}"
      }
    }

    /**
     * Get the formatted pre-release version.
     *
     * @return The formatted pre-release version.
     */
    private fun preReleaseVersionImpl(): String {
      return preReleaseVersion.toString().padStart(2, '0')
    }
  }
}
