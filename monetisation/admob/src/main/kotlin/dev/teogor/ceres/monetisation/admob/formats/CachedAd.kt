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

package dev.teogor.ceres.monetisation.admob.formats

import com.google.android.gms.ads.appopen.AppOpenAd
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAd

/**
 * Sealed class representing cached ad models with a load time.
 *
 * @property loadTime The time when the ad was loaded.
 */
sealed class CachedAd(
  open val loadTime: Long,
) {
  /**
   * Data class representing a cached App Open ad.
   *
   * @property ad The App Open ad instance.
   * @property loadTime The time when the ad was loaded.
   */
  data class AppOpen(
    val ad: AppOpenAd,
    override val loadTime: Long,
  ) : CachedAd(loadTime)

  /**
   * Data class representing a cached Interstitial ad.
   *
   * @property ad The Interstitial ad instance.
   * @property loadTime The time when the ad was loaded.
   */
  data class Interstitial(
    val ad: InterstitialAd,
    override val loadTime: Long,
  ) : CachedAd(loadTime)

  /**
   * Data class representing a cached Rewarded Interstitial ad.
   *
   * @property ad The Rewarded Interstitial ad instance.
   * @property loadTime The time when the ad was loaded.
   */
  data class RewardedInterstitial(
    val ad: RewardedInterstitialAd,
    override val loadTime: Long,
  ) : CachedAd(loadTime)

  /**
   * Data class representing a cached Rewarded ad.
   *
   * @property ad The Rewarded ad instance.
   * @property loadTime The time when the ad was loaded.
   */
  data class Rewarded(
    val ad: RewardedAd,
    override val loadTime: Long,
  ) : CachedAd(loadTime)

  /**
   * Data class representing a cached Native ad.
   *
   * @property ad The Native ad instance.
   * @property loadTime The time when the ad was loaded.
   */
  data class Native(
    val ad: NativeAd,
    override val loadTime: Long,
  ) : CachedAd(loadTime)
}
