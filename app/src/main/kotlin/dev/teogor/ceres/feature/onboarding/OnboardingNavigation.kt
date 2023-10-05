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

package dev.teogor.ceres.feature.onboarding

import androidx.navigation.NavGraphBuilder
import dev.teogor.ceres.feature.home.HomeScreenConfig
import dev.teogor.ceres.framework.core.app.BaseActions
import dev.teogor.ceres.screen.ui.api.ExperimentalOnboardingScreenApi
import dev.teogor.ceres.screen.ui.onboarding.OnboardingScreenData
import dev.teogor.ceres.screen.ui.onboarding.model.OnboardingScreen
import dev.teogor.ceres.screen.ui.onboarding.onboardingNavPath

@OptIn(ExperimentalOnboardingScreenApi::class)
fun NavGraphBuilder.onboardingScreenNav(
  baseActions: BaseActions,
  adsConsentOnly: Boolean,
) = onboardingNavPath(
  baseActions = baseActions,
  onboardingData = OnboardingScreenData(
    appName = "Ceres",
    supportEmail = "open-source@teogor.dev",
    privacyPolicyLink = "https://privacy.teogor.dev",
    termsOfServiceLink = "https://terms.teogor.dev",
  ),
  endRoute = HomeScreenConfig,
  screens = if (adsConsentOnly) {
    listOf(
      OnboardingScreen.AD_CHOICES,
    )
  } else {
    listOf(
      OnboardingScreen.INTRO,
      OnboardingScreen.LEGAL,
      OnboardingScreen.AD_CHOICES,
    )
  },
)
