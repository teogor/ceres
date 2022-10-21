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

package dev.teogor.ceres.m3.widgets.colorpicker.group

import dev.teogor.ceres.m3.widgets.colorpicker.model.Color
import dev.teogor.ceres.m3.widgets.colorpicker.view.picker.ColorSeekBar

open class PickerGroup<C : Color> :
  ColorSeekBar.OnColorPickListener<ColorSeekBar<C>, C>,
  Iterable<ColorSeekBar<C>> {

  // Kinda prioritized collection
  private val pickers = linkedSetOf<ColorSeekBar<C>>()
  private val colorPickListeners =
    hashSetOf<ColorSeekBar.OnColorPickListener<ColorSeekBar<C>, C>>()

  fun addListener(listener: ColorSeekBar.OnColorPickListener<ColorSeekBar<C>, C>) {
    colorPickListeners.add(listener)
  }

  fun removeListener(listener: ColorSeekBar.OnColorPickListener<ColorSeekBar<C>, C>) {
    colorPickListeners.remove(listener)
  }

  fun clearListeners() {
    colorPickListeners.clear()
  }

  fun setColor(color: C) {
    pickers.firstOrNull()?.pickedColor = color
  }

  fun registerPicker(picker: ColorSeekBar<C>) {
    picker.addListener(this)
    pickers.add(picker)
    // Sync state on register
    notifyGroupOnBroadcastFrom(
      picker,
      picker.pickedColor
    )
  }

  fun unregisterPicker(picker: ColorSeekBar<C>) {
    picker.removeListener(this)
    pickers.remove(picker)
  }

  override fun onColorPicking(
    picker: ColorSeekBar<C>,
    color: C,
    value: Int,
    fromUser: Boolean
  ) {
    notifyGroupOnBroadcastFrom(
      picker,
      color
    )
    colorPickListeners.forEach {
      it.onColorPicking(
        picker,
        color,
        value,
        fromUser
      )
    }
  }

  override fun onColorPicked(
    picker: ColorSeekBar<C>,
    color: C,
    value: Int,
    fromUser: Boolean
  ) {
    notifyGroupOnBroadcastFrom(
      picker,
      color
    )
    colorPickListeners.forEach {
      it.onColorPicked(
        picker,
        color,
        value,
        fromUser
      )
    }
  }

  override fun onColorChanged(
    picker: ColorSeekBar<C>,
    color: C,
    value: Int
  ) {
    notifyGroupOnBroadcastFrom(
      picker,
      color
    )
    colorPickListeners.forEach {
      it.onColorChanged(
        picker,
        color,
        value
      )
    }
  }

  private fun notifyGroupOnBroadcastFrom(
    picker: ColorSeekBar<C>,
    color: C
  ) {
    disableListeners()
    pickers.filter { it != picker }.forEach {
      it.pickedColor = color
    }
    enableListeners()
  }

  private fun disableListeners() {
    setListenerEnabled(false)
  }

  private fun enableListeners() {
    setListenerEnabled(true)
  }

  private fun setListenerEnabled(isEnabled: Boolean) {
    pickers.forEach {
      it.notifyListeners = isEnabled
    }
  }

  override fun iterator(): Iterator<ColorSeekBar<C>> {
    return pickers.iterator()
  }
}
