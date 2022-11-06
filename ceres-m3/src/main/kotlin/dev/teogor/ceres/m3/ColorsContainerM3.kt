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

package dev.teogor.ceres.m3

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.annotation.ColorInt
import androidx.core.content.res.ResourcesCompat
import dev.teogor.ceres.extensions.defaultResId
import dev.teogor.ceres.extensions.show
import dev.teogor.ceres.extensions.validFontRes
import dev.teogor.ceres.m3.databinding.Lm3ColorsContainerBinding
import dev.teogor.ceres.m3.widgets.colorpicker.converter.setFromColorInt
import dev.teogor.ceres.m3.widgets.colorpicker.group.PickerGroup
import dev.teogor.ceres.m3.widgets.colorpicker.group.registerPickers
import dev.teogor.ceres.m3.widgets.colorpicker.model.IntegerHSLColor
import dev.teogor.ceres.m3.widgets.colorpicker.view.picker.ColorSeekBar
import dev.teogor.ceres.m3.widgets.colorpicker.view.picker.OnIntegerHSLColorPickListener

class ColorsContainerM3 constructor(
  context: Context,
  attrs: AttributeSet
) : ContainerBaseM3(context, attrs) {

  private val binding: Lm3ColorsContainerBinding

  private val fontId: Int
  private val group: PickerGroup<IntegerHSLColor>

  var onColorPickListener: OnColorPickListener? = null

  var title: String? = null
    set(value) {
      field = value
      setTitle()
    }

  @ColorInt
  var color: Int = 0
    set(value) {
      field = value
      setColor()
    }

  private fun setTitle() {
    binding.textTitle.show(title != null)
    binding.textTitle.text = title
  }

  private fun setColor() {
    group.setColor(
      IntegerHSLColor().also { itHsl ->
        itHsl.setFromColorInt(color)
      }
    )
  }

  init {
    // layout binding
    binding = Lm3ColorsContainerBinding.inflate(
      LayoutInflater.from(context),
      this,
      true
    )

    // attrs initialization
    context.theme.obtainStyledAttributes(
      attrs,
      R.styleable.ColorsContainerM3,
      0,
      0
    ).apply {
      try {
        fontId = getResourceId(R.styleable.ColorsContainerM3_android_fontFamily, defaultResId)
        title = getString(R.styleable.ColorsContainerM3_title)
      } finally {
        recycle()
      }
    }

    // font family initialization
    fontId.validFontRes {
      val typeface = ResourcesCompat.getFont(context, this)
      binding.textTitle.typeface = typeface
    }

    group = PickerGroup<IntegerHSLColor>().also {
      it.registerPickers(
        binding.hueSeekBar
      )
    }

    group.addListener(object : OnIntegerHSLColorPickListener() {
      override fun onColorChanged(
        picker: ColorSeekBar<IntegerHSLColor>,
        color: IntegerHSLColor,
        value: Int
      ) {
        onColorPickListener?.onColorChanged(color)
      }
    })
  }

  override fun setEnabled(isEnabled: Boolean) {
    super.setEnabled(isEnabled)

    binding.hueSeekBar.isEnabled = isEnabled
    binding.textTitle.isEnabled = isEnabled
    binding.container.isEnabled = isEnabled
    if (isEnabled) {
      binding.hueSeekBar.alpha = 1f
      binding.textTitle.alpha = 1f
    } else {
      binding.hueSeekBar.alpha = .2f
      binding.textTitle.alpha = .2f
    }
  }

  /**
   * Interface definition for a callback to be invoked when the element was
   * clicked.
   */
  interface OnColorPickListener {
    /**
     * Called when the element was clicked.
     */
    fun onColorChanged(color: IntegerHSLColor)
  }
}
