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

// TODO: Remove float properties? Move to converters?
class IntegerCMYKColor : IntegerColor(
  COMPONENTS_COUNT,
  DEFAULT_VALUES
) {

  override val colorKey = ColorKey.CMYK

  override val alpha: Float
    get() {
      return intA / Component.A.maxValue.toFloat()
    }

  var intA: Int
    get() {
      return intValues[Component.A.index]
    }
    set(value) {
      setValue(
        Component.A.index,
        value,
        Component.A.minValue,
        Component.A.maxValue
      )
    }

  var floatC: Float
    get() {
      return intC / Component.C.maxValue.toFloat()
    }
    set(value) {
      intC = (value * Component.C.maxValue).toInt()
    }
  var intC: Int
    get() {
      return intValues[Component.C.index]
    }
    set(value) {
      setValue(
        Component.C.index,
        value,
        Component.C.minValue,
        Component.C.maxValue
      )
    }

  var floatM: Float
    get() {
      return intM / Component.M.maxValue.toFloat()
    }
    set(value) {
      intM = (value * Component.M.maxValue).toInt()
    }
  var intM: Int
    get() {
      return intValues[Component.M.index]
    }
    set(value) {
      setValue(
        Component.M.index,
        value,
        Component.M.minValue,
        Component.M.maxValue
      )
    }

  var floatY: Float
    get() {
      return intY / Component.Y.maxValue.toFloat()
    }
    set(value) {
      intY = (value * Component.Y.maxValue).toInt()
    }
  var intY: Int
    get() {
      return intValues[Component.Y.index]
    }
    set(value) {
      setValue(
        Component.Y.index,
        value,
        Component.Y.minValue,
        Component.Y.maxValue
      )
    }

  var floatK: Float
    get() {
      return intK / Component.K.maxValue.toFloat()
    }
    set(value) {
      intK = (value * Component.K.maxValue).toInt()
    }
  var intK: Int
    get() {
      return intValues[Component.K.index]
    }
    set(value) {
      setValue(
        Component.K.index,
        value,
        Component.K.minValue,
        Component.K.maxValue
      )
    }

  override fun clone(): IntegerCMYKColor {
    return super.clone() as IntegerCMYKColor
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false
    if (!super.equals(other)) return false

    other as IntegerCMYKColor

    if (colorKey != other.colorKey) return false

    return true
  }

  override fun hashCode(): Int {
    var result = super.hashCode()
    result = 31 * result + colorKey.hashCode()
    return result
  }

  // TODO: Make Component top-level?
  // TODO: Make tree?
  // TODO: Use range?
  enum class Component(
    val defaultValue: Int,
    val minValue: Int,
    val maxValue: Int
  ) {
    C(
      0,
      0,
      100
    ),
    M(
      0,
      0,
      100
    ),
    Y(
      0,
      0,
      100
    ),
    K(
      0,
      0,
      100
    ),
    A(
      255,
      0,
      255
    );

    // TODO: Review approach
    val index: Int
      get() {
        return ordinal
      }

    // TODO: Adapt for non-zero min values
    val normalizedDefaultValue: Float
      get() {
        return defaultValue / maxValue.toFloat()
      }
  }

  companion object {
    private val COMPONENTS_COUNT = Component.values().size

    private val DEFAULT_VALUES = Component
      .values().map { it.defaultValue }.toIntArray()
  }
}
