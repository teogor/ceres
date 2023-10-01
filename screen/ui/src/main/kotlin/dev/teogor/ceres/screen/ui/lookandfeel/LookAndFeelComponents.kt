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

package dev.teogor.ceres.screen.ui.lookandfeel

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ContentAlpha
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Audiotrack
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.ColorLens
import androidx.compose.material.icons.filled.InvertColors
import androidx.compose.material.icons.filled.Style
import androidx.compose.material.icons.filled.Vibration
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import dev.teogor.ceres.data.compose.rememberPreference
import dev.teogor.ceres.data.datastore.defaults.AppTheme
import dev.teogor.ceres.data.datastore.defaults.JustBlackTheme
import dev.teogor.ceres.data.datastore.defaults.ThemeConfig
import dev.teogor.ceres.data.datastore.defaults.ceresPreferences
import dev.teogor.ceres.screen.builder.compose.HeaderView
import dev.teogor.ceres.screen.builder.compose.SimpleView
import dev.teogor.ceres.screen.builder.compose.SwitchView
import dev.teogor.ceres.screen.core.scope.ScreenListScope
import dev.teogor.ceres.ui.designsystem.SegmentedButtons
import dev.teogor.ceres.ui.designsystem.VerticalDivider
import dev.teogor.ceres.ui.foundation.clickable
import dev.teogor.ceres.ui.spectrum.model.ColorInfo
import dev.teogor.ceres.ui.spectrum.palettes.TonalPalette
import dev.teogor.ceres.ui.spectrum.utilities.asHexColor
import dev.teogor.ceres.ui.theme.MaterialTheme
import dev.teogor.ceres.ui.theme.supportsDynamicTheming
import dev.teogor.ceres.ui.theme.tokens.ColorSchemeKeyTokens

fun ScreenListScope.lookAndFeelHeaderAppearance() = item {
  HeaderView(
    title = "Appearance",
  )
}

fun ScreenListScope.lookAndFeelAppTheme() = item {
  val ceresPreferences = remember {
    ceresPreferences()
  }
  val appThemeFlow = rememberPreference(
    preferenceFlow = ceresPreferences.getAppThemeFlow(),
    initialValue = ceresPreferences.appTheme,
  )
  val options = listOf("Auto", "Light", "Dark")
  var selectedOption by remember {
    mutableIntStateOf(
      when (appThemeFlow) {
        AppTheme.FollowSystem -> 0
        AppTheme.ClearlyWhite -> 1
        AppTheme.KindaDark -> 2
      },
    )
  }

  SimpleView(
    title = "App theme",
    subtitle = "Try another look",
    icon = Icons.Default.Style,
    columnContent = {
      SegmentedButtons(
        modifier = Modifier.padding(top = 10.dp, end = 20.dp),
        options = options,
        selectedOption = selectedOption,
        onOptionSelected = { option ->
          selectedOption = option
          val appTheme = when (option) {
            0 -> AppTheme.FollowSystem
            1 -> AppTheme.ClearlyWhite
            2 -> AppTheme.KindaDark
            else -> AppTheme.FollowSystem
          }
          ceresPreferences.appTheme = appTheme
        },
      )
    },
  )
}

fun ScreenListScope.lookAndFeelDynamicTheming() {
  if (supportsDynamicTheming()) {
    item {
      val ceresPreferences = remember {
        ceresPreferences()
      }
      val disableDynamicTheming = rememberPreference(
        preferenceFlow = ceresPreferences.getDisableDynamicThemingFlow(),
        initialValue = ceresPreferences.disableDynamicTheming,
      )
      SwitchView(
        title = "Dynamic Theming",
        subtitle = "Turn Off for more color options",
        icon = Icons.Default.AutoAwesome,
        switchToggled = !disableDynamicTheming,
      ) { isToggled ->
        ceresPreferences.disableDynamicTheming = !isToggled
      }
    }
  }
}

