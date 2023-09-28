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

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import dev.teogor.ceres.ui.theme.toColor
import dev.teogor.ceres.ui.theme.tokens.ColorSchemeKeyTokens
import dev.teogor.ceres.ui.theme.tokens.ShapeKeyTokens
import dev.teogor.ceres.ui.theme.tokens.TypographyKeyTokens

@Composable
fun OutlinedTextField(
  value: String,
  onValueChange: (String) -> Unit,
  modifier: Modifier = Modifier,
  enabled: Boolean = true,
  readOnly: Boolean = false,
  textStyle: TextStyle = LocalTextStyle.current,
  label: @Composable (() -> Unit)? = null,
  placeholder: @Composable (() -> Unit)? = null,
  leadingIcon: @Composable (() -> Unit)? = null,
  trailingIcon: @Composable (() -> Unit)? = null,
  prefix: @Composable (() -> Unit)? = null,
  suffix: @Composable (() -> Unit)? = null,
  supportingText: @Composable (() -> Unit)? = null,
  isError: Boolean = false,
  visualTransformation: VisualTransformation = VisualTransformation.None,
  keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
  keyboardActions: KeyboardActions = KeyboardActions.Default,
  singleLine: Boolean = false,
  maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
  minLines: Int = 1,
  interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
  shape: Shape = OutlinedTextFieldDefaults.shape,
  colors: TextFieldColors = OutlinedTextFieldDefaults.colors(
    focusedTextColor = OutlinedTextFieldTokens.FocusInputColor.toColor(),
    unfocusedTextColor = OutlinedTextFieldTokens.InputColor.toColor(),
    disabledTextColor = OutlinedTextFieldTokens.DisabledInputColor.toColor()
      .copy(alpha = OutlinedTextFieldTokens.DisabledInputOpacity),
    errorTextColor = OutlinedTextFieldTokens.ErrorInputColor.toColor(),
    focusedContainerColor = Color.Transparent,
    unfocusedContainerColor = Color.Transparent,
    disabledContainerColor = Color.Transparent,
    errorContainerColor = Color.Transparent,
    cursorColor = OutlinedTextFieldTokens.CaretColor.toColor(),
    errorCursorColor = OutlinedTextFieldTokens.ErrorFocusCaretColor.toColor(),
    selectionColors = LocalTextSelectionColors.current,
    focusedBorderColor = OutlinedTextFieldTokens.FocusOutlineColor.toColor(),
    unfocusedBorderColor = OutlinedTextFieldTokens.OutlineColor.toColor(),
    disabledBorderColor = OutlinedTextFieldTokens.DisabledOutlineColor.toColor()
      .copy(alpha = OutlinedTextFieldTokens.DisabledOutlineOpacity),
    errorBorderColor = OutlinedTextFieldTokens.ErrorOutlineColor.toColor(),
    focusedLeadingIconColor = OutlinedTextFieldTokens.FocusLeadingIconColor.toColor(),
    unfocusedLeadingIconColor = OutlinedTextFieldTokens.LeadingIconColor.toColor(),
    disabledLeadingIconColor = OutlinedTextFieldTokens.DisabledLeadingIconColor.toColor()
      .copy(alpha = OutlinedTextFieldTokens.DisabledLeadingIconOpacity),
    errorLeadingIconColor = OutlinedTextFieldTokens.ErrorLeadingIconColor.toColor(),
    focusedTrailingIconColor = OutlinedTextFieldTokens.FocusTrailingIconColor.toColor(),
    unfocusedTrailingIconColor = OutlinedTextFieldTokens.TrailingIconColor.toColor(),
    disabledTrailingIconColor = OutlinedTextFieldTokens.DisabledTrailingIconColor
      .toColor().copy(alpha = OutlinedTextFieldTokens.DisabledTrailingIconOpacity),
    errorTrailingIconColor = OutlinedTextFieldTokens.ErrorTrailingIconColor.toColor(),
    focusedLabelColor = OutlinedTextFieldTokens.FocusLabelColor.toColor(),
    unfocusedLabelColor = OutlinedTextFieldTokens.LabelColor.toColor(),
    disabledLabelColor = OutlinedTextFieldTokens.DisabledLabelColor.toColor()
      .copy(alpha = OutlinedTextFieldTokens.DisabledLabelOpacity),
    errorLabelColor = OutlinedTextFieldTokens.ErrorLabelColor.toColor(),
    focusedPlaceholderColor = OutlinedTextFieldTokens.InputPlaceholderColor.toColor(),
    unfocusedPlaceholderColor = OutlinedTextFieldTokens.InputPlaceholderColor.toColor(),
    disabledPlaceholderColor = OutlinedTextFieldTokens.DisabledInputColor.toColor()
      .copy(alpha = OutlinedTextFieldTokens.DisabledInputOpacity),
    errorPlaceholderColor = OutlinedTextFieldTokens.InputPlaceholderColor.toColor(),
    focusedSupportingTextColor = OutlinedTextFieldTokens.FocusSupportingColor.toColor(),
    unfocusedSupportingTextColor = OutlinedTextFieldTokens.SupportingColor.toColor(),
    disabledSupportingTextColor = OutlinedTextFieldTokens.DisabledSupportingColor
      .toColor().copy(alpha = OutlinedTextFieldTokens.DisabledSupportingOpacity),
    errorSupportingTextColor = OutlinedTextFieldTokens.ErrorSupportingColor.toColor(),
    focusedPrefixColor = OutlinedTextFieldTokens.InputPrefixColor.toColor(),
    unfocusedPrefixColor = OutlinedTextFieldTokens.InputPrefixColor.toColor(),
    disabledPrefixColor = OutlinedTextFieldTokens.InputPrefixColor.toColor()
      .copy(alpha = OutlinedTextFieldTokens.DisabledInputOpacity),
    errorPrefixColor = OutlinedTextFieldTokens.InputPrefixColor.toColor(),
    focusedSuffixColor = OutlinedTextFieldTokens.InputSuffixColor.toColor(),
    unfocusedSuffixColor = OutlinedTextFieldTokens.InputSuffixColor.toColor(),
    disabledSuffixColor = OutlinedTextFieldTokens.InputSuffixColor.toColor()
      .copy(alpha = OutlinedTextFieldTokens.DisabledInputOpacity),
    errorSuffixColor = OutlinedTextFieldTokens.InputSuffixColor.toColor(),
  ),
) {
  androidx.compose.material3.OutlinedTextField(
    value = value,
    onValueChange = onValueChange,
    modifier = modifier,
    enabled = enabled,
    readOnly = readOnly,
    textStyle = textStyle,
    label = label,
    placeholder = placeholder,
    leadingIcon = leadingIcon,
    trailingIcon = trailingIcon,
    prefix = prefix,
    suffix = suffix,
    supportingText = supportingText,
    isError = isError,
    visualTransformation = visualTransformation,
    keyboardOptions = keyboardOptions,
    keyboardActions = keyboardActions,
    singleLine = singleLine,
    maxLines = maxLines,
    minLines = minLines,
    interactionSource = interactionSource,
    shape = shape,
    colors = colors,
  )
}

