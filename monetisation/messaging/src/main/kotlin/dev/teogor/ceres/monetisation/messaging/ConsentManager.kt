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
import dev.teogor.ceres.core.runtime.AppMetadataManager
import dev.teogor.ceres.monetisation.admob.AdMobInitializer
import dev.teogor.ceres.monetisation.admob.AdMobInitializer.getHashedAdvertisingId
import java.lang.ref.WeakReference
import java.util.concurrent.atomic.AtomicBoolean

object ConsentManager {
  internal lateinit var consentInformation: ConsentInformation
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

  fun loadAndShowConsentFormIfRequired() {
    activity?.let {
      UserMessagingPlatform.loadAndShowConsentFormIfRequired(
        it,
      ) { formError ->
        state.value = ConsentResult.ConsentFormDismissed(
          canRequestAds = consentInformation.canRequestAds(),
          requirementStatus = consentInformation.privacyOptionsRequirementStatus,
          formError = formError,
        )
      }
    }
  }

  fun resetConsent() {
    if (!::consentInformation.isInitialized) {
      return
    }
    consentInformation.reset()
    activity?.let { activity ->
      initialiseConsentForm(activity)
    }
  }

  fun initialiseConsentForm(
    activity: Activity,
  ) {
    consentInformation = UserMessagingPlatform.getConsentInformation(activity)

    val debugSettings = ConsentDebugSettings.Builder(activity).apply {
      if (AppMetadataManager.isDebuggable) {
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
        Log.w(
          TAG,
          String.format("%s: %s", requestConsentError.errorCode, requestConsentError.message),
        )
      },
    )

    // Check if you can initialize the Google Mobile Ads SDK in parallel
    // while checking for new consent information. Consent obtained in
    // the previous session can be used to request ads.
    if (consentInformation.canRequestAds()) {
      initializeMobileAdsSdk(activity)
    }
  }

  private fun onConsentInfoUpdateSuccess(
    activity: Activity,
  ) {
    if (!consentInformation.isConsentFormAvailable) {
      // Consent has been gathered or not required
      if (consentInformation.canRequestAds()) {
        initializeMobileAdsSdk(activity)
      }

      state.value = ConsentResult.ConsentFormAcquired(
        canRequestAds = consentInformation.canRequestAds(),
        requirementStatus = consentInformation.privacyOptionsRequirementStatus,
        formAvailable = false,
      )
    } else {
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
}
