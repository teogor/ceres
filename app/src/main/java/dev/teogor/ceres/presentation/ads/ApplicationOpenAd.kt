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
import dev.teogor.ceres.ads.annotation.AppOpenAdType
import dev.teogor.ceres.ads.formats.AppOpenAd
import dev.teogor.ceres.ads.utils.Constants
import javax.inject.Inject
import javax.inject.Singleton

@AppOpenAdType
@Singleton
class ApplicationOpenAd @Inject constructor() : AppOpenAd() {

  override fun id() = Constants.TestAdsId.APP_OPEN

  override fun type() = AdType.APP_OPEN

  override fun loadContinuously() = true

  override fun useCache() = false
}
