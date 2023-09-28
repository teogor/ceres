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

package dev.teogor.ceres.screen.builder.compose

import androidx.compose.runtime.Composable
import dev.teogor.ceres.navigation.core.ScreenRoute
import dev.teogor.ceres.navigation.core.utilities.toScreenName
import dev.teogor.ceres.screen.builder.model.ViewBuilder
import dev.teogor.ceres.screen.builder.screenItems
import dev.teogor.ceres.screen.core.layout.LazyColumnLayoutBase

@Deprecated("to be replaced with ColumnLayoutBase")
@Composable
inline fun LazyColumnLayout(
  screenName: ScreenRoute,
  hasScrollbar: Boolean = true,
  noinline bottomContent: (@Composable () -> Unit)? = null,
  crossinline block: MutableList<ViewBuilder>.() -> Unit,
) = LazyColumnLayoutBase(
  screenName = screenName.toScreenName(),
  hasScrollbarBackground = false,
  hasScrollbar = hasScrollbar,
  bottomContent = bottomContent,
) {
  screenItems {
    block()
  }
}
