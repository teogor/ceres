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

import android.util.Log
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.util.trace
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import dev.teogor.ceres.core.foundation.networkConnectivityUtils
import dev.teogor.ceres.core.network.NetworkMonitor
import dev.teogor.ceres.core.startup.ApplicationContextProvider
import dev.teogor.ceres.framework.core.screen.ScreenInfo
import dev.teogor.ceres.framework.core.screen.rememberScreenInfo
import dev.teogor.ceres.navigation.core.isRouteInBackStack
import dev.teogor.ceres.navigation.core.menu.TopLevelDestination
import dev.teogor.ceres.ui.foundation.lib.tools.TrackDisposableJank
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

@Composable
fun rememberCeresAppState(
  windowSizeClass: WindowSizeClass,
  networkMonitor: NetworkMonitor,
  coroutineScope: CoroutineScope = rememberCoroutineScope(),
  navController: NavHostController = rememberNavController(),
  screenInfo: ScreenInfo = rememberScreenInfo(),
  topLevelDestinations: List<TopLevelDestination>,
): CeresAppState {
  NavigationTrackingSideEffect(navController)
  return remember(
    navController,
    coroutineScope,
    windowSizeClass,
    screenInfo,
    networkMonitor,
    topLevelDestinations,
  ) {
    CeresAppState(
      navController,
      coroutineScope,
      windowSizeClass,
      screenInfo,
      networkMonitor,
      topLevelDestinations,
    )
  }
}

@Stable
class CeresAppState(
  val navController: NavHostController,
  val coroutineScope: CoroutineScope,
  val windowSizeClass: WindowSizeClass,
  val screenInfo: ScreenInfo,
  networkMonitor: NetworkMonitor,
  topLevelDestinations: List<TopLevelDestination>,
) {
  val currentDestination: NavDestination?
    @Composable get() = navController.currentBackStackEntryAsState().value?.destination

  var shouldShowNavBar by mutableStateOf(screenInfo.showNavBar)

  var isStatusBarVisible by mutableStateOf(screenInfo.isStatusBarVisible)

  var isNavigationBarVisible by mutableStateOf(screenInfo.isNavigationBarVisible)

  var floatingButtonView by mutableStateOf(screenInfo.floatingButtonView)

  var snackbarView by mutableStateOf(screenInfo.snackbarView)

  var toolbarTokens by mutableStateOf(screenInfo.toolbarTokens)

  val shouldShowBottomBar: Boolean
    get() = windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact

  val shouldShowNavRail: Boolean
    get() = !shouldShowBottomBar

  val isOffline = ApplicationContextProvider.context.networkConnectivityUtils()
    .isOnline
    .map(Boolean::not)
    .stateIn(
      scope = coroutineScope,
      started = SharingStarted.WhileSubscribed(5_000),
      initialValue = false,
    )

  /**
   * Map of top level destinations to be used in the TopBar, BottomBar and NavRail. The key is the
   * route.
   */
  val topLevelDestinations: List<TopLevelDestination> = topLevelDestinations

  /**
   * UI logic for navigating to a top level destination in the app. Top level destinations have
   * only one copy of the destination of the back stack, and save and restore state whenever you
   * navigate to and from it.
   *
   * @param topLevelDestination: The destination the app needs to navigate to.
   */
  fun navigateToTopLevelDestination(topLevelDestination: TopLevelDestination) {
    trace("Navigation: ${topLevelDestination.titleText.lowercase()}") {
      topLevelDestination.screenRoute?.let { destination ->
        val isExisting = navController.isRouteInBackStack(destination.route)
        // todo let users decide
        val enableSingleInstance = true
        if (isExisting && enableSingleInstance) {
          // Pop back stack to bring the existing destination to the top
          navController.popBackStack(
            route = destination.route,
            inclusive = false,
            saveState = true,
          )
        } else {
          // Create new navigation options for the destination
          val topLevelNavOptions = navOptions {
            // Pop up to the specified destination (exclusive)
            popUpTo(navController.graph.findStartDestination().id) { // destination.route) {
              saveState = true
            }
            // Avoid multiple copies of the same destination when reselecting the same item
            launchSingleTop = true
            // Restore state when reselecting a previously selected item
            restoreState = true
          }

          // Navigate to the destination with the specified navigation options
          when (topLevelDestination) {
            in topLevelDestinations -> navController.navigate(
              destination.route,
              topLevelNavOptions,
            )

            else -> Log.e("AppState", "Invalid TopLevelDestination: $topLevelDestination")
          }
        }
      }
    }
  }
}

/**
 * Stores information about navigation events to be used with JankStats
 */
@Composable
private fun NavigationTrackingSideEffect(navController: NavHostController) {
  TrackDisposableJank(navController) { metricsHolder ->
    val listener = NavController.OnDestinationChangedListener { _, destination, _ ->
      metricsHolder.state?.putState("Navigation", destination.route.toString())
    }

    navController.addOnDestinationChangedListener(listener)

    onDispose {
      navController.removeOnDestinationChangedListener(listener)
    }
  }
}
