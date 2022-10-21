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

package dev.teogor.ceres.ads

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.util.TypedValue
import androidx.appcompat.widget.AppCompatTextView
import dev.teogor.ceres.extensions.dpToPx
import dev.teogor.ceres.extensions.spToPx

class AdView @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

  private val gd = GradientDrawable()

  fun setColor(color: Int) {
    setTextColor(color)
    // todo background set
    setBackground(
      if (isColorDark(color)) Color.parseColor("#ffffff") else Color.parseColor("#424242")
    )
    gd.setStroke(
      1.dpToPx,
      color
    )
    background = gd
  }

  private fun isColorDark(color: Int): Boolean {
    val red = 0.299 * Color.red(color)
    val green = 0.587 * Color.green(color)
    val blue = 0.114 * Color.blue(color)
    val darkness = 1 - (red + green + blue) / 255
    return darkness >= 0.5
  }

  private fun setBackground(color: Int) {
    gd.setColor(color)
    background = gd
  }

  init {
    val color = Color.parseColor("#000000")
    text = "AD"
    setTextSize(TypedValue.COMPLEX_UNIT_SP, 3.spToPx.toFloat())
    // setTypeface(ResourcesCompat.getFont(context, R.font.font_bold))
    gd.cornerRadius = 4.dpToPx.toFloat()
    setColor(color)
    setPadding(
      4.dpToPx,
      2.dpToPx,
      4.dpToPx,
      2.dpToPx
    )
  }
}
