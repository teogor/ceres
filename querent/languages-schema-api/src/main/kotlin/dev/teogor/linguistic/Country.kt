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

sealed class Country : Region {
  abstract val code: String

  abstract val name: String

  abstract val nativeName: String

  data object Afghanistan : Country() {
    override val code: String = "AF"

    override val name: String = "Afghanistan"

    override val nativeName: String = "افغانستان"
  }

  data object AlandIslands : Country() {
    override val code: String = "AX"

    override val name: String = "Åland Islands"

    override val nativeName: String = "Åland"
  }

  data object Albania : Country() {
    override val code: String = "AL"

    override val name: String = "Albania"

    override val nativeName: String = "Shqipëri"
  }

  data object Algeria : Country() {
    override val code: String = "DZ"

    override val name: String = "Algeria"

    override val nativeName: String = "الجزائر"
  }

  data object AmericanSamoa : Country() {
    override val code: String = "AS"

    override val name: String = "American Samoa"

    override val nativeName: String = "American Samoa"
  }

  data object Andorra : Country() {
    override val code: String = "AD"

    override val name: String = "Andorra"

    override val nativeName: String = "Andorra"
  }

  data object Angola : Country() {
    override val code: String = "AO"

    override val name: String = "Angola"

    override val nativeName: String = "Angóla"
  }

  data object Anguilla : Country() {
    override val code: String = "AI"

    override val name: String = "Anguilla"

    override val nativeName: String = "Anguilla"
  }

  data object AntiguaBarbuda : Country() {
    override val code: String = "AG"

    override val name: String = "Antigua & Barbuda"

    override val nativeName: String = "Antigua & Barbuda"
  }

  data object Argentina : Country() {
    override val code: String = "AR"

    override val name: String = "Argentina"

    override val nativeName: String = "Argentina"
  }

  data object Armenia : Country() {
    override val code: String = "AM"

    override val name: String = "Armenia"

    override val nativeName: String = "Հայաստան"
  }

  data object Aruba : Country() {
    override val code: String = "AW"

    override val name: String = "Aruba"

    override val nativeName: String = "Aruba"
  }

  data object Australia : Country() {
    override val code: String = "AU"

    override val name: String = "Australia"

    override val nativeName: String = "Australia"
  }

  data object Austria : Country() {
    override val code: String = "AT"

    override val name: String = "Austria"

    override val nativeName: String = "Österreich"
  }

  data object Azerbaijan : Country() {
    override val code: String = "AZ"

    override val name: String = "Azerbaijan"

    override val nativeName: String = "Azərbaycan"
  }

  data object Bahamas : Country() {
    override val code: String = "BS"

    override val name: String = "Bahamas"

    override val nativeName: String = "Bahamas"
  }

  data object Bahrain : Country() {
    override val code: String = "BH"

    override val name: String = "Bahrain"

    override val nativeName: String = "البحرين"
  }

  data object Bangladesh : Country() {
    override val code: String = "BD"

    override val name: String = "Bangladesh"

    override val nativeName: String = "বাংলাদেশ"
  }

  data object Barbados : Country() {
    override val code: String = "BB"

    override val name: String = "Barbados"

    override val nativeName: String = "Barbados"
  }

  data object Belarus : Country() {
    override val code: String = "BY"

    override val name: String = "Belarus"

    override val nativeName: String = "Беларусь"
  }

  data object Belgium : Country() {
    override val code: String = "BE"

    override val name: String = "Belgium"

    override val nativeName: String = "Belgien"
  }

  data object Belize : Country() {
    override val code: String = "BZ"

    override val name: String = "Belize"

    override val nativeName: String = "Belize"
  }

  data object Benin : Country() {
    override val code: String = "BJ"

    override val name: String = "Benin"

    override val nativeName: String = "Bénin"
  }

  data object Bermuda : Country() {
    override val code: String = "BM"

    override val name: String = "Bermuda"

    override val nativeName: String = "Bermuda"
  }

  data object Bhutan : Country() {
    override val code: String = "BT"

    override val name: String = "Bhutan"

    override val nativeName: String = "འབྲུག"
  }

  data object Bolivia : Country() {
    override val code: String = "BO"

    override val name: String = "Bolivia"

    override val nativeName: String = "Bolivia"
  }

  data object BosniaHerzegovina : Country() {
    override val code: String = "BA"

    override val name: String = "Bosnia & Herzegovina"

    override val nativeName: String = "Bosna i Hercegovina"
  }

  data object Botswana : Country() {
    override val code: String = "BW"

    override val name: String = "Botswana"

    override val nativeName: String = "Botswana"
  }

  data object Brazil : Country() {
    override val code: String = "BR"

    override val name: String = "Brazil"

    override val nativeName: String = "Brasil"
  }

  data object BritishIndianOceanTerritory : Country() {
    override val code: String = "IO"

    override val name: String = "British Indian Ocean Territory"

    override val nativeName: String = "British Indian Ocean Territory"
  }

  data object BritishVirginIslands : Country() {
    override val code: String = "VG"

