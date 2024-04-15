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

package dev.teogor.ceres.framework.core.screen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import dev.teogor.ceres.navigation.core.ScreenRoute
import dev.teogor.ceres.ui.designsystem.ButtonData
import dev.teogor.ceres.ui.designsystem.DropDownItem
import dev.teogor.ceres.ui.designsystem.Icon
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun rememberScreenInfo(
  showNavBar: Boolean = true,
  isStatusBarVisible: Boolean = true,
  isNavigationBarVisible: Boolean = true,
  floatingButtonView: FloatingButtonView = FloatingButtonView(),
  snackbarView: SnackbarView = SnackbarView(),
  toolbarTokens: ToolbarTokens = ToolbarTokens(),
): ScreenInfo {
  return remember {
    ScreenInfo(
      showNavBar,
      isStatusBarVisible,
      isNavigationBarVisible,
      floatingButtonView = floatingButtonView,
      snackbarView = snackbarView,
      toolbarTokens = toolbarTokens,
    )
  }
}

class ToolbarTokens {
  internal var title: String = ""
  internal var visible: Boolean = true
  internal var showBackButton: Boolean = false
  internal var showSettingsButton: Boolean = false
  internal var showEditButton: Boolean = false
  internal var showDropDownMenu: Boolean = false
  internal var dropDownItems: List<DropDownItem> = emptyList()
  internal var buttons: List<ButtonData> = emptyList()
}

// todo refactor
// region Screen Info Extensions
fun ScreenInfo.showNavBar(block: () -> Boolean) {
  this.showNavBar = block()
}

fun ScreenInfo.cleanFullScreen(block: () -> Boolean) {
  this.showNavBar = !block()
  this.toolbarTokens.visible = !block()
  this.isStatusBarVisible = !block()
}

fun ScreenInfo.statusBarOnly(block: () -> Boolean) {
  this.showNavBar = !block()
  this.toolbarTokens.visible = !block()
  this.isStatusBarVisible = true
}

fun ScreenInfo.fullScreen(block: () -> Boolean) {
  this.toolbarTokens.visible = !block()
  this.isStatusBarVisible = !block()
}

fun ScreenInfo.isStatusBarVisible(block: () -> Boolean) {
  this.isStatusBarVisible = block()
}

fun ScreenInfo.isNavigationBarVisible(block: () -> Boolean) {
  this.isNavigationBarVisible = block()
}

fun ScreenInfo.floatingButton(block: FloatingButtonView.() -> Unit) {
  this.floatingButtonView = FloatingButtonView().apply(block)
}

fun ScreenInfo.snackbar(block: SnackbarView.() -> Unit) {
  this.snackbarView = SnackbarView().apply(block)
}

fun ScreenInfo.toolbarTokens(block: ToolbarTokens.() -> Unit) {
  this.toolbarTokens = ToolbarTokens().apply(block)
}

// region Floating Button Tokens Extensions
fun FloatingButtonView.isVisible(block: () -> Boolean) {
  this.isVisible = block()
}

fun FloatingButtonView.icon(block: () -> ImageVector) {
  this.icon = block()
}

fun FloatingButtonView.contentDescription(block: () -> String) {
  this.contentDescription = block()
}

fun FloatingButtonView.tint(block: () -> Color) {
  this.tint = block()
}

fun FloatingButtonView.destination(block: () -> ScreenRoute) {
  this.screenRoute = block()
}

fun FloatingButtonView.action(block: () -> Unit) {
  this.action = block
}
// endregion

// region Snackbar Tokens Extensions
fun SnackbarView.isVisible(block: () -> Boolean) {
  this.isVisible = block()
}

fun SnackbarView.icon(block: () -> ImageVector) {
  this.icon = block()
}

fun SnackbarView.content(block: () -> String) {
  this.content = block()
}

fun SnackbarView.tint(block: () -> Color) {
  this.tint = block()
}

fun SnackbarView.action(block: () -> Unit) {
  this.action = block
}
// endregion

// region Toolbar Tokens Extensions
fun ToolbarTokens.toolbarButtons(block: MutableList<ButtonData>.() -> Unit) {
  val list = mutableListOf<ButtonData>()
  list.block()
  this.buttons = list
}

fun ToolbarTokens.toolbarDropDownItems(block: MutableList<DropDownItem>.() -> Unit) {
  val list = mutableListOf<DropDownItem>()
  list.block()
  this.dropDownItems = list
}

fun ToolbarTokens.toolbarTitle(block: () -> String) {
  this.title = block()
}

