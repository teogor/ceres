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

package dev.teogor.ceres.core.register

import java.util.Locale

interface SupportedDialects {

  /**
   * Retrieves the language code of the default locale.
   *
   * @return The language code of the default locale as a string.
   */
  fun getDefaultLocaleLanguage(): String

  /**
   * Retrieves the default locale.
   *
   * @return The default locale as a Java [Locale] object.
   */
  fun getDefaultLocale(): Locale

  /**
   * Retrieves a map of supported languages and their corresponding names by their language code.
   * The returned map contains language codes as keys and their respective language names as values.
   *
   * @return A [Map] where keys represent the language codes and values indicate the corresponding
   * language names.
   */
  fun getSupportedLanguages(): List<String>

  /**
   * Retrieves a list of language tags supported by the project.
   *
   * @return A [List] of language tags.
   */
  fun getSupportedDialects(): List<String>

  /**
   * Retrieves a map of language tags and their written endonyms.
   * (Endonyms are the preferred name of a language as written in that language.)
   *
   * @return A [Map] where keys represent the language tags and values indicate the corresponding
   * language endonyms.
   */
  fun getEndonyms(): Map<String, String>

  /**
   * Retrieves a map of language tags and their written exonyms according to the resolved languageTag.
   * (Exonyms are the name of a language written in another language.)
   *
   * @param languageTag a Unicode-formatted language tag in [String] form such as "en-US".
   * @return A [Map] where keys represent the language tags and values indicate the corresponding
   * language exonyms.
   */
  fun getExonyms(languageTag: String): Map<String, String>

  /**
   * Retrieves a map of language tags and their written exonyms according to the resolved locale.
   * (Exonyms are the name of a language written in another language.)
   *
   * @param locale a Java [Locale] object.
   * @return A [Map] where keys represent the language tags and values indicate the corresponding
   * language exonyms.
   */
  fun getExonyms(locale: Locale = Locale.getDefault()): Map<String, String>

  /**
   * Retrieves a list of country codes for the given language code.
   *
   * @param languageCode the language code.
   * @return A [List] of country codes.
   */
  fun getCountryCodesForLanguage(languageCode: String): List<String>

  /**
   * Retrieves a list of country names for the given language code.
   *
   * @param languageCode the language code.
   * @return A [List] of country names.
   */
  fun getCountryNamesForLanguage(languageCode: String): List<String>

  /**
   * Checks if the project supports multiple countries for the given language code.
   *
   * @param languageCode the language code.
   * @return `true` if the project supports multiple countries for the given language code,
   * `false` otherwise.
   */
  fun hasMultipleCountriesForLanguage(languageCode: String): Boolean

  /**
   * Retrieves the current locale.
   *
   * @return The current locale as a Java [Locale] object.
   */
  fun getCurrentLocale(): Locale

  /**
   * Checks if the given language code is selected.
   *
   * @param languageCode the language code.
   * @return `true` if the given language code is selected, `false` otherwise.
   */
  fun isLanguageSelected(languageCode: String): Boolean
}

val LocalSupportedDialects = staticRegistryLocalOf<SupportedDialects> {
  intrinsicImplementation<SupportedDialects>()
}
