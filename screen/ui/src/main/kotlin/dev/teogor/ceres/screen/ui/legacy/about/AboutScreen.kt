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

package dev.teogor.ceres.screen.ui.legacy.about

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.DomainVerification
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.PermDeviceInformation
import androidx.compose.material.icons.filled.Source
import androidx.compose.material.icons.filled.Verified
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.teogor.ceres.core.runtime.AppMetadataManager
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
import dev.teogor.ceres.screen.builder.customView
import dev.teogor.ceres.screen.builder.header
import dev.teogor.ceres.screen.builder.simpleView
import dev.teogor.ceres.ui.designsystem.Text
import dev.teogor.ceres.ui.theme.MaterialTheme
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

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

  AboutLayout(
    navigateTo = { destination ->
      baseActions.navigateTo(destination)
    },
  )
}

@Composable
private fun AboutLayout(
  navigateTo: (ScreenRoute) -> Unit,
) = LazyColumnLayout(
  screenName = AboutScreenRoute,
) {
  header {
    "Version Info"
  }

  simpleView(
    title = "App version",
    subtitle = "${AppMetadataManager.versionName}",
    icon = Icons.Default.Info,
  )

  simpleView(
    title = "Ceres Framework version",
    subtitle = "${AppMetadataManager.ceresFrameworkVersion}",
    icon = Icons.Default.PermDeviceInformation,
  ) {
    customView {
      Text(
        text = "This app is powered by the Ceres Framework, created by developer Teodor Grigor.",
        color = MaterialTheme.colorScheme.success,
      )
    }
  }

  simpleView(
    title = "Build date",
    subtitle = "${
      AppMetadataManager.buildDateTime.format(
        DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM),
      )
    }",
    icon = Icons.Default.DateRange,
  )

  header {
    "About Us"
  }

  simpleView(
    title = "Company",
    subtitle = "ZeoOwl",
    icon = Icons.Default.Business,
  )

  simpleView(
    title = "Made in",
    subtitle = "Brasov, Romania",
    icon = Icons.Default.LocationOn,
  )

  header {
    "Security Patch"
  }

  simpleView(
    title = "Build hash",
    subtitle = "${AppMetadataManager.gitHash}",
    icon = Icons.Default.DomainVerification,
  )

  simpleView(
    title = "APK hash",
    subtitle = "${AppMetadataManager.apkSignature}",
    icon = Icons.Default.Verified,
  )

  header {
    "Licenses"
  }

  simpleView(
    title = "Open-source licenses",
    subtitle = "License details for open-source software",
    icon = Icons.Default.Source,
    clickable = {
      navigateTo(AboutLibrariesScreenRoute)
    },
  )

  customView {
    Spacer(modifier = Modifier.height(100.dp))
  }
}
