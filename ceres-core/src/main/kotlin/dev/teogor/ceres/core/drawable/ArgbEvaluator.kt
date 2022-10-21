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

package dev.teogor.ceres.core.drawable

import android.animation.TypeEvaluator
import android.animation.ValueAnimator
import kotlin.math.pow
import kotlin.math.roundToInt

class ArgbEvaluator : TypeEvaluator<Any?> {
  /**
   * This function returns the calculated in-between value for a color
   * given integers that represent the start and end values in the four
   * bytes of the 32-bit int. Each channel is separately linearly interpolated
   * and the resulting calculated values are recombined into the return value.
   *
   * @param fraction   The fraction from the starting to the ending values
   * @param startValue A 32-bit int value representing colors in the
   * separate bytes of the parameter
   * @param endValue   A 32-bit int value representing colors in the
   * separate bytes of the parameter
   * @return A value that is calculated to be the linearly interpolated
   * result, derived by separating the start and end values into separate
   * color channels and interpolating each one separately, recombining the
   * resulting values in the same way.
   */
  override fun evaluate(fraction: Float, startValue: Any?, endValue: Any?): Any {
    val startInt = startValue as Int
    val startA = (startInt shr 24 and 0xff) / 255.0f
    var startR = (startInt shr 16 and 0xff) / 255.0f
    var startG = (startInt shr 8 and 0xff) / 255.0f
    var startB = (startInt and 0xff) / 255.0f
    val endInt = endValue as Int
    val endA = (endInt shr 24 and 0xff) / 255.0f
    var endR = (endInt shr 16 and 0xff) / 255.0f
    var endG = (endInt shr 8 and 0xff) / 255.0f
    var endB = (endInt and 0xff) / 255.0f

    // convert from sRGB to linear
    startR = startR.toDouble().pow(2.2).toFloat()
    startG = startG.toDouble().pow(2.2).toFloat()
    startB = startB.toDouble().pow(2.2).toFloat()
    endR = endR.toDouble().pow(2.2).toFloat()
    endG = endG.toDouble().pow(2.2).toFloat()
    endB = endB.toDouble().pow(2.2).toFloat()

    // compute the interpolated color in linear space
    var a = startA + fraction * (endA - startA)
    var r = startR + fraction * (endR - startR)
    var g = startG + fraction * (endG - startG)
    var b = startB + fraction * (endB - startB)

    // convert back to sRGB in the [0..255] range
    a *= 255.0f
    r = r.toDouble().pow(1.0 / 2.2).toFloat() * 255.0f
    g = g.toDouble().pow(1.0 / 2.2).toFloat() * 255.0f
    b = b.toDouble().pow(1.0 / 2.2).toFloat() * 255.0f
    return a.roundToInt() shl 24 or (r.roundToInt() shl 16) or (g.roundToInt() shl 8) or b.roundToInt()
  }

  companion object {
    /**
     * Returns an instance of `ArgbEvaluator` that may be used in
     * [ValueAnimator.setEvaluator]. The same instance may
     * be used in multiple `Animator`s because it holds no state.
     *
     * @return An instance of `ArgbEvalutor`.
     */
    val instance = ArgbEvaluator()
  }
}
