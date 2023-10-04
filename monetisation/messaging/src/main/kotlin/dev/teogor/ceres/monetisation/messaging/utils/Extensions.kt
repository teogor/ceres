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

package dev.teogor.ceres.monetisation.messaging.utils

import com.google.android.ump.ConsentInformation.PrivacyOptionsRequirementStatus
import dev.teogor.ceres.monetisation.ads.model.ConsentRequirementStatus

fun PrivacyOptionsRequirementStatus.toConsentRequirementStatus(): ConsentRequirementStatus {
  return when (this) {
    PrivacyOptionsRequirementStatus.UNKNOWN -> ConsentRequirementStatus.UNKNOWN
    PrivacyOptionsRequirementStatus.NOT_REQUIRED -> ConsentRequirementStatus.NOT_REQUIRED
    PrivacyOptionsRequirementStatus.REQUIRED -> ConsentRequirementStatus.REQUIRED
  }
}
