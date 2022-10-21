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

package dev.teogor.ceres.m3.widgets.colorpicker.converter

import dev.teogor.ceres.m3.widgets.colorpicker.model.Color
import dev.teogor.ceres.m3.widgets.colorpicker.model.IntegerCMYKColor

class IntegerCMYKColorConverter : ColorConverter {

  override fun convertToOpaqueColorInt(color: Color): Int {
    TODO("Not yet implemented")
  }

  override fun convertToColorInt(color: Color): Int {
    require(color is IntegerCMYKColor) { "Unsupported color type supplied" }

    val r = 255f * (1f - color.floatC) * (1f - color.floatK)
    val g = 255f * (1f - color.floatM) * (1f - color.floatK)
    val b = 255f * (1f - color.floatY) * (1f - color.floatK)

    return android.graphics.Color.rgb(
      r.toInt(),
      g.toInt(),
      b.toInt()
    )
  }

  override fun convertToPureHueColorInt(color: Color): Int {
    TODO("Not yet implemented")
  }

  override fun setFromColorInt(color: Color, value: Int) {
    require(color is IntegerCMYKColor) { "Unsupported color type supplied" }

    val r = android.graphics.Color.red(value) / 255f
    val g = android.graphics.Color.green(value) / 255f
    val b = android.graphics.Color.blue(value) / 255f

    val k = 1f - requireNotNull(
      arrayOf(
        r,
        g,
        b
      ).maxOrNull()
    )
    val c = (1f - r - k) / (1f - k)
    val m = (1f - g - k) / (1f - k)
    val y = (1f - b - k) / (1f - k)

    color.copyValuesFrom(
      intArrayOf(
        (c * 100f).toInt(),
        (m * 100f).toInt(),
        (y * 100f).toInt(),
        (k * 100f).toInt()
      )
    )
  }
}
