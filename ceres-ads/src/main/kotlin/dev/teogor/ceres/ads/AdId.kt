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

import dev.teogor.ceres.ads.utils.Constants
import dev.teogor.ceres.core.construct.AppData

abstract class AdId {

  abstract fun id(): String
  abstract fun type(): AdType

  val id: String = Constants.NOT_SET
    get() = if (!AppData.IsDebuggable) {
      id()
    } else when (type()) {
      AdType.APP_OPEN -> Constants.TestAdsId.APP_OPEN
      AdType.INTERSTITIAL -> Constants.TestAdsId.INTERSTITIAL
      AdType.INTERSTITIAL_REWARDED -> Constants.TestAdsId.REWARDED_INTERSTITIAL
      AdType.NATIVE -> Constants.TestAdsId.NATIVE
      AdType.REWARDED -> Constants.TestAdsId.REWARDED
      AdType.BANNER -> Constants.TestAdsId.BANNER
      AdType.INTERSTITIAL_VIDEO -> Constants.TestAdsId.INTERSTITIAL_VIDEO
      AdType.NATIVE_VIDEO -> Constants.TestAdsId.NATIVE_VIDEO
      AdType.NOT_SET -> field
    }
}
