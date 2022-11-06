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

package dev.teogor.ceres.components.view

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.Gravity
import androidx.annotation.ColorInt
import androidx.appcompat.widget.AppCompatTextView
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.RelativeCornerSize
import com.google.android.material.shape.RoundedCornerTreatment
import com.google.android.material.shape.ShapeAppearanceModel
import dev.teogor.ceres.components.R
import dev.teogor.ceres.extensions.colorStateList

open class DigitsImage @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null
) : AppCompatTextView(context, attrs) {

  private var shape: Shape

  @ColorInt
  private var bgColor: Int

  private var digits: String

  init {
    gravity = Gravity.CENTER

    context.theme.obtainStyledAttributes(
      attrs,
      R.styleable.DigitsImage,
      0,
      0
    ).apply {
      try {
        shape = Shape.values()[
          getInt(
            R.styleable.DigitsImage_shape,
            0
          )
        ]
        bgColor = getColor(R.styleable.DigitsImage_android_background, Color.TRANSPARENT)
        digits = getText(R.styleable.DigitsImage_android_text).toString().substring(0, 2)
      } finally {
        recycle()
      }
    }

    val sam = ShapeAppearanceModel.builder()
    if (shape == Shape.Circle) {
      sam.setAllCorners(RoundedCornerTreatment())
        .setAllCornerSizes(RelativeCornerSize(.5f))
    }
    val msd = MaterialShapeDrawable(sam.build())
    msd.fillColor = bgColor.colorStateList
    background = msd
    text = digits
  }
}
