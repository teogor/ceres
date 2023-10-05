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
import android.app.Application
import android.content.Context
import android.os.Bundle
import dev.teogor.ceres.monetisation.admob.AdMobInitializer
import dev.teogor.ceres.monetisation.ads.AndroidAdsControl
import dev.teogor.ceres.monetisation.ads.ExperimentalAdsControlApi
import java.util.concurrent.atomic.AtomicBoolean

class MessagingManager {
  companion object {
    private var isMobileAdsInitializeCalled = AtomicBoolean(false)

    @OptIn(ExperimentalAdsControlApi::class)
    fun initialize(context: Context) {
      AdMobInitializer.configureAdsControl(
        AndroidAdsControl(
          showConsent = {
            ConsentManager.loadAndShowConsentFormIfRequired()
          },
          resetConsent = {
            ConsentManager.resetConsent()
          },
        ),
      )

      if (context is Application) {
        context.registerActivityLifecycleCallbacks(
          object : Application.ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
              ConsentManager.activity = activity
              if (!isMobileAdsInitializeCalled.get()) {
                ConsentManager.initialiseConsentForm(
                  activity = activity,
                )
                isMobileAdsInitializeCalled.set(true)
              }
            }

            override fun onActivityStarted(activity: Activity) {
              // Your initialization code when an activity is started
              ConsentManager.activity = activity
            }

            override fun onActivityResumed(activity: Activity) {
              // Your initialization code when an activity is resumed
              ConsentManager.activity = activity
            }

            override fun onActivityPaused(activity: Activity) {
              // Your initialization code when an activity is paused
            }

            override fun onActivityStopped(activity: Activity) {
              // Your initialization code when an activity is stopped
            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
              // Your initialization code when an activity's state is saved
            }

            override fun onActivityDestroyed(activity: Activity) {
              // Your initialization code when an activity is destroyed
            }
          },
        )
      }
    }
  }
}
