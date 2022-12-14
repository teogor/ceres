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

package dev.teogor.ceres.firebase.app_check

import com.google.firebase.FirebaseException
import com.google.firebase.appcheck.AppCheckTokenResult
import com.google.firebase.appcheck.FirebaseAppCheck
import com.google.firebase.appcheck.safetynet.SafetyNetAppCheckProviderFactory
import dev.teogor.ceres.firebase.FirebaseMemo
import javax.inject.Inject

class AppCheck @Inject constructor(
  private val firebaseMemo: FirebaseMemo
) {

  fun validateSignature() {
    val firebaseAppCheck: FirebaseAppCheck = FirebaseAppCheck.getInstance()
    firebaseAppCheck.installAppCheckProviderFactory(
      SafetyNetAppCheckProviderFactory.getInstance()
    )
    firebaseAppCheck.getToken(true).addOnCompleteListener { task ->
      val appCheckTokenResult: AppCheckTokenResult = task.result
      val firebaseException: FirebaseException? = appCheckTokenResult.error
      val isValid = firebaseException == null
      firebaseMemo.hasValidSignature = isValid
      firebaseMemo.signatureChecked = true
    }
  }
}
