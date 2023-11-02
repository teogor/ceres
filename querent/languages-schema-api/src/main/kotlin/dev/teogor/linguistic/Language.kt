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

sealed class Language : LanguageFamily {
  abstract val code: String

  abstract val name: String

  abstract val nativeName: String

  val locale: JavaLocale
    get() = JavaLocale(code)

  override val languageTag: String
    get() = code

  override fun asLanguageTag(): LanguageTag = LanguageTag(code, this)

  fun getDisplayName(language: Language): String = getDisplayName(language.code)

  fun getDisplayName(languageCode: String): String = if (languageCode == code) {
    nativeName
  } else {
    JavaLocale(code).getDisplayLanguage(JavaLocale(languageCode))
  }

  fun isSpokenIn(country: Country): Boolean = isSpokenIn(country.code)

  fun isSpokenIn(countryCode: String): Boolean = LanguageTerritory.isSpokenIn(
    code,
    countryCode,
  )

  fun isWrittenIn(script: Script): Boolean {
    val languageCodes = script.languages
    val languageCodesWithoutCountry = languageCodes.map { it.substringBefore("-") }

    return code in languageCodes || code in languageCodesWithoutCountry
  }

  data object Afrikaans : Language() {
    override val code: String = "af"

    override val name: String = "Afrikaans"

    override val nativeName: String = "Afrikaans"
  }

  data object Aghem : Language() {
    override val code: String = "agq"

    override val name: String = "Aghem"

    override val nativeName: String = "Aghem"
  }

  data object Akan : Language() {
    override val code: String = "ak"

    override val name: String = "Akan"

    override val nativeName: String = "Akan"
  }

  data object Albanian : Language() {
    override val code: String = "sq"

    override val name: String = "Albanian"

    override val nativeName: String = "shqip"
  }

  data object Amharic : Language() {
    override val code: String = "am"

    override val name: String = "Amharic"

    override val nativeName: String = "አማርኛ"
  }

  data object Arabic : Language() {
    override val code: String = "ar"

    override val name: String = "Arabic"

    override val nativeName: String = "العربية"
  }

  data object Armenian : Language() {
    override val code: String = "hy"

    override val name: String = "Armenian"

    override val nativeName: String = "հայերեն"
  }

  data object Assamese : Language() {
    override val code: String = "as"

    override val name: String = "Assamese"

    override val nativeName: String = "অসমীয়া"
  }

  data object Asturian : Language() {
    override val code: String = "ast"

    override val name: String = "Asturian"

    override val nativeName: String = "asturianu"
  }

  data object Asu : Language() {
    override val code: String = "asa"

    override val name: String = "Asu"

    override val nativeName: String = "Kipare"
  }

  data object Azerbaijani : Language() {
    override val code: String = "az"

    override val name: String = "Azerbaijani"

    override val nativeName: String = "azərbaycan"
  }

  data object Bafia : Language() {
    override val code: String = "ksf"

    override val name: String = "Bafia"

    override val nativeName: String = "rikpa"
  }

  data object Bambara : Language() {
    override val code: String = "bm"

    override val name: String = "Bambara"

    override val nativeName: String = "bamanakan"
  }

  data object Bangla : Language() {
    override val code: String = "bn"

    override val name: String = "Bangla"

    override val nativeName: String = "বাংলা"
  }

  data object Basaa : Language() {
    override val code: String = "bas"

    override val name: String = "Basaa"

    override val nativeName: String = "Ɓàsàa"
  }

  data object Basque : Language() {
    override val code: String = "eu"

    override val name: String = "Basque"

    override val nativeName: String = "euskara"
  }

  data object Belarusian : Language() {
    override val code: String = "be"

    override val name: String = "Belarusian"

    override val nativeName: String = "беларуская"
  }

  data object Bemba : Language() {
    override val code: String = "bem"

    override val name: String = "Bemba"

    override val nativeName: String = "Ichibemba"
  }

  data object Bena : Language() {
    override val code: String = "bez"

    override val name: String = "Bena"

    override val nativeName: String = "Hibena"
  }

  data object Bodo : Language() {
    override val code: String = "brx"

    override val name: String = "Bodo"

    override val nativeName: String = "बड़ो"
  }

  data object Bosnian : Language() {
    override val code: String = "bs"

    override val name: String = "Bosnian"

