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

package dev.teogor.ceres.ui.theme

import androidx.compose.ui.graphics.Color
import dev.teogor.ceres.ui.spectrum.palettes.CorePalette
import dev.teogor.ceres.ui.spectrum.palettes.TonalPalette

@Suppress("MemberVisibilityCanBePrivate", "unused")
class SchemeM3 private constructor(
  val themeTokens: ThemeTokens,
) {
  private val corePalette: CorePalette = if (themeTokens.dynamicColors) {
    CorePalette.of(
      themeTokens.dynamicColorTokens.primary,
      themeTokens.dynamicColorTokens.secondary,
      themeTokens.dynamicColorTokens.tertiary,
    )
  } else {
    CorePalette.of(themeTokens.seed)
  }

  val primaryPalette: TonalPalette = corePalette.a1
  val secondaryPalette: TonalPalette = corePalette.a2
  val tertiaryPalette: TonalPalette = corePalette.a3
  val quaternaryPalette: TonalPalette = corePalette.a4
  val quinaryPalette: TonalPalette = corePalette.a5
  val neutralPalette: TonalPalette = corePalette.n1
  val neutralVariantPalette: TonalPalette = corePalette.n1

  val successPalette: TonalPalette = corePalette.success
  val warningPalette: TonalPalette = corePalette.warning
  val errorPalette: TonalPalette = corePalette.error

  val lightColorScheme: ColorScheme
  val darkColorScheme: ColorScheme
  val justBlackColorScheme: ColorScheme

  init {
    lightColorScheme = ColorScheme(
      primary = primaryPalette.tone(40),
      onPrimary = primaryPalette.tone(100),
      primaryContainer = primaryPalette.tone(90),
      onPrimaryContainer = primaryPalette.tone(10),
      inversePrimary = primaryPalette.tone(80),
      secondary = secondaryPalette.tone(40),
      onSecondary = secondaryPalette.tone(100),
      secondaryContainer = secondaryPalette.tone(90),
      onSecondaryContainer = secondaryPalette.tone(10),
      tertiary = tertiaryPalette.tone(40),
      onTertiary = tertiaryPalette.tone(100),
      tertiaryContainer = tertiaryPalette.tone(90),
      onTertiaryContainer = tertiaryPalette.tone(10),
      quaternary = quaternaryPalette.tone(40),
      onQuaternary = quaternaryPalette.tone(100),
      quaternaryContainer = quaternaryPalette.tone(90),
      onQuaternaryContainer = quaternaryPalette.tone(10),
      quinary = quinaryPalette.tone(40),
      onQuinary = quinaryPalette.tone(100),
      quinaryContainer = quinaryPalette.tone(90),
      onQuinaryContainer = quinaryPalette.tone(10),
      background = neutralPalette.tone(99),
      onBackground = neutralPalette.tone(10),
      surface = neutralPalette.tone(99),
      onSurface = neutralPalette.tone(10),
      surfaceVariant = neutralVariantPalette.tone(90),
      onSurfaceVariant = neutralVariantPalette.tone(30),
      surfaceTint = primaryPalette.tone(40),
      inverseSurface = neutralPalette.tone(20),
      inverseOnSurface = neutralPalette.tone(95),
      error = errorPalette.tone(40),
      onError = errorPalette.tone(100),
      errorContainer = errorPalette.tone(90),
      onErrorContainer = errorPalette.tone(10),
      success = successPalette.tone(40),
      onSuccess = successPalette.tone(100),
      successContainer = successPalette.tone(90),
      onSuccessContainer = successPalette.tone(10),
      warning = warningPalette.tone(40),
      onWarning = warningPalette.tone(100),
      warningContainer = warningPalette.tone(90),
      onWarningContainer = warningPalette.tone(10),
      outline = neutralVariantPalette.tone(50),
      outlineVariant = neutralVariantPalette.tone(80),
      scrim = neutralPalette.tone(0),
    )
    darkColorScheme = ColorScheme(
      primary = primaryPalette.tone(80),
      onPrimary = primaryPalette.tone(20),
      primaryContainer = primaryPalette.tone(30),
      onPrimaryContainer = primaryPalette.tone(90),
      inversePrimary = primaryPalette.tone(40),
      secondary = secondaryPalette.tone(80),
      onSecondary = secondaryPalette.tone(20),
      secondaryContainer = secondaryPalette.tone(30),
      onSecondaryContainer = secondaryPalette.tone(90),
      tertiary = tertiaryPalette.tone(80),
      onTertiary = tertiaryPalette.tone(20),
      tertiaryContainer = tertiaryPalette.tone(30),
      onTertiaryContainer = tertiaryPalette.tone(90),
      quaternary = quaternaryPalette.tone(80),
      onQuaternary = quaternaryPalette.tone(20),
      quaternaryContainer = quaternaryPalette.tone(30),
      onQuaternaryContainer = quaternaryPalette.tone(90),
      quinary = quinaryPalette.tone(80),
      onQuinary = quinaryPalette.tone(20),
      quinaryContainer = quinaryPalette.tone(30),
      onQuinaryContainer = quinaryPalette.tone(90),
      background = neutralPalette.tone(10),
      onBackground = neutralPalette.tone(90),
      surface = neutralPalette.tone(10),
      onSurface = neutralPalette.tone(80),
      surfaceVariant = neutralVariantPalette.tone(30),
      onSurfaceVariant = neutralVariantPalette.tone(80),
      surfaceTint = primaryPalette.tone(80),
      inverseSurface = neutralPalette.tone(90),
      inverseOnSurface = neutralPalette.tone(40),
      error = errorPalette.tone(80),
      onError = errorPalette.tone(20),
      errorContainer = errorPalette.tone(30),
      onErrorContainer = errorPalette.tone(90),
      success = successPalette.tone(80),
      onSuccess = successPalette.tone(20),
      successContainer = successPalette.tone(30),
      onSuccessContainer = successPalette.tone(90),
      warning = warningPalette.tone(80),
      onWarning = warningPalette.tone(20),
      warningContainer = warningPalette.tone(30),
      onWarningContainer = warningPalette.tone(90),
      outline = neutralVariantPalette.tone(60),
      outlineVariant = neutralVariantPalette.tone(30),
      scrim = neutralPalette.tone(0),
    )
    justBlackColorScheme = ColorScheme(
      primary = primaryPalette.tone(80),
      onPrimary = primaryPalette.tone(20),
      primaryContainer = primaryPalette.tone(30),
      onPrimaryContainer = primaryPalette.tone(90),
      inversePrimary = primaryPalette.tone(40),
      secondary = secondaryPalette.tone(80),
      onSecondary = secondaryPalette.tone(20),
      secondaryContainer = secondaryPalette.tone(30),
      onSecondaryContainer = secondaryPalette.tone(90),
      tertiary = tertiaryPalette.tone(80),
      onTertiary = tertiaryPalette.tone(20),
      tertiaryContainer = tertiaryPalette.tone(30),
      onTertiaryContainer = tertiaryPalette.tone(90),
      quaternary = quaternaryPalette.tone(80),
      onQuaternary = quaternaryPalette.tone(20),
      quaternaryContainer = quaternaryPalette.tone(30),
      onQuaternaryContainer = quaternaryPalette.tone(90),
      quinary = quinaryPalette.tone(80),
      onQuinary = quinaryPalette.tone(20),
      quinaryContainer = quinaryPalette.tone(30),
      onQuinaryContainer = quinaryPalette.tone(90),
      background = neutralPalette.tone(10),
      onBackground = neutralPalette.tone(90),
      surface = Color.Black,
      onSurface = neutralPalette.tone(80),
      surfaceVariant = neutralVariantPalette.tone(30),
      onSurfaceVariant = neutralVariantPalette.tone(80),
      surfaceTint = primaryPalette.tone(80),
      inverseSurface = neutralPalette.tone(90),
      inverseOnSurface = neutralPalette.tone(40),
      error = errorPalette.tone(80),
      onError = errorPalette.tone(20),
      errorContainer = errorPalette.tone(30),
      onErrorContainer = errorPalette.tone(90),
      success = successPalette.tone(80),
      onSuccess = successPalette.tone(20),
      successContainer = successPalette.tone(30),
      onSuccessContainer = successPalette.tone(90),
      warning = warningPalette.tone(80),
      onWarning = warningPalette.tone(20),
      warningContainer = warningPalette.tone(30),
      onWarningContainer = warningPalette.tone(90),
      outline = neutralVariantPalette.tone(60),
      outlineVariant = neutralVariantPalette.tone(30),
      scrim = neutralPalette.tone(0),
    )
  }

  data class Builder(
    private var themeTokens: ThemeTokens = ThemeTokens(),
  ) {
    fun themeTokens(themeTokens: ThemeTokens) = apply { this.themeTokens = themeTokens }

    fun build() = SchemeM3(
      themeTokens,
    )
  }
}
