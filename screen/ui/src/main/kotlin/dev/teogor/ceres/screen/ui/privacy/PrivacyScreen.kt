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
    title = "Privacy Options",
  )
}

fun ScreenListScope.resetAdsConsentOption() = item {
  var dialogVisible by remember { mutableStateOf(false) }
  val resetConsentHandler = resetConsentHandler()
  SimpleView(
    title = "Reset Ads Consent",
    subtitle = "Reset your preferences for personalized ads",
    icon = Icons.Default.Refresh,
    clickable = {
      dialogVisible = true
    },
  )

  if (dialogVisible) {
    AlertDialog(
      onDismissRequest = { dialogVisible = false },
      title = {
        Text("Reset Ads Consent")
      },
      text = {
        Column {
          Text("Are you sure you want to reset your personalized ads preferences?")
        }
      },
      confirmButton = {
        Button(
          onClick = {
            resetConsentHandler()
            dialogVisible = false
          },
        ) {
          Text("Reset")
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
            text = "Cancel",
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
    title = "User ID",
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
              text = if (showFullUserId) "Show Less" else "Show More",
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
              text = "Reset",
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
    title = "Reset Onboarding",
    subtitle = "Start the onboarding process again",
    icon = Icons.Default.Refresh,
    clickable = {
      dialogVisible = true
    },
  )

  if (dialogVisible) {
    AlertDialog(
      onDismissRequest = { dialogVisible = false },
      title = {
        Text("Restart Onboarding Process")
      },
      text = {
        Column {
          Text("Are you sure you want to clear all onboarding progress and start over?")
          Spacer(modifier = Modifier.height(8.dp))
          CheckboxWithText(
            text = "Delete user-stored content",
            isChecked = deleteContentChecked,
            onCheckedChange = { isChecked ->
              deleteContentChecked = isChecked
            },
          )
          if (deleteContentChecked) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
              text = "This action cannot be undone.",
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
          Text("Restart")
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
            text = "Cancel",
            color = ColorSchemeKeyTokens.Primary.toColor(),
          )
        }
      },
    )
  }
}

fun ScreenListScope.legalAgreementsHeader() = item {
  HeaderView(
    title = "Legal Agreements",
  )
}

fun ScreenListScope.privacyPolicyOption() = item {
  val uriHandler = LocalUriHandler.current
  val applicationDetails = LocalApplicationDetails.current
  SimpleView(
    title = "Privacy Policy",
    subtitle = "Review our Privacy Policy",
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
    title = "Terms of Service",
    subtitle = "Review our Terms of Service",
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
    title = "Copyright Policy",
    subtitle = "Review our Copyright Policy",
    icon = Icons.Filled.Description,
    clickable = {
    },
  )
}

fun ScreenListScope.policyRefundPolicy() = item {
  SimpleView(
    title = "Refund Policy",
    subtitle = "Review our Refund Policy",
    icon = Icons.Filled.MonetizationOn,
    clickable = {
    },
  )
}

fun ScreenListScope.policyAppLicensePolicy() = item {
  SimpleView(
    title = "App License",
    subtitle = "Licensed under Apache License 2.0",
    icon = Icons.Filled.Code,
  )
}
