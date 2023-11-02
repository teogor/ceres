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

class SupportedDialects2 : SupportedDialects {

  private val supportedLanguages = listOf(
    "ar", "de", "en", "es",
    "hi", "ja", "ko", "nl",
    "ro", "zh",
  )

  @Deprecated("supportedLocales")
  private val tags: List<String> = listOf(
    "ar",
    "de-DE",
    "en-GB",
    "en-US",
    "es",
    "hi",
    "ja",
    "ko-KR",
    "nl-NL",
    "ro-RO",
    "zh-CN",
  )

  private val endonyms: Map<String, String> = mapOf(
    "ar" to "العربية",
    "de-DE" to "Deutsch (Deutschland)",
    "en-GB" to "English (United Kingdom)",
    "en-US" to "English (United States)",
    "es" to "español",
    "hi" to "हिन्दी",
    "ja" to "日本語",
    "ko-KR" to "한국어 (대한민국)",
    "nl-NL" to "Nederlands (Nederland)",
    "ro-RO" to "română (România)",
    "zh-CN" to "中文 (中国)",
  )

  private val exonyms: Map<String, Map<String, String>> = mapOf(
    "ar" to mapOf(
      "ar" to "العربية",
      "de-DE" to "الألمانية (ألمانيا)",
      "en-GB" to "الإنجليزية (المملكة المتحدة)",
      "en-US" to "الإنجليزية (الولايات المتحدة)",
      "es" to "الإسبانية",
      "hi" to "الهندية",
      "ja" to "اليابانية",
      "ko-KR" to "الكورية (كوريا الجنوبية)",
      "nl-NL" to "الهولندية (هولندا)",
      "ro-RO" to "الرومانية (رومانيا)",
      "zh-CN" to "الصينية (الصين)",
    ),
    "de-DE" to mapOf(
      "ar" to "Arabisch",
      "de-DE" to "Deutsch (Deutschland)",
      "en-GB" to "Englisch (Vereinigtes Königreich)",
      "en-US" to "Englisch (Vereinigte Staaten)",
      "es" to "Spanisch",
      "hi" to "Hindi",
      "ja" to "Japanisch",
      "ko-KR" to "Koreanisch (Südkorea)",
      "nl-NL" to "Niederländisch (Niederlande)",
      "ro-RO" to "Rumänisch (Rumänien)",
      "zh-CN" to "Chinesisch (China)",
    ),
    "en-GB" to mapOf(
      "ar" to "Arabic",
      "de-DE" to "German (Germany)",
      "en-GB" to "English (United Kingdom)",
      "en-US" to "English (United States)",
      "es" to "Spanish",
      "hi" to "Hindi",
      "ja" to "Japanese",
      "ko-KR" to "Korean (South Korea)",
      "nl-NL" to "Dutch (Netherlands)",
      "ro-RO" to "Romanian (Romania)",
      "zh-CN" to "Chinese (China)",
    ),
    "en-US" to mapOf(
      "ar" to "Arabic",
      "de-DE" to "German (Germany)",
      "en-GB" to "English (United Kingdom)",
      "en-US" to "English (United States)",
      "es" to "Spanish",
      "hi" to "Hindi",
      "ja" to "Japanese",
      "ko-KR" to "Korean (South Korea)",
      "nl-NL" to "Dutch (Netherlands)",
      "ro-RO" to "Romanian (Romania)",
      "zh-CN" to "Chinese (China)",
    ),
    "es" to mapOf(
      "ar" to "árabe",
      "de-DE" to "alemán (Alemania)",
      "en-GB" to "inglés (Reino Unido)",
      "en-US" to "inglés (Estados Unidos)",
      "es" to "español",
      "hi" to "hindi",
      "ja" to "japonés",
      "ko-KR" to "coreano (Corea del Sur)",
      "nl-NL" to "neerlandés (Países Bajos)",
      "ro-RO" to "rumano (Rumanía)",
      "zh-CN" to "chino (China)",
    ),
    "hi" to mapOf(
      "ar" to "अरबी",
      "de-DE" to "जर्मन (जर्मनी)",
      "en-GB" to "अंग्रेज़ी (यूनाइटेड किंगडम)",
      "en-US" to "अंग्रेज़ी (संयुक्त राज्य)",
      "es" to "स्पेनी",
      "hi" to "हिन्दी",
      "ja" to "जापानी",
      "ko-KR" to "कोरियाई (दक्षिण कोरिया)",
      "nl-NL" to "डच (नीदरलैंड)",
      "ro-RO" to "रोमानियाई (रोमानिया)",
      "zh-CN" to "चीनी (चीन)",
    ),
    "ja" to mapOf(
      "ar" to "アラビア語",
      "de-DE" to "ドイツ語 (ドイツ)",
      "en-GB" to "英語 (イギリス)",
      "en-US" to "英語 (アメリカ合衆国)",
      "es" to "スペイン語",
      "hi" to "ヒンディー語",
      "ja" to "日本語",
      "ko-KR" to "韓国語 (韓国)",
      "nl-NL" to "オランダ語 (オランダ)",
      "ro-RO" to "ルーマニア語 (ルーマニア)",
      "zh-CN" to "中国語 (中国)",
    ),
    "ko-KR" to mapOf(
      "ar" to "아랍어",
      "de-DE" to "독일어 (독일)",
      "en-GB" to "영어 (영국)",
      "en-US" to "영어 (미국)",
      "es" to "스페인어",
      "hi" to "힌디어",
      "ja" to "일본어",
      "ko-KR" to "한국어 (대한민국)",
      "nl-NL" to "네덜란드어 (네덜란드)",
      "ro-RO" to "루마니아어 (루마니아)",
      "zh-CN" to "중국어 (중국)",
    ),
    "nl-NL" to mapOf(
      "ar" to "Arabisch",
      "de-DE" to "Duits (Duitsland)",
      "en-GB" to "Engels (Verenigd Koninkrijk)",
      "en-US" to "Engels (Verenigde Staten)",
      "es" to "Spaans",
      "hi" to "Hindi",
      "ja" to "Japans",
      "ko-KR" to "Koreaans (Zuid-Korea)",
      "nl-NL" to "Nederlands (Nederland)",
      "ro-RO" to "Roemeens (Roemenië)",
      "zh-CN" to "Chinees (China)",
    ),
    "ro-RO" to mapOf(
      "ar" to "arabă",
      "de-DE" to "germană (Germania)",
      "en-GB" to "engleză (Regatul Unit)",
      "en-US" to "engleză (Statele Unite ale Americii)",
      "es" to "spaniolă",
      "hi" to "hindi",
      "ja" to "japoneză",
      "ko-KR" to "coreeană (Coreea de Sud)",
      "nl-NL" to "neerlandeză (Țările de Jos)",
      "ro-RO" to "română (România)",
      "zh-CN" to "chineză (China)",
    ),
    "zh-CN" to mapOf(
      "ar" to "阿拉伯语",
      "de-DE" to "德语 (德国)",
      "en-GB" to "英语 (英国)",
      "en-US" to "英语 (美国)",
      "es" to "西班牙语",
      "hi" to "印地语",
      "ja" to "日语",
      "ko-KR" to "韩语 (韩国)",
      "nl-NL" to "荷兰语 (荷兰)",
      "ro-RO" to "罗马尼亚语 (罗马尼亚)",
      "zh-CN" to "中文 (中国)",
    ),
  )