    override val nativeName: String = "bosanski"
  }

  data object Breton : Language() {
    override val code: String = "br"

    override val name: String = "Breton"

    override val nativeName: String = "brezhoneg"
  }

  data object Bulgarian : Language() {
    override val code: String = "bg"

    override val name: String = "Bulgarian"

    override val nativeName: String = "български"
  }

  data object Burmese : Language() {
    override val code: String = "my"

    override val name: String = "Burmese"

    override val nativeName: String = "မြန်မာ"
  }

  data object Cantonese : Language() {
    override val code: String = "yue"

    override val name: String = "Cantonese"

    override val nativeName: String = "粵語"
  }

  data object Catalan : Language() {
    override val code: String = "ca"

    override val name: String = "Catalan"

    override val nativeName: String = "català"
  }

  data object Cebuano : Language() {
    override val code: String = "ceb"

    override val name: String = "Cebuano"

    override val nativeName: String = "Cebuano"
  }

  data object CentralAtlasTamazight : Language() {
    override val code: String = "tzm"

    override val name: String = "Central Atlas Tamazight"

    override val nativeName: String = "Tamaziɣt n laṭlaṣ"
  }

  data object CentralKurdish : Language() {
    override val code: String = "ckb"

    override val name: String = "Central Kurdish"

    override val nativeName: String = "کوردیی ناوەندی"
  }

  data object Chakma : Language() {
    override val code: String = "ccp"

    override val name: String = "Chakma"

    override val nativeName: String = "𑄌𑄋𑄴𑄟𑄳𑄦"
  }

  data object Chechen : Language() {
    override val code: String = "ce"

    override val name: String = "Chechen"

    override val nativeName: String = "нохчийн"
  }

  data object Cherokee : Language() {
    override val code: String = "chr"

    override val name: String = "Cherokee"

    override val nativeName: String = "ᏣᎳᎩ"
  }

  data object Chiga : Language() {
    override val code: String = "cgg"

    override val name: String = "Chiga"

    override val nativeName: String = "Rukiga"
  }

  data object Chinese : Language() {
    override val code: String = "zh"

    override val name: String = "Chinese"

    override val nativeName: String = "中文"
  }

  data object ChurchSlavic : Language() {
    override val code: String = "cu"

    override val name: String = "Church Slavic"

    override val nativeName: String = "Church Slavic"
  }

  data object Colognian : Language() {
    override val code: String = "ksh"

    override val name: String = "Colognian"

    override val nativeName: String = "Kölsch"
  }

  data object Cornish : Language() {
    override val code: String = "kw"

    override val name: String = "Cornish"

    override val nativeName: String = "kernewek"
  }

  data object Croatian : Language() {
    override val code: String = "hr"

    override val name: String = "Croatian"

    override val nativeName: String = "hrvatski"
  }

  data object Czech : Language() {
    override val code: String = "cs"

    override val name: String = "Czech"

    override val nativeName: String = "čeština"
  }

  data object Danish : Language() {
    override val code: String = "da"

    override val name: String = "Danish"

    override val nativeName: String = "dansk"
  }

  data object Duala : Language() {
    override val code: String = "dua"

    override val name: String = "Duala"

    override val nativeName: String = "duálá"
  }

  data object Dutch : Language() {
    override val code: String = "nl"

    override val name: String = "Dutch"

    override val nativeName: String = "Nederlands"
  }

  data object Dzongkha : Language() {
    override val code: String = "dz"

    override val name: String = "Dzongkha"

    override val nativeName: String = "རྫོང་ཁ"
  }

  data object Embu : Language() {
    override val code: String = "ebu"

    override val name: String = "Embu"

    override val nativeName: String = "Kĩembu"
  }

  data object English : Language() {
    override val code: String = "en"

    override val name: String = "English"

    override val nativeName: String = "English"
  }

  data object Esperanto : Language() {
    override val code: String = "eo"

    override val name: String = "Esperanto"

    override val nativeName: String = "esperanto"
  }

  data object Estonian : Language() {
    override val code: String = "et"

    override val name: String = "Estonian"

    override val nativeName: String = "eesti"
  }

  data object Ewe : Language() {
    override val code: String = "ee"

    override val name: String = "Ewe"

    override val nativeName: String = "Eʋegbe"
  }

