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
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import dev.teogor.ceres.ui.foundation.applyTouchFeedback
import dev.teogor.ceres.ui.theme.MaterialTheme
import dev.teogor.ceres.ui.theme.core.SurfaceOverlay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Dialog(
  title: String,
  items: List<String>,
  selectedItem: Int,
  onSelectionChange: (Int) -> Unit,
  onDismissRequest: () -> Unit,
) {
  val colorScheme = MaterialTheme.colorScheme
  val surfaceOverlay = SurfaceOverlay(
    themeOverlayColor = colorScheme.surface.toArgb(),
    themeSurfaceColor = colorScheme.primary.toArgb(),
  )
  val background = surfaceOverlay.forAlpha(overlayAlpha = 0.1f)
  val selectedColor = colorScheme.primary
  val unselectedColor = colorScheme.onPrimaryContainer
  AlertDialog(
    onDismissRequest = onDismissRequest,
    properties = DialogProperties(
      dismissOnBackPress = true,
      dismissOnClickOutside = true,
    ),
  ) {
    Box(
      Modifier
        .padding(vertical = 30.dp)
        .clip(shape = RoundedCornerShape(24.dp))
        .background(color = background),
    ) {
      Column {
        if (title.isNotEmpty()) {
          Text(
            text = title,
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier
              .fillMaxWidth()
              .padding(
                top = 24.dp,
                bottom = 8.dp,
              )
              .padding(horizontal = 24.dp),
            textAlign = TextAlign.Center,
          )
        }
        val state = rememberScrollState()
        Column(
          modifier = Modifier.verticalScroll(
            state,
          ),
        ) {
          items.forEachIndexed { idx, item ->
            Row(
              Modifier
                .fillMaxWidth()
                .selectable(
                  selected = idx == selectedItem,
                  onClick = {
                    applyTouchFeedback()
                    onSelectionChange(idx)
                    onDismissRequest()
                  },
                )
                .padding(horizontal = 16.dp, vertical = 4.dp),
              verticalAlignment = Alignment.CenterVertically,
            ) {
              RadioButton(
                selected = idx == selectedItem,
                onClick = {
                  applyTouchFeedback()
                  onSelectionChange(idx)
                  onDismissRequest()
                },
                colors = RadioButtonDefaults.colors(
                  selectedColor = selectedColor,
                  unselectedColor = unselectedColor,
                ),
              )
              Spacer(Modifier.width(8.dp))
              Text(
                text = item,
                fontSize = 16.sp,
                color = colorScheme.onSurface,
              )
            }
          }
        }
        Spacer(Modifier.height(16.dp))
      }
    }
  }
}
