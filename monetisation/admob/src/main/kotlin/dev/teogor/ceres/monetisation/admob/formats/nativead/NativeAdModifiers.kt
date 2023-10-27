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

package dev.teogor.ceres.monetisation.admob.formats.nativead

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.teogor.ceres.monetisation.admob.models.AdChoicesPlacement

/**
 * Composable function that creates a background [Modifier] for a native
 * ad, with options for customizing color, corner size, shadow elevation,
 * and AdChoices placement.
 *
 * @param color The background color of the native ad.
 * @param cornerSize The corner size for the rounded corners. Default is 0.dp.
 * @param shadowElevation The shadow elevation for the background. Default is 0.dp.
 * @param adChoicesPlacement The placement of AdChoices in the native ad.
 * @return A [Modifier] that represents the background with specified properties.
 */
@SuppressLint("ModifierFactoryExtensionFunction")
@Composable
fun nativeAdBackground(
  color: Color,
  cornerSize: Dp = 0.dp,
  shadowElevation: Dp = 0.dp,
  adChoicesPlacement: AdChoicesPlacement,
): Modifier {
  val shape = adChoicesPlacement.nativeAdRoundedCornerShape(cornerSize)
  return Modifier
    .shadow(
      elevation = shadowElevation,
      shape = shape,
      clip = true,
    )
    .background(
      color = color,
      shape = shape,
    )
    .clip(shape)
}

/**
 * Creates a [RoundedCornerShape] based on the provided [AdChoicesPlacement]
 * and corner size.
 *
 * @param cornerSize The corner size for the rounded corners.
 * @return A [RoundedCornerShape] that has rounded corners based on the [AdChoicesPlacement].
 */
fun AdChoicesPlacement.nativeAdRoundedCornerShape(
  cornerSize: Dp = 0.dp,
): RoundedCornerShape {
  return when (this) {
    AdChoicesPlacement.TOP_LEFT -> RoundedCornerShape(
      0.dp,
      cornerSize,
      cornerSize,
      cornerSize,
    )

    AdChoicesPlacement.TOP_RIGHT -> RoundedCornerShape(
      cornerSize,
      0.dp,
      cornerSize,
      cornerSize,
    )

    AdChoicesPlacement.BOTTOM_RIGHT -> RoundedCornerShape(
      cornerSize,
      cornerSize,
      0.dp,
      cornerSize,
    )

    AdChoicesPlacement.BOTTOM_LEFT -> RoundedCornerShape(
      cornerSize,
      cornerSize,
      cornerSize,
      0.dp,
    )
  }
}
