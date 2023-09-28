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

package dev.teogor.ceres.monetisation.admob

import android.app.Activity
import com.google.android.gms.ads.AdActivity
import java.lang.ref.WeakReference

internal object CurrentActivityHolder {
  private var weakActivity: WeakReference<Activity>? = null

  var activity: Activity?
    get() = weakActivity?.get()
    set(value) {
      weakActivity = if (value != null) {
        WeakReference(value)
      } else {
        null
      }
    }

  var previousActivityWasAd = false

  val canShowFullScreenAd: Boolean
    get() = activity !is AdActivity

  val isOffline: Boolean
    get() = activity !is AdActivity
}
