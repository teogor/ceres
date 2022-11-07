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

package dev.teogor.ceres.m3.widgets.bar

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import androidx.annotation.ColorInt
import androidx.constraintlayout.widget.ConstraintLayout
import dev.teogor.ceres.m3.ColorM3
import dev.teogor.ceres.m3.R
import dev.teogor.ceres.m3.theme.IThemeM3
import dev.teogor.ceres.m3.theme.SurfaceOverlay

open class Bar constructor(
  context: Context,
  attrs: AttributeSet
) : ConstraintLayout(context, attrs), IThemeM3 {

  @ColorInt
  internal val colorTransparent = Color.TRANSPARENT

  @ColorInt
  internal fun colorSurfaceNormal() = SurfaceOverlay(
    themeOverlayColor = dev.teogor.ceres.m3.extension.getColorM3(ColorM3.Surface),
    themeSurfaceColor = dev.teogor.ceres.m3.extension.getColorM3(ColorM3.SurfaceTint)
  ).forAlpha(.05f)

  @ColorInt
  internal fun colorSurfaceFilled() = SurfaceOverlay(
    themeOverlayColor = dev.teogor.ceres.m3.extension.getColorM3(ColorM3.Surface),
    themeSurfaceColor = dev.teogor.ceres.m3.extension.getColorM3(ColorM3.SurfaceTint)
  ).forAlpha(.11f)

  internal val consumeTop: Boolean

  init {
    context.theme.obtainStyledAttributes(
      attrs,
      R.styleable.Bar,
      0,
      0
    ).apply {
      try {
        consumeTop = getBoolean(R.styleable.Bar_consumeTop, false)
      } finally {
        recycle()
      }
    }
  }
}
