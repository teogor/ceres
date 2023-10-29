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

package dev.teogor.ceres.screen.ui.about

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.OpenInNew
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.DomainVerification
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.PermDeviceInformation
import androidx.compose.material.icons.filled.Source
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Icon
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.teogor.ceres.core.runtime.AppMetadataManager
import dev.teogor.ceres.navigation.core.LocalNavigationParameters
import dev.teogor.ceres.screen.builder.compose.HeaderView
import dev.teogor.ceres.screen.builder.compose.SimpleView
import dev.teogor.ceres.screen.builder.compose.UiOptions
import dev.teogor.ceres.screen.builder.compose.UiTypes
import dev.teogor.ceres.screen.builder.compose.addExtraPadding
import dev.teogor.ceres.screen.builder.horizontalPadding
import dev.teogor.ceres.screen.builder.iconSize
import dev.teogor.ceres.screen.builder.verticalPadding
import dev.teogor.ceres.screen.core.scope.ScreenListScope
import dev.teogor.ceres.screen.ui.components.HeaderSurface
import dev.teogor.ceres.ui.designsystem.Surface
import dev.teogor.ceres.ui.designsystem.Text
import dev.teogor.ceres.ui.foundation.graphics.asImageVectorIcon
import dev.teogor.ceres.ui.theme.MaterialTheme
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

fun ScreenListScope.aboutOpenAppInfo() = item {
  val context = LocalContext.current
  val openSettingsLauncher = rememberLauncherForActivityResult(
    contract = ActivityResultContracts.StartActivityForResult(),
  ) { }

  HeaderSurface(
    title = "App Info",
    icon = Icons.AutoMirrored.Default.OpenInNew.asImageVectorIcon(),
    clickable = {
      val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
      val packageName = context.packageName
      val uri = Uri.fromParts("package", packageName, null)
      intent.data = uri
      openSettingsLauncher.launch(intent)
    },
  )
}

fun ScreenListScope.aboutHeaderVersion() = item {
  HeaderView(
    title = "Version Info",
  )
}

fun ScreenListScope.aboutAppVersion(
  version: String = AppMetadataManager.versionName,
) = item {
  SimpleView(
    title = "App version",
    subtitle = version,
    icon = Icons.Default.Info,
  )
}

fun ScreenListScope.aboutCeresFramework(
  ceresVersion: String = AppMetadataManager.ceresFrameworkVersion,
) = item {
  val uriHandler = LocalUriHandler.current
  SimpleView(
    title = "Ceres Framework version",
    subtitle = ceresVersion,
    icon = Icons.Default.PermDeviceInformation,
    clickable = {
      uriHandler.openUri("https://github.com/teogor/ceres")
    },
  ) {
    val hasFilledIconUI = UiOptions.uiTypes == UiTypes.Filled
    Surface(
      modifier = Modifier
        .fillMaxWidth()
        .padding(
          bottom = verticalPadding,
          start = horizontalPadding + addExtraPadding(hasFilledIconUI),
          end = horizontalPadding,
        ),
      afterModifier = Modifier
        .padding(
          horizontal = 10.dp,
          vertical = 4.dp,
        ),
      shape = RoundedCornerShape(16.dp),
      tonalElevation = 10.dp,
    ) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
      ) {
        Text(
          text = "This app is built using the \uD83E\uDE90 Ceres Framework",
          fontSize = 12.sp,
          lineHeight = 14.sp,
          modifier = Modifier.weight(1f),
        )
        Icon(
          imageVector = Icons.AutoMirrored.Filled.OpenInNew,
          contentDescription = "open github link",
          modifier = Modifier
            .size(iconSize),
          tint = MaterialTheme.colorScheme.secondary,
        )
      }
    }
  }
}

fun ScreenListScope.aboutBuildDate(
  buildDateTime: LocalDateTime = AppMetadataManager.buildDateTime,
) {
  item {
    val formattedDate = buildDateTime.format(
      DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM),
    )
    SimpleView(
      title = "Build date",
      subtitle = formattedDate,
      icon = Icons.Default.DateRange,
    )
  }
}

fun ScreenListScope.aboutHeaderAboutUs() = item {
  HeaderView(
    title = "About Us",
  )
}

fun ScreenListScope.aboutMadeIn() = item {
  SimpleView(
    title = "Made in",
    subtitle = "Brasov, Romania",
    icon = Icons.Default.LocationOn,
  )
}

fun ScreenListScope.aboutHeaderSecurityPatch() = item {
  HeaderView(
    title = "Security Patch",
  )
}

fun ScreenListScope.aboutBuildHash(
  hash: String = AppMetadataManager.gitHash,
) = item {
  SimpleView(
    title = "Build hash",
    subtitle = hash,
    icon = Icons.Default.DomainVerification,
  )
}

fun ScreenListScope.aboutApkSignature(
  apkSignature: String? = AppMetadataManager.apkSignature,
) {
  apkSignature?.let { signature ->
    item {
      SimpleView(
        title = "APK signature",
        subtitle = signature,
        icon = Icons.Default.Verified,
      )
    }
  }
}

fun ScreenListScope.aboutHeaderLicenses() = item {
  HeaderView(
    title = "Licenses",
  )
}

fun ScreenListScope.aboutOpenSourceLicenses() = item {
  val navigation = LocalNavigationParameters.current
  SimpleView(
    title = "Open-source licenses",
    subtitle = "License details for open-source software",
    icon = Icons.Default.Source,
    clickable = {
      navigation.screenRoute = AboutLibrariesScreenRoute
    },
  )
}
