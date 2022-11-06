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
import androidx.annotation.FloatRange
import androidx.annotation.Px
import androidx.constraintlayout.widget.ConstraintLayout
import dev.teogor.ceres.extensions.defaultResId
import dev.teogor.ceres.m3.elevation.SurfaceLevel
import dev.teogor.ceres.m3.theme.ThemeHandler

open class ContainerBaseM3 constructor(
  context: Context,
  attrs: AttributeSet
) : ConstraintLayout(context, attrs), ThemeHandler {

  val backgroundColorM3: ColorM3
  val surfaceTintOverlay: ColorM3

  val surfaceLevel: SurfaceLevel

  @Px
  @Dimension
  val cornerRadius: Float

  var rippleEnabled: Boolean

  @FloatRange(from = 0.0, to = 1.0)
  val surfaceTint: Float

  open val customBackground = false

  init {
    context.theme.obtainStyledAttributes(
      attrs,
      R.styleable.ContainerBaseM3,
      0,
      0
    ).apply {
      try {
        surfaceLevel = SurfaceLevel.values()[
          getInt(
            R.styleable.ContainerBaseM3_surface_level,
            0
          )
        ]
        val backgroundM3 = getInt(
          R.styleable.ContainerBaseM3_background_m3,
          defaultResId
        )
        backgroundColorM3 = if (backgroundM3 != -1) {
          ColorM3.values()[backgroundM3]
        } else {
          ColorM3.OnBackground
        }
        surfaceTint = getFloat(R.styleable.ContainerBaseM3_surface_tint, 0f)
        surfaceTintOverlay = ColorM3.values()[
          getInt(
            R.styleable.ContainerBaseM3_surface_tint_overlay,
            0
          )
        ]
        cornerRadius = getDimension(R.styleable.ContainerBaseM3_corner_radius, 0f)
        rippleEnabled = getBoolean(
          R.styleable.ContainerBaseM3_ripple_enabled,
          false
        )
      } finally {
        recycle()
      }
    }
    if (!isInEditMode) {
      // todo fixme
      onThemeChanged()
    }
    if (rippleEnabled) {
      isClickable = true
      isFocusable = true
    }
  }

  override fun onThemeChanged() {
    super.onThemeChanged()

    defaultTheme()
  }

  private fun defaultTheme() {
    val materialShapeDrawable = getMaterialShapeDrawable(
      view = this,
      surfaceLevel = surfaceLevel,
      cornerSize = cornerRadius
    )

    if (!customBackground) {
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

  override fun setEnabled(isEnabled: Boolean) {
  }
}
