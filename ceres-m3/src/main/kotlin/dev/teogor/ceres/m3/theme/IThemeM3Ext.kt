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

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.view.View
import androidx.annotation.ColorInt
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.RelativeCornerSize
import com.google.android.material.shape.RoundedCornerTreatment
import dev.teogor.ceres.components.view.Shape
import dev.teogor.ceres.m3.ColorM3
import dev.teogor.ceres.m3.beta.Beta
import dev.teogor.ceres.m3.drawable.ShapeAppearanceModelM3
import dev.teogor.ceres.m3.elevation.SurfaceLevel

@ColorInt
fun IThemeM3.backgroundColor(
  view: View,
  surfaceLevel: SurfaceLevel
) = if (view.isInEditMode) {
  Color.TRANSPARENT
} else {
  if (surfaceLevel == SurfaceLevel.Transparent) {
    Color.TRANSPARENT
  } else {
    ElevationOverlay(view.context, colorScheme()).compositeOverlay(
      surfaceLevel
    )
  }
}

fun IThemeM3.getBackgroundDrawable(
  backgroundDrawable: Beta.BackgroundDrawable
): Drawable {
  val shapeAppearanceModel = ShapeAppearanceModelM3()
    .toBuilder()

  when (backgroundDrawable.shape) {
    Shape.Rectangle -> {
      shapeAppearanceModel.apply {
        setAllCorners(CornerFamily.ROUNDED, backgroundDrawable.cornerSize)
      }
    }
    Shape.Circle -> {
      shapeAppearanceModel.apply {
        setAllCorners(RoundedCornerTreatment())
        setAllCornerSizes(RelativeCornerSize(.5f))
      }
    }
  }
  return if (backgroundDrawable.rippleEnabled) {
    Beta.RippleDrawableM3.toBuilder(shapeAppearanceModel.build())
      .backgroundDrawable(backgroundDrawable)
      .build()
  } else {
    Beta.ShapeDrawableM3(shapeAppearanceModel.build())
      .toBuilder()
      .backgroundDrawable(backgroundDrawable)
      .build()
  }
}

fun ColorM3.inverse(): ColorM3 {
  return when (this) {
    ColorM3.Primary -> ColorM3.OnPrimary
    ColorM3.OnPrimary -> ColorM3.Primary
    ColorM3.PrimaryContainer -> ColorM3.OnPrimaryContainer
    ColorM3.OnPrimaryContainer -> ColorM3.PrimaryContainer
    ColorM3.InversePrimary -> ColorM3.Primary
    ColorM3.Secondary -> ColorM3.OnSecondary
    ColorM3.OnSecondary -> ColorM3.Secondary
    ColorM3.SecondaryContainer -> ColorM3.OnSecondaryContainer
    ColorM3.OnSecondaryContainer -> ColorM3.SecondaryContainer
    ColorM3.Tertiary -> ColorM3.OnTertiary
    ColorM3.OnTertiary -> ColorM3.Tertiary
    ColorM3.TertiaryContainer -> ColorM3.OnTertiaryContainer
    ColorM3.OnTertiaryContainer -> ColorM3.TertiaryContainer
    ColorM3.Quaternary -> ColorM3.OnQuaternary
    ColorM3.OnQuaternary -> ColorM3.Quaternary
    ColorM3.QuaternaryContainer -> ColorM3.OnQuaternaryContainer
    ColorM3.OnQuaternaryContainer -> ColorM3.QuaternaryContainer
    ColorM3.Quinary -> ColorM3.OnQuinary
    ColorM3.OnQuinary -> ColorM3.Quinary
    ColorM3.QuinaryContainer -> ColorM3.OnQuinaryContainer
    ColorM3.OnQuinaryContainer -> ColorM3.QuinaryContainer
    ColorM3.Background -> ColorM3.OnBackground
    ColorM3.OnBackground -> ColorM3.Background
    ColorM3.Surface -> ColorM3.OnSurface
    ColorM3.OnSurface -> ColorM3.Surface
    ColorM3.SurfaceVariant -> ColorM3.OnSurfaceVariant
    ColorM3.OnSurfaceVariant -> ColorM3.SurfaceVariant
    ColorM3.SurfaceTint -> ColorM3.Surface
    ColorM3.InverseSurface -> ColorM3.InverseOnSurface
    ColorM3.InverseOnSurface -> ColorM3.InverseSurface
    ColorM3.Error -> ColorM3.OnError
    ColorM3.OnError -> ColorM3.Error
    ColorM3.ErrorContainer -> ColorM3.OnErrorContainer
    ColorM3.OnErrorContainer -> ColorM3.ErrorContainer
    ColorM3.Outline -> ColorM3.OutlineVariant
    ColorM3.OutlineVariant -> ColorM3.Outline
    ColorM3.Scrim -> ColorM3.Scrim
    ColorM3.OnPrimaryMenu -> ColorM3.OnBackground
  }
}
