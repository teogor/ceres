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

import com.google.android.gms.ads.LoadAdError

// todo rename
sealed class AdEvent {
  data object Unspecified : AdEvent()
  data object AdClicked : AdEvent()
  data object AdClosed : AdEvent()
  data class AdFailedToLoad(val error: LoadAdError) : AdEvent()
  data object AdImpression : AdEvent()
  data object AdIsLoading : AdEvent() // LoadInProgress
  data object AdLoaded : AdEvent()
  data object AdOpened : AdEvent()
  data object AdSwipeGestureClicked : AdEvent()
  data class OnUserEarnedReward(val rewardItem: RewardItem) : AdEvent()

  val name = this.javaClass.simpleName
}
