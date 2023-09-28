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

package dev.teogor.ceres.firebase.analytics

import androidx.compose.runtime.staticCompositionLocalOf

// todo move this somewhere in base
//  like module firebase-base-helpers
//   ... ceres modules include that
//   analytics/crashlytics depends on that
/**
 * Global key used to obtain access to the AnalyticsHelper through a CompositionLocal.
 */
val LocalAnalyticsHelper = staticCompositionLocalOf<AnalyticsHelper> {
  // Provide a default AnalyticsHelper which does nothing. This is so that tests and previews
  // do not have to provide one. For real app builds provide a different implementation.
  NoOpAnalyticsHelper()
}
