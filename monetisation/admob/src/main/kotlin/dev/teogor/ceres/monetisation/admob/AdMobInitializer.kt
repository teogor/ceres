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

package dev.teogor.ceres.monetisation.admob

import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration
import dev.teogor.ceres.core.network.ConnectivityManagerNetworkMonitor
import dev.teogor.ceres.core.runtime.AppMetadataManager
import java.io.UnsupportedEncodingException
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

object AdMobInitializer {

  private var connectivityManager: ConnectivityManagerNetworkMonitor? = null

  @SuppressLint("HardwareIds")
  fun getHashedAdvertisingId(
    context: Context,
  ): String {
    val androidId: String = Settings.Secure.getString(
      context.contentResolver,
      Settings.Secure.ANDROID_ID,
    )
    return md5(androidId)?.uppercase() ?: ""
  }

  fun initialize(context: Context) {
    MobileAds.initialize(context) {
      val configuration = RequestConfiguration.Builder().apply {
        if (AppMetadataManager.isDebuggable) {
          setTestDeviceIds(listOf(getHashedAdvertisingId(context)))
        }
      }.build()
      MobileAds.setRequestConfiguration(configuration)
    }
  }

  private fun md5(md5: String): String? {
    try {
      val md = MessageDigest.getInstance("MD5")
      val array = md.digest(md5.toByteArray(charset("UTF-8")))
      val sb = StringBuffer()
      for (i in array.indices) {
        sb.append(Integer.toHexString(array[i].toInt() and 0xFF or 0x100).substring(1, 3))
      }
      return sb.toString()
    } catch (_: NoSuchAlgorithmException) {
    } catch (_: UnsupportedEncodingException) {
    }
    return null
  }
}
