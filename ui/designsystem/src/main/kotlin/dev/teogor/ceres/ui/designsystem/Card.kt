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

package dev.teogor.ceres.ui.designsystem

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import dev.teogor.ceres.ui.designsystem.CardScheme.AMEX
import dev.teogor.ceres.ui.designsystem.CardScheme.DINERS_CLUB
import dev.teogor.ceres.ui.designsystem.CardScheme.DISCOVER
import dev.teogor.ceres.ui.designsystem.CardScheme.JCB
import dev.teogor.ceres.ui.designsystem.CardScheme.MAESTRO
import dev.teogor.ceres.ui.designsystem.CardScheme.MASTERCARD
import dev.teogor.ceres.ui.designsystem.CardScheme.UNKNOWN
import dev.teogor.ceres.ui.designsystem.CardScheme.VISA

@Composable
fun CardNumberTextField(
  modifier: Modifier = Modifier,
  label: String,
  cardNumber: String = "",
  onCardNumberChange: (String) -> Unit,
) {
  OutlinedTextField(
    value = cardNumber,
    onValueChange = {
      onCardNumberChange(it.take(16))
    },
    keyboardOptions = KeyboardOptions(
      keyboardType =
      KeyboardType.Number,
    ),
    visualTransformation = { number ->
      when (identifyCardScheme(cardNumber)) {
        AMEX -> formatAmex(number)
        DINERS_CLUB -> formatDinnersClub(number)
        else -> formatOtherCardNumbers(number)
      }
    },
    modifier = modifier,
    label = { Text(label) },
  )
}

enum class CardScheme {
  JCB, AMEX, DINERS_CLUB, VISA, MASTERCARD, DISCOVER, MAESTRO, UNKNOWN
}

fun identifyCardScheme(cardNumber: String): CardScheme {
  val jcbRegex = Regex("^(?:2131|1800|35)[0-9]*$")
  val ameRegex = Regex("^3[47][0-9]*\$")
  val dinersRegex = Regex("^3(?:0[0-59]|[689])[0-9]*\$")
  val visaRegex = Regex("^4[0-9]*\$")
  val masterCardRegex = Regex("^(5[1-5]|222[1-9]|22[3-9]|2[3-6]|27[01]|2720)[0-9]*\$")
  val maestroRegex = Regex("^(5[06789]|6)[0-9]*\$")
  val discoverRegex =
    Regex("^(6011|65|64[4-9]|62212[6-9]|6221[3-9]|622[2-8]|6229[01]|62292[0-5])[0-9]*\$")

  val trimmedCardNumber = cardNumber.replace(" ", "")

  return when {
    trimmedCardNumber.matches(jcbRegex) -> JCB
    trimmedCardNumber.matches(ameRegex) -> AMEX
    trimmedCardNumber.matches(dinersRegex) -> DINERS_CLUB
    trimmedCardNumber.matches(visaRegex) -> VISA
    trimmedCardNumber.matches(masterCardRegex) -> MASTERCARD
    trimmedCardNumber.matches(discoverRegex) -> DISCOVER
    trimmedCardNumber.matches(maestroRegex) -> if (cardNumber[0] == '5') MASTERCARD else MAESTRO
    else -> UNKNOWN
  }
}

fun formatAmex(text: AnnotatedString): TransformedText {
//
  val trimmed = if (text.text.length >= 15) text.text.substring(0..14) else text.text
  var out = ""

  for (i in trimmed.indices) {
    out += trimmed[i]
//        put - character at 3rd and 9th indicies
    if (i == 3 || i == 9) out += "-"
  }
//    original - 345678901234564
//    transformed - 3456-7890123-4564
//    xxxx-xxxxxx-xxxxx
  /**
   * The offset translator should ignore the hyphen characters, so conversion from
   *  original offset to transformed text works like
   *  - The 4th char of the original text is 5th char in the transformed text. (i.e original[4th] == transformed[5th]])
   *  - The 11th char of the original text is 13th char in the transformed text. (i.e original[11th] == transformed[13th])
   *  Similarly, the reverse conversion works like
   *  - The 5th char of the transformed text is 4th char in the original text. (i.e  transformed[5th] == original[4th] )
   *  - The 13th char of the transformed text is 11th char in the original text. (i.e transformed[13th] == original[11th])
   */
  val creditCardOffsetTranslator = object : OffsetMapping {
    override fun originalToTransformed(offset: Int): Int {
      if (offset <= 3) return offset
      if (offset <= 9) return offset + 1
      if (offset <= 15) return offset + 2
      return 17
    }

    override fun transformedToOriginal(offset: Int): Int {
      if (offset <= 4) return offset
      if (offset <= 11) return offset - 1
      if (offset <= 17) return offset - 2
      return 15
    }
  }
  return TransformedText(AnnotatedString(out), creditCardOffsetTranslator)
}

fun formatDinnersClub(text: AnnotatedString): TransformedText {
  val trimmed = if (text.text.length >= 14) text.text.substring(0..13) else text.text
  var out = ""

  for (i in trimmed.indices) {
    out += trimmed[i]
    if (i == 3 || i == 9) out += "-"
  }

  val creditCardOffsetTranslator = object : OffsetMapping {
    override fun originalToTransformed(offset: Int): Int {
      if (offset <= 3) return offset
      if (offset <= 9) return offset + 1
      if (offset <= 14) return offset + 2
      return 16
    }

    override fun transformedToOriginal(offset: Int): Int {
      if (offset <= 4) return offset
      if (offset <= 11) return offset - 1
      if (offset <= 16) return offset - 2
      return 14
    }
  }
  return TransformedText(AnnotatedString(out), creditCardOffsetTranslator)
}

fun formatOtherCardNumbers(text: AnnotatedString): TransformedText {
  val trimmed = if (text.text.length >= 16) text.text.substring(0..15) else text.text
  var out = ""

  for (i in trimmed.indices) {
    out += trimmed[i]
    if (i % 4 == 3 && i != 15) out += "-"
  }
  val creditCardOffsetTranslator = object : OffsetMapping {
    override fun originalToTransformed(offset: Int): Int {
      if (offset <= 3) return offset
      if (offset <= 7) return offset + 1
      if (offset <= 11) return offset + 2
      if (offset <= 16) return offset + 3
      return 19
    }

    override fun transformedToOriginal(offset: Int): Int {
      if (offset <= 4) return offset
      if (offset <= 9) return offset - 1
      if (offset <= 14) return offset - 2
      if (offset <= 19) return offset - 3
      return 16
    }
  }

  return TransformedText(AnnotatedString(out), creditCardOffsetTranslator)
}
