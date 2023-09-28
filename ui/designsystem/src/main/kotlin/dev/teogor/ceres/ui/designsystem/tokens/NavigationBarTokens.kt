/*
 * Copyright 2021 teogor (Teodor Grigor)
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

package dev.teogor.ceres.ui.designsystem.tokens

import androidx.compose.ui.unit.dp
import dev.teogor.ceres.ui.theme.tokens.ColorSchemeKeyTokens.OnSecondaryContainer
import dev.teogor.ceres.ui.theme.tokens.ColorSchemeKeyTokens.OnSurface
import dev.teogor.ceres.ui.theme.tokens.ColorSchemeKeyTokens.OnSurfaceVariant
import dev.teogor.ceres.ui.theme.tokens.ColorSchemeKeyTokens.SecondaryContainer
import dev.teogor.ceres.ui.theme.tokens.ColorSchemeKeyTokens.Surface
import dev.teogor.ceres.ui.theme.tokens.ColorSchemeKeyTokens.SurfaceTint
import dev.teogor.ceres.ui.theme.tokens.ElevationTokens
import dev.teogor.ceres.ui.theme.tokens.ShapeKeyTokens.CornerFull
import dev.teogor.ceres.ui.theme.tokens.ShapeKeyTokens.CornerNone
import dev.teogor.ceres.ui.theme.tokens.TypographyKeyTokens.LabelMedium

object NavigationBarTokens {
  val ActiveFocusIconColor = OnSecondaryContainer
  val ActiveFocusLabelTextColor = OnSurface
  val ActiveHoverIconColor = OnSecondaryContainer
  val ActiveHoverLabelTextColor = OnSurface
  val ActiveIconColor = OnSecondaryContainer
  val ActiveIndicatorColor = SecondaryContainer
  val ActiveIndicatorHeight = 32.0.dp
  val ActiveIndicatorShape = CornerFull
  val ActiveIndicatorWidth = 64.0.dp
  val ActiveLabelTextColor = OnSurface
  val ActivePressedIconColor = OnSecondaryContainer
  val ActivePressedLabelTextColor = OnSurface
  val ContainerColor = Surface
  val ContainerElevation = ElevationTokens.Level4
  val ContainerHeight = 80.0.dp
  val ContainerShape = CornerNone
  val ContainerSurfaceTintLayerColor = SurfaceTint
  val IconSize = 24.0.dp
  val InactiveFocusIconColor = OnSurface
  val InactiveFocusLabelTextColor = OnSurface
  val InactiveHoverIconColor = OnSurface
  val InactiveHoverLabelTextColor = OnSurface
  val InactiveIconColor = OnSurfaceVariant
  val InactiveLabelTextColor = OnSurfaceVariant
  val InactivePressedIconColor = OnSurface
  val InactivePressedLabelTextColor = OnSurface
  val LabelTextFont = LabelMedium
}
