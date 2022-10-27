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

import android.text.SpannableString
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.VideoController
import com.google.android.gms.ads.VideoOptions
import com.google.android.gms.ads.nativead.MediaView
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdOptions
import com.google.android.gms.ads.nativead.NativeAdView
import dev.teogor.ceres.ads.Ad
import dev.teogor.ceres.ads.AdEvent
import dev.teogor.ceres.core.network.Network
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

abstract class NativeAd(
  val network: Network
) : Ad() {

  //region API
  open val maxNumberOfAds: Int = 1

  open val stopLoadingAdsAfter: Int = 3

  open val failedToLoadWaitTime: Long = TimeUnit.MINUTES.toMillis(30)

  open val refreshInterval: Long = TimeUnit.SECONDS.toMillis(45)

  @NativeAdOptions.AdChoicesPlacement
  open val adChoicesPlacement: Int = NativeAdOptions.ADCHOICES_TOP_RIGHT
  //endregion API

  private var mNativeAd: NativeAd? = null
  private var currentMillis = 0L
  private var failedToLoad = 0
  private var failedToLoadMillis = 0L
  private var isFailedAdCopy = false

  lateinit var binder: AdBinder

  override fun load() {
    if (isLoading) {
      return
    }
    super.load()

    if (useCache() && cacheAds.nativeAd != null) {
      onListener(AdEvent.LOADED)
    } else {
      onListener(AdEvent.IS_LOADING)

      val builder = AdLoader.Builder(context, id)

      builder.forNativeAd { nativeAd ->
        // You must call destroy on old ads when you are done with them,
        // otherwise you will have a memory leak.
        mNativeAd?.destroy()
        mNativeAd = nativeAd
        if (useCache()) {
          cacheAds.nativeAd = nativeAd
        }
        onListener(AdEvent.LOADED)
      }

      val videoOptions = VideoOptions
        .Builder()
        .setStartMuted(true)
        .build()

      val adOptions = NativeAdOptions.Builder()
        .setVideoOptions(videoOptions)
        .setAdChoicesPlacement(adChoicesPlacement)
        .build()

      builder.withNativeAdOptions(adOptions)

      val adLoader = builder.withAdListener(
        object : AdListener() {
          override fun onAdFailedToLoad(loadAdError: LoadAdError) {
            failedToLoad++
            onListener(AdEvent.FAILED_TO_LOAD)
          }

          override fun onAdClicked() {
            onListener(AdEvent.CLICKED)
          }

          override fun onAdClosed() {
            onListener(AdEvent.DISMISSED)
          }

          override fun onAdImpression() {
            onListener(AdEvent.IMPRESSION)
          }

          override fun onAdOpened() {
          }
        }
      )
        .build()

      if (maxNumberOfAds == 1) {
        adLoader.loadAd(AdRequest.Builder().build())
      } else if (maxNumberOfAds > 1) {
        adLoader.loadAds(AdRequest.Builder().build(), maxNumberOfAds)
      }
    }
  }

  private fun populateAd(nativeAd: NativeAd) {
    val binder = binder
    val adView: NativeAdView = binder.getAdView()
    val adHeadlineView: TextView? = binder.getHeadlineView()
    if (nativeAd.headline != null) {
      val headlineString = SpannableString(nativeAd.headline)
      adHeadlineView?.text = headlineString
    }
    val adMediaView: MediaView? = binder.getMediaView()
    if (adMediaView != null) {
      if (nativeAd.mediaContent != null) {
        adMediaView.visibility = View.VISIBLE
        adMediaView.mediaContent = nativeAd.mediaContent!!
      } else {
        adMediaView.visibility = View.GONE
      }
    }
    val adBodyView: TextView? = binder.getBodyView()
    if (nativeAd.body == null) {
      adBodyView?.visibility = View.GONE
    } else {
      adBodyView?.visibility = View.VISIBLE
      adBodyView?.text = nativeAd.body
    }
    val adCallToActionView: View? = binder.getCallToActionView()
    if (adCallToActionView != null) {
      if (nativeAd.callToAction == null) {
        adCallToActionView.visibility = View.GONE
      } else {
        adCallToActionView.visibility = View.VISIBLE
        if (adCallToActionView is TextView) {
          adCallToActionView.text = nativeAd.callToAction
        }
      }
    }
    val adIconView: ImageView? = binder.getAppIconView()
    if (adIconView != null) {
      if (nativeAd.icon == null) {
        adIconView.visibility = View.GONE
      } else {
        adIconView.visibility = View.VISIBLE
        adIconView.setImageDrawable(
          nativeAd.icon!!.drawable
        )
      }
    }
    val adPriceView: TextView? = binder.getPriceView()
    if (adPriceView != null) {
      if (nativeAd.price == null) {
        adPriceView.visibility = View.GONE
      } else {
        adPriceView.visibility = View.VISIBLE
        adPriceView.text = nativeAd.price
      }
    }
    val adStoreView: TextView? = binder.getStoreView()
    if (adStoreView != null) {
      if (nativeAd.store == null) {
        adStoreView.visibility = View.GONE
      } else {
        adStoreView.visibility = View.VISIBLE
        adStoreView.text = nativeAd.store
      }
    }
    val adStarRatingView: RatingBar? = binder.getStarsRatingView()
    if (adStarRatingView != null) {
      if (nativeAd.starRating == null) {
        adStarRatingView.visibility = View.GONE
      } else {
        adStarRatingView.visibility = View.VISIBLE
        adStarRatingView.rating = nativeAd.starRating!!.toFloat()
      }
    }
    val adAdvertiserView: TextView? = binder.getAdvertiserView()
    if (adAdvertiserView != null) {
      if (nativeAd.advertiser == null) {
        adAdvertiserView.visibility = View.GONE
      } else {
        adAdvertiserView.visibility = View.VISIBLE
        adAdvertiserView.text = nativeAd.advertiser
      }
    }

    // Bind the UI elements to the ad
    adView.mediaView = binder.getMediaView()
    adView.headlineView = binder.getHeadlineView()
    adView.bodyView = binder.getBodyView()
    adView.callToActionView = binder.getCallToActionView()
    adView.iconView = binder.getAppIconView()
    adView.priceView = binder.getPriceView()
    adView.starRatingView = binder.getStarsRatingView()
    adView.storeView = binder.getStoreView()
    adView.advertiserView = binder.getAdvertiserView()

    // This method tells the Google Mobile Ads SDK that you have finished populating your
    // native ad view with this native ad.
    adView.setNativeAd(nativeAd)

    // Get the video controller for the ad. One will always be provided, even if the ad doesn't
    // have a video asset.
    val vc = nativeAd.mediaContent?.videoController

    // Updates the UI to say whether or not this ad has a video asset.
    if (vc != null && vc.hasVideoContent()) {
      // Create a new VideoLifecycleCallbacks object and pass it to the VideoController. The
      // VideoController will call methods on this object when events occur in the video
      // lifecycle.
      vc.videoLifecycleCallbacks = object : VideoController.VideoLifecycleCallbacks() {
        override fun onVideoEnd() {
          super.onVideoEnd()
        }

        override fun onVideoMute(p0: Boolean) {
          super.onVideoMute(p0)
        }

        override fun onVideoPause() {
          super.onVideoPause()
        }

        override fun onVideoPlay() {
          super.onVideoPlay()
        }

        override fun onVideoStart() {
          super.onVideoStart()
        }
      }
    }
  }

  open fun bind(adFrame: FrameLayout): Boolean {
    if (!canShow()) {
      return false
    }
    val ad = if (useCache() && cacheAds.nativeAd != null) {
      cacheAds.nativeAd
    } else {
      mNativeAd
    }
    if (ad == null) {
      return false
    }
    populateAd(ad)
    return true
  }

  /**
   * todo Look into `lifecycleScope`
   *
   * [link](https://developer.android.com/topic/libraries/architecture/coroutines)
   */
  fun buildRefresh(
    owner: LifecycleOwner,
    refreshAction: () -> Unit
  ) = owner.lifecycleScope.launch {
    owner.repeatOnLifecycle(Lifecycle.State.STARTED) {
      while (true) {
        isFailedAdCopy = isFailedAd()
        var currentMillisS = if (isFailedAdCopy) {
          if (failedToLoadMillis == 0L) {
            failedToLoadMillis = failedToLoadWaitTime
          }
          failedToLoadMillis
        } else {
          if (currentMillis == 0L) {
            currentMillis = refreshInterval
          }
          currentMillis
        }
        while (currentMillisS > 0) {
          currentMillisS -= 100
          if (isFailedAdCopy) {
            failedToLoadMillis -= 100
          } else {
            currentMillis -= 100
          }
          delay(100)
        }
        if (isFailedAdCopy) {
          failedToLoadMillis = 0L
          failedToLoad = 0
          refreshAction()
        } else {
          currentMillis = 0L
          refreshAction()
        }
      }
    }
  }

  fun isFailedAd(): Boolean = failedToLoad >= stopLoadingAdsAfter
}
