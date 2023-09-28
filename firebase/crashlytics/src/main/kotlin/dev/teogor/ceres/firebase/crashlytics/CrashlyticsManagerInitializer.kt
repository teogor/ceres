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

package dev.teogor.ceres.firebase.crashlytics

import android.content.Context
import androidx.startup.Initializer
import com.google.firebase.crashlytics.FirebaseCrashlytics

/**
 * Initializes [FirebaseCrashlytics] using `androidx.startup`.
 */
class CrashlyticsManagerInitializer : Initializer<Unit> {
  override fun create(context: Context) {
    // val firebaseCrashlytics = Firebase.crashlytics
    // val ceresPreferences = ceresPreferences(context)
    // // Get the user ID value
    // ceresPreferences.launch {
    //   val userId = ceresPreferences.userId
    //   firebaseCrashlytics.setUserId(userId.value)
    //   firebaseCrashlytics.setCrashlyticsCollectionEnabled(true)
    //
    CrashlyticsManager(context)
    // }
  }

  override fun dependencies(): List<Class<out Initializer<*>?>> {
    return emptyList()
  }
}
