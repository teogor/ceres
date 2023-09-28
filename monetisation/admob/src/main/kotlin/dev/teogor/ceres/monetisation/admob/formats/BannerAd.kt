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

import android.content.Context
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.LoadAdError
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

abstract class BannerAd(
  loadAtInitialisation: Boolean = false,
) : Ad(loadAtInitialisation) {

  final override fun type() = AdType.Banner

  override fun useCache() = false

  open val adSize: AdSize = AdSize.BANNER

  private var adView: AdView? = null

  fun buildAdView(context: Context) = AdView(context).apply {
    setAdSize(this@BannerAd.adSize)
    adUnitId = this@BannerAd.id

    adListener = getBannerAdListener()

    // calling load ad to load our ad.
    loadAd(AdRequest.Builder().build())

    adView = this
  }

  private fun getBannerAdListener() = object : AdListener() {
    override fun onAdClicked() {
      // Code to be executed when the user clicks on an ad.
      log("Ad clicked.")
      onListener(AdEvent.AdClicked)
    }

    override fun onAdClosed() {
      // Code to be executed when the user is about to return
      // to the app after tapping on an ad.
      log("Ad was closed.")
      onListener(AdEvent.AdClosed)
    }

    override fun onAdFailedToLoad(adError: LoadAdError) {
      // Code to be executed when an ad request fails.
      log(adError.message)
      val error =
        "domain: ${adError.domain}, code: ${adError.code}, " + "message: ${adError.message}"
      log("Ad failed to load: $error")
      onListener(AdEvent.AdFailedToLoad(adError))
    }

    override fun onAdImpression() {
      // Code to be executed when an impression is recorded
      // for an ad.
      log("Ad shown - impression recognized.")
      // onListener(AdEvent.AdImpression)
    }

    override fun onAdLoaded() {
      // Code to be executed when an ad finishes loading.
      log("Ad was loaded.")
      onListener(AdEvent.AdLoaded)
    }

    override fun onAdOpened() {
      // Code to be executed when an ad opens an overlay that
      // covers the screen.
    }
  }

  @OptIn(DelicateCoroutinesApi::class)
  override fun load(): Boolean {
    if (!super.load()) {
      return false
    }

    if (adView == null) {
      return false
    }

    GlobalScope.launch(Dispatchers.Main) {
      delay(5000)

      onListener(AdEvent.AdIsLoading)

      val adRequest = AdRequest.Builder().build()

      adView?.loadAd(adRequest)
    }

    return true
  }
}
