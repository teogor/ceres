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

package dev.teogor.ceres.ui.spectrum.quantize

/**
 * Creates a dictionary with keys of colors, and values of count of the color
 */
class QuantizerMap : Quantizer {
  var colorToCount: Map<Int, Int>? = null

  override fun quantize(pixels: IntArray?, maxColors: Int): QuantizerResult {
    val pixelByCount = HashMap<Int, Int>()
    for (pixel in pixels!!) {
      val currentPixelCount = pixelByCount[pixel]
      val newPixelCount = if (currentPixelCount == null) 1 else currentPixelCount + 1
      pixelByCount[pixel] = newPixelCount
    }
    colorToCount = pixelByCount
    return QuantizerResult(pixelByCount)
  }
}