fun ToolbarTokens.showBackButton(block: () -> Boolean) {
  this.showBackButton = block()
}

fun ToolbarTokens.showSettingsButton(block: () -> Boolean) {
  this.showSettingsButton = block()
}

fun ToolbarTokens.showEditButton(block: () -> Boolean) {
  this.showSettingsButton = block()
}

fun ToolbarTokens.showDropDownMenu(block: () -> Boolean) {
  this.showDropDownMenu = block()
}

fun ToolbarTokens.isVisible(block: () -> Boolean) {
  this.visible = block()
}

fun MutableList<ButtonData>.toolbarButton(
  label: String,
  imageVector: ImageVector,
  clickable: () -> Unit,
): ButtonData {
  return ButtonData(
    label = label,
    icon = Icon(
      image = imageVector,
    ),
    onClick = {
      clickable()
    },
  ).apply {
    add(this)
  }
}

fun MutableList<ButtonData>.toolbarRotatableButton(
  label: String,
  imageVector: ImageVector,
  rotation: Float,
  clickable: () -> Unit,
): ButtonData {
  return ButtonData(
    label = label,
    icon = Icon(
      image = imageVector,
      rotation = rotation,
    ),
    onClick = {
      clickable()
    },
  ).apply {
    add(this)
  }
}

fun MutableList<ButtonData>.toolbarTwoStatesButton(
  label: String,
  stateDefault: ImageVector,
  stateActive: ImageVector,
  activeState: Boolean,
  clickable: () -> Unit,
): ButtonData {
  return ButtonData(
    label = label,
    icon = Icon(
      image = stateDefault,
      image2 = stateActive,
      activeState = activeState,
    ),
    onClick = {
      clickable()
    },
  ).apply {
    add(this)
  }
}

fun MutableList<DropDownItem>.toolbarDropDownItem(
  label: String,
  clickable: () -> Unit,
): DropDownItem {
  return DropDownItem(
    label = label,
    onClick = {
      clickable()
    },
  ).apply {
    add(this)
  }
}
// endregion
// endregion

@Composable
fun rememberSnackbarVisibility(
  visibilityMs: Long = 3000,
): MutableState<Boolean> {
  val snackbarVisible = remember {
    mutableStateOf(false)
  }
  val coroutineScope = rememberCoroutineScope()
  val effectJob = remember { mutableStateOf<Job?>(null) }
  DisposableEffect(snackbarVisible.value) {
    if (snackbarVisible.value) {
      val job = coroutineScope.launch {
        delay(visibilityMs)
        snackbarVisible.value = false
      }
      effectJob.value = job
    }

    onDispose {
      if (snackbarVisible.value) {
        effectJob.value?.cancel()
        effectJob.value = null
      }
    }
  }
  return snackbarVisible
}

@Deprecated("Use ScreenInfo.toolbarTokens(block: ToolbarTokens.() -> Unit)")
fun toolbarTokens(
  title: String = "",
  visible: Boolean = true,
  showBackButton: Boolean = false,
  showSettingsButton: Boolean = false,
  showEditButton: Boolean = false,
  showDropDownMenu: Boolean = false,
  dropDownItems: List<DropDownItem> = emptyList(),
  buttons: List<ButtonData> = emptyList(),
): ToolbarTokens {
  return ToolbarTokens().apply {
    this.title = title
    this.visible = visible
    this.showBackButton = showBackButton
    this.showSettingsButton = showSettingsButton
    this.showEditButton = showEditButton
    this.showDropDownMenu = showDropDownMenu
    this.dropDownItems = dropDownItems
    this.buttons = buttons
  }
}

class FloatingButtonView {
  internal var isVisible: Boolean = false
  internal var icon: ImageVector = Icons.Rounded.Add
  internal var contentDescription: String = ""
  internal var tint: Color? = null
  internal var screenRoute: ScreenRoute? = null
  internal var action: (() -> Unit)? = null
}

class SnackbarView {
  internal var isVisible: Boolean = false
  internal var icon: ImageVector = Icons.Rounded.Add
  internal var content: String = ""
  internal var tint: Color? = null
  internal var action: (() -> Unit)? = null
}

data class ScreenInfo(
  internal var showNavBar: Boolean = false,
  internal var isStatusBarVisible: Boolean = false,
  internal var isNavigationBarVisible: Boolean = false,
  internal var floatingButtonView: FloatingButtonView,
  internal var snackbarView: SnackbarView,

  internal var toolbarTokens: ToolbarTokens = ToolbarTokens(),
)

// todo default toolbar extensions ?? for instance navEnabled or full screen
