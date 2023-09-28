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
import dev.teogor.ceres.ui.theme.tokens.ShapeKeyTokens.CornerLargeEnd
import dev.teogor.ceres.ui.theme.tokens.ShapeKeyTokens.CornerLargeTop
import dev.teogor.ceres.ui.theme.tokens.TypographyKeyTokens.LabelLarge
import dev.teogor.ceres.ui.theme.tokens.TypographyKeyTokens.TitleSmall

object NavigationDrawerTokens {
  val ActiveFocusIconColor = OnSecondaryContainer
  val ActiveFocusLabelTextColor = OnSecondaryContainer
  val ActiveHoverIconColor = OnSecondaryContainer
  val ActiveHoverLabelTextColor = OnSecondaryContainer
  val ActiveIconColor = OnSecondaryContainer
  val ActiveIndicatorColor = SecondaryContainer
  val ActiveIndicatorHeight = 56.0.dp
  val ActiveIndicatorShape = CornerFull
  val ActiveIndicatorWidth = 336.0.dp
  val ActiveLabelTextColor = OnSecondaryContainer
  val ActivePressedIconColor = OnSecondaryContainer
  val ActivePressedLabelTextColor = OnSecondaryContainer
  val BottomContainerShape = CornerLargeTop
  val ContainerColor = Surface
  const val ContainerHeightPercent = 100.0f
  val ContainerShape = CornerLargeEnd
  val ContainerSurfaceTintLayerColor = SurfaceTint
  val ContainerWidth = 360.0.dp
  val HeadlineColor = OnSurfaceVariant
  val HeadlineFont = TitleSmall
  val IconSize = 24.0.dp
  val InactiveFocusIconColor = OnSurface
  val InactiveFocusLabelTextColor = OnSurface
  val InactiveHoverIconColor = OnSurface
  val InactiveHoverLabelTextColor = OnSurface
  val InactiveIconColor = OnSurfaceVariant
  val InactiveLabelTextColor = OnSurfaceVariant
  val InactivePressedIconColor = OnSurface
  val InactivePressedLabelTextColor = OnSurface
  val LabelTextFont = LabelLarge
  val LargeBadgeLabelColor = OnSurfaceVariant
  val LargeBadgeLabelFont = LabelLarge
  val ModalContainerElevation = ElevationTokens.Level1
  val StandardContainerElevation = ElevationTokens.Level0
}
