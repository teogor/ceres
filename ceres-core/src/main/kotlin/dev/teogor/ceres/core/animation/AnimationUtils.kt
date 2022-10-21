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

package dev.teogor.ceres.core.animation

import android.animation.TimeInterpolator
import android.view.animation.DecelerateInterpolator
import android.view.animation.LinearInterpolator
import androidx.interpolator.view.animation.FastOutLinearInInterpolator
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator

/**
 * Utility class for animations containing Material interpolators.
 */
object AnimationUtils {
  val LINEAR_INTERPOLATOR: TimeInterpolator = LinearInterpolator()
  val FAST_OUT_SLOW_IN_INTERPOLATOR: TimeInterpolator = FastOutSlowInInterpolator()
  val FAST_OUT_LINEAR_IN_INTERPOLATOR: TimeInterpolator = FastOutLinearInInterpolator()
  val LINEAR_OUT_SLOW_IN_INTERPOLATOR: TimeInterpolator = LinearOutSlowInInterpolator()
  val DECELERATE_INTERPOLATOR: TimeInterpolator = DecelerateInterpolator()

  /** Linear interpolation between `startValue` and `endValue` by `fraction`.  */
  fun lerp(startValue: Float, endValue: Float, fraction: Float): Float {
    return startValue + fraction * (endValue - startValue)
  }

  /** Linear interpolation between `startValue` and `endValue` by `fraction`.  */
  fun lerp(startValue: Int, endValue: Int, fraction: Float): Int {
    return startValue + Math.round(fraction * (endValue - startValue))
  }

  /**
   * Linear interpolation between `outputMin` and `outputMax` when `value` is
   * between `inputMin` and `inputMax`.
   *
   *
   * Note that `value` will be coerced into `inputMin` and `inputMax`.This
   * function can handle input and output ranges that span positive and negative numbers.
   */
  fun lerp(
    outputMin: Float,
    outputMax: Float,
    inputMin: Float,
    inputMax: Float,
    value: Float
  ): Float {
    if (value <= inputMin) {
      return outputMin
    }
    return if (value >= inputMax) {
      outputMax
    } else lerp(
      outputMin,
      outputMax,
      (value - inputMin) / (inputMax - inputMin)
    )
  }
}
