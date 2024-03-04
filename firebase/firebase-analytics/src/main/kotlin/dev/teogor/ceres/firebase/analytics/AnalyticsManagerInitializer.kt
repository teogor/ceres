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

import android.content.Context
import androidx.startup.Initializer
import com.google.firebase.analytics.FirebaseAnalytics
import dev.teogor.ceres.core.analytics.ExperimentalAnalyticsApi
import dev.teogor.ceres.core.analytics.analyticsProvider
import dev.teogor.ceres.core.analytics.plusAssign
import dev.teogor.ceres.data.datastore.common.launch
import dev.teogor.ceres.data.datastore.defaults.ceresPreferences

/**
 * Initializes [FirebaseAnalytics] using `androidx.startup`.
 */
class AnalyticsManagerInitializer : Initializer<Unit> {
  @OptIn(ExperimentalAnalyticsApi::class)
  override fun create(context: Context) {
    val firebaseAnalytics = FirebaseAnalytics.getInstance(context)
    analyticsProvider += FirebaseAnalyticsHelper(firebaseAnalytics)
    // todo handle this into base activity or via a provider
    //  because this way we can not provide a custom userId
    val ceresPreferences = ceresPreferences(context)
    // Get the user ID value
    ceresPreferences.launch {
      val userId = ceresPreferences.userId
      firebaseAnalytics.setUserId(userId.value)
    }
  }

  override fun dependencies(): List<Class<out Initializer<*>?>> {
    return emptyList()
  }
}

fun text() {
  FirebaseAnalytics.Param.SCREEN_CLASS
}
