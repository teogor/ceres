/*
 * Copyright 2022 teogor (Teodor Grigor) All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dev.teogor.ceres.core.util

import android.annotation.SuppressLint
import android.os.Build
import android.os.Build.VERSION
import androidx.annotation.ChecksSdkIntAtLeast

@Suppress("unused")
object VersionUtils {
  /**
   * @return true if device is running API >= 23
   */
  @ChecksSdkIntAtLeast(api = Build.VERSION_CODES.M)
  fun hasMarshmallow(): Boolean {
    return VERSION.SDK_INT >= Build.VERSION_CODES.M
  }

  /**
   * @return true if device is running API >= 24
   */
  @ChecksSdkIntAtLeast(api = Build.VERSION_CODES.N)
  fun hasNougat(): Boolean {
    return VERSION.SDK_INT >= Build.VERSION_CODES.N
  }

  /**
   * @return true if device is running API >= 25
   */
  @ChecksSdkIntAtLeast(api = Build.VERSION_CODES.N_MR1)
  fun hasNougatMR(): Boolean {
    return VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1
  }

  /**
   * @return true if device is running API >= 26
   */
  @ChecksSdkIntAtLeast(api = Build.VERSION_CODES.O)
  fun hasOreo(): Boolean {
    return VERSION.SDK_INT >= Build.VERSION_CODES.O
  }

  /**
   * @return true if device is running API >= 27
   */
  @ChecksSdkIntAtLeast(api = Build.VERSION_CODES.O_MR1)
  fun hasOreoMR1(): Boolean {
    return VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1
  }

  /**
   * @return true if device is running API >= 28
   */
  @ChecksSdkIntAtLeast(api = Build.VERSION_CODES.P)
  fun hasP(): Boolean {
    return VERSION.SDK_INT >= Build.VERSION_CODES.P
  }

  /**
   * @return true if device is running API >= 29
   */
  @ChecksSdkIntAtLeast(api = Build.VERSION_CODES.Q)
  fun hasQ(): Boolean {
    return VERSION.SDK_INT >= Build.VERSION_CODES.Q
  }

  /**
   * @return true if device is running API >= 30
   */
  @ChecksSdkIntAtLeast(api = Build.VERSION_CODES.R)
  fun hasR(): Boolean {
    return VERSION.SDK_INT >= Build.VERSION_CODES.R
  }

  /**
   * @return true if device is running API >= 31
   */
  @ChecksSdkIntAtLeast(api = Build.VERSION_CODES.S)
  fun hasS(): Boolean {
    return VERSION.SDK_INT >= Build.VERSION_CODES.S
  }

  /**
   * Checks if the codename is a matching or higher version than the given build value.
   * @param codename the requested build codename, e.g. `"O"` or `"OMR1"`
   * @param buildCodename the value of [Build.VERSION.CODENAME]
   *
   * @return `true` if APIs from the requested codename are available in the build.
   *
   * @hide
   */
  private fun isAtLeastPreReleaseCodename(
    codename: String,
    buildCodename: String
  ): Boolean {
    // Special case "REL", which means the build is not a pre-release build.
    if ("REL" == buildCodename) {
      return false
    }

    // Otherwise lexically compare them.  Return true if the build codename is equal to or
    // greater than the requested codename.
    val buildCodenameUpper = buildCodename.uppercase()
    val codenameUpper = codename.uppercase()
    return buildCodenameUpper >= codenameUpper
  }

  /**
   * Checks if the device is running on the Android N release or newer.
   *
   * @return `true` if N APIs are available for use
   */
  @ChecksSdkIntAtLeast(api = Build.VERSION_CODES.N)
  @Deprecated(
    """Android N is a finalized release and this method is no longer necessary. It will
                  be removed in a future release of the Support Library. Instead, use
                  {@code Build.VERSION.SDK_INT >= Build.VERSION_CODES.N}."""
  )
  fun isAtLeastN(): Boolean {
    return VERSION.SDK_INT >= 24
  }

  /**
   * Checks if the device is running on the Android N MR1 release or newer.
   *
   * @return `true` if N MR1 APIs are available for use
   */
  @ChecksSdkIntAtLeast(api = Build.VERSION_CODES.N_MR1)
  @Deprecated(
    """Android N MR1 is a finalized release and this method is no longer necessary. It
                  will be removed in a future release of the Support Library. Instead, use
                  {@code Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1}."""
  )
  fun isAtLeastNMR1(): Boolean {
    return VERSION.SDK_INT >= 25
  }

  /**
   * Checks if the device is running on a release version of Android O or newer.
   *
   *
   * @return `true` if O APIs are available for use, `false` otherwise
   */
  @ChecksSdkIntAtLeast(api = Build.VERSION_CODES.O)
  @Deprecated(
    """Android O is a finalized release and this method is no longer necessary. It will
                  be removed in a future release of the Support Library. Instead use
                  {@code Build.VERSION.SDK_INT >= Build.VERSION_CODES.O}."""
  )
  fun isAtLeastO(): Boolean {
    return VERSION.SDK_INT >= 26
  }

  /**
   * Checks if the device is running on a release version of Android O MR1 or newer.
   *
   *
   * @return `true` if O MR1 APIs are available for use, `false` otherwise
   */
  @ChecksSdkIntAtLeast(api = Build.VERSION_CODES.O_MR1)
  @Deprecated(
    """Android O MR1 is a finalized release and this method is no longer necessary. It
                  will be removed in a future release of the Support Library. Instead, use
                  {@code Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1}."""
  )
  fun isAtLeastOMR1(): Boolean {
    return VERSION.SDK_INT >= 27
  }

  /**
   * Checks if the device is running on a release version of Android P or newer.
   *
   *
   * @return `true` if P APIs are available for use, `false` otherwise
   */
  @ChecksSdkIntAtLeast(api = Build.VERSION_CODES.P)
  @Deprecated(
    """Android P is a finalized release and this method is no longer necessary. It
                  will be removed in a future release of the Support Library. Instead, use
                  {@code Build.VERSION.SDK_INT >= Build.VERSION_CODES.P}."""
  )
  fun isAtLeastP(): Boolean {
    return VERSION.SDK_INT >= 28
  }

  /**
   * Checks if the device is running on release version of Android Q or newer.
   *
   *
   * @return `true` if Q APIs are available for use, `false` otherwise
   */
  @ChecksSdkIntAtLeast(api = Build.VERSION_CODES.Q)
  @Deprecated(
    """Android Q is a finalized release and this method is no longer necessary. It
                  will be removed in a future release of the Support Library. Instead, use
                  {@code Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q}."""
  )
  fun isAtLeastQ(): Boolean {
    return VERSION.SDK_INT >= 29
  }

  /**
   * Checks if the device is running on release version of Android R or newer.
   *
   *
   * @return `true` if R APIs are available for use, `false` otherwise
   */
  @ChecksSdkIntAtLeast(api = Build.VERSION_CODES.R)
  @Deprecated(
    """Android R is a finalized release and this method is no longer necessary. It
                  will be removed in a future release of the Support Library. Instead, use
                  {@code Build.VERSION.SDK_INT >= Build.VERSION_CODES.R}."""
  )
  fun isAtLeastR(): Boolean {
    return VERSION.SDK_INT >= 30
  }

  /**
   * Checks if the device is running on a pre-release version of Android S or a release version of
   * Android S or newer.
   *
   * @return `true` if S APIs are available for use, `false` otherwise
   */
  @SuppressLint("RestrictedApi")
  @ChecksSdkIntAtLeast(api = 31, codename = "S")
  @Deprecated(
    """Android S is a finalized release and this method is no longer necessary. It
                  will be removed in a future release of this library. Instead, use
                  {@code Build.VERSION.SDK_INT >= 31}."""
  )
  fun isAtLeastS(): Boolean {
    return (
      VERSION.SDK_INT >= 31 ||
        VERSION.SDK_INT >= 30 && isAtLeastPreReleaseCodename("S", VERSION.CODENAME)
      )
  }

  /**
   * Checks if the device is running on a pre-release version of Android Sv2 or a release
   * version of Android Sv2 or newer.
   *
   * @return `true` if Sv2 APIs are available for use, `false` otherwise
   */
  @ChecksSdkIntAtLeast(api = 32, codename = "Sv2")
  @Deprecated(
    """Android Sv2 is a finalized release and this method is no longer necessary. It
                  will be removed in a future release of this library. Instead, use
                  {@code Build.VERSION.SDK_INT >= 32}."""
  )
  fun isAtLeastSv2(): Boolean {
    return (
      VERSION.SDK_INT >= 32 ||
        VERSION.SDK_INT >= 31 && isAtLeastPreReleaseCodename("Sv2", VERSION.CODENAME)
      )
  }

  /**
   * Checks if the device is running on a pre-release version of Android Tiramisu or a release
   * version of Android Tiramisu or newer.
   *
   *
   * **Note:** When Android Tiramisu is finalized for release, this method will be
   * removed and all calls must be replaced with `Build.VERSION.SDK_INT >= 33`.
   *
   * @return `true` if Tiramisu APIs are available for use, `false` otherwise
   */
  @ChecksSdkIntAtLeast(api = 33, codename = "Tiramisu")
  fun isAtLeastT(): Boolean {
    return (
      VERSION.SDK_INT >= 33 ||
        (
          VERSION.SDK_INT >= 32 &&
            isAtLeastPreReleaseCodename("Tiramisu", VERSION.CODENAME)
          )
      )
  }

  /**
   * Checks if the device is running on a pre-release version of Android U.
   *
   *
   * **Note:** When Android U is finalized for release, this method will be
   * removed and all calls must be replaced with `Build.VERSION.SDK_INT >=
   * Build.VERSION_CODES.U`.
   *
   * @return `true` if U APIs are available for use, `false` otherwise
   */
  @ChecksSdkIntAtLeast(codename = "UpsideDownCake")
  fun isAtLeastU(): Boolean {
    return (
      VERSION.SDK_INT >= 33 &&
        isAtLeastPreReleaseCodename("UpsideDownCake", VERSION.CODENAME)
      )
  }
}
