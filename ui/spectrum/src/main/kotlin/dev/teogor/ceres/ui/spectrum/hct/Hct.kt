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

package dev.teogor.ceres.ui.spectrum.hct

import dev.teogor.ceres.ui.spectrum.hct.HctSolver.solveToInt
import dev.teogor.ceres.ui.spectrum.util.ColorUtils

/**
 * A color system built using CAM16 hue and chroma, and L* from L*a*b*.
 *
 *
 * Using L* creates a link between the color system, contrast, and thus accessibility. Contrast
 * ratio depends on relative luminance, or Y in the XYZ color space. L*, or perceptual luminance can
 * be calculated from Y.
 *
 *
 * Unlike Y, L* is linear to human perception, allowing trivial creation of accurate color tones.
 *
 *
 * Unlike contrast ratio, measuring contrast in L* is linear, and simple to calculate. A
 * difference of 40 in HCT tone guarantees a contrast ratio >= 3.0, and a difference of 50
 * guarantees a contrast ratio >= 4.5.
 */
/**
 * HCT, hue, chroma, and tone. A color system that provides a perceptually accurate color
 * measurement system that can also accurately render what colors will appear as in different
 * lighting environments.
 */
class Hct private constructor(argb: Int) {

  private var _hue = 0.0
  private var _chroma = 0.0
  private var _tone = 0.0

  var hue: Double
    get() = _hue
    set(value) {
      setInternalState(solveToInt(value, chroma, tone))
    }

  var chroma: Double
    get() = _chroma
    set(value) {
      setInternalState(solveToInt(hue, value, tone))
    }

  var tone: Double
    get() = _tone
    set(value) {
      setInternalState(solveToInt(hue, chroma, value))
    }
  private var argb = 0

  init {
    setInternalState(argb)
  }

  fun toInt(): Int {
    return argb
  }

  private fun setInternalState(argb: Int) {
    this.argb = argb
    val cam = Cam16.fromInt(argb)
    _hue = cam.hue
    _chroma = cam.chroma
    _tone = ColorUtils.lstarFromArgb(argb)
  }

  companion object {
    /**
     * Create an HCT color from hue, chroma, and tone.
     *
     * @param hue 0 <= hue < 360; invalid values are corrected.
     * @param chroma 0 <= chroma < ?; Informally, colorfulness. The color returned may be lower than
     * the requested chroma. Chroma has a different maximum for any given hue and tone.
     * @param tone 0 <= tone <= 100; invalid values are corrected.
     * @return HCT representation of a color in default viewing conditions.
     */
    fun from(hue: Double, chroma: Double, tone: Double): Hct {
      val argb = solveToInt(hue, chroma, tone)
      return Hct(argb)
    }

    /**
     * Create an HCT color from a color.
     *
     * @param argb ARGB representation of a color.
     * @return HCT representation of a color in default viewing conditions
     */
    fun fromInt(argb: Int): Hct {
      return Hct(argb)
    }
  }
}
