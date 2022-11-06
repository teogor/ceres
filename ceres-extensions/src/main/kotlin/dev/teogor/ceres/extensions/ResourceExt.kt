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

import androidx.annotation.DrawableRes
import androidx.annotation.FontRes
import androidx.annotation.IdRes
import androidx.annotation.StringRes

const val defaultResId = -1

inline val @receiver:IdRes Int.validIdRes: Boolean
  get() = this != defaultResId

inline val @receiver:FontRes Int.validFontRes: Boolean
  get() = this != defaultResId

inline val @receiver:DrawableRes Int.validDrawableRes: Boolean
  get() = this != defaultResId

inline val @receiver:StringRes Int.validStringRes: Boolean
  get() = this != defaultResId

fun @receiver:IdRes Int.validIdRes(
  invalidId: Int = defaultResId,
  runnable: Int.() -> Unit
) {
  if (this != invalidId) {
    runnable()
  }
}

fun @receiver:FontRes Int.validFontRes(
  invalidId: Int = defaultResId,
  runnable: Int.() -> Unit
) {
  if (this != invalidId) {
    runnable()
  }
}

fun @receiver:DrawableRes Int.validDrawableRes(
  invalidId: Int = defaultResId,
  runnable: Int.() -> Unit
) {
  if (this != invalidId) {
    runnable()
  }
}

fun @receiver:StringRes Int.validStringRes(
  invalidId: Int = defaultResId,
  runnable: Int.() -> Unit
) {
  if (this != invalidId) {
    runnable()
  }
}
