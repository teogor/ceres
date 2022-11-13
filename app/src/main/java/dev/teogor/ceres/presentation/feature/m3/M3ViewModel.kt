/*
 * Copyright 2022 teogor (Teodor Grigor) All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dev.teogor.ceres.presentation.feature.m3

import dagger.hilt.android.lifecycle.HiltViewModel
import dev.teogor.ceres.R
import dev.teogor.ceres.components.system.InsetsConfigurator
import dev.teogor.ceres.components.toolbar.ToolbarType
import dev.teogor.ceres.components.view.ToolBar
import dev.teogor.ceres.m3.app.BaseViewModelM3
import javax.inject.Inject

@HiltViewModel
class M3ViewModel @Inject constructor() : BaseViewModelM3() {

  override val toolBarBuilder: ToolBar.Builder
    get() = ToolBar.Builder(
      type = ToolbarType.COLLAPSABLE,
      title = R.string.m3
    )

  override val insets: InsetsConfigurator
    get() = InsetsConfigurator(
      bottomInsets = InsetsConfigurator.BottomInsets.BottomBar,
      topInsets = InsetsConfigurator.TopInsets.ToolBar
    )

  override fun onFragmentCreated() {
    super.onFragmentCreated()

    showBottomNavigation(true)
  }
}