    override val name: String = "British Virgin Islands"

    override val nativeName: String = "British Virgin Islands"
  }

  data object Brunei : Country() {
    override val code: String = "BN"

    override val name: String = "Brunei"

    override val nativeName: String = "Brunei"
  }

  data object Bulgaria : Country() {
    override val code: String = "BG"

    override val name: String = "Bulgaria"

    override val nativeName: String = "България"
  }

  data object BurkinaFaso : Country() {
    override val code: String = "BF"

    override val name: String = "Burkina Faso"

    override val nativeName: String = "Burkibaa Faaso"
  }

  data object Burundi : Country() {
    override val code: String = "BI"

    override val name: String = "Burundi"

    override val nativeName: String = "Burundi"
  }

  data object Cambodia : Country() {
    override val code: String = "KH"

    override val name: String = "Cambodia"

    override val nativeName: String = "កម្ពុជា"
  }

  data object Cameroon : Country() {
    override val code: String = "CM"

    override val name: String = "Cameroon"

    override val nativeName: String = "Kàmàlûŋ"
  }

  data object Canada : Country() {
    override val code: String = "CA"

    override val name: String = "Canada"

    override val nativeName: String = "Canada"
  }

  data object CanaryIslands : Country() {
    override val code: String = "IC"

    override val name: String = "Canary Islands"

    override val nativeName: String = "Canarias"
  }

  data object CapeVerde : Country() {
    override val code: String = "CV"

    override val name: String = "Cape Verde"

    override val nativeName: String = "Kabu Verdi"
  }

  data object CaribbeanNetherlands : Country() {
    override val code: String = "BQ"

    override val name: String = "Caribbean Netherlands"

    override val nativeName: String = "Caribisch Nederland"
  }

  data object CaymanIslands : Country() {
    override val code: String = "KY"

    override val name: String = "Cayman Islands"

    override val nativeName: String = "Cayman Islands"
  }

  data object CentralAfricanRepublic : Country() {
    override val code: String = "CF"

    override val name: String = "Central African Republic"

    override val nativeName: String = "République centrafricaine"
  }

  data object CeutaMelilla : Country() {
    override val code: String = "EA"

    override val name: String = "Ceuta & Melilla"

    override val nativeName: String = "Ceuta y Melilla"
  }

  data object Chad : Country() {
    override val code: String = "TD"

    override val name: String = "Chad"

    override val nativeName: String = "تشاد"
  }

  data object Chile : Country() {
    override val code: String = "CL"

    override val name: String = "Chile"

    override val nativeName: String = "Chile"
  }

  data object China : Country() {
    override val code: String = "CN"

    override val name: String = "China"

    override val nativeName: String = "རྒྱ་ནག"
  }

  data object ChristmasIsland : Country() {
    override val code: String = "CX"

    override val name: String = "Christmas Island"

    override val nativeName: String = "Christmas Island"
  }

  data object CocosKeelingIslands : Country() {
    override val code: String = "CC"

    override val name: String = "Cocos (Keeling) Islands"

    override val nativeName: String = "Cocos (Keeling) Islands"
  }

  data object Colombia : Country() {
    override val code: String = "CO"

    override val name: String = "Colombia"

    override val nativeName: String = "Colombia"
  }

  data object Comoros : Country() {
    override val code: String = "KM"

    override val name: String = "Comoros"

    override val nativeName: String = "جزر القمر"
  }

  data object CongoBrazzaville : Country() {
    override val code: String = "CG"

    override val name: String = "Congo - Brazzaville"

    override val nativeName: String = "Congo-Brazzaville"
  }

  data object CongoKinshasa : Country() {
    override val code: String = "CD"

    override val name: String = "Congo - Kinshasa"

    override val nativeName: String = "Congo-Kinshasa"
  }

  data object CookIslands : Country() {
    override val code: String = "CK"

    override val name: String = "Cook Islands"

    override val nativeName: String = "Cook Islands"
  }

  data object CostaRica : Country() {
    override val code: String = "CR"

    override val name: String = "Costa Rica"

    override val nativeName: String = "Costa Rica"
  }

  data object CotedIvoire : Country() {
    override val code: String = "CI"

    override val name: String = "Côte d’Ivoire"

    override val nativeName: String = "Côte d’Ivoire"
  }

  data object Croatia : Country() {
    override val code: String = "HR"

    override val name: String = "Croatia"

    override val nativeName: String = "Hrvatska"
  }

  data object Cuba : Country() {
    override val code: String = "CU"

    override val name: String = "Cuba"

    override val nativeName: String = "Cuba"
  }

  data object Curacao : Country() {
    override val code: String = "CW"

    override val name: String = "Curaçao"

    override val nativeName: String = "Curaçao"
  }

  data object Cyprus : Country() {
    override val code: String = "CY"

    override val name: String = "Cyprus"

    override val nativeName: String = "Κύπρος"
  }

  data object Czechia : Country() {
    override val code: String = "CZ"

    override val name: String = "Czechia"

