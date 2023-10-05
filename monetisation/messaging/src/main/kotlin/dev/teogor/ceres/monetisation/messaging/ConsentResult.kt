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

import com.google.android.ump.ConsentInformation.PrivacyOptionsRequirementStatus
import com.google.android.ump.FormError

@Deprecated(
  message = "Use the Ads Control API in the 'dev.teogor.ceres.monetisation.ads' package for better control over ads.",
  replaceWith = ReplaceWith(expression = "AdsControl"),
  level = DeprecationLevel.WARNING, // Or your preferred deprecation level
)
sealed class ConsentResult {
  @Deprecated(
    message = "Use the Ads Control API in the 'dev.teogor.ceres.monetisation.ads' package for better control over ads.",
    replaceWith = ReplaceWith(expression = "AdsControl"),
    level = DeprecationLevel.WARNING, // Or your preferred deprecation level
  )
  data object Undefined : ConsentResult()

  @Deprecated(
    message = "Use the Ads Control API in the 'dev.teogor.ceres.monetisation.ads' package for better control over ads.",
    replaceWith = ReplaceWith(expression = "AdsControl"),
    level = DeprecationLevel.WARNING, // Or your preferred deprecation level
  )
  data class ConsentFormAcquired(
    val canRequestAds: Boolean,
    val requirementStatus: PrivacyOptionsRequirementStatus,
    val formAvailable: Boolean,
  ) : ConsentResult()

  @Deprecated(
    message = "Use the Ads Control API in the 'dev.teogor.ceres.monetisation.ads' package for better control over ads.",
    replaceWith = ReplaceWith(expression = "AdsControl"),
    level = DeprecationLevel.WARNING, // Or your preferred deprecation level
  )
  data class ConsentFormDismissed(
    val canRequestAds: Boolean,
    val requirementStatus: PrivacyOptionsRequirementStatus,
    val formError: FormError?,
  ) : ConsentResult()
}
