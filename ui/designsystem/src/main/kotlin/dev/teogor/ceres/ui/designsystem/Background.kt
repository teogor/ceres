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

package dev.teogor.ceres.ui.designsystem

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.teogor.ceres.ui.theme.MaterialTheme
import dev.teogor.ceres.ui.theme.core.GradientColors
import dev.teogor.ceres.ui.theme.core.LocalBackgroundTheme
import dev.teogor.ceres.ui.theme.core.LocalGradientColors
import dev.teogor.ceres.ui.theme.core.Theme
import kotlin.math.tan

/**
 * The main background for the app.
 * Uses [LocalBackgroundTheme] to set the color and tonal elevation of a [Surface].
 *
 * @param modifier Modifier to be applied to the background.
 * @param content The background content.
 */
@Composable
fun CeresBackground(
  modifier: Modifier = Modifier,
  content: @Composable () -> Unit,
) {
  val color = LocalBackgroundTheme.current.color
  val tonalElevation = LocalBackgroundTheme.current.tonalElevation
  Surface(
    color = if (color == Color.Unspecified) Color.Transparent else color,
    tonalElevation = if (tonalElevation == Dp.Unspecified) 0.dp else tonalElevation,
    modifier = modifier.fillMaxSize(),
  ) {
    CompositionLocalProvider(LocalAbsoluteTonalElevation provides 0.dp) {
      content()
    }
  }
}

@Composable
fun CeresBackground(
  modifier: Modifier = Modifier.fillMaxSize(),
  color: Color = Color.Unspecified,
  tonalElevation: Dp = Dp.Unspecified,
  content: @Composable () -> Unit,
) {
  if (color == Color.Unspecified) {
    content()
  } else {
    Surface(
      color = color.surfaceColorAtElevation(
        elevation = if (tonalElevation == Dp.Unspecified) 0.dp else tonalElevation,
        surface = MaterialTheme.colorScheme.surface,
      ),
      modifier = modifier,
    ) {
      CompositionLocalProvider(LocalAbsoluteTonalElevation provides 0.dp) {
        content()
      }
    }
  }
}

/**
 * A gradient background for select screens. Uses [LocalBackgroundTheme] to set the gradient colors
 * of a [Box] within a [Surface].
 *
 * @param modifier Modifier to be applied to the background.
 * @param gradientColors The gradient colors to be rendered.
 * @param content The background content.
 */
@Composable
fun CeresGradientBackground(
  modifier: Modifier = Modifier,
  gradientColors: GradientColors = LocalGradientColors.current,
  content: @Composable () -> Unit,
) {
  val currentTopColor by rememberUpdatedState(gradientColors.top)
  val currentBottomColor by rememberUpdatedState(gradientColors.bottom)
  Surface(
    color = if (gradientColors.container == Color.Unspecified) {
      Color.Transparent
    } else {
      gradientColors.container
    },
    modifier = modifier.fillMaxSize(),
  ) {
    Box(
      Modifier
        .fillMaxSize()
        .drawWithCache {
          // Compute the start and end coordinates such that the gradients are angled 11.06
          // degrees off the vertical axis
          val offset = size.height * tan(
            Math
              .toRadians(11.06)
              .toFloat(),
          )

          val start = Offset(size.width / 2 + offset / 2, 0f)
          val end = Offset(size.width / 2 - offset / 2, size.height)

          // Create the top gradient that fades out after the halfway point vertically
          val topGradient = Brush.linearGradient(
            0f to if (currentTopColor == Color.Unspecified) {
              Color.Transparent
            } else {
              currentTopColor
            },
            0.724f to Color.Transparent,
            start = start,
            end = end,
          )
          // Create the bottom gradient that fades in before the halfway point vertically
          val bottomGradient = Brush.linearGradient(
            0.2552f to Color.Transparent,
            1f to if (currentBottomColor == Color.Unspecified) {
              Color.Transparent
            } else {
              currentBottomColor
            },
            start = start,
            end = end,
          )

          onDrawBehind {
            // There is overlap here, so order is important
            drawRect(topGradient)
            drawRect(bottomGradient)
          }
        },
    ) {
      content()
    }
  }
}

/**
 * Multipreview annotation that represents light and dark themes. Add this annotation to a
 * composable to render the both themes.
 */
@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, name = "Light theme")
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark theme")
annotation class ThemePreviews

@ThemePreviews
@Composable
fun BackgroundDefault() {
  Theme(disableDynamicTheming = true) {
    CeresBackground(Modifier.size(100.dp), content = {})
  }
}

@ThemePreviews
@Composable
fun BackgroundDynamic() {
  Theme(disableDynamicTheming = false) {
    CeresBackground(Modifier.size(100.dp), content = {})
  }
}

@ThemePreviews
@Composable
fun BackgroundAndroid() {
  Theme(androidTheme = true) {
    CeresBackground(Modifier.size(100.dp), content = {})
  }
}

@ThemePreviews
@Composable
fun GradientBackgroundDefault() {
  Theme(disableDynamicTheming = true) {
    CeresGradientBackground(Modifier.size(100.dp), content = {})
  }
}

@ThemePreviews
@Composable
fun GradientBackgroundDynamic() {
  Theme(disableDynamicTheming = false) {
    CeresGradientBackground(Modifier.size(100.dp), content = {})
  }
}

@ThemePreviews
@Composable
fun GradientBackgroundAndroid() {
  Theme(androidTheme = true) {
    CeresGradientBackground(Modifier.size(100.dp), content = {})
  }
}
