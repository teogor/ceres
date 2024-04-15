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

package dev.teogor.ceres.firebase.analytics

import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import dev.teogor.ceres.core.analytics.AnalyticsEvent
import dev.teogor.ceres.core.analytics.AnalyticsHelper
import dev.teogor.ceres.core.analytics.ExperimentalAnalyticsApi
import dev.teogor.ceres.core.analytics.Types
import dev.teogor.ceres.core.analytics.toLowercaseString

@ExperimentalAnalyticsApi
class FirebaseAnalyticsHelper(
  private val firebaseAnalytics: FirebaseAnalytics,
) : AnalyticsHelper {

  @ExperimentalAnalyticsApi
  override fun logEvent(analyticsEvent: AnalyticsEvent) {
    firebaseAnalytics.logEvent(
      analyticsEvent.type,
    ) {
      analyticsEvent.params.forEach { extra ->
        param(
          key = extra.key.toLowercaseString().take(40),
          value = extra.value.take(100),
        )
      }
    }
  }
}

inline fun FirebaseAnalytics.logEvent(
  type: Types,
  crossinline block: ParametersBuilder.() -> Unit,
) {
  logEvent(
    type.toLowercaseString(),
    ParametersBuilder().apply(block).build(),
  )
}

class ParametersBuilder {
  private val bundle = Bundle()

  fun param(key: String, value: String) {
    bundle.putString(key, value)
  }

  fun param(key: String, value: Double) {
    bundle.putDouble(key, value)
  }

  fun param(key: String, value: Long) {
    bundle.putLong(key, value)
  }

  fun param(key: String, value: Bundle) {
    bundle.putBundle(key, value)
  }

  fun param(key: String, value: Array<Bundle>) {
    bundle.putParcelableArray(key, value)
  }

  fun build(): Bundle {
    return bundle
  }
}
