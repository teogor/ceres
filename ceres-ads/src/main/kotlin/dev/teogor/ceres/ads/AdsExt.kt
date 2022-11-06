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

@file:JvmName("AdsExt")

package dev.teogor.ceres.ads

import android.app.Activity
import android.content.Intent
import dev.teogor.ceres.ads.utils.Constants
import dev.teogor.ceres.ads.utils.Constants.FORCE_SHOW_APP_OPEN_AD

fun Activity.isAdActivity(): Boolean {
  return this.javaClass.canonicalName == Constants.AD_ACTIVITY_CLASS
}

fun Intent.showAppOpenAd() {
  putExtra(FORCE_SHOW_APP_OPEN_AD, true)
}
