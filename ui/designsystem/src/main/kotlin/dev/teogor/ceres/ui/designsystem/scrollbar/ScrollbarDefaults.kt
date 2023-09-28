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

package dev.teogor.ceres.ui.designsystem.scrollbar

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.teogor.ceres.ui.designsystem.Text
import dev.teogor.ceres.ui.designsystem.scrollbar.ScrollbarSelectionMode.Thumb
import dev.teogor.ceres.ui.designsystem.surfaceColorAtElevation
import dev.teogor.ceres.ui.designsystem.tone
import dev.teogor.ceres.ui.theme.MaterialTheme
import dev.teogor.ceres.ui.theme.tokens.ColorSchemeKeyTokens
import dev.teogor.ceres.ui.theme.tokens.ElevationTokens

data class ScrollbarCustomization(
  val state: ScrollableState,
  val rightSide: Boolean = true,
  val thickness: Dp = ScrollbarTokens.Thickness,
  val padding: Dp = ScrollbarTokens.Padding,
  val thumbMinHeight: Float = ScrollbarTokens.ThumbMinHeight,
  val thumbColor: ColorSchemeKeyTokens = ScrollbarTokens.ThumbColor,
  val thumbSelectedColor: ColorSchemeKeyTokens = ScrollbarTokens.ThumbSelectedColor,
  val thumbShape: Shape = ScrollbarTokens.ThumbShape,
  val enabled: Boolean = true,
  val selectionMode: ScrollbarSelectionMode = Thumb,
  val hasScrollbarBackground: Boolean = true,
  val indicatorContent: (
    @Composable (
      normalizedOffset: Int,
      isThumbSelected: Boolean,
    ) -> Unit
  )? = null,
)

@Composable
fun ScrollbarIndicator(
  content: String,
) {
  val contentCopy = remember(content) {
    content
  }
  val size = 100.dp
  val shape = RoundedCornerShape(
    topStart = 60.dp,
    topEnd = 60.dp,
    bottomStart = 60.dp,
  )
  val backgroundColor = ScrollbarDefaults.thumbSelectedColor(ScrollbarTokens.ThumbSelectedColor)
  val textSize = 24.sp
  Box(
    modifier = Modifier
      .padding(bottom = size / 2)
      .size(size)
      .clip(shape)
      .background(
        color = backgroundColor,
        shape = shape,
      ),
    contentAlignment = Alignment.Center,
  ) {
    Text(
      text = contentCopy.trim(),
      color = MaterialTheme.colorScheme.surface,
      textAlign = TextAlign.Center,
      fontSize = textSize,
      lineHeight = textSize,
    )
  }
}

object ScrollbarDefaults {
  @Composable
  fun thumbColor(
    colorKeyToken: ColorSchemeKeyTokens,
  ) = colorKeyToken.tone(
    tone = 60,
  )

  @Composable
  fun thumbSelectedColor(
    colorKeyToken: ColorSchemeKeyTokens,
  ) = colorKeyToken.tone(
    tone = 70,
  )

  @Composable
  fun background(
    colorKeyToken: ColorSchemeKeyTokens = ScrollbarTokens.Background,
  ) = surfaceColorAtElevation(
    elevation = ElevationTokens.Level10,
    tint = colorKeyToken.tone(
      tone = 60,
    ),
  )
}
