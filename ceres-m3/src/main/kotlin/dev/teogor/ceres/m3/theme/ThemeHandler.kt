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

package dev.teogor.ceres.m3.theme

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.RippleDrawable
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.Dimension
import com.google.android.material.shape.CornerFamily
import com.zeoflow.startup.ktx.ApplicationInitializer
import dev.teogor.ceres.core.logger.Logger
import dev.teogor.ceres.extensions.addAlpha
import dev.teogor.ceres.extensions.blendColors
import dev.teogor.ceres.extensions.colorStateList
import dev.teogor.ceres.m3.ColorM3
import dev.teogor.ceres.m3.drawable.RippleDrawableM3
import dev.teogor.ceres.m3.drawable.ShapeAppearanceModelM3
import dev.teogor.ceres.m3.drawable.ShapeDrawableM3
import dev.teogor.ceres.m3.drawable.toBuilder
import dev.teogor.ceres.m3.elevation.SurfaceLevel

interface ThemeHandler : Logger {

  fun onThemeChanged() {
  }

  fun getSchemeColor(): ColorScheme {
    return ThemeM3.currentColorScheme()
  }

  @ColorInt
  fun getBackgroundColorM3(view: View, surfaceLevel: SurfaceLevel): Int = if (view.isInEditMode) {
    Color.TRANSPARENT
  } else {
    if (surfaceLevel == SurfaceLevel.Transparent) {
      Color.TRANSPARENT
    } else {
      ElevationOverlay(view.context, getSchemeColor()).compositeOverlay(
        surfaceLevel
      )
    }
  }

  @ColorInt
  fun getSurfaceColorM3(surfaceLevel: SurfaceLevel): Int {
    val colorM3 = if (surfaceLevel == SurfaceLevel.Transparent) {
      Color.TRANSPARENT
    } else {
      ElevationOverlay(ApplicationInitializer.context, getSchemeColor()).compositeOverlay(
        surfaceLevel
      )
    }
    return colorM3 // getColorM3(colorM3)
  }

  @ColorInt
  fun getColorM3(colorM3: ColorM3): Int {
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

  fun getBackgroundDrawable(
    @Dimension cornerSize: Float,
    backgroundColor: ColorM3,
    surfaceTint: Float = 0f,
    surfaceTintOverlay: ColorM3 = ColorM3.Primary,
    rippleEnabled: Boolean = false
  ): Drawable {
    val shapeAppearanceModel = ShapeAppearanceModelM3()
      .toBuilder()
      .setAllCorners(CornerFamily.ROUNDED, cornerSize)
      .build()
    return if (rippleEnabled) {
      RippleDrawableM3.toBuilder(shapeAppearanceModel)
        .backgroundColor(backgroundColor)
        .surfaceTint(surfaceTint)
        .surfaceTintOverlay(surfaceTintOverlay)
        .build()
    } else {
      ShapeDrawableM3(shapeAppearanceModel)
        .toBuilder()
        .backgroundColor(backgroundColor)
        .surfaceTint(surfaceTint)
        .surfaceTintOverlay(surfaceTintOverlay)
        .build()
    }
  }

  @Deprecated(message = "Deprecated in favour of getBackgroundDrawable()")
  fun getMaterialShapeDrawable(
    surfaceLevel: SurfaceLevel,
    @Dimension cornerSize: Float,
    view: View
  ): ShapeDrawableM3 {
    val shapeAppearanceModel = ShapeAppearanceModelM3()
      .toBuilder()
      .setAllCorners(CornerFamily.ROUNDED, cornerSize)
      .build()
    val materialShapeDrawable = ShapeDrawableM3(shapeAppearanceModel)

    materialShapeDrawable.fillColor = if (surfaceLevel == SurfaceLevel.Transparent) {
      Color.TRANSPARENT
    } else {
      getBackgroundColorM3(view, surfaceLevel)
    }.colorStateList

    return materialShapeDrawable
  }

  fun rippleColorStatesList(): ColorStateList {
    val colorScheme = getSchemeColor()
    val tintStates = arrayOf(
      intArrayOf(android.R.attr.state_pressed),
      intArrayOf(android.R.attr.state_focused, android.R.attr.state_hovered),
      intArrayOf(android.R.attr.state_focused),
      intArrayOf(android.R.attr.state_hovered),
      intArrayOf()
    )
    val tintColors = intArrayOf(
      colorScheme.primary.addAlpha(0.24f),
      colorScheme.primary.addAlpha(0.4f),
      colorScheme.primary.addAlpha(0.4f),
      colorScheme.primary.addAlpha(0.4f),
      colorScheme.transparent
    )
    return ColorStateList(tintStates, tintColors)
  }

  @Deprecated(message = "Deprecated in favour of getBackgroundDrawable()")
  fun rippleMask(rounded: Boolean = false): Drawable {
    val rippleMask = GradientDrawable()
    if (rounded) {
      rippleMask.shape = GradientDrawable.OVAL
      rippleMask.cornerRadii = floatArrayOf(0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f)
    }
    rippleMask.setColor(Color.WHITE)
    return rippleMask
  }

  @Deprecated(message = "Deprecated in favour of getBackgroundDrawable()")
  fun circleDrawable(@ColorInt color: Int): Drawable {
    val rippleMask = GradientDrawable()
    rippleMask.shape = GradientDrawable.OVAL
    rippleMask.cornerRadii = floatArrayOf(0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f)
    rippleMask.color = color.colorStateList
    return rippleMask
  }

  @Deprecated(message = "Deprecated in favour of getBackgroundDrawable()")
  fun drawable(@ColorInt color: Int, rounded: Boolean = false): Drawable {
    val rippleMask = GradientDrawable()
    if (rounded) {
      rippleMask.shape = GradientDrawable.OVAL
      rippleMask.cornerRadii = floatArrayOf(0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f)
    } else {
      rippleMask.shape = GradientDrawable.RECTANGLE
    }
    rippleMask.color = color.colorStateList
    return rippleMask
  }

  @Deprecated(
    message = "Deprecated in favour of getBackgroundDrawable()",
    replaceWith = ReplaceWith(
      "RippleDrawable(rippleColorStatesList, content, mask)",
      "android.graphics.drawable.RippleDrawable"
    )
  )
  fun getRippleDrawable(
    rippleColorStatesList: ColorStateList = rippleColorStatesList(),
    content: Drawable,
    mask: Drawable? = null
  ): RippleDrawable {
    return RippleDrawable(
      rippleColorStatesList,
      content,
      mask
    )
  }
}