  data object Ewondo : Language() {
    override val code: String = "ewo"

    override val name: String = "Ewondo"

    override val nativeName: String = "ewondo"
  }

  data object Faroese : Language() {
    override val code: String = "fo"

    override val name: String = "Faroese"

    override val nativeName: String = "føroyskt"
  }

  data object Filipino : Language() {
    override val code: String = "fil"

    override val name: String = "Filipino"

    override val nativeName: String = "Filipino"
  }

  data object Finnish : Language() {
    override val code: String = "fi"

    override val name: String = "Finnish"

    override val nativeName: String = "suomi"
  }

  data object French : Language() {
    override val code: String = "fr"

    override val name: String = "French"

    override val nativeName: String = "français"
  }

  data object Friulian : Language() {
    override val code: String = "fur"

    override val name: String = "Friulian"

    override val nativeName: String = "furlan"
  }

  data object Fulah : Language() {
    override val code: String = "ff"

    override val name: String = "Fulah"

    override val nativeName: String = "Pulaar"
  }

  data object Galician : Language() {
    override val code: String = "gl"

    override val name: String = "Galician"

    override val nativeName: String = "galego"
  }

  data object Ganda : Language() {
    override val code: String = "lg"

    override val name: String = "Ganda"

    override val nativeName: String = "Luganda"
  }

  data object Georgian : Language() {
    override val code: String = "ka"

    override val name: String = "Georgian"

    override val nativeName: String = "ქართული"
  }

  data object German : Language() {
    override val code: String = "de"

    override val name: String = "German"

    override val nativeName: String = "Deutsch"
  }

  data object Greek : Language() {
    override val code: String = "el"

    override val name: String = "Greek"

    override val nativeName: String = "Ελληνικά"
  }

  data object Gujarati : Language() {
    override val code: String = "gu"

    override val name: String = "Gujarati"

    override val nativeName: String = "ગુજરાતી"
  }

  data object Gusii : Language() {
    override val code: String = "guz"

    override val name: String = "Gusii"

    override val nativeName: String = "Ekegusii"
  }

  data object Hausa : Language() {
    override val code: String = "ha"

    override val name: String = "Hausa"

    override val nativeName: String = "Hausa"
  }

  data object Hawaiian : Language() {
    override val code: String = "haw"

    override val name: String = "Hawaiian"

    override val nativeName: String = "ʻŌlelo Hawaiʻi"
  }

  data object Hebrew : Language() {
    override val code: String = "he"

    override val name: String = "Hebrew"

    override val nativeName: String = "עברית"
  }

  data object Hindi : Language() {
    override val code: String = "hi"

    override val name: String = "Hindi"

    override val nativeName: String = "हिन्दी"
  }

  data object Hungarian : Language() {
    override val code: String = "hu"

    override val name: String = "Hungarian"

    override val nativeName: String = "magyar"
  }

  data object Icelandic : Language() {
    override val code: String = "is"

    override val name: String = "Icelandic"

    override val nativeName: String = "íslenska"
  }

  data object Igbo : Language() {
    override val code: String = "ig"

    override val name: String = "Igbo"

    override val nativeName: String = "Igbo"
  }

  data object InariSami : Language() {
    override val code: String = "smn"

    override val name: String = "Inari Sami"

    override val nativeName: String = "anarâškielâ"
  }

  data object Indonesian : Language() {
    override val code: String = "id"

    override val name: String = "Indonesian"

    override val nativeName: String = "Bahasa Indonesia"
  }

  data object Interlingua : Language() {
    override val code: String = "ia"

    override val name: String = "Interlingua"

    override val nativeName: String = "Interlingua"
  }

  data object Irish : Language() {
    override val code: String = "ga"

    override val name: String = "Irish"

    override val nativeName: String = "Gaeilge"
  }

  data object Italian : Language() {
    override val code: String = "it"

    override val name: String = "Italian"

    override val nativeName: String = "italiano"
  }

  data object Japanese : Language() {
    override val code: String = "ja"

    override val name: String = "Japanese"

    override val nativeName: String = "日本語"
  }

  data object Javanese : Language() {
    override val code: String = "jv"

    override val name: String = "Javanese"

    override val nativeName: String = "Javanese"
  }

  data object JolaFonyi : Language() {
    override val code: String = "dyo"

    override val name: String = "Jola-Fonyi"

