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
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration
import com.google.android.gms.ads.initialization.InitializationStatus
import com.zeoflow.startup.ktx.ApplicationInitializer
import dev.teogor.ceres.ads.AdsData
import dev.teogor.ceres.ads.isAdActivity
import dev.teogor.ceres.ads.utils.Constants
import dev.teogor.ceres.core.construct.AppData
import dev.teogor.ceres.core.logger.Logger
import dev.teogor.ceres.core.util.AppUtils
import dev.teogor.ceres.extensions.activityName
import dev.teogor.ceres.extensions.extrasBoolean

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
    log("activityData: ${activity.activityName} --destroyed:${activity.isDestroyed} --adsInitialized:$adsInitialized")
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
    activity.extrasBoolean(
      key = Constants.FORCE_SHOW_APP_OPEN_AD
    ) {
      if (this) {
        log("Showing AppOpenAd because it's intent was flagged with showAppOpenAd()")
        showAd()
      }
    }
  }

  override fun onActivityResumed(activity: Activity) {
    if (activity.isAdActivity()) {
      log("onActivityResumed::${activity.activityName} ${activity.isDestroyed}")

    }
  }

  override fun onActivityPaused(activity: Activity) {
    if (activity.isAdActivity()) {
      log("onActivityPaused::${activity.activityName}")
    }
  }

  override fun onActivityStopped(activity: Activity) {
    if (activity.isAdActivity()) {
      log("onActivityStopped::${activity.activityName}")
    }
  }

  override fun onActivitySaveInstanceState(activity: Activity, bundle: Bundle) {
    if (activity.isAdActivity()) {
      log("onActivitySaveInstanceState::${activity.activityName}")
    }
  }

  override fun onActivityDestroyed(activity: Activity) {
    if (activity.isAdActivity()) {
      log("onActivityDestroyed::${activity.activityName}")
    }
    if (activity.isAdActivity()) {
      AdsData.fullScreenAdIsShowing = false
    }
  }

  /** LifecycleObserver method that shows the app open ad when the app moves to foreground. */
  override fun onStart(owner: LifecycleOwner) {
    super.onStart(owner)
    showAd()
  }

  private fun showAd() {
    if (!AdsData.enabled) {
      return
    }
    AdsData.openAdsWeak.map { weakAd -> weakAd.get()!! }.forEach { ad ->
      ad.show()
    }
  }
}
