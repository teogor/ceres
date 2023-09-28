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

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel

class ToolbarState : ViewModel() {
  private val _toolbarAlpha = mutableStateOf(0f)
  val toolbarAlpha: State<Float> = _toolbarAlpha
  private val _elevation = mutableStateOf(0.dp)
  val elevation: State<Dp> = _elevation
  private val _toolbarColor = mutableStateOf(Color.Unspecified)
  val toolbarColor: State<Color> = _toolbarColor

  fun updateToolbarColor(value: Color) {
    _toolbarColor.value = value
  }

  fun updateElevation(value: Dp) {
    _elevation.value = value
  }

  fun updateToolbarAlpha(value: Float) {
    _toolbarAlpha.value = value
  }
}

val LocalToolbarState = staticCompositionLocalOf<ToolbarState> {
  error("No ToolbarState provided")
}
