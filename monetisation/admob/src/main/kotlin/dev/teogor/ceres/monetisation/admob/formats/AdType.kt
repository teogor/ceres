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

import androidx.annotation.Keep
import dev.teogor.ceres.monetisation.admob.formats.AdType.AdaptiveBanner
import dev.teogor.ceres.monetisation.admob.formats.AdType.AppOpen
import dev.teogor.ceres.monetisation.admob.formats.AdType.Banner
import dev.teogor.ceres.monetisation.admob.formats.AdType.Interstitial
import dev.teogor.ceres.monetisation.admob.formats.AdType.InterstitialVideo
import dev.teogor.ceres.monetisation.admob.formats.AdType.NativeAdvanced
import dev.teogor.ceres.monetisation.admob.formats.AdType.NativeAdvancedVideo
import dev.teogor.ceres.monetisation.admob.formats.AdType.Rewarded
import dev.teogor.ceres.monetisation.admob.formats.AdType.RewardedInterstitial
import dev.teogor.ceres.monetisation.admob.formats.AdType.Unspecified

/**
 * Represents the various supported ad types within the app.
 *
 * @property AdaptiveBanner Ads that automatically adjust their size and layout
 * to fit different screen sizes.
 * @property AppOpen Ads that are displayed automatically when the app is opened.
 * @property Banner Standard rectangular ads that appear at the top or bottom of
 * the screen.
 * @property Interstitial Full-screen ads that cover the entire app interface.
 * @property InterstitialVideo Full-screen ads that play a video before allowing
 * the user to return to the app.
 * @property RewardedInterstitial Full-screen ads that offer a reward to the user
 * for watching them.
 * @property NativeAdvanced Ads that are designed to blend seamlessly with the app's
 * content and layout.
 * @property NativeAdvancedVideo Native ads that incorporate a video component.
 * @property Rewarded Ads that offer a reward to the user for interacting with them.
 * @property Unspecified Used when the ad type is unknown or not yet determined.
 */
@Keep
enum class AdType {
  AdaptiveBanner,
  AppOpen,
  Banner,
  Interstitial,
  InterstitialVideo,
  RewardedInterstitial,
  NativeAdvanced,
  NativeAdvancedVideo,
  Rewarded,
  Unspecified,
  ;

  fun type(): AdType = this
}

/**
 * Converts an AdType to a user-friendly name with an optional prefix and suffix.
 *
 * @param prefix The prefix to add to the friendly name.
 * @param suffix The suffix to add to the friendly name.
 * @return The user-friendly name of the AdType.
 */
fun AdType.toFriendlyName(
  prefix: String = "",
  suffix: String = "",
): String {
  return this.name
    .split(Regex("(?=[A-Z])"))
    .joinToString(" ") {
      it.replaceFirstChar { char ->
        if (char.isLowerCase()) {
          char.titlecase()
        } else {
          char.toString()
        }
      }
    }
    .trim()
    .let { "$prefix$it$suffix" }
}
