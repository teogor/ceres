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

package dev.teogor.ceres.main

import android.view.View
import androidx.databinding.library.baseAdapters.BR
import dagger.hilt.android.AndroidEntryPoint
import dev.chrisbanes.insetter.applyInsetter
import dev.teogor.ceres.R
import dev.teogor.ceres.databinding.ActivityMainBinding
import dev.teogor.ceres.extensions.isVisible
import dev.teogor.ceres.m3.app.BaseActivityM3

@AndroidEntryPoint
class MainActivity : BaseActivityM3<ActivityMainBinding, MainViewModel>() {

  override fun getContentView(): Int = R.layout.activity_main

  override fun getBindingVariable(): Int = BR.viewModel

  override fun getNavController(): Int = R.id.nav_controller

  override fun getViewModelClass() = MainViewModel::class.java

  override val splashExitAnimDuration: Long = 1000

  override val insetsViews: InsetsViews
    get() = InsetsViews(
      navController = binding.navController,
      bottomNavigation = binding.bottomNavigation,
      toolBar = binding.toolBar
    )

  override fun drawEdgeToEdge() {
    super.drawEdgeToEdge()

    binding.bottomNavigation.applyInsetter {
      type(navigationBars = true) {
        padding()
      }
    }
  }

  override fun setupObservers() {
    super.setupObservers()

    viewModel.showBottomNavigation.observe(this) {
      if (it) {
        showBottomAnim()
      } else {
        hideBottomAnim()
      }
    }

    /**
     * Navigation VM
     */
    viewModel.onActionButtonClicked.observe(this) {
      navigationViewModel.onActionButtonClicked.value = true
    }
  }

  override fun setupUi() {
    super.setupUi()

    setupBottomNavigation()
  }

  private fun setupBottomNavigation() {
    setupWithNavController(binding.bottomNavigation)
  }

  private fun showBottomAnim() {
    binding.bottomNavigation.isVisible = true
  }

  private fun hideBottomAnim() {
    binding.bottomNavigation.isVisible = false
  }

  override fun getSnackbarAnchor(): View {
    return binding.bottomNavigation
  }
}
