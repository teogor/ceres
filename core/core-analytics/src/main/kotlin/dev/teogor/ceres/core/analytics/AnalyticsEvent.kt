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

enum class Types {
  SCREEN_VIEW, // (extras: SCREEN_NAME, SCREEN_CLASS)
}

fun Types.toLowercaseString(): String {
  return this.name.lowercase()
}

enum class ParamKeys {
  SCREEN_NAME,
  SCREEN_CLASS,
}

fun ParamKeys.toLowercaseString(): String {
  return this.name.lowercase()
}

sealed class AnalyticsEvent {
  abstract val type: Types
  abstract val params: List<Param>

  data class ScreenView(
    val screenName: String,
    val screenClass: String,
  ) : AnalyticsEvent() {
    override val type: Types
      get() = Types.SCREEN_VIEW

    override val params: List<Param>
      get() = listOf(
        Param(ParamKeys.SCREEN_NAME, screenName),
        Param(ParamKeys.SCREEN_CLASS, screenClass),
      )
  }
}

/**
 * A key-value pair used to supply extra context to an analytics event.
 *
 * @param key - the parameter key. Wherever possible use one of the standard `ParamKeys`,
 * however, if no suitable key is available you can define your own as long as it is configured
 * in your backend analytics system (for example, by creating a Firebase Analytics custom
 * parameter).
 *
 * @param value - the parameter value.
 */
data class Param(val key: ParamKeys, val value: String)
