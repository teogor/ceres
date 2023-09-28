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

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.teogor.ceres.ui.foundation.clickable
import dev.teogor.ceres.ui.theme.MaterialTheme
import dev.teogor.ceres.ui.theme.core.SurfaceOverlay

/**
 * Composable item with an icon and text.
 *
 * @param iconId the drawable resource ID for the icon.
 * @param text the text to display.
 * @param enableRipple whether to apply a ripple effect on click or not.
 * @param modifier the modifier to apply to the item.
 * @param contentDescription The description for the icon.
 */
@Composable
fun Item(
  @DrawableRes iconId: Int,
  text: String,
  enableRipple: Boolean = true,
  modifier: Modifier = Modifier,
  contentDescription: String = "",
) {
  val colorScheme = MaterialTheme.colorScheme
  val surfaceOverlay = SurfaceOverlay(
    themeOverlayColor = colorScheme.surface.toArgb(),
    themeSurfaceColor = colorScheme.primary.toArgb(),
  )
  val background = surfaceOverlay.forAlpha(overlayAlpha = 0.12f)
  val ripple = surfaceOverlay.forAlpha(overlayAlpha = 0.5f)
  Row(
    modifier = modifier
      .fillMaxWidth()
      .padding(horizontal = 4.dp, vertical = 1.dp)
      .clip(shape = RoundedCornerShape(10.dp))
      .background(background)
      .let {
        if (enableRipple) {
          it.clickable(
            // conditionally apply clickable with ripple effect
            onClick = {
            },
            indication = rememberRipple(
              color = ripple,
            ),
            interactionSource = remember { MutableInteractionSource() },
          )
        } else {
          it
        }
      }
      .padding(horizontal = 10.dp, vertical = 6.dp),
  ) {
    Image(
      painter = painterResource(iconId),
      contentDescription = contentDescription,
      colorFilter = ColorFilter.tint(colorScheme.onPrimaryContainer),
      modifier = Modifier
        .padding(end = 8.dp)
        .clip(CircleShape)
        .background(colorScheme.primaryContainer)
        .size(36.dp)
        .padding(10.dp)
        .align(alignment = Alignment.CenterVertically),
    )
    Text(
      text = text,
      color = colorScheme.onBackground,
      fontSize = 14.sp,
      lineHeight = 14.sp,
      textAlign = TextAlign.Start,
      modifier = Modifier
        .align(alignment = Alignment.CenterVertically),
    )
  }
}
