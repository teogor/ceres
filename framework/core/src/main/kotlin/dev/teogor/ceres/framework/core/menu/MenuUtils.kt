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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.components.rememberImageComponent
import com.skydoves.landscapist.glide.GlideImage
import com.skydoves.landscapist.placeholder.thumbnail.ThumbnailPlugin
import dev.teogor.ceres.core.foundation.models.getPrivacyFormattedValue
import dev.teogor.ceres.data.compose.rememberPreference
import dev.teogor.ceres.data.datastore.defaults.ceresPreferences
import dev.teogor.ceres.ui.designsystem.Text
import dev.teogor.ceres.ui.theme.MaterialTheme

fun MutableList<MenuItem>.menuUserData(
  modifier: Modifier = Modifier,
  clickable: () -> Unit = {},
  textIfEmpty: () -> String = { "User Name" },
) {
  menuItem(
    clickable = clickable,
  ) {
    Column(
      modifier = modifier
        .padding(vertical = 16.dp, horizontal = 12.dp),
    ) {
      val ceresPreferences = remember {
        ceresPreferences()
      }
      val name = rememberPreference(
        ceresPreferences.getNameFlow(),
        ceresPreferences.name,
      )
      val profileImage = rememberPreference(
        ceresPreferences.getProfileImageFlow(),
        ceresPreferences.profileImage,
      )

      Row(
        verticalAlignment = Alignment.CenterVertically,
      ) {
        if (profileImage.isNotEmpty()) {
          GlideImage(
            imageModel = { profileImage },
            imageOptions = ImageOptions(
              contentScale = ContentScale.Inside,
              alignment = Alignment.Center,
            ),
            component = rememberImageComponent {
              +ThumbnailPlugin()
            },
            modifier = Modifier
              .padding(end = 10.dp)
              .size(40.dp)
              .clip(CircleShape),
          )
        }
        Column {
          Text(
            text = "Welcome,",
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onSurface,
          )
          Text(
            text = name.ifEmpty {
              textIfEmpty()
            },
            fontSize = 18.sp,
            modifier = Modifier
              .padding(start = 2.dp),
            color = MaterialTheme.colorScheme.onSurface,
          )
        }
      }
    }
  }
}

fun MutableList<MenuItem>.menuUserId(
  modifier: Modifier = Modifier,
) {
  menuItem {
    Column(
      modifier = modifier
        .fillMaxWidth(),
      horizontalAlignment = Alignment.CenterHorizontally,
    ) {
      val ceresPreferences = remember {
        ceresPreferences()
      }
      val userId = ceresPreferences.userId
      Text(
        modifier = Modifier.padding(vertical = 0.dp),
        text = "ID-${userId.getPrivacyFormattedValue(6)}",
        fontSize = 10.sp,
        lineHeight = 10.sp,
        textAlign = TextAlign.Center,
        color = MaterialTheme.colorScheme.onSurface,
      )
    }
  }
}
