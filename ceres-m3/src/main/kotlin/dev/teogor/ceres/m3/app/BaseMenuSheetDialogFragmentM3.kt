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

package dev.teogor.ceres.m3.app

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorInt
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.fragment.findNavController
import dev.teogor.ceres.components.app.BaseActivity
import dev.teogor.ceres.components.events.UiEvent
import dev.teogor.ceres.components.navigation.NavigationViewModel
import dev.teogor.ceres.m3.elevation.SurfaceLevel
import dev.teogor.ceres.m3.theme.ElevationOverlay
import dev.teogor.ceres.m3.theme.ThemeM3
import dev.teogor.ceres.m3.widgets.menusheet.MenuSheetDialogFragment

abstract class BaseMenuSheetDialogFragmentM3<B : ViewDataBinding, VM : BaseViewModelM3> :
  MenuSheetDialogFragment() {

  private fun getSchemeColor() = ThemeM3.currentColorScheme()

  @ColorInt
  fun getBackgroundColorM3(): Int = ElevationOverlay(
    requireContext(),
    getSchemeColor()
  ).compositeOverlay(
    SurfaceLevel.Lvl1
  )

  protected lateinit var binding: B private set
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
    binding = DataBindingUtil.inflate(layoutInflater, getContentViewId(), container, false)
    return binding.root
  }

  override fun onViewCreated(savedInstanceState: Bundle?) {
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
    behaviour.materialShapeDrawable.fillColor = ColorStateList.valueOf(getBackgroundColorM3())
  }

  protected open fun processUiEvent(uiEvent: UiEvent) {
    (activity as? BaseActivity<*, *>)?.processUiEvent(uiEvent)
  }

  protected open fun setupObservers() {
  }
}
