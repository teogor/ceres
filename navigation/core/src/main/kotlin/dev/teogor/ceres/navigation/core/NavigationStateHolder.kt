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

package dev.teogor.ceres.navigation.core

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf

class NavigationParameters {
  var uri: Uri? by mutableStateOf(null)
  var isMenuVisible: Boolean? by mutableStateOf(
    null,
  )
  var screenRoute: ScreenRoute? by mutableStateOf(null)

  fun showMenu() {
    isMenuVisible = true
  }

  fun hideMenu() {
    isMenuVisible = false
  }
}

val LocalNavigationParameters = staticCompositionLocalOf { NavigationParameters() }
