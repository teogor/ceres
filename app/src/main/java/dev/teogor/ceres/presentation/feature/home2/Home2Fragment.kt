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

package dev.teogor.ceres.presentation.feature.home2

import androidx.databinding.library.baseAdapters.BR
import dagger.hilt.android.AndroidEntryPoint
import dev.teogor.ceres.R
import dev.teogor.ceres.databinding.FragmentHome2Binding
import dev.teogor.ceres.m3.app.BaseFragmentM3

@AndroidEntryPoint
class Home2Fragment : BaseFragmentM3<FragmentHome2Binding, Home2ViewModel>() {

  override fun getContentViewId(): Int = R.layout.fragment_home2

  override fun getBindingVariable() = BR.viewModel

  override fun getViewModelClass() = Home2ViewModel::class.java
}
