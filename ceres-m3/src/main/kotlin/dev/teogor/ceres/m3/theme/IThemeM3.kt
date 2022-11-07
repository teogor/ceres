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

import androidx.annotation.ColorInt
import dev.teogor.ceres.core.logger.Logger
import dev.teogor.ceres.extensions.blendColors
import dev.teogor.ceres.m3.ColorM3

interface IThemeM3 : Logger {
  fun onThemeChanged() {
  }

  fun colorScheme(): ColorScheme {
    return ThemeM3.currentColorScheme()
  }

  @ColorInt
  fun getColorM3(colorM3: ColorM3): Int {
    return when (colorM3) {
      ColorM3.Primary -> colorScheme().primary
      ColorM3.OnPrimary -> colorScheme().onPrimary
      ColorM3.PrimaryContainer -> colorScheme().primaryContainer
      ColorM3.OnPrimaryContainer -> colorScheme().onPrimaryContainer
      ColorM3.InversePrimary -> colorScheme().inversePrimary
      ColorM3.Secondary -> colorScheme().secondary
      ColorM3.OnSecondary -> colorScheme().onSecondary
      ColorM3.SecondaryContainer -> colorScheme().secondaryContainer
      ColorM3.OnSecondaryContainer -> colorScheme().onSecondaryContainer
      ColorM3.Tertiary -> colorScheme().tertiary
      ColorM3.OnTertiary -> colorScheme().onTertiary
      ColorM3.TertiaryContainer -> colorScheme().tertiaryContainer
      ColorM3.OnTertiaryContainer -> colorScheme().onTertiaryContainer
      ColorM3.Quaternary -> colorScheme().quaternary
      ColorM3.OnQuaternary -> colorScheme().onQuaternary
      ColorM3.QuaternaryContainer -> colorScheme().quaternaryContainer
      ColorM3.OnQuaternaryContainer -> colorScheme().onQuaternaryContainer
      ColorM3.Quinary -> colorScheme().quinary
      ColorM3.OnQuinary -> colorScheme().onQuinary
      ColorM3.QuinaryContainer -> colorScheme().quaternaryContainer
      ColorM3.OnQuinaryContainer -> colorScheme().onQuinaryContainer
      ColorM3.Background -> colorScheme().background
      ColorM3.OnBackground -> colorScheme().onBackground
      ColorM3.Surface -> colorScheme().surface
      ColorM3.OnSurface -> colorScheme().onSurface
      ColorM3.SurfaceVariant -> colorScheme().surfaceVariant
      ColorM3.OnSurfaceVariant -> colorScheme().onSurfaceVariant
      ColorM3.SurfaceTint -> colorScheme().surfaceTint
      ColorM3.InverseSurface -> colorScheme().inverseSurface
      ColorM3.InverseOnSurface -> colorScheme().inverseOnSurface
      ColorM3.Error -> colorScheme().error
      ColorM3.OnError -> colorScheme().onError
      ColorM3.ErrorContainer -> colorScheme().errorContainer
      ColorM3.OnErrorContainer -> colorScheme().onErrorContainer
      ColorM3.Outline -> colorScheme().outline
      ColorM3.OutlineVariant -> colorScheme().outlineVariant
      ColorM3.Scrim -> colorScheme().scrim

      ColorM3.OnPrimaryMenu -> blendColors(
        colorScheme().primary,
        colorScheme().onBackground,
        0.9f
      )
    }
  }
}
