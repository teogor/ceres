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

import androidx.lifecycle.ViewModel
import com.google.android.gms.ads.nativead.NativeAd
import dev.teogor.ceres.monetisation.admob.annotations.AdField

abstract class NativeAdViewModel(
  private val nativeAdData: NativeAdData,
) : ViewModel() {
  @AdField
  val nativeAd = nativeAdData.nativeAdData

  @AdField
  fun setNativeAd(ad: NativeAd) {
    nativeAdData.setNativeAd(ad)
  }
}