    override val nativeName: String = "Česko"
  }

  data object Denmark : Country() {
    override val code: String = "DK"

    override val name: String = "Denmark"

    override val nativeName: String = "Danmark"
  }

  data object DiegoGarcia : Country() {
    override val code: String = "DG"

    override val name: String = "Diego Garcia"

    override val nativeName: String = "Diego Garcia"
  }

  data object Djibouti : Country() {
    override val code: String = "DJ"

    override val name: String = "Djibouti"

    override val nativeName: String = "جيبوتي"
  }

  data object Dominica : Country() {
    override val code: String = "DM"

    override val name: String = "Dominica"

    override val nativeName: String = "Dominica"
  }

  data object DominicanRepublic : Country() {
    override val code: String = "DO"

    override val name: String = "Dominican Republic"

    override val nativeName: String = "República Dominicana"
  }

  data object Ecuador : Country() {
    override val code: String = "EC"

    override val name: String = "Ecuador"

    override val nativeName: String = "Ecuador"
  }

  data object Egypt : Country() {
    override val code: String = "EG"

    override val name: String = "Egypt"

    override val nativeName: String = "مصر"
  }

  data object ElSalvador : Country() {
    override val code: String = "SV"

    override val name: String = "El Salvador"

    override val nativeName: String = "El Salvador"
  }

  data object EquatorialGuinea : Country() {
    override val code: String = "GQ"

    override val name: String = "Equatorial Guinea"

    override val nativeName: String = "Guinea Ecuatorial"
  }

  data object Eritrea : Country() {
    override val code: String = "ER"

    override val name: String = "Eritrea"

    override val nativeName: String = "إريتريا"
  }

  data object Estonia : Country() {
    override val code: String = "EE"

    override val name: String = "Estonia"

    override val nativeName: String = "Eesti"
  }

  data object Ethiopia : Country() {
    override val code: String = "ET"

    override val name: String = "Ethiopia"

    override val nativeName: String = "ኢትዮጵያ"
  }

  data object Europe : Country() {
    override val code: String = "150"

    override val name: String = "Europe"

    override val nativeName: String = "Europe"
  }

  data object FalklandIslands : Country() {
    override val code: String = "FK"

    override val name: String = "Falkland Islands"

    override val nativeName: String = "Falkland Islands"
  }

  data object FaroeIslands : Country() {
    override val code: String = "FO"

    override val name: String = "Faroe Islands"

    override val nativeName: String = "Føroyar"
  }

  data object Fiji : Country() {
    override val code: String = "FJ"

    override val name: String = "Fiji"

    override val nativeName: String = "Fiji"
  }

  data object Finland : Country() {
    override val code: String = "FI"

    override val name: String = "Finland"

    override val nativeName: String = "Finland"
  }

  data object France : Country() {
    override val code: String = "FR"

    override val name: String = "France"

    override val nativeName: String = "Frañs"
  }

  data object FrenchGuiana : Country() {
    override val code: String = "GF"

    override val name: String = "French Guiana"

    override val nativeName: String = "Guyane française"
  }

  data object FrenchPolynesia : Country() {
    override val code: String = "PF"

    override val name: String = "French Polynesia"

    override val nativeName: String = "Polynésie française"
  }

  data object Gabon : Country() {
    override val code: String = "GA"

    override val name: String = "Gabon"

    override val nativeName: String = "Gabon"
  }

  data object Gambia : Country() {
    override val code: String = "GM"

    override val name: String = "Gambia"

    override val nativeName: String = "Gambia"
  }

  data object Georgia : Country() {
    override val code: String = "GE"

    override val name: String = "Georgia"

    override val nativeName: String = "საქართველო"
  }

  data object Germany : Country() {
    override val code: String = "DE"

    override val name: String = "Germany"

    override val nativeName: String = "Deutschland"
  }

  data object Ghana : Country() {
    override val code: String = "GH"

    override val name: String = "Ghana"

    override val nativeName: String = "Gaana"
  }

  data object Gibraltar : Country() {
    override val code: String = "GI"

    override val name: String = "Gibraltar"

    override val nativeName: String = "Gibraltar"
  }

  data object Greece : Country() {
    override val code: String = "GR"

    override val name: String = "Greece"

    override val nativeName: String = "Ελλάδα"
  }

  data object Greenland : Country() {
    override val code: String = "GL"

    override val name: String = "Greenland"

    override val nativeName: String = "Grønland"
  }

  data object Grenada : Country() {
    override val code: String = "GD"

    override val name: String = "Grenada"

    override val nativeName: String = "Grenada"
  }

  data object Guadeloupe : Country() {
    override val code: String = "GP"

    override val name: String = "Guadeloupe"

    override val nativeName: String = "Guadeloupe"
  }

  data object Guam : Country() {
    override val code: String = "GU"

    override val name: String = "Guam"

    override val nativeName: String = "Guam"
  }

  data object Guatemala : Country() {
    override val code: String = "GT"

    override val name: String = "Guatemala"

    override val nativeName: String = "Guatemala"
  }

