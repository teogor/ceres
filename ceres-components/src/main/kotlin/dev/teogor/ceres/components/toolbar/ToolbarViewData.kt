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

package dev.teogor.ceres.components.toolbar

import androidx.annotation.StringRes
import androidx.lifecycle.MutableLiveData

class ToolbarViewData(
  showToolbar: Boolean,
  showTitle: Boolean,
  showLogo: Boolean,
  showBackButton: Boolean,
  showActionElements: Boolean,
  toolbarType: ToolbarType
) {

  val showToolbar = MutableLiveData(true)
  val showLogo = MutableLiveData(true)
  val showBackButton = MutableLiveData(false)
  val showTitle = MutableLiveData(false)
  val showActionElements = MutableLiveData(false)
  val toolbarType = MutableLiveData(toolbarType)
  val titleText = MutableLiveData(dev.teogor.ceres.components.R.string.empty)

  init {
    this.showToolbar.value = showToolbar
    this.showTitle.value = showTitle
    this.showLogo.value = showLogo
    this.showBackButton.value = showBackButton
    this.showActionElements.value = showActionElements
    this.toolbarType.value = toolbarType
  }

  fun setTitleText(@StringRes titleText: Int) {
    this.titleText.value = titleText
  }
}
