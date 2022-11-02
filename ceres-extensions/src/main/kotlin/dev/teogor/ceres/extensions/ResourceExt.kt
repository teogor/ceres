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

inline val @receiver:IdRes Int.validIdRes: Boolean
  get() = this != -1

inline val @receiver:FontRes Int.validFontId: Boolean
  get() = this != -1

inline val @receiver:DrawableRes Int.validDrawableId: Boolean
  get() = this != -1

inline val @receiver:StringRes Int.validStringId: Boolean
  get() = this != -1
