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

import androidx.compose.foundation.text.ClickableText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import dev.teogor.ceres.ui.theme.MaterialTheme

@Composable
fun ParsableText(
  text: String,
  elements: List<ClickableElement>,
  onElementClicked: (element: ClickableElement) -> Unit,
  modifier: Modifier = Modifier,
) {
  val annotatedString = buildAnnotatedString {
    val parts = text.split(*elements.map { it.text }.toTypedArray())
    var i = 0
    parts.forEachIndexed { index, part ->
      append(part)
      i += part.length
      if (index != parts.lastIndex) {
        val element = elements[index]
        pushStringAnnotation(tag = element.text, annotation = "")
        withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.tertiary)) {
          append(element.text)
        }
        pop()
        i += element.text.length
      }
    }
  }

  ClickableText(
    text = annotatedString,
    style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onBackground),
    modifier = modifier,
    onClick = { offset ->
      annotatedString.getStringAnnotations(start = offset, end = offset)
        .firstOrNull()?.let { annotation ->
          val elementText = annotation.tag
          elements.find { it.text == elementText }?.let { element ->
            onElementClicked(element)
          }
        }
    },
  )
}

open class ClickableElement(val text: String)
