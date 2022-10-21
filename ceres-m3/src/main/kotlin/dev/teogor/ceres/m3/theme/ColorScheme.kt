/*
 * Copyright 2022 teogor (Teodor Grigor) All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dev.teogor.ceres.m3.theme

import android.graphics.Color
import androidx.annotation.ColorInt

/**
 * A color scheme holds all the named color parameters for a [MaterialTheme].
 *
 * Color schemes are designed to be harmonious, ensure accessible text, and distinguish UI
 * elements and surfaces from one another. There are two built-in baseline schemes,
 * [lightColorScheme] and a [darkColorScheme], that can be used as-is or customized.
 *
 * The Material color system and custom schemes provide default values for color as a starting point
 * for customization.
 *
 * To learn more about colors, see [Material Design colors](https://m3.material.io/styles/color/overview).
 *
 * @property primary The primary color is the color displayed most frequently across your app’s
 * screens and components.
 * @property onPrimary Color used for text and icons displayed on top of the primary color.
 * @property primaryContainer The preferred tonal color of containers.
 * @property onPrimaryContainer The color (and state variants) that should be used for content on
 * top of [primaryContainer].
 * @property inversePrimary Color to be used as a "primary" color in places where the inverse color
 * scheme is needed, such as the button on a SnackBar.
 * @property secondary The secondary color provides more ways to accent and distinguish your
 * product. Secondary colors are best for:
 * - Floating action buttons
 * - Selection controls, like checkboxes and radio buttons
 * - Highlighting selected text
 * - Links and headlines
 * @property onSecondary Color used for text and icons displayed on top of the secondary color.
 * @property secondaryContainer A tonal color to be used in containers.
 * @property onSecondaryContainer The color (and state variants) that should be used for content on
 * top of [secondaryContainer].
 * @property tertiary The tertiary color that can be used to balance primary and secondary
 * colors, or bring heightened attention to an element such as an input field.
 * @property onTertiary Color used for text and icons displayed on top of the tertiary color.
 * @property tertiaryContainer A tonal color to be used in containers.
 * @property onTertiaryContainer The color (and state variants) that should be used for content on
 * top of [tertiaryContainer].
 * @property background The background color that appears behind scrollable content.
 * @property onBackground Color used for text and icons displayed on top of the background color.
 * @property surface The surface color that affect surfaces of components, such as cards, sheets,
 * and menus.
 * @property onSurface Color used for text and icons displayed on top of the surface color.
 * @property surfaceVariant Another option for a color with similar uses of [surface].
 * @property onSurfaceVariant The color (and state variants) that can be used for content on top of
 * [surface].
 * @property surfaceTint This color will be used by components that apply tonal elevation and is
 * applied on top of [surface]. The higher the elevation the more this color is used.
 * @property inverseSurface A color that contrasts sharply with [surface]. Useful for surfaces that
 * sit on top of other surfaces with [surface] color.
 * @property inverseOnSurface A color that contrasts well with [inverseSurface]. Useful for content
 * that sits on top of containers that are [inverseSurface].
 * @property error The error color is used to indicate errors in components, such as invalid text in
 * a text field.
 * @property onError Color used for text and icons displayed on top of the error color.
 * @property errorContainer The preferred tonal color of error containers.
 * @property onErrorContainer The color (and state variants) that should be used for content on
 * top of [errorContainer].
 * @property outline Subtle color used for boundaries. Outline color role adds contrast for
 * accessibility purposes.
 * @property outlineVariant Utility color used for boundaries for decorative elements when strong
 * contrast is not required.
 * @property scrim Color of a scrim that obscures content.
 */
data class ColorScheme(
  @ColorInt val primary: Int,
  @ColorInt val onPrimary: Int,
  @ColorInt val primaryContainer: Int,
  @ColorInt val onPrimaryContainer: Int,
  @ColorInt val inversePrimary: Int,
  @ColorInt val secondary: Int,
  @ColorInt val onSecondary: Int,
  @ColorInt val secondaryContainer: Int,
  @ColorInt val onSecondaryContainer: Int,
  @ColorInt val tertiary: Int,
  @ColorInt val onTertiary: Int,
  @ColorInt val tertiaryContainer: Int,
  @ColorInt val onTertiaryContainer: Int,
  @ColorInt val background: Int,
  @ColorInt val onBackground: Int,
  @ColorInt val surface: Int,
  @ColorInt val onSurface: Int,
  @ColorInt val surfaceVariant: Int,
  @ColorInt val onSurfaceVariant: Int,
  @ColorInt val surfaceTint: Int,
  @ColorInt val inverseSurface: Int,
  @ColorInt val inverseOnSurface: Int,
  @ColorInt val error: Int,
  @ColorInt val onError: Int,
  @ColorInt val errorContainer: Int,
  @ColorInt val onErrorContainer: Int,
  @ColorInt val outline: Int,
  @ColorInt val outlineVariant: Int,
  @ColorInt val scrim: Int
) {

  @ColorInt
  val transparent: Int = Color.TRANSPARENT

  override fun toString(): String {
    return "ColorScheme(" +
      "primary=$primary" +
      "onPrimary=$onPrimary" +
      "primaryContainer=$primaryContainer" +
      "onPrimaryContainer=$onPrimaryContainer" +
      "inversePrimary=$inversePrimary" +
      "secondary=$secondary" +
      "onSecondary=$onSecondary" +
      "secondaryContainer=$secondaryContainer" +
      "onSecondaryContainer=$onSecondaryContainer" +
      "tertiary=$tertiary" +
      "onTertiary=$onTertiary" +
      "tertiaryContainer=$tertiaryContainer" +
      "onTertiaryContainer=$onTertiaryContainer" +
      "background=$background" +
      "onBackground=$onBackground" +
      "surface=$surface" +
      "onSurface=$onSurface" +
      "surfaceVariant=$surfaceVariant" +
      "onSurfaceVariant=$onSurfaceVariant" +
      "surfaceTint=$surfaceTint" +
      "inverseSurface=$inverseSurface" +
      "inverseOnSurface=$inverseOnSurface" +
      "error=$error" +
      "onError=$onError" +
      "errorContainer=$errorContainer" +
      "onErrorContainer=$onErrorContainer" +
      "outline=$outline" +
      "outlineVariant=$outlineVariant" +
      "scrim=$scrim" +
      ")"
  }
}
