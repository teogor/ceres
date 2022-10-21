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

package dev.teogor.ceres.firebase

import dev.teogor.ceres.core.construct.AppData
import dev.teogor.ceres.firebase.analytics.Analytics
import dev.teogor.ceres.firebase.app_check.AppCheck
import dev.teogor.ceres.firebase.crashlytics.Crashlytics
import dev.teogor.ceres.firebase.performance.Performance
import dev.teogor.ceres.firebase.remote_config.RemoteConfig
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Firebase @Inject constructor(
  private val firebaseMemo: FirebaseMemo,
  private val analytics: Analytics,
  private val crashlytics: Crashlytics,
  private val performance: Performance,
  private val remoteConfig: RemoteConfig,
  private val appCheck: AppCheck
) {

  fun setupModules() {
    if (!firebaseMemo.signatureChecked) {
      appCheck.validateSignature()
    }
    analytics.initialize()
    crashlytics.initialize()
    performance.initialize()
    remoteConfig.initialize()
  }

  fun enableModules() {
    analytics.enableAnalytics(true)
    if (AppData.IsDebuggable) {
      return
    }
    crashlytics.enableCrashlytics(true)
    performance.enablePerformance(true)
  }

  fun disableModules() {
    analytics.enableAnalytics(false)
    crashlytics.enableCrashlytics(false)
    performance.enablePerformance(false)
  }
}
