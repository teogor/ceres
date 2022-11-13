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
import dev.teogor.ceres.components.system.InsetsConfigurator
import dev.teogor.ceres.components.toolbar.ToolbarType
import dev.teogor.ceres.components.toolbar.ToolbarViewData
import dev.teogor.ceres.components.view.ToolBar

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
      toolbarType = ToolbarType.NOT_SET
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
  val insetsStream: SingleLiveEvent<InsetsConfigurator> = SingleLiveEvent()

  val resources: Resources = ApplicationInitializer.context.resources

  open val insets: InsetsConfigurator = InsetsConfigurator()

  open fun onActivityCreated() {
  }

  open fun onFragmentCreated() {
    getToolbarView(type = toolBarBuilder.type).apply {
      setIsTransparent(toolBarBuilder.isTransparent)
      setTitleText(toolBarBuilder.title)
      toolbarViewData.value = this
    }
    insetsStream.value = insets
  }

  private fun getToolbarView(type: ToolbarType): ToolbarViewData {
    return when (type) {
      ToolbarType.HIDDEN -> ToolbarViewData(
        showToolbar = false,
        showTitle = false,
        showLogo = false,
        showBackButton = false,
        showActionElements = false,
        toolbarType = type
      )
      ToolbarType.ROUNDED -> ToolbarViewData(
        showToolbar = true,
        showTitle = true,
        showLogo = true,
        showBackButton = false,
        showActionElements = false,
        toolbarType = type
      )
      ToolbarType.BACK_BUTTON -> ToolbarViewData(
        showToolbar = true,
        showTitle = true,
        showLogo = false,
        showBackButton = true,
        showActionElements = false,
        toolbarType = type
      )
      ToolbarType.ACTION -> ToolbarViewData(
        showToolbar = true,
        showTitle = false,
        showLogo = false,
        showBackButton = true,
        showActionElements = true,
        toolbarType = type
      )
      ToolbarType.COLLAPSABLE -> ToolbarViewData(
        showToolbar = true,
        showTitle = true,
        showLogo = true,
        showBackButton = false,
        showActionElements = false,
        toolbarType = type
      )
      ToolbarType.ONLY_LOGO -> ToolbarViewData(
        showToolbar = true,
        showTitle = false,
        showLogo = true,
        showBackButton = false,
        showActionElements = false,
        toolbarType = type
      )
      ToolbarType.NOT_SET -> ToolbarViewData(
        showToolbar = true,
        showTitle = false,
        showLogo = true,
        showBackButton = false,
        showActionElements = false,
        toolbarType = type
      )
    }
  }

  open val toolBarBuilder: ToolBar.Builder
    get() = ToolBar.Builder()

  fun setToolbarFilled(isFilled: Boolean) {
    val s = toolbarViewData.value!!
    s.setIsFilled(isFilled)
    toolbarViewData.value = s
  }

  @Deprecated(message = "Deprecated due to low performance. Use `toolBarBuilder`")
  fun setToolbarType(toolbarType: ToolbarType) {
    val toolbarView = getToolbarView(toolbarType)
    toolbarViewData.value = toolbarView
  }

  @Deprecated(message = "Deprecated due to low performance. Use `toolBarBuilder`")
  fun setToolbarTitle(@StringRes titleStringId: Int) {
    toolbarViewData.value?.apply {
      setTitleText(titleStringId)
      toolbarViewData.value = this
    }
  }

  @Deprecated(message = "Deprecated due to low performance. Use `toolBarBuilder`")
  fun setTransparentToolbar(isTransparent: Boolean) {
    toolbarViewData.value?.apply {
      setIsTransparent(isTransparent)
      toolbarViewData.value = this
    }
  }

  fun showBottomNavigation(showBottomNavigation: Boolean) {
    this.showBottomNavigation.value = showBottomNavigation
  }

  open fun onRefresh() {
  }
}
