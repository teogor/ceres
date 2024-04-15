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

package dev.teogor.ceres.screen.ui.settings

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import dev.teogor.ceres.navigation.core.ScreenRoute
import dev.teogor.ceres.navigation.core.screenNav

fun NavGraphBuilder.settingsGraphNav(
  settingsScreenNav: NavGraphBuilder.() -> Unit,
) {
  settingsScreenNav()
}

const val settingsNavigationRoute = "settings_route"

object SettingsScreenRoute : ScreenRoute {
  override val route: String = settingsNavigationRoute
}

inline fun NavGraphBuilder.settingsScreenNav(
  crossinline block: @Composable () -> Unit,
) = screenNav(
  route = settingsNavigationRoute,
) {
  block()
}
