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

import dev.teogor.ceres.core.register.BuildProfiler
import dev.teogor.ceres.core.register.LocalBuildProfiler
import dev.teogor.ceres.monetisation.admob.TestAdUnitIds

abstract class AdId {
  abstract fun id(): String
  abstract fun type(): AdType

  internal val buildProfiler: BuildProfiler
    get() = LocalBuildProfiler.current

  val id: String = ""
    get() = if (!buildProfiler.isDebuggable) {
      id()
    } else {
      when (type()) {
        AdType.AdaptiveBanner -> TestAdUnitIds.ADAPTIVE_BANNER
        AdType.AppOpen -> TestAdUnitIds.APP_OPEN
        AdType.Banner -> TestAdUnitIds.BANNER
        AdType.Interstitial -> TestAdUnitIds.INTERSTITIAL
        AdType.InterstitialVideo -> TestAdUnitIds.INTERSTITIAL_VIDEO
        AdType.NativeAdvanced -> TestAdUnitIds.NATIVE_ADVANCED
        AdType.NativeAdvancedVideo -> TestAdUnitIds.NATIVE_ADVANCED_VIDEO
        AdType.Rewarded -> TestAdUnitIds.REWARDED
        AdType.RewardedInterstitial -> TestAdUnitIds.REWARDED_INTERSTITIAL
        AdType.Unspecified -> field
      }
    }
}
