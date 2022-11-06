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

package dev.teogor.ceres.m3.generator.palettes

import dev.teogor.ceres.extensions.invertColor
import dev.teogor.ceres.m3.generator.hct.Hct

/**
 * An intermediate concept between the key color for a UI theme, and a full color scheme. 5 sets of
 * tones are generated, all except one use the same hue as the key color, and all vary in chroma.
 */
class CorePalette private constructor(argb: Int, isContent: Boolean) {
  var a1: TonalPalette
  var a2: TonalPalette
  var a3: TonalPalette
  var a4: TonalPalette
  var a5: TonalPalette
  var n1: TonalPalette
  var n2: TonalPalette
  var error: TonalPalette

  init {
    val hct = Hct.fromInt(argb)
    val hue = hct.hue
    val chroma = hct.chroma
    if (isContent) {
      a1 = TonalPalette.fromHueAndChroma(hue, chroma)
      a2 = TonalPalette.fromHueAndChroma(hue, chroma / 3.0)
      a3 = TonalPalette.fromHueAndChroma(hue + 60.0, chroma / 2.0)
      a4 = TonalPalette.fromHueAndChroma(hue + 60, .1)
      a5 = TonalPalette.fromHueAndChroma(
        Hct.fromInt(argb.invertColor()).hue,
        Hct.fromInt(argb.invertColor()).chroma
      )
      n1 = TonalPalette.fromHueAndChroma(hue, (chroma / 12.0).coerceAtMost(4.0))
      n2 = TonalPalette.fromHueAndChroma(hue, (chroma / 6.0).coerceAtMost(8.0))
    } else {
      a1 = TonalPalette.fromHueAndChroma(hue, 48.0.coerceAtLeast(chroma))
      a2 = TonalPalette.fromHueAndChroma(hue, 16.0)
      a3 = TonalPalette.fromHueAndChroma(hue + 60.0, 24.0)
      a4 = TonalPalette.fromHueAndChroma(hue + 90, 48.0.coerceAtLeast(chroma))
      a5 = TonalPalette.fromHueAndChroma(
        Hct.fromInt(argb.invertColor()).hue,
        Hct.fromInt(argb.invertColor()).chroma
      )
      n1 = TonalPalette.fromHueAndChroma(hue, 4.0)
      n2 = TonalPalette.fromHueAndChroma(hue, 8.0)
    }
    error = TonalPalette.fromHueAndChroma(25.0, 84.0)
  }

  companion object {
    /**
     * Create key tones from a color.
     *
     * @param argb ARGB representation of a color
     */
    fun of(argb: Int): CorePalette {
      return CorePalette(argb, false)
    }

    /**
     * Create content key tones from a color.
     *
     * @param argb ARGB representation of a color
     */
    fun contentOf(argb: Int): CorePalette {
      return CorePalette(argb, true)
    }
  }
}