  data object Guernsey : Country() {
    override val code: String = "GG"

    override val name: String = "Guernsey"

    override val nativeName: String = "Guernsey"
  }

  data object Guinea : Country() {
    override val code: String = "GN"

    override val name: String = "Guinea"

    override val nativeName: String = "Gine"
  }

  data object GuineaBissau : Country() {
    override val code: String = "GW"

    override val name: String = "Guinea-Bissau"

    override val nativeName: String = "Gine-Bisaawo"
  }

  data object Guyana : Country() {
    override val code: String = "GY"

    override val name: String = "Guyana"

    override val nativeName: String = "Guyana"
  }

  data object Haiti : Country() {
    override val code: String = "HT"

    override val name: String = "Haiti"

    override val nativeName: String = "Haïti"
  }

  data object Honduras : Country() {
    override val code: String = "HN"

    override val name: String = "Honduras"

    override val nativeName: String = "Honduras"
  }

  data object HongKongSARChina : Country() {
    override val code: String = "HK"

    override val name: String = "Hong Kong SAR China"

    override val nativeName: String = "Hong Kong SAR China"
  }

  data object Hungary : Country() {
    override val code: String = "HU"

    override val name: String = "Hungary"

    override val nativeName: String = "Magyarország"
  }

  data object Iceland : Country() {
    override val code: String = "IS"

    override val name: String = "Iceland"

    override val nativeName: String = "Ísland"
  }

  data object India : Country() {
    override val code: String = "IN"

    override val name: String = "India"

    override val nativeName: String = "ভাৰত"
  }

  data object Indonesia : Country() {
    override val code: String = "ID"

    override val name: String = "Indonesia"

    override val nativeName: String = "Indonesia"
  }

  data object Iran : Country() {
    override val code: String = "IR"

    override val name: String = "Iran"

    override val nativeName: String = "ئێران"
  }

  data object Iraq : Country() {
    override val code: String = "IQ"

    override val name: String = "Iraq"

    override val nativeName: String = "العراق"
  }

  data object Ireland : Country() {
    override val code: String = "IE"

    override val name: String = "Ireland"

    override val nativeName: String = "Ireland"
  }

  data object IsleofMan : Country() {
    override val code: String = "IM"

    override val name: String = "Isle of Man"

    override val nativeName: String = "Isle of Man"
  }

  data object Israel : Country() {
    override val code: String = "IL"

    override val name: String = "Israel"

    override val nativeName: String = "إسرائيل"
  }

  data object Italy : Country() {
    override val code: String = "IT"

    override val name: String = "Italy"

    override val nativeName: String = "Itàlia"
  }

  data object Jamaica : Country() {
    override val code: String = "JM"

    override val name: String = "Jamaica"

    override val nativeName: String = "Jamaica"
  }

  data object Japan : Country() {
    override val code: String = "JP"

    override val name: String = "Japan"

    override val nativeName: String = "日本"
  }

  data object Jersey : Country() {
    override val code: String = "JE"

    override val name: String = "Jersey"

    override val nativeName: String = "Jersey"
  }

  data object Jordan : Country() {
    override val code: String = "JO"

    override val name: String = "Jordan"

    override val nativeName: String = "الأردن"
  }

  data object Kazakhstan : Country() {
    override val code: String = "KZ"

    override val name: String = "Kazakhstan"

    override val nativeName: String = "Қазақстан"
  }

  data object Kenya : Country() {
    override val code: String = "KE"

    override val name: String = "Kenya"

    override val nativeName: String = "Kenya"
  }

  data object Kiribati : Country() {
    override val code: String = "KI"

    override val name: String = "Kiribati"

    override val nativeName: String = "Kiribati"
  }

  data object Kosovo : Country() {
    override val code: String = "XK"

    override val name: String = "Kosovo"

    override val nativeName: String = "Kosovë"
  }

  data object Kuwait : Country() {
    override val code: String = "KW"

    override val name: String = "Kuwait"

    override val nativeName: String = "الكويت"
  }

  data object Kyrgyzstan : Country() {
    override val code: String = "KG"

    override val name: String = "Kyrgyzstan"

    override val nativeName: String = "Кыргызстан"
  }

  data object Laos : Country() {
    override val code: String = "LA"

    override val name: String = "Laos"

    override val nativeName: String = "ລາວ"
  }

  data object LatinAmerica : Country() {
    override val code: String = "419"

    override val name: String = "Latin America"

    override val nativeName: String = "Latinoamérica"
  }

  data object Latvia : Country() {
    override val code: String = "LV"

    override val name: String = "Latvia"

    override val nativeName: String = "Latvija"
  }

  data object Lebanon : Country() {
    override val code: String = "LB"

    override val name: String = "Lebanon"

    override val nativeName: String = "لبنان"
  }

  data object Lesotho : Country() {
    override val code: String = "LS"

    override val name: String = "Lesotho"

    override val nativeName: String = "Lesotho"
  }

  data object Liberia : Country() {
    override val code: String = "LR"

