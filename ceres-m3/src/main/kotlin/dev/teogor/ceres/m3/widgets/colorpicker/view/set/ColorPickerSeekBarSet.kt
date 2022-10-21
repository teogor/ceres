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
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.core.widget.TextViewCompat
import dev.teogor.ceres.m3.widgets.colorpicker.group.PickerGroup
import dev.teogor.ceres.m3.widgets.colorpicker.model.Color

abstract class ColorPickerSeekBarSet<C : Color> @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyle: Int = 0
) : LinearLayout(
  context,
  attrs,
  defStyle
) {

  val pickerGroup = PickerGroup<C>()

  init {
    orientation = VERTICAL
    init(attrs)
  }

  private fun init(attrs: AttributeSet? = null) {
    // val typedArray = context.theme.obtainStyledAttributes(
    //  attrs,
    //  R.styleable.ColorPickerSeekBarSet,
    //  0,
    //  0
    // )

    // typedArray.recycle()
  }

  protected fun addLabel(@StringRes titleResId: Int) {
    addView(
      TextView(
        context
      ).also {
        TextViewCompat.setTextAppearance(
          it,
          defaultTextAppearance
        )
        it.setText(titleResId)
      }
    )
  }

  companion object {
    private const val TAG = "ColorPickerSeekBarSet"
    private const val defaultTextAppearance = android.R.style.TextAppearance_Material_Caption
  }
}
