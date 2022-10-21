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

package dev.teogor.ceres.m3.widgets.bar

import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import dev.teogor.ceres.core.drawable.ArgbEvaluator

class StatusBar constructor(
  context: Context,
  attrs: AttributeSet
) : Bar(context, attrs) {

  private val animationDelay = 200L
  private val animationSpeed = 400L

  private var isFilled = false

  init {
    if (!consumeTop) {
      setBackgroundColor(colorSurfaceNormal())
    } else {
      setBackgroundColor(colorSurfaceFilled())
    }
    if (!isInEditMode) {
      ViewCompat.setOnApplyWindowInsetsListener(this) { _, insets ->
        val statusBarSize = insets.getInsets(WindowInsetsCompat.Type.statusBars()).top
        layoutParams = LayoutParams(
          LayoutParams.MATCH_PARENT,
          statusBarSize
        )
        insets
      }
    } else {
      layoutParams = LayoutParams(
        LayoutParams.MATCH_PARENT,
        0
      )
    }
  }

  fun setFilled(isFilled: Boolean, animated: Boolean = true) {
    if (isFilled == this.isFilled) {
      return
    }
    this.isFilled = isFilled
    if (!animated) {
      setBackgroundColor(
        if (isFilled) {
          colorSurfaceFilled()
        } else {
          colorSurfaceNormal()
        }
      )
      return
    }
    if (isFilled) {
      val colorAnimator = ValueAnimator.ofObject(
        ArgbEvaluator(),
        colorSurfaceNormal(),
        colorSurfaceFilled()
      )
      colorAnimator.duration = animationSpeed - animationDelay
      colorAnimator.startDelay = animationDelay
      createShapeValueAnimator(colorAnimator)
    } else {
      val colorAnimator = ValueAnimator.ofObject(
        ArgbEvaluator(),
        colorSurfaceFilled(),
        colorSurfaceNormal()
      )
      colorAnimator.duration = animationSpeed
      createShapeValueAnimator(colorAnimator)
    }
  }

  private fun createShapeValueAnimator(colorAnimator: ValueAnimator) {
    colorAnimator.addUpdateListener { animation ->
      val value = animation.animatedValue as Int
      setBackgroundColor(value)
    }
    colorAnimator.start()
  }

  override fun onThemeChanged() {
    super.onThemeChanged()

    setBackgroundColor(
      if (isFilled) {
        colorSurfaceFilled()
      } else {
        colorSurfaceNormal()
      }
    )
  }
}