    override val name: String = "Liberia"

    override val nativeName: String = "Liberia"
  }

  data object Libya : Country() {
    override val code: String = "LY"

    override val name: String = "Libya"

    override val nativeName: String = "ليبيا"
  }

  data object Liechtenstein : Country() {
    override val code: String = "LI"

    override val name: String = "Liechtenstein"

    override val nativeName: String = "Liechtenstein"
  }

  data object Lithuania : Country() {
    override val code: String = "LT"

    override val name: String = "Lithuania"

    override val nativeName: String = "Lietuva"
  }

  data object Luxembourg : Country() {
    override val code: String = "LU"

    override val name: String = "Luxembourg"

    override val nativeName: String = "Luxemburg"
  }

  data object MacauSARChina : Country() {
    override val code: String = "MO"

    override val name: String = "Macau SAR China"

    override val nativeName: String = "Macau SAR China"
  }

  data object Macedonia : Country() {
    override val code: String = "MK"

    override val name: String = "Macedonia"

    override val nativeName: String = "Македонија"
  }

  data object Madagascar : Country() {
    override val code: String = "MG"

    override val name: String = "Madagascar"

    override val nativeName: String = "Madagascar"
  }

  data object Malawi : Country() {
    override val code: String = "MW"

    override val name: String = "Malawi"

    override val nativeName: String = "Malawi"
  }

  data object Malaysia : Country() {
    override val code: String = "MY"

    override val name: String = "Malaysia"

    override val nativeName: String = "Malaysia"
  }

  data object Mali : Country() {
    override val code: String = "ML"

    override val name: String = "Mali"

    override val nativeName: String = "Mali"
  }

  data object Malta : Country() {
    override val code: String = "MT"

    override val name: String = "Malta"

    override val nativeName: String = "Malta"
  }

  data object MarshallIslands : Country() {
    override val code: String = "MH"

    override val name: String = "Marshall Islands"

    override val nativeName: String = "Marshall Islands"
  }

  data object Martinique : Country() {
    override val code: String = "MQ"

    override val name: String = "Martinique"

    override val nativeName: String = "Martinique"
  }

  data object Mauritania : Country() {
    override val code: String = "MR"

    override val name: String = "Mauritania"

    override val nativeName: String = "موريتانيا"
  }

  data object Mauritius : Country() {
    override val code: String = "MU"

    override val name: String = "Mauritius"

    override val nativeName: String = "Mauritius"
  }

  data object Mayotte : Country() {
    override val code: String = "YT"

    override val name: String = "Mayotte"

    override val nativeName: String = "Mayotte"
  }

  data object Mexico : Country() {
    override val code: String = "MX"

    override val name: String = "Mexico"

    override val nativeName: String = "México"
  }

  data object Micronesia : Country() {
    override val code: String = "FM"

    override val name: String = "Micronesia"

    override val nativeName: String = "Micronesia"
  }

  data object Moldova : Country() {
    override val code: String = "MD"

    override val name: String = "Moldova"

    override val nativeName: String = "Republica Moldova"
  }

  data object Monaco : Country() {
    override val code: String = "MC"

    override val name: String = "Monaco"

    override val nativeName: String = "Monaco"
  }

  data object Mongolia : Country() {
    override val code: String = "MN"

    override val name: String = "Mongolia"

    override val nativeName: String = "Монгол"
  }

  data object Montenegro : Country() {
    override val code: String = "ME"

    override val name: String = "Montenegro"

    override val nativeName: String = "Crna Gora"
  }

  data object Montserrat : Country() {
    override val code: String = "MS"

    override val name: String = "Montserrat"

    override val nativeName: String = "Montserrat"
  }

  data object Morocco : Country() {
    override val code: String = "MA"

    override val name: String = "Morocco"

    override val nativeName: String = "المغرب"
  }

  data object Mozambique : Country() {
    override val code: String = "MZ"

    override val name: String = "Mozambique"

    override val nativeName: String = "Umozambiki"
  }

  data object MyanmarBurma : Country() {
    override val code: String = "MM"

    override val name: String = "Myanmar (Burma)"

    override val nativeName: String = "မြန်မာ"
  }

  data object Namibia : Country() {
    override val code: String = "NA"

    override val name: String = "Namibia"

    override val nativeName: String = "Namibië"
  }

  data object Nauru : Country() {
    override val code: String = "NR"

    override val name: String = "Nauru"

    override val nativeName: String = "Nauru"
  }

  data object Nepal : Country() {
    override val code: String = "NP"

    override val name: String = "Nepal"

    override val nativeName: String = "नेपाल"
  }

  data object Netherlands : Country() {
    override val code: String = "NL"

    override val name: String = "Netherlands"

    override val nativeName: String = "Netherlands"
  }

  data object NewCaledonia : Country() {
    override val code: String = "NC"

    override val name: String = "New Caledonia"

    override val nativeName: String = "Nouvelle-Calédonie"
  }

  data object NewZealand : Country() {
    override val code: String = "NZ"

    override val name: String = "New Zealand"

