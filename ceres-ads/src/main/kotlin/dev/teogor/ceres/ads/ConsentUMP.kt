/*
 * Copyright 2022 teogor (Teodor Grigor) All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dev.teogor.ceres.ads

import com.google.android.ump.ConsentDebugSettings
import com.google.android.ump.ConsentForm
import com.google.android.ump.ConsentInformation
import com.google.android.ump.ConsentRequestParameters
import com.google.android.ump.UserMessagingPlatform
import com.zeoflow.startup.ktx.ApplicationInitializer
import dev.teogor.ceres.core.construct.AppData
import dev.teogor.ceres.core.global.GlobalData
import dev.teogor.ceres.core.util.AppUtils
import javax.inject.Inject

class ConsentUMP @Inject constructor() {

  private val consentInformation = UserMessagingPlatform.getConsentInformation(
    ApplicationInitializer.context
  )
  private var consentForm: ConsentForm? = null

  fun prepareIabConsent() {
    val consentRequestParams = ConsentRequestParameters.Builder()
    consentRequestParams.setTagForUnderAgeOfConsent(false)

    if (AppData.IsDebuggable) {
      val debugSettings = ConsentDebugSettings.Builder(ApplicationInitializer.context)
        .setDebugGeography(ConsentDebugSettings.DebugGeography.DEBUG_GEOGRAPHY_EEA)
        .addTestDeviceHashedId(AppUtils.deviceID)
        .build()
      consentRequestParams.setConsentDebugSettings(debugSettings)
    }

    consentInformation.requestConsentInfoUpdate(
      GlobalData.activity,
      consentRequestParams.build(),
      {
        // The consent information state was updated.
        // You are now ready to check if a form is available.
        if (consentInformation.isConsentFormAvailable) {
          loadForm()
        }
      },
      { }
    )
  }

  private fun loadForm() {
    UserMessagingPlatform.loadConsentForm(
      ApplicationInitializer.context,
      { consentForm: ConsentForm ->
        this.consentForm = consentForm
        showConsentForm()
      },
      {
      }
    )
  }

  private fun showConsentForm() {
    if (consentForm == null) {
      return
    }
    if (consentInformation.consentStatus == ConsentInformation.ConsentStatus.REQUIRED) {
      consentForm!!.show(GlobalData.activity) {
        // Handle dismissal by reloading form.
        loadForm()
      }
    }
  }
}
