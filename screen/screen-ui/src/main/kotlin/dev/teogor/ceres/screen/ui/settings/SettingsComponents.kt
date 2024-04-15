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

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PrivacyTip
import androidx.compose.material.icons.filled.SettingsBackupRestore
import androidx.compose.material.icons.filled.Style
import dev.teogor.ceres.navigation.core.LocalNavigationParameters
import dev.teogor.ceres.screen.builder.compose.HeaderView
import dev.teogor.ceres.screen.builder.compose.SimpleView
import dev.teogor.ceres.screen.core.scope.ScreenListScope
import dev.teogor.ceres.screen.ui.lookandfeel.LookAndFeelScreenRoute
import dev.teogor.ceres.screen.ui.privacy.PrivacyOptionsRoute
import dev.teogor.ceres.screen.ui.res.Resources

fun ScreenListScope.settingsHeaderUI() = item {
  HeaderView(
    title = Resources.Ui,
  )
}

fun ScreenListScope.settingsLookAndFeel() = item {
  val navigation = LocalNavigationParameters.current
  SimpleView(
    title = Resources.LookAndFeel,
    subtitle = Resources.DesignAndColorOptions,
    icon = Icons.Default.Style,
    clickable = {
      navigation.screenRoute = LookAndFeelScreenRoute
    },
  )
}

fun ScreenListScope.settingsBackup() = item {
  val navigation = LocalNavigationParameters.current
  SimpleView(
    title = Resources.BackupAndRestore,
    subtitle = Resources.FullBackupOfYourApp,
    icon = Icons.Default.SettingsBackupRestore,
    clickable = {
    },
  )
}

fun ScreenListScope.settingsHeaderSystem() = item {
  HeaderView(
    title = Resources.System,
  )
}

fun ScreenListScope.settingsHeaderDataPrivacy() = item {
  HeaderView(
    title = Resources.DataAndPrivacy,
  )
}

fun ScreenListScope.settingsPrivacyOptions() = item {
  val navigation = LocalNavigationParameters.current
  SimpleView(
    title = Resources.PrivacyOptions,
    subtitle = Resources.ManageYourDataAndPrivacyPreferences,
    icon = Icons.Default.PrivacyTip,
    clickable = {
      navigation.screenRoute = PrivacyOptionsRoute
    },
  )
}
