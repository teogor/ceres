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

package dev.teogor.ceres.m3.widgets.colorpicker.model

import android.util.Log

abstract class IntegerColor(
  componentsCount: Int,
  defaultValues: IntArray? = null
) : Color {

  protected val intValues = IntArray(componentsCount)

  init {
    defaultValues?.copyInto(intValues)
  }

  protected fun setValue(index: Int, value: Int, minValue: Int, maxValue: Int) {
    intValues[index] = value.coerceIn(
      minValue,
      maxValue
    )
    Log.d(
      TAG,
      "Set $this from ${intValues.contentToString()}"
    )
  }

  fun copyValuesTo(array: IntArray) {
    intValues.copyInto(array)
  }

  fun copyValuesFrom(array: IntArray) {
    if (array.size != intValues.size) {
      Log.d(
        TAG,
        "Copying values from array with different size"
      )
    }
    array.copyInto(intValues)
  }

  fun setFrom(color: IntegerColor) {
    color.copyValuesTo(intValues)
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as IntegerColor

    if (!intValues.contentEquals(other.intValues)) return false

    return true
  }

  override fun hashCode(): Int {
    return intValues.contentHashCode()
  }

  companion object {
    private const val TAG = "IntegerColor"
  }
}
