/*
 * Copyright 2022 teogor (Teodor Grigor)
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

package dev.teogor.ceres.ads

import dev.teogor.ceres.monetisation.admob.TestAdUnitIds
import dev.teogor.ceres.monetisation.admob.formats.RewardedInterstitialAd
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomeRewardedInterstitialAd @Inject constructor() : RewardedInterstitialAd(
  loadAtInitialisation = true,
) {
  override val concurrentAdLoadLimit: Int
    get() = 2

  override fun id() = TestAdUnitIds.REWARDED_INTERSTITIAL

  override fun loadContinuously() = true
}
