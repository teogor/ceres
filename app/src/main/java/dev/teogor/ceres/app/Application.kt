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

package dev.teogor.ceres.app

import dagger.hilt.android.HiltAndroidApp
import dev.teogor.ceres.R
import dev.teogor.ceres.ads.AdsModule
import dev.teogor.ceres.core.app.CoreApplication
import dev.teogor.ceres.core.app.ModuleProvider
import dev.teogor.ceres.core.network.NetworkModule
import dev.teogor.ceres.firebase.FirebaseModule
import dev.teogor.ceres.presentation.ads.ApplicationOpenAd
import javax.inject.Inject

@HiltAndroidApp
class Application : CoreApplication() {

  @Inject
  lateinit var networkModule: NetworkModule

  @Inject
  lateinit var firebaseModule: FirebaseModule

  @Inject
  lateinit var adsModule: AdsModule

  @Inject
  lateinit var applicationOpenAd: ApplicationOpenAd

  override fun getModules(): List<ModuleProvider> {
    adsModule.withAds(
      listOf(
        applicationOpenAd
      )
    )
    firebaseModule.remoteConfigDefXML = R.xml.remote_config_defaults
    return listOf(
      networkModule,
      adsModule,
      firebaseModule
    )
  }
}
