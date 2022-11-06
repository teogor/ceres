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
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import dev.teogor.ceres.ads.Ad
import dev.teogor.ceres.ads.AdEvent
import dev.teogor.ceres.ads.AdsData
import dev.teogor.ceres.core.global.GlobalData

abstract class InterstitialAd : Ad() {

  override fun useCache() = false

  override fun load() {
    super.load()

    onListener(AdEvent.IS_LOADING)

    val adRequest = AdRequest.Builder().build()
    InterstitialAd.load(
      context,
      id,
      adRequest,
      object : InterstitialAdLoadCallback() {
        override fun onAdFailedToLoad(adError: LoadAdError) {
          log(adError.message)
          cacheAds.interstitialAd = null
          val error =
            "domain: ${adError.domain}, code: ${adError.code}, " + "message: ${adError.message}"
          log("Ad failed to load: $error")
          onListener(AdEvent.FAILED_TO_LOAD)
        }

        override fun onAdLoaded(interstitialAd: InterstitialAd) {
          log("Ad was loaded.")
          cacheAds.interstitialAd = interstitialAd
          onListener(AdEvent.LOADED)
        }
      }
    )
  }

  override fun show() {
    super.show()

    if (!canShow()) {
      return
    }
    if (AdsData.fullScreenAdIsShowing) {
      log("Another ad is showing.")
      return
    }
    val ad = cacheAds.interstitialAd
    if (ad == null) {
      log("ad was not loaded. requesting to load.")
      load()
      return
    }
    if (isShowing) {
      log("The interstitial ad is already showing.")
      return
    }

    log("Will show ad.")
    AdsData.fullScreenAdIsShowing = true

    ad.fullScreenContentCallback = object : FullScreenContentCallback() {
      override fun onAdDismissedFullScreenContent() {
        log("Ad was dismissed.")
        cacheAds.interstitialAd = null
        AdsData.fullScreenAdIsShowing = false
        onListener(AdEvent.DISMISSED)
      }

      override fun onAdFailedToShowFullScreenContent(adError: AdError) {
        log("Ad failed to show.")
        cacheAds.interstitialAd = null
        AdsData.fullScreenAdIsShowing = false
        onListener(AdEvent.NOT_COMPLETED)
      }

      override fun onAdShowedFullScreenContent() {
        log("Ad showed fullscreen content.")
        // Called when ad is dismissed.
        onListener(AdEvent.COMPLETED)
      }

      override fun onAdClicked() {
        log("Ad clicked.")
        onListener(AdEvent.CLICKED)
      }

      override fun onAdImpression() {
        log("Ad shown - impression recognized.")
        onListener(AdEvent.IMPRESSION)
      }
    }
    ad.show(GlobalData.activity)
  }
}
