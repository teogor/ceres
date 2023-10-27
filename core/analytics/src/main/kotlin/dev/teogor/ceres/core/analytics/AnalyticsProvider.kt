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

package dev.teogor.ceres.core.analytics

import androidx.annotation.RestrictTo
import kotlin.reflect.KClass

@ExperimentalAnalyticsApi
open class AnalyticsProvider {
  private val _analyticsHelpers: MutableList<AnalyticsHelper> = mutableListOf()

  @get:RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
  val analyticsHelpers: List<AnalyticsHelper>
    get() = _analyticsHelpers.toList()

  fun <T : AnalyticsHelper> getAnalyticsHelper(helperClass: Class<T>): T {
    return _analyticsHelpers
      .filterIsInstance(helperClass)
      .firstOrNull()
      ?: throw RuntimeException("AnalyticsHelper of type $helperClass not found.")
  }

  fun addAnalyticsHelper(helper: AnalyticsHelper): AnalyticsHelper {
    val existingHelper = _analyticsHelpers.filterIsInstance(helper::class.java).firstOrNull()
    if (existingHelper == null) {
      _analyticsHelpers.add(helper)
    }
    return existingHelper ?: helper
  }
}

@ExperimentalAnalyticsApi
@Suppress("NOTHING_TO_INLINE")
inline operator fun <T : AnalyticsHelper> AnalyticsProvider.get(
  clazz: KClass<T>,
): T = getAnalyticsHelper(clazz.java)

@ExperimentalAnalyticsApi
@Suppress("NOTHING_TO_INLINE")
inline operator fun AnalyticsProvider.plusAssign(helper: AnalyticsHelper) {
  addAnalyticsHelper(helper)
}
