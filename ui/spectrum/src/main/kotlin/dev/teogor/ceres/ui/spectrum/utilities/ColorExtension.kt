/*
 * Copyright 2023 teogor (Teodor Grigor)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dev.teogor.ceres.ui.spectrum.utilities

import androidx.annotation.ColorInt
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb

@ColorInt
fun Color.asIntColor(): Int {
  return this.toArgb()
}

@ColorInt
fun String.asIntColor(): Int {
  return android.graphics.Color.parseColor(this)
}

fun String.asColor(): Color {
  return Color(android.graphics.Color.parseColor(this))
}

fun Color.asHexColor(): String {
  val argb = this.toArgb()
  return String.format("#%08X", argb)
}
