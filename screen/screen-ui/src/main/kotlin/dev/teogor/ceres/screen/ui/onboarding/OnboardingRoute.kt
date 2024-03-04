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

package dev.teogor.ceres.screen.ui.onboarding

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.teogor.ceres.framework.core.app.BaseActions
import dev.teogor.ceres.framework.core.app.setScreenInfo
import dev.teogor.ceres.framework.core.screen.floatingButton
import dev.teogor.ceres.framework.core.screen.isStatusBarVisible
import dev.teogor.ceres.framework.core.screen.isVisible
import dev.teogor.ceres.framework.core.screen.showNavBar
import dev.teogor.ceres.framework.core.screen.toolbarTokens
import dev.teogor.ceres.screen.ui.api.ExperimentalOnboardingScreenApi
import dev.teogor.ceres.screen.ui.onboarding.model.OnboardingScreen
import dev.teogor.ceres.screen.ui.onboarding.screens.AdChoicesScreen
import dev.teogor.ceres.screen.ui.onboarding.screens.IntroScreen
import dev.teogor.ceres.screen.ui.onboarding.screens.LegalScreen
import dev.teogor.ceres.screen.ui.onboarding.screens.PermissionScreen

@OptIn(ExperimentalOnboardingScreenApi::class)
@Composable
internal fun OnboardingGraphBeta(
  baseActions: BaseActions,
  onboardingData: OnboardingScreenData,
  screen: OnboardingScreen,
  onNext: () -> Unit,
  onPrevious: () -> Unit,
) {
  baseActions.setScreenInfo {
    showNavBar {
      false
    }

    isStatusBarVisible {
      true
    }

    toolbarTokens {
      isVisible {
        false
      }
    }

    floatingButton {
      isVisible {
        false
      }
    }
  }

  Box(
    modifier = Modifier
      .fillMaxSize()
      .navigationBarsPadding(),
  ) {
    when (screen) {
      OnboardingScreen.INTRO -> {
        IntroScreen(
          data = onboardingData,
          onNext = onNext,
          onBack = onPrevious,
        )
      }

      OnboardingScreen.LEGAL -> {
        LegalScreen(
          data = onboardingData,
          onNext = onNext,
          onBack = onPrevious,
        )
      }

      OnboardingScreen.PERMISSION -> {
        PermissionScreen(
          data = onboardingData,
          onNext = onNext,
          onBack = onPrevious,
        )
      }

      OnboardingScreen.AD_CHOICES -> {
        AdChoicesScreen(
          onBack = onPrevious,
          onNext = onNext,
        )
      }
    }
  }
}
