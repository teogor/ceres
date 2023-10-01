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

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import dev.teogor.ceres.navigation.events.TrackScreenViewEvent
import dev.teogor.ceres.ui.theme.MaterialTheme

@Composable
fun FullScreenLayoutBase(
  screenName: String? = null,
  backgroundColor: Color = MaterialTheme.colorScheme.background,
  hasStatusBar: Boolean = false,
  content: @Composable BoxScope.() -> Unit,
) {
  Box(
    modifier = Modifier
      .fillMaxSize()
      .background(color = backgroundColor)
      .apply {
        if (hasStatusBar) {
          statusBarsPadding()
        }
      },
    content = content,
  )

  screenName?.let {
    TrackScreenViewEvent(screenName = it)
  }
}
