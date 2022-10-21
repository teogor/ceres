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

package dev.teogor.ceres.components.app

import android.content.res.Resources
import androidx.annotation.StringRes
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import com.zeoflow.startup.ktx.ApplicationInitializer
import dev.teogor.ceres.components.events.SingleLiveEvent
import dev.teogor.ceres.components.events.UiEvent
import dev.teogor.ceres.components.toolbar.ToolbarType
import dev.teogor.ceres.components.toolbar.ToolbarViewData

open class BaseViewModel : ViewModel(), BaseCommon {

  /**
   * UI Binding
   */
  val toolbarViewData = MutableLiveData(
    ToolbarViewData(
      showToolbar = true,
      showTitle = false,
      showLogo = true,
      showBackButton = false,
      showActionElements = false,
      toolbarType = ToolbarType.ROUNDED
    )
  )
  val showBottomNavigation = MutableLiveData(true)

  /**
   * Activity Observables
   */
  val navigate: SingleLiveEvent<NavDirections> = SingleLiveEvent()
  val onBackPressed: SingleLiveEvent<Boolean> = SingleLiveEvent()
  val onThemeChanged: SingleLiveEvent<Boolean> = SingleLiveEvent()
  val uiEventStream: SingleLiveEvent<UiEvent> = SingleLiveEvent()

  val resources: Resources = ApplicationInitializer.context.resources

  open fun onActivityCreated() {
  }

  open fun onFragmentCreated() {
  }

  fun setToolbarType(toolbarType: ToolbarType) {
    val toolbarView = when (toolbarType) {
      ToolbarType.HIDDEN -> ToolbarViewData(
        showToolbar = false,
        showTitle = false,
        showLogo = false,
        showBackButton = false,
        showActionElements = false,
        toolbarType = toolbarType
      )
      ToolbarType.ROUNDED -> ToolbarViewData(
        showToolbar = true,
        showTitle = true,
        showLogo = true,
        showBackButton = false,
        showActionElements = false,
        toolbarType = toolbarType
      )
      ToolbarType.BACK_BUTTON -> ToolbarViewData(
        showToolbar = true,
        showTitle = true,
        showLogo = false,
        showBackButton = true,
        showActionElements = false,
        toolbarType = toolbarType
      )
      ToolbarType.ACTION -> ToolbarViewData(
        showToolbar = true,
        showTitle = false,
        showLogo = false,
        showBackButton = true,
        showActionElements = true,
        toolbarType = toolbarType
      )
      ToolbarType.COLLAPSABLE -> ToolbarViewData(
        showToolbar = true,
        showTitle = true,
        showLogo = true,
        showBackButton = false,
        showActionElements = false,
        toolbarType = toolbarType
      )
    }
    toolbarViewData.value = toolbarView
  }

  fun setToolbarTitle(@StringRes titleStringId: Int) {
    val toolbarView = toolbarViewData.value!!
    toolbarView.setTitleText(titleStringId)
    toolbarViewData.value = toolbarView
  }

  fun showBottomNavigation(showBottomNavigation: Boolean) {
    this.showBottomNavigation.value = showBottomNavigation
  }

  open fun onRefresh() {
  }
}