    override val nativeName: String = "New Zealand"
  }

  data object Nicaragua : Country() {
    override val code: String = "NI"

    override val name: String = "Nicaragua"

    override val nativeName: String = "Nicaragua"
  }

  data object Niger : Country() {
    override val code: String = "NE"

    override val name: String = "Niger"

    override val nativeName: String = "Nižer"
  }

  data object Nigeria : Country() {
    override val code: String = "NG"

    override val name: String = "Nigeria"

    override val nativeName: String = "Nigeria"
  }

  data object Niue : Country() {
    override val code: String = "NU"

    override val name: String = "Niue"

    override val nativeName: String = "Niue"
  }

  data object NorfolkIsland : Country() {
    override val code: String = "NF"

    override val name: String = "Norfolk Island"

    override val nativeName: String = "Norfolk Island"
  }

  data object NorthKorea : Country() {
    override val code: String = "KP"

    override val name: String = "North Korea"

    override val nativeName: String = "조선민주주의인민공화국"
  }

  data object NorthernMarianaIslands : Country() {
    override val code: String = "MP"

    override val name: String = "Northern Mariana Islands"

    override val nativeName: String = "Northern Mariana Islands"
  }

  data object Norway : Country() {
    override val code: String = "NO"

    override val name: String = "Norway"

    override val nativeName: String = "Norge"
  }

  data object Oman : Country() {
    override val code: String = "OM"

    override val name: String = "Oman"

    override val nativeName: String = "عُمان"
  }

  data object Pakistan : Country() {
    override val code: String = "PK"

    override val name: String = "Pakistan"

    override val nativeName: String = "Pakistan"
  }

  data object Palau : Country() {
    override val code: String = "PW"

    override val name: String = "Palau"

    override val nativeName: String = "Palau"
  }

  data object PalestinianTerritories : Country() {
    override val code: String = "PS"

    override val name: String = "Palestinian Territories"

    override val nativeName: String = "الأراضي الفلسطينية"
  }

  data object Panama : Country() {
    override val code: String = "PA"

    override val name: String = "Panama"

    override val nativeName: String = "Panamá"
  }

  data object PapuaNewGuinea : Country() {
    override val code: String = "PG"

    override val name: String = "Papua New Guinea"

    override val nativeName: String = "Papua New Guinea"
  }

  data object Paraguay : Country() {
    override val code: String = "PY"

    override val name: String = "Paraguay"

    override val nativeName: String = "Paraguay"
  }

  data object Peru : Country() {
    override val code: String = "PE"

    override val name: String = "Peru"

    override val nativeName: String = "Perú"
  }

  data object Philippines : Country() {
    override val code: String = "PH"

    override val name: String = "Philippines"

    override val nativeName: String = "Philippines"
  }

  data object PitcairnIslands : Country() {
    override val code: String = "PN"

    override val name: String = "Pitcairn Islands"

    override val nativeName: String = "Pitcairn Islands"
  }

  data object Poland : Country() {
    override val code: String = "PL"

    override val name: String = "Poland"

    override val nativeName: String = "Polska"
  }

  data object Portugal : Country() {
    override val code: String = "PT"

    override val name: String = "Portugal"

    override val nativeName: String = "Portugal"
  }

  data object PuertoRico : Country() {
    override val code: String = "PR"

    override val name: String = "Puerto Rico"

    override val nativeName: String = "Puerto Rico"
  }

  data object Qatar : Country() {
    override val code: String = "QA"

    override val name: String = "Qatar"

    override val nativeName: String = "قطر"
  }

  data object Reunion : Country() {
    override val code: String = "RE"

    override val name: String = "Réunion"

    override val nativeName: String = "La Réunion"
  }

  data object Romania : Country() {
    override val code: String = "RO"

    override val name: String = "Romania"

    override val nativeName: String = "România"
  }

  data object Russia : Country() {
    override val code: String = "RU"

    override val name: String = "Russia"

    override val nativeName: String = "Росси"
  }

  data object Rwanda : Country() {
    override val code: String = "RW"

    override val name: String = "Rwanda"

    override val nativeName: String = "Rwanda"
  }

  data object Samoa : Country() {
    override val code: String = "WS"

    override val name: String = "Samoa"

    override val nativeName: String = "Samoa"
  }

  data object SanMarino : Country() {
    override val code: String = "SM"

    override val name: String = "San Marino"

    override val nativeName: String = "San Marino"
  }

  data object SaoTomePrincipe : Country() {
    override val code: String = "ST"

    override val name: String = "São Tomé & Príncipe"

    override val nativeName: String = "São Tomé e Príncipe"
  }

  data object SaudiArabia : Country() {
    override val code: String = "SA"

    override val name: String = "Saudi Arabia"

    override val nativeName: String = "المملكة العربية السعودية"
  }

  data object Senegal : Country() {
    override val code: String = "SN"

    override val name: String = "Senegal"

    override val nativeName: String = "Senegal"
  }

  data object Serbia : Country() {
    override val code: String = "RS"

