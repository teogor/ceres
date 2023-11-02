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

package dev.teogor.querent.utils

import java.util.Locale

/**
 * Converts a language tag to Unicode-friendly tags that work more consistently with the Java [Locale] class.
 */
fun String.convertToUnicodeLanguageTag(): String {
  return this.removePrefix("b+")
    .replace('+', '-')
    .replace("-r", "-")
}

/**
 * Checks the validity of a language tag
 * @return True/false if the provided tag is ISO valid for languages
 *
 * Example - 'fr-FR' is a language tag and is valid
 *
 * Example - 'sw320dp' is a screen dimension tag and is not valid
 */
fun String.isIsoValid(): Boolean {
  // Prevents stripping of pseudo-locales
  if (this == "en-XA" || this == "ar-XB") {
    return true
  }

  val parts = split('-')
  val one = parts.getOrNull(0)
  val two = parts.getOrNull(1)
  val three = parts.getOrNull(2)

  one?.let {
    if (it !in Locale.getISOLanguages()) {
      return false
    }
  }

  two?.let {
    if ((it.length != 4) && (it !in Locale.getISOCountries())) {
      return false
    }
  }

  three?.let {
    if (it !in Locale.getISOCountries()) {
      return false
    }
  }

  return true
}
