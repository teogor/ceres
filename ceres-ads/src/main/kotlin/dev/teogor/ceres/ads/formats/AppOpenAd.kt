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

package dev.teogor.ceres.ads.formats

import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.appopen.AppOpenAd
import dev.teogor.ceres.ads.Ad
import dev.teogor.ceres.ads.AdEvent
import dev.teogor.ceres.ads.utils.isAdActivity
import dev.teogor.ceres.core.global.GlobalData

abstract class AppOpenAd : Ad() {

  override fun useCache() = false

  override fun load() {
    super.load()

    onListener(AdEvent.IS_LOADING)

    val request = AdRequest.Builder().build()
    AppOpenAd.load(
      context,
      id,
      request,
      AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT,
      object : AppOpenAd.AppOpenAdLoadCallback() {
        /**
         * Called when an app open ad has loaded.
         *
         * @param ad the loaded app open ad.
         */
        override fun onAdLoaded(ad: AppOpenAd) {
          cacheAds.appOpenAd = ad
          log("onAdLoaded.")
          onListener(AdEvent.LOADED)
        }

        /**
         * Called when an app open ad has failed to load.
         *
         * @param adError the error.
         */
        override fun onAdFailedToLoad(adError: LoadAdError) {
          log("onAdFailedToLoad: " + adError.message)
          val error =
            "domain: ${adError.domain}, code: ${adError.code}, " + "message: ${adError.message}"
          log("Ad failed to load: $error")
          onListener(AdEvent.FAILED_TO_LOAD)
        }
      }
    )
  }

  override fun show() {
    super.show()

    if (!canShow()) {
      return
    }
    if (GlobalData.activity.isAdActivity()) {
      log("Another ad is showing.")
      return
    }
    val ad = cacheAds.appOpenAd
    if (ad == null) {
      load()
      return
    }
    if (isShowing) {
      log("The app open ad is already showing.")
      return
    }
    if (isLoading) {
      log("The app open ad is loading.")
      return
    }

    log("Will show ad.")

    ad.fullScreenContentCallback = object : FullScreenContentCallback() {
      override fun onAdClicked() {
        log("Ad clicked.")
        onListener(AdEvent.CLICKED)
      }

      override fun onAdImpression() {
        log("Ad shown - impression recognized.")
        onListener(AdEvent.IMPRESSION)
      }

      /** Called when full screen content is dismissed. */
      override fun onAdDismissedFullScreenContent() {
        // Set the reference to null so isAdAvailable() returns false.
        log("Ad was dismissed.")
        onListener(AdEvent.DISMISSED)
      }

      /** Called when fullscreen content failed to show. */
      override fun onAdFailedToShowFullScreenContent(adError: AdError) {
        log("Ad failed to show.")
        onListener(AdEvent.NOT_COMPLETED)
      }

      /** Called when fullscreen content is shown. */
      override fun onAdShowedFullScreenContent() {
        log("Ad showed fullscreen content.")
        // Called when ad is dismissed.
        onListener(AdEvent.COMPLETED)
      }
    }
    ad.show(GlobalData.activity)
  }
}
