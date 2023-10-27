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

package dev.teogor.ceres.monetisation.ads.model

/**
 * Data class representing options for configuring a request configuration for ad requests.
 *
 * @property maxAdContentRating The option for setting the maximum ad content rating.
 * @property tagForChildDirectedTreatment The option for setting child-directed treatment.
 * @property tagForUnderAgeOfConsent The option for setting under-age-of-consent treatment.
 */
data class AdRequestOptions(
  var maxAdContentRating: AdContentRating = AdContentRating.UNSPECIFIED,
  var tagForChildDirectedTreatment: TagForChildDirectedTreatment = TagForChildDirectedTreatment.UNSPECIFIED,
  var tagForUnderAgeOfConsent: TagForUnderAgeOfConsent = TagForUnderAgeOfConsent.UNSPECIFIED,
)
