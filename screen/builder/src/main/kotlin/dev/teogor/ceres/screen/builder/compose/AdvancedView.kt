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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.teogor.ceres.screen.builder.endPadding
import dev.teogor.ceres.screen.builder.horizontalNoIconPadding
import dev.teogor.ceres.screen.builder.horizontalPadding
import dev.teogor.ceres.screen.builder.iconSize
import dev.teogor.ceres.screen.builder.model.AdvancedViewBuilder
import dev.teogor.ceres.screen.builder.verticalPadding
import dev.teogor.ceres.ui.designsystem.SegmentedButtons
import dev.teogor.ceres.ui.designsystem.Switch
import dev.teogor.ceres.ui.designsystem.Text
import dev.teogor.ceres.ui.foundation.clickable
import dev.teogor.ceres.ui.theme.MaterialTheme
import dev.teogor.ceres.ui.theme.toColor
import dev.teogor.ceres.ui.theme.tokens.ColorSchemeKeyTokens

@Composable
fun AdvancedView(
  item: AdvancedViewBuilder,
) = with(item) {
  val hasSwitch = switchToggled != null
  var isSwitchToggled by remember(switchToggled) {
    if (hasSwitch) {
      mutableStateOf(switchToggled!!)
    } else {
      mutableStateOf(false)
    }
  }
  LaunchedEffect(switchToggled, isSwitchToggled) {
    if (hasSwitch) {
      if (isSwitchToggled != switchToggled!!) {
        onSwitchToggled?.invoke(isSwitchToggled)
      }
    }
  }
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .clickable(
        enabled = clickable != null || hasSwitch,
      ) {
        // todo add vertical divider like Samsung switch option
        if (hasSwitch) {
          isSwitchToggled = !isSwitchToggled
        } else {
          clickable?.invoke()
        }
      }
      .padding(
        top = verticalPadding,
        bottom = verticalPadding,
        start = horizontalPadding,
        end = horizontalPadding,
      ),
  ) {
    icon?.let {
      Icon(
        imageVector = it,
        contentDescription = title,
        modifier = Modifier
          .padding(top = 10.dp)
          .size(iconSize),
        tint = MaterialTheme.colorScheme.secondary,
      )
    }
    Column(
      modifier = Modifier.padding(
        start = if (icon != null) {
          horizontalPadding
        } else {
          horizontalNoIconPadding
        },
      ),
    ) {
      Row(
        modifier = Modifier.fillMaxWidth(),
      ) {
        Column(
          modifier = Modifier.weight(1f),
        ) {
          Text(
            text = title,
            fontSize = 15.sp,
            textAlign = TextAlign.Start,
            color = MaterialTheme.colorScheme.onSurface,
          )
          subtitle?.let { subtitle ->
            val subtitleTextColor = subtitleColor?.toColor() ?: MaterialTheme.colorScheme.onSurface
            Text(
              modifier = Modifier.padding(
                top = 1.dp,
                end = endPadding,
              ),
              text = subtitle,
              fontSize = 13.sp,
              lineHeight = 16.sp,
              textAlign = TextAlign.Start,
              color = subtitleTextColor,
            )
          }
        }

        if (hasSwitch) {
          Switch(
            checked = isSwitchToggled,
            onCheckedChange = {
              isSwitchToggled = !isSwitchToggled
            },
          )
        }
      }

      segmentedOptions?.let { segmentedOptions ->
        var selectedOption by remember { mutableIntStateOf(segmentedSelectedOption ?: -1) }

        SegmentedButtons(
          modifier = Modifier.padding(top = 10.dp, end = 20.dp),
          options = segmentedOptions,
          selectedOption = selectedOption,
          onOptionSelected = { option ->
            selectedOption = option
            segmentedOnOptionSelected?.invoke(option)
          },
        )
      }

      customView?.invoke()
    }
  }
}

@Composable
fun SwitchView(
  title: String,
  subtitle: String? = null,
  subtitleColor: ColorSchemeKeyTokens? = null,
  icon: ImageVector? = null,
  switchToggled: Boolean,
  onSwitchToggled: ((Boolean) -> Unit)? = null,
) {
  var isSwitchToggled by remember(switchToggled) {
    mutableStateOf(switchToggled)
  }
  LaunchedEffect(switchToggled, isSwitchToggled) {
    if (isSwitchToggled != switchToggled) {
      onSwitchToggled?.invoke(isSwitchToggled)
    }
  }
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .clickable(
        enabled = true,
      ) {
        // todo add vertical divider like Samsung switch option
        isSwitchToggled = !isSwitchToggled
      }
      .padding(
        top = verticalPadding,
        bottom = verticalPadding,
        start = horizontalPadding,
        end = horizontalPadding,
      ),
  ) {
    icon?.let {
      Icon(
        imageVector = it,
        contentDescription = title,
        modifier = Modifier
          .padding(top = 10.dp)
          .size(iconSize),
        tint = MaterialTheme.colorScheme.secondary,
      )
    }
    Column(
      modifier = Modifier.padding(
        start = if (icon != null) {
          horizontalPadding
        } else {
          horizontalNoIconPadding
        },
      ),
    ) {
      Row(
        modifier = Modifier.fillMaxWidth(),
      ) {
        Column(
          modifier = Modifier.weight(1f),
        ) {
          Text(
            text = title,
            fontSize = 15.sp,
            textAlign = TextAlign.Start,
            color = MaterialTheme.colorScheme.onSurface,
          )
          subtitle?.let { subtitle ->
            val subtitleTextColor = subtitleColor?.toColor() ?: MaterialTheme.colorScheme.onSurface
            Text(
              modifier = Modifier.padding(
                top = 1.dp,
                end = endPadding,
              ),
              text = subtitle,
              fontSize = 13.sp,
              lineHeight = 16.sp,
              textAlign = TextAlign.Start,
              color = subtitleTextColor,
            )
          }
        }

        Switch(
          checked = isSwitchToggled,
          onCheckedChange = {
            isSwitchToggled = !isSwitchToggled
          },
        )
      }
    }
  }
}
