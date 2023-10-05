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

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.teogor.ceres.screen.ui.api.ExperimentalOnboardingScreenApi
import dev.teogor.ceres.screen.ui.onboarding.OnboardingScreenData
import dev.teogor.ceres.ui.designsystem.Button
import dev.teogor.ceres.ui.designsystem.Text
import dev.teogor.ceres.ui.theme.MaterialTheme

@OptIn(ExperimentalOnboardingScreenApi::class)
@Composable
fun BoxScope.IntroScreen(
  data: OnboardingScreenData,
  onBack: () -> Unit,
  onNext: () -> Unit,
) {
  Column(
    modifier = Modifier
      .align(Alignment.TopCenter)
      .fillMaxWidth()
      .padding(horizontal = 20.dp),
    horizontalAlignment = Alignment.CenterHorizontally,
  ) {
    Text(
      text = "Welcome to",
      color = MaterialTheme.colorScheme.onBackground,
      textAlign = TextAlign.Center,
      fontSize = 30.sp,
      fontWeight = FontWeight.Bold,
      modifier = Modifier.padding(top = 50.dp),
    )

    Text(
      text = data.appName,
      color = MaterialTheme.colorScheme.primary,
      textAlign = TextAlign.Center,
      fontSize = 40.sp,
      fontWeight = FontWeight.ExtraBold,
      modifier = Modifier.padding(top = 0.dp),
    )
  }

  AnimatedVisibility(
    modifier = Modifier
      .align(Alignment.BottomCenter),
    visible = true,
    enter = fadeIn() + slideInVertically(
      initialOffsetY = { it },
      animationSpec = tween(durationMillis = 300),
    ),
    exit = slideOutVertically(
      targetOffsetY = { it },
      animationSpec = tween(durationMillis = 300),
    ) + fadeOut(),
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 20.dp),
      horizontalAlignment = Alignment.CenterHorizontally,
    ) {
      Text(
        text = data.description,
        color = MaterialTheme.colorScheme.secondary,
        fontSize = 16.sp,
        lineHeight = 18.sp,
        fontWeight = FontWeight.Normal,
        textAlign = TextAlign.Center,
        modifier = Modifier.padding(top = 10.dp, bottom = 20.dp),
      )

      Button(
        onClick = onNext,
        contentPadding = PaddingValues(
          horizontal = 24.dp,
          vertical = 16.dp,
        ),
        modifier = Modifier.padding(bottom = 30.dp),
      ) {
        Text(
          text = "Get started",
          modifier = Modifier.padding(end = 10.dp, start = 10.dp),
        )
      }
    }
  }
}
