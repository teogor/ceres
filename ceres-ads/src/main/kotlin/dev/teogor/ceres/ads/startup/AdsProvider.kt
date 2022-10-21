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

package dev.teogor.ceres.ads.startup

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.ProcessLifecycleOwner
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration
import com.google.android.gms.ads.initialization.InitializationStatus
import com.zeoflow.startup.ktx.ApplicationInitializer
import dev.teogor.ceres.ads.utils.Constants
import dev.teogor.ceres.core.construct.AppData
import dev.teogor.ceres.core.logger.Logger
import dev.teogor.ceres.core.util.AppUtils

/**
 * fixme Interstitials that show when your app is in the background are a
 *  violation of AdMob policies and may lead to blocked ad serving. To
 *  learn more, visit
 */
class AdsProvider constructor(application: Application) :
  Application.ActivityLifecycleCallbacks,
  DefaultLifecycleObserver,
  Logger {

  private var adsInitialized = false

  init {
    // Log the Mobile Ads SDK version.
    Log.d(Constants.LOG_TAG, "Google Mobile Ads SDK Version: " + MobileAds.getVersion())

    application.registerActivityLifecycleCallbacks(this)
    ProcessLifecycleOwner.get().lifecycle.addObserver(this)
  }

  override fun onActivityCreated(activity: Activity, bundle: Bundle?) {
    initAds()
  }

  private fun initAds() {
    if (adsInitialized) {
      return
    }
    adsInitialized = true
    initializeModule()
    addTestDevice()
  }

  private fun initializeModule() {
    MobileAds.initialize(ApplicationInitializer.context) { initializationStatus: InitializationStatus ->
      val statusMap =
        initializationStatus.adapterStatusMap
      for (adapterClass in statusMap.keys) {
        val status = statusMap[adapterClass] ?: continue
      }
    }
  }

  private fun addTestDevice() {
    if (!AppData.IsDebuggable) {
      return
    }
    val configuration = RequestConfiguration.Builder()
      .setTestDeviceIds(listOf(AppUtils.deviceID))
      .build()
    MobileAds.setRequestConfiguration(configuration)
  }

  override fun onActivityStarted(activity: Activity) {
    showAd()
  }

  override fun onActivityResumed(activity: Activity) {
  }

  override fun onActivityPaused(activity: Activity) {
  }

  override fun onActivityStopped(activity: Activity) {
  }

  override fun onActivitySaveInstanceState(activity: Activity, bundle: Bundle) {
  }

  override fun onActivityDestroyed(activity: Activity) {
  }

  private fun showAd() {
    if (!AdsStartUp.enabled) {
      return
    }
    AdsStartUp.openAdsWeak.map { weakAd -> weakAd.get()!! }.forEach { ad ->
      ad.show()
    }
  }
}
