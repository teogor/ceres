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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidViewBinding
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdView
import dev.teogor.ceres.monetisation.admob.databinding.AdmobNativeBinding
import dev.teogor.ceres.monetisation.admob.formats.AdEvent

@Composable
fun NativeAd(
  modifier: Modifier = Modifier,
  nativeAdConfig: NativeAdConfig,
  adContent: @Composable () -> Unit,
  nativeAd: NativeAd?,
  config: AdLoaderConfig = AdLoaderConfig(""),
  refreshIntervalMillis: Long = 30000L,
  onAdEvent: (AdEvent) -> Unit = {},
  onAdLoaded: (NativeAd) -> Unit = {},
) {
  var adView by remember {
    mutableStateOf<NativeAdView?>(null)
  }

  val adLoader = rememberAdLoader(
    config = config,
    onAdEvent = onAdEvent,
    onNativeAd = onAdLoaded,
  )

  RefreshableNativeAd(
    adId = config.adId,
    adLoader = adLoader,
    refreshIntervalMillis = refreshIntervalMillis,
  )

  AndroidViewBinding(
    factory = AdmobNativeBinding::inflate,
    modifier = modifier,
  ) {
    if (adView == null) {
      adView = root.also { adview ->
        adview.bodyView = nativeAdConfig.bodyView?.composeView
        adview.advertiserView = nativeAdConfig.advertiserView?.composeView
        // adview.adChoicesView = nativeAdConfig.adChoicesView?.composeView
        adview.headlineView = nativeAdConfig.headlineView?.composeView
        adview.callToActionView = nativeAdConfig.callToActionView?.composeView
        adview.iconView = nativeAdConfig.iconView?.composeView
        adview.imageView = nativeAdConfig.imageView?.composeView
        // adview.mediaView = nativeAdConfig.mediaView?.composeView
        adview.priceView = nativeAdConfig.priceView?.composeView
        adview.starRatingView = nativeAdConfig.starRatingView?.composeView
        adview.storeView = nativeAdConfig.storeView?.composeView

        adview.adChoicesView
      }
      composeView.setContent(adContent)
    }
  }

  LaunchedEffect(nativeAd) {
    nativeAd?.let { nativeAd ->
      nativeAd.body?.let { body ->
        nativeAdConfig.bodyView?.setValue(body)
      }
      nativeAd.advertiser?.let { advertiser ->
        nativeAdConfig.advertiserView?.setValue(advertiser)
      }
      nativeAd.adChoicesInfo?.let { adChoices ->
        nativeAdConfig.adChoicesView?.setValue(adChoices)
      }
      nativeAd.headline?.let { headline ->
        nativeAdConfig.headlineView?.setValue(headline)
      }
      nativeAd.callToAction?.let { callToAction ->
        nativeAdConfig.callToActionView?.setValue(callToAction)
      }
      nativeAd.icon?.let { icon ->
        nativeAdConfig.iconView?.setValue(icon)
      }
      nativeAd.images.let { image ->
        nativeAdConfig.imageView?.setValue(image)
      }
      nativeAd.mediaContent?.let { media ->
        nativeAdConfig.mediaView?.setValue(media)
      }
      nativeAd.price?.let { price ->
        nativeAdConfig.priceView?.setValue(price)
      }
      nativeAd.starRating?.let { starRating ->
        nativeAdConfig.starRatingView?.setValue(starRating)
      }
      nativeAd.store?.let { store ->
        nativeAdConfig.storeView?.setValue(store)
      }
      adView?.setNativeAd(nativeAd)
    }
  }
}
