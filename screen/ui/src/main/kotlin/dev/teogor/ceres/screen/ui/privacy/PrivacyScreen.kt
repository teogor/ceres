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

package dev.teogor.ceres.screen.ui.privacy

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Link
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.Person2
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.teogor.ceres.core.foundation.models.UserID
import dev.teogor.ceres.core.foundation.models.getPrivacyFormattedValue
import dev.teogor.ceres.core.foundation.ui.platform.LocalApplicationDetails
import dev.teogor.ceres.data.datastore.defaults.ceresPreferences
import dev.teogor.ceres.screen.builder.compose.HeaderView
import dev.teogor.ceres.screen.builder.compose.SimpleView
import dev.teogor.ceres.screen.core.scope.ScreenListScope
import dev.teogor.ceres.screen.ui.res.Resources
import dev.teogor.ceres.screen.ui.utils.resetConsentHandler
import dev.teogor.ceres.screen.ui.utils.resetOnboardingHandler
import dev.teogor.ceres.ui.designsystem.AlertDialog
import dev.teogor.ceres.ui.designsystem.Button
import dev.teogor.ceres.ui.designsystem.CeresButtonDefaults
import dev.teogor.ceres.ui.designsystem.CheckboxWithText
import dev.teogor.ceres.ui.designsystem.OutlinedButton
import dev.teogor.ceres.ui.designsystem.Text
import dev.teogor.ceres.ui.theme.MaterialTheme
import dev.teogor.ceres.ui.theme.toColor
import dev.teogor.ceres.ui.theme.tokens.ColorSchemeKeyTokens

fun ScreenListScope.privacyOptionsHeader() = item {
  HeaderView(
    title = Resources.PrivacyOptions,
  )
}

fun ScreenListScope.resetAdsConsentOption() = item {
  var dialogVisible by remember { mutableStateOf(false) }
  val resetConsentHandler = resetConsentHandler()
  SimpleView(
    title = Resources.ResetAdsConsent,
    subtitle = Resources.ResetAdsConsentSubtitle,
    icon = Icons.Default.Refresh,
    clickable = {
      dialogVisible = true
    },
  )

  if (dialogVisible) {
    AlertDialog(
      onDismissRequest = { dialogVisible = false },
      title = {
        Text(Resources.ResetAdsConsentDialogTitle)
      },
      text = {
        Column {
          Text(Resources.ResetAdsConsentDialogText)
        }
      },
      confirmButton = {
        Button(
          onClick = {
            resetConsentHandler()
            dialogVisible = false
          },
        ) {
          Text(Resources.Reset)
        }
      },
      dismissButton = {
        OutlinedButton(
          onClick = {
            dialogVisible = false
          },
          border = BorderStroke(
            width = CeresButtonDefaults.OutlinedButtonBorderWidth,
            color = ColorSchemeKeyTokens.Primary.toColor(),
          ),
        ) {
          Text(
            text = Resources.Cancel,
            color = ColorSchemeKeyTokens.Primary.toColor(),
          )
        }
      },
    )
  }
}

fun ScreenListScope.privacyUserId() = item {
  var showFullUserId by remember { mutableStateOf(false) }
  // todo auto-flow
  val userId = remember(ceresPreferences().userId) {
    ceresPreferences().userId
  }
  val formattedUserId by remember(showFullUserId) {
    mutableStateOf(if (showFullUserId) userId.value else userId.getPrivacyFormattedValue(6))
  }

  val clipboardManager = LocalClipboardManager.current
  SimpleView(
    title = Resources.UserId,
    subtitle = formattedUserId,
    icon = Icons.Default.Person2,
    clickable = {
      clipboardManager.setText(
        buildAnnotatedString {
          append(userId.value)
        },
      )
      // toast("User ID copied to clipboard!")
    },
    content = {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 20.dp),
      ) {
        Row(
          modifier = Modifier.align(Alignment.End),
        ) {
          OutlinedButton(
            modifier = Modifier
              .defaultMinSize(
                minWidth = 20.dp,
                minHeight = 10.dp,
              ),
            onClick = {
              showFullUserId = !showFullUserId
            },
          ) {
            Text(
              text = if (showFullUserId) Resources.ShowLess else Resources.ShowMore,
              modifier = Modifier.padding(horizontal = 2.dp),
            )
          }

          Spacer(modifier = Modifier.width(8.dp))

          Button(
            modifier = Modifier
              .defaultMinSize(
                minWidth = 20.dp,
                minHeight = 10.dp,
              ),
            onClick = {
              ceresPreferences().userId = UserID().apply {
                generate()
              }
            },
          ) {
            Text(
              text = Resources.Reset,
              modifier = Modifier.padding(horizontal = 2.dp),
            )
          }
        }
      }
    },
  )
}

