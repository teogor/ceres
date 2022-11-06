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

import androidx.databinding.library.baseAdapters.BR
import dagger.hilt.android.AndroidEntryPoint
import dev.teogor.ceres.R
import dev.teogor.ceres.databinding.FragmentM3Binding
import dev.teogor.ceres.m3.app.BaseFragmentM3

@AndroidEntryPoint
class M3Fragment : BaseFragmentM3<FragmentM3Binding, M3ViewModel>() {

  override fun getContentViewId(): Int = R.layout.fragment_m3

  override fun getBindingVariable() = BR.viewModel

  override fun getViewModelClass() = M3ViewModel::class.java
}
