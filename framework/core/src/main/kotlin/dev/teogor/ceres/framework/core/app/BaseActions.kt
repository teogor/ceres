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

package dev.teogor.ceres.framework.core.app

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavOptions
import dev.teogor.ceres.framework.core.screen.ScreenInfo
import dev.teogor.ceres.framework.core.screen.isVisible
import dev.teogor.ceres.framework.core.screen.snackbar
import dev.teogor.ceres.navigation.core.ScreenRoute

data class BaseActions(
  val appState: CeresAppState,
  val bottomSpace: Dp = 0.dp,
)

@Deprecated("use Navigation local provider - LocalNavigationParameters.current")
fun BaseActions.navigateTo(
  screenRoute: ScreenRoute,
  navOptions: NavOptions = NavOptions.Builder()
    .build(),
) {
  appState.navController.navigate(screenRoute.route, navOptions)
}

fun BaseActions.setScreenInfo(screenInfoAction: ScreenInfo.() -> Unit) {
  with(appState) {
    screenInfo.snackbar {
      isVisible {
        false
      }
    }
    screenInfo.apply(screenInfoAction)

    // Menu Configuration
    shouldShowNavBar = screenInfo.showNavBar

    // Toolbar Area Configuration
    toolbarTokens = screenInfo.toolbarTokens
    isStatusBarVisible = screenInfo.isStatusBarVisible

    // System Nav Bar Configuration
    isNavigationBarVisible = screenInfo.isNavigationBarVisible

    // Floating Button Configuration
    floatingButtonView = screenInfo.floatingButtonView

    // Snackbar Configuration
    snackbarView = screenInfo.snackbarView
  }
}
