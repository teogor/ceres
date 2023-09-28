/*
 * Copyright 2023 teogor (Teodor Grigor)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dev.teogor.ceres.ui.theme.core

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dev.teogor.ceres.ui.theme.MaterialTheme
import dev.teogor.ceres.ui.theme.SchemeM3
import dev.teogor.ceres.ui.theme.getThemeTokens
import dev.teogor.ceres.ui.theme.supportsDynamicTheming
import dev.teogor.ceres.ui.theme.surfaceColorAtElevation
import dev.teogor.ceres.ui.theme.tokens.ElevationTokens

/**
 * Light Android gradient colors
 */
val LightAndroidGradientColors = GradientColors(container = DarkGreenGray95)

/**
 * Dark Android gradient colors
 */
val DarkAndroidGradientColors = GradientColors(container = Color.Black)

/**
 * Light Android background theme
 */
val LightAndroidBackgroundTheme = BackgroundTheme(color = DarkGreenGray95)

/**
 * Dark Android background theme
 */
val DarkAndroidBackgroundTheme = BackgroundTheme(color = Color.Black)

/**
 * Ceres theme.
 *
 * @param darkTheme Whether the theme should use a dark color scheme (follows system by default).
 * @param androidTheme Whether the theme should use the Android theme color scheme instead of the
 *        default theme.
 * @param disableDynamicTheming If `true`, disables the use of dynamic theming, even when it is
 *        supported. This parameter has no effect if [androidTheme] is `true`.
 */
@Composable
fun Theme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  androidTheme: Boolean = false,
  disableDynamicTheming: Boolean = false,
  themeSeed: String = "#0B57D0",
  content: @Composable () -> Unit,
) {
  // Color scheme
  val schemeM3 = SchemeM3.Builder()
    .themeTokens(
      themeTokens = getThemeTokens(
        androidTheme = androidTheme,
        disableDynamicTheming = disableDynamicTheming,
        themeSeed = themeSeed,
      ),
    )
    .build()
  val colorScheme = if (darkTheme) {
    schemeM3.darkColorScheme
  } else {
    schemeM3.lightColorScheme
  }

  // Gradient colors
  val emptyGradientColors = GradientColors(container = colorScheme.surfaceColorAtElevation(2.dp))
  val defaultGradientColors = GradientColors(
    top = colorScheme.inverseOnSurface,
    bottom = colorScheme.primaryContainer,
    container = colorScheme.surface,
  )
  val gradientColors = when {
    androidTheme -> if (darkTheme) DarkAndroidGradientColors else LightAndroidGradientColors
    !disableDynamicTheming && supportsDynamicTheming() -> emptyGradientColors
    else -> defaultGradientColors
  }
  // Background theme
  val defaultBackgroundTheme = BackgroundTheme(
    color = colorScheme.surface,
    tonalElevation = ElevationTokens.Level1,
  )
  val backgroundTheme = when {
    androidTheme -> if (darkTheme) DarkAndroidBackgroundTheme else LightAndroidBackgroundTheme
    else -> defaultBackgroundTheme
  }
  val tintTheme = when {
    androidTheme -> TintTheme()
    !disableDynamicTheming && supportsDynamicTheming() -> TintTheme(colorScheme.primary)
    else -> TintTheme()
  }
  // Composition locals
  CompositionLocalProvider(
    LocalGradientColors provides gradientColors,
    LocalBackgroundTheme provides backgroundTheme,
    LocalTintTheme provides tintTheme,
  ) {
    MaterialTheme(
      darkTheme = darkTheme,
      colorScheme = colorScheme,
      typography = CeresTypography,
      content = content,
    )
  }
}
