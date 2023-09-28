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

package dev.teogor.ceres.ui.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * A side-effect which records a screen view event.
 */
@Composable
fun UpdateToolbarAlpha(
  value: Float,
) {
  val toolbarState = LocalToolbarState.current
  toolbarState.updateToolbarAlpha(value)
}

@Composable
inline fun ToolbarBackground(
  modifier: Modifier = Modifier,
  paddingBottom: Dp = 0.dp,
  crossinline content: (@Composable () -> Unit),
) {
  val toolbarState = LocalToolbarState.current
  val toolbarColor by remember(toolbarState.toolbarColor.value) {
    mutableStateOf(toolbarState.toolbarColor.value)
  }
  Box(
    modifier = modifier
      .background(toolbarColor)
      .padding(bottom = paddingBottom),
  ) {
    content()
  }
}