@Composable
fun copyOutlinedTextFieldTokens(): TextFieldColors {
  val colorsOutlinedTextFieldDefaults = OutlinedTextFieldDefaults.colors(
    focusedTextColor = OutlinedTextFieldTokens.FocusInputColor.toColor(),
    unfocusedTextColor = OutlinedTextFieldTokens.InputColor.toColor(),
    disabledTextColor = OutlinedTextFieldTokens.DisabledInputColor.toColor()
      .copy(alpha = OutlinedTextFieldTokens.DisabledInputOpacity),
    errorTextColor = OutlinedTextFieldTokens.ErrorInputColor.toColor(),
    focusedContainerColor = Color.Transparent,
    unfocusedContainerColor = Color.Transparent,
    disabledContainerColor = Color.Transparent,
    errorContainerColor = Color.Transparent,
    cursorColor = OutlinedTextFieldTokens.CaretColor.toColor(),
    errorCursorColor = OutlinedTextFieldTokens.ErrorFocusCaretColor.toColor(),
    selectionColors = LocalTextSelectionColors.current,
    focusedBorderColor = OutlinedTextFieldTokens.FocusOutlineColor.toColor(),
    unfocusedBorderColor = OutlinedTextFieldTokens.OutlineColor.toColor(),
    disabledBorderColor = OutlinedTextFieldTokens.DisabledOutlineColor.toColor()
      .copy(alpha = OutlinedTextFieldTokens.DisabledOutlineOpacity),
    errorBorderColor = OutlinedTextFieldTokens.ErrorOutlineColor.toColor(),
    focusedLeadingIconColor = OutlinedTextFieldTokens.FocusLeadingIconColor.toColor(),
    unfocusedLeadingIconColor = OutlinedTextFieldTokens.LeadingIconColor.toColor(),
    disabledLeadingIconColor = OutlinedTextFieldTokens.DisabledLeadingIconColor.toColor()
      .copy(alpha = OutlinedTextFieldTokens.DisabledLeadingIconOpacity),
    errorLeadingIconColor = OutlinedTextFieldTokens.ErrorLeadingIconColor.toColor(),
    focusedTrailingIconColor = OutlinedTextFieldTokens.FocusTrailingIconColor.toColor(),
    unfocusedTrailingIconColor = OutlinedTextFieldTokens.TrailingIconColor.toColor(),
    disabledTrailingIconColor = OutlinedTextFieldTokens.DisabledTrailingIconColor
      .toColor().copy(alpha = OutlinedTextFieldTokens.DisabledTrailingIconOpacity),
    errorTrailingIconColor = OutlinedTextFieldTokens.ErrorTrailingIconColor.toColor(),
    focusedLabelColor = OutlinedTextFieldTokens.FocusLabelColor.toColor(),
    unfocusedLabelColor = OutlinedTextFieldTokens.LabelColor.toColor(),
    disabledLabelColor = OutlinedTextFieldTokens.DisabledLabelColor.toColor()
      .copy(alpha = OutlinedTextFieldTokens.DisabledLabelOpacity),
    errorLabelColor = OutlinedTextFieldTokens.ErrorLabelColor.toColor(),
    focusedPlaceholderColor = OutlinedTextFieldTokens.InputPlaceholderColor.toColor(),
    unfocusedPlaceholderColor = OutlinedTextFieldTokens.InputPlaceholderColor.toColor(),
    disabledPlaceholderColor = OutlinedTextFieldTokens.DisabledInputColor.toColor()
      .copy(alpha = OutlinedTextFieldTokens.DisabledInputOpacity),
    errorPlaceholderColor = OutlinedTextFieldTokens.InputPlaceholderColor.toColor(),
    focusedSupportingTextColor = OutlinedTextFieldTokens.FocusSupportingColor.toColor(),
    unfocusedSupportingTextColor = OutlinedTextFieldTokens.SupportingColor.toColor(),
    disabledSupportingTextColor = OutlinedTextFieldTokens.DisabledSupportingColor
      .toColor().copy(alpha = OutlinedTextFieldTokens.DisabledSupportingOpacity),
    errorSupportingTextColor = OutlinedTextFieldTokens.ErrorSupportingColor.toColor(),
    focusedPrefixColor = OutlinedTextFieldTokens.InputPrefixColor.toColor(),
    unfocusedPrefixColor = OutlinedTextFieldTokens.InputPrefixColor.toColor(),
    disabledPrefixColor = OutlinedTextFieldTokens.InputPrefixColor.toColor()
      .copy(alpha = OutlinedTextFieldTokens.DisabledInputOpacity),
    errorPrefixColor = OutlinedTextFieldTokens.InputPrefixColor.toColor(),
    focusedSuffixColor = OutlinedTextFieldTokens.InputSuffixColor.toColor(),
    unfocusedSuffixColor = OutlinedTextFieldTokens.InputSuffixColor.toColor(),
    disabledSuffixColor = OutlinedTextFieldTokens.InputSuffixColor.toColor()
      .copy(alpha = OutlinedTextFieldTokens.DisabledInputOpacity),
    errorSuffixColor = OutlinedTextFieldTokens.InputSuffixColor.toColor(),
  )
  return colorsOutlinedTextFieldDefaults
}

