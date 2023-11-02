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

package dev.teogor.linguistic

sealed class Script {
  abstract val code: String

  abstract val name: String

  abstract val isRtl: Boolean

  abstract val languages: List<String>

  data object Arabic : Script() {
    override val code: String = "Arab"

    override val name: String = "Arabic"

    override val isRtl: Boolean = true

    override val languages: List<String> = listOf("ar")
  }

  data object Cyrillic : Script() {
    override val code: String = "Cyrl"

    override val name: String = "Cyrillic"

    override val isRtl: Boolean = false

    override val languages: List<String> = listOf("ru", "uk", "bg", "sr", "mk")
  }

  data object Gurmukhi : Script() {
    override val code: String = "Guru"

    override val name: String = "Gurmukhi"

    override val isRtl: Boolean = false

    override val languages: List<String> = listOf("pa")
  }

  data object Hangul : Script() {
    override val code: String = "Hang"

    override val name: String = "Hangul"

    override val isRtl: Boolean = false

    override val languages: List<String> = listOf("ko")
  }

  data object Latin : Script() {
    override val code: String = "Latn"

    override val name: String = "Latin"

    override val isRtl: Boolean = false

    override val languages: List<String> = listOf(
      "en", "es", "fr", "de", "it", "pt", "ru", "uk",
      "bg", "sr", "mk",
    )
  }

  data object SimplifiedChinese : Script() {
    override val code: String = "Hans"

    override val name: String = "Simplified Chinese"

    override val isRtl: Boolean = false

    override val languages: List<String> = listOf("zh-CN", "zh-SG")
  }

  data object Tifinagh : Script() {
    override val code: String = "Tfng"

    override val name: String = "Tifinagh"

    override val isRtl: Boolean = false

    override val languages: List<String> = listOf("tzm")
  }

  data object TraditionalChinese : Script() {
    override val code: String = "Hant"

    override val name: String = "Traditional Chinese"

    override val isRtl: Boolean = false

    override val languages: List<String> = listOf("zh-TW", "zh-HK")
  }

  data object Vai : Script() {
    override val code: String = "Vaii"

    override val name: String = "Vai"

    override val isRtl: Boolean = false

    override val languages: List<String> = listOf("vai")
  }
}