    override val name: String = "Serbia"

    override val nativeName: String = "Србија"
  }

  data object Seychelles : Country() {
    override val code: String = "SC"

    override val name: String = "Seychelles"

    override val nativeName: String = "Seychelles"
  }

  data object SierraLeone : Country() {
    override val code: String = "SL"

    override val name: String = "Sierra Leone"

    override val nativeName: String = "Sierra Leone"
  }

  data object Singapore : Country() {
    override val code: String = "SG"

    override val name: String = "Singapore"

    override val nativeName: String = "Singapore"
  }

  data object SintMaarten : Country() {
    override val code: String = "SX"

    override val name: String = "Sint Maarten"

    override val nativeName: String = "Sint Maarten"
  }

  data object Slovakia : Country() {
    override val code: String = "SK"

    override val name: String = "Slovakia"

    override val nativeName: String = "Slovensko"
  }

  data object Slovenia : Country() {
    override val code: String = "SI"

    override val name: String = "Slovenia"

    override val nativeName: String = "Slovenia"
  }

  data object SolomonIslands : Country() {
    override val code: String = "SB"

    override val name: String = "Solomon Islands"

    override val nativeName: String = "Solomon Islands"
  }

  data object Somalia : Country() {
    override val code: String = "SO"

    override val name: String = "Somalia"

    override val nativeName: String = "الصومال"
  }

  data object SouthAfrica : Country() {
    override val code: String = "ZA"

    override val name: String = "South Africa"

    override val nativeName: String = "Suid-Afrika"
  }

  data object SouthKorea : Country() {
    override val code: String = "KR"

    override val name: String = "South Korea"

    override val nativeName: String = "대한민국"
  }

  data object SouthSudan : Country() {
    override val code: String = "SS"

    override val name: String = "South Sudan"

    override val nativeName: String = "جنوب السودان"
  }

  data object Spain : Country() {
    override val code: String = "ES"

    override val name: String = "Spain"

    override val nativeName: String = "España"
  }

  data object SriLanka : Country() {
    override val code: String = "LK"

    override val name: String = "Sri Lanka"

    override val nativeName: String = "ශ්‍රී ලංකාව"
  }

  data object StBarthelemy : Country() {
    override val code: String = "BL"

    override val name: String = "St Barthélemy"

    override val nativeName: String = "Saint-Barthélemy"
  }

  data object StHelena : Country() {
    override val code: String = "SH"

    override val name: String = "St Helena"

    override val nativeName: String = "St. Helena"
  }

  data object StKittsNevis : Country() {
    override val code: String = "KN"

    override val name: String = "St Kitts & Nevis"

    override val nativeName: String = "St. Kitts & Nevis"
  }

  data object StLucia : Country() {
    override val code: String = "LC"

    override val name: String = "St Lucia"

    override val nativeName: String = "St. Lucia"
  }

  data object StMartin : Country() {
    override val code: String = "MF"

    override val name: String = "St Martin"

    override val nativeName: String = "Saint-Martin"
  }

  data object StPierreMiquelon : Country() {
    override val code: String = "PM"

    override val name: String = "St Pierre & Miquelon"

    override val nativeName: String = "Saint-Pierre-et-Miquelon"
  }

  data object StVincentGrenadines : Country() {
    override val code: String = "VC"

    override val name: String = "St Vincent & Grenadines"

    override val nativeName: String = "St. Vincent & Grenadines"
  }

  data object Sudan : Country() {
    override val code: String = "SD"

    override val name: String = "Sudan"

    override val nativeName: String = "السودان"
  }

  data object Suriname : Country() {
    override val code: String = "SR"

    override val name: String = "Suriname"

    override val nativeName: String = "Suriname"
  }

  data object SvalbardJanMayen : Country() {
    override val code: String = "SJ"

    override val name: String = "Svalbard & Jan Mayen"

    override val nativeName: String = "Svalbard og Jan Mayen"
  }

  data object Swaziland : Country() {
    override val code: String = "SZ"

    override val name: String = "Swaziland"

    override val nativeName: String = "Swaziland"
  }

  data object Sweden : Country() {
    override val code: String = "SE"

    override val name: String = "Sweden"

    override val nativeName: String = "Sweden"
  }

  data object Switzerland : Country() {
    override val code: String = "CH"

    override val name: String = "Switzerland"

    override val nativeName: String = "Schweiz"
  }

  data object Syria : Country() {
    override val code: String = "SY"

    override val name: String = "Syria"

    override val nativeName: String = "سوريا"
  }

  data object Taiwan : Country() {
    override val code: String = "TW"

    override val name: String = "Taiwan"

    override val nativeName: String = "台灣"
  }

  data object Tajikistan : Country() {
    override val code: String = "TJ"

    override val name: String = "Tajikistan"

    override val nativeName: String = "Тоҷикистон"
  }

  data object Tanzania : Country() {
    override val code: String = "TZ"

    override val name: String = "Tanzania"

    override val nativeName: String = "Tadhania"
  }

