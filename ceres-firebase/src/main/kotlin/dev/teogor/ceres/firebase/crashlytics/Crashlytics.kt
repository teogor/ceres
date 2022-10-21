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

package dev.teogor.ceres.firebase.crashlytics

import com.google.firebase.crashlytics.FirebaseCrashlytics
import dev.teogor.ceres.core.construct.AppData
import dev.teogor.ceres.core.network.Network
import dev.teogor.ceres.core.network.NetworkQuality
import dev.teogor.ceres.core.network.NetworkStatus
import dev.teogor.ceres.core.util.AppUtils
import dev.teogor.ceres.firebase.FirebaseMemo
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Crashlytics @Inject constructor(
  private val firebaseMemo: FirebaseMemo,
  network: Network
) {

  private val mFirebaseCrashlytics = FirebaseCrashlytics.getInstance()

  init {
    network.connection.observeForever {
      setConnection(it.networkStatus)
      setConnectionQuality(it.networkCapabilities.networkQuality)
    }
  }

  fun initialize() {
    mFirebaseCrashlytics.setUserId(firebaseMemo.userID.value)
    mFirebaseCrashlytics.setCustomKey(
      "release_version",
      AppData.IsRelease
    )
    mFirebaseCrashlytics.setCustomKey(
      "debuggable",
      AppData.IsDebuggable
    )
    mFirebaseCrashlytics.setCustomKey(
      "localization_country",
      Locale.getDefault().displayCountry
    )
    mFirebaseCrashlytics.setCustomKey(
      "localization_language",
      Locale.getDefault().displayLanguage
    )
    mFirebaseCrashlytics.setCustomKey(
      "is_valid_signature",
      firebaseMemo.hasValidSignature
    )
    val countryCode: String = AppUtils.deviceCountryCode.uppercase()
    val locale = Locale("", countryCode)
    mFirebaseCrashlytics.setCustomKey(
      "country_code",
      countryCode
    )
    mFirebaseCrashlytics.setCustomKey(
      "country",
      locale.displayCountry
    )
    mFirebaseCrashlytics.setCustomKey(
      "app_package",
      AppData.PackageName
    )
  }

  fun enableCrashlytics(enable: Boolean) {
    mFirebaseCrashlytics.setCrashlyticsCollectionEnabled(enable)
  }

  fun log(log: String) {
    mFirebaseCrashlytics.log(log)
  }

  fun refreshValidApp() {
    mFirebaseCrashlytics.setCustomKey(
      "is_valid_signature",
      firebaseMemo.hasValidSignature
    )
  }

  fun refreshUserId() {
    mFirebaseCrashlytics.setUserId(firebaseMemo.userID.value)
  }

  private fun setConnection(networkStatus: NetworkStatus) {
    val connected = networkStatus != NetworkStatus.NOT_CONNECTED
    mFirebaseCrashlytics.setCustomKey(
      "connection_available",
      connected
    )
  }

  private fun setConnectionQuality(connectionQuality: NetworkQuality) {
    mFirebaseCrashlytics.setCustomKey(
      "connection_quality",
      connectionQuality.name
    )
  }
}
