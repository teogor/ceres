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

package dev.teogor.ceres.core.common

import android.view.WindowManager
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import dev.teogor.ceres.core.common.utils.activity

/**
 * A composable function that allows you to keep the screen on while a specified
 * condition is met.
 *
 * @param predicate A lambda function that returns a boolean value indicating whether
 *                  the screen should be kept on or not. When `true`, the screen will
 *                  remain on; when `false`, the screen will not be forced to stay on.
 */
@Composable
fun KeepScreenOn(
  predicate: () -> Boolean = { true },
) {
  val activity = activity
  val window = activity?.window

  if (predicate()) {
    DisposableEffect(Unit) {
      window?.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
      onDispose {
        window?.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
      }
    }
  } else {
    window?.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
  }
}
