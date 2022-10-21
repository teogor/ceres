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

package dev.teogor.ceres.presentation.ads

import dev.teogor.ceres.ads.AdType
import dev.teogor.ceres.ads.annotation.Ad
import dev.teogor.ceres.ads.formats.InterstitialAd
import dev.teogor.ceres.ads.utils.Constants
import dev.teogor.ceres.core.network.Network
import javax.inject.Inject
import javax.inject.Singleton

@Ad
@Singleton
class HomeInterstitialAd @Inject constructor(
  network: Network
) : InterstitialAd(network) {

  override fun id() = Constants.TestAdsId.INTERSTITIAL

  override fun type() = AdType.INTERSTITIAL

  override fun loadContinuously() = false

  override fun useCache() = true
}
