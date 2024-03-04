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

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalRippleConfiguration
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.RippleConfiguration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.remember
import dev.teogor.ceres.ui.theme.tokens.StateTokens

/**
 * Material Theming refers to the customization of your Material Design app to better reflect your
 * product’s brand.
 *
 * Material components such as [Button] and [Checkbox] use values provided here when retrieving
 * default values.
 *
 * All values may be set by providing this component with the [colorScheme][ColorScheme],
 * [typography][Typography] attributes. Use this to configure the overall
 * theme of elements within this MaterialTheme.
 *
 * Any values that are not set will inherit the current value from the theme, falling back to the
 * defaults if there is no parent MaterialTheme. This allows using a MaterialTheme at the top
 * of your application, and then separate MaterialTheme(s) for different screens / parts of your
 * UI, overriding only the parts of the theme definition that need to change.
 *
 * @param colorScheme A complete definition of the Material Color theme for this hierarchy
 * @param typography A set of text styles to be used as this hierarchy's typography system
 * @param shapes A set of corner shapes to be used as this hierarchy's shape system
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MaterialTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  colorScheme: ColorScheme = MaterialTheme.colorScheme,
  shapes: Shapes = MaterialTheme.shapes,
  typography: Typography = MaterialTheme.typography,
  content: @Composable () -> Unit,
) {
  val rememberedColorScheme = remember {
    // Explicitly creating a new object here so we don't mutate the initial [colorScheme]
    // provided, and overwrite the values set in it.
    colorScheme.copy()
  }.apply {
    updateColorSchemeFrom(colorScheme)
  }
  // todo better handling for dark theme and theme config
  val rememberDarkTheme = remember(darkTheme) {
    darkTheme
  }
  val selectionColors = rememberTextSelectionColors(rememberedColorScheme)
  CompositionLocalProvider(
    LocalColorScheme provides rememberedColorScheme,
    LocalRippleConfiguration provides MaterialRippleConfiguration,
    LocalShapes provides shapes,
    LocalTextSelectionColors provides selectionColors,
    LocalTypography provides typography,
    LocalIsDarkMode provides rememberDarkTheme,
  ) {
    ProvideTextStyle(value = typography.bodyLarge, content = content)
  }
}

@OptIn(ExperimentalMaterial3Api::class)
private val MaterialRippleConfiguration: RippleConfiguration
  @Composable
  get() = RippleConfiguration(
    color = LocalContentColor.current,
    rippleAlpha = DefaultRippleAlpha,
  )

private val DefaultRippleAlpha = RippleAlpha(
  pressedAlpha = StateTokens.PressedStateLayerOpacity,
  focusedAlpha = StateTokens.FocusStateLayerOpacity,
  draggedAlpha = StateTokens.DraggedStateLayerOpacity,
  hoveredAlpha = StateTokens.HoverStateLayerOpacity,
)

/**
 * Contains functions to access the current theme values provided at the call site's position in
 * the hierarchy.
 */
object MaterialTheme {
  /**
   * Retrieves the current [ColorScheme] at the call site's position in the hierarchy.
   */
  val colorScheme: ColorScheme
    @Composable
    @ReadOnlyComposable
    get() = LocalColorScheme.current

  /**
   * Retrieves the current isDarkMode value at the call site's position in the hierarchy.
   *
   * @return `true` if the current theme is in dark mode, `false` otherwise.
   */
  val isDarkMode: Boolean
    @Composable
    @ReadOnlyComposable
    get() = LocalIsDarkMode.current

  /**
   * Retrieves the current [Typography] at the call site's position in the hierarchy.
   */
  val typography: Typography
    @Composable
    @ReadOnlyComposable
    get() = LocalTypography.current

  /**
   * Retrieves the current [Shapes] at the call site's position in the hierarchy.
   */
  val shapes: Shapes
    @Composable
    @ReadOnlyComposable
    get() = LocalShapes.current
}

@Composable
internal fun rememberTextSelectionColors(colorScheme: ColorScheme): TextSelectionColors {
  val primaryColor = colorScheme.primary
  return remember(primaryColor) {
    TextSelectionColors(
      handleColor = primaryColor,
      backgroundColor = primaryColor.copy(alpha = TextSelectionBackgroundOpacity),
    )
  }
}

/*@VisibleForTesting*/
internal const val TextSelectionBackgroundOpacity = 0.4f
