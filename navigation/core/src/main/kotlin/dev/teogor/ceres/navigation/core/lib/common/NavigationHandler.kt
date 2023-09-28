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

@file:OptIn(ExperimentalMaterialApi::class, ExperimentalMaterialApi::class)

package dev.teogor.ceres.navigation.core.lib.common

import androidx.activity.compose.ReportDrawn
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import dev.teogor.ceres.ui.designsystem.modalsheet.ModalBottomSheetState
import dev.teogor.ceres.ui.foundation.lib.common.emptyComposable

data class NavigationData(
  val sheetState: ModalBottomSheetState,
  val content: SingleLiveEvent<@Composable (ColumnScope) -> Unit> = SingleLiveEvent(),
) {
  @OptIn(ExperimentalMaterialApi::class)
  fun isVisible(): Boolean = sheetState.isVisible
}

@Composable
fun rememberNavigationModules(
  content: String,
  bottomNavSheetState: ModalBottomSheetState,
): NavigationModules = remember(content, bottomNavSheetState) {
  NavigationModules(content, bottomNavSheetState)
}

class NavigationModules(
  val content: String,
  bottomNavSheetState: ModalBottomSheetState,
) {

  @OptIn(ExperimentalMaterialApi::class)
  val bottomNav by mutableStateOf(
    NavigationData(
      sheetState = bottomNavSheetState,
      content = SingleLiveEvent<@Composable (ColumnScope) -> Unit>(),
    ),
  )
}

@Composable
fun OnScreenReady(
  predicate: () -> Boolean,
  block: @Composable () -> Unit,
) {
  if (predicate()) {
    ReportDrawn()
    block()
  }
}

val LocalBottomSheetVM = staticCompositionLocalOf<BottomSheetState> {
  error("No LocalBottomSheetVM provided")
}

@Composable
private fun bottomSheetVM() = LocalBottomSheetVM.current

@Composable
fun ShowBottomSheet(
  onDismiss: () -> Unit = {},
  content: @Composable (ColumnScope) -> Unit,
) {
  var previousIsHiding by remember { mutableStateOf(true) }
  var dismissBottomSheet by remember { mutableStateOf(false) }
  val isHiding = bottomSheetVM().isHiding.value
  LaunchedEffect(bottomSheetVM().isHiding.value) {
    if (isHiding && !previousIsHiding) {
      dismissBottomSheet = true
    }
    previousIsHiding = isHiding
  }
  if (dismissBottomSheet) {
    dismissBottomSheet = false
    HideBottomSheet()
    onDismiss()
  }
  bottomSheetVM().updateContent(newContent = content)
}

@Composable
fun HideBottomSheet() {
  bottomSheetVM().updateContent(newContent = emptyComposable)
}

class BottomSheetState : ViewModel() {
  private val _content = mutableStateOf<@Composable (ColumnScope) -> Unit>(emptyComposable)
  val content: State<@Composable (ColumnScope) -> Unit> = _content

  private val _isHiding = mutableStateOf(false)
  val isHiding: State<Boolean> = _isHiding

  private val _bottomSpace = mutableStateOf(0.dp)
  val bottomSpace: State<Dp> = _bottomSpace

  fun updateContent(newContent: @Composable (ColumnScope) -> Unit) {
    _content.value = newContent
  }

  fun setIsHiding(value: Boolean) {
    _isHiding.value = value
  }

  fun setBottomSpace(value: Dp) {
    _bottomSpace.value = value
  }
}