    override val nativeName: String = "joola"
  }

  data object Kabuverdianu : Language() {
    override val code: String = "kea"

    override val name: String = "Kabuverdianu"

    override val nativeName: String = "kabuverdianu"
  }

  data object Kabyle : Language() {
    override val code: String = "kab"

    override val name: String = "Kabyle"

    override val nativeName: String = "Taqbaylit"
  }

  data object Kako : Language() {
    override val code: String = "kkj"

    override val name: String = "Kako"

    override val nativeName: String = "kakɔ"
  }

  data object Kalaallisut : Language() {
    override val code: String = "kl"

    override val name: String = "Kalaallisut"

    override val nativeName: String = "kalaallisut"
  }

  data object Kalenjin : Language() {
    override val code: String = "kln"

    override val name: String = "Kalenjin"

    override val nativeName: String = "Kalenjin"
  }

  data object Kamba : Language() {
    override val code: String = "kam"

    override val name: String = "Kamba"

    override val nativeName: String = "Kikamba"
  }

  data object Kannada : Language() {
    override val code: String = "kn"

    override val name: String = "Kannada"

    override val nativeName: String = "ಕನ್ನಡ"
  }

  data object Kashmiri : Language() {
    override val code: String = "ks"

    override val name: String = "Kashmiri"

    override val nativeName: String = "کٲشُر"
  }

  data object Kazakh : Language() {
    override val code: String = "kk"

    override val name: String = "Kazakh"

    override val nativeName: String = "қазақ тілі"
  }

  data object Khmer : Language() {
    override val code: String = "km"

    override val name: String = "Khmer"

    override val nativeName: String = "ខ្មែរ"
  }

  data object Kikuyu : Language() {
    override val code: String = "ki"

    override val name: String = "Kikuyu"

    override val nativeName: String = "Gikuyu"
  }

  data object Kinyarwanda : Language() {
    override val code: String = "rw"

    override val name: String = "Kinyarwanda"

    override val nativeName: String = "Kinyarwanda"
  }

  data object Konkani : Language() {
    override val code: String = "kok"

    override val name: String = "Konkani"

    override val nativeName: String = "कोंकणी"
  }

  data object Korean : Language() {
    override val code: String = "ko"

    override val name: String = "Korean"

    override val nativeName: String = "한국어"
  }

  data object KoyraChiini : Language() {
    override val code: String = "khq"

    override val name: String = "Koyra Chiini"

    override val nativeName: String = "Koyra ciini"
  }

  data object KoyraboroSenni : Language() {
    override val code: String = "ses"

    override val name: String = "Koyraboro Senni"

    override val nativeName: String = "Koyraboro senni"
  }

  data object Kurdish : Language() {
    override val code: String = "ku"

    override val name: String = "Kurdish"

    override val nativeName: String = "Kurdish"
  }

  data object Kwasio : Language() {
    override val code: String = "nmg"

    override val name: String = "Kwasio"

    override val nativeName: String = "nmg"
  }

  data object Kyrgyz : Language() {
    override val code: String = "ky"

    override val name: String = "Kyrgyz"

    override val nativeName: String = "кыргызча"
  }

  data object Lakota : Language() {
    override val code: String = "lkt"

    override val name: String = "Lakota"

    override val nativeName: String = "Lakȟólʼiyapi"
  }

  data object Langi : Language() {
    override val code: String = "lag"

    override val name: String = "Langi"

    override val nativeName: String = "Kɨlaangi"
  }

  data object Lao : Language() {
    override val code: String = "lo"

    override val name: String = "Lao"

    override val nativeName: String = "ລາວ"
  }

  data object Latvian : Language() {
    override val code: String = "lv"

    override val name: String = "Latvian"

    override val nativeName: String = "latviešu"
  }

  data object Lingala : Language() {
    override val code: String = "ln"

    override val name: String = "Lingala"

    override val nativeName: String = "lingála"
  }

  data object Lithuanian : Language() {
    override val code: String = "lt"

    override val name: String = "Lithuanian"

    override val nativeName: String = "lietuvių"
  }

  data object LowGerman : Language() {
    override val code: String = "nds"

    override val name: String = "Low German"

    override val nativeName: String = "Low German"
  }

  data object LowerSorbian : Language() {
    override val code: String = "dsb"

