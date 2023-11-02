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

package dev.teogor.querent.languages.schema

import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import java.util.Locale

/**
 * Object that handles language settings and provides methods to change the application language.
 */
object LanguageSettings {

  /**
   * Changes the application language based on the specified locale.
   *
   * @param locale The locale to set as the application language.
   */
  fun setAppLanguage(locale: Locale) {
    setAppLanguage(locale.language)
  }

  /**
   * Changes the application language based on the specified language tag.
   *
   * @param languageTag The language tag to set as the application language.
   */
  fun setAppLanguage(languageTag: String) {
    AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(languageTag))
  }
}
