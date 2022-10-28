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

package dev.teogor.ceres.components.app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.fragment.findNavController
import dev.teogor.ceres.components.events.UiEvent
import dev.teogor.ceres.components.navigation.NavigationViewModel

abstract class BaseFragment<B : ViewDataBinding, VM : BaseViewModel> : Fragment(), BaseCommon {

  private var _binding: B? = null
  protected val binding: B
    get() = _binding!!

  protected lateinit var viewModel: VM private set
  protected lateinit var navigationViewModel: NavigationViewModel

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    viewModel = ViewModelProvider(getViewModelScope())[getViewModelClass()]
    viewModel.navigate.observe(this) { direction -> findNavController().navigate(direction) }
    viewModel.onBackPressed.observe(this) { findNavController().popBackStack() }
    viewModel.uiEventStream.observe(this) { uiEvent -> processUiEvent(uiEvent) }
    viewModel.onFragmentCreated()

    navigationViewModel = activity!!.run {
      ViewModelProvider(this)[NavigationViewModel::class.java]
    }
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    _binding = DataBindingUtil.inflate(layoutInflater, getContentViewId(), container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    binding.lifecycleOwner = this
    binding.setVariable(getBindingVariable(), viewModel)

    setupUi()
    setupObservers()
  }

  protected open fun getViewModelScope(): ViewModelStoreOwner = this

  @LayoutRes
  protected abstract fun getContentViewId(): Int

  protected abstract fun getBindingVariable(): Int

  protected abstract fun getViewModelClass(): Class<VM>

  protected open fun setupUi() {
  }

  open fun onRefresh() {
    viewModel.onRefresh()
  }

  protected open fun processUiEvent(uiEvent: UiEvent) {
    (activity as? BaseActivity<*, *>)?.processUiEvent(uiEvent)
  }

  protected open fun setupObservers() {
    viewModel.toolbarViewData.observe(viewLifecycleOwner) {
      (activity as? BaseActivity<*, *>)?.viewModel?.toolbarViewData?.value = it
    }
    viewModel.showBottomNavigation.observe(viewLifecycleOwner) {
      (activity as? BaseActivity<*, *>)?.viewModel?.showBottomNavigation?.value = it
    }
    // todo move this to themed fragment
    viewModel.onThemeChanged.observe(viewLifecycleOwner) {
      (activity as? BaseActivity<*, *>)?.viewModel?.onThemeChanged?.value = it
    }
  }

  override fun onDestroyView() {
    super.onDestroyView()
    _binding?.unbind()
    _binding = null
  }
}