    override val name: String = "Lower Sorbian"

    override val nativeName: String = "dolnoserbšćina"
  }

  data object LubaKatanga : Language() {
    override val code: String = "lu"

    override val name: String = "Luba-Katanga"

    override val nativeName: String = "Tshiluba"
  }

  data object Luo : Language() {
    override val code: String = "luo"

    override val name: String = "Luo"

    override val nativeName: String = "Dholuo"
  }

  data object Luxembourgish : Language() {
    override val code: String = "lb"

    override val name: String = "Luxembourgish"

    override val nativeName: String = "Lëtzebuergesch"
  }

  data object Luyia : Language() {
    override val code: String = "luy"

    override val name: String = "Luyia"

    override val nativeName: String = "Luluhia"
  }

  data object Macedonian : Language() {
    override val code: String = "mk"

    override val name: String = "Macedonian"

    override val nativeName: String = "македонски"
  }

  data object Machame : Language() {
    override val code: String = "jmc"

    override val name: String = "Machame"

    override val nativeName: String = "Kimachame"
  }

  data object Maithili : Language() {
    override val code: String = "mai"

    override val name: String = "Maithili"

    override val nativeName: String = "Maithili"
  }

  data object MakhuwaMeetto : Language() {
    override val code: String = "mgh"

    override val name: String = "Makhuwa-Meetto"

    override val nativeName: String = "Makua"
  }

  data object Makonde : Language() {
    override val code: String = "kde"

    override val name: String = "Makonde"

    override val nativeName: String = "Chimakonde"
  }

  data object Malagasy : Language() {
    override val code: String = "mg"

    override val name: String = "Malagasy"

    override val nativeName: String = "Malagasy"
  }

  data object Malay : Language() {
    override val code: String = "ms"

    override val name: String = "Malay"

    override val nativeName: String = "Melayu"
  }

  data object Malayalam : Language() {
    override val code: String = "ml"

    override val name: String = "Malayalam"

    override val nativeName: String = "മലയാളം"
  }

  data object Maltese : Language() {
    override val code: String = "mt"

    override val name: String = "Maltese"

    override val nativeName: String = "Malti"
  }

  data object Manipuri : Language() {
    override val code: String = "mni"

    override val name: String = "Manipuri"

    override val nativeName: String = "Manipuri"
  }

  data object Manx : Language() {
    override val code: String = "gv"

    override val name: String = "Manx"

    override val nativeName: String = "Gaelg"
  }

  data object Maori : Language() {
    override val code: String = "mi"

    override val name: String = "Maori"

    override val nativeName: String = "Maori"
  }

  data object Marathi : Language() {
    override val code: String = "mr"

    override val name: String = "Marathi"

    override val nativeName: String = "मराठी"
  }

  data object Masai : Language() {
    override val code: String = "mas"

    override val name: String = "Masai"

    override val nativeName: String = "Maa"
  }

  data object Mazanderani : Language() {
    override val code: String = "mzn"

    override val name: String = "Mazanderani"

    override val nativeName: String = "مازرونی"
  }

  data object Meru : Language() {
    override val code: String = "mer"

    override val name: String = "Meru"

    override val nativeName: String = "Kĩmĩrũ"
  }

  data object Meta : Language() {
    override val code: String = "mgo"

    override val name: String = "Metaʼ"

    override val nativeName: String = "metaʼ"
  }

  data object Mongolian : Language() {
    override val code: String = "mn"

    override val name: String = "Mongolian"

    override val nativeName: String = "монгол"
  }

  data object Morisyen : Language() {
    override val code: String = "mfe"

    override val name: String = "Morisyen"

    override val nativeName: String = "kreol morisien"
  }

  data object Mundang : Language() {
    override val code: String = "mua"

    override val name: String = "Mundang"

    override val nativeName: String = "MUNDAŊ"
  }

  data object Nama : Language() {
    override val code: String = "naq"

    override val name: String = "Nama"

    override val nativeName: String = "Khoekhoegowab"
  }

  data object Nepali : Language() {
    override val code: String = "ne"

    override val name: String = "Nepali"

    override val nativeName: String = "नेपाली"
  }

  data object Ngiemboon : Language() {
    override val code: String = "nnh"

    override val name: String = "Ngiemboon"

    override val nativeName: String = "Shwóŋò ngiembɔɔn"
  }

