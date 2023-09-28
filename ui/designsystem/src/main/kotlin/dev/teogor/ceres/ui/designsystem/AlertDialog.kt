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

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import dev.teogor.ceres.ui.theme.toColor
import dev.teogor.ceres.ui.theme.toShape
import dev.teogor.ceres.ui.theme.tokens.ColorSchemeKeyTokens
import dev.teogor.ceres.ui.theme.tokens.ElevationTokens
import dev.teogor.ceres.ui.theme.tokens.ShapeKeyTokens
import dev.teogor.ceres.ui.theme.tokens.TypographyKeyTokens

@Composable
fun AlertDialog(
  onDismissRequest: () -> Unit,
  confirmButton: @Composable () -> Unit,
  modifier: Modifier = Modifier,
  dismissButton: @Composable (() -> Unit)? = null,
  icon: @Composable (() -> Unit)? = null,
  title: @Composable (() -> Unit)? = null,
  text: @Composable (() -> Unit)? = null,
  shape: Shape = AlertDialogDefaults.shape,
  containerColor: Color = AlertDialogDefaults.containerColor,
  iconContentColor: Color = AlertDialogDefaults.iconContentColor,
  titleContentColor: Color = AlertDialogDefaults.titleContentColor,
  textContentColor: Color = AlertDialogDefaults.textContentColor,
  tonalElevation: Dp = AlertDialogDefaults.TonalElevation,
  properties: DialogProperties = DialogProperties(),
) = androidx.compose.material3.AlertDialog(
  onDismissRequest = onDismissRequest,
  confirmButton = confirmButton,
  modifier = modifier,
  dismissButton = dismissButton,
  icon = icon,
  title = title,
  text = text,
  shape = shape,
  containerColor = containerColor,
  iconContentColor = iconContentColor,
  titleContentColor = titleContentColor,
  textContentColor = textContentColor,
  tonalElevation = tonalElevation,
  properties = properties,
)

/**
 * Contains default values used for [AlertDialog]
 */
object AlertDialogDefaults {
  /** The default shape for alert dialogs */
  val shape: Shape @Composable get() = DialogTokens.ContainerShape.toShape()

  /** The default container color for alert dialogs */
  val containerColor: Color @Composable get() = DialogTokens.ContainerColor.toColor()

  /** The default icon color for alert dialogs */
  val iconContentColor: Color @Composable get() = DialogTokens.IconColor.toColor()

  /** The default title color for alert dialogs */
  val titleContentColor: Color @Composable get() = DialogTokens.HeadlineColor.toColor()

  /** The default text color for alert dialogs */
  val textContentColor: Color @Composable get() = DialogTokens.SupportingTextColor.toColor()

  /** The default tonal elevation for alert dialogs */
  val TonalElevation: Dp = DialogTokens.ContainerElevation
}

internal object DialogTokens {
  val ActionFocusLabelTextColor = ColorSchemeKeyTokens.Primary
  val ActionHoverLabelTextColor = ColorSchemeKeyTokens.Primary
  val ActionLabelTextColor = ColorSchemeKeyTokens.Primary
  val ActionLabelTextFont = TypographyKeyTokens.LabelLarge
  val ActionPressedLabelTextColor = ColorSchemeKeyTokens.Primary
  val ContainerColor = ColorSchemeKeyTokens.Surface
  val ContainerElevation = ElevationTokens.Level3
  val ContainerShape = ShapeKeyTokens.CornerExtraLarge
  val ContainerSurfaceTintLayerColor = ColorSchemeKeyTokens.SurfaceTint
  val HeadlineColor = ColorSchemeKeyTokens.OnSurface
  val HeadlineFont = TypographyKeyTokens.HeadlineSmall
  val SupportingTextColor = ColorSchemeKeyTokens.OnSurfaceVariant
  val SupportingTextFont = TypographyKeyTokens.BodyMedium
  val IconColor = ColorSchemeKeyTokens.Secondary
  val IconSize = 24.0.dp
}
