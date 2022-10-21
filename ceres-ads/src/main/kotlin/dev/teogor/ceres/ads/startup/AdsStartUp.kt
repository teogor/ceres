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

package dev.teogor.ceres.ads.startup

import dev.teogor.ceres.ads.Ad
import java.lang.ref.WeakReference

object AdsStartUp {
  var openAdsWeak: List<WeakReference<Ad>> = listOf()
    set(value) {
      field = value
      value.map { weakAd -> weakAd.get()!! }.forEach { ad ->
        ad.load()
      }
    }

  var enabled: Boolean = true
}
