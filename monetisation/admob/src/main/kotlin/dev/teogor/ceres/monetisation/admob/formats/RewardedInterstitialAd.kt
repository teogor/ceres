/*
 * Copyright 2022 teogor (Teodor Grigor)
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

package dev.teogor.ceres.monetisation.admob.formats

import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAd
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAdLoadCallback
import dev.teogor.ceres.monetisation.admob.CurrentActivityHolder
import java.util.Date

abstract class RewardedInterstitialAd(
  loadAtInitialisation: Boolean = false,
) : Ad(loadAtInitialisation) {

  final override fun type() = AdType.RewardedInterstitial

  override fun useCache() = false

  override fun load(): Boolean {
    if (!super.load()) {
      return false
    }

    onListener(AdEvent.AdIsLoading)

    val adRequest = AdRequest.Builder().build()
    RewardedInterstitialAd.load(
      context,
      id,
      adRequest,
      object : RewardedInterstitialAdLoadCallback() {
        override fun onAdFailedToLoad(adError: LoadAdError) {
          log(adError.message)
          val error =
            "domain: ${adError.domain}, code: ${adError.code}, " + "message: ${adError.message}"
          log("Ad failed to load: $error")
          onListener(AdEvent.AdFailedToLoad(adError))
        }

        override fun onAdLoaded(ad: RewardedInterstitialAd) {
          log("Ad was loaded.")
          AdCache.cacheAd(
            adId = id,
            ad = CachedAd.RewardedInterstitial(ad, Date().time),
          )
          onListener(AdEvent.AdLoaded)
        }
      },
    )

    return true
  }

  override fun show() {
    super.show()

    if (!canShow()) {
      return
    }
    if (!CurrentActivityHolder.canShowFullScreenAd) {
      log("Another ad is showing.")
      return
    }
    val ad = AdCache.getAd(id)
    if (ad == null) {
      log("ad was not loaded. requesting to load.")
      load()
      return
    }
    val rewardedInterstitialAd = (ad as CachedAd.RewardedInterstitial).ad
    if (isShowing) {
      log("The rewarded interstitial ad is already showing.")
      return
    }

    log("Will show ad.")

    rewardedInterstitialAd.fullScreenContentCallback = object : FullScreenContentCallback() {
      override fun onAdDismissedFullScreenContent() {
        log("Ad was dismissed.")
        AdCache.removeAd(id)
        onListener(AdEvent.AdClosed)
      }

      override fun onAdFailedToShowFullScreenContent(adError: AdError) {
        log("Ad failed to show.")
        AdCache.removeAd(id)
        // cacheAds.interstitialAd = null
      }

      override fun onAdShowedFullScreenContent() {
        log("Ad showed fullscreen content.")
        // Called when ad is dismissed.
        // onListener(AdEvent.COMPLETED)
      }

      override fun onAdClicked() {
        log("Ad clicked.")
        onListener(AdEvent.AdClicked)
      }

      override fun onAdImpression() {
        log("Ad shown - impression recognized.")
        onListener(AdEvent.AdImpression)
      }
    }
    CurrentActivityHolder.activity?.let {
      rewardedInterstitialAd.show(
        it,
      ) { reward ->
        onListener(AdEvent.OnUserEarnedReward(reward.toRewardItem()))
      }
    }
  }
}
