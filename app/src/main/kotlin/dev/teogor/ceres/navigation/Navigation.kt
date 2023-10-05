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

package dev.teogor.ceres.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import dev.teogor.ceres.data.datastore.defaults.ceresPreferences
import dev.teogor.ceres.feature.about.aboutScreenNav
import dev.teogor.ceres.feature.home.HomeScreenConfig
import dev.teogor.ceres.feature.home.homeScreenNav
import dev.teogor.ceres.feature.lookandfeel.lookAndFeelScreenNav
import dev.teogor.ceres.feature.onboarding.onboardingScreenNav
import dev.teogor.ceres.feature.privacy.privacyOptionsNavPath
import dev.teogor.ceres.feature.settings.settingsScreenNav
import dev.teogor.ceres.framework.core.app.BaseActions
import dev.teogor.ceres.framework.core.app.CeresAppState
import dev.teogor.ceres.framework.core.model.NavGraphOptions
import dev.teogor.ceres.monetisation.ads.ExperimentalAdsControlApi
import dev.teogor.ceres.monetisation.ads.LocalAdsControl
import dev.teogor.ceres.navigation.core.NavHost
import dev.teogor.ceres.screen.ui.about.aboutGraphNav
import dev.teogor.ceres.screen.ui.api.ExperimentalOnboardingScreenApi
import dev.teogor.ceres.screen.ui.onboarding.OnboardingRoute
import dev.teogor.ceres.screen.ui.settings.settingsGraphNav
import dev.teogor.ceres.screen.ui.userprefs.userPreferencesScreenNav

/**
 * Composable function that applies a [NavHost] to the UI with specified
 * modifiers and parameters.
 *
 * @return The [NavHost] composable.
 */
@Composable
fun NavGraphOptions.ApplyNavHost() = NavHost(
  modifier = Modifier.padding(
    bottom = padding.calculateBottomPadding(),
    top = padding.calculateTopPadding(),
  ),
  appState = ceresAppState,
  baseActions = baseActions,
)

/**
 * Composable function for defining a [NavHost] with customizable parameters.
 *
 * @param modifier The modifier for the [NavHost].
 * @param appState The Ceres app state.
 * @param baseActions The base actions for navigation.
 */
@OptIn(ExperimentalOnboardingScreenApi::class, ExperimentalAdsControlApi::class)
@Composable
private fun NavHost(
  modifier: Modifier = Modifier,
  appState: CeresAppState,
  baseActions: BaseActions,
) {
  val onboardingCompleted = ceresPreferences().onboardingComplete
  val adsControl = LocalAdsControl.current
  val canRequestAds by remember { adsControl.canRequestAds }
  val startDestination = if (onboardingCompleted && canRequestAds) {
    HomeScreenConfig
  } else {
    OnboardingRoute
  }.route

  NavHost(
    navController = appState.navController,
    modifier = modifier,
    startDestination = startDestination,
  ) {
    onboardingScreenNav(
      baseActions = baseActions,
      adsConsentOnly = !canRequestAds && onboardingCompleted,
    )

    homeScreenNav(baseActions)

    userPreferencesScreenNav(baseActions)

    settingsGraphNav {
      settingsScreenNav(baseActions)

      lookAndFeelScreenNav(baseActions)

      privacyOptionsNavPath(baseActions)
    }

    aboutGraphNav(baseActions) {
      aboutScreenNav(baseActions)
    }
  }
}
