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
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.View
import androidx.annotation.FloatRange
import androidx.annotation.IdRes
import androidx.constraintlayout.widget.ConstraintSet
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.ShapeAppearanceModel
import dev.teogor.ceres.components.toolbar.ToolbarType
import dev.teogor.ceres.core.drawable.ArgbEvaluator
import dev.teogor.ceres.extensions.colorStateList
import dev.teogor.ceres.extensions.dpToPx
import dev.teogor.ceres.m3.R
import dev.teogor.ceres.m3.extension.applyRippleEnabled

class ToolBar constructor(
  context: Context,
  attrs: AttributeSet
) : Bar(context, attrs) {

  private val cornersSize = 60.dpToPx.toFloat()
  private val shapeAppearanceModel = ShapeAppearanceModel()
    .toBuilder()
    .setAllCorners(CornerFamily.ROUNDED, cornersSize)
    .build()
  private val toolbarBackground = MaterialShapeDrawable(shapeAppearanceModel)

  @IdRes
  private val idToolbar = generateViewId()
  private val toolbarView = View(context)

  private val verticalMargin = 8.dpToPx
  private val horizontalMargin = 10.dpToPx
  private val toolbarHeight = resources.getDimension(R.dimen.toolbar_height).toInt()
  private val toolbarHeightRounded = toolbarHeight - 2 * verticalMargin
  private val animationSpeed = 400L

  private var toolbarType = ToolbarType.ROUNDED
  private var statusBar: StatusBar? = null
  private var interpolatorAnimator: ValueAnimator = ValueAnimator.ofFloat(
    0f,
    1f
  )
  private var isFilled: Boolean = true
  private var isTransparent: Boolean = false

  init {
    init()
  }

  private fun init() {
    prepareRoot()
    prepareToolbar()

    val constraintSet = ConstraintSet()
    constraintSet.clone(this)
    setToolbarConstraints(constraintSet)
    constraintSet.applyTo(this)
  }

  private fun prepareRoot() {
    setBackgroundColor(colorTransparent)
  }

  private fun prepareToolbar() {
    // set id for toolbar
    toolbarView.id = idToolbar

    // set toolbar layout params
    val layoutParams = LayoutParams(
      0,
      toolbarHeightRounded
    )
    layoutParams.setMargins(
      horizontalMargin,
      verticalMargin,
      horizontalMargin,
      verticalMargin
    )
    toolbarView.layoutParams = layoutParams

    if (!consumeTop) {
      // set toolbar background
      toolbarBackground.fillColor = ColorStateList.valueOf(
        colorSurfaceFilled()
      )
      toolbarView.background = toolbarBackground
    }

    createShapeValueAnimator()
    // add toolbar
    addView(toolbarView)
  }

  private fun setToolbarConstraints(constraintSet: ConstraintSet) {
    constraintSet.connect(
      idToolbar,
      ConstraintSet.START,
      ConstraintSet.PARENT_ID,
      ConstraintSet.START
    )
    constraintSet.connect(
      idToolbar,
      ConstraintSet.TOP,
      ConstraintSet.PARENT_ID,
      ConstraintSet.TOP
    )
    constraintSet.connect(
      idToolbar,
      ConstraintSet.END,
      ConstraintSet.PARENT_ID,
      ConstraintSet.END
    )
    constraintSet.connect(
      idToolbar,
      ConstraintSet.BOTTOM,
      ConstraintSet.PARENT_ID,
      ConstraintSet.BOTTOM
    )
  }

  private fun expandToolbar(expanded: Boolean) {
    statusBar?.setFilled(expanded)
    if (interpolatorAnimator.isRunning) {
      interpolatorAnimator.reverse()
    } else {
      val to = if (expanded) 0f else 1f
      val from = 1f - to
      interpolatorAnimator.setFloatValues(from, to)
      interpolatorAnimator.start()
    }
  }

  private fun createShapeValueAnimator() {
    interpolatorAnimator.duration = animationSpeed
    interpolatorAnimator.addUpdateListener { animation ->
      val value = animation.animatedValue as Float
      setBackgroundInterpolation(value)

      val toolbarLayoutParams = LayoutParams(
        0,
        0
      )

      val horizontalMargin = (horizontalMargin * value).toInt()
      val verticalMargin = (verticalMargin * value).toInt()

      toolbarLayoutParams.setMargins(
        horizontalMargin,
        verticalMargin,
        horizontalMargin,
        verticalMargin
      )
      toolbarView.layoutParams = toolbarLayoutParams

      val constraintSet = ConstraintSet()
      constraintSet.clone(this)
      setToolbarConstraints(constraintSet)
      constraintSet.applyTo(this)
    }
  }

  private fun setMargins(expanded: Boolean) {
    statusBar?.setFilled(expanded, animated = false)
    if (interpolatorAnimator.isRunning) {
      interpolatorAnimator.reverse()
    } else {
      val to = if (expanded) 0f else 1f
      val from = 1f - to
      interpolatorAnimator.setFloatValues(from, to)
      interpolatorAnimator.start()
    }
  }

  private fun setBackgroundInterpolation(
    @FloatRange(
      from = 0.0,
      to = 1.0,
      fromInclusive = true
    ) value: Float
  ) {
    toolbarBackground.interpolation = value
  }

  private fun setBackgroundFilled(isFilled: Boolean) {
    if (isFilled == this.isFilled) {
      return
    }
    this.isFilled = isFilled
    if (isFilled) {
      val colorAnimator = ValueAnimator.ofObject(
        ArgbEvaluator(),
        colorSurfaceNormal(),
        colorSurfaceFilled()
      )
      colorAnimator.duration = animationSpeed
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

  private var colorAnimator: ValueAnimator? = null
  private fun createShapeValueAnimator(colorAnimator: ValueAnimator) {
    this.colorAnimator = colorAnimator
    colorAnimator.addUpdateListener { animation ->
      val value = animation.animatedValue as Int
      toolbarBackground.fillColor = value.colorStateList
      toolbarView.background = toolbarBackground
    }
    colorAnimator.start()
  }

  /**
   * API Methods
   */
  fun setType(type: ToolbarType) {
    if (type == ToolbarType.COLLAPSABLE && toolbarType != ToolbarType.COLLAPSABLE) {
      setMargins(true)
      setBackgroundInterpolation(0f)
      statusBar?.setFilled(isFilled = false, animated = false)
      setBackgroundFilled(false)
    } else if (type == ToolbarType.ROUNDED && toolbarType != ToolbarType.ROUNDED) {
      setMargins(false)
      setBackgroundInterpolation(1f)
      statusBar?.setFilled(false)
      setBackgroundFilled(true)
    } else if (type == ToolbarType.BACK_BUTTON && toolbarType != ToolbarType.BACK_BUTTON) {
      setMargins(true)
      setBackgroundInterpolation(0f)
      statusBar?.setFilled(isFilled = false, animated = false)
      setBackgroundFilled(false)
    } else if (type == ToolbarType.ONLY_LOGO && toolbarType != ToolbarType.ONLY_LOGO) {
      setMargins(true)
      setBackgroundInterpolation(0f)
      statusBar?.setFilled(isFilled = false, animated = false)
      setBackgroundFilled(false)
    }
    // fixme toolbar types
    else if (type == ToolbarType.BACK_BUTTON && toolbarType != ToolbarType.BACK_BUTTON ||
      type == ToolbarType.ACTION && toolbarType != ToolbarType.ACTION
    ) {
      expandToolbar(true)
    } else if (type != toolbarType && (
      toolbarType == ToolbarType.BACK_BUTTON ||
        toolbarType == ToolbarType.ACTION
      )
    ) {
      expandToolbar(false)
    }
    toolbarType = type
  }

  fun setIsTransparent(isTransparent: Boolean) {
    applyRippleEnabled(!isTransparent)
    this.isTransparent = isTransparent
    if (isTransparent) {
      colorAnimator?.cancel()
      if (isFilled) {
        val colorAnimator = ValueAnimator.ofObject(
          ArgbEvaluator(),
          colorSurfaceNormal(),
          colorTransparent
        )
        colorAnimator.duration = animationSpeed
        createShapeValueAnimator(colorAnimator)
      } else {
        val colorAnimator = ValueAnimator.ofObject(
          ArgbEvaluator(),
          colorSurfaceFilled(),
          colorTransparent
        )
        colorAnimator.duration = animationSpeed
        createShapeValueAnimator(colorAnimator)
      }
    } else {
      if (isFilled) {
        val colorAnimator = ValueAnimator.ofObject(
          ArgbEvaluator(),
          colorSurfaceNormal(),
          colorSurfaceFilled()
        )
        colorAnimator.duration = animationSpeed
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
  }

  fun setStatusBar(statusBar: StatusBar) {
    this.statusBar = statusBar
  }

  override fun onThemeChanged() {
    super.onThemeChanged()

    toolbarBackground.fillColor = if (isFilled) {
      colorSurfaceFilled()
    } else {
      colorSurfaceNormal()
    }.colorStateList
    toolbarView.background = toolbarBackground
  }
}
