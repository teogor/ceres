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

package dev.teogor.ceres.monetisation.admob.formats.nativead

import android.os.CountDownTimer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest

@Composable
fun RefreshableNativeAd(
  adId: String,
  adLoader: AdLoader,
  refreshIntervalMillis: Long,
) {
  // Load the ad initially
  LaunchedEffect(Unit) {
    adLoader.loadAd(AdRequest.Builder().build())
  }

  // Create a timer to refresh the ad at the specified interval
  DisposableEffect(adId) {
    val timer = object : CountDownTimer(refreshIntervalMillis, refreshIntervalMillis) {
      override fun onTick(millisUntilFinished: Long) {
        // No action needed on tick
      }

      override fun onFinish() {
        // Refresh the ad when the timer finishes
        adLoader.loadAd(AdRequest.Builder().build())
        start()
      }
    }

    // Start the timer when the Composable is first composed
    timer.start()

    // Stop the timer when the Composable is disposed
    onDispose {
      timer.cancel()
    }
  }
}
