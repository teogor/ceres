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

package dev.teogor.ceres.presentation.feature.home

import androidx.databinding.library.baseAdapters.BR
import dagger.hilt.android.AndroidEntryPoint
import dev.teogor.ceres.R
import dev.teogor.ceres.databinding.FragmentHomeBinding
import dev.teogor.ceres.m3.app.BaseFragmentM3

@AndroidEntryPoint
class HomeFragment : BaseFragmentM3<FragmentHomeBinding, HomeViewModel>() {

  override fun getContentViewId(): Int = R.layout.fragment_home

  override fun getBindingVariable() = BR.viewModel

  override fun getViewModelClass() = HomeViewModel::class.java

  override fun onRefresh() {
    super.onRefresh()

    binding.root.scrollToTop()
  }
}
