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

package dev.teogor.ceres.firebase.analytics

import android.os.Bundle
import androidx.annotation.Size
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.FirebaseAnalytics.UserProperty
import com.zeoflow.startup.ktx.ApplicationInitializer
import dev.teogor.ceres.firebase.FirebaseMemo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Analytics @Inject constructor(
  private val firebaseMemo: FirebaseMemo
) {

  private var mFirebaseAnalytics: FirebaseAnalytics = FirebaseAnalytics.getInstance(
    ApplicationInitializer.context
  )

  fun initialize() {
    mFirebaseAnalytics.setUserId(firebaseMemo.userID.value)
  }

  fun refreshUserId() {
    mFirebaseAnalytics.setUserId(firebaseMemo.userID.value)
  }

  fun enableAnalytics(enabled: Boolean) {
    mFirebaseAnalytics.setAnalyticsCollectionEnabled(enabled)
  }

  fun allowAdPersonalization(enable: Boolean) {
    mFirebaseAnalytics.setUserProperty(
      UserProperty.ALLOW_AD_PERSONALIZATION_SIGNALS,
      enable.toString()
    )
  }

  fun resetAnalyticsData() {
    mFirebaseAnalytics.resetAnalyticsData()
  }

  fun logEvent(@Size(min = 1L, max = 40L) name: String, params: Bundle) {
    mFirebaseAnalytics.logEvent(name, params)
  }
}
