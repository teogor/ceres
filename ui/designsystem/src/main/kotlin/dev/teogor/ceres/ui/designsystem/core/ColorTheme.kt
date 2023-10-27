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

package dev.teogor.ceres.ui.designsystem.core

import androidx.annotation.IntRange
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import dev.teogor.ceres.ui.designsystem.tone
import dev.teogor.ceres.ui.theme.MaterialTheme

object ColorTheme {
  @Composable
  fun Color.tone(
    @IntRange(from = 0, to = 100) lightTone: Int,
    @IntRange(from = 0, to = 100) darkTone: Int,
  ): Color {
    val isDarkMode = MaterialTheme.isDarkMode
    return if (isDarkMode) {
      tone(darkTone)
    } else {
      tone(lightTone)
    }
  }

  @Composable
  fun lightDarkColor(
    lightColor: Color,
    darkColor: Color,
  ): Color {
    val isDarkMode = MaterialTheme.isDarkMode
    return if (isDarkMode) {
      darkColor
    } else {
      lightColor
    }
  }
}
