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

package dev.teogor.ceres

import androidx.compose.runtime.Composable
import dagger.hilt.android.AndroidEntryPoint
import dev.teogor.ceres.framework.core.Activity
import dev.teogor.ceres.framework.core.model.MenuConfig
import dev.teogor.ceres.framework.core.model.NavGraphOptions
import dev.teogor.ceres.menu.applyMenuConfig
import dev.teogor.ceres.navigation.ApplyNavHost
import dev.teogor.ceres.navigation.core.menu.TopLevelDestination

/**
 * The main activity of the Ceres application.
 *
 * This activity serves as the entry point for the application and handles navigation and menu configuration.
 */
@AndroidEntryPoint
class MainActivity : Activity() {

  /**
   * Gets the list of top-level destinations based on the current screen.
   *
   * @return A list of [TopLevelDestination] for the current screen.
   */
  override val topLevelDestinations: List<TopLevelDestination>
    get() = super.topLevelDestinations

  /**
   * Builds and applies the navigation graph for the Ceres application using [NavGraphOptions].
   *
   * This function is responsible for constructing and applying the navigation graph.
   * It returns a [NavHost] composable representing the navigation graph.
   *
   * @return The [NavHost] composable representing the applied navigation graph.
   */
  @Composable
  override fun NavGraphOptions.BuildNavGraph() = ApplyNavHost()

  /**
   * Builds the menu for the Ceres application using [MenuConfig].
   *
   * @return The configured [MenuConfig] instance.
   */
  @Composable
  override fun MenuConfig.buildMenu() = applyMenuConfig()
}
