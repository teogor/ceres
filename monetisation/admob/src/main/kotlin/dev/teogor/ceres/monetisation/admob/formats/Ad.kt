/*
 * Copyright 2022 teogor (Teodor Grigor)
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

package dev.teogor.ceres.monetisation.admob.formats

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import dev.teogor.ceres.core.foundation.FoundationGlobal
import dev.teogor.ceres.core.startup.ApplicationContextProvider
import dev.teogor.ceres.monetisation.admob.Logger
import dev.teogor.ceres.monetisation.ads.AdsControlProvider

abstract class Ad(
  loadAtInitialisation: Boolean = false,
) : AdId(), Logger {
  open val concurrentAdLoadLimit = 1

  abstract fun loadContinuously(): Boolean

  abstract fun useCache(): Boolean

  open fun isNetworkAvailable(): Boolean {
    return !FoundationGlobal.networkMonitor.isOffline
  }

  open fun canRequestAds(): Boolean {
    return AdsControlProvider.canRequestAds()
  }

  protected var isLoading = false
  protected var isShowing = false

  val adEvent = mutableStateOf<AdEvent>(AdEvent.Unspecified)

  val context: Context = ApplicationContextProvider.context

  private val loadedAdCount: Int
    get() = AdCache.getAdCount(id)

  open fun load(): Boolean {
    log("Ad is loading ?: $isLoading")
    if (isLoading) {
      return false
    }
    if (loadedAdCount >= concurrentAdLoadLimit) {
      log(
        "Can not load more ads (loaded ads $loadedAdCount/${AdCache.getAdCount(
          id,
        )}). The limit of loaded ads is set to $concurrentAdLoadLimit.",
      )
      return false
    }
    isLoading = true
    return true
  }

  init {
    if (loadAtInitialisation) {
      load()
    }
  }

  open fun show() = Unit

  open fun reloadExpiredAd() {
    val adTypeName = type().toFriendlyName(suffix = " Ad")
    log("Loading a new $adTypeName to replace the expired ad.")
    AdCache.removeAd(id)

    load()
  }

  fun onListener(event: AdEvent) {
    log("onListener::$event")
    when (event) {
      is AdEvent.AdClicked -> {
        // Handle ad clicked event
      }

      is AdEvent.AdClosed -> {
        // Handle ad closed event
        isShowing = false
        if (loadContinuously()) {
          load()
        }
      }

      is AdEvent.AdFailedToLoad -> {
        // Handle ad failed to load event
        isLoading = false
        // loadedAdCount -= 1
        // if (loadedAdCount < 0) {
        //   loadedAdCount = 0
        // }
      }

      is AdEvent.AdImpression -> {
        // Handle ad impression event
        isShowing = true
        // loadedAdCount -= 1
        // if (loadedAdCount < 0) {
        //   loadedAdCount = 0
        // }
      }

      is AdEvent.AdIsLoading -> {
        // Handle ad impression event
      }

      is AdEvent.AdLoaded -> {
        // Handle ad loaded event
        isLoading = false
        // loadedAdCount += 1
        load()
      }

      is AdEvent.AdOpened -> {
        // Handle ad opened event
      }

      is AdEvent.AdSwipeGestureClicked -> {
        // Handle swipe gesture clicked event
      }

      is AdEvent.Unspecified -> {
        // Handle unspecified event
      }

      else -> {
        // Handle event
      }
    }
    adEvent.value = event
  }

  fun canShow() = isNetworkAvailable() && canRequestAds()

  fun isAdLoaded() = adEvent.value == AdEvent.AdLoaded

  fun isReady() = canShow() && isAdLoaded()
}
