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

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.teogor.ceres.ui.foundation.clickable
import dev.teogor.ceres.ui.theme.MaterialTheme

@Composable
fun SegmentedButtons(
  options: List<String>,
  selectedOption: Int,
  onOptionSelected: (Int) -> Unit,
  modifier: Modifier = Modifier,
) {
  val backgroundOutline = MaterialTheme.colorScheme.outline
  var selectedOptionRem by remember { mutableIntStateOf(selectedOption) }

  Row(
    modifier = Modifier
      .horizontalScroll(rememberScrollState())
      .then(modifier)
      .border(
        width = 1.dp,
        color = backgroundOutline,
        shape = MaterialTheme.shapes.extraLarge,
      ),
    horizontalArrangement = Arrangement.SpaceBetween,
  ) {
    options.forEachIndexed { index, option ->
      val cornerShape = when (index) {
        0 -> if (options.size - 1 != 0) {
          MaterialTheme.shapes.extraLarge.copy(
            topEnd = ZeroCornerSize,
            bottomEnd = ZeroCornerSize,
          )
        } else {
          MaterialTheme.shapes.extraLarge
        }

        options.size - 1 -> MaterialTheme.shapes.extraLarge.copy(
          topStart = ZeroCornerSize,
          bottomStart = ZeroCornerSize,
        )

        else -> RectangleShape
      }

      Box(
        modifier = if (index == selectedOption) {
          Modifier
            .height(40.dp)
            .widthIn(min = 80.dp, max = Dp.Unspecified)
            .clip(cornerShape)
            .background(
              color = MaterialTheme.colorScheme.secondaryContainer,
              shape = cornerShape,
            )
            .clickable {
              selectedOptionRem = index
              onOptionSelected(index)
            }
            .padding(horizontal = 20.dp)
        } else {
          Modifier
            .height(40.dp)
            .widthIn(min = 80.dp, max = Dp.Unspecified)
            .clip(cornerShape)
            .clickable {
              selectedOptionRem = index
              onOptionSelected(index)
            }
            .padding(horizontal = 20.dp)
        },
      ) {
        Text(
          text = option,
          color = MaterialTheme.colorScheme.onSurface,
          style = MaterialTheme.typography.labelMedium,
          modifier = Modifier.align(Alignment.Center),
        )
      }

      if (index < options.size - 1) {
        VerticalDivider(
          color = backgroundOutline,
          thickness = 1.dp,
          modifier = Modifier.height(40.dp),
        )
      }
    }
  }
}