  data object Ngomba : Language() {
    override val code: String = "jgo"

    override val name: String = "Ngomba"

    override val nativeName: String = "Ndaꞌa"
  }

  data object NigerianPidgin : Language() {
    override val code: String = "pcm"

    override val name: String = "Nigerian Pidgin"

    override val nativeName: String = "pcm"
  }

  data object NorthNdebele : Language() {
    override val code: String = "nd"

    override val name: String = "North Ndebele"

    override val nativeName: String = "isiNdebele"
  }

  data object NorthernLuri : Language() {
    override val code: String = "lrc"

    override val name: String = "Northern Luri"

    override val nativeName: String = "لۊری شومالی"
  }

  data object NorthernSami : Language() {
    override val code: String = "se"

    override val name: String = "Northern Sami"

    override val nativeName: String = "davvisámegiella"
  }

  data object NorwegianBokmal : Language() {
    override val code: String = "nb"

    override val name: String = "Norwegian Bokmål"

    override val nativeName: String = "norsk bokmål"
  }

  data object NorwegianNynorsk : Language() {
    override val code: String = "nn"

    override val name: String = "Norwegian Nynorsk"

    override val nativeName: String = "nynorsk"
  }

  data object Nuer : Language() {
    override val code: String = "nus"

    override val name: String = "Nuer"

    override val nativeName: String = "Thok Nath"
  }

  data object Nyankole : Language() {
    override val code: String = "nyn"

    override val name: String = "Nyankole"

    override val nativeName: String = "Runyankore"
  }

  data object Odia : Language() {
    override val code: String = "or"

    override val name: String = "Odia"

    override val nativeName: String = "ଓଡ଼ିଆ"
  }

  data object Oromo : Language() {
    override val code: String = "om"

    override val name: String = "Oromo"

    override val nativeName: String = "Oromoo"
  }

  data object Ossetic : Language() {
    override val code: String = "os"

    override val name: String = "Ossetic"

    override val nativeName: String = "ирон"
  }

  data object Pashto : Language() {
    override val code: String = "ps"

    override val name: String = "Pashto"

    override val nativeName: String = "پښتو"
  }

  data object Persian : Language() {
    override val code: String = "fa"

    override val name: String = "Persian"

    override val nativeName: String = "فارسی"
  }

  data object Polish : Language() {
    override val code: String = "pl"

    override val name: String = "Polish"

    override val nativeName: String = "polski"
  }

  data object Portuguese : Language() {
    override val code: String = "pt"

    override val name: String = "Portuguese"

    override val nativeName: String = "português"
  }

  data object Prussian : Language() {
    override val code: String = "prg"

    override val name: String = "Prussian"

    override val nativeName: String = "prūsiskan"
  }

  data object Punjabi : Language() {
    override val code: String = "pa"

    override val name: String = "Punjabi"

    override val nativeName: String = "ਪੰਜਾਬੀ"
  }

  data object Quechua : Language() {
    override val code: String = "qu"

    override val name: String = "Quechua"

    override val nativeName: String = "Runasimi"
  }

  data object Romanian : Language() {
    override val code: String = "ro"

    override val name: String = "Romanian"

    override val nativeName: String = "română"
  }

  data object Romansh : Language() {
    override val code: String = "rm"

    override val name: String = "Romansh"

    override val nativeName: String = "rumantsch"
  }

  data object Rombo : Language() {
    override val code: String = "rof"

    override val name: String = "Rombo"

    override val nativeName: String = "Kihorombo"
  }

  data object Root : Language() {
    override val code: String = "root"

    override val name: String = "Root"

    override val nativeName: String = "root"
  }

  data object Rundi : Language() {
    override val code: String = "rn"

    override val name: String = "Rundi"

    override val nativeName: String = "Ikirundi"
  }

  data object Russian : Language() {
    override val code: String = "ru"

    override val name: String = "Russian"

    override val nativeName: String = "русский"
  }

  data object Rwa : Language() {
    override val code: String = "rwk"

    override val name: String = "Rwa"

    override val nativeName: String = "Kiruwa"
  }

  data object Sakha : Language() {
    override val code: String = "sah"

    override val name: String = "Sakha"

    override val nativeName: String = "саха тыла"
  }

  data object Samburu : Language() {
    override val code: String = "saq"

