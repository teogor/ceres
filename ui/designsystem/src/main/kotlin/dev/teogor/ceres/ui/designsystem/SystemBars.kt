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

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun SystemBarsBox(
  isStatusBarVisible: Boolean = false,
  isNavigationBarVisible: Boolean = true,
  content: @Composable ColumnScope.() -> Unit,
) {
  Column {
    if (isStatusBarVisible) {
      StatusBar()
    }
    content()
    if (isNavigationBarVisible) {
      NavigationBar()
    }
  }
}

@Composable
fun StatusBar(modifier: Modifier = Modifier) {
  Spacer(
    modifier = modifier
      .fillMaxWidth()
      .statusBarsPadding(),
  )
}

@Composable
fun NavigationBar(modifier: Modifier = Modifier) {
  Spacer(
    modifier = modifier
      .fillMaxWidth()
      .navigationBarsPadding(),
  )
}
