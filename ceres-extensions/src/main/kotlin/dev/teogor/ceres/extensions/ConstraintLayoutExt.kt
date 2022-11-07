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

@file:JvmName("ConstraintLayoutExt")

package dev.teogor.ceres.extensions

import androidx.annotation.IdRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet

fun ConstraintLayout.applyConstraintSet(
  constraintSetUnit: ConstraintSet.() -> Unit = {}
) {
  apply {
    val constraintSet = ConstraintSet()
    constraintSet.clone(this)
    constraintSetUnit(constraintSet)
    constraintSet.applyTo(this)
  }
}

fun ConstraintSet.centerInParent(
  @IdRes id: Int
) {
  apply {
    connect(
      id,
      ConstraintSet.START,
      ConstraintSet.PARENT_ID,
      ConstraintSet.START
    )
    connect(
      id,
      ConstraintSet.TOP,
      ConstraintSet.PARENT_ID,
      ConstraintSet.TOP
    )
    connect(
      id,
      ConstraintSet.END,
      ConstraintSet.PARENT_ID,
      ConstraintSet.END
    )
    connect(
      id,
      ConstraintSet.BOTTOM,
      ConstraintSet.PARENT_ID,
      ConstraintSet.BOTTOM
    )
  }
}
