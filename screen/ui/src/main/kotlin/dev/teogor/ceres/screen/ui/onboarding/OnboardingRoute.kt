package dev.teogor.ceres.screen.ui.onboarding

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.teogor.ceres.framework.core.app.BaseActions
import dev.teogor.ceres.framework.core.app.setScreenInfo
import dev.teogor.ceres.framework.core.screen.floatingButton
import dev.teogor.ceres.framework.core.screen.isStatusBarVisible
import dev.teogor.ceres.framework.core.screen.isVisible
import dev.teogor.ceres.framework.core.screen.showNavBar
import dev.teogor.ceres.framework.core.screen.toolbarTokens
import dev.teogor.ceres.screen.ui.onboarding.model.OnboardingScreen
import dev.teogor.ceres.screen.ui.onboarding.screens.AdChoicesScreen
import dev.teogor.ceres.screen.ui.onboarding.screens.IntroScreen
import dev.teogor.ceres.screen.ui.onboarding.screens.LegalScreen
import dev.teogor.ceres.screen.ui.onboarding.screens.PermissionScreen

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
      .fillMaxSize(),
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