    override val name: String = "Samburu"

    override val nativeName: String = "Kisampur"
  }

  data object Sango : Language() {
    override val code: String = "sg"

    override val name: String = "Sango"

    override val nativeName: String = "Sängö"
  }

  data object Sangu : Language() {
    override val code: String = "sbp"

    override val name: String = "Sangu"

    override val nativeName: String = "Ishisangu"
  }

  data object Santali : Language() {
    override val code: String = "sat"

    override val name: String = "Santali"

    override val nativeName: String = "Santali"
  }

  data object ScottishGaelic : Language() {
    override val code: String = "gd"

    override val name: String = "Scottish Gaelic"

    override val nativeName: String = "Gàidhlig"
  }

  data object Sena : Language() {
    override val code: String = "seh"

    override val name: String = "Sena"

    override val nativeName: String = "sena"
  }

  data object Serbian : Language() {
    override val code: String = "sr"

    override val name: String = "Serbian"

    override val nativeName: String = "српски"
  }

  data object Shambala : Language() {
    override val code: String = "ksb"

    override val name: String = "Shambala"

    override val nativeName: String = "Kishambaa"
  }

  data object Shona : Language() {
    override val code: String = "sn"

    override val name: String = "Shona"

    override val nativeName: String = "chiShona"
  }

  data object SichuanYi : Language() {
    override val code: String = "ii"

    override val name: String = "Sichuan Yi"

    override val nativeName: String = "ꆈꌠꉙ"
  }

  data object Sindhi : Language() {
    override val code: String = "sd"

    override val name: String = "Sindhi"

    override val nativeName: String = "سنڌي"
  }

  data object Sinhala : Language() {
    override val code: String = "si"

    override val name: String = "Sinhala"

    override val nativeName: String = "සිංහල"
  }

  data object Slovak : Language() {
    override val code: String = "sk"

    override val name: String = "Slovak"

    override val nativeName: String = "slovenčina"
  }

  data object Slovenian : Language() {
    override val code: String = "sl"

    override val name: String = "Slovenian"

    override val nativeName: String = "slovenščina"
  }

  data object Soga : Language() {
    override val code: String = "xog"

    override val name: String = "Soga"

    override val nativeName: String = "Olusoga"
  }

  data object Somali : Language() {
    override val code: String = "so"

    override val name: String = "Somali"

    override val nativeName: String = "Soomaali"
  }

  data object Spanish : Language() {
    override val code: String = "es"

    override val name: String = "Spanish"

    override val nativeName: String = "español"
  }

  data object StandardMoroccanTamazight : Language() {
    override val code: String = "zgh"

    override val name: String = "Standard Moroccan Tamazight"

    override val nativeName: String = "ⵜⴰⵎⴰⵣⵉⵖⵜ"
  }

  data object Sundanese : Language() {
    override val code: String = "su"

    override val name: String = "Sundanese"

    override val nativeName: String = "Sundanese"
  }

  data object Swahili : Language() {
    override val code: String = "sw"

    override val name: String = "Swahili"

    override val nativeName: String = "Kiswahili"
  }

  data object Swedish : Language() {
    override val code: String = "sv"

    override val name: String = "Swedish"

    override val nativeName: String = "svenska"
  }

  data object SwissGerman : Language() {
    override val code: String = "gsw"

    override val name: String = "Swiss German"

    override val nativeName: String = "Schwiizertüütsch"
  }

  data object Tachelhit : Language() {
    override val code: String = "shi"

    override val name: String = "Tachelhit"

    override val nativeName: String = "ⵜⴰⵛⵍⵃⵉⵜ"
  }

  data object Taita : Language() {
    override val code: String = "dav"

    override val name: String = "Taita"

    override val nativeName: String = "Kitaita"
  }

  data object Tajik : Language() {
    override val code: String = "tg"

    override val name: String = "Tajik"

    override val nativeName: String = "тоҷикӣ"
  }

  data object Tamil : Language() {
    override val code: String = "ta"

    override val name: String = "Tamil"

    override val nativeName: String = "தமிழ்"
  }

  data object Tasawaq : Language() {
    override val code: String = "twq"

    override val name: String = "Tasawaq"

    override val nativeName: String = "Tasawaq senni"
  }

  data object Tatar : Language() {
    override val code: String = "tt"

