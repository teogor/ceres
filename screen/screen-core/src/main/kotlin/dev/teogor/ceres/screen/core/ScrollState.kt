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

package dev.teogor.ceres.screen.core

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import dev.teogor.ceres.ui.compose.UpdateToolbarAlpha

@Composable
fun <T : ScrollableState> T.attachScrollState(trigger: Int = 50): T {
  val toolbarAlphaState = remember { mutableFloatStateOf(0f) }
  LaunchedEffect(this) {
    snapshotFlow {
      when (val scrollState = this@attachScrollState) {
        is ScrollState -> scrollState.value
        is LazyListState -> {
          val firstVisibleItemScrollOffset = scrollState.firstVisibleItemScrollOffset
          val firstVisibleItemIndex = scrollState.firstVisibleItemIndex
          if (firstVisibleItemIndex == 0) {
            firstVisibleItemScrollOffset
          } else {
            trigger
          }
        }

        is LazyGridState -> {
          val firstVisibleItemScrollOffset = scrollState.firstVisibleItemScrollOffset
          val firstVisibleItemIndex = scrollState.firstVisibleItemIndex
          if (firstVisibleItemIndex == 0) {
            firstVisibleItemScrollOffset
          } else {
            trigger
          }
        }

        else -> 0
      }
    }.collect { value ->
      val newAlpha = if (value < trigger) value / trigger.toFloat() else 1f
      if (newAlpha != toolbarAlphaState.value) {
        toolbarAlphaState.value = newAlpha
      }
    }
  }

  UpdateToolbarAlpha(toolbarAlphaState.value)
  return this
}
