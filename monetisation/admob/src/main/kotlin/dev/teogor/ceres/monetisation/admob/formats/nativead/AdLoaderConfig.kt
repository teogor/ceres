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

data class AdLoaderConfig(
  val adId: String,
  val adChoicesPlacement: Int = NativeAdOptions.ADCHOICES_TOP_RIGHT,
  val mediaAspectRatio: Int = NativeAdOptions.NATIVE_MEDIA_ASPECT_RATIO_PORTRAIT,
  val requestCustomMuteThisAd: Boolean = false,
  val requestMultipleImages: Boolean = false,
  val returnUrlsForImageAssets: Boolean = false,
  val clickToExpandRequested: Boolean = true,
  val customControlsRequested: Boolean = false,
  val startMuted: Boolean = false,
) {
  val videoOptions = VideoOptions.Builder()
    .setClickToExpandRequested(clickToExpandRequested)
    .setCustomControlsRequested(customControlsRequested)
    .setStartMuted(startMuted)
    .build()
}

fun AdLoaderConfig.toNativeAdOptions() = NativeAdOptions.Builder()
  .setAdChoicesPlacement(adChoicesPlacement)
  .setMediaAspectRatio(mediaAspectRatio)
  .setRequestCustomMuteThisAd(requestCustomMuteThisAd)
  .setRequestMultipleImages(requestMultipleImages)
  .setReturnUrlsForImageAssets(returnUrlsForImageAssets)
  .setVideoOptions(videoOptions)
  .build()
