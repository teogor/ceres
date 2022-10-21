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

package dev.teogor.ceres.presentation.ads

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import com.google.android.gms.ads.nativead.NativeAdView
import dev.teogor.ceres.ads.annotation.NativeBinder
import dev.teogor.ceres.ads.formats.AdBinder
import dev.teogor.ceres.databinding.NativeAdHomeBinding
import javax.inject.Inject

@NativeBinder
class HomeNativeAdBinder @Inject constructor() : AdBinder() {

  private var adBinding: NativeAdHomeBinding = NativeAdHomeBinding.inflate(
    getLayoutInflater()
  )

  override fun setAdBinding(parent: ViewGroup) {
    super.setAdBinding(parent)

    adBinding = NativeAdHomeBinding.inflate(
      getLayoutInflater(),
      parent,
      false
    )
  }

  override fun getAdView(): NativeAdView {
    return adBinding.root
  }

  override fun getHeadlineView(): TextView {
    return adBinding.adHeadline
  }

  override fun getBodyView(): TextView {
    return adBinding.adBody
  }

  override fun getCallToActionView(): View {
    return adBinding.adCallToAction
  }

  override fun getAdClickIcon(): View {
    return adBinding.adAppIcon
  }

  override fun getAppIconView(): ImageView {
    return adBinding.adAppIcon
  }

  override fun getStarsRatingView(): RatingBar {
    return adBinding.adStars
  }

  override fun getAdvertiserView(): TextView {
    return adBinding.adAdvertiser
  }
}
