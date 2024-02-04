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

package dev.teogor.ceres.monetisation.admob

/**
 * Provides test ad unit IDs for Google AdMob. These IDs are for demonstration
 * purposes only and should be replaced with your own ad unit IDs before publishing
 * your app.
 *
 * Using test ad unit IDs allows you to test your ad integration without generating
 * invalid traffic on your AdMob account.
 *
 * @see [Google AdMob Demo Ad Units documentation](https://developers.google.com/admob/android/test-ads)
 */
typealias AdUnitId = String

/**
 * Contains constant test ad unit IDs for various ad formats.
 *
 * **IMPORTANT:**
 * - Use these IDs exclusively for testing purposes.
 * - Replace them with your actual production ad unit IDs before releasing your app.
 * - Failure to do so will result in invalid traffic on your AdMob account and
 * potential policy violations.
 */
object TestAdUnitIds {
  /**
   * Ad unit ID for App Open ads.
   */
  const val APP_OPEN: AdUnitId = "ca-app-pub-3940256099942544/9257395921"

  /**
   * Ad unit ID for Adaptive Banner ads.
   */
  const val ADAPTIVE_BANNER: AdUnitId = "ca-app-pub-3940256099942544/9214589741"

  /**
   * Ad unit ID for Banner ads.
   */
  const val BANNER: AdUnitId = "ca-app-pub-3940256099942544/6300978111"

  /**
   * Ad unit ID for Interstitial ads.
   */
  const val INTERSTITIAL: AdUnitId = "ca-app-pub-3940256099942544/1033173712"

  /**
   * Ad unit ID for Interstitial Video ads.
   */
  const val INTERSTITIAL_VIDEO: AdUnitId = "ca-app-pub-3940256099942544/8691691433"

  /**
   * Ad unit ID for Rewarded ads.
   */
  const val REWARDED: AdUnitId = "ca-app-pub-3940256099942544/5224354917"

  /**
   * Ad unit ID for Rewarded Interstitial ads.
   */
  const val REWARDED_INTERSTITIAL: AdUnitId = "ca-app-pub-3940256099942544/5354046379"

  /**
   * Ad unit ID for Native Advanced ads.
   */
  const val NATIVE_ADVANCED: AdUnitId = "ca-app-pub-3940256099942544/2247696110"

  /**
   * Ad unit ID for Native Advanced Video ads.
   */
  const val NATIVE_ADVANCED_VIDEO: AdUnitId = "ca-app-pub-3940256099942544/1044960115"
}
