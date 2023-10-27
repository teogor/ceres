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

import com.google.android.gms.ads.VideoOptions
import com.google.android.gms.ads.nativead.NativeAdOptions
import dev.teogor.ceres.monetisation.admob.models.AdChoicesPlacement
import dev.teogor.ceres.monetisation.admob.models.NativeMediaAspectRatio

class AdLoaderConfig internal constructor(
  val adId: String,
  val adChoicesPlacement: AdChoicesPlacement,
  val mediaAspectRatio: NativeMediaAspectRatio,
  val requestCustomMuteThisAd: Boolean,
  val requestMultipleImages: Boolean,
  val returnUrlsForImageAssets: Boolean,
  clickToExpandRequested: Boolean,
  customControlsRequested: Boolean,
  startMuted: Boolean,
) {
  val videoOptions = VideoOptions.Builder()
    .setClickToExpandRequested(clickToExpandRequested)
    .setCustomControlsRequested(customControlsRequested)
    .setStartMuted(startMuted)
    .build()

  // Nested builder class
  data class Builder(
    private var adId: String = "",
    private var adChoicesPlacement: AdChoicesPlacement = AdChoicesPlacement.TOP_RIGHT,
    private var mediaAspectRatio: NativeMediaAspectRatio = NativeMediaAspectRatio.PORTRAIT,
    private var requestCustomMuteThisAd: Boolean = false,
    private var requestMultipleImages: Boolean = false,
    private var returnUrlsForImageAssets: Boolean = false,
    private var clickToExpandRequested: Boolean = true,
    private var customControlsRequested: Boolean = false,
    private var startMuted: Boolean = false,
  ) {

    fun adId(adId: String) = apply {
      if (adId.isEmpty()) {
        throw IllegalArgumentException("`adId` cannot be empty.")
      }
      this.adId = adId
    }

    fun adChoicesPlacement(adChoicesPlacement: AdChoicesPlacement) = apply {
      this.adChoicesPlacement = adChoicesPlacement
    }

    fun mediaAspectRatio(mediaAspectRatio: NativeMediaAspectRatio) = apply {
      this.mediaAspectRatio = mediaAspectRatio
    }

    fun requestCustomMuteThisAd(requestCustomMuteThisAd: Boolean) = apply {
      this.requestCustomMuteThisAd = requestCustomMuteThisAd
    }

    fun requestMultipleImages(requestMultipleImages: Boolean) = apply {
      this.requestMultipleImages = requestMultipleImages
    }

    fun returnUrlsForImageAssets(returnUrlsForImageAssets: Boolean) = apply {
      this.returnUrlsForImageAssets = returnUrlsForImageAssets
    }

    fun clickToExpandRequested(clickToExpandRequested: Boolean) = apply {
      this.clickToExpandRequested = clickToExpandRequested
    }

    fun customControlsRequested(customControlsRequested: Boolean) = apply {
      this.customControlsRequested = customControlsRequested
    }

    fun startMuted(startMuted: Boolean) = apply {
      this.startMuted = startMuted
    }

    fun build(): AdLoaderConfig {
      return AdLoaderConfig(
        adId,
        adChoicesPlacement,
        mediaAspectRatio,
        requestCustomMuteThisAd,
        requestMultipleImages,
        returnUrlsForImageAssets,
        clickToExpandRequested,
        customControlsRequested,
        startMuted,
      )
    }
  }
}

fun AdLoaderConfig.toNativeAdOptions() = NativeAdOptions.Builder()
  .setAdChoicesPlacement(adChoicesPlacement)
  .setMediaAspectRatio(mediaAspectRatio)
  .setRequestCustomMuteThisAd(requestCustomMuteThisAd)
  .setRequestMultipleImages(requestMultipleImages)
  .setReturnUrlsForImageAssets(returnUrlsForImageAssets)
  .setVideoOptions(videoOptions)
  // TODO enableCustomClickGestureDirection
  //  .enableCustomClickGestureDirection()
  .build()

fun NativeAdOptions.Builder.setAdChoicesPlacement(
  adChoicesPlacement: AdChoicesPlacement,
): NativeAdOptions.Builder {
  return setAdChoicesPlacement(adChoicesPlacement.value)
}

fun NativeAdOptions.Builder.setMediaAspectRatio(
  mediaAspectRatio: NativeMediaAspectRatio,
): NativeAdOptions.Builder {
  return setMediaAspectRatio(mediaAspectRatio.value)
}

fun defaultAdLoaderConfig(
  adId: String,
) = AdLoaderConfig.Builder()
  .adId(adId)
  .build()
