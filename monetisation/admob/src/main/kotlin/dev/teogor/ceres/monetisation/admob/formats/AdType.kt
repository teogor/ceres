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

@Keep
enum class AdType {
  AppOpen,
  Banner,
  Interstitial,
  InterstitialVideo,
  RewardedInterstitial,
  Native,
  NativeVideo,
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
