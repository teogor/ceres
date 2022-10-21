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

package dev.teogor.ceres.m3.util

import android.annotation.SuppressLint
import android.os.Build
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import androidx.annotation.ChecksSdkIntAtLeast
import dev.teogor.ceres.core.util.VersionUtils
import java.util.*

/**
 * Utility for applying dynamic colors to application/activities.
 *
 * Class based on [com.google.android.material.color.DynamicColors]
 */
object DynamicColors {

  private val DYNAMIC_COLOR_SUPPORTED_MANUFACTURERS: Map<String, DeviceSupportCondition>
  private val DYNAMIC_COLOR_SUPPORTED_BRANDS: Map<String, DeviceSupportCondition>

  private interface DeviceSupportCondition {
    val isSupported: Boolean
  }

  private val DEFAULT_DEVICE_SUPPORT_CONDITION: DeviceSupportCondition =
    object : DeviceSupportCondition {
      override val isSupported: Boolean
        get() = true
    }

  @SuppressLint("PrivateApi")
  private val SAMSUNG_DEVICE_SUPPORT_CONDITION: DeviceSupportCondition =
    object : DeviceSupportCondition {
      private var version: Long? = null
      override val isSupported: Boolean
        @SuppressLint("DiscouragedPrivateApi")
        get() {
          if (version == null) {
            try {
              val method = Build::class.java.getDeclaredMethod(
                "getLong",
                String::class.java
              )
              method.isAccessible = true
              version = method.invoke(null, "ro.build.version.oneui") as Long
            } catch (e: Exception) {
              version = -1L
            }
          }
          return version!! >= 40100L
        }
    }

  init {
    val deviceMap = HashMap<String, DeviceSupportCondition>()
    deviceMap["fcnt"] = DEFAULT_DEVICE_SUPPORT_CONDITION
    deviceMap["google"] = DEFAULT_DEVICE_SUPPORT_CONDITION
    deviceMap["hmd global"] = DEFAULT_DEVICE_SUPPORT_CONDITION
    deviceMap["infinix"] = DEFAULT_DEVICE_SUPPORT_CONDITION
    deviceMap["infinix mobility limited"] = DEFAULT_DEVICE_SUPPORT_CONDITION
    deviceMap["itel"] = DEFAULT_DEVICE_SUPPORT_CONDITION
    deviceMap["kyocera"] = DEFAULT_DEVICE_SUPPORT_CONDITION
    deviceMap["lenovo"] = DEFAULT_DEVICE_SUPPORT_CONDITION
    deviceMap["lge"] = DEFAULT_DEVICE_SUPPORT_CONDITION
    deviceMap["motorola"] = DEFAULT_DEVICE_SUPPORT_CONDITION
    deviceMap["nothing"] = DEFAULT_DEVICE_SUPPORT_CONDITION
    deviceMap["oneplus"] = DEFAULT_DEVICE_SUPPORT_CONDITION
    deviceMap["oppo"] = DEFAULT_DEVICE_SUPPORT_CONDITION
    deviceMap["realme"] = DEFAULT_DEVICE_SUPPORT_CONDITION
    deviceMap["robolectric"] = DEFAULT_DEVICE_SUPPORT_CONDITION
    deviceMap["samsung"] = SAMSUNG_DEVICE_SUPPORT_CONDITION
    deviceMap["sharp"] = DEFAULT_DEVICE_SUPPORT_CONDITION
    deviceMap["sony"] = DEFAULT_DEVICE_SUPPORT_CONDITION
    deviceMap["tcl"] = DEFAULT_DEVICE_SUPPORT_CONDITION
    deviceMap["tecno"] = DEFAULT_DEVICE_SUPPORT_CONDITION
    deviceMap["tecno mobile limited"] = DEFAULT_DEVICE_SUPPORT_CONDITION
    deviceMap["vivo"] = DEFAULT_DEVICE_SUPPORT_CONDITION
    deviceMap["wingtech"] = DEFAULT_DEVICE_SUPPORT_CONDITION
    deviceMap["xiaomi"] = DEFAULT_DEVICE_SUPPORT_CONDITION
    DYNAMIC_COLOR_SUPPORTED_MANUFACTURERS = Collections.unmodifiableMap(deviceMap)

    val deviceBrandsMap = HashMap<String, DeviceSupportCondition>()
    deviceBrandsMap["asus"] = DEFAULT_DEVICE_SUPPORT_CONDITION
    deviceBrandsMap["jio"] = DEFAULT_DEVICE_SUPPORT_CONDITION
    DYNAMIC_COLOR_SUPPORTED_BRANDS = Collections.unmodifiableMap(deviceBrandsMap)
  }

  /** Returns `true` if dynamic colors are available on the current SDK level.  */
  @SuppressLint("DefaultLocale")
  @ChecksSdkIntAtLeast(api = VERSION_CODES.S)
  fun isDynamicColorAvailable(): Boolean {
    if (VERSION.SDK_INT < VERSION_CODES.S) {
      return false
    }
    if (VersionUtils.isAtLeastT()) {
      return true
    }
    var deviceSupportCondition =
      DYNAMIC_COLOR_SUPPORTED_MANUFACTURERS[
        Build.MANUFACTURER.lowercase(
          Locale.getDefault()
        )
      ]
    if (deviceSupportCondition == null) {
      deviceSupportCondition = DYNAMIC_COLOR_SUPPORTED_BRANDS[
        Build.BRAND.lowercase(
          Locale.getDefault()
        )
      ]
    }
    return deviceSupportCondition != null && deviceSupportCondition.isSupported
  }
}
