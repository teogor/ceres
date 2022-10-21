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
import androidx.annotation.Dimension
import androidx.annotation.Px
import dev.teogor.ceres.components.view.Button
import dev.teogor.ceres.m3.elevation.SurfaceLevel
import dev.teogor.ceres.m3.theme.ThemeHandler

class ButtonM3 @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null
) : Button(context, attrs), ThemeHandler {

  private val surfaceLevel: SurfaceLevel
  private var rippleEnabled: Boolean

  private val backgroundColorM3: ColorM3
  private val textColorM3: ColorM3

  @Px
  @Dimension
  val cornerRadius: Float

  init {
    context.theme.obtainStyledAttributes(
      attrs,
      R.styleable.ButtonM3,
      0,
      0
    ).apply {
      try {
        surfaceLevel = SurfaceLevel.values()[
          getInt(
            R.styleable.ButtonM3_surface_level,
            0
          )
        ]
        backgroundColorM3 = ColorM3.values()[
          getInt(
            R.styleable.ButtonM3_background_m3,
            0
          )
        ]
        textColorM3 = ColorM3.values()[
          getInt(
            R.styleable.ButtonM3_text_color_m3,
            0
          )
        ]
        rippleEnabled = getBoolean(
          R.styleable.ButtonM3_ripple_enabled,
          false
        )
        cornerRadius = getDimension(R.styleable.ButtonM3_corner_radius, 0f)
      } finally {
        recycle()
      }
    }
    if (!isInEditMode) {
      onThemeChanged()
    }
  }

  override fun onThemeChanged() {
    super.onThemeChanged()

    setTextColor(getColorM3(colorM3 = textColorM3))

    val materialShapeDrawable = getBackgroundDrawable(
      cornerSize = cornerRadius,
      backgroundColor = backgroundColorM3
    )

    // materialShapeDrawable.fillColor = getColorM3(colorM3 = backgroundColorM3).colorStateList
    background = if (rippleEnabled) {
      getRippleDrawable(
        content = materialShapeDrawable,
        mask = getBackgroundDrawable(
          cornerSize = cornerRadius,
          backgroundColor = backgroundColorM3
        )
      )
    } else {
      materialShapeDrawable
    }

    background = getBackgroundDrawable(
      cornerSize = cornerRadius,
      backgroundColor = backgroundColorM3,
      rippleEnabled = rippleEnabled
    )

//        background = getRippleDrawable(
//            content = materialShapeDrawable,
//            mask = getBackgroundDrawable(
//                cornerSize = cornerRadius,
//                backgroundColor = backgroundColorM3,
//            ),
//        )
  }
}
