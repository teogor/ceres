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
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderColors
import androidx.compose.material3.SliderDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import dev.teogor.ceres.ui.theme.MaterialTheme

@Composable
fun Slider(
  value: Float,
  onValueChange: (Float) -> Unit,
  modifier: Modifier = Modifier,
  enabled: Boolean = true,
  valueRange: ClosedFloatingPointRange<Float> = 0f..1f,
  steps: Int = 0,
  onValueChangeFinished: (() -> Unit)? = null,
  colors: SliderColors = SliderDefaults.colors(
    activeTrackColor = CeresSliderDefaults.sliderActiveContent(),
    inactiveTrackColor = CeresSliderDefaults.sliderInactiveContent(),
    thumbColor = CeresSliderDefaults.sliderActiveContent(),
  ),
  interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
  Slider(
    value = value,
    onValueChange = onValueChange,
    modifier = modifier,
    enabled = enabled,
    valueRange = valueRange,
    steps = steps,
    onValueChangeFinished = onValueChangeFinished,
    colors = colors,
    interactionSource = interactionSource,
  )
}

/**
 * Object to hold defaults used by [Slider]
 */
@Stable
object CeresSliderDefaults {
  @Composable
  fun sliderActiveContent() = MaterialTheme.colorScheme.primary

  @Composable
  fun sliderInactiveContent() = MaterialTheme.colorScheme.surfaceVariant
}
