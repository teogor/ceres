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

package dev.teogor.ceres.firebase.remote_config

import com.google.android.gms.tasks.Task
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteConfig @Inject constructor() {

  private var mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance()

  fun initialize() {
    val configSettings = FirebaseRemoteConfigSettings.Builder()
      .build()
    mFirebaseRemoteConfig.setConfigSettingsAsync(configSettings)
    // todo default
    // mFirebaseRemoteConfig.setDefaultsAsync(R.xml.remote_config_defaults)
    fetch()
  }

  private fun fetch() {
    val cacheExpiration: Long = 3600
    mFirebaseRemoteConfig.fetch(cacheExpiration).addOnCompleteListener { task: Task<Void?> ->
      if (task.isSuccessful) {
        mFirebaseRemoteConfig.activate()
      }
    }
  }

  fun getString(key: String): String {
    return mFirebaseRemoteConfig.getString(key)
  }

  fun getLong(key: String): Long {
    return mFirebaseRemoteConfig.getLong(key)
  }

  fun getBoolean(key: String): Boolean {
    return mFirebaseRemoteConfig.getBoolean(key)
  }

  annotation class Constants {
    companion object {
      var UPDATE_APP_MIN_CRITICAL = "update_app_min_critical"
      var UPDATE_APP_MAX_CRITICAL = "update_app_max_critical"
      var UPDATE_APP_FLEXIBLE = "update_app_flexible"
      var URL_POLICIES_ToS = "url_policies_terms"
      var URL_POLICIES_PP = "url_policies_privacy"
    }
  }
}
