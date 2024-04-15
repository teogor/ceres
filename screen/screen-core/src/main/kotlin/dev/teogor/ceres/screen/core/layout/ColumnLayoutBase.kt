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
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import dev.teogor.ceres.core.analytics.composables.TrackScreenViewEvent
import dev.teogor.ceres.screen.core.attachScrollState
import dev.teogor.ceres.screen.core.scope.ScreenColumnScope
import dev.teogor.ceres.ui.designsystem.scrollbar.ColumnScrollbar
import dev.teogor.ceres.ui.designsystem.scrollbar.ScrollbarCustomization

@Composable
fun ColumnLayoutBase(
  modifier: Modifier = Modifier,
  verticalArrangement: Arrangement.Vertical = Arrangement.Top,
  horizontalAlignment: Alignment.Horizontal = Alignment.Start,
  hasScrollbar: Boolean = true,
  hasScrollbarBackground: Boolean = true,
  screenName: String? = null,
  bottomContent: (@Composable () -> Unit)? = null,
  content: @Composable ScreenColumnScope.() -> Unit,
) {
  Column(
    modifier = Modifier.fillMaxSize(),
  ) {
    Box(
      modifier = Modifier.weight(1f),
    ) {
      val state = rememberScrollState().attachScrollState()
      ColumnScrollbar(
        scrollbarCustomization = ScrollbarCustomization(
          state = state,
        ).copy(
          enabled = hasScrollbar,
          hasScrollbarBackground = hasScrollbarBackground,
        ),
      ) {
        Column(
          modifier = modifier
            .verticalScroll(state)
            .fillMaxWidth(),
          verticalArrangement = verticalArrangement,
          horizontalAlignment = horizontalAlignment,
        ) {
          content()
        }
      }
    }
    bottomContent?.invoke()
  }

  screenName?.let {
    TrackScreenViewEvent(screenName = it)
  }
}
