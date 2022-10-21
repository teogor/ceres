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

import androidx.core.graphics.ColorUtils
import dev.teogor.ceres.m3.widgets.colorpicker.model.Color
import dev.teogor.ceres.m3.widgets.colorpicker.model.IntegerLABColor

class IntegerLABColorConverter : ColorConverter {

  override fun convertToOpaqueColorInt(color: Color): Int {
    TODO("Not yet implemented")
  }

  override fun convertToColorInt(color: Color): Int {
    require(color is IntegerLABColor) { "Unsupported color type supplied" }

    return ColorUtils.LABToColor(
      color.intL.toDouble(),
      color.intA.toDouble(),
      color.intB.toDouble()
    )
  }

  override fun convertToPureHueColorInt(color: Color): Int {
    TODO("Not yet implemented")
  }

  override fun setFromColorInt(color: Color, value: Int) {
    require(color is IntegerLABColor) { "Unsupported color type supplied" }
    TODO("Not yet implemented")
  }
}
