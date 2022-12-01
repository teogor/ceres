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
import androidx.appcompat.widget.AppCompatImageView
import dev.teogor.ceres.components.view.Shape
import dev.teogor.ceres.extensions.colorStateList
import dev.teogor.ceres.m3.beta.Beta
import dev.teogor.ceres.m3.elevation.SurfaceLevel
import dev.teogor.ceres.m3.theme.IThemeM3
import dev.teogor.ceres.m3.theme.getBackgroundDrawable

class ImageViewM3 constructor(
  context: Context,
  attrs: AttributeSet
) : AppCompatImageView(context, attrs), IThemeM3 {

  private val surfaceLevel: SurfaceLevel
  private var rippleEnabled: Boolean
  private val imageTintColorM3: ColorM3

  init {
    context.theme.obtainStyledAttributes(
      attrs,
      R.styleable.ImageViewM3,
      0,
      0
    ).apply {
      try {
        surfaceLevel = SurfaceLevel.values()[
          getInt(
            R.styleable.ImageViewM3_surface_level,
            0
          )
        ]
        imageTintColorM3 = ColorM3.values()[
          getInt(
            R.styleable.ImageViewM3_color_m3,
            0
          )
        ]

        val rippleEnabledAttr = getBoolean(
          R.styleable.ImageViewM3_ripple_enabled,
          isClickable
        )
        rippleEnabled = if (isClickable && !rippleEnabledAttr) {
          false
        } else if (isClickable) {
          true
        } else {
          rippleEnabledAttr
        }
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

    background = getBackgroundDrawable(
      backgroundDrawable = Beta.BackgroundDrawable(
        background = Beta.BackgroundData.Transparent,
        foreground = Beta.BackgroundData(
          color = ColorM3.Primary,
          surfaceTintOverlay = ColorM3.Surface,
          surfaceTint = .2f
        ),
        rippleEnabled = rippleEnabled,
        shape = Shape.Circle
      )
    )

    imageTintList = getColorM3(imageTintColorM3).colorStateList
  }
}
