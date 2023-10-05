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

package dev.teogor.ceres.screen.ui.utils

import androidx.compose.runtime.Composable
import dev.teogor.ceres.data.datastore.defaults.ceresPreferences
import dev.teogor.ceres.monetisation.ads.ExperimentalAdsControlApi
import dev.teogor.ceres.monetisation.ads.LocalAdsControl
import dev.teogor.ceres.navigation.core.LocalNavigationParameters
import dev.teogor.ceres.screen.ui.api.ExperimentalOnboardingScreenApi
import dev.teogor.ceres.screen.ui.onboarding.OnboardingRoute

@OptIn(ExperimentalAdsControlApi::class)
@Composable
fun resetConsentHandler(): () -> Unit {
  val adsControl = LocalAdsControl.current
  val resetOnboarding: () -> Unit = {
    adsControl.resetConsent()
  }

  return resetOnboarding
}

@OptIn(ExperimentalOnboardingScreenApi::class, ExperimentalAdsControlApi::class)
@Composable
fun resetOnboardingHandler(): () -> Unit {
  val navigationParameters = LocalNavigationParameters.current
  val adsControl = LocalAdsControl.current

  val resetOnboarding: () -> Unit = {
    val ceresPreferences = ceresPreferences()
    ceresPreferences.onboardingComplete = false
    adsControl.resetConsent()
    navigationParameters.screenRoute = OnboardingRoute
  }

  return resetOnboarding
}
