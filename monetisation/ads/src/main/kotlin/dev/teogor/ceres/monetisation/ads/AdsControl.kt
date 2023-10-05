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

@file:OptIn(ExperimentalAdsControlApi::class)

package dev.teogor.ceres.monetisation.ads

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import dev.teogor.ceres.monetisation.ads.model.ConsentRequirementStatus
import dev.teogor.ceres.monetisation.ads.model.ConsentStatus

data class AndroidAdsControl(
  override val canRequestAds: MutableState<Boolean> = mutableStateOf(
    false,
  ),
  override val consentStatus: MutableState<ConsentStatus> = mutableStateOf(
    ConsentStatus.UNKNOWN,
  ),
  override val consentRequirementStatus: MutableState<ConsentRequirementStatus> = mutableStateOf(
    ConsentRequirementStatus.UNKNOWN,
  ),
  val showConsent: (() -> Unit)? = null,
) : AdsControl {

  override fun showConsent() {
    requireNotNull(showConsent) {
      "The 'showConsent' function must be provided when creating an instance of AndroidAdsControl."
    }
    showConsent.invoke()
  }
}

val LocalAdsControl = compositionLocalOf<AdsControl> {
  error("No AdsControl provided")
}

@ExperimentalAdsControlApi
interface AdsControl {
  val canRequestAds: MutableState<Boolean>
  val consentStatus: MutableState<ConsentStatus>
  val consentRequirementStatus: MutableState<ConsentRequirementStatus>

  fun showConsent()
}

val AdsControl.shouldShowConsentDialog: Boolean
  get() = !canRequestAds.value && consentStatus.value == ConsentStatus.CONSENT_FORM_ACQUIRED

val AdsControl.consentIsShowing: Boolean
  get() = consentStatus.value == ConsentStatus.CONSENT_FORM_DISPLAYED

@OptIn(ExperimentalAdsControlApi::class)
@Composable
fun HandleAdsConsent(
  onAdsConsentGranted: () -> Unit,
  onAdsConsentRejected: () -> Unit,
) {
  val adsControl = LocalAdsControl.current
  val shouldShowConsentDialog = remember { adsControl.shouldShowConsentDialog }
  val canRequestAds by remember { adsControl.canRequestAds }
  val state by remember { adsControl.consentStatus }
  // TODO: Implement the missing functionality in ConsentManager (Issue #126)
  //  - temporary workaround
  var isConsentVisible by rememberSaveable { mutableStateOf(false) }

  LaunchedEffect(shouldShowConsentDialog, canRequestAds, state) {
    if (canRequestAds) {
      onAdsConsentGranted()
      if (state == ConsentStatus.CONSENT_FORM_DISMISSED) {
        isConsentVisible = false
      }
    } else if (state == ConsentStatus.CONSENT_FORM_ERROR) {
      adsControl.showConsent()
    } else if (shouldShowConsentDialog && !isConsentVisible) {
      adsControl.showConsent()
      isConsentVisible = true
    } else {
      if (state == ConsentStatus.CONSENT_FORM_DISMISSED) {
        onAdsConsentRejected()
        isConsentVisible = false
      }
    }
  }
}
