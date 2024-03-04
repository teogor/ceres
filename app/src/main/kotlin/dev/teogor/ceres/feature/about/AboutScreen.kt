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

package dev.teogor.ceres.feature.about

import android.os.Build
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Business
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.teogor.ceres.build.BuildProfile
import dev.teogor.ceres.framework.core.app.BaseActions
import dev.teogor.ceres.framework.core.app.setScreenInfo
import dev.teogor.ceres.framework.core.screen.floatingButton
import dev.teogor.ceres.framework.core.screen.isStatusBarVisible
import dev.teogor.ceres.framework.core.screen.isVisible
import dev.teogor.ceres.framework.core.screen.showBackButton
import dev.teogor.ceres.framework.core.screen.showNavBar
import dev.teogor.ceres.framework.core.screen.showSettingsButton
import dev.teogor.ceres.framework.core.screen.toolbarTitle
import dev.teogor.ceres.framework.core.screen.toolbarTokens
import dev.teogor.ceres.navigation.core.utilities.toScreenName
import dev.teogor.ceres.screen.builder.compose.SimpleView
import dev.teogor.ceres.screen.core.layout.LazyColumnLayoutBase
import dev.teogor.ceres.screen.core.scope.ScreenListScope
import dev.teogor.ceres.screen.ui.about.AboutScreenRoute
import dev.teogor.ceres.screen.ui.about.aboutApkSignature
import dev.teogor.ceres.screen.ui.about.aboutAppVersion
import dev.teogor.ceres.screen.ui.about.aboutBuildDate
import dev.teogor.ceres.screen.ui.about.aboutBuildHash
import dev.teogor.ceres.screen.ui.about.aboutCeresFramework
import dev.teogor.ceres.screen.ui.about.aboutHeaderAboutUs
import dev.teogor.ceres.screen.ui.about.aboutHeaderLicenses
import dev.teogor.ceres.screen.ui.about.aboutHeaderSecurityPatch
import dev.teogor.ceres.screen.ui.about.aboutHeaderVersion
import dev.teogor.ceres.screen.ui.about.aboutMadeIn
import dev.teogor.ceres.screen.ui.about.aboutOpenAppInfo
import dev.teogor.ceres.screen.ui.about.aboutOpenSourceLicenses
import dev.teogor.ceres.screen.ui.res.Resources

@Composable
internal fun AboutRoute(
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
        "About"
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

  AboutLayout()
}

@Composable
private fun AboutLayout() = LazyColumnLayoutBase(
  screenName = AboutScreenRoute.toScreenName(),
) {
  aboutOpenAppInfo()

  aboutHeaderVersion()

  aboutAppVersion()

  aboutCeresFramework()

  // BuildConfigBet

  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
    aboutBuildDate(
      BuildProfile.buildLocalDateTime,
    )
  }

  aboutHeaderAboutUs()

  developerInfo()

  aboutMadeIn("Brasov, Romania")

  aboutHeaderSecurityPatch()

  aboutBuildHash()

  aboutApkSignature()

  aboutHeaderLicenses()

  aboutOpenSourceLicenses()

  item {
    Spacer(modifier = Modifier.height(40.dp))
  }
}

private fun ScreenListScope.developerInfo() = item {
  SimpleView(
    title = Resources.Developer,
    subtitle = "Teodor Grigor",
    icon = Icons.Default.Business,
  )
}
