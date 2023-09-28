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

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.teogor.ceres.ui.foundation.applyTouchFeedback
import dev.teogor.ceres.ui.theme.MaterialTheme
import dev.teogor.ceres.ui.theme.toColor
import dev.teogor.ceres.ui.theme.tokens.ColorSchemeKeyTokens
import dev.teogor.ceres.ui.theme.tokens.ElevationTokens
import dev.teogor.ceres.ui.theme.tokens.ShapeKeyTokens
import dev.teogor.ceres.ui.theme.tokens.TypographyKeyTokens

/**
 * Ceres filled button with generic content slot. Wraps Material 3 [Button].
 *
 * @param onClick Will be called when the user clicks the button.
 * @param modifier Modifier to be applied to the button.
 * @param enabled Controls the enabled state of the button. When `false`, this button will not be
 * clickable and will appear disabled to accessibility services.
 * @param contentPadding The spacing values to apply internally between the container and the
 * content.
 * @param content The button content.
 */
@Composable
fun Button(
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
  enabled: Boolean = true,
  contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
  colors: ButtonColors = buttonColorsDefaults(),
  content: @Composable RowScope.() -> Unit,
) = androidx.compose.material3.Button(
  onClick = {
    applyTouchFeedback()
    onClick()
  },
  modifier = modifier,
  enabled = enabled,
  colors = colors,
  contentPadding = contentPadding,
  content = content,
)

@Composable
fun buttonColorsDefaults() = ButtonDefaults.buttonColors(
  containerColor = FilledButtonTokens.ContainerColor.toColor(),
  contentColor = FilledButtonTokens.LabelTextColor.toColor(),
  disabledContainerColor =
  FilledButtonTokens.DisabledContainerColor.toColor()
    .copy(alpha = FilledButtonTokens.DisabledContainerOpacity),
  disabledContentColor = FilledButtonTokens.DisabledLabelTextColor.toColor()
    .copy(alpha = FilledButtonTokens.DisabledLabelTextOpacity),
)

/**
 * Ceres filled button with generic content slot. Wraps Material 3 [Button].
 *
 * @param onClick Will be called when the user clicks the button.
 * @param modifier Modifier to be applied to the button.
 * @param enabled Controls the enabled state of the button. When `false`, this button will not be
 * clickable and will appear disabled to accessibility services.
 * @param contentPadding The spacing values to apply internally between the container and the
 * content.
 * @param content The button content.
 */
@Composable
fun CeresButton(
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
  enabled: Boolean = true,
  contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
  content: @Composable RowScope.() -> Unit,
) {
  Button(
    onClick = onClick,
    modifier = modifier,
    enabled = enabled,
    colors = ButtonDefaults.buttonColors(
      containerColor = MaterialTheme.colorScheme.onBackground,
    ),
    contentPadding = contentPadding,
    content = content,
  )
}

/**
 * Ceres filled button with text and icon content slots.
 *
 * @param onClick Will be called when the user clicks the button.
 * @param modifier Modifier to be applied to the button.
 * @param enabled Controls the enabled state of the button. When `false`, this button will not be
 * clickable and will appear disabled to accessibility services.
 * @param text The button text label content.
 * @param leadingIcon The button leading icon content. Pass `null` here for no leading icon.
 */
@Composable
fun CeresButton(
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
  enabled: Boolean = true,
  text: @Composable () -> Unit,
  leadingIcon: @Composable (() -> Unit)? = null,
) {
  CeresButton(
    onClick = onClick,
    modifier = modifier,
    enabled = enabled,
    contentPadding = if (leadingIcon != null) {
      ButtonDefaults.ButtonWithIconContentPadding
    } else {
      ButtonDefaults.ContentPadding
    },
  ) {
    CeresButtonContent(
      text = text,
      leadingIcon = leadingIcon,
    )
  }
}

/**
 * Ceres outlined button with generic content slot. Wraps Material 3 [OutlinedButton].
 *
 * @param onClick Will be called when the user clicks the button.
 * @param modifier Modifier to be applied to the button.
 * @param enabled Controls the enabled state of the button. When `false`, this button will not be
 * clickable and will appear disabled to accessibility services.
 * @param contentPadding The spacing values to apply internally between the container and the
 * content.
 * @param content The button content.
 */
@Composable
fun OutlinedButton(
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
  enabled: Boolean = true,
  border: BorderStroke = BorderStroke(
    width = CeresButtonDefaults.OutlinedButtonBorderWidth,
    color = if (enabled) {
      MaterialTheme.colorScheme.outline
    } else {
      MaterialTheme.colorScheme.onSurface.copy(
        alpha = CeresButtonDefaults.DisabledOutlinedButtonBorderAlpha,
      )
    },
  ),
  contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
  content: @Composable RowScope.() -> Unit,
) {
  OutlinedButton(
    onClick = {
      applyTouchFeedback()
      onClick()
    },
    modifier = modifier,
    enabled = enabled,
    colors = ButtonDefaults.outlinedButtonColors(
      contentColor = MaterialTheme.colorScheme.onBackground,
    ),
    border = border,
    contentPadding = contentPadding,
    content = content,
  )
}

