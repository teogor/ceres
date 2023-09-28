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

package dev.teogor.ceres.ui.theme.core

import android.graphics.Color
import androidx.annotation.ColorInt
import androidx.annotation.FloatRange

class SurfaceOverlay(
  @get:ColorInt @param:ColorInt
  val themeOverlayColor: Int,
  @get:ColorInt @param:ColorInt
  val themeSurfaceColor: Int,
) {

  fun forAlpha(
    @FloatRange(from = 0.0, to = 1.0) overlayAlpha: Float,
  ): androidx.compose.ui.graphics.Color {
    return androidx.compose.ui.graphics.Color(
      blendColors(
        themeOverlayColor,
        themeSurfaceColor,
        overlayAlpha,
      ),
    )
  }
}

@ColorInt
fun blendColors(
  color1: Int,
  color2: Int,
  @FloatRange(from = 0.0, to = 1.0) ratio: Float,
): Int {
  val inverseRatio = 1f - ratio
  val a = Color.alpha(color1) * inverseRatio + Color.alpha(color2) * ratio
  val r = Color.red(color1) * inverseRatio + Color.red(color2) * ratio
  val g = Color.green(color1) * inverseRatio + Color.green(color2) * ratio
  val b = Color.blue(color1) * inverseRatio + Color.blue(color2) * ratio
  return Color.argb(a.toInt(), r.toInt(), g.toInt(), b.toInt())
}
