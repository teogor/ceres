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

package dev.teogor.ceres.screen.ui.onboarding.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.teogor.ceres.screen.core.layout.LayoutWithBottomHeader
import dev.teogor.ceres.screen.core.scope.ScreenListScope
import dev.teogor.ceres.screen.ui.api.ExperimentalOnboardingScreenApi
import dev.teogor.ceres.screen.ui.onboarding.OnboardingScreenData
import dev.teogor.ceres.ui.designsystem.Button
import dev.teogor.ceres.ui.designsystem.CheckboxWithText
import dev.teogor.ceres.ui.designsystem.ClickableElement
import dev.teogor.ceres.ui.designsystem.HorizontalDivider
import dev.teogor.ceres.ui.designsystem.OutlinedButton
import dev.teogor.ceres.ui.designsystem.ParsableText
import dev.teogor.ceres.ui.designsystem.Text
import dev.teogor.ceres.ui.foundation.clickable
import dev.teogor.ceres.ui.theme.MaterialTheme
import dev.teogor.ceres.ui.theme.core.SurfaceOverlay

@OptIn(ExperimentalFoundationApi::class, ExperimentalOnboardingScreenApi::class)
@Composable
fun BoxScope.LegalScreen(
  data: OnboardingScreenData,
  onBack: () -> Unit,
  onNext: () -> Unit,
) = LayoutWithBottomHeader(
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

      var agreedPp by rememberSaveable { mutableStateOf(false) }
      var agreedTos by rememberSaveable { mutableStateOf(false) }

      CheckboxWithText(
        text = "I agree with Privacy Policy",
        isChecked = agreedPp,
        onCheckedChange = { agreedPp = it },
        modifier = Modifier
          .padding(horizontal = 10.dp),
      )
      CheckboxWithText(
        text = "I agree with Terms of Service",
        isChecked = agreedTos,
        onCheckedChange = { agreedTos = it },
        modifier = Modifier
          .padding(horizontal = 10.dp),
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
          enabled = agreedTos && agreedPp,
        ) {
          Text(
            text = "Accept",
            modifier = Modifier
              .padding(end = 10.dp, start = 10.dp),
          )
        }
      }

      Spacer(modifier = Modifier.height(8.dp))
    }
  },
) {
  legalTitle()

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

  header("Data Collection")

  item {
    Text(
      text = "Our commitment to privacy includes the responsible collection and handling of your personal data. Here, we detail the various types of information we gather, how we utilize it, and the rights and choices available to you.",
      color = MaterialTheme.colorScheme.onBackground,
      fontSize = 12.sp,
      lineHeight = 14.sp,
      textAlign = TextAlign.Start,
      modifier = Modifier
        .padding(horizontal = 14.dp)
        .padding(top = 10.dp, bottom = 4.dp),
    )
  }

  item {
    Spacer(modifier = Modifier.height(10.dp))
  }

  header("Data Collection Details")

  item {
    StyledInfoSection(
      title = "Types of Personal Data We Collect",
      items = listOf(
        "Information you provide when you register for an account or use our services, such as your name.",
        "Information we collect automatically, such as your IP address, device type.",
        "Information we collect from third-party sources, such as social media platforms or advertising partners.",
      ),
    )
  }

  header("Data Utilization Overview")

  item {
    StyledInfoSection(
      title = "How We Use Your Personal Data",
      items = listOf(
        "To provide and improve our services, personalize your experience, and communicate with you.",
        "To analyze and improve our products and services, as well as to develop new features and offerings.",
        "To show you targeted advertising and measure the effectiveness of our advertising campaigns.",
        "To comply with legal and regulatory requirements.",
      ),
    )
  }

  header("Your Privacy Rights")

  item {
    StyledInfoSection(
      title = "Your Rights and Choices",
      items = listOf(
        "You have the right to access, correct, or delete your personal data.",
        "You can opt-out of receiving marketing communications from us at any time.",
        "You can choose to disable cookies in your browser menu.",
        "We honor 'Do Not Track' signals and offer opt-out mechanisms for certain types of data processing.",
      ),
    )
  }

  header("Data Security Measures")

  item {
    StyledInfoSection(
      title = "How We Protect Your Personal Data",
      items = listOf(
        "We use industry-standard security measures to protect your personal data.",
        "We limit access to your personal data to authorized personnel only.",
        "We regularly review and update our security practices to ensure they meet the highest standards.",
      ),
    )
  }

  header("Contact and Agreement")

  item {
    StyledInfoSection(
      title = "Contact Us",
      items = listOf(
        "If you have any questions or concerns about our privacy practices, please contact us at ${data.supportEmail}.",
      ),
      highlights = listOf(data.supportEmail),
    )

    val uriHandler = LocalUriHandler.current
    ParsableText(
      text = "By using this app, you agree to our Privacy Policy and Terms of Service.",
      elements = listOf(
        PrivacyPolicy,
        TermsOfService,
      ),
      onElementClicked = { element ->
        when (element) {
          is PrivacyPolicy -> {
            val url = data.termsOfServiceLink
            uriHandler.openUri(url)
          }

          is TermsOfService -> {
            val url = data.privacyPolicyLink
            uriHandler.openUri(url)
          }
        }
      },
      modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp),
    )
  }
}

