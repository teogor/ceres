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

@file:JvmName("ToolBarExt")

package dev.teogor.ceres.components.view

import android.view.View
import androidx.annotation.ColorInt
import dev.teogor.ceres.components.toolbar.ToolbarType
import dev.teogor.ceres.extensions.colorStateList

fun ToolBar.Data.transparencyChanged(newData: ToolBar.Data): Boolean {
  return this.isTransparent != newData.isTransparent
}

fun ToolBar.Data.typeChanged(newData: ToolBar.Data): Boolean {
  return this.type != newData.type
}

fun ToolBar.Data.colorChanged(newData: ToolBar.Data): Boolean {
  if ((this.isTransparent || newData.isTransparent) && this.isTransparent != newData.isTransparent) {
    return true
  }
  return this.color != newData.color
}

fun ToolBar.Data.roundedTreatmentChanged(newData: ToolBar.Data): Boolean {
  return this.isRounded != newData.isRounded
}

fun ToolBar.Data.expandedChanged(newData: ToolBar.Data): Boolean {
  return this.isExpanded != newData.isExpanded
}

fun ToolBar.Data.hiddenType(newData: ToolBar.Data): Boolean {
  with(this to newData) {
    if (this.first.type == ToolbarType.HIDDEN) {
      return true
    }
    if (this.first.type == ToolbarType.ONLY_LOGO) {
      return true
    }
    if (this.second.type == ToolbarType.HIDDEN) {
      return true
    }
    if (this.second.type == ToolbarType.ONLY_LOGO) {
      return true
    }
  }
  return false
}

fun ToolbarType.hasFlags(): Boolean {
  return this == ToolbarType.ONLY_LOGO || this == ToolbarType.HIDDEN
}

fun ToolBar.Background.applyHidden(
  @ColorInt color: Int
) {
  this.apply {
    toolbarBackground.apply {
      fillColor = color.colorStateList
    }
  }
}

fun ToolBar.Background.applyCornerTreatment(
  value: Float
) {
  this.apply {
    toolbarBackground.apply {
      interpolation = value
    }
  }
}

fun ToolBar.Background.applyOn(
  view: View
) {
  view.background = this.toolbarBackground
}

fun ToolBar.Data.wasNotSet(): Boolean {
  return this.type == ToolbarType.NOT_SET
}
