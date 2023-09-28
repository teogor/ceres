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

package dev.teogor.ceres.ui.designsystem

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.NavigationRailItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import dev.teogor.ceres.ui.designsystem.tokens.NavigationBarTokens
import dev.teogor.ceres.ui.theme.MaterialTheme
import dev.teogor.ceres.ui.theme.toColor

/**
 * Ceres navigation bar item with icon and label content slots. Wraps Material 3
 * [NavigationBarItem].
 *
 * @param selected Whether this item is selected.
 * @param onClick The callback to be invoked when this item is selected.
 * @param icon The item icon content.
 * @param modifier Modifier to be applied to this item.
 * @param selectedIcon The item icon content when selected.
 * @param enabled controls the enabled state of this item. When `false`, this item will not be
 * clickable and will appear disabled to accessibility services.
 * @param label The item text label content.
 * @param alwaysShowLabel Whether to always show the label for this item. If false, the label will
 * only be shown when this item is selected.
 */
@Composable
fun RowScope.CeresNavigationBarItem(
  selected: Boolean,
  onClick: () -> Unit,
  icon: @Composable () -> Unit,
  modifier: Modifier = Modifier,
  selectedIcon: @Composable () -> Unit = icon,
  enabled: Boolean = true,
  label: String = "",
  alwaysShowLabel: Boolean = true,
) {
  NavigationBarItem(
    selected = selected,
    onClick = onClick,
    icon = if (selected) selectedIcon else icon,
    modifier = modifier,
    enabled = enabled,
    label = {
      Text(
        text = label,
        fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
      )
    },
    alwaysShowLabel = alwaysShowLabel,
    colors = NavigationBarItemDefaults.colors(
      selectedIconColor = CeresNavigationDefaults.navigationSelectedItemColor(),
      unselectedIconColor = CeresNavigationDefaults.navigationContentColor(),
      selectedTextColor = CeresNavigationDefaults.navigationSelectedContentColor(),
      unselectedTextColor = CeresNavigationDefaults.navigationContentColor(),
      indicatorColor = CeresNavigationDefaults.navigationIndicatorColor(),
    ),
  )
}

/**
 * Ceres navigation bar with content slot. Wraps Material 3 [NavigationBar].
 *
 * @param modifier Modifier to be applied to the navigation bar.
 * @param content Destinations inside the navigation bar. This should contain multiple
 * [NavigationBarItem]s.
 */
@Composable
fun CeresNavigationBar(
  modifier: Modifier = Modifier,
  content: @Composable RowScope.() -> Unit,
) {
  NavigationBar(
    modifier = modifier,
    containerColor = surfaceColorAtElevation(
      color = CeresNavigationDefaults.containerColor,
      elevation = CeresNavigationDefaults.Elevation,
    ),
    tonalElevation = CeresNavigationDefaults.Elevation,
    contentColor = CeresNavigationDefaults.navigationContentColor(),
    content = content,
  )
}

/**
 * Ceres navigation rail item with icon and label content slots. Wraps Material 3
 * [NavigationRailItem].
 *
 * @param selected Whether this item is selected.
 * @param onClick The callback to be invoked when this item is selected.
 * @param icon The item icon content.
 * @param modifier Modifier to be applied to this item.
 * @param selectedIcon The item icon content when selected.
 * @param enabled controls the enabled state of this item. When `false`, this item will not be
 * clickable and will appear disabled to accessibility services.
 * @param label The item text label content.
 * @param alwaysShowLabel Whether to always show the label for this item. If false, the label will
 * only be shown when this item is selected.
 */
@Composable
fun CeresNavigationRailItem(
  selected: Boolean,
  onClick: () -> Unit,
  icon: @Composable () -> Unit,
  modifier: Modifier = Modifier,
  selectedIcon: @Composable () -> Unit = icon,
  enabled: Boolean = true,
  label: @Composable (() -> Unit)? = null,
  alwaysShowLabel: Boolean = true,
) {
  NavigationRailItem(
    selected = selected,
    onClick = onClick,
    icon = if (selected) selectedIcon else icon,
    modifier = modifier,
    enabled = enabled,
    label = label,
    alwaysShowLabel = alwaysShowLabel,
    colors = NavigationRailItemDefaults.colors(
      selectedIconColor = CeresNavigationDefaults.navigationSelectedItemColor(),
      unselectedIconColor = CeresNavigationDefaults.navigationContentColor(),
      selectedTextColor = CeresNavigationDefaults.navigationSelectedItemColor(),
      unselectedTextColor = CeresNavigationDefaults.navigationContentColor(),
      indicatorColor = CeresNavigationDefaults.navigationIndicatorColor(),
    ),
  )
}

/**
 * Ceres navigation rail with header and content slots. Wraps Material 3 [NavigationRail].
 *
 * @param modifier Modifier to be applied to the navigation rail.
 * @param header Optional header that may hold a floating action button or a logo.
 * @param content Destinations inside the navigation rail. This should contain multiple
 * [NavigationRailItem]s.
 */
@Composable
fun CeresNavigationRail(
  modifier: Modifier = Modifier,
  header: @Composable (ColumnScope.() -> Unit)? = null,
  content: @Composable ColumnScope.() -> Unit,
) {
  NavigationRail(
    modifier = modifier,
    containerColor = Color.Transparent,
    contentColor = CeresNavigationDefaults.navigationContentColor(),
    header = header,
    content = content,
  )
}

/**
 * Ceres navigation default values.
 */
object CeresNavigationDefaults {
  @Composable
  fun navigationContentColor() = MaterialTheme.colorScheme.onPrimaryContainer

  @Composable
  fun navigationSelectedItemColor() = MaterialTheme.colorScheme.onSecondaryContainer

  @Composable
  fun navigationSelectedContentColor() = MaterialTheme.colorScheme.onBackground

  @Composable
  fun navigationIndicatorColor() = MaterialTheme.colorScheme.secondaryContainer

  /** Default elevation for a navigation bar. */
  val Elevation: Dp = NavigationBarTokens.ContainerElevation

  /** Default color for a navigation bar. */
  val containerColor: Color @Composable get() = NavigationBarTokens.ContainerColor.toColor()
}
