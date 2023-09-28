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

import androidx.annotation.FloatRange
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.unit.sp
import dev.teogor.ceres.ui.foundation.withTouchFeedback
import dev.teogor.ceres.ui.theme.MaterialTheme

// todo refactor toolbar file structure

data class DropDownItem(
  val label: String,
  val onClick: () -> Unit,
)

data class Icon(
  val image: ImageVector,
  val image2: ImageVector? = null,
  val activeState: Boolean = false,
  @FloatRange(from = 0.0, to = 360.0) val rotation: Float = 0f,
)

data class ButtonData(
  val label: String,
  val icon: Icon,
  val onClick: () -> Unit,
)

@Composable
fun Toolbar(
  title: String,
  showBackButton: Boolean = false,
  showSettingsButton: Boolean = false,
  showEditButton: Boolean = false,
  showDropDownMenu: Boolean = false,
  endDigits: String = "",
  dropDownItems: List<DropDownItem> = listOf(),
  buttons: List<ButtonData> = listOf(),
  onBackClicked: () -> Unit = {},
  onOpenMenu: () -> Unit = {},
  onEditClicked: () -> Unit = {},
  triggerAlpha: Float = 0f,
  handleElevation: @Composable () -> Dp = {
    CeresNavigationDefaults.Elevation
  },
) {
  val surfaceColor = MaterialTheme.colorScheme.surface
  val elevation = handleElevation()

  val toolbarHeight = remember(triggerAlpha) {
    derivedStateOf {
      lerp(56.dp, 120.dp, triggerAlpha)
    }
  }

  Surface(
    modifier = Modifier.fillMaxWidth(),
    // todo shadowElevation = elevation,
    color = surfaceColorAtElevation(
      elevation = elevation,
      color = surfaceColor,
    ),
  ) {
    Column(
      modifier = Modifier.fillMaxWidth(),
    ) {
      Spacer(
        modifier = Modifier
          .fillMaxWidth()
          .statusBarsPadding(),
      )
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .height(toolbarHeight.value),
      ) {
        if (showBackButton) {
          IconButton(
            onClick = onBackClicked.withTouchFeedback(LocalContext.current),
            modifier = Modifier
              .align(Alignment.CenterStart)
              .padding(start = 4.dp)
              .size(48.dp),
          ) {
            Icon(
              imageVector = Icons.Default.ArrowBackIosNew,
              contentDescription = "Back",
            )
          }
        }

        if (title.isNotEmpty()) {
          Text(
            text = title,
            fontSize = 20.sp,
            modifier = Modifier.align(Alignment.Center),
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
          )
        }

        Row(
          modifier = Modifier
            .align(Alignment.CenterEnd)
            .padding(end = 8.dp),
        ) {
          buttons.forEach {
            IconButton(
              onClick = it.onClick.withTouchFeedback(LocalContext.current),
              modifier = Modifier.size(48.dp),
            ) {
              if (it.icon.image2 == null) {
                RotatableIcon(
                  imageVector = it.icon.image,
                  rotation = it.icon.rotation,
                  contentDescription = it.label,
                )
              } else {
                val icon = remember(it.icon.activeState) {
                  mutableStateOf(
                    if (it.icon.activeState) {
                      it.icon.image2
                    } else {
                      it.icon.image
                    },
                  )
                }
                Icon(
                  imageVector = icon.value,
                  contentDescription = it.label,
                )
              }
            }
          }
          if (showEditButton) {
            IconButton(
              onClick = onEditClicked.withTouchFeedback(LocalContext.current),
              modifier = Modifier.size(48.dp),
            ) {
              Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = "Edit",
              )
            }
          }
          if (showSettingsButton) {
            DigitsImage(
              content = DigitsImageContent.Text(
                endDigits.ifEmpty {
                  "ME"
                },
              ),
              modifier = Modifier
                .size(48.dp)
                .padding(4.dp),
              onClick = onOpenMenu,
            )
          }
          if (showDropDownMenu && dropDownItems.isNotEmpty()) {
            var expanded by remember { mutableStateOf(false) }
            IconButton(
              onClick = { expanded = true }.withTouchFeedback(LocalContext.current),
              modifier = Modifier.size(48.dp),
            ) {
              Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = "More options",
              )
            }

            DropdownMenu(
              expanded = expanded,
              onDismissRequest = { expanded = false },
              modifier = Modifier
                .background(
                  color = surfaceColorAtElevation(
                    color = CeresNavigationDefaults.containerColor,
                    elevation = CeresNavigationDefaults.Elevation,
                  ),
                ),
            ) {
              dropDownItems.forEach {
                DropdownMenuItem(
                  onClick = {
                    expanded = false
                    it.onClick()
                  }.withTouchFeedback(LocalContext.current),
                  text = {
                    Text(
                      text = it.label,
                      color = MaterialTheme.colorScheme.onSurface,
                    )
                  },
                )
              }
            }
          }
        }
      }
    }
  }
}
