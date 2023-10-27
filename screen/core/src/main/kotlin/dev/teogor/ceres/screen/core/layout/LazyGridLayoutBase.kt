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

package dev.teogor.ceres.screen.core.layout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import dev.teogor.ceres.core.analytics.composables.TrackScreenViewEvent
import dev.teogor.ceres.screen.core.attachScrollState
import dev.teogor.ceres.screen.core.scope.ScreenGridScope
import dev.teogor.ceres.ui.designsystem.scrollbar.LazyGridScrollbar
import dev.teogor.ceres.ui.designsystem.scrollbar.ScrollbarCustomization
import dev.teogor.ceres.ui.designsystem.scrollbar.ScrollbarIndicator

@Composable
fun LazyGridLayoutBase(
  columns: GridCells,
  hasScrollbar: Boolean = true,
  hasScrollbarBackground: Boolean = true,
  screenName: String? = null,
  indicatorContent: ((Int) -> String)? = null,
  contentPadding: PaddingValues = PaddingValues(0.dp),
  reverseLayout: Boolean = false,
  verticalArrangement: Arrangement.Vertical =
    if (!reverseLayout) Arrangement.Top else Arrangement.Bottom,
  horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
  content: ScreenGridScope.() -> Unit,
) {
  val state = rememberLazyGridState().attachScrollState()
  LazyGridScrollbar(
    scrollbarCustomization = ScrollbarCustomization(
      state = state,
    ).copy(
      enabled = hasScrollbar,
      hasScrollbarBackground = hasScrollbarBackground,
      indicatorContent = if (indicatorContent != null) {
        { index, isThumbSelected ->
          if (isThumbSelected) {
            ScrollbarIndicator(
              indicatorContent(index),
            )
          }
        }
      } else {
        null
      },
    ),
    columns = columns,
  ) {
    LazyVerticalGrid(
      state = state,
      columns = columns,
      contentPadding = contentPadding,
      reverseLayout = reverseLayout,
      verticalArrangement = verticalArrangement,
      horizontalArrangement = horizontalArrangement,
    ) {
      content()
    }
  }

  screenName?.let {
    TrackScreenViewEvent(screenName = it)
  }
}
