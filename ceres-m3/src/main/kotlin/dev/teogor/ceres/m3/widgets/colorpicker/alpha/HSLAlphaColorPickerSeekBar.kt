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

package dev.teogor.ceres.m3.widgets.colorpicker.alpha

import android.content.Context
import android.util.AttributeSet
import dev.teogor.ceres.m3.R
import dev.teogor.ceres.m3.widgets.colorpicker.converter.IntegerHSLColorConverter
import dev.teogor.ceres.m3.widgets.colorpicker.model.IntegerHSLColor
import dev.teogor.ceres.m3.widgets.colorpicker.model.factory.HSLColorFactory

// TODO: Add modes support
class HSLAlphaColorPickerSeekBar @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyle: Int = R.attr.seekBarStyle
) :
  AlphaColorPickerSeekBar<IntegerHSLColor>(
    HSLColorFactory(),
    context,
    attrs,
    defStyle
  ) {

  private var isInitialized = false

  override val colorConverter: IntegerHSLColorConverter
    get() = super.colorConverter as IntegerHSLColorConverter

  init {
    refreshProperties()
    isInitialized = true
  }

  override fun setMax(max: Int) {
    if (isInitialized && max != IntegerHSLColor.Component.A.maxValue) {
      throw IllegalArgumentException("Current mode supports ${IntegerHSLColor.Component.A.maxValue} max value only, was $max")
    }
    super.setMax(max)
  }

  override fun onUpdateColorFrom(color: IntegerHSLColor, value: IntegerHSLColor) {
    color.setFrom(value)
  }

  override fun onRefreshProperties() {
    max = IntegerHSLColor.Component.A.maxValue
  }

  override fun onRefreshProgressFromColor(color: IntegerHSLColor): Int {
    return color.intA
  }

  override fun onRefreshColorFromProgress(color: IntegerHSLColor, progress: Int): Boolean {
    val currentA = internalPickedColor.intA
    return if (currentA != progress) {
      internalPickedColor.intA = progress
      true
    } else {
      false
    }
  }
}
