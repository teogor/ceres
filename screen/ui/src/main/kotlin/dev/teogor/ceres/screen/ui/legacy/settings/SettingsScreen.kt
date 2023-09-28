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

package com.zeoowl.chess.clock.ceres.feature.settings

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Style
import androidx.compose.runtime.Composable
import com.zeoowl.chess.clock.ceres.feature.lookfeel.LookAndFeelScreenRoute
import dev.teogor.ceres.framework.core.app.BaseActions
import dev.teogor.ceres.framework.core.app.navigateTo
import dev.teogor.ceres.framework.core.app.setScreenInfo
import dev.teogor.ceres.framework.core.screen.floatingButton
import dev.teogor.ceres.framework.core.screen.isStatusBarVisible
import dev.teogor.ceres.framework.core.screen.isVisible
import dev.teogor.ceres.framework.core.screen.showBackButton
import dev.teogor.ceres.framework.core.screen.showNavBar
import dev.teogor.ceres.framework.core.screen.showSettingsButton
import dev.teogor.ceres.framework.core.screen.toolbarTitle
import dev.teogor.ceres.framework.core.screen.toolbarTokens
import dev.teogor.ceres.navigation.core.ScreenRoute
import dev.teogor.ceres.screen.builder.compose.LazyColumnLayout
import dev.teogor.ceres.screen.builder.header
import dev.teogor.ceres.screen.builder.simpleView

@Composable
internal fun SettingsRoute(
  baseActions: BaseActions,
) {
  baseActions.setScreenInfo {
    showNavBar {
      false
    }
    isStatusBarVisible {
      true
    }
    toolbarTokens {
      toolbarTitle {
        "Settings"
      }
      showSettingsButton {
        false
      }
      showBackButton {
        true
      }
    }

    floatingButton {
      isVisible {
        false
      }
    }
  }

  SettingsLayout(
    navigateTo = { destination ->
      baseActions.navigateTo(destination)
    },
    developerModeEnabled = false,
  )
}

@Composable
private fun SettingsLayout(
  navigateTo: (ScreenRoute) -> Unit,
  developerModeEnabled: Boolean,
) = LazyColumnLayout(
  screenName = SettingsScreenRoute,
) {
  header {
    "UI"
  }

  simpleView(
    title = "Look & Feel",
    subtitle = "Design & color options",
    icon = Icons.Default.Style,
    clickable = {
      navigateTo(LookAndFeelScreenRoute)
    },
  )
}
