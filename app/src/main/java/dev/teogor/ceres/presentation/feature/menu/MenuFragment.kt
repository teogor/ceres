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

package dev.teogor.ceres.presentation.feature.menu

import androidx.databinding.library.baseAdapters.BR
import dagger.hilt.android.AndroidEntryPoint
import dev.teogor.ceres.R
import dev.teogor.ceres.databinding.MenuSheetContentBinding
import dev.teogor.ceres.extensions.dpToPx
import dev.teogor.ceres.m3.app.BaseMenuSheetDialogFragmentM3

@AndroidEntryPoint
class MenuFragment : BaseMenuSheetDialogFragmentM3<MenuSheetContentBinding, MenuViewModel>() {

  override fun getContentViewId() = R.layout.menu_sheet_content

  override fun getBindingVariable() = BR.viewModel

  override fun getViewModelClass() = MenuViewModel::class.java

  override val topCornersSize: Float
    get() = 40.dpToPx.toFloat()

  override val horizontalMargin: Float
    get() = 20.dpToPx.toFloat()

  override val layoutRes: Int
    get() = R.layout.menu_sheet_content

  override val isFitToContent: Boolean
    get() = false

  override val halfExpandedRatio: Float
    get() = 0.7f

  override fun setupUi() {
    super.setupUi()

//        with(viewModel) {
//            binding.groupDeveloperOptions.show(
//                appSessionStorage.developerModeEnabled
//            )
//        }
  }

  override fun setupObservers() {
    super.setupObservers()

    with(viewModel) {
      uiOnClose.observe(this@MenuFragment) {
        dismiss()
      }
    }
  }
}
