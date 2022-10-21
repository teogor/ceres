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

package dev.teogor.ceres.core.crash

import android.content.Context
import android.os.Build
import androidx.annotation.IntRange
import androidx.core.content.pm.PackageInfoCompat
import dev.teogor.ceres.core.util.AppUtils
import java.util.Arrays
import java.util.Locale

class DeviceInfo(context: Context) {
  private val abis = Build.SUPPORTED_ABIS
  private val abis32Bits = Build.SUPPORTED_32_BIT_ABIS
  private val abis64Bits = Build.SUPPORTED_64_BIT_ABIS
  private val brand = Build.BRAND
  private val buildID = Build.DISPLAY
  private val buildVersion = Build.VERSION.INCREMENTAL
  private val device = Build.DEVICE
  private val hardware = Build.HARDWARE
  private val manufacturer = Build.MANUFACTURER
  private val model = Build.MODEL
  private val product = Build.PRODUCT
  private val releaseVersion = Build.VERSION.RELEASE

  @IntRange(from = 0)
  private val sdkVersion = Build.VERSION.SDK_INT
  private var versionCode = 0L
  private var versionName: String? = null

  fun toMarkdown(): String {
    return """
               Device info:
               ---
               <table>
               <tr><td><b>App version</b></td><td>$versionName</td></tr>
               <tr><td>App version code</td><td>$versionCode</td></tr>
               <tr><td>Android build version</td><td>$buildVersion</td></tr>
               <tr><td>Android release version</td><td>$releaseVersion</td></tr>
               <tr><td>Android SDK version</td><td>$sdkVersion</td></tr>
               <tr><td>Android build ID</td><td>$buildID</td></tr>
               <tr><td>Device brand</td><td>$brand</td></tr>
               <tr><td>Device manufacturer</td><td>$manufacturer</td></tr>
               <tr><td>Device name</td><td>$device</td></tr>
               <tr><td>Device model</td><td>$model</td></tr>
               <tr><td>Device product name</td><td>$product</td></tr>
               <tr><td>Device hardware name</td><td>$hardware</td></tr>
               <tr><td>ABIs</td><td>${Arrays.toString(abis)}</td></tr>
               <tr><td>ABIs (32bit)</td><td>${Arrays.toString(abis32Bits)}</td></tr>
               <tr><td>ABIs (64bit)</td><td>${Arrays.toString(abis64Bits)}</td></tr>
               </table>

    """.trimIndent()
  }

  override fun toString(): String {
    return """
            App version: $versionName
            App version code: $versionCode
            Android build version: $buildVersion
            Android release version: $releaseVersion
            Android SDK version: $sdkVersion
            Android build ID: $buildID
            Device brand: $brand
            Device manufacturer: $manufacturer
            Device name: $device
            Device model: $model
            Device product name: $product
            Device hardware name: $hardware
            ABIs: ${Arrays.toString(abis)}
            ABIs (32bit): ${Arrays.toString(abis32Bits)}
            ABIs (64bit): ${Arrays.toString(abis64Bits)}
            System language: ${Locale.getDefault().toLanguageTag()}
    """.trimIndent()
  }

  init {
    val packageInfo = AppUtils.appPackageInfo
    versionCode = PackageInfoCompat.getLongVersionCode(packageInfo)
    versionName = packageInfo.versionName
  }
}
