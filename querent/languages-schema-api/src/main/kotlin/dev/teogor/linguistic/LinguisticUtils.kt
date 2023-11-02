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

import java.util.Locale

typealias JavaLocale = Locale

infix fun Language.displayName(language: Language): String = getDisplayName(language)

infix fun Language.isSpokenIn(country: Country): Boolean = isSpokenIn(country)

infix fun Language.isWrittenIn(script: Script): Boolean = isWrittenIn(script)

/**
 * TODO: Implement logic to check if this language is translated
 *  into the provided language. This function should verify if there
 *  exists a translation from this language to the given language.
 */
infix fun Language.isTranslatedIn(language: Language): Boolean = false

infix fun <L : Language, C : Country> L.territorialize(country: C): Dialect = Dialect(
  country =
  country,
  language = this,
)
