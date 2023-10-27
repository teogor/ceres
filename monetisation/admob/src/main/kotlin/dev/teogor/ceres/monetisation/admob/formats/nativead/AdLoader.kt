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

package dev.teogor.ceres.monetisation.admob.formats.nativead

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.nativead.NativeAd
import dev.teogor.ceres.monetisation.admob.formats.AdEvent

@Composable
fun rememberAdLoader(
  config: AdLoaderConfig,
  onAdEvent: (AdEvent) -> Unit = {},
  onNativeAd: (NativeAd) -> Unit,
): AdLoader {
  val context = LocalContext.current
  return remember(config.adId) {
    val adLoader = AdLoader.Builder(context, config.adId)
      .forNativeAd { nativeAd ->
        onNativeAd(nativeAd)
      }
      .withAdListener(object : AdListener() {
        override fun onAdClicked() {
          // Handle ad clicked
          onAdEvent(AdEvent.AdClicked)
        }

        override fun onAdClosed() {
          // Handle ad closed
          onAdEvent(AdEvent.AdClosed)
        }

        override fun onAdFailedToLoad(error: LoadAdError) {
          // Handle ad failed to load
          onAdEvent(AdEvent.AdFailedToLoad(error))
        }

        override fun onAdImpression() {
          // Handle ad impression
          onAdEvent(AdEvent.AdImpression)
        }

        override fun onAdLoaded() {
          // Handle ad loaded
          onAdEvent(AdEvent.AdLoaded)
        }

        override fun onAdOpened() {
          // Handle ad opened
          onAdEvent(AdEvent.AdOpened)
        }

        override fun onAdSwipeGestureClicked() {
          // Handle swipe gesture clicked
          onAdEvent(AdEvent.AdSwipeGestureClicked)
        }
      })
      .withNativeAdOptions(config.toNativeAdOptions())
      .build()

    adLoader
  }
}
