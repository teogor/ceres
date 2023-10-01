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

package dev.teogor.ceres.screen.ui.userprefs

import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Save
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.teogor.ceres.data.datastore.defaults.ceresPreferences
import dev.teogor.ceres.framework.core.app.BaseActions
import dev.teogor.ceres.framework.core.app.setScreenInfo
import dev.teogor.ceres.framework.core.screen.action
import dev.teogor.ceres.framework.core.screen.floatingButton
import dev.teogor.ceres.framework.core.screen.icon
import dev.teogor.ceres.framework.core.screen.isStatusBarVisible
import dev.teogor.ceres.framework.core.screen.isVisible
import dev.teogor.ceres.framework.core.screen.showBackButton
import dev.teogor.ceres.framework.core.screen.showNavBar
import dev.teogor.ceres.framework.core.screen.toolbarTitle
import dev.teogor.ceres.framework.core.screen.toolbarTokens
import dev.teogor.ceres.navigation.core.utilities.toScreenName
import dev.teogor.ceres.screen.core.layout.ColumnLayoutBase
import dev.teogor.ceres.ui.designsystem.OutlinedTextField
import dev.teogor.ceres.ui.designsystem.Text
import dev.teogor.ceres.ui.designsystem.TrailingErrorIcon
import dev.teogor.ceres.ui.theme.MaterialTheme

@Composable
internal fun UserPrefRoute(
  baseActions: BaseActions,
) {
  val onBackPressedDispatcher = LocalOnBackPressedDispatcherOwner.current
  val ceresPreferences = remember {
    ceresPreferences()
  }

  var name by remember { mutableStateOf(ceresPreferences.name) }

  baseActions.setScreenInfo {
    showNavBar {
      false
    }

    isStatusBarVisible {
      true
    }

    toolbarTokens {
      isVisible {
        true
      }

      toolbarTitle {
        "Profile"
      }

      showBackButton {
        true
      }
    }

    floatingButton {
      isVisible {
        true
      }
      icon {
        Icons.Filled.Save
      }
      action {
        // save
        ceresPreferences.name = name.trim()

        // go back
        onBackPressedDispatcher?.onBackPressedDispatcher?.onBackPressed()
      }
    }
  }

  UserPrefScreen(
    name = name,
    onNameEdit = {
      name = it
    },
  )
}

@Composable
fun UserPrefScreen(
  name: String,
  onNameEdit: (String) -> Unit,
) = ColumnLayoutBase(
  hasScrollbar = false,
  screenName = UserPreferencesScreenRoute.toScreenName(),
) {
  OutlinedTextField(
    value = name,
    onValueChange = { onNameEdit(it) },
    label = { Text("Name") },
    keyboardOptions = KeyboardOptions(
      keyboardType = KeyboardType.Text,
      imeAction = ImeAction.Done,
    ),
    singleLine = true,
    modifier = Modifier
      .fillMaxWidth()
      .padding(horizontal = 16.dp, vertical = 8.dp),
    trailingIcon = {
      TrailingErrorIcon(
        modifier = Modifier
          .padding(end = 10.dp)
          .size(26.dp)
          .clip(CircleShape)
          .background(
            color = MaterialTheme.colorScheme.errorContainer,
            shape = CircleShape,
          ),
        imageVector = Icons.Default.Close,
        contentDescription = "Clear Text",
        iconTint = MaterialTheme.colorScheme.onErrorContainer,
        rule = {
          name.isNotEmpty()
        },
      ) {
        onNameEdit("")
      }
    },
  )

  val informationText =
    "Feel secure knowing that your name, cover image, and profile image are stored locally to enrich your in-app customization."
  Text(
    text = informationText,
    modifier = Modifier
      .fillMaxWidth()
      .padding(horizontal = 16.dp)
      .padding(top = 4.dp),
    fontSize = 10.sp,
    lineHeight = 10.sp,
    textAlign = TextAlign.Center,
  )
}
