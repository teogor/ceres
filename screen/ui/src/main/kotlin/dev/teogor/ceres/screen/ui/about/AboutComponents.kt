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
import android.util.Base64
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyItemScope
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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.teogor.ceres.core.foundation.packageManagerUtils
import dev.teogor.ceres.core.register.BuildProfiler
import dev.teogor.ceres.core.register.LocalBuildProfiler
import dev.teogor.ceres.core.startup.ApplicationContextProvider
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
import dev.teogor.ceres.screen.ui.res.Resources
import dev.teogor.ceres.ui.designsystem.Surface
import dev.teogor.ceres.ui.designsystem.Text
import dev.teogor.ceres.ui.foundation.graphics.asImageVectorIcon
import dev.teogor.ceres.ui.theme.MaterialTheme
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

private val buildProfiler: BuildProfiler
  get() = LocalBuildProfiler.current

inline fun ScreenListScope.itemIf(
  predicate: Boolean,
  crossinline block: @Composable LazyItemScope.() -> Unit,
) {
  if (predicate) {
    item {
      block()
    }
  }
}

fun ScreenListScope.aboutOpenAppInfo() = item {
  val context = LocalContext.current
  val openSettingsLauncher = rememberLauncherForActivityResult(
    contract = ActivityResultContracts.StartActivityForResult(),
  ) { }

  HeaderSurface(
    title = Resources.AppInfo,
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
    title = Resources.VersionInfo,
  )
}

fun ScreenListScope.aboutAppVersion(
  version: String = buildProfiler.versionName,
) = item {
  SimpleView(
    title = Resources.AppVersion,
    subtitle = version,
    icon = Icons.Default.Info,
  )
}

fun ScreenListScope.aboutCeresFramework(
  ceresVersion: String? = buildProfiler.ceresBomVersion,
) = itemIf(ceresVersion != null) {
  val uriHandler = LocalUriHandler.current
  SimpleView(
    title = Resources.CeresFrameworkVersion,
    subtitle = ceresVersion!!,
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
          text = Resources.BuiltWithCeres,
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
  buildLocalDateTime: LocalDateTime = LocalBuildProfiler.current.buildLocalDateTime,
) {
  item {
    val formattedDate = buildLocalDateTime.format(
      DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM),
    )
    SimpleView(
      title = Resources.BuildDate,
      subtitle = formattedDate,
      icon = Icons.Default.DateRange,
    )
  }
}

fun ScreenListScope.aboutHeaderAboutUs() = item {
  HeaderView(
    title = Resources.AboutUs,
  )
}

fun ScreenListScope.aboutMadeIn(
  location: String,
) = item {
  SimpleView(
    title = Resources.MadeIn,
    subtitle = location,
    icon = Icons.Default.LocationOn,
  )
}

fun ScreenListScope.aboutHeaderSecurityPatch() = item {
  HeaderView(
    title = Resources.SecurityPatch,
  )
}

fun ScreenListScope.aboutBuildHash(
  hash: String? = buildProfiler.gitCommitHash,
) = itemIf(hash != null) {
  SimpleView(
    title = Resources.BuildHash,
    subtitle = hash,
    icon = Icons.Default.DomainVerification,
  )
}

fun getApkSignature(): String {
  val signatures = ApplicationContextProvider.context.packageManagerUtils().packageSignatures
  val signature = signatures.apkContentsSigners[0]
  val signatureBytes = signature.toByteArray()
  return Base64.encodeToString(signatureBytes, Base64.NO_WRAP)
}

fun ScreenListScope.aboutApkSignature(
  apkSignature: String = getApkSignature(),
) = item {
  SimpleView(
    title = Resources.ApkSignature,
    subtitle = apkSignature.substring(0, 40),
    icon = Icons.Default.Verified,
  )
}

fun ScreenListScope.aboutHeaderLicenses() = item {
  HeaderView(
    title = Resources.Licenses,
  )
}

fun ScreenListScope.aboutOpenSourceLicenses() = item {
  val navigation = LocalNavigationParameters.current
  SimpleView(
    title = Resources.OpenSourceLicenses,
    subtitle = Resources.LicenseDetailsForOpenSourceSoftware,
    icon = Icons.Default.Source,
    clickable = {
      navigation.screenRoute = AboutLibrariesScreenRoute
    },
  )
}
