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

package dev.teogor.ceres.screen.ui.lookandfeel

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import dev.teogor.ceres.navigation.core.ScreenRoute
import dev.teogor.ceres.navigation.core.screenNav

const val lookAndFeelNavigationRoute = "settings_look_and_feel_route"

object LookAndFeelScreenRoute : ScreenRoute {
  override val route: String = lookAndFeelNavigationRoute
}

inline fun NavGraphBuilder.lookAndFeelScreenNav(
  crossinline block: @Composable () -> Unit,
) = screenNav(
  route = lookAndFeelNavigationRoute,
) {
  block()
}
