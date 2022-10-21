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

package dev.teogor.ceres.ads.binding

import androidx.databinding.BindingAdapter
import dev.teogor.ceres.ads.formats.AdBinder
import dev.teogor.ceres.ads.formats.NativeAd
import dev.teogor.ceres.ads.view.NativeAdView
import dev.teogor.ceres.binding.BindingAdapterClass

@BindingAdapterClass
class BindingMethods {
  companion object {
    @BindingAdapter("ad")
    @JvmStatic
    fun nativeAdWithAd(
      nativeAdView: NativeAdView,
      ad: NativeAd
    ) {
      nativeAdView.ad = ad
    }

    @BindingAdapter("binder")
    @JvmStatic
    fun nativeAdWithBinder(
      nativeAdView: NativeAdView,
      adBinder: AdBinder
    ) {
      nativeAdView.binder = adBinder
    }
  }
}
