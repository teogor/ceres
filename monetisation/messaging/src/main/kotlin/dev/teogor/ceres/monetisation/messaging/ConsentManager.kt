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

package dev.teogor.ceres.monetisation.messaging

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.google.android.ump.ConsentDebugSettings
import com.google.android.ump.ConsentInformation
import com.google.android.ump.ConsentRequestParameters
import com.google.android.ump.UserMessagingPlatform
import dev.teogor.ceres.core.register.BuildProfiler
import dev.teogor.ceres.core.register.LocalBuildProfiler
import dev.teogor.ceres.monetisation.admob.AdMob
import dev.teogor.ceres.monetisation.admob.AdMobInitializer
import dev.teogor.ceres.monetisation.admob.getHashedAdvertisingId
import dev.teogor.ceres.monetisation.ads.AdsControl
import dev.teogor.ceres.monetisation.ads.AdsControlProvider
import dev.teogor.ceres.monetisation.ads.ExperimentalAdsControlApi
import dev.teogor.ceres.monetisation.ads.model.ConsentStatus
import dev.teogor.ceres.monetisation.messaging.utils.toConsentRequirementStatus
import java.lang.ref.WeakReference
import java.util.concurrent.atomic.AtomicBoolean

object ConsentManager {
  private lateinit var consentInformation: ConsentInformation
  private var weakActivity: WeakReference<Activity>? = null

  private val TAG = "ConsentManager"
  private var isMobileAdsInitializeCalled = AtomicBoolean(false)

  val state: MutableState<ConsentResult> = mutableStateOf(ConsentResult.Undefined)

  var activity: Activity?
    get() = weakActivity?.get()
    set(value) {
      weakActivity = if (value != null) {
        WeakReference(value)
      } else {
        null
      }
    }

  private val isConsentFormShown = AtomicBoolean(false)

  private fun showConsentForm() {
    isConsentFormShown.set(true)
  }

  private fun onConsentFormDismissedOrFailed() {
    isConsentFormShown.set(false)
  }

  private fun isConsentFormAlreadyShown(): Boolean {
    return isConsentFormShown.get()
  }

  @OptIn(ExperimentalAdsControlApi::class)
  private val adsControl: AdsControl
    get() = AdsControlProvider.adsControl

  @OptIn(ExperimentalAdsControlApi::class)
  fun loadAndShowConsentFormIfRequired() {
    if (isConsentFormAlreadyShown()) {
      return
    }
    activity?.let {
      showConsentForm()
      adsControl.consentStatus.value = ConsentStatus.CONSENT_FORM_DISPLAYED
      UserMessagingPlatform.loadAndShowConsentFormIfRequired(
        it,
      ) { formError ->
        onConsentFormDismissedOrFailed()
        adsControl.canRequestAds.value = consentInformation.canRequestAds()
        adsControl.consentStatus.value = ConsentStatus.CONSENT_FORM_DISMISSED
        state.value = ConsentResult.ConsentFormDismissed(
          canRequestAds = consentInformation.canRequestAds(),
          requirementStatus = consentInformation.privacyOptionsRequirementStatus,
          formError = formError,
        )
      }
    }
  }

  @OptIn(ExperimentalAdsControlApi::class)
  fun resetConsent() {
    if (!::consentInformation.isInitialized) {
      return
    }
    adsControl.consentStatus.value = ConsentStatus.CONSENT_FORM_RESET
    consentInformation.reset()
    activity?.let { activity ->
      initialiseConsentForm(activity)
    }
  }

  internal val buildProfiler: BuildProfiler
    get() = LocalBuildProfiler.current

  @OptIn(ExperimentalAdsControlApi::class)
  fun initialiseConsentForm(
    activity: Activity,
  ) {
    consentInformation = UserMessagingPlatform.getConsentInformation(activity)

    val debugSettings = ConsentDebugSettings.Builder(activity).apply {
      if (buildProfiler.isDebuggable) {
        addTestDeviceHashedId(getHashedAdvertisingId(activity))
        setDebugGeography(ConsentDebugSettings.DebugGeography.DEBUG_GEOGRAPHY_EEA)
      }
    }.build()

    // Request consent information
    val params = ConsentRequestParameters.Builder()
      .setTagForUnderAgeOfConsent(false)
      .setConsentDebugSettings(debugSettings)
      .build()

    consentInformation.requestConsentInfoUpdate(
      activity,
      params,
      { onConsentInfoUpdateSuccess(activity) },
      { requestConsentError ->
        adsControl.consentStatus.value = ConsentStatus.CONSENT_FORM_ERROR
        Log.w(
          TAG,
          String.format("%s: %s", requestConsentError.errorCode, requestConsentError.message),
        )
      },
    )

    // Check if you can initialize the Google Mobile Ads SDK in parallel
    // while checking for new consent information. Consent obtained in
    // the previous session can be used to request ads.
    adsControl.canRequestAds.value = consentInformation.canRequestAds()
    if (consentInformation.canRequestAds()) {
      initializeMobileAdsSdk(activity)

      attemptToReloadAppOpenAd()
    }
  }

  @OptIn(ExperimentalAdsControlApi::class)
  private fun onConsentInfoUpdateSuccess(
    activity: Activity,
  ) {
    adsControl.consentRequirementStatus.value = consentInformation
      .privacyOptionsRequirementStatus
      .toConsentRequirementStatus()
    adsControl.canRequestAds.value = consentInformation.canRequestAds()

    if (!consentInformation.isConsentFormAvailable) {
      // Consent has been gathered or not required
      if (consentInformation.canRequestAds()) {
        initializeMobileAdsSdk(activity)
        attemptToReloadAppOpenAd()
      }

      state.value = ConsentResult.ConsentFormAcquired(
        canRequestAds = consentInformation.canRequestAds(),
        requirementStatus = consentInformation.privacyOptionsRequirementStatus,
        formAvailable = false,
      )
    } else {
      adsControl.consentStatus.value = ConsentStatus.CONSENT_FORM_ACQUIRED
      state.value = ConsentResult.ConsentFormAcquired(
        canRequestAds = consentInformation.canRequestAds(),
        requirementStatus = consentInformation.privacyOptionsRequirementStatus,
        formAvailable = true,
      )
    }
  }

  private fun initializeMobileAdsSdk(context: Context) {
    if (isMobileAdsInitializeCalled.get()) {
      return
    }
    isMobileAdsInitializeCalled.set(true)

    // Initialize the Google Mobile Ads SDK.
    AdMobInitializer.initialize(context)
  }

  private fun attemptToReloadAppOpenAd() {
    AdMob.getAppOpenAd()?.let { appOpenAd ->
      if (!appOpenAd.isAdLoaded()) {
        appOpenAd.load()
      }
    }
  }
}