/**
 * Ceres outlined button with text and icon content slots.
 *
 * @param onClick Will be called when the user clicks the button.
 * @param modifier Modifier to be applied to the button.
 * @param enabled Controls the enabled state of the button. When `false`, this button will not be
 * clickable and will appear disabled to accessibility services.
 * @param text The button text label content.
 * @param leadingIcon The button leading icon content. Pass `null` here for no leading icon.
 */
@Composable
fun OutlinedButton(
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
  enabled: Boolean = true,
  text: @Composable () -> Unit,
  leadingIcon: @Composable (() -> Unit)? = null,
) {
  OutlinedButton(
    onClick = onClick,
    modifier = modifier,
    enabled = enabled,
    contentPadding = if (leadingIcon != null) {
      ButtonDefaults.ButtonWithIconContentPadding
    } else {
      ButtonDefaults.ContentPadding
    },
  ) {
    CeresButtonContent(
      text = text,
      leadingIcon = leadingIcon,
    )
  }
}

/**
 * Ceres text button with generic content slot. Wraps Material 3 [TextButton].
 *
 * @param onClick Will be called when the user clicks the button.
 * @param modifier Modifier to be applied to the button.
 * @param enabled Controls the enabled state of the button. When `false`, this button will not be
 * clickable and will appear disabled to accessibility services.
 * @param content The button content.
 */
@Composable
fun CeresTextButton(
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
  enabled: Boolean = true,
  content: @Composable RowScope.() -> Unit,
) {
  TextButton(
    onClick = onClick,
    modifier = modifier,
    enabled = enabled,
    colors = ButtonDefaults.textButtonColors(
      contentColor = MaterialTheme.colorScheme.onBackground,
    ),
    content = content,
  )
}

/**
 * Ceres text button with text and icon content slots.
 *
 * @param onClick Will be called when the user clicks the button.
 * @param modifier Modifier to be applied to the button.
 * @param enabled Controls the enabled state of the button. When `false`, this button will not be
 * clickable and will appear disabled to accessibility services.
 * @param text The button text label content.
 * @param leadingIcon The button leading icon content. Pass `null` here for no leading icon.
 */
@Composable
fun CeresTextButton(
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
  enabled: Boolean = true,
  text: @Composable () -> Unit,
  leadingIcon: @Composable (() -> Unit)? = null,
) {
  CeresTextButton(
    onClick = onClick,
    modifier = modifier,
    enabled = enabled,
  ) {
    CeresButtonContent(
      text = text,
      leadingIcon = leadingIcon,
    )
  }
}

/**
 * Internal Ceres button content layout for arranging the text label and leading icon.
 *
 * @param text The button text label content.
 * @param leadingIcon The button leading icon content. Default is `null` for no leading icon.Ï
 */
@Composable
private fun CeresButtonContent(
  text: @Composable () -> Unit,
  leadingIcon: @Composable (() -> Unit)? = null,
) {
  if (leadingIcon != null) {
    Box(Modifier.sizeIn(maxHeight = ButtonDefaults.IconSize)) {
      leadingIcon()
    }
  }
  Box(
    Modifier
      .padding(
        start = if (leadingIcon != null) {
          ButtonDefaults.IconSpacing
        } else {
          0.dp
        },
      ),
  ) {
    text()
  }
}

/**
 * Ceres button default values.
 */
object CeresButtonDefaults {
  // TODO: File bug
  // OutlinedButton border color doesn't respect disabled state by default
  const val DisabledOutlinedButtonBorderAlpha = 0.12f

  // TODO: File bug
  // OutlinedButton default border width isn't exposed via ButtonDefaults
  val OutlinedButtonBorderWidth = 1.dp
}

internal object FilledButtonTokens {
  val ContainerColor = ColorSchemeKeyTokens.Primary
  val ContainerElevation = ElevationTokens.Level0
  val ContainerHeight = 40.0.dp
  val ContainerShape = ShapeKeyTokens.CornerFull
  val DisabledContainerColor = ColorSchemeKeyTokens.OnSurface
  val DisabledContainerElevation = ElevationTokens.Level0
  const val DisabledContainerOpacity = 0.12f
  val DisabledLabelTextColor = ColorSchemeKeyTokens.OnSurface
  const val DisabledLabelTextOpacity = 0.38f
  val FocusContainerElevation = ElevationTokens.Level0
  val FocusLabelTextColor = ColorSchemeKeyTokens.OnPrimary
  val HoverContainerElevation = ElevationTokens.Level1
  val HoverLabelTextColor = ColorSchemeKeyTokens.OnPrimary
  val LabelTextColor = ColorSchemeKeyTokens.OnPrimary
  val LabelTextFont = TypographyKeyTokens.LabelLarge
  val PressedContainerElevation = ElevationTokens.Level0
  val PressedLabelTextColor = ColorSchemeKeyTokens.OnPrimary
  val DisabledIconColor = ColorSchemeKeyTokens.OnSurface
  const val DisabledIconOpacity = 0.38f
  val FocusIconColor = ColorSchemeKeyTokens.OnPrimary
  val HoverIconColor = ColorSchemeKeyTokens.OnPrimary
  val IconColor = ColorSchemeKeyTokens.OnPrimary
  val IconSize = 18.0.dp
  val PressedIconColor = ColorSchemeKeyTokens.OnPrimary
}
