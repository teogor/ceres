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

package dev.teogor.ceres.ui.foundation.lib.resources.model

import androidx.compose.runtime.Immutable

@Immutable
@JvmInline
value class Strings private constructor(@Suppress("unused") private val value: Int) {
  companion object {
    val NavigationMenu = Strings(0)
    val CloseDrawer = Strings(1)
    val CloseSheet = Strings(2)
    val DefaultErrorMessage = Strings(3)
    val ExposedDropdownMenu = Strings(4)
    val SliderRangeStart = Strings(5)
    val SliderRangeEnd = Strings(6)
  }
}
