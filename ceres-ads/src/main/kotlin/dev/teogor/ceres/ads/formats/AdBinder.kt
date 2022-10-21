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

package dev.teogor.ceres.ads.formats

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import com.google.android.gms.ads.nativead.MediaView
import com.google.android.gms.ads.nativead.NativeAdView
import dev.teogor.ceres.ads.AdView
import dev.teogor.ceres.core.global.GlobalData

abstract class AdBinder {

  open fun setAdBinding(parent: ViewGroup) {
    // empty
  }

  open fun getLayoutInflater(): LayoutInflater {
    val currentActivity = GlobalData.activity
    val rootView = currentActivity.window.decorView.rootView
    return LayoutInflater.from(rootView.context)
  }

  abstract fun getAdView(): NativeAdView

  open fun getMediaView(): MediaView? {
    return null
  }

  open fun getHeadlineView(): TextView? {
    return null
  }

  open fun getBodyView(): TextView? {
    return null
  }

  open fun getCallToActionView(): View? {
    return null
  }

  open fun getAppIconView(): ImageView? {
    return null
  }

  open fun getPriceView(): TextView? {
    return null
  }

  open fun getStoreView(): TextView? {
    return null
  }

  open fun getStarsRatingView(): RatingBar? {
    return null
  }

  open fun getAdvertiserView(): TextView? {
    return null
  }

  open fun getAdInfoView(): AdView? {
    return null
  }

  open fun getAdClickIcon(): View? {
    return null
  }

  open fun getAdClickText(): TextView? {
    return null
  }

  open fun getAdClickView(): View? {
    return null
  }
}
