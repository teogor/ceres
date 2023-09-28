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

package dev.teogor.ceres.monetisation.admob.formats.ui

import android.util.Log
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import dev.teogor.ceres.monetisation.admob.formats.BannerAd

@Composable
fun AdmobBanner(
  modifier: Modifier = Modifier,
  bannerAd: BannerAd,
) {
  LaunchedEffect(bannerAd.adEvent.value) {
    Log.d("AdmobBanner", "event=${bannerAd.adEvent.value}")
  }

  AndroidView(
    modifier = modifier.fillMaxWidth(),
    factory = { context ->
      // on below line specifying ad view.
      bannerAd.buildAdView(context)
    },
  )
}
