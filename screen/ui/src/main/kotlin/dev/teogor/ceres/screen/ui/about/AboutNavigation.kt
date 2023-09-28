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

package dev.teogor.ceres.screen.ui.about

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import dev.teogor.ceres.framework.core.app.BaseActions
import dev.teogor.ceres.navigation.core.ScreenRoute
import dev.teogor.ceres.navigation.core.screenNav
import dev.teogor.ceres.screen.ui.about.libraries.AboutLibrariesRoute

fun NavGraphBuilder.aboutGraphNav(
  baseActions: BaseActions,
  aboutScreenNav: NavGraphBuilder.() -> Unit,
) {
  aboutScreenNav()
  aboutLibrariesScreenNav(baseActions)
}

// About Screen
const val aboutNavigationRoute = "about_route"

object AboutScreenRoute : ScreenRoute {
  override val route: String = aboutNavigationRoute
}

inline fun NavGraphBuilder.aboutScreenNav(
  crossinline block: @Composable () -> Unit,
) = screenNav(
  route = aboutNavigationRoute,
) {
  block()
}

// About Libraries Screen
const val aboutLibrariesNavigationRoute = "about_libraries_license_route"

object AboutLibrariesScreenRoute : ScreenRoute {
  override val route: String = aboutLibrariesNavigationRoute
}

fun NavGraphBuilder.aboutLibrariesScreenNav(
  baseActions: BaseActions,
) = aboutLibrariesScreenNav {
  AboutLibrariesRoute(
    baseActions = baseActions,
  )
}

inline fun NavGraphBuilder.aboutLibrariesScreenNav(
  crossinline block: @Composable () -> Unit,
) = screenNav(
  route = aboutLibrariesNavigationRoute,
) {
  block()
}
