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

import android.util.Log
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import dev.teogor.ceres.ads.Ad
import dev.teogor.ceres.ads.AdEvent
import dev.teogor.ceres.ads.utils.Constants.LOG_TAG
import dev.teogor.ceres.ads.utils.isAdActivity
import dev.teogor.ceres.core.global.GlobalData
import dev.teogor.ceres.core.network.Network

abstract class InterstitialAd(
  network: Network? = null
) : Ad() {

  private var mInterstitialAd: InterstitialAd? = null

  override fun load() {
    super.load()

    if (useCache() && cacheAds.interstitialAd != null) {
      onListener(AdEvent.LOADED)
    } else {
      onListener(AdEvent.IS_LOADING)

      val adRequest = AdRequest.Builder().build()
      InterstitialAd.load(
        context,
        id,
        adRequest,
        object : InterstitialAdLoadCallback() {
          override fun onAdFailedToLoad(adError: LoadAdError) {
            Log.d(LOG_TAG, adError.message)
            mInterstitialAd = null
            val error =
              "domain: ${adError.domain}, code: ${adError.code}, " + "message: ${adError.message}"
            Log.d(LOG_TAG, "Ad failed to load: $error")
            onListener(AdEvent.FAILED_TO_LOAD)
          }

          override fun onAdLoaded(interstitialAd: InterstitialAd) {
            Log.d(LOG_TAG, "Ad was loaded.")
            mInterstitialAd = interstitialAd
            onListener(AdEvent.LOADED)
          }
        }
      )
    }
  }

  override fun show() {
    super.show()

    if (!canShow()) {
      return
    }
    if (GlobalData.activity.isAdActivity()) {
      Log.d(LOG_TAG, "Another ad is showing.")
      return
    }
    val ad = if (useCache() && cacheAds.interstitialAd != null) {
      cacheAds.interstitialAd
    } else {
      mInterstitialAd
    }
    if (isShowing) {
      Log.d(LOG_TAG, "The interstitial ad is already showing.")
      return
    }
    ad?.fullScreenContentCallback =
      object : FullScreenContentCallback() {
        override fun onAdDismissedFullScreenContent() {
          Log.d(LOG_TAG, "Ad was dismissed.")
          if (useCache()) {
            cacheAds.interstitialAd = ad
          }
          mInterstitialAd = null
          onListener(AdEvent.DISMISSED)
        }

        override fun onAdFailedToShowFullScreenContent(adError: AdError) {
          Log.d(LOG_TAG, "Ad failed to show.")
          if (useCache()) {
            cacheAds.interstitialAd = ad
          }
          mInterstitialAd = null
          onListener(AdEvent.NOT_COMPLETED)
        }

        override fun onAdShowedFullScreenContent() {
          Log.d(LOG_TAG, "Ad showed fullscreen content.")
          // Called when ad is dismissed.
          onListener(AdEvent.COMPLETED)
        }

        override fun onAdClicked() {
          Log.d(LOG_TAG, "Ad clicked.")
          onListener(AdEvent.CLICKED)
        }

        override fun onAdImpression() {
          Log.d(LOG_TAG, "Ad shown - impression recognized.")
          onListener(AdEvent.IMPRESSION)
        }
      }
    ad?.show(GlobalData.activity)
  }
}
