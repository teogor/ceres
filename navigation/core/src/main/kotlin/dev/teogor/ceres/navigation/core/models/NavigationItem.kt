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

package dev.teogor.ceres.navigation.core.models

import dev.teogor.ceres.navigation.core.ScreenRoute
import dev.teogor.ceres.ui.foundation.graphics.Icon

/**
 * Represents a navigation item used in a bottom navigation bar, tab bar, or similar UI components.
 * This class encapsulates the properties needed to define a navigation item.
 *
 * @param selectedIcon The icon to display when this item is selected.
 * @param unselectedIcon The icon to display when this item is not selected.
 * @param titleText The text to display as the title or label for this item.
 * @param iconText The text to display as the icon's label (defaults to [titleText]).
 * @param screenRoute The route to the screen that is visible when this item is active.
 */
open class NavigationItem(
  val selectedIcon: Icon,
  val unselectedIcon: Icon,
  val titleText: String,
  val iconText: String = titleText,
  val screenRoute: ScreenRoute,
)
