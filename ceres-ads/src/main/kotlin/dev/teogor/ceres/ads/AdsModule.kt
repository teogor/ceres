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

import com.google.android.gms.ads.AdActivity
import dev.teogor.ceres.ads.startup.AdsStartUp
import dev.teogor.ceres.core.app.Module
import dev.teogor.ceres.core.app.ModuleProvider
import dev.teogor.ceres.core.provider.InitProviderData
import java.lang.ref.WeakReference
import javax.inject.Inject
import javax.inject.Singleton

@Module
@Singleton
class AdsModule @Inject constructor() : ModuleProvider() {

  override fun onCreate() {
    super.onCreate()
    InitProviderData.flagActivityClass(
      AdActivity::class
    )
  }

  fun withAds(appOpenAds: List<Ad>) {
    val openAdsWeak = mutableListOf<WeakReference<Ad>>()
    appOpenAds.forEach { ad ->
      openAdsWeak.add(WeakReference(ad))
    }
    AdsStartUp.openAdsWeak = openAdsWeak
  }
}
