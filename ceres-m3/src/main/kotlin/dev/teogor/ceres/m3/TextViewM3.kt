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
import com.google.android.material.textview.MaterialTextView
import dev.teogor.ceres.m3.elevation.SurfaceLevel
import dev.teogor.ceres.m3.theme.ThemeHandler

class TextViewM3 : MaterialTextView, ThemeHandler {

  private val surfaceLevel: SurfaceLevel
  private val textColorM3: ColorM3
  private val rippleEnabled: Boolean
  private val cornerRadius: Float

  constructor(context: Context) : this(context, null)

  constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

  constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
    context,
    attrs,
    defStyleAttr
  ) {
    context.theme.obtainStyledAttributes(
      attrs,
      R.styleable.TextViewM3,
      0,
      0
    ).apply {
      try {
        textColorM3 = ColorM3.values()[
          getInt(
            R.styleable.TextViewM3_color_m3,
            0
          )
        ]
        surfaceLevel = SurfaceLevel.values()[
          getInt(
            R.styleable.TextViewM3_surface_level,
            0
          )
        ]
        rippleEnabled = getBoolean(
          R.styleable.TextViewM3_ripple_enabled,
          false
        )
        cornerRadius = getDimension(R.styleable.TextViewM3_corner_radius, 0f)
      } finally {
        recycle()
      }
    }

    if (!isInEditMode) {
      onThemeChanged()
    }
    if (rippleEnabled) {
      isClickable = true
      isFocusable = true
    }
  }

  override fun onThemeChanged() {
    super.onThemeChanged()

    setTextColor(getColorM3(colorM3 = textColorM3))
    val materialShapeDrawable = getMaterialShapeDrawable(
      view = this,
      surfaceLevel = surfaceLevel,
      cornerSize = cornerRadius
    )

    background = if (rippleEnabled) {
      getRippleDrawable(
        content = materialShapeDrawable,
        mask = getMaterialShapeDrawable(
          view = this,
          surfaceLevel = SurfaceLevel.Lvl0,
          cornerSize = cornerRadius
        )
      )
    } else {
      materialShapeDrawable
    }
  }
}
