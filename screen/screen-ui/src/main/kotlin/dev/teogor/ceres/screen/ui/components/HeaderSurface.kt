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

package dev.teogor.ceres.screen.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.teogor.ceres.ui.designsystem.Surface
import dev.teogor.ceres.ui.designsystem.Switch
import dev.teogor.ceres.ui.designsystem.SwitchDefaults
import dev.teogor.ceres.ui.designsystem.Text
import dev.teogor.ceres.ui.designsystem.surfaceColorAtElevation
import dev.teogor.ceres.ui.foundation.graphics.Icon
import dev.teogor.ceres.ui.theme.MaterialTheme
import dev.teogor.ceres.ui.theme.contentColorFor
import dev.teogor.ceres.ui.theme.tokens.ElevationTokens

@Composable
fun HeaderSurface(
  title: String,
  icon: Icon,
  clickable: () -> Unit,
) {
  HeaderSurfaceBase(
    modifier = Modifier
      .padding(top = 10.dp)
      .padding(horizontal = 10.dp),
    clickable = clickable,
  ) {
    Text(
      text = title,
      fontSize = 16.sp,
      modifier = Modifier.weight(1f),
    )
    when (icon) {
      is Icon.ImageVectorIcon -> Icon(
        imageVector = icon.imageVector,
        contentDescription = title,
        modifier = Modifier.size(26.dp),
      )

      is Icon.DrawableResourceIcon -> Icon(
        painter = painterResource(id = icon.id),
        contentDescription = title,
        modifier = Modifier.size(26.dp),
      )
    }
  }
}

@Composable
fun HeaderSurfaceSwitch(
  title: String,
  isChecked: Boolean,
  onCheckedChange: (Boolean) -> Unit,
) {
  HeaderSurfaceBase(
    modifier = Modifier
      .padding(top = 10.dp)
      .padding(horizontal = 10.dp),
    clickable = {
      onCheckedChange(!isChecked)
    },
  ) {
    Text(
      text = title,
      fontSize = 16.sp,
      modifier = Modifier.weight(1f),
    )
    Switch(
      modifier = Modifier.heightIn(max = 26.dp),
      checked = isChecked,
      onCheckedChange = {
        onCheckedChange(it)
      },
      colors = SwitchDefaults.colors(),
    )
  }
}

@Composable
fun HeaderSurfaceBase(
  modifier: Modifier = Modifier,
  contentPadding: PaddingValues = PaddingValues(
    horizontal = 20.dp,
    vertical = 16.dp,
  ),
  clickable: () -> Unit,
  content: @Composable RowScope.() -> Unit,
) {
  val roundedShape = remember {
    RoundedCornerShape(20.dp)
  }
  val backgroundColor = MaterialTheme.colorScheme.tertiaryContainer
  Surface(
    onClick = clickable,
    modifier = modifier.fillMaxWidth(),
    color = backgroundColor,
    shape = roundedShape,
    contentColor = contentColorFor(
      backgroundColor = MaterialTheme.colorScheme.quaternary.surfaceColorAtElevation(
        elevation = ElevationTokens.Level10,
        surface = MaterialTheme.colorScheme.tertiaryContainer,
      ),
    ),
  ) {
    Row(
      modifier = Modifier
        .padding(contentPadding),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.End,
    ) {
      content()
    }
  }
}
