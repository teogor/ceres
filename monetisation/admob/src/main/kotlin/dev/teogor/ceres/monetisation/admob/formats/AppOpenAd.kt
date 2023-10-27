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
import com.google.android.gms.ads.appopen.AppOpenAd
import dev.teogor.ceres.monetisation.admob.CurrentActivityHolder
import java.util.Date
import java.util.concurrent.TimeUnit

abstract class AppOpenAd : Ad() {

  final override fun type() = AdType.AppOpen

  override fun useCache() = false

  override fun load(): Boolean {
    if (!super.load()) {
      return false
    }

    onListener(AdEvent.AdIsLoading)

    val request = AdRequest.Builder().build()
    AppOpenAd.load(
      context,
      id,
      request,
      object : AppOpenAd.AppOpenAdLoadCallback() {
        /**
         * Called when an app open ad has loaded.
         *
         * @param ad the loaded app open ad.
         */
        override fun onAdLoaded(ad: AppOpenAd) {
          log("onAdLoaded.")
          AdCache.cacheAd(
            adId = id,
            ad = CachedAd.AppOpen(ad, Date().time),
          )
          onListener(AdEvent.AdLoaded)
        }

        /**
         * Called when an app open ad has failed to load.
         *
         * @param adError the error.
         */
        override fun onAdFailedToLoad(adError: LoadAdError) {
          val error =
            "domain: ${adError.domain}, code: ${adError.code}, " + "message: ${adError.message}"
          log("Ad failed to load: $error")
          onListener(AdEvent.AdFailedToLoad(adError))
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

    val appOpenAd = (ad as CachedAd.AppOpen).ad

    if (!wasLoadTimeLessThanNHoursAgo(ad.loadTime, 4)) {
      reloadExpiredAd()
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

    appOpenAd.fullScreenContentCallback = object : FullScreenContentCallback() {
      override fun onAdClicked() {
        log("Ad clicked.")
        onListener(AdEvent.AdClicked)
      }

      override fun onAdImpression() {
        log("Ad shown - impression recognized.")
        onListener(AdEvent.AdImpression)
      }

      /** Called when full screen content is dismissed. */
      override fun onAdDismissedFullScreenContent() {
        // Set the reference to null so isAdAvailable() returns false.
        log("Ad was dismissed.")
        AdCache.removeAd(id)
        onListener(AdEvent.AdClosed)
      }

      /** Called when fullscreen content failed to show. */
      override fun onAdFailedToShowFullScreenContent(adError: AdError) {
        log("Ad failed to show.")
        AdCache.removeAd(id)
        load()
        // onListener(AdEvent.NOT_COMPLETED)
      }

      /** Called when fullscreen content is shown. */
      override fun onAdShowedFullScreenContent() {
        log("Ad showed fullscreen content.")
        // Called when ad is dismissed.
        // onListener(AdEvent.COAdMPLETED)
      }
    }
    appOpenAd.setImmersiveMode(true)
    CurrentActivityHolder.activity?.let { appOpenAd.show(it) }
  }

  private fun wasLoadTimeLessThanNHoursAgo(
    adLoadTime: Long,
    hoursThreshold: Long,
  ): Boolean {
    val dateDifference: Long = Date().time - adLoadTime
    val numMillisecondsPerHour: Long = TimeUnit.HOURS.toMillis(1)
    return dateDifference < numMillisecondsPerHour * hoursThreshold
  }
}
