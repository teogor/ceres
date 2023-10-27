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
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import dev.teogor.ceres.monetisation.admob.CurrentActivityHolder
import java.util.Date

abstract class RewardedAd(
  loadAtInitialisation: Boolean = false,
) : Ad(loadAtInitialisation) {

  final override fun type() = AdType.Rewarded

  override fun useCache() = false

  override fun load(): Boolean {
    if (!super.load()) {
      return false
    }

    onListener(AdEvent.AdIsLoading)

    val adRequest = AdRequest.Builder().build()
    RewardedAd.load(
      context,
      id,
      adRequest,
      object : RewardedAdLoadCallback() {
        override fun onAdFailedToLoad(adError: LoadAdError) {
          log(adError.message)
          val error =
            "domain: ${adError.domain}, code: ${adError.code}, " + "message: ${adError.message}"
          log("Ad failed to load: $error")
          onListener(AdEvent.AdFailedToLoad(adError))
        }

        override fun onAdLoaded(ad: RewardedAd) {
          log("Ad was loaded.")
          AdCache.cacheAd(
            adId = id,
            ad = CachedAd.Rewarded(ad, Date().time),
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
    val rewardedAd = (ad as CachedAd.Rewarded).ad
    if (isShowing) {
      log("The rewarded interstitial ad is already showing.")
      return
    }

    log("Will show ad.")

    rewardedAd.fullScreenContentCallback = object : FullScreenContentCallback() {
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
      rewardedAd.show(
        it,
      ) { reward ->
        onListener(AdEvent.OnUserEarnedReward(reward.toRewardItem()))
      }
    }
  }
}