internal object OutlinedTextFieldTokens {
  val CaretColor = ColorSchemeKeyTokens.Primary
  val ContainerHeight = 56.0.dp
  val ContainerShape = ShapeKeyTokens.CornerExtraSmall
  val DisabledInputColor = ColorSchemeKeyTokens.OnSurface
  const val DisabledInputOpacity = 0.38f
  val DisabledLabelColor = ColorSchemeKeyTokens.OnSurface
  const val DisabledLabelOpacity = 0.38f
  val DisabledLeadingIconColor = ColorSchemeKeyTokens.OnSurface
  const val DisabledLeadingIconOpacity = 0.38f
  val DisabledOutlineColor = ColorSchemeKeyTokens.OnSurface
  const val DisabledOutlineOpacity = 0.12f
  val DisabledOutlineWidth = 1.0.dp
  val DisabledSupportingColor = ColorSchemeKeyTokens.OnSurface
  const val DisabledSupportingOpacity = 0.38f
  val DisabledTrailingIconColor = ColorSchemeKeyTokens.OnSurface
  const val DisabledTrailingIconOpacity = 0.38f
  val ErrorFocusCaretColor = ColorSchemeKeyTokens.Error
  val ErrorFocusInputColor = ColorSchemeKeyTokens.OnSurface
  val ErrorFocusLabelColor = ColorSchemeKeyTokens.Error
  val ErrorFocusLeadingIconColor = ColorSchemeKeyTokens.OnSurfaceVariant
  val ErrorFocusOutlineColor = ColorSchemeKeyTokens.Error
  val ErrorFocusSupportingColor = ColorSchemeKeyTokens.Error
  val ErrorFocusTrailingIconColor = ColorSchemeKeyTokens.Error
  val ErrorHoverInputColor = ColorSchemeKeyTokens.OnSurface
  val ErrorHoverLabelColor = ColorSchemeKeyTokens.OnErrorContainer
  val ErrorHoverLeadingIconColor = ColorSchemeKeyTokens.OnSurfaceVariant
  val ErrorHoverOutlineColor = ColorSchemeKeyTokens.OnErrorContainer
  val ErrorHoverSupportingColor = ColorSchemeKeyTokens.Error
  val ErrorHoverTrailingIconColor = ColorSchemeKeyTokens.OnErrorContainer
  val ErrorInputColor = ColorSchemeKeyTokens.OnSurface
  val ErrorLabelColor = ColorSchemeKeyTokens.Error
  val ErrorLeadingIconColor = ColorSchemeKeyTokens.OnSurfaceVariant
  val ErrorOutlineColor = ColorSchemeKeyTokens.Error
  val ErrorSupportingColor = ColorSchemeKeyTokens.Error
  val ErrorTrailingIconColor = ColorSchemeKeyTokens.Error
  val FocusInputColor = ColorSchemeKeyTokens.OnSurface
  val FocusLabelColor = ColorSchemeKeyTokens.Primary
  val FocusLeadingIconColor = ColorSchemeKeyTokens.OnSurfaceVariant
  val FocusOutlineColor = ColorSchemeKeyTokens.Primary
  val FocusOutlineWidth = 2.0.dp
  val FocusSupportingColor = ColorSchemeKeyTokens.OnSurfaceVariant
  val FocusTrailingIconColor = ColorSchemeKeyTokens.OnSurfaceVariant
  val HoverInputColor = ColorSchemeKeyTokens.OnSurface
  val HoverLabelColor = ColorSchemeKeyTokens.OnSurface
  val HoverLeadingIconColor = ColorSchemeKeyTokens.OnSurfaceVariant
  val HoverOutlineColor = ColorSchemeKeyTokens.OnSurface
  val HoverOutlineWidth = 1.0.dp
  val HoverSupportingColor = ColorSchemeKeyTokens.OnSurfaceVariant
  val HoverTrailingIconColor = ColorSchemeKeyTokens.OnSurfaceVariant
  val InputColor = ColorSchemeKeyTokens.OnSurface
  val InputFont = TypographyKeyTokens.BodyLarge
  val InputPlaceholderColor = ColorSchemeKeyTokens.OnSurfaceVariant
  val InputPrefixColor = ColorSchemeKeyTokens.OnSurfaceVariant
  val InputSuffixColor = ColorSchemeKeyTokens.OnSurfaceVariant
  val LabelColor = ColorSchemeKeyTokens.OnSurfaceVariant
  val LabelFont = TypographyKeyTokens.BodyLarge
  val LeadingIconColor = ColorSchemeKeyTokens.OnSurfaceVariant
  val LeadingIconSize = 24.0.dp
  val OutlineColor = ColorSchemeKeyTokens.Outline
  val OutlineWidth = 1.0.dp
  val SupportingColor = ColorSchemeKeyTokens.OnSurfaceVariant
  val SupportingFont = TypographyKeyTokens.BodySmall
  val TrailingIconColor = ColorSchemeKeyTokens.OnSurfaceVariant
  val TrailingIconSize = 24.0.dp
}
