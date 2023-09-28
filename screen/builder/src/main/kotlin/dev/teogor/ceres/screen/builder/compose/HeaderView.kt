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

package dev.teogor.ceres.screen.builder.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import dev.teogor.ceres.screen.builder.horizontalNoIconPadding
import dev.teogor.ceres.screen.builder.horizontalPadding
import dev.teogor.ceres.screen.builder.model.HeaderViewBuilder
import dev.teogor.ceres.screen.builder.topPadding
import dev.teogor.ceres.ui.designsystem.Text
import dev.teogor.ceres.ui.theme.MaterialTheme

@Deprecated(
  message = "Use HeaderView(title) instead",
  replaceWith = ReplaceWith(
    expression = "HeaderView(title)",
    imports = ["dev.teogor.ceres.screen.builder.compose.HeaderView"],
  ),
)
@Composable
fun HeaderView(
  item: HeaderViewBuilder,
) = with(item) {
  Column(
    modifier = Modifier
      .padding(
        top = topPadding,
      )
      .padding(
        horizontal = horizontalPadding,
      )
      .padding(
        start = horizontalNoIconPadding,
      ),
  ) {
    Text(
      text = title,
      fontSize = 13.sp,
      fontWeight = FontWeight.Medium,
      textAlign = TextAlign.Start,
      color = MaterialTheme.colorScheme.secondary,
    )
  }
}

@Composable
fun HeaderView(
  title: String,
) {
  Column(
    modifier = Modifier
      .padding(
        top = topPadding,
      )
      .padding(
        horizontal = horizontalPadding,
      )
      .padding(
        start = horizontalNoIconPadding,
      ),
  ) {
    Text(
      text = title,
      fontSize = 13.sp,
      fontWeight = FontWeight.Medium,
      textAlign = TextAlign.Start,
      color = MaterialTheme.colorScheme.secondary,
    )
  }
}
