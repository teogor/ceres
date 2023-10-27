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

package dev.teogor.ceres.monetisation.ads.extensions

import dev.teogor.ceres.monetisation.ads.model.AdContentRating
import dev.teogor.ceres.monetisation.ads.model.AdRequestOptions
import dev.teogor.ceres.monetisation.ads.model.TagForChildDirectedTreatment
import dev.teogor.ceres.monetisation.ads.model.TagForUnderAgeOfConsent

/**
 * Extension function to configure AdRequestOptions for "Apps designed
 * specifically for children."
 *
 * These apps must use Google Play Families self-certified ads SDKs and
 * set properties for compliance.
 */
fun AdRequestOptions.setForChildrenApp() {
  maxAdContentRating = AdContentRating.G
  tagForChildDirectedTreatment = TagForChildDirectedTreatment.TRUE
  tagForUnderAgeOfConsent = TagForUnderAgeOfConsent.FALSE
}

/**
 * Extension function to configure AdRequestOptions for "Families".
 * These apps must use Google Play Families self-certified ads SDKs and
 * set properties for compliance.
 *
 * @param primarilyChildDirected Set to true for apps primarily child-directed
 *                               where the maximum ad content rating will be "G".
 * @param mixedAudience Set to true for apps with a mixed audience where the maximum
 *                      ad content rating will be "PG" if greater than PG.
 */
fun AdRequestOptions.setForFamilies(
  primarilyChildDirected: Boolean = false,
  mixedAudience: Boolean = false,
) {
  maxAdContentRating = when {
    primarilyChildDirected -> AdContentRating.G
    mixedAudience -> AdContentRating.PG
    else -> AdContentRating.G // Default for other cases
  }
  tagForChildDirectedTreatment = TagForChildDirectedTreatment.TRUE
  tagForUnderAgeOfConsent = TagForUnderAgeOfConsent.FALSE
}

/**
 * Extension function to configure AdRequestOptions for "Apps for everyone,
 * including children and families."
 *
 * These apps must use Google Play Families self-certified ads SDKs and set
 * properties for compliance.
 */
fun AdRequestOptions.setForEveryoneApp() {
  maxAdContentRating = AdContentRating.G
  tagForChildDirectedTreatment = TagForChildDirectedTreatment.TRUE
  tagForUnderAgeOfConsent = TagForUnderAgeOfConsent.FALSE
}

/**
 * Extension function to configure AdRequestOptions for "Teens".
 *
 * Set ad content rating to "T" (Teen) and tag for child-directed treatment
 * to false.
 */
fun AdRequestOptions.setForTeens() {
  maxAdContentRating = AdContentRating.T
  tagForChildDirectedTreatment = TagForChildDirectedTreatment.FALSE
  tagForUnderAgeOfConsent = TagForUnderAgeOfConsent.FALSE
}

/**
 * Extension function to configure AdRequestOptions for "Adults".
 *
 * Set ad content rating to "MA" (Mature) and tag for child-directed treatment
 * to false.
 */
fun AdRequestOptions.setForAdults() {
  maxAdContentRating = AdContentRating.MA
  tagForChildDirectedTreatment = TagForChildDirectedTreatment.FALSE
  tagForUnderAgeOfConsent = TagForUnderAgeOfConsent.FALSE
}
