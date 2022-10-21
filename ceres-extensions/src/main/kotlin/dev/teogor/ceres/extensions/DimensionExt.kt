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

import android.content.res.Resources
import android.view.View

/**
 * Converts to dp.
 */
val Int.pxToDp: Int
  get() = (this / Resources.getSystem().displayMetrics.density).toInt()

/**
 * Converts to px.
 */
val Int.dpToPx: Int
  get() = (this * Resources.getSystem().displayMetrics.density).toInt()

/**
 * Converts to px.
 */
val Int.spToPx: Int
  get() = (this * Resources.getSystem().displayMetrics.scaledDensity).toInt()

/**
 * Converts to px and takes into account LTR/RTL layout.
 */
val Float.dpToPxEnd: Float
  get() = this * Resources.getSystem().displayMetrics.density *
    if (Resources.getSystem().isLTR) 1 else -1

val Resources.isLTR
  get() = configuration.layoutDirection == View.LAYOUT_DIRECTION_LTR
