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

import androidx.navigation.NavGraphBuilder
import dev.teogor.ceres.data.datastore.defaults.ceresPreferences
import dev.teogor.ceres.framework.core.app.BaseActions
import dev.teogor.ceres.navigation.core.LocalNavigationParameters
import dev.teogor.ceres.navigation.core.ScreenRoute
import dev.teogor.ceres.navigation.core.screenNav
import dev.teogor.ceres.screen.ui.api.ExperimentalOnboardingScreenApi
import dev.teogor.ceres.screen.ui.onboarding.model.OnboardingScreen

private const val navPath = "onboarding_path"

@ExperimentalOnboardingScreenApi
object OnboardingRoute : ScreenRoute {
  override val route: String = navPath
}

private const val introNavPath = "intro_path"

object IntroRoute : ScreenRoute {
  override val route: String = introNavPath
}

private const val legalNavPath = "legal_path"

object LegalRoute : ScreenRoute {
  override val route: String = legalNavPath
}

private const val adChoicesNavPath = "ad_choices_path"

object AdChoicesRoute : ScreenRoute {
  override val route: String = adChoicesNavPath
}

private const val permissionNavPath = "permission_path"

object PermissionRoute : ScreenRoute {
  override val route: String = permissionNavPath
}

@ExperimentalOnboardingScreenApi
fun NavGraphBuilder.onboardingNavPath(
  baseActions: BaseActions,
  onboardingData: OnboardingScreenData,
  endRoute: ScreenRoute,
  screens: List<OnboardingScreen> = listOf(
    OnboardingScreen.INTRO,
    OnboardingScreen.LEGAL,
    OnboardingScreen.PERMISSION,
    OnboardingScreen.AD_CHOICES,
  ),
) {
  screenNav(navPath) {
    val navigationParameters = LocalNavigationParameters.current
    navigationParameters.screenRoute = if (screens.isEmpty()) {
      throw IllegalArgumentException("The 'screens' list must not be empty.")
    } else {
      when (screens.first()) {
        OnboardingScreen.INTRO -> IntroRoute
        OnboardingScreen.LEGAL -> LegalRoute
        OnboardingScreen.PERMISSION -> PermissionRoute
        OnboardingScreen.AD_CHOICES -> AdChoicesRoute
      }
    }
  }
  screens.forEachIndexed { index, screen ->
    val isLast = index == screens.size - 1
    val isFirst = index == 0
    screenNav(screen.screenRoute().route) {
      val navigationParameters = LocalNavigationParameters.current
      OnboardingGraphBeta(
        baseActions = baseActions,
        onboardingData = onboardingData,
        screen = screen,
        onNext = {
          if (isLast) {
            ceresPreferences().onboardingComplete = true
            navigationParameters.screenRoute = endRoute
          } else {
            navigationParameters.screenRoute = screens[index + 1].screenRoute()
          }
        },
      ) {
        if (!isFirst) {
          navigationParameters.screenRoute = screens[index - 1].screenRoute()
        }
      }
    }
  }
}

private fun OnboardingScreen.screenRoute() = when (this) {
  OnboardingScreen.INTRO -> IntroRoute
  OnboardingScreen.LEGAL -> LegalRoute
  OnboardingScreen.PERMISSION -> PermissionRoute
  OnboardingScreen.AD_CHOICES -> AdChoicesRoute
}
