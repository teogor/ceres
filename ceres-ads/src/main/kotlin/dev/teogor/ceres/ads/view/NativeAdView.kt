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

package dev.teogor.ceres.ads.view

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import dev.teogor.ceres.ads.AdEvent
import dev.teogor.ceres.ads.formats.AdBinder
import dev.teogor.ceres.ads.formats.NativeAd
import dev.teogor.ceres.core.logger.Logger
import dev.teogor.ceres.core.network.isConnected
import dev.teogor.ceres.core.network.justConnected
import dev.teogor.ceres.extensions.findViewTreeLifecycleOwner
import dev.teogor.ceres.extensions.isVisible
import dev.teogor.ceres.extensions.show

class NativeAdView(context: Context, attrs: AttributeSet) : FrameLayout(context, attrs), Logger {

  private lateinit var owner: LifecycleOwner
  private var initialized = false

  var ad: NativeAd? = null
    set(value) {
      field = value

      prepNativeAd()
    }

  var binder: AdBinder? = null
    set(value) {
      field = value

      prepNativeAd()
    }

  /**
   * Bind to ad events
   *
   * emits event([AdEvent])
   */
  val event = MutableLiveData<AdEvent>()

  private fun prepNativeAd() {
    if (!initializationCompleted()) {
      return
    }
    if (initialized) {
      return
    }
    initialized = true

    owner = findViewTreeLifecycleOwner()!!

    val ad = ad!!
    val binder = binder!!

    binder.setAdBinding(this)

    ad.binder = binder
    ad.event.observe(owner) {
      if (it == AdEvent.LOADED) {
        if (!addAd(ad)) {
          return@observe
        }
      } else if (it == AdEvent.FAILED_TO_LOAD) {
        isVisible = ad.bind(this)
      }
      event.value = it
    }
    ad.network.connection.observe(owner) {
      this.show(it.networkStatus.isConnected)
      if (it.networkStatus.justConnected) {
        ad.load()
      }
    }

    if (ad.loadContinuously()) {
      ad.apply {
        if (!addAd(this)) {
          load()
        }
      }
      ad.buildRefresh(
        owner = owner,
        refreshAction = {
          ad.load()
        }
      )
    } else {
      ad.load()
    }
  }

  private fun addAd(ad: NativeAd): Boolean {
    removeAllViews()
    return if (ad.bind(this)) {
      val adView = ad.binder.getAdView()
      addView(adView)
      true
    } else {
      false
    }
  }

  override fun onDetachedFromWindow() {
    super.onDetachedFromWindow()
    removeAllViews()
  }

  private fun initializationCompleted(): Boolean {
    return binder != null && ad != null
  }
}
