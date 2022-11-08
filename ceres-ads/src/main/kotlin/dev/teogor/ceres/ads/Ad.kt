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

package dev.teogor.ceres.ads

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.zeoflow.startup.ktx.ApplicationInitializer
import dev.teogor.ceres.ads.cache.CacheAds
import dev.teogor.ceres.ads.utils.Constants
import dev.teogor.ceres.ads.utils.isNetworkAvailable
import dev.teogor.ceres.core.logger.Logger

abstract class Ad : AdId(), Logger {

  abstract fun loadContinuously(): Boolean

  abstract fun useCache(): Boolean

  protected val cacheAds = CacheAds()

  protected var isLoading = false
  protected var isShowing = false

  val event = MutableLiveData<AdEvent>()

  val context: Context = ApplicationInitializer.context

  open fun load(): Boolean {
    if (isLoading) {
      return false
    }
    isLoading = true
    return true
  }

  open fun show() {
  }

  fun onListener(event: AdEvent) {
    Log.d(Constants.LOG_TAG, "onListener::$event")
    when (event) {
      AdEvent.FAILED_TO_LOAD -> {
        isLoading = false
      }
      AdEvent.LOADED -> {
        isLoading = false
      }
      AdEvent.IMPRESSION -> {
        isShowing = true
      }
      AdEvent.CLICKED -> {
      }
      AdEvent.DISMISSED -> {
        isShowing = false
        if (loadContinuously()) {
          load()
        }
      }
      AdEvent.COMPLETED -> {
      }
      AdEvent.NOT_COMPLETED -> {
        isShowing = false
        if (loadContinuously()) {
          load()
        }
      }
      AdEvent.IS_LOADING -> {
        isLoading = true
      }
    }
    this@Ad.event.postValue(event)
  }

  fun canShow(): Boolean {
    return isNetworkAvailable()
  }

  fun isReady(): Boolean {
    return canShow() && event.value == AdEvent.LOADED
  }
}
