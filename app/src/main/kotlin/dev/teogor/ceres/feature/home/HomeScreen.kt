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

package dev.teogor.ceres.feature.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import dev.teogor.ceres.R
import dev.teogor.ceres.ads.HomeNativeAdBeta
import dev.teogor.ceres.core.foundation.compositions.LocalNetworkMonitor
import dev.teogor.ceres.core.foundation.extensions.createMediaPlayer
import dev.teogor.ceres.data.datastore.defaults.ceresPreferences
import dev.teogor.ceres.framework.core.app.BaseActions
import dev.teogor.ceres.framework.core.app.setScreenInfo
import dev.teogor.ceres.framework.core.screen.floatingButton
import dev.teogor.ceres.framework.core.screen.isStatusBarVisible
import dev.teogor.ceres.framework.core.screen.isVisible
import dev.teogor.ceres.framework.core.screen.showNavBar
import dev.teogor.ceres.framework.core.screen.showSettingsButton
import dev.teogor.ceres.framework.core.screen.toolbarTitle
import dev.teogor.ceres.framework.core.screen.toolbarTokens
import dev.teogor.ceres.monetisation.admob.formats.nativead.NativeAd
import dev.teogor.ceres.monetisation.ads.ExperimentalAdsControlApi
import dev.teogor.ceres.monetisation.messaging.ConsentManager
import dev.teogor.ceres.navigation.core.LocalNavigationParameters
import dev.teogor.ceres.navigation.core.utilities.toScreenName
import dev.teogor.ceres.screen.builder.compose.HeaderView
import dev.teogor.ceres.screen.builder.compose.SimpleView
import dev.teogor.ceres.screen.core.layout.ColumnLayoutBase
import dev.teogor.ceres.screen.ui.api.ExperimentalOnboardingScreenApi
import dev.teogor.ceres.screen.ui.onboarding.OnboardingRoute

// todo better way to configure this. perhaps use kotlin builder syntax
@Composable
internal fun HomeRoute(
  baseActions: BaseActions,
  homeVM: HomeViewModel = hiltViewModel(),
) {
  baseActions.setScreenInfo {
    showNavBar {
      true
    }

    isStatusBarVisible {
      true
    }

    toolbarTokens {
      isVisible {
        true
      }

      toolbarTitle {
        "Home"
      }

      showSettingsButton {
        true
      }
    }

    floatingButton {
      isVisible {
        false
      }
    }
  }

  val networkMonitor = LocalNetworkMonitor.current

  val switchOff = createMediaPlayer(R.raw.button_switch_off)
  LaunchedEffect(switchOff) {
    switchOff.start()
  }

  val switchOn = createMediaPlayer(R.raw.button_switch_on)
  LaunchedEffect(switchOn) {
    switchOn.start()
  }

  HomeScreen(
    homeVM,
    isOffline = networkMonitor.isOffline,
  )
}

@OptIn(ExperimentalOnboardingScreenApi::class)
@Composable
fun handleOnboardingReset(): () -> Unit {
  val navigationParameters = LocalNavigationParameters.current

  val resetOnboarding: () -> Unit = {
    val ceresPreferences = ceresPreferences()
    ceresPreferences.onboardingComplete = false
    ConsentManager.resetConsent()
    navigationParameters.screenRoute = OnboardingRoute
  }

  return resetOnboarding
}

@OptIn(ExperimentalAdsControlApi::class)
@Composable
private fun HomeScreen(
  homeVM: HomeViewModel,
  isOffline: Boolean,
) = ColumnLayoutBase(
  hasScrollbarBackground = false,
  screenName = HomeScreenConfig.toScreenName(),
) {
  HeaderView(title = "Ads Demo")

  // customView {
  //   dev.teogor.ceres.monetisation.admob.beta.formats.ui.AdmobBanner(
  //     modifier = Modifier.fillMaxWidth(),
  //     bannerAd = homeVM.homeBannerAd,
  //   )
  // }

  SimpleView(
    title = "Show Interstitial" + if (isOffline) " (Off)" else "",
    clickable = {
      homeVM.homeInterstitialAd.show()
    },
  )

  SimpleView(
    title = "Show Rewarded Interstitial" + if (isOffline) " (Off)" else "",
    clickable = {
      homeVM.homeRewardedInterstitialAd.show()
    },
  )

  SimpleView(
    title = "Show Rewarded" + if (isOffline) " (Off)" else "",
    clickable = {
      homeVM.homeRewardedAd.show()
    },
  )

  NativeAd<HomeNativeAdBeta>()
}
