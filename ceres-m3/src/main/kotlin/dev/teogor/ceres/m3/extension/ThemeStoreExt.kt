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

package dev.teogor.ceres.m3.extension

import android.view.View
import androidx.annotation.ColorInt
import androidx.core.view.allViews
import dev.teogor.ceres.components.annotation.ColorString
import dev.teogor.ceres.extensions.asHex
import dev.teogor.ceres.extensions.blendColors
import dev.teogor.ceres.m3.ColorM3
import dev.teogor.ceres.m3.theme.ColorScheme
import dev.teogor.ceres.m3.theme.IThemeM3
import dev.teogor.ceres.m3.theme.ThemeM3
import dev.teogor.ceres.m3.theme.ThemeMemo

fun ThemeMemo.applyNewColor(
  @ColorInt color: Int
) {
  this.seed = color.asHex()
}

fun ThemeMemo.applyNewColor(
  @ColorString color: String
) {
  this.seed = color
}

fun View.applyThemeOnChildren() {
  this.allViews
    .filter { view -> view is IThemeM3 }
    .filter { it != this }
    .map { it as IThemeM3 }
    .forEach { view ->
      view.onThemeChanged()
    }
}

fun getSchemeColor(): ColorScheme {
  return ThemeM3.currentColorScheme()
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
    ColorM3.Quaternary -> getSchemeColor().quaternary
    ColorM3.OnQuaternary -> getSchemeColor().onQuaternary
    ColorM3.QuaternaryContainer -> getSchemeColor().quaternaryContainer
    ColorM3.OnQuaternaryContainer -> getSchemeColor().onQuaternaryContainer
    ColorM3.Quinary -> getSchemeColor().quinary
    ColorM3.OnQuinary -> getSchemeColor().onQuinary
    ColorM3.QuinaryContainer -> getSchemeColor().quinaryContainer
    ColorM3.OnQuinaryContainer -> getSchemeColor().onQuinaryContainer
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
