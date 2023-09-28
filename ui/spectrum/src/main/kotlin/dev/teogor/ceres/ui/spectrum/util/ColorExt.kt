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

package dev.teogor.ceres.ui.spectrum.util

import android.content.res.ColorStateList
import android.graphics.Color
import androidx.annotation.ColorInt
import androidx.annotation.FloatRange
import androidx.core.graphics.ColorUtils
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min
import kotlin.random.Random

@ColorInt
fun randomColor(
  @FloatRange(
    from = 0.0,
    to = 1.0,
  ) alpha: Float = 1f,
): Int {
  val rnd = Random
  val a = min(255, max(0, (alpha * 255).toInt())) shl 24
  return Color.argb(
    a,
    rnd.nextInt(256),
    rnd.nextInt(256),
    rnd.nextInt(256),
  )
}

fun @receiver:ColorInt Int.stripAlpha(): Int {
  return -0x1000000 or this
}

fun @receiver:ColorInt Int.addAlpha(
  @FloatRange(
    from = 0.0,
    to = 1.0,
  ) alpha: Float,
): Int {
  val a = min(255, max(0, (alpha * 255).toInt())) shl 24
  val rgb = 0x00ffffff and this
  return a + rgb
}

@ColorInt
fun @receiver:ColorInt Int.adjustAlpha(
  @FloatRange(
    from = 0.0,
    to = 1.0,
  ) alpha: Float,
): Int {
  val a = min(255, max(0, (alpha * 255).toInt())) shl 24
  val rgb = 0x00ffffff and this
  return a + rgb
}

@ColorInt
fun @receiver:ColorInt Int.invertColor(): Int {
  val r = 255 - Color.red(this)
  val g = 255 - Color.green(this)
  val b = 255 - Color.blue(this)
  return Color.argb(Color.alpha(this), r, g, b)
}

fun @receiver:ColorInt Int.isColorLight(): Boolean {
  val darkness =
    1 - (0.299 * Color.red(this) + 0.587 * Color.green(this) + 0.114 * Color.blue(this)) / 255
  return darkness < 0.4
}

@ColorInt
fun @receiver:ColorInt Int.getReadableColorDark(@ColorInt bgColor: Int): Int {
  var foregroundColor = this
  while (ColorUtils.calculateContrast(foregroundColor, bgColor) <= 3.0
  ) {
    foregroundColor = foregroundColor.lightenColor(0.1F)
  }
  return foregroundColor
}

@ColorInt
fun @receiver:ColorInt Int.getReadableColorLight(@ColorInt bgColor: Int): Int {
  var foregroundColor = this
  while (ColorUtils.calculateContrast(foregroundColor, bgColor) <= 3.0
  ) {
    foregroundColor = foregroundColor.darkenColor(0.1F)
  }
  return foregroundColor
}

@ColorInt
fun @receiver:ColorInt Int.lightenColor(
  value: Float = 1.1f,
): Int {
  val hsl = FloatArray(3)
  ColorUtils.colorToHSL(this, hsl)
  hsl[2] += value
  hsl[2] = hsl[2].coerceIn(0f, 1f)
  return ColorUtils.HSLToColor(hsl)
}

@ColorInt
fun @receiver:ColorInt Int.darkenColor(
  value: Float = .9f,
): Int {
  val hsl = FloatArray(3)
  ColorUtils.colorToHSL(this, hsl)
  hsl[2] -= value
  hsl[2] = hsl[2].coerceIn(0f, 1f)
  return ColorUtils.HSLToColor(hsl)
}

@ColorInt
fun @receiver:ColorInt Int.shiftColor(
  @FloatRange(from = 0.0, to = 2.0) by: Float,
): Int {
  if (by == 1f) return this
  val alpha = Color.alpha(this)
  val hsv = FloatArray(3)
  Color.colorToHSV(this, hsv)
  hsv[2] *= by // value component
  return (alpha shl 24) + (0x00ffffff and Color.HSVToColor(hsv))
}

@ColorInt
fun @receiver:ColorInt Int.desaturateColor(
  ratio: Float,
): Int {
  val hsv = FloatArray(3)
  Color.colorToHSV(this, hsv)

  hsv[1] = hsv[1] / 1 * ratio + 0.2f * (1.0f - ratio)

  return Color.HSVToColor(hsv)
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

fun @receiver:ColorInt Int.getColorDarkness(): Double {
  return if (this == Color.BLACK) {
    1.0
  } else if (this == Color.WHITE || this == Color.TRANSPARENT) {
    0.0
  } else {
    1 - (0.299 * Color.red(this) + 0.587 * Color.green(this) + 0.114 * Color.blue(this)) / 255
  }
}

fun @receiver:ColorInt Int.getInverseColor(): Int {
  return 0xFFFFFF - this or -0x1
}

@ColorInt
fun @receiver:ColorInt Int.getContrastColor(): Int {
  // Counting the perceptive luminance - human eye favors green color...
  val a =
    1 - (0.299 * Color.red(this) + 0.587 * Color.green(this) + 0.114 * Color.blue(this)) / 255
  return if (a < 0.5) Color.BLACK else Color.WHITE
}

fun @receiver:ColorInt Int.isColorSaturated(): Boolean {
  val max = max(
    0.299 * Color.red(this),
    max(0.587 * Color.green(this), 0.114 * Color.blue(this)),
  )
  val min = min(
    0.299 * Color.red(this),
    min(0.587 * Color.green(this), 0.114 * Color.blue(this)),
  )
  val diff = abs(max - min)
  return diff > 20
}

@ColorInt
fun getMixedColor(@ColorInt color1: Int, @ColorInt color2: Int): Int {
  return Color.rgb(
    (Color.red(color1) + Color.red(color2)) / 2,
    (Color.green(color1) + Color.green(color2)) / 2,
    (Color.blue(color1) + Color.blue(color2)) / 2,
  )
}

fun getDifference(@ColorInt color1: Int, @ColorInt color2: Int): Double {
  var diff = abs(0.299 * (Color.red(color1) - Color.red(color2)))
  diff += abs(0.587 * (Color.green(color1) - Color.green(color2)))
  diff += abs(0.114 * (Color.blue(color1) - Color.blue(color2)))
  return diff
}

@ColorInt
fun getReadableText(
  @ColorInt textColor: Int,
  @ColorInt backgroundColor: Int,
  difference: Int = 100,
): Int {
  var textColorFinal = textColor
  val isLight = backgroundColor.isColorLight()
  var i = 0
  while (getDifference(textColorFinal, backgroundColor) < difference && i < 100) {
    textColorFinal =
      getMixedColor(textColorFinal, if (isLight) Color.BLACK else Color.WHITE)
    i++
  }

  return textColorFinal
}

inline val @receiver:ColorInt Int.colorStateList: ColorStateList
  get() = ColorStateList.valueOf(this)
