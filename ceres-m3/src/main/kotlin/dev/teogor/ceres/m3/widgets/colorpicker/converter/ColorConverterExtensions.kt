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

package dev.teogor.ceres.m3.widgets.colorpicker.converter

import androidx.annotation.ColorInt
import dev.teogor.ceres.m3.widgets.colorpicker.model.Color

@ColorInt
fun Color.toColorInt(): Int {
  return ColorConverterHub.getConverterByKey(colorKey).convertToColorInt(this)
}

@ColorInt
fun Color.toOpaqueColorInt(): Int {
  return ColorConverterHub.getConverterByKey(colorKey).convertToOpaqueColorInt(this)
}

@ColorInt
fun Color.toPureHueColorInt(): Int {
  return ColorConverterHub.getConverterByKey(colorKey).convertToPureHueColorInt(this)
}

fun Color.getRInt(): Int {
  return android.graphics.Color.red(toColorInt())
}

fun Color.getGInt(): Int {
  return android.graphics.Color.green(toColorInt())
}

fun Color.getBInt(): Int {
  return android.graphics.Color.blue(toColorInt())
}

@ColorInt
fun Color.toContrastColor(mode: ContrastColorAlphaMode = ContrastColorAlphaMode.NONE): Int {
  val black = (getRInt() * 0.299f + getGInt() * 0.587f + getBInt() * 0.114f > 186)
  val highAlphaContrastColor = if (black) {
    android.graphics.Color.BLACK
  } else {
    android.graphics.Color.WHITE
  }
  return when (mode) {
    ContrastColorAlphaMode.NONE -> {
      highAlphaContrastColor
    }
    ContrastColorAlphaMode.LIGHT_BACKGROUND -> {
      if (alpha < 0.5f) {
        android.graphics.Color.BLACK
      } else {
        highAlphaContrastColor
      }
    }
    ContrastColorAlphaMode.DARK_BACKGROUND -> {
      if (alpha < 0.5f) {
        android.graphics.Color.WHITE
      } else {
        highAlphaContrastColor
      }
    }
  }
}

fun Color.setFromColorInt(@ColorInt value: Int) {
  ColorConverterHub.getConverterByKey(colorKey).setFromColorInt(
    this,
    value
  )
}

enum class ContrastColorAlphaMode {
  NONE,
  LIGHT_BACKGROUND,
  DARK_BACKGROUND
}
