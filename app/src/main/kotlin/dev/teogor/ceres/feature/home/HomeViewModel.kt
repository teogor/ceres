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

package dev.teogor.ceres.feature.home

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.teogor.ceres.ads.HomeBannerAd
import dev.teogor.ceres.ads.HomeInterstitialAd
import dev.teogor.ceres.ads.HomeNativeAdBeta
import dev.teogor.ceres.ads.HomeRewardedAd
import dev.teogor.ceres.ads.HomeRewardedInterstitialAd
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
  val homeInterstitialAd: HomeInterstitialAd,
  val homeRewardedInterstitialAd: HomeRewardedInterstitialAd,
  val homeRewardedAd: HomeRewardedAd,
  val homeBannerAd: HomeBannerAd,
) : ViewModel() {

  val homeNativeAd = HomeNativeAdBeta()
}
