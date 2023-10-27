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

import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import dev.teogor.ceres.core.foundation.compositions.LocalNetworkMonitor

/**
 * Abstract class representing a Native Ad Manager.
 */
abstract class NativeAdManager {
  /**
   * Retrieve the NativeAdConfig to configure the native ad.
   *
   * @return A [NativeAdConfig] object.
   */
  @Composable
  abstract fun config(): NativeAdConfig

  /**
   * Display the Native Ad.
   */
  @Composable
  abstract fun Display()

  /**
   * Composable function that provides the UI for the native ad.
   *
   * @param isAdFillEmpty A boolean indicating whether the ad fill is empty.
   */
  @Composable
  abstract fun NativeAdConfig.UI(isAdFillEmpty: Boolean)

  /**
   * Check network connectivity and call the provided [displayAd] function
   * if online.
   *
   * @param displayAd A composable function to display the ad when online.
   */
  @Composable
  open fun IsOnline(
    displayAd: @Composable () -> Unit,
  ) {
    val networkMonitor = LocalNetworkMonitor.current
    val isOffline by remember {
      derivedStateOf { networkMonitor.isOffline }
    }
    if (!isOffline) {
      displayAd()
    }
  }
}

/**
 * A composable function that displays a native ad of the specified type.
 *
 * @param T The type of NativeAdManager to create and display.
 */
@Composable
inline fun <reified T : NativeAdManager> NativeAd() {
  val adManager = remember { createNativeAdManager<T>() }
  adManager.Display()
}

/**
 * Create an instance of the specified [NativeAdManager] type.
 *
 * @param T The type of NativeAdManager to create.
 * @return An instance of the specified [NativeAdManager].
 */
inline fun <reified T : NativeAdManager> createNativeAdManager(): T {
  return T::class.java.getDeclaredConstructor().newInstance()
}
