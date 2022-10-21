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
import androidx.annotation.ColorInt
import dev.teogor.ceres.m3.generator.palettes.CorePalette
import dev.teogor.ceres.m3.generator.palettes.TonalPalette

@Suppress("MemberVisibilityCanBePrivate", "unused")
class SchemeM3 private constructor(
  val seed: String,
  @ColorInt private val primaryColor: Int,
  val dynamicColors: Boolean
) {

  val corePalette: dev.teogor.ceres.m3.generator.palettes.CorePalette = dev.teogor.ceres.m3.generator.palettes.CorePalette.of(primaryColor)
  val primaryPalette: dev.teogor.ceres.m3.generator.palettes.TonalPalette = corePalette.a1
  val secondaryPalette: dev.teogor.ceres.m3.generator.palettes.TonalPalette = corePalette.a2
  val tertiaryPalette: dev.teogor.ceres.m3.generator.palettes.TonalPalette = corePalette.a3
  val errorPalette: dev.teogor.ceres.m3.generator.palettes.TonalPalette = corePalette.error
  val neutralPalette: dev.teogor.ceres.m3.generator.palettes.TonalPalette = corePalette.n1
  val neutralVariantPalette: dev.teogor.ceres.m3.generator.palettes.TonalPalette = corePalette.n1

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
      outline = neutralVariantPalette.tone(50),
      outlineVariant = neutralVariantPalette.tone(80),
      scrim = neutralPalette.tone(0)
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
      outline = neutralVariantPalette.tone(60),
      outlineVariant = neutralVariantPalette.tone(30),
      scrim = neutralPalette.tone(0)
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
      background = neutralPalette.tone(10),
      onBackground = neutralPalette.tone(90),
      surface = Color.BLACK,
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
      outline = neutralVariantPalette.tone(60),
      outlineVariant = neutralVariantPalette.tone(30),
      scrim = neutralPalette.tone(0)
    )
  }

  data class Builder(
    private var seed: String = "#0B57D0",
    private var dynamicColors: Boolean = false
  ) {

    fun seed(color: String) = apply { this.seed = color }

    fun dynamicColors(enabled: Boolean) = apply { this.dynamicColors = enabled }

    fun build() = SchemeM3(
      seed,
      Color.parseColor(seed),
      dynamicColors
    )
  }
}
