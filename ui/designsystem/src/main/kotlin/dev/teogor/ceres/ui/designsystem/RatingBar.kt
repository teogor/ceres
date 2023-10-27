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

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.StarHalf
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.teogor.ceres.ui.designsystem.core.ColorUtils.harmonizeWithPrimary
import kotlin.math.ceil
import kotlin.math.floor

@Composable
fun RatingBar(
  modifier: Modifier = Modifier,
  rating: Double = 0.0,
  stars: Int = 5,
  starsColor: Color = Color.Yellow.harmonizeWithPrimary(fraction = .4f),
  starSize: Dp = 24.dp,
) {
  val filledStars = floor(rating).toInt()
  val unfilledStars = (stars - ceil(rating)).toInt()
  val halfStar = !(rating.rem(1).equals(0.0))
  Row(modifier = modifier) {
    repeat(filledStars) {
      Icon(
        imageVector = Icons.Outlined.Star,
        contentDescription = null,
        tint = starsColor,
        modifier = Modifier.size(starSize),
      )
    }
    if (halfStar) {
      Icon(
        imageVector = Icons.AutoMirrored.Outlined.StarHalf,
        contentDescription = null,
        tint = starsColor,
        modifier = Modifier.size(starSize),
      )
    }
    repeat(unfilledStars) {
      Icon(
        imageVector = Icons.Outlined.StarOutline,
        contentDescription = null,
        tint = starsColor,
        modifier = Modifier.size(starSize),
      )
    }
  }
}
