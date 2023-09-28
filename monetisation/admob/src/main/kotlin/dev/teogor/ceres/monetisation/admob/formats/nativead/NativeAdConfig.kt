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

import com.google.android.gms.ads.MediaContent
import com.google.android.gms.ads.nativead.NativeAd

data class NativeAdConfig(
  val advertiserView: AdComponent<String>? = null,
  val adChoicesView: AdComponent<NativeAd.AdChoicesInfo>? = null,
  val headlineView: AdComponent<String>? = null,
  val bodyView: AdComponent<String>? = null,
  val callToActionView: AdComponent<String>? = null,
  val iconView: AdComponent<NativeAd.Image?>? = null,
  val imageView: AdComponent<List<NativeAd.Image>>? = null,
  val mediaView: AdComponent<MediaContent>? = null,
  val priceView: AdComponent<String>? = null,
  val starRatingView: AdComponent<Double>? = null,
  val storeView: AdComponent<String>? = null,
) {
  data class Builder(
    private var advertiserView: AdComponent<String>? = null,
    private var adChoicesView: AdComponent<NativeAd.AdChoicesInfo>? = null,
    private var headlineView: AdComponent<String>? = null,
    private var bodyView: AdComponent<String>? = null,
    private var callToActionView: AdComponent<String>? = null,
    private var iconView: AdComponent<NativeAd.Image?>? = null,
    private var imageView: AdComponent<List<NativeAd.Image>>? = null,
    private var mediaView: AdComponent<MediaContent>? = null,
    private var priceView: AdComponent<String>? = null,
    private var starRatingView: AdComponent<Double>? = null,
    private var storeView: AdComponent<String>? = null,
  ) {
    fun advertiserView(advertiserView: AdComponent<String>) =
      apply { this.advertiserView = advertiserView }

    fun adChoicesView(adChoicesView: AdComponent<NativeAd.AdChoicesInfo>) =
      apply { this.adChoicesView = adChoicesView }

    fun headlineView(headlineView: AdComponent<String>) = apply { this.headlineView = headlineView }
    fun bodyView(bodyView: AdComponent<String>) = apply { this.bodyView = bodyView }
    fun callToActionView(callToActionView: AdComponent<String>) =
      apply { this.callToActionView = callToActionView }

    fun iconView(iconView: AdComponent<NativeAd.Image?>) = apply { this.iconView = iconView }
    fun imageView(
      imageView: AdComponent<List<NativeAd.Image>>,
    ) = apply { this.imageView = imageView }
    fun mediaView(mediaView: AdComponent<MediaContent>) = apply { this.mediaView = mediaView }
    fun priceView(priceView: AdComponent<String>) = apply { this.priceView = priceView }
    fun starRatingView(starRatingView: AdComponent<Double>) =
      apply { this.starRatingView = starRatingView }

    fun storeView(storeView: AdComponent<String>) = apply { this.storeView = storeView }

    fun build() = NativeAdConfig(
      advertiserView = advertiserView,
      adChoicesView = adChoicesView,
      headlineView = headlineView,
      bodyView = bodyView,
      callToActionView = callToActionView,
      iconView = iconView,
      imageView = imageView,
      mediaView = mediaView,
      priceView = priceView,
      starRatingView = starRatingView,
      storeView = storeView,
    )
  }
}