  data object Thailand : Country() {
    override val code: String = "TH"

    override val name: String = "Thailand"

    override val nativeName: String = "ไทย"
  }

  data object TimorLeste : Country() {
    override val code: String = "TL"

    override val name: String = "Timor-Leste"

    override val nativeName: String = "Timor-Leste"
  }

  data object Togo : Country() {
    override val code: String = "TG"

    override val name: String = "Togo"

    override val nativeName: String = "Togo nutome"
  }

  data object Tokelau : Country() {
    override val code: String = "TK"

    override val name: String = "Tokelau"

    override val nativeName: String = "Tokelau"
  }

  data object Tonga : Country() {
    override val code: String = "TO"

    override val name: String = "Tonga"

    override val nativeName: String = "Tonga"
  }

  data object TrinidadTobago : Country() {
    override val code: String = "TT"

    override val name: String = "Trinidad & Tobago"

    override val nativeName: String = "Trinidad & Tobago"
  }

  data object Tunisia : Country() {
    override val code: String = "TN"

    override val name: String = "Tunisia"

    override val nativeName: String = "تونس"
  }

  data object Turkey : Country() {
    override val code: String = "TR"

    override val name: String = "Turkey"

    override val nativeName: String = "Turkey"
  }

  data object Turkmenistan : Country() {
    override val code: String = "TM"

    override val name: String = "Turkmenistan"

    override val nativeName: String = "Türkmenistan"
  }

  data object TurksCaicosIslands : Country() {
    override val code: String = "TC"

    override val name: String = "Turks & Caicos Islands"

    override val nativeName: String = "Turks & Caicos Islands"
  }

  data object Tuvalu : Country() {
    override val code: String = "TV"

    override val name: String = "Tuvalu"

    override val nativeName: String = "Tuvalu"
  }

  data object USOutlyingIslands : Country() {
    override val code: String = "UM"

    override val name: String = "US Outlying Islands"

    override val nativeName: String = "U.S. Outlying Islands"
  }

  data object USVirginIslands : Country() {
    override val code: String = "VI"

    override val name: String = "US Virgin Islands"

    override val nativeName: String = "U.S. Virgin Islands"
  }

  data object Uganda : Country() {
    override val code: String = "UG"

    override val name: String = "Uganda"

    override val nativeName: String = "Uganda"
  }

  data object Ukraine : Country() {
    override val code: String = "UA"

    override val name: String = "Ukraine"

    override val nativeName: String = "Украина"
  }

  data object UnitedArabEmirates : Country() {
    override val code: String = "AE"

    override val name: String = "United Arab Emirates"

    override val nativeName: String = "الإمارات العربية المتحدة"
  }

  data object UnitedKingdom : Country() {
    override val code: String = "GB"

    override val name: String = "United Kingdom"

    override val nativeName: String = "Y Deyrnas Unedig"
  }

  data object UnitedStates : Country() {
    override val code: String = "US"

    override val name: String = "United States"

    override val nativeName: String = "ᏌᏊ ᎢᏳᎾᎵᏍᏔᏅ ᏍᎦᏚᎩ"
  }

  data object Uruguay : Country() {
    override val code: String = "UY"

    override val name: String = "Uruguay"

    override val nativeName: String = "Uruguay"
  }

  data object Uzbekistan : Country() {
    override val code: String = "UZ"

    override val name: String = "Uzbekistan"

    override val nativeName: String = "Oʻzbekiston"
  }

  data object Vanuatu : Country() {
    override val code: String = "VU"

    override val name: String = "Vanuatu"

    override val nativeName: String = "Vanuatu"
  }

  data object VaticanCity : Country() {
    override val code: String = "VA"

    override val name: String = "Vatican City"

    override val nativeName: String = "Città del Vaticano"
  }

  data object Venezuela : Country() {
    override val code: String = "VE"

    override val name: String = "Venezuela"

    override val nativeName: String = "Venezuela"
  }

  data object Vietnam : Country() {
    override val code: String = "VN"

    override val name: String = "Vietnam"

    override val nativeName: String = "Việt Nam"
  }

  data object WallisFutuna : Country() {
    override val code: String = "WF"

    override val name: String = "Wallis & Futuna"

    override val nativeName: String = "Wallis-et-Futuna"
  }

  data object WesternSahara : Country() {
    override val code: String = "EH"

    override val name: String = "Western Sahara"

    override val nativeName: String = "الصحراء الغربية"
  }

  data object World : Country() {
    override val code: String = "001"

    override val name: String = "World"

    override val nativeName: String = "العالم"
  }

  data object Yemen : Country() {
    override val code: String = "YE"

    override val name: String = "Yemen"

    override val nativeName: String = "اليمن"
  }

  data object Zambia : Country() {
    override val code: String = "ZM"

    override val name: String = "Zambia"

    override val nativeName: String = "Zambia"
  }

  data object Zimbabwe : Country() {
    override val code: String = "ZW"

    override val name: String = "Zimbabwe"

    override val nativeName: String = "Zimbabwe"
  }
}
