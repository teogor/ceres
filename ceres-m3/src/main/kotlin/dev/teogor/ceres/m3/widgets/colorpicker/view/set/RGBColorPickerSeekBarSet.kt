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

package dev.teogor.ceres.m3.widgets.colorpicker.view.set

import android.content.Context
import android.util.AttributeSet
import dev.teogor.ceres.m3.widgets.colorpicker.model.IntegerRGBColor
import dev.teogor.ceres.m3.widgets.colorpicker.rgb.RGBColorPickerSeekBar

class RGBColorPickerSeekBarSet @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyle: Int = 0
) : ColorPickerSeekBarSet<IntegerRGBColor>(
  context,
  attrs,
  defStyle
) {

  init {
    RGBColorPickerSeekBar.Mode.values().forEach { rgbMode ->
      addLabel(rgbMode.nameStringResId)
      val picker =
        RGBColorPickerSeekBar(
          context
        ).also {
          it.mode = rgbMode
        }
      pickerGroup.registerPicker(picker)
      addView(picker)
    }
  }
}
