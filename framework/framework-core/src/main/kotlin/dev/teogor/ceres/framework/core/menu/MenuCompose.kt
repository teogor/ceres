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

package dev.teogor.ceres.framework.core.menu

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.teogor.ceres.core.register.BuildProfiler
import dev.teogor.ceres.core.register.LocalBuildProfiler
import dev.teogor.ceres.navigation.core.LocalNavigationParameters
import dev.teogor.ceres.ui.designsystem.Surface
import dev.teogor.ceres.ui.designsystem.Text
import dev.teogor.ceres.ui.foundation.clickable
import dev.teogor.ceres.ui.foundation.clickableSingle
import dev.teogor.ceres.ui.foundation.lib.resources.Symbols
import dev.teogor.ceres.ui.foundation.withTouchFeedback
import dev.teogor.ceres.ui.theme.MaterialTheme
import dev.teogor.ceres.ui.theme.surfaceColorAtElevation
import dev.teogor.ceres.ui.theme.tokens.ElevationTokens
import java.util.Calendar

private val buildProfiler: BuildProfiler
  get() = LocalBuildProfiler.current
private val cornerSize = 20.dp
private val roundedShape = RoundedCornerShape(cornerSize)
private val roundedShapeTop = RoundedCornerShape(
  topStart = cornerSize,
  topEnd = cornerSize,
)
private val roundedShapeBottom = RoundedCornerShape(
  bottomStart = cornerSize,
  bottomEnd = cornerSize,
)

// todo is public due to use in Menu
@Composable
fun MenuTitle(
  title: String,
  hasCloseButton: Boolean = true,
  onClose: () -> Unit = {},
  closeIcon: ImageVector = Icons.Default.Close,
) {
  val localAppState = LocalNavigationParameters.current
  Row(
    verticalAlignment = Alignment.CenterVertically,
  ) {
    if (hasCloseButton) {
      IconButton(
        onClick = {
          localAppState.hideMenu()
          onClose()
        }.withTouchFeedback(LocalContext.current),
        modifier = Modifier
          .size(48.dp),
      ) {
        Icon(
          imageVector = closeIcon,
          contentDescription = "Close",
          tint = MaterialTheme.colorScheme.onSurface,
        )
      }
    }

    Text(
      text = title,
      fontSize = 18.sp,
      modifier = Modifier
        .weight(1f)
        .padding(end = if (hasCloseButton) 48.dp else 0.dp),
      textAlign = TextAlign.Center,
      maxLines = 1,
      overflow = TextOverflow.Ellipsis,
      color = MaterialTheme.colorScheme.onSurface,
    )
  }
}

@Composable
internal fun MenuItem(
  content: String,
  modifier: Modifier = Modifier,
  contentDescription: String = content.lowercase(),
  icon: ImageVector? = null,
  description: String? = null,
  clickable: (() -> Unit)? = null,
) {
  val localAppState = LocalNavigationParameters.current

  Row(
    modifier = if (clickable != null) {
      modifier
        .fillMaxWidth()
        .clip(roundedShape)
        .clickableSingle {
          localAppState.hideMenu()
          clickable()
        }
        .padding(vertical = 10.dp, horizontal = 12.dp)
    } else {
      modifier
        .fillMaxWidth()
        .clip(roundedShape)
        .padding(vertical = 10.dp, horizontal = 12.dp)
    },
    verticalAlignment = Alignment.CenterVertically,
  ) {
    icon?.let {
      Icon(
        imageVector = it,
        contentDescription = contentDescription,
        tint = MaterialTheme.colorScheme.onSurface,
        modifier = Modifier.padding(end = 12.dp),
      )
    }
    Column {
      Text(
        modifier = Modifier.padding(vertical = 6.dp),
        text = content,
        fontSize = 14.sp,
        color = MaterialTheme.colorScheme.onSurface,
      )
    }
  }
}

@Composable
internal fun MenuFooterItem(
  licenseHolder: String,
  modifier: Modifier = Modifier,
) {
  Column(
    modifier = modifier
      .fillMaxWidth(),
    horizontalAlignment = Alignment.CenterHorizontally,
  ) {
    val currentYear = Calendar.getInstance().get(Calendar.YEAR)
    Text(
      modifier = Modifier.padding(vertical = 0.dp),
      text = "${Symbols.COPYRIGHT} $currentYear $licenseHolder. All rights reserved.",
      fontSize = 10.sp,
      color = MaterialTheme.colorScheme.onSurface,
    )
    val versionContent = if (buildProfiler.isDebuggable) {
      "v${buildProfiler.versionName} [debug]"
    } else {
      "v${buildProfiler.versionName}"
    }
    Text(
      modifier = Modifier.padding(vertical = 0.dp),
      text = versionContent,
      fontSize = 10.sp,
      color = MaterialTheme.colorScheme.onSurface,
    )
  }
}

@Composable
internal fun MenuTopSurface(
  topRadius: Boolean,
  bottomRadius: Boolean,
  clickable: () -> Unit = {},
  content: @Composable () -> Unit,
) {
  val localAppState = LocalNavigationParameters.current
  val shape = when {
    topRadius && bottomRadius -> {
      roundedShape
    }

    topRadius -> {
      roundedShapeTop
    }

    bottomRadius -> {
      roundedShapeBottom
    }

    else -> {
      RectangleShape
    }
  }
  Surface(
    modifier = Modifier
      .fillMaxWidth()
      .clip(shape)
      .clickable {
        localAppState.hideMenu()
        clickable()
      },
    color = MaterialTheme.colorScheme.surfaceColorAtElevation(ElevationTokens.Level10),
    shape = shape,
  ) {
    content()
  }
}
