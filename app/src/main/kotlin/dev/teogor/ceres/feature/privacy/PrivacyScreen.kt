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

package dev.teogor.ceres.feature.privacy

import androidx.compose.runtime.Composable
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
import dev.teogor.ceres.screen.core.layout.LazyColumnLayoutBase
import dev.teogor.ceres.screen.ui.privacy.PrivacyOptionsRoute
import dev.teogor.ceres.screen.ui.privacy.legalAgreementsHeader
import dev.teogor.ceres.screen.ui.privacy.policyAppLicensePolicy
import dev.teogor.ceres.screen.ui.privacy.policyCopyrightPolicy
import dev.teogor.ceres.screen.ui.privacy.privacyOptionsHeader
import dev.teogor.ceres.screen.ui.privacy.privacyPolicyOption
import dev.teogor.ceres.screen.ui.privacy.privacyResetOnboarding
import dev.teogor.ceres.screen.ui.privacy.privacyUserId
import dev.teogor.ceres.screen.ui.privacy.resetAdsConsentOption
import dev.teogor.ceres.screen.ui.privacy.termsOfServiceOption
import dev.teogor.ceres.screen.ui.res.Resources

@Composable
internal fun PrivacyOptionsRoute(
  baseActions: BaseActions,
) {
  val title = Resources.PrivacyOptions

  baseActions.setScreenInfo {
    showNavBar {
      false
    }
    isStatusBarVisible {
      true
    }
    toolbarTokens {
      toolbarTitle {
        title
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

  PrivacyOptionsLayout()
}

@Composable
private fun PrivacyOptionsLayout() = LazyColumnLayoutBase(
  screenName = PrivacyOptionsRoute.toScreenName(),
) {
  privacyOptionsHeader()

  resetAdsConsentOption()

  privacyUserId()

  privacyResetOnboarding()

  legalAgreementsHeader()

  privacyPolicyOption()

  termsOfServiceOption()

  policyCopyrightPolicy()

  // policyRefundPolicy()

  policyAppLicensePolicy(license = 0)
}
