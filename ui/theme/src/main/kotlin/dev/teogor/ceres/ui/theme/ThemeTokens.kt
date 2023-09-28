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

package dev.teogor.ceres.ui.theme

import android.os.Build
import androidx.annotation.ChecksSdkIntAtLeast
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import dev.teogor.ceres.ui.spectrum.utilities.asColor

data class DynamicColorTokens(
  val primary: Color = Color.Unspecified,
  val secondary: Color = Color.Unspecified,
  val tertiary: Color = Color.Unspecified,
)

data class ThemeTokens(
  val seed: Color = Color.Unspecified,
  val dynamicColors: Boolean = false,
  val dynamicColorTokens: DynamicColorTokens = DynamicColorTokens(),
)

private val defaultThemeTokens = ThemeTokens()

@Composable
fun getThemeTokens(
  androidTheme: Boolean = false,
  disableDynamicTheming: Boolean = false,
  themeSeed: String = "#000000",
) = if (!disableDynamicTheming && supportsDynamicTheming()) {
  val tonalPalette = dynamicTonalPalette(LocalContext.current)
  defaultThemeTokens.copy(
    dynamicColorTokens = DynamicColorTokens(
      primary = tonalPalette.primary20,
      secondary = tonalPalette.secondary20,
      tertiary = tonalPalette.tertiary20,
    ),
    dynamicColors = true,
  )
} else if (androidTheme) {
  defaultThemeTokens.copy(seed = themeSeed.asColor())
} else {
  defaultThemeTokens.copy(seed = themeSeed.asColor())
}

@ChecksSdkIntAtLeast(api = Build.VERSION_CODES.S)
fun supportsDynamicTheming() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
