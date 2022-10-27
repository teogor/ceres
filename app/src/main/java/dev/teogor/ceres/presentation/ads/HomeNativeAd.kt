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
import dev.teogor.ceres.ads.formats.NativeAd
import dev.teogor.ceres.ads.utils.Constants
import dev.teogor.ceres.core.network.Network
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Ad
@Singleton
class HomeNativeAd @Inject constructor(
  network: Network
) : NativeAd(network) {

  override fun id() = Constants.TestAdsId.NATIVE

  override fun type() = AdType.NATIVE

  override fun loadContinuously() = true

  override fun useCache() = true

  override val stopLoadingAdsAfter = 2

  override val failedToLoadWaitTime = TimeUnit.MINUTES.toMillis(30)

  override val refreshInterval = TimeUnit.SECONDS.toMillis(45)
}
