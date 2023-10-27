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

import androidx.annotation.FloatRange
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.graphics.ColorUtils
import dev.teogor.ceres.ui.theme.MaterialTheme
import kotlin.math.max
import kotlin.math.min

object ColorUtils {

  fun Color.blend(
    color: Color,
    @FloatRange(from = 0.0, to = 1.0) fraction: Float = 0.2f,
  ): Color = ColorUtils.blendARGB(this.toArgb(), color.toArgb(), fraction).toColor()

  fun Color.darken(
    @FloatRange(from = 0.0, to = 1.0) fraction: Float = 0.2f,
  ): Color = blend(color = Color.Black, fraction = fraction)

  fun Color.lighten(
    @FloatRange(from = 0.0, to = 1.0) fraction: Float = 0.2f,
  ): Color = blend(color = Color.White, fraction = fraction)

  fun Int.toColor(): Color = Color(color = this)

  fun Color.toGrayscale(): Color {
    val grayValue = (red + green + blue) / 3
    return Color(grayValue, grayValue, grayValue, alpha)
  }

  fun Color.invert(): Color {
    return Color(1.0f - red, 1.0f - green, 1.0f - blue, alpha)
  }

  fun Color.getBrightness(): Float {
    return (0.299f * red + 0.587f * green + 0.114f * blue)
  }

  fun Color.calculateContrastRatio(otherColor: Color): Double {
    val l1 = getBrightness()
    val l2 = otherColor.getBrightness()
    val lighter = max(l1, l2)
    val darker = min(l1, l2)
    return (lighter + 0.05) / (darker + 0.05)
  }

  @Composable
  fun Color.harmonizeWithPrimary(
    @FloatRange(from = 0.0, to = 1.0)
    fraction: Float = 0.2f,
  ): Color = blend(MaterialTheme.colorScheme.primary, fraction)
}
