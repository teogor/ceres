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

package dev.teogor.ceres.extensions.internal.graphics

import androidx.annotation.ColorInt
import androidx.annotation.FloatRange
import dev.teogor.ceres.extensions.*

@Suppress("unused")
object ColorResourceHelper {
  @Deprecated(
    message = "Deprecated in favour of color extension",
    replaceWith = ReplaceWith(
      "color.desaturateColor(ratio)",
      "dev.teogor.ceres.extension.desaturateColor"
    )
  )
  fun desaturateColor(color: Int, ratio: Float): Int {
    return color.desaturateColor(ratio)
  }

  @Deprecated(
    message = "Deprecated in favour of color extension",
    replaceWith =
    ReplaceWith("color.stripAlpha()", "dev.teogor.ceres.extension.stripAlpha")
  )
  fun stripAlpha(@ColorInt color: Int): Int {
    return color.stripAlpha()
  }

  @Deprecated(
    message = "Deprecated in favour of color extension",
    replaceWith =
    ReplaceWith("color.shiftColor(by)", "dev.teogor.ceres.extension.shiftColor")
  )
  @ColorInt
  fun shiftColor(@ColorInt color: Int, @FloatRange(from = 0.0, to = 2.0) by: Float): Int {
    return color.shiftColor(by)
  }

  @Deprecated(
    message = "Deprecated in favour of color extension",
    replaceWith =
    ReplaceWith("color.darkenColor()", "dev.teogor.ceres.extension.darkenColor")
  )
  @ColorInt
  fun darkenColor(@ColorInt color: Int): Int {
    return color.darkenColor()
  }

  @Deprecated(
    message = "Deprecated in favour of color extension",
    replaceWith = ReplaceWith(
      "shiftColor(color, 0.8f)",
      "dev.teogor.ceres.extension.internal.graphics.ColorResourceHelper.shiftColor"
    )
  )
  @ColorInt
  fun darkenColorTheme(@ColorInt color: Int): Int {
    return color.shiftColor(0.8f)
  }

  @Deprecated(
    message = "Deprecated in favour of color extension",
    replaceWith =
    ReplaceWith("color.lightenColor()", "dev.teogor.ceres.extension.lightenColor")
  )
  @ColorInt
  fun lightenColor(@ColorInt color: Int): Int {
    return color.lightenColor()
  }

  @Deprecated(
    message = "Deprecated in favour of color extension",
    replaceWith = ReplaceWith(
      "color.lightenColor(value)",
      "dev.teogor.ceres.extension.lightenColor"
    )
  )
  @ColorInt
  fun lightenColor(
    @ColorInt color: Int,
    value: Float
  ): Int {
    return color.lightenColor(value)
  }

  @Deprecated(
    message = "Deprecated in favour of color extension",
    replaceWith =
    ReplaceWith("color.darkenColor(value)", "dev.teogor.ceres.extension.darkenColor")
  )
  @ColorInt
  fun darkenColor(
    @ColorInt color: Int,
    value: Float
  ): Int {
    return color.darkenColor(value)
  }

  @Deprecated(
    message = "Deprecated in favour of color extension",
    replaceWith = ReplaceWith(
      "color.getReadableColorLight(bgColor)",
      "dev.teogor.ceres.extension.getReadableColorLight"
    )
  )
  @ColorInt
  fun getReadableColorLight(@ColorInt color: Int, @ColorInt bgColor: Int): Int {
    return color.getReadableColorLight(bgColor)
  }

  @Deprecated(
    message = "Deprecated in favour of color extension",
    replaceWith = ReplaceWith(
      "color.getReadableColorDark(bgColor)",
      "dev.teogor.ceres.extension.getReadableColorDark"
    )
  )
  @ColorInt
  fun getReadableColorDark(@ColorInt color: Int, @ColorInt bgColor: Int): Int {
    return color.getReadableColorDark(bgColor)
  }

  @Deprecated(
    message = "Deprecated in favour of color extension",
    replaceWith =
    ReplaceWith("color.isColorLight()", "dev.teogor.ceres.extension.isColorLight")
  )
  fun isColorLight(@ColorInt color: Int): Boolean {
    return color.isColorLight()
  }

  @Deprecated(
    message = "Deprecated in favour of color extension",
    replaceWith =
    ReplaceWith("color.invertColor()", "dev.teogor.ceres.extension.invertColor")
  )
  @ColorInt
  fun invertColor(@ColorInt color: Int): Int {
    return color.invertColor()
  }

  @ColorInt
  @Deprecated(
    message = "Deprecated in favour of color extension",
    replaceWith =
    ReplaceWith("color.adjustAlpha(factor)", "dev.teogor.ceres.extension.adjustAlpha")
  )
  fun adjustAlpha(@ColorInt color: Int, @FloatRange(from = 0.0, to = 1.0) factor: Float): Int {
    return color.adjustAlpha(factor)
  }

  @ColorInt
  @Deprecated(
    message = "Deprecated in favour of color extension",
    replaceWith =
    ReplaceWith("baseColor.addAlpha(alpha)", "dev.teogor.ceres.extension.addAlpha")
  )
  fun addAlpha(@ColorInt baseColor: Int, @FloatRange(from = 0.0, to = 1.0) alpha: Float): Int {
    return baseColor.addAlpha(alpha)
  }

  @Deprecated(
    message = "Deprecated in favour of color extension",
    replaceWith = ReplaceWith(
      "color.getColorDarkness()",
      "dev.teogor.ceres.extension.getColorDarkness"
    )
  )
  private fun getColorDarkness(@ColorInt color: Int): Double {
    return color.getColorDarkness()
  }

  @Deprecated(
    message = "Deprecated in favour of color extension",
    replaceWith = ReplaceWith(
      "color.getInverseColor()",
      "dev.teogor.ceres.extension.getInverseColor"
    )
  )
  @ColorInt
  fun getInverseColor(@ColorInt color: Int): Int {
    return color.getInverseColor()
  }

  @Deprecated(
    message = "Deprecated in favour of color extension",
    replaceWith = ReplaceWith(
      "color.isColorSaturated()",
      "dev.teogor.ceres.extension.isColorSaturated"
    )
  )
  fun isColorSaturated(@ColorInt color: Int): Boolean {
    return color.isColorSaturated()
  }
}
