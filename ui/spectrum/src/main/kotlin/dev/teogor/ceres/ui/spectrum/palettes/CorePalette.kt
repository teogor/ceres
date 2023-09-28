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

package dev.teogor.ceres.ui.spectrum.palettes

import androidx.compose.ui.graphics.Color
import dev.teogor.ceres.ui.spectrum.hct.Hct
import dev.teogor.ceres.ui.spectrum.model.ColorInfo
import dev.teogor.ceres.ui.spectrum.util.invertColor

/**
 * An intermediate concept between the key color for a UI theme, and a full color scheme. 5 sets of
 * tones are generated, all except one use the same hue as the key color, and all vary in chroma.
 */
class CorePalette private constructor(
  primary: Color,
  secondary: Color = Color.Unspecified,
  tertiary: Color = Color.Unspecified,
  isContent: Boolean,
) {
  val a1: TonalPalette
  val a2: TonalPalette
  val a3: TonalPalette
  val a4: TonalPalette
  val a5: TonalPalette

  val n1: TonalPalette
  val n2: TonalPalette

  val error: TonalPalette
  val warning: TonalPalette
  val success: TonalPalette

  init {
    val primaryColorInfo = ColorInfo.from(primary)
    val secondaryColorInfo = ColorInfo.from(secondary)
    val tertiaryColorInfo = ColorInfo.from(tertiary)
    if (isContent) {
      a1 = TonalPalette.fromHueAndChroma(
        hue = primaryColorInfo.hue,
        chroma = primaryColorInfo.chroma,
      )
      a2 = TonalPalette.fromHueAndChroma(
        hue = if (secondary == Color.Unspecified) {
          primaryColorInfo
        } else {
          secondaryColorInfo
        }.hue,
        chroma = if (secondary == Color.Unspecified) {
          primaryColorInfo
        } else {
          secondaryColorInfo
        }.chroma / 3.0,
      )
      a3 = TonalPalette.fromHueAndChroma(
        hue = if (tertiary == Color.Unspecified) {
          primaryColorInfo
        } else {
          tertiaryColorInfo
        }.hue + 60.0,
        chroma = if (tertiary == Color.Unspecified) {
          primaryColorInfo
        } else {
          tertiaryColorInfo
        }.chroma / 2.0,
      )
      a4 = TonalPalette.fromHueAndChroma(
        hue = primaryColorInfo.hue + 60,
        chroma = .1,
      )
      a5 = TonalPalette.fromHueAndChroma(
        hue = Hct.fromInt(primaryColorInfo.colorInt.invertColor()).hue,
        chroma = 48.0.coerceAtLeast(primaryColorInfo.chroma),
      )
      n1 = TonalPalette.fromHueAndChroma(
        hue = primaryColorInfo.hue,
        chroma = (primaryColorInfo.chroma / 12.0).coerceAtMost(4.0),
      )
      n2 = TonalPalette.fromHueAndChroma(
        hue = primaryColorInfo.hue,
        chroma = (primaryColorInfo.chroma / 6.0).coerceAtMost(8.0),
      )
    } else {
      a1 = TonalPalette.fromHueAndChroma(
        hue = primaryColorInfo.hue,
        chroma = 48.0.coerceAtLeast(primaryColorInfo.chroma),
      )
      a2 = TonalPalette.fromHueAndChroma(
        hue = if (secondary == Color.Unspecified) {
          primaryColorInfo
        } else {
          secondaryColorInfo
        }.hue,
        chroma = 16.0,
      )
      a3 = TonalPalette.fromHueAndChroma(
        hue = if (tertiary == Color.Unspecified) {
          primaryColorInfo
        } else {
          tertiaryColorInfo
        }.hue + 60.0,
        chroma = 24.0,
      )
      a4 = TonalPalette.fromHueAndChroma(
        hue = primaryColorInfo.hue + 90,
        chroma = 48.0.coerceAtLeast(primaryColorInfo.chroma),
      )
      a5 = TonalPalette.fromHueAndChroma(
        hue = Hct.fromInt(primaryColorInfo.colorInt.invertColor()).hue,
        chroma = 48.0.coerceAtLeast(primaryColorInfo.chroma),
      )
      n1 = TonalPalette.fromHueAndChroma(
        hue = primaryColorInfo.hue,
        chroma = 4.0,
      )
      n2 = TonalPalette.fromHueAndChroma(
        hue = primaryColorInfo.hue,
        chroma = 8.0,
      )
    }
    warning = TonalPalette.fromHueAndChroma(
      hue = 86.0,
      chroma = 84.0,
    )
    success = TonalPalette.fromHueAndChroma(
      hue = 115.0,
      chroma = 84.0,
    )
    error = TonalPalette.fromHueAndChroma(
      hue = 25.0,
      chroma = 84.0,
    )
  }

  companion object {
    /**
     * Creates a `CorePalette` object with key tones derived from the specified primary color.
     *
     * @param primary the primary color to derive the key tones from
     * @param secondary the secondary color to use in the palette, defaults to `Color.Unspecified`
     * @param tertiary the tertiary color to use in the palette, defaults to `Color.Unspecified`
     * @return a new `CorePalette` object with key tones derived from the specified primary color
     */
    fun of(
      primary: Color,
      secondary: Color = Color.Unspecified,
      tertiary: Color = Color.Unspecified,
    ): CorePalette {
      return CorePalette(
        primary = primary,
        secondary = secondary,
        tertiary = tertiary,
        isContent = false,
      )
    }

    /**
     * Create content key tones from a color.
     *
     * @param primary ARGB representation of a color
     */
    fun contentOf(primary: Color): CorePalette {
      return CorePalette(
        primary = primary,
        isContent = false,
      )
    }
  }
}
