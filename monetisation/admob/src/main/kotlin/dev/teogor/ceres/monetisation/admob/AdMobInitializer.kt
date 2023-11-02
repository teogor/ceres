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

@file:OptIn(ExperimentalAdsControlApi::class)

package dev.teogor.ceres.monetisation.admob

import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration
import dev.teogor.ceres.core.register.BuildProfiler
import dev.teogor.ceres.core.register.LocalBuildProfiler
import dev.teogor.ceres.monetisation.ads.AdsControl
import dev.teogor.ceres.monetisation.ads.AdsControlProvider
import dev.teogor.ceres.monetisation.ads.ExperimentalAdsControlApi
import dev.teogor.ceres.monetisation.ads.model.AdRequestOptions
import java.io.UnsupportedEncodingException
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

object AdMobInitializer {

  fun configureAdsControl(adsControl: AdsControl) {
    AdsControlProvider.initialize(adsControl)
  }

  internal val adsControl: AdsControl
    get() = AdsControlProvider.adsControl

  internal val buildProfiler: BuildProfiler
    get() = LocalBuildProfiler.current

  fun initialize(context: Context) {
    adsControl.canRequestAds.value = true
    MobileAds.initialize(context) {
      val configuration = RequestConfiguration.Builder().apply {
        if (buildProfiler.isDebuggable) {
          setTestDeviceIds(listOf(getHashedAdvertisingId(context)))
        }
      }.build()
      MobileAds.setRequestConfiguration(configuration)
    }
  }
}

inline fun getRequestConfiguration(
  adRequestOptions: AdRequestOptions,
  crossinline block: RequestConfiguration.Builder.() -> Unit = {},
): RequestConfiguration {
  return MobileAds.getRequestConfiguration()
    .toBuilder()
    .apply {
      setTagForChildDirectedTreatment(
        adRequestOptions.tagForChildDirectedTreatment.intValue,
      )
      setMaxAdContentRating(
        adRequestOptions.maxAdContentRating.stringValue,
      )
      setTagForUnderAgeOfConsent(
        adRequestOptions.tagForUnderAgeOfConsent.intValue,
      )
      block()
    }
    .build()
}

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
