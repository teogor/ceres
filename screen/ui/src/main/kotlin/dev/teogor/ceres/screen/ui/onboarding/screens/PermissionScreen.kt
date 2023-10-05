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

@file:OptIn(ExperimentalPermissionsApi::class)

package dev.teogor.ceres.screen.ui.onboarding.screens

import android.content.Context
import android.content.pm.PackageManager
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Security
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import dev.teogor.ceres.screen.builder.compose.SimpleView
import dev.teogor.ceres.screen.core.layout.LayoutWithBottomHeader
import dev.teogor.ceres.screen.core.scope.ScreenListScope
import dev.teogor.ceres.screen.ui.api.ExperimentalOnboardingScreenApi
import dev.teogor.ceres.screen.ui.onboarding.OnboardingScreenData
import dev.teogor.ceres.screen.ui.onboarding.Permission
import dev.teogor.ceres.ui.designsystem.Button
import dev.teogor.ceres.ui.designsystem.HorizontalDivider
import dev.teogor.ceres.ui.designsystem.OutlinedButton
import dev.teogor.ceres.ui.designsystem.Surface
import dev.teogor.ceres.ui.designsystem.Text
import dev.teogor.ceres.ui.theme.MaterialTheme
import dev.teogor.ceres.ui.theme.contentColorFor

fun Context.hasPermissions(
  permission: String,
): Boolean {
  return ContextCompat.checkSelfPermission(
    this,
    permission,
  ) == PackageManager.PERMISSION_GRANTED
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalOnboardingScreenApi::class)
@Composable
fun BoxScope.PermissionScreen(
  data: OnboardingScreenData,
  onBack: () -> Unit,
  onNext: () -> Unit,
) {
  val context = LocalContext.current
  var allPermissionsGranted by remember {
    mutableStateOf(data.areAllPermissionsGranted())
  }
  LaunchedEffect(Unit) {
    data.permissionCategories.forEach { permissionCategory ->
      permissionCategory.permissions.forEach { permission ->
        permission.isGranted = context.hasPermissions(permission.manifestPermission)
      }
    }
    allPermissionsGranted = data.areAllPermissionsGranted()
  }

  LayoutWithBottomHeader(
    hasScrollbar = false,
    bottomContent = {
      Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
      ) {
        HorizontalDivider(
          modifier = Modifier.padding(bottom = 10.dp),
          thickness = 1.dp,
        )

        Spacer(modifier = Modifier.height(4.dp))

        Row(
          modifier = Modifier.padding(horizontal = 20.dp),
        ) {
          OutlinedButton(
            onClick = onBack,
          ) {
            Text(
              text = "Back",
              modifier = Modifier
                .padding(end = 10.dp, start = 10.dp),
            )
          }
          Spacer(modifier = Modifier.weight(1f))
          Button(
            onClick = onNext,
            enabled = allPermissionsGranted,
          ) {
            Text(
              text = "Next",
              modifier = Modifier
                .padding(end = 10.dp, start = 10.dp),
            )
          }
        }

        Spacer(modifier = Modifier.height(8.dp))
      }
    },
  ) {
    title()

    item {
      Spacer(modifier = Modifier.height(6.dp))
    }
    stickyHeader {
      HorizontalDivider(
        thickness = 1.dp,
      )
    }
    item {
      Spacer(modifier = Modifier.height(10.dp))
    }

    data.permissionCategories.forEach { permissionCategory ->
      item {
        Spacer(modifier = Modifier.height(10.dp))
      }

      header(permissionCategory.name)

      permissionCategory.permissions.forEach { permission ->
        permission(
          permission = permission,
          onGranted = {
            allPermissionsGranted = data.areAllPermissionsGranted()
          },
        )
      }
    }

    item {
      Spacer(modifier = Modifier.height(20.dp))
    }
  }
}

@OptIn(ExperimentalPermissionsApi::class, ExperimentalOnboardingScreenApi::class)
fun ScreenListScope.permission(
  permission: Permission,
  onGranted: () -> Unit,
) = item {
  val permissionState = rememberPermissionState(
    permission.manifestPermission,
  )
  LaunchedEffect(permissionState.status.isGranted) {
    permission.isGranted = permissionState.status.isGranted
    onGranted()
  }
  SimpleView(
    title = permission.title,
    subtitle = permission.description,
    clickable = {
      if (!permissionState.status.isGranted) {
        permissionState.launchPermissionRequest()
      }
    },
    icon = Icons.Filled.Security,
  ) {
    val backgroundColor = if (permissionState.status.isGranted) {
      MaterialTheme.colorScheme.success
    } else if (permissionState.status.shouldShowRationale) {
      MaterialTheme.colorScheme.error
    } else {
      MaterialTheme.colorScheme.warning
    }
    Surface(
      modifier = Modifier
        .fillMaxWidth()
        .padding(
          horizontal = 40.dp,
        )
        .padding(bottom = 4.dp),
      afterModifier = Modifier
        .padding(
          horizontal = 10.dp,
          vertical = 6.dp,
        ),
      color = backgroundColor,
      shape = RoundedCornerShape(16.dp),
      tonalElevation = 10.dp,
    ) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
      ) {
        if (permissionState.status.isGranted) {
          Text(
            text = "Permission Granted",
            fontSize = 12.sp,
            lineHeight = 14.sp,
            color = contentColorFor(backgroundColor),
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
          )
        } else {
          val textToShow = if (permissionState.status.shouldShowRationale) {
            // If the user has denied the permission but the rationale can be shown,
            // then gently explain why the app requires this permission
            "The camera is important for this app. Please grant the permission."
          } else {
            // If it's the first time the user lands on this feature, or the user
            // doesn't want to be asked again for this permission, explain that the
            // permission is required
            "Not Granted"
          }
          Text(
            text = textToShow,
            fontSize = 12.sp,
            lineHeight = 14.sp,
            color = contentColorFor(backgroundColor),
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
          )
        }
      }
    }
  }
}

private fun ScreenListScope.title() = item {
  val text = buildAnnotatedString {
    append("Grant ")
    withStyle(
      style = SpanStyle(
        color = MaterialTheme.colorScheme.primary,
        fontWeight = FontWeight.Medium,
      ),
    ) {
      append("Permissions")
    }
  }

  Text(
    text = text,
    color = MaterialTheme.colorScheme.onBackground,
    textAlign = TextAlign.Center,
    fontSize = 22.sp,
    lineHeight = 24.sp,
    fontWeight = FontWeight.Normal,
    modifier = Modifier
      .fillMaxWidth()
      .padding(top = 30.dp, bottom = 10.dp),
  )
}
