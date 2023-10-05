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

import android.content.Context
import android.content.pm.PackageManager
import androidx.startup.Initializer
import dev.teogor.ceres.monetisation.ads.AndroidAdsControl
import dev.teogor.ceres.monetisation.ads.ExperimentalAdsControlApi

/**
 * Initializes [AdmobManagerInitializer] using `androidx.startup`.
 */
class AdmobManagerInitializer : Initializer<Unit> {
  override fun create(context: Context) {
    val packageManager = context.packageManager
    val applicationInfo = packageManager.getApplicationInfo(
      context.packageName,
      PackageManager.GET_META_DATA,
    )
    val manualAdsSetup = applicationInfo.metaData?.getBoolean(
      "dev.teogor.ceres.monetisation.admob.flag.MANUAL_ADS_SETUP",
      false,
    ) ?: false

    if (!manualAdsSetup) {
      AdMobInitializer.configureAdsControl(
        AndroidAdsControl().apply {
          canRequestAds.value = true
        },
      )
      AdMobInitializer.initialize(context)
    }
    AdMobLifecycleManager(context)
  }

  override fun dependencies() = emptyList<Class<out Initializer<*>?>>()
}
