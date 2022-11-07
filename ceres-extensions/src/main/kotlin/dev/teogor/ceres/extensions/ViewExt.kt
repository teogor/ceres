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

package dev.teogor.ceres.extensions

import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout

fun View.show(isVisible: Boolean) {
  this.visibility = if (isVisible) {
    View.VISIBLE
  } else {
    View.GONE
  }
}

fun View.hide(isHidden: Boolean) {
  this.visibility = if (isHidden) {
    View.GONE
  } else {
    View.VISIBLE
  }
}

fun View.applyConstraintLayoutMargins(
  verticalMargins: Int,
  horizontalMargins: Int,
  width: Int = 0,
  height: Int = 0
) {
  val layoutParams = ConstraintLayout.LayoutParams(
    width,
    height
  )
  layoutParams.setMargins(
    horizontalMargins,
    verticalMargins,
    horizontalMargins,
    verticalMargins
  )
  applyParams(layoutParams)
}

fun View.applyParams(
  params: ConstraintLayout.LayoutParams
) {
  apply {
    layoutParams = params
  }
}

/**
 * Returns true when this view's visibility is [View.VISIBLE], false otherwise.
 *
 * ```
 * if (view.isVisible) {
 *     // Behavior...
 * }
 * ```
 *
 * Setting this property to true sets the visibility to [View.VISIBLE], false to [View.GONE].
 *
 * ```
 * view.isVisible = true
 * ```
 */
inline var View.isVisible: Boolean
  get() = visibility == View.VISIBLE
  set(value) {
    visibility = if (value) View.VISIBLE else View.GONE
  }

/**
 * Returns true when this view's visibility is [View.INVISIBLE], false otherwise.
 *
 * ```
 * if (view.isInvisible) {
 *     // Behavior...
 * }
 * ```
 *
 * Setting this property to true sets the visibility to [View.INVISIBLE], false to [View.VISIBLE].
 *
 * ```
 * view.isInvisible = true
 * ```
 */
inline var View.isInvisible: Boolean
  get() = visibility == View.INVISIBLE
  set(value) {
    visibility = if (value) View.INVISIBLE else View.VISIBLE
  }
