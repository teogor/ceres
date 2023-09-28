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

package dev.teogor.ceres.theme

import androidx.compose.ui.graphics.Color
import dev.teogor.ceres.framework.core.model.ThemeBuilder
import dev.teogor.ceres.ui.spectrum.utilities.asHexColor

/**
 * Default color used for configuring the Ceres theme.
 */
private val color = Color(39, 158, 31, 255)

/**
 * Configures the Ceres theme using a predefined color.
 *
 * @return A [ThemeBuilder] instance representing the configured theme.
 */
fun configureTheme() = ThemeBuilder(
  themeSeed = color.asHexColor(),
)
