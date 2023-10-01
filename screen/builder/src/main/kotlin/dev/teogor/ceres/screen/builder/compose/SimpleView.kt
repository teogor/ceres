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

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.teogor.ceres.screen.builder.endPadding
import dev.teogor.ceres.screen.builder.horizontalNoIconPadding
import dev.teogor.ceres.screen.builder.horizontalPadding
import dev.teogor.ceres.screen.builder.iconSize
import dev.teogor.ceres.screen.builder.model.SimpleViewBuilder
import dev.teogor.ceres.screen.builder.utilities.perform
import dev.teogor.ceres.screen.builder.verticalPadding
import dev.teogor.ceres.ui.designsystem.Text
import dev.teogor.ceres.ui.foundation.clickable
import dev.teogor.ceres.ui.theme.MaterialTheme
import dev.teogor.ceres.ui.theme.toColor
import dev.teogor.ceres.ui.theme.tokens.ColorSchemeKeyTokens

fun addExtraPadding(enabled: Boolean) = if (enabled) 0.dp else 0.dp // 9.dp

@Deprecated(
  message = "Use SimpleView(title, subtitle, subtitleColor, icon, clickable) instead",
  replaceWith = ReplaceWith(
    expression = "SimpleView(title, subtitle, subtitleColor, icon, clickable)",
    imports = ["dev.teogor.ceres.screen.builder.compose.SimpleView"],
  ),
)
@Composable
fun SimpleView(
  item: SimpleViewBuilder,
) = with(item) {
  val hasFilledIconUI = false
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .clickable {
        clickable?.invoke()
      }
      .padding(
        top = verticalPadding,
        bottom = verticalPadding,
        start = horizontalPadding + addExtraPadding(hasFilledIconUI && icon != null),
        end = horizontalPadding,
      ),
    verticalAlignment = if (subtitle != null) {
      Alignment.Top
    } else {
      Alignment.CenterVertically
    },
  ) {
    icon.perform {
      if (hasFilledIconUI) {
        Icon(
          imageVector = it,
          contentDescription = title,
          tint = MaterialTheme.colorScheme.onPrimaryContainer,
          modifier = Modifier
            .size(44.dp)
            .background(
              color = MaterialTheme.colorScheme.primaryContainer,
              shape = CircleShape,
            )
            .padding(10.dp)
            .align(Alignment.CenterVertically),
        )
      } else {
        Icon(
          imageVector = it,
          contentDescription = title,
          modifier = Modifier
            .size(iconSize)
            .align(Alignment.CenterVertically),
          tint = MaterialTheme.colorScheme.secondary,
        )
      }
    }
    Column(
      modifier = Modifier
        .padding(
          start = if (icon != null) {
            horizontalPadding + addExtraPadding(hasFilledIconUI)
          } else {
            horizontalNoIconPadding + addExtraPadding(false)
          },
        )
        .defaultMinSize(minHeight = 50.dp),
      verticalArrangement = Arrangement.Center,
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
  }
}

@Composable
fun SimpleView(
  title: String,
  subtitle: String? = null,
  subtitleColor: ColorSchemeKeyTokens? = null,
  icon: ImageVector? = null,
  clickable: (() -> Unit)? = null,
  enabled: Boolean = clickable != null,
  columnContent: (@Composable ColumnScope.() -> Unit)? = null,
  content: (@Composable () -> Unit)? = null,
) {
  val hasFilledIconUI = UiOptions.uiTypes == UiTypes.Filled
  Column(
    modifier = Modifier
      .fillMaxWidth()
      .clickable(enabled = enabled) {
        clickable?.invoke()
      },
  ) {
    Row(
      modifier = Modifier
        .padding(
          top = verticalPadding,
          bottom = verticalPadding,
          start = horizontalPadding + addExtraPadding(hasFilledIconUI && icon != null),
          end = horizontalPadding,
        ),
      verticalAlignment = if (subtitle != null) {
        Alignment.Top
      } else {
        Alignment.CenterVertically
      },
    ) {
      icon.perform {
        if (hasFilledIconUI) {
          Icon(
            imageVector = it,
            contentDescription = title,
            tint = MaterialTheme.colorScheme.onPrimaryContainer,
            modifier = Modifier
              .size(44.dp)
              .background(
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = CircleShape,
              )
              .padding(10.dp)
              .align(Alignment.CenterVertically),
          )
        } else {
          Icon(
            imageVector = it,
            contentDescription = title,
            modifier = Modifier
              .size(iconSize)
              .align(Alignment.CenterVertically),
            tint = MaterialTheme.colorScheme.secondary,
          )
        }
      }
      Column(
        modifier = Modifier
          .padding(
            start = if (icon != null) {
              horizontalPadding + addExtraPadding(hasFilledIconUI)
            } else {
              horizontalNoIconPadding + addExtraPadding(false)
            },
          )
          .defaultMinSize(minHeight = 50.dp),
        verticalArrangement = Arrangement.Center,
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

        columnContent?.invoke(this)
      }
    }
    content?.invoke()
  }
}