fun ScreenListScope.privacyResetOnboarding() = item {
  var dialogVisible by remember { mutableStateOf(false) }
  var deleteContentChecked by remember { mutableStateOf(false) }
  val resetOnboardingHandler = resetOnboardingHandler()
  SimpleView(
    title = Resources.ResetOnboarding,
    subtitle = Resources.ResetOnboardingSubtitle,
    icon = Icons.Default.Refresh,
    clickable = {
      dialogVisible = true
    },
  )

  if (dialogVisible) {
    AlertDialog(
      onDismissRequest = { dialogVisible = false },
      title = {
        Text(Resources.ResetOnboardingDialogTitle)
      },
      text = {
        Column {
          Text(Resources.ResetOnboardingDialogText)
          Spacer(modifier = Modifier.height(8.dp))
          CheckboxWithText(
            text = Resources.ResetOnboardingDialogDeleteUserStoredContent,
            isChecked = deleteContentChecked,
            onCheckedChange = { isChecked ->
              deleteContentChecked = isChecked
            },
          )
          if (deleteContentChecked) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
              text = Resources.ThisActionCannotBeUndone,
              color = MaterialTheme.colorScheme.error,
              fontSize = 12.sp,
            )
          }
        }
      },
      confirmButton = {
        Button(
          onClick = {
            if (deleteContentChecked) {
              val ceresPreferences = ceresPreferences()
              ceresPreferences.name = ""
              ceresPreferences.coverImage = ""
              ceresPreferences.profileImage = ""
            }
            resetOnboardingHandler()
            dialogVisible = false
          },
        ) {
          Text(Resources.Restart)
        }
      },
      dismissButton = {
        OutlinedButton(
          onClick = {
            dialogVisible = false
          },
          border = BorderStroke(
            width = CeresButtonDefaults.OutlinedButtonBorderWidth,
            color = ColorSchemeKeyTokens.Primary.toColor(),
          ),
        ) {
          Text(
            text = Resources.Cancel,
            color = ColorSchemeKeyTokens.Primary.toColor(),
          )
        }
      },
    )
  }
}

fun ScreenListScope.legalAgreementsHeader() = item {
  HeaderView(
    title = Resources.LegalAgreementsHeader,
  )
}

fun ScreenListScope.privacyPolicyOption() = item {
  val uriHandler = LocalUriHandler.current
  val applicationDetails = LocalApplicationDetails.current
  SimpleView(
    title = Resources.PrivacyPolicy,
    subtitle = Resources.ReviewOurPrivacyPolicy,
    icon = Icons.Default.Link,
    clickable = {
      applicationDetails.privacyLink?.let {
        uriHandler.openUri(it)
      }
    },
  )
}

fun ScreenListScope.termsOfServiceOption() = item {
  val uriHandler = LocalUriHandler.current
  val applicationDetails = LocalApplicationDetails.current
  SimpleView(
    title = Resources.TermsOfService,
    subtitle = Resources.ReviewOurTermsOfService,
    icon = Icons.Default.Link,
    clickable = {
      applicationDetails.privacyLink?.let {
        uriHandler.openUri(it)
      }
    },
  )
}

fun ScreenListScope.policyCopyrightPolicy() = item {
  SimpleView(
    title = Resources.CopyrightPolicy,
    subtitle = Resources.ReviewOurCopyrightPolicy,
    icon = Icons.Filled.Description,
    clickable = {
    },
  )
}

fun ScreenListScope.policyRefundPolicy() = item {
  SimpleView(
    title = Resources.RefundPolicy,
    subtitle = Resources.ReviewOurRefundPolicy,
    icon = Icons.Filled.MonetizationOn,
    clickable = {
    },
  )
}

/**
 * Displays the app license policy.
 *
 * Supported licenses:
 * * Apache License 2.0
 * * GNU General Public License v3.0
 * * MIT License
 * * BSD 3-Clause License
 * * Creative Commons Zero v1.0 Universal License
 * * Mozilla Public License 2.0
 *
 * @param license The index of the license to display, from the
 * `licenses` string array.
 */
fun ScreenListScope.policyAppLicensePolicy(
  license: Int,
) = item {
  // TODO generate enum for string array ???
  SimpleView(
    title = Resources.AppLicense,
    subtitle = Resources.LicensedUnder(license = Resources.LicensesArray[license]),
    icon = Icons.Filled.Code,
    clickable = { },
  )
}