fun ScreenListScope.lookAndFeelColorTheme() {
  item {
    // todo
    val ceresPreferences = remember {
      ceresPreferences()
    }
    val disableDynamicTheming = rememberPreference(
      preferenceFlow = ceresPreferences.getDisableDynamicThemingFlow(),
      initialValue = ceresPreferences.disableDynamicTheming,
    )
    if (disableDynamicTheming) {
      SimpleView(
        title = "App color theme",
        subtitle = "Try another color",
        icon = Icons.Default.ColorLens,
        columnContent = {
          // AnimatedVisibility(
          //   visible = ceresPreferences.disableDynamicTheming,
          //   enter = slideInVertically(initialOffsetY = { it }),
          //   exit = slideOutVertically(targetOffsetY = { it })
          // ) {
          val alphaDisabled = ContentAlpha.disabled
          Row(
            modifier = Modifier
              .horizontalScroll(rememberScrollState())
              .padding(top = 8.dp)
              .pointerInput(Unit) {
                detectTapGestures(
                  onDoubleTap = {
                    if (ceresPreferences.disableDynamicTheming) {
                      return@detectTapGestures
                    }
                  },
                  onLongPress = {
                    if (ceresPreferences.disableDynamicTheming) {
                      return@detectTapGestures
                    }
                  },
                  onPress = {
                    if (ceresPreferences.disableDynamicTheming) {
                      return@detectTapGestures
                    }
                  },
                  onTap = {
                    if (ceresPreferences.disableDynamicTheming) {
                      return@detectTapGestures
                    }
                  },
                )
              }
              .graphicsLayer {
                this.alpha =
                  if (!ceresPreferences.disableDynamicTheming) alphaDisabled else 1f
                compositingStrategy = CompositingStrategy.Offscreen
              },
            verticalAlignment = Alignment.CenterVertically,
          ) {
            val darkMode = MaterialTheme.isDarkMode
            val rememberDarkMode = remember(darkMode) {
              darkMode
            }

            fun Color.getColor(): Color {
              val tone = if (rememberDarkMode) 30 else 85
              val colorInfo = ColorInfo.from(this)
              val tonalPalette = TonalPalette.fromHueAndChroma(
                hue = colorInfo.hue,
                chroma = colorInfo.chroma,
              )
              return tonalPalette.tone(tone)
            }

            val materialColors = listOf(
              Color(ThemeConfig.seedHex.toColorInt()),
              Color(0xFFF44336),
              Color(0xFFE91E63),
              Color(0xFF9C27B0),
              Color(0xFF3F51B5),
              Color(0xFF2196F3),
              Color(0xFF00BCD4),
              Color(0xFF009688),
              Color(0xFF4CAF50),
              Color(0xFF8BC34A),
              Color(0xFFCDDC39),
              Color(0xFFFF9800),
              Color(0xFF795548),
            )

            val materialColorsThemeAware = materialColors.map {
              it.getColor()
            }

            var selectedThemeIndex = materialColors.indexOf(
              Color(ceresPreferences().themeSeed.toColorInt()),
            )
            if (selectedThemeIndex == -1) {
              selectedThemeIndex = 0
            }
            materialColorsThemeAware.forEachIndexed { index, color ->
              Box(
                modifier = Modifier
                  .padding(horizontal = 2.dp)
                  .size(90.dp)
                  .background(
                    color = MaterialTheme.colorScheme.secondaryContainer,
                    shape = RoundedCornerShape(20.dp),
                  )
                  .clip(RoundedCornerShape(20.dp))
                  .clickable {
                    ceresPreferences().themeSeed =
                      materialColors[index].asHexColor()
                  }
                  .padding(if (selectedThemeIndex == index) 28.dp else 18.dp),
              ) {
                Box(
                  modifier = Modifier
                    .fillMaxSize()
                    .background(
                      color = color,
                      shape = CircleShape,
                    )
                    .border(
                      width = 1.5.dp,
                      color = MaterialTheme.colorScheme.outline,
                      shape = CircleShape,
                    ),
                )
              }
              if (index == 0) {
                VerticalDivider(
                  modifier = Modifier
                    .height(54.dp)
                    .padding(horizontal = 8.dp),
                  thickness = 1.dp,
                  color = MaterialTheme.colorScheme.outline,
                )
              }
            }
          }
          // }
        },
      ) {
      }
    }
  }
}

fun ScreenListScope.lookAndFeelJustBlack() = item {
  val ceresPreferences = remember {
    ceresPreferences()
  }
  val justBlack = rememberPreference(
    preferenceFlow = ceresPreferences.getJustBlackThemeFlow(),
    initialValue = ceresPreferences.justBlack,
  )
  val options = listOf("On", "Off", "Auto")
  var selectedOption by remember {
    mutableIntStateOf(
      when (justBlack) {
        JustBlackTheme.Off -> 1
        JustBlackTheme.AlwaysOn -> 0
        JustBlackTheme.FollowSystem -> 2
        else -> 1
      },
    )
  }

  SimpleView(
    title = "Just Black",
    subtitle = "Changing it from 'Off' will prioritize this over the App theme.",
    subtitleColor = ColorSchemeKeyTokens.Error,
    icon = Icons.Default.InvertColors,
    columnContent = {
      SegmentedButtons(
        modifier = Modifier.padding(top = 10.dp, end = 20.dp),
        options = options,
        selectedOption = selectedOption,
        onOptionSelected = { option ->
          selectedOption = option
          val justBlackTheme = when (option) {
            0 -> JustBlackTheme.AlwaysOn
            1 -> JustBlackTheme.Off
            2 -> JustBlackTheme.FollowSystem
            else -> JustBlackTheme.FollowSystem
          }
          ceresPreferences.justBlack = justBlackTheme
        },
      )
    },
  )
}

fun ScreenListScope.lookAndFeelHeaderFeedback() = item {
  HeaderView(
    title = "Feedback",
  )
}

fun ScreenListScope.lookAndFeelSoundFeedback() = item {
  val ceresPreferences = remember {
    ceresPreferences()
  }
  val disableSoundFeedback = rememberPreference(
    preferenceFlow = ceresPreferences.getDisableSoundFeedbackFlow(),
    initialValue = ceresPreferences.disableSoundFeedback,
  )
  SwitchView(
    title = "Sound Feedback",
    subtitle = "Toggle Off for no sounds",
    icon = Icons.Default.Audiotrack,
    switchToggled = !disableSoundFeedback,
  ) { isToggled ->
    ceresPreferences.disableSoundFeedback = !isToggled
  }
}

fun ScreenListScope.lookAndFeelVibrationFeedback() = item {
  val ceresPreferences = remember {
    ceresPreferences()
  }
  val disableVibrationFeedback = rememberPreference(
    preferenceFlow = ceresPreferences.getDisableVibrationFeedbackFlow(),
    initialValue = ceresPreferences.disableVibrationFeedback,
  )
  SwitchView(
    title = "Vibration Feedback",
    subtitle = "Toggle Off for no vibrations",
    icon = Icons.Default.Vibration,
    switchToggled = !disableVibrationFeedback,
  ) { isToggled ->
    ceresPreferences.disableVibrationFeedback = !isToggled
  }
}
