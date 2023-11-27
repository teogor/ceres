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

package dev.teogor.ceres.navigation.core

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun NavHost(
  navController: NavHostController,
  modifier: Modifier = Modifier,
  startDestination: String,
  builder: NavGraphBuilder.() -> Unit,
) {
  val isNavigating = isNavigating(
    navController = navController,
  )

  NavHost(
    navController = navController,
    startDestination = startDestination,
    modifier = modifier,
  ) {
    builder()
  }
}

inline fun NavGraphBuilder.screenNav(
  route: String,
  crossinline block: @Composable () -> Unit,
) {
  composable(route = route) {
    block()
  }
}

// todo
inline fun NavGraphBuilder.screenNavGraph(
  route: String,
  crossinline block: @Composable () -> Unit,
) {
  composable(route = route) {
    block()
  }
}

@Composable
private fun isNavigating(
  navController: NavController,
  navigationParameters: NavigationParameters = LocalNavigationParameters.current,
): Boolean {
  var navigating = false
  navigationParameters.apply {
    // todo is this the proper way to handle uri defaults ???
    //  **hardcoded values**
    // uri?.let { _ ->
    //   navigating = true
    //   navController.navigate("settings_route") {
    //     popUpTo("home_route") {
    //       inclusive = true
    //     }
    //   }
    //   this.uri = null
    // }
    // todo look into better ways to handle this
    screenRoute?.let { destination ->
      navigating = true
      this.screenRoute = null
      navController.navigate(destination.route)
    }
  }
  return navigating
}

@SuppressLint("RestrictedApi")
fun NavController.isRouteInBackStack(route: String): Boolean {
  // todo graph.route ??
  val backStackEntries = this.currentBackStack.value

  for (entry in backStackEntries) {
    if (entry.destination.route == route) {
      return true
    }
  }

  return false
}
