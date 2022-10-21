/*
 * Copyright 2022 teogor (Teodor Grigor) All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dev.teogor.ceres.core.util

import android.graphics.Color
import androidx.annotation.ColorInt
import androidx.annotation.FloatRange
import androidx.core.graphics.ColorUtils
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min
import kotlin.math.roundToInt

@Suppress("unused")
object ColorUtil {
  fun desaturateColor(color: Int, ratio: Float): Int {
    val hsv = FloatArray(3)
    Color.colorToHSV(color, hsv)

    hsv[1] = hsv[1] / 1 * ratio + 0.2f * (1.0f - ratio)

    return Color.HSVToColor(hsv)
  }

  fun stripAlpha(@ColorInt color: Int): Int {
    return -0x1000000 or color
  }

  @ColorInt
  fun shiftColor(@ColorInt color: Int, @FloatRange(from = 0.0, to = 2.0) by: Float): Int {
    if (by == 1f) return color
    val alpha = Color.alpha(color)
    val hsv = FloatArray(3)
    Color.colorToHSV(color, hsv)
    hsv[2] *= by // value component
    return (alpha shl 24) + (0x00ffffff and Color.HSVToColor(hsv))
  }

  @ColorInt
  fun darkenColor(@ColorInt color: Int): Int {
    return shiftColor(color, 0.9f)
  }

  @ColorInt
  fun darkenColorTheme(@ColorInt color: Int): Int {
    return shiftColor(color, 0.8f)
  }

  @ColorInt
  fun lightenColor(@ColorInt color: Int): Int {
    return shiftColor(color, 1.1f)
  }

  @ColorInt
  fun lightenColor(
    @ColorInt color: Int,
    value: Float
  ): Int {
    val hsl = FloatArray(3)
    ColorUtils.colorToHSL(color, hsl)
    hsl[2] += value
    hsl[2] = hsl[2].coerceIn(0f, 1f)
    return ColorUtils.HSLToColor(hsl)
  }

  @ColorInt
  fun darkenColor(
    @ColorInt color: Int,
    value: Float
  ): Int {
    val hsl = FloatArray(3)
    ColorUtils.colorToHSL(color, hsl)
    hsl[2] -= value
    hsl[2] = hsl[2].coerceIn(0f, 1f)
    return ColorUtils.HSLToColor(hsl)
  }

  @ColorInt
  fun getReadableColorLight(@ColorInt color: Int, @ColorInt bgColor: Int): Int {
    var foregroundColor = color
    while (ColorUtils.calculateContrast(foregroundColor, bgColor) <= 3.0
    ) {
      foregroundColor = darkenColor(foregroundColor, 0.1F)
    }
    return foregroundColor
  }

  @ColorInt
  fun getReadableColorDark(@ColorInt color: Int, @ColorInt bgColor: Int): Int {
    var foregroundColor = color
    while (ColorUtils.calculateContrast(foregroundColor, bgColor) <= 3.0
    ) {
      foregroundColor = lightenColor(foregroundColor, 0.1F)
    }
    return foregroundColor
  }

  fun isColorLight(@ColorInt color: Int): Boolean {
    val darkness =
      1 - (0.299 * Color.red(color) + 0.587 * Color.green(color) + 0.114 * Color.blue(color)) / 255
    return darkness < 0.4
  }

  @ColorInt
  fun invertColor(@ColorInt color: Int): Int {
    val r = 255 - Color.red(color)
    val g = 255 - Color.green(color)
    val b = 255 - Color.blue(color)
    return Color.argb(Color.alpha(color), r, g, b)
  }

  @ColorInt
  fun adjustAlpha(@ColorInt color: Int, @FloatRange(from = 0.0, to = 1.0) factor: Float): Int {
    val alpha = (Color.alpha(color) * factor).roundToInt()
    val red = Color.red(color)
    val green = Color.green(color)
    val blue = Color.blue(color)
    return Color.argb(alpha, red, green, blue)
  }

  @ColorInt
  fun withAlpha(@ColorInt baseColor: Int, @FloatRange(from = 0.0, to = 1.0) alpha: Float): Int {
    val a = min(255, max(0, (alpha * 255).toInt())) shl 24
    val rgb = 0x00ffffff and baseColor
    return a + rgb
  }

  /**
   * Taken from CollapsingToolbarLayout's CollapsingTextHelper class.
   */
  fun blendColors(color1: Int, color2: Int, @FloatRange(from = 0.0, to = 1.0) ratio: Float): Int {
    val inverseRatio = 1f - ratio
    val a = Color.alpha(color1) * inverseRatio + Color.alpha(color2) * ratio
    val r = Color.red(color1) * inverseRatio + Color.red(color2) * ratio
    val g = Color.green(color1) * inverseRatio + Color.green(color2) * ratio
    val b = Color.blue(color1) * inverseRatio + Color.blue(color2) * ratio
    return Color.argb(a.toInt(), r.toInt(), g.toInt(), b.toInt())
  }

  private fun getColorDarkness(@ColorInt color: Int): Double {
    return if (color == Color.BLACK) {
      1.0
    } else if (color == Color.WHITE || color == Color.TRANSPARENT) {
      0.0
    } else {
      1 - (0.299 * Color.red(color) + 0.587 * Color.green(color) + 0.114 * Color.blue(color)) / 255
    }
  }

  @ColorInt
  fun getInverseColor(@ColorInt color: Int): Int {
    return 0xFFFFFF - color or -0x1
  }

  fun isColorSaturated(@ColorInt color: Int): Boolean {
    val max = max(
      0.299 * Color.red(color),
      max(0.587 * Color.green(color), 0.114 * Color.blue(color))
    )
    val min = min(
      0.299 * Color.red(color),
      min(0.587 * Color.green(color), 0.114 * Color.blue(color))
    )
    val diff = abs(max - min)
    return diff > 20
  }

  @ColorInt
  fun getMixedColor(@ColorInt color1: Int, @ColorInt color2: Int): Int {
    return Color.rgb(
      (Color.red(color1) + Color.red(color2)) / 2,
      (Color.green(color1) + Color.green(color2)) / 2,
      (Color.blue(color1) + Color.blue(color2)) / 2
    )
  }

  private fun getDifference(@ColorInt color1: Int, @ColorInt color2: Int): Double {
    var diff = abs(0.299 * (Color.red(color1) - Color.red(color2)))
    diff += abs(0.587 * (Color.green(color1) - Color.green(color2)))
    diff += abs(0.114 * (Color.blue(color1) - Color.blue(color2)))
    return diff
  }

  @ColorInt
  fun getReadableText(@ColorInt textColor: Int, @ColorInt backgroundColor: Int): Int {
    return getReadableText(textColor, backgroundColor, 100)
  }

  @ColorInt
  fun getReadableText(
    @ColorInt textColor: Int,
    @ColorInt backgroundColor: Int,
    difference: Int
  ): Int {
    var textColorFinal = textColor
    val isLight = isColorLight(backgroundColor)
    var i = 0
    while (getDifference(textColorFinal, backgroundColor) < difference && i < 100) {
      textColorFinal =
        getMixedColor(textColorFinal, if (isLight) Color.BLACK else Color.WHITE)
      i++
    }

    return textColorFinal
  }

  @ColorInt
  fun getContrastColor(@ColorInt color: Int): Int {
    // Counting the perceptive luminance - human eye favors green color...
    val a =
      1 - (0.299 * Color.red(color) + 0.587 * Color.green(color) + 0.114 * Color.blue(color)) / 255
    return if (a < 0.5) Color.BLACK else Color.WHITE
  }
}