object PrivacyPolicy : ClickableElement("Privacy Policy")
object TermsOfService : ClickableElement("Terms of Service")

fun ScreenListScope.header(
  title: String,
  modifier: Modifier = Modifier,
) = item {
  Text(
    modifier = modifier
      .padding(
        top = 6.dp,
        bottom = 0.dp,
      )
      .padding(horizontal = 30.dp),
    text = title,
    fontSize = 13.sp,
    fontWeight = FontWeight.Medium,
    lineHeight = 13.sp,
    color = MaterialTheme.colorScheme.secondary,
  )
}

fun ScreenListScope.legalTitle() = item {
  val text = buildAnnotatedString {
    append("Your ")
    withStyle(
      style = SpanStyle(
        color = MaterialTheme.colorScheme.primary,
        fontWeight = FontWeight.Medium,
      ),
    ) {
      append("Privacy")
    }
    append("\nMatters to Us")
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

fun ScreenListScope.divider(
  modifier: Modifier = Modifier,
) = item {
  HorizontalDivider(
    modifier = modifier,
    thickness = 1.dp,
  )
}

@Composable
private fun StyledInfoSection(
  title: String,
  items: List<String>,
  highlights: List<String> = emptyList(),
  titleColor: Color = MaterialTheme.colorScheme.onSurface,
) {
  val colorScheme = MaterialTheme.colorScheme
  val surfaceOverlay = SurfaceOverlay(
    themeOverlayColor = colorScheme.surface.toArgb(),
    themeSurfaceColor = colorScheme.primary.toArgb(),
  )
  val background = surfaceOverlay.forAlpha(overlayAlpha = 0.12f)
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .padding(vertical = 8.dp, horizontal = 20.dp)
      .clip(shape = RoundedCornerShape(10.dp))
      .background(background)
      .clickable(
        onClick = {
        },
        indication = rememberRipple(
          color = colorScheme.primary,
        ),
        interactionSource = remember { MutableInteractionSource() },
      )
      .padding(4.dp),
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 10.dp)
        .align(Alignment.CenterVertically),
    ) {
      Text(
        text = title,
        color = titleColor,
        fontSize = 14.sp,
        modifier = Modifier.padding(vertical = 4.dp),
      )
      items.forEach { item ->
        val annotatedString = buildAnnotatedString {
          val parts = item.split(*highlights.map { it }.toTypedArray())
          var i = 0
          parts.forEachIndexed { index, part ->
            append(part)
            i += part.length
            if (index != parts.lastIndex) {
              val element = highlights[index]
              pushStringAnnotation(tag = element, annotation = "")
              withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.tertiary)) {
                append(element)
              }
              pop()
              i += element.length
            }
          }
        }

        Row(
          verticalAlignment = Alignment.CenterVertically,
          modifier = Modifier.padding(vertical = 6.dp),
        ) {
          Text(
            text = annotatedString,
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.bodySmall,
          )
        }
      }
    }
  }
}
