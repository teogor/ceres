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

package dev.teogor.ceres.menu

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Details
import androidx.compose.material.icons.outlined.HelpOutline
import androidx.compose.material.icons.outlined.Link
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.teogor.ceres.core.foundation.DefaultResources
import dev.teogor.ceres.core.foundation.ui.platform.LocalApplicationDetails
import dev.teogor.ceres.core.startup.ApplicationContextProvider.context
import dev.teogor.ceres.framework.core.menu.MenuTitle
import dev.teogor.ceres.framework.core.menu.menu
import dev.teogor.ceres.framework.core.menu.menuContent
import dev.teogor.ceres.framework.core.menu.menuDivider
import dev.teogor.ceres.framework.core.menu.menuFooter
import dev.teogor.ceres.framework.core.menu.menuItem
import dev.teogor.ceres.framework.core.menu.menuTop
import dev.teogor.ceres.framework.core.menu.menuUserData
import dev.teogor.ceres.framework.core.menu.menuUserId
import dev.teogor.ceres.framework.core.model.MenuConfig
import dev.teogor.ceres.navigation.core.LocalNavigationParameters
import dev.teogor.ceres.navigation.core.ScreenRoute
import dev.teogor.ceres.screen.ui.about.AboutScreenRoute
import dev.teogor.ceres.screen.ui.res.ResourceKey
import dev.teogor.ceres.screen.ui.res.ResourceType
import dev.teogor.ceres.screen.ui.res.Resources
import dev.teogor.ceres.screen.ui.settings.SettingsScreenRoute
import dev.teogor.ceres.screen.ui.userprefs.UserPreferencesScreenRoute

inline fun <reified Type> ResourceKey.asRes(quantity: Int = 0, vararg args: Any): Type =
  when (type) {
    ResourceType.Array -> {
      DefaultResources(resources = context.resources)
        .getStringArray(id) as Type
    }

    ResourceType.Plurals -> {
      DefaultResources(resources = context.resources)
        .getQuantityString(id, quantity, *args) as Type
    }

    ResourceType.String -> {
      DefaultResources(resources = context.resources)
        .getString(id, *args) as Type
    }
  }

/**
 * Applies the menu configuration to this [MenuConfig].
 *
 * This function sets the header content and menu content based on the provided configuration.
 */
@Composable
fun MenuConfig.applyMenuConfig() = apply {
  val navigationParameters = LocalNavigationParameters.current
  val applicationDetails = LocalApplicationDetails.current

  // Extension function to navigate to a screen route
  fun ScreenRoute.navigateTo() {
    navigationParameters.screenRoute = this
  }

  val resSettingsTitle = Resources.Settings
  val resHelpAndFeedback = Resources.HelpAndFeedback

  // Set the header content
  headerContent = {
    MenuTitle(
      title = applicationDetails.appName ?: "",
    )
  }

  // Set the menu content
  menuContent = {
    // Menu configurator
    menu {
      // Menu header
      menuTop {
        // Ceres built-in user details
        menuUserData(
          clickable = {
            UserPreferencesScreenRoute.navigateTo()
          },
        )

        // Use menu divider to add a divider
        menuDivider()

        // Demo showcase of menu top header elements
        repeat(3) {
          menuItem(
            content = "Element $it",
            icon = Icons.Outlined.Settings,
            clickable = {
              SettingsScreenRoute.navigateTo()
            },
          )
        }
      }

      menuContent {
        menuItem(
          content = ResourceKey.SETTINGS.asRes(),
          icon = Icons.Outlined.Settings,
          clickable = {
            SettingsScreenRoute.navigateTo()
          },
        )

        menuItem(
          content = ResourceKey.HELP_AND_FEEDBACK.asRes(),
          icon = Icons.Outlined.HelpOutline,
        )

        menuItem(
          content = ResourceKey.PRIVACY_POLICY.asRes(),
          icon = Icons.Outlined.Link,
        )

        menuItem(
          content = ResourceKey.TERMS_OF_SERVICE.asRes(),
          icon = Icons.Outlined.Link,
        )

        menuItem(
          content = ResourceKey.ABOUT.asRes(),
          icon = Icons.Outlined.Details,
          clickable = {
            AboutScreenRoute.navigateTo()
          },
        )

        menuDivider()

        menuFooter(
          licenseHolder = "teogor (Teodor G.)",
          modifier = Modifier
            .padding(horizontal = 6.dp)
            .padding(top = 10.dp, bottom = 4.dp),
        )

        menuUserId(
          modifier = Modifier
            .padding(horizontal = 6.dp)
            .padding(top = 2.dp),
        )
      }

      // More menu content can be added here
    }
  }
}