    override val name: String = "Tatar"

    override val nativeName: String = "татар"
  }

  data object Telugu : Language() {
    override val code: String = "te"

    override val name: String = "Telugu"

    override val nativeName: String = "తెలుగు"
  }

  data object Teso : Language() {
    override val code: String = "teo"

    override val name: String = "Teso"

    override val nativeName: String = "Kiteso"
  }

  data object Thai : Language() {
    override val code: String = "th"

    override val name: String = "Thai"

    override val nativeName: String = "ไทย"
  }

  data object Tibetan : Language() {
    override val code: String = "bo"

    override val name: String = "Tibetan"

    override val nativeName: String = "བོད་སྐད་"
  }

  data object Tigrinya : Language() {
    override val code: String = "ti"

    override val name: String = "Tigrinya"

    override val nativeName: String = "ትግርኛ"
  }

  data object Tongan : Language() {
    override val code: String = "to"

    override val name: String = "Tongan"

    override val nativeName: String = "lea fakatonga"
  }

  data object Turkish : Language() {
    override val code: String = "tr"

    override val name: String = "Turkish"

    override val nativeName: String = "Türkçe"
  }

  data object Turkmen : Language() {
    override val code: String = "tk"

    override val name: String = "Turkmen"

    override val nativeName: String = "türkmen dili"
  }

  data object Ukrainian : Language() {
    override val code: String = "uk"

    override val name: String = "Ukrainian"

    override val nativeName: String = "українська"
  }

  data object UpperSorbian : Language() {
    override val code: String = "hsb"

    override val name: String = "Upper Sorbian"

    override val nativeName: String = "hornjoserbšćina"
  }

  data object Urdu : Language() {
    override val code: String = "ur"

    override val name: String = "Urdu"

    override val nativeName: String = "اردو"
  }

  data object Uyghur : Language() {
    override val code: String = "ug"

    override val name: String = "Uyghur"

    override val nativeName: String = "ئۇيغۇرچە"
  }

  data object Uzbek : Language() {
    override val code: String = "uz"

    override val name: String = "Uzbek"

    override val nativeName: String = "o‘zbek"
  }

  data object Vai : Language() {
    override val code: String = "vai"

    override val name: String = "Vai"

    override val nativeName: String = "ꕙꔤ"
  }

  data object Vietnamese : Language() {
    override val code: String = "vi"

    override val name: String = "Vietnamese"

    override val nativeName: String = "Tiếng Việt"
  }

  data object Volapuk : Language() {
    override val code: String = "vo"

    override val name: String = "Volapük"

    override val nativeName: String = "Volapük"
  }

  data object Vunjo : Language() {
    override val code: String = "vun"

    override val name: String = "Vunjo"

    override val nativeName: String = "Kyivunjo"
  }

  data object Walser : Language() {
    override val code: String = "wae"

    override val name: String = "Walser"

    override val nativeName: String = "Walser"
  }

  data object Welsh : Language() {
    override val code: String = "cy"

    override val name: String = "Welsh"

    override val nativeName: String = "Cymraeg"
  }

  data object WesternFrisian : Language() {
    override val code: String = "fy"

    override val name: String = "Western Frisian"

    override val nativeName: String = "Frysk"
  }

  data object Wolof : Language() {
    override val code: String = "wo"

    override val name: String = "Wolof"

    override val nativeName: String = "Wolof"
  }

  data object Xhosa : Language() {
    override val code: String = "xh"

    override val name: String = "Xhosa"

    override val nativeName: String = "Xhosa"
  }

  data object Yangben : Language() {
    override val code: String = "yav"

    override val name: String = "Yangben"

    override val nativeName: String = "nuasue"
  }

  data object Yiddish : Language() {
    override val code: String = "yi"

    override val name: String = "Yiddish"

    override val nativeName: String = "Yiddish"
  }

  data object Yoruba : Language() {
    override val code: String = "yo"

    override val name: String = "Yoruba"

    override val nativeName: String = "Èdè Yorùbá"
  }

  data object Zarma : Language() {
    override val code: String = "dje"

    override val name: String = "Zarma"

    override val nativeName: String = "Zarmaciine"
  }

  data object Zulu : Language() {
    override val code: String = "zu"

    override val name: String = "Zulu"

    override val nativeName: String = "isiZulu"
  }
}
