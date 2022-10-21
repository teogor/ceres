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
import androidx.annotation.ColorInt
import androidx.annotation.FloatRange
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.ShapeAppearanceModel
import dev.teogor.ceres.extensions.blendColors
import dev.teogor.ceres.extensions.colorStateList
import dev.teogor.ceres.extensions.isColorLight
import dev.teogor.ceres.m3.theme.ColorScheme
import dev.teogor.ceres.m3.theme.SurfaceOverlay
import dev.teogor.ceres.m3.theme.ThemeM3

class ShapeDrawableM3 : MaterialShapeDrawable {

  private fun getSchemeColor(): ColorScheme {
    return ThemeM3.currentColorScheme()
  }

  @ColorInt
  private fun getColorM3(colorM3: ColorM3): Int {
    return when (colorM3) {
      ColorM3.Primary -> getSchemeColor().primary
      ColorM3.OnPrimary -> getSchemeColor().onPrimary
      ColorM3.PrimaryContainer -> getSchemeColor().primaryContainer
      ColorM3.OnPrimaryContainer -> getSchemeColor().onPrimaryContainer
      ColorM3.InversePrimary -> getSchemeColor().inversePrimary
      ColorM3.Secondary -> getSchemeColor().secondary
      ColorM3.OnSecondary -> getSchemeColor().onSecondary
      ColorM3.SecondaryContainer -> getSchemeColor().secondaryContainer
      ColorM3.OnSecondaryContainer -> getSchemeColor().onSecondaryContainer
      ColorM3.Tertiary -> getSchemeColor().tertiary
      ColorM3.OnTertiary -> getSchemeColor().onTertiary
      ColorM3.TertiaryContainer -> getSchemeColor().tertiaryContainer
      ColorM3.OnTertiaryContainer -> getSchemeColor().onTertiaryContainer
      ColorM3.Background -> getSchemeColor().background
      ColorM3.OnBackground -> getSchemeColor().onBackground
      ColorM3.Surface -> getSchemeColor().surface
      ColorM3.OnSurface -> getSchemeColor().onSurface
      ColorM3.SurfaceVariant -> getSchemeColor().surfaceVariant
      ColorM3.OnSurfaceVariant -> getSchemeColor().onSurfaceVariant
      ColorM3.SurfaceTint -> getSchemeColor().surfaceTint
      ColorM3.InverseSurface -> getSchemeColor().inverseSurface
      ColorM3.InverseOnSurface -> getSchemeColor().inverseOnSurface
      ColorM3.Error -> getSchemeColor().error
      ColorM3.OnError -> getSchemeColor().onError
      ColorM3.ErrorContainer -> getSchemeColor().errorContainer
      ColorM3.OnErrorContainer -> getSchemeColor().onErrorContainer
      ColorM3.Outline -> getSchemeColor().outline
      ColorM3.OutlineVariant -> getSchemeColor().outlineVariant
      ColorM3.Scrim -> getSchemeColor().scrim

      ColorM3.OnPrimaryMenu -> blendColors(
        getSchemeColor().primary,
        getSchemeColor().onBackground,
        0.9f
      )
    }
  }

  private var backgroundColor: ColorM3 = ColorM3.Background

  @FloatRange(from = 0.0, to = 1.0)
  private var surfaceTint: Float = 0f
  private var surfaceTintOverlay: ColorM3 = ColorM3.Primary

  constructor() : super()

  constructor(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int,
    defStyleRes: Int
  ) : super(context, attrs, defStyleAttr, defStyleRes)

  constructor(shapeAppearanceModel: ShapeAppearanceModel) : super(shapeAppearanceModel)

  private constructor(
    shapeAppearanceModel: ShapeAppearanceModel,
    backgroundColor: ColorM3,
    surfaceTint: Float,
    surfaceTintOverlay: ColorM3
  ) : super(shapeAppearanceModel) {
    this.backgroundColor = backgroundColor
    this.surfaceTint = surfaceTint
    this.surfaceTintOverlay = surfaceTintOverlay

    computeBackgroundColor()
  }

  private fun computeBackgroundColor() {
    fillColor = SurfaceOverlay(
      themeOverlayColor = getColorM3(backgroundColor),
      themeSurfaceColor = getColorM3(surfaceTintOverlay)
    ).forAlpha(surfaceTint).colorStateList
  }

  fun isBackgroundLight(): Boolean {
    return SurfaceOverlay(
      themeOverlayColor = getColorM3(backgroundColor),
      themeSurfaceColor = getColorM3(surfaceTintOverlay)
    ).forAlpha(surfaceTint).isColorLight()
  }

  /** Returns a builder with the edges and corners from this [ShapeDrawableM3]  */
  fun toBuilder(): Builder {
    return Builder(shapeAppearanceModel)
  }

  /** Builder to create instances of [ShapeDrawableM3]s.  */
  data class Builder(
    private var shapeAppearanceModel: ShapeAppearanceModel,
    private var backgroundColor: ColorM3 = ColorM3.Background,
    @FloatRange(from = 0.0, to = 1.0)
    private var surfaceTint: Float = 0f,
    private var surfaceTintOverlay: ColorM3 = ColorM3.Primary
  ) {

    fun backgroundColor(colorM3: ColorM3) = apply { this.backgroundColor = colorM3 }

    fun surfaceTint(alpha: Float) = apply { this.surfaceTint = alpha }

    fun surfaceTintOverlay(colorM3: ColorM3) = apply { this.surfaceTintOverlay = colorM3 }

    fun build() = ShapeDrawableM3(
      shapeAppearanceModel,
      backgroundColor,
      surfaceTint,
      surfaceTintOverlay
    )
  }
}
