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
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import androidx.annotation.IdRes
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.animation.doOnEnd
import dev.teogor.ceres.components.toolbar.ToolbarType
import dev.teogor.ceres.components.view.*
import dev.teogor.ceres.components.view.ToolBar
import dev.teogor.ceres.core.drawable.ArgbEvaluator
import dev.teogor.ceres.extensions.*
import dev.teogor.ceres.m3.R
import dev.teogor.ceres.m3.extension.applyRippleEnabled

class ToolBar constructor(
  context: Context,
  attrs: AttributeSet
) : Bar(context, attrs) {

  @IdRes
  private val idToolbar = generateViewId()
  private val toolbarView = View(context)
  private val toolbarBackground = ToolBar.Background()

  private val verticalMargin = 8.dpToPx
  private val horizontalMargin = 10.dpToPx
  private val toolbarHeight = resources.getDimension(R.dimen.toolbar_height).toInt()
  private val toolbarHeightRounded = toolbarHeight - 2 * verticalMargin
  private val animationSpeed = 400L

  private var statusBar: StatusBar? = null
  private var interpolatorAnimator: ValueAnimator = ValueAnimator.ofFloat(
    0f,
    1f
  )

  private var data: ToolBar.Data = ToolBar.Data(
    type = ToolbarType.NOT_SET,
    isTransparent = false,
    isFilled = false
  )

  init {
    prepareRoot()
    prepareToolbar()

    applyConstraintSet {
      setToolbarConstraints(this)
    }
  }

  private fun prepareRoot() {
    setBackgroundColor(colorTransparent)
  }

  private fun prepareToolbar() {
    // set id for toolbar
    toolbarView.id = idToolbar

    // set toolbar layout params
    toolbarView.applyConstraintLayoutMargins(
      verticalMargins = verticalMargin,
      horizontalMargins = horizontalMargin,
      height = toolbarHeightRounded,
      width = 0
    )

    // add toolbar
    addView(toolbarView)
  }

  private fun setToolbarConstraints(constraintSet: ConstraintSet) {
    constraintSet.centerInParent(idToolbar)
  }

  /**
   * API Methods
   */
  fun setData(data: ToolBar.Data) {
    if (this.data.wasNotSet()) {
      with(data) {
        toolbarBackground.applyHidden(
          if (isTransparent) {
            Color.TRANSPARENT
          } else {
            data.color
          }
        )
        toolbarView.applyConstraintLayoutMargins(
          verticalMargins = verticalMargins,
          horizontalMargins = horizontalMargins
        )
        applyConstraintSet {
          setToolbarConstraints(this)
        }
        toolbarBackground.applyCornerTreatment(cornerSize / toolbarBackground.cornersSize)
      }
    } else {
      applyData(data)
    }
    this.data = data
  }

  private fun applyData(newData: ToolBar.Data) {
    applyRippleEnabled(newData.rippleEnabled)
    applyTypeEvent(newData)
    toolbarBackground.applyOn(toolbarView)
    val typeChanged = data.typeChanged(newData)
    if (!typeChanged) {
      with(newData) {
        toolbarBackground.applyHidden(
          if (isTransparent) {
            Color.TRANSPARENT
          } else {
            if (isFilled) {
              colorSurfaceFilled()
            } else {
              colorSurfaceNormal()
            }
          }
        )
        statusBar?.setFilledColor(
          if (isFilled && !isTransparent) {
            colorSurfaceFilled()
          } else {
            colorSurfaceNormal()
          }
        )
      }
      return
    }

    val transparencyChanged = data.transparencyChanged(newData)
    val colorChanged = data.colorChanged(newData)
    val roundedTreatmentChanged = data.roundedTreatmentChanged(newData)
    val expandedChanged = data.expandedChanged(newData)

    val to = if (expandedChanged) {
      if (newData.increasingAnimator && !data.increasingAnimator) {
        0f
      } else {
        1f
      }
    } else {
      if (newData.increasingAnimator) {
        0f
      } else {
        1f
      }
    }
    val from = 1f - to
    interpolatorAnimator.setFloatValues(from, to)
    interpolatorAnimator.duration = animationSpeed

    interpolatorAnimator.addUpdateListener { animation ->
      val value = animation.animatedValue as Float
      if (roundedTreatmentChanged) {
        toolbarBackground.applyCornerTreatment(value)
      }
      if (expandedChanged) {
        val horizontalMargin = (horizontalMargin * value).toInt()
        val verticalMargin = (verticalMargin * value).toInt()
        toolbarView.applyConstraintLayoutMargins(
          verticalMargins = verticalMargin,
          horizontalMargins = horizontalMargin
        )
        applyConstraintSet {
          setToolbarConstraints(this)
        }
      }
    }
    interpolatorAnimator.doOnEnd {
      interpolatorAnimator.removeAllUpdateListeners()
      it.removeAllListeners()
    }
    interpolatorAnimator.start()

    if (colorChanged) {
      if (newData.isTransparent) {
        val colorAnimator = ValueAnimator.ofObject(
          ArgbEvaluator(),
          data.color,
          newData.color
        )
        colorAnimator.duration = animationSpeed / 2
        colorAnimator.addUpdateListener { animation ->
          val value = animation.animatedValue as Int
          toolbarBackground.applyHidden(value)
        }
        colorAnimator.doOnEnd {
          colorAnimator.removeAllUpdateListeners()
          it.removeAllListeners()
        }
        colorAnimator.start()
        val colorAnimatorTransparent = ValueAnimator.ofObject(
          ArgbEvaluator(),
          newData.color,
          Color.TRANSPARENT
        )
        colorAnimatorTransparent.duration = animationSpeed / 2
        colorAnimatorTransparent.addUpdateListener { animation ->
          val value = animation.animatedValue as Int
          toolbarBackground.applyHidden(value)
        }
        colorAnimatorTransparent.doOnEnd {
          colorAnimatorTransparent.removeAllUpdateListeners()
          it.removeAllListeners()
          toolbarBackground.applyHidden(Color.TRANSPARENT)
        }
        colorAnimatorTransparent.startDelay = animationSpeed / 2
        colorAnimatorTransparent.start()
      } else if (data.isTransparent) {
        val colorAnimator = ValueAnimator.ofObject(
          ArgbEvaluator(),
          Color.TRANSPARENT,
          data.color
        )
        colorAnimator.duration = animationSpeed / 2
        colorAnimator.addUpdateListener { animation ->
          val value = animation.animatedValue as Int
          toolbarBackground.applyHidden(value)
        }
        colorAnimator.doOnEnd {
          colorAnimator.removeAllUpdateListeners()
          it.removeAllListeners()
        }
        colorAnimator.start()
        val colorAnimatorTransparent = ValueAnimator.ofObject(
          ArgbEvaluator(),
          data.color,
          newData.color
        )
        colorAnimatorTransparent.duration = animationSpeed / 2
        colorAnimatorTransparent.addUpdateListener { animation ->
          val value = animation.animatedValue as Int
          toolbarBackground.applyHidden(value)
        }
        colorAnimatorTransparent.doOnEnd {
          colorAnimatorTransparent.removeAllUpdateListeners()
          it.removeAllListeners()
        }
        colorAnimatorTransparent.startDelay = animationSpeed / 2
        colorAnimatorTransparent.start()
      } else {
        val colorAnimator = ValueAnimator.ofObject(
          ArgbEvaluator(),
          data.color,
          newData.color
        )
        colorAnimator.duration = animationSpeed
        colorAnimator.addUpdateListener { animation ->
          val value = animation.animatedValue as Int
          toolbarBackground.applyHidden(value)
        }
        colorAnimator.doOnEnd {
          colorAnimator.removeAllUpdateListeners()
          it.removeAllListeners()
        }
        colorAnimator.start()
      }
    }
  }

  /**
   * ToolbarType.HIDDEN -> {
   *   visibility = hidden
   *   background = transparent
   * }
   * ToolbarType.ROUNDED -> {
   *   visibility = visible
   *   background = normal
   * }
   * ToolbarType.COLLAPSABLE -> {
   *   visibility = visible
   *   background = filled on default / normal on scroll
   * }
   * ToolbarType.BACK_BUTTON -> {
   *   visibility = visible
   *   background = normal
   * }
   * ToolbarType.ACTION -> {
   *   visibility = visible
   *   background = normal
   * }
   * ToolbarType.ONLY_LOGO -> {
   *   visibility = hidden
   *   background = transparent
   * }
   */
  private fun applyTypeEvent(newData: ToolBar.Data) {
    when (newData.type) {
      ToolbarType.HIDDEN -> {
        withToolbarHidden(newData)
        withToolbarRectangle(newData)
        withToolbarExpanded(newData)
        setBackgroundColor(colorTransparent)
      }
      ToolbarType.ONLY_LOGO -> {
        withToolbarHidden(newData)
        withToolbarRectangle(newData)
        withToolbarExpanded(newData)
        setBackgroundColor(colorTransparent)
      }
      ToolbarType.ROUNDED -> {
        withToolbarVisible(newData)
        withToolbarRounded(newData)
        withToolbarShrinked(newData)
        setBackgroundColor(colorSurfaceNormal())
      }
      ToolbarType.COLLAPSABLE -> {
        withToolbarVisible(newData, false)
        withToolbarRectangle(newData)
        withToolbarExpanded(newData)
        setBackgroundColor(colorSurfaceNormal())
      }
      ToolbarType.BACK_BUTTON -> {
        withToolbarVisible(newData, false)
        withToolbarRectangle(newData)
        withToolbarExpanded(newData)
        setBackgroundColor(colorSurfaceNormal())
      }
      ToolbarType.ACTION -> {
        withToolbarVisible(newData, false)
        withToolbarRectangle(newData)
        withToolbarExpanded(newData)
        setBackgroundColor(colorSurfaceNormal())
      }
      else -> {
        // shouldn't be here
        withToolbarVisible(newData, false)
        withToolbarRectangle(newData)
        withToolbarExpanded(newData)
        setBackgroundColor(colorSurfaceNormal())
      }
    }
  }

  private fun withToolbarHidden(newData: ToolBar.Data) {
    newData.isHidden = true
    newData.color = colorSurfaceNormal()
  }

  private fun withToolbarVisible(newData: ToolBar.Data, isFilled: Boolean = true) {
    newData.isHidden = false
    newData.color = if (isFilled) {
      colorSurfaceFilled()
    } else {
      colorSurfaceNormal()
    }
  }

  private fun withToolbarRectangle(newData: ToolBar.Data) {
    newData.isRounded = false
    newData.cornerSize = 0f
  }

  private fun withToolbarRounded(newData: ToolBar.Data) {
    newData.isRounded = true
    newData.cornerSize = toolbarBackground.cornersSize
  }

  private fun withToolbarExpanded(newData: ToolBar.Data) {
    newData.isExpanded = true
    newData.verticalMargins = 0
    newData.horizontalMargins = 0
  }

  private fun withToolbarShrinked(newData: ToolBar.Data) {
    newData.isExpanded = false
    newData.verticalMargins = verticalMargin
    newData.horizontalMargins = horizontalMargin
  }

  fun setStatusBar(statusBar: StatusBar) {
    this.statusBar = statusBar
  }

  /**
   * Apply the new color scheme.
   */
  override fun onThemeChanged() {
    super.onThemeChanged()

    setData(data)
  }
}