  private fun errorTagNotFound(errorMessage: String): Nothing = throw NoSuchElementException(
    """
  |The specified language tag ("$errorMessage") was not located in your supported locales.
  |Ensure it's included within the 'supportedLanguages' property in your Gradle setup.
  |
  |```kt
  |buildOptions {
  |  buildFeatures {
  |    addSupportedLanguages {
  |      listOf(
  |        // add it here
  |        "$errorMessage",
  |      )
  |    }
  |  }
  |}
  |```
    """.trimMargin(),
  )

  override fun getDefaultLocaleLanguage(): String = "en-US"

  override fun getDefaultLocale(): Locale = Locale.forLanguageTag("en-US")

  /**
   * Retrieves a map of supported languages and their corresponding names by their language code.
   * The returned map contains language codes as keys and their respective language names as values.
   *
   * @return A [Map] where keys represent the language codes and values indicate the corresponding
   * language names.
   */
  override fun getSupportedLanguages() = supportedLanguages

  /**
   * @returns List of language tags supported by your project.
   */
  override fun getSupportedDialects(): List<String> = tags

  /**
   * @returns Map of language tags and their written endoynms.
   * (Endonyms are the preferred name of a language as written in that language.)
   */
  override fun getEndonyms(): Map<String, String> = endonyms

  /**
   * @param languageTag a Unicode-formatted language tag in [String] form such as "en-US".
   * @returns Map of language tags and their written exoynms according to the resolved languageTag.
   * (Exonyms are the name of a language written in another language.)
   */
  override fun getExonyms(languageTag: String): Map<String, String> =
    exonyms[languageTag] ?: exonyms[getDefaultLocaleLanguage()] ?: errorTagNotFound(languageTag)

  /**
   * @param locale a Java [Locale] object.
   * @returns Map of language tags and their written exoynms according to the resolved locale.
   * (Exonyms are the name of a language written in another language.)
   */
  override fun getExonyms(locale: Locale): Map<String, String> =
    exonyms[locale.toLanguageTag()] ?: exonyms[getDefaultLocaleLanguage()] ?: errorTagNotFound(
      locale.toLanguageTag(),
    )

  override fun getCountryCodesForLanguage(languageCode: String): List<String> = tags.filter {
    it.startsWith(languageCode)
  }

  override fun getCountryNamesForLanguage(languageCode: String): List<String> =
    getCountryCodesForLanguage(languageCode).mapNotNull { endonyms[it] }

  override fun hasMultipleCountriesForLanguage(languageCode: String): Boolean =
    getCountryCodesForLanguage(languageCode).size > 1

  override fun getCurrentLocale(): Locale = Locale.getDefault()

  override fun isLanguageSelected(languageCode: String): Boolean {
    val currentLocale = Locale.getDefault().toLanguageTag()
    return languageCode == currentLocale || currentLocale.startsWith(languageCode)
  }
}

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
