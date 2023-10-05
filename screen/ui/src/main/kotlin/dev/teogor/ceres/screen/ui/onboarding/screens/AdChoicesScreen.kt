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
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.teogor.ceres.monetisation.ads.ExperimentalAdsControlApi
import dev.teogor.ceres.monetisation.ads.HandleAdsConsent
import dev.teogor.ceres.monetisation.ads.LocalAdsControl
import dev.teogor.ceres.monetisation.ads.consentIsShowing
import dev.teogor.ceres.screen.core.layout.LayoutWithBottomHeader
import dev.teogor.ceres.ui.designsystem.Button
import dev.teogor.ceres.ui.designsystem.HorizontalDivider
import dev.teogor.ceres.ui.designsystem.OutlinedButton
import dev.teogor.ceres.ui.designsystem.Text
import dev.teogor.ceres.ui.theme.MaterialTheme

@OptIn(ExperimentalFoundationApi::class, ExperimentalAdsControlApi::class)
@Composable
fun BoxScope.AdChoicesScreen(
  onBack: () -> Unit,
  onNext: () -> Unit,
) {
  val adsControl = LocalAdsControl.current
  val canRequestAds by remember { adsControl.canRequestAds }
  HandleAdsConsent(
    onAdsConsentGranted = onNext,
    onAdsConsentRejected = onNext,
  )

  LayoutWithBottomHeader(
    hasScrollbar = false,
    bottomContent = {
      Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
      ) {
        if (!adsControl.consentIsShowing && !canRequestAds) {
          LinearProgressIndicator(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.primary,
          )
        } else {
          HorizontalDivider(
            modifier = Modifier.padding(bottom = 10.dp),
            thickness = 1.dp,
          )
        }

        Spacer(modifier = Modifier.height(15.dp))

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
            enabled = canRequestAds,
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
    stickyHeader {
      HorizontalDivider(
        thickness = 1.dp,
      )
    }
  }
}
