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

package dev.teogor.ceres.m3.widgets.extension

import android.graphics.Color
import androidx.core.graphics.ColorUtils
import dev.teogor.ceres.m3.widgets.colorpicker.model.IntegerHSLColor

fun IntegerHSLColor.toHex(): String {
  val color = floatArrayOf(floatH, floatS, floatL)
  val intColor = ColorUtils.HSLToColor(color)
  val red: Int = Color.red(intColor)
  val green: Int = Color.green(intColor)
  val blue: Int = Color.blue(intColor)
  return String.format("#%02x%02x%02x", red, green, blue)
}
