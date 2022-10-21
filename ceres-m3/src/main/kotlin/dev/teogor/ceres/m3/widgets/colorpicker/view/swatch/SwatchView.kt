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

package dev.teogor.ceres.m3.widgets.colorpicker.view.swatch

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.LayerDrawable
import android.util.AttributeSet
import android.view.View
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import dev.teogor.ceres.m3.R
import dev.teogor.ceres.m3.widgets.colorpicker.converter.ColorConverterHub
import dev.teogor.ceres.m3.widgets.colorpicker.model.Color

class SwatchView @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : View(
  context,
  attrs,
  defStyleAttr
) {

  private val patternDrawable =
    requireNotNull(
      ContextCompat.getDrawable(
        context,
        R.drawable.bg_transparency_pattern
      )
    )

  init {
    background = LayerDrawable(
      arrayOf(
        patternDrawable,
        ColorDrawable()
      )
    )
  }

  fun setSwatchColor(color: Color) {
    ((background as LayerDrawable).getDrawable(1) as ColorDrawable).color =
      ColorConverterHub.getConverterByKey(color.colorKey).convertToColorInt(color)
  }

  fun setSwatchPatternTint(@ColorInt tintColor: Int) {
    DrawableCompat.setTint(
      patternDrawable,
      tintColor
    )
  }
}
