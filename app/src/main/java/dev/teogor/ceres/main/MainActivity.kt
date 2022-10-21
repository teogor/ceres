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

import android.animation.ValueAnimator
import android.graphics.Color
import android.os.Build
import android.view.View
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.library.baseAdapters.BR
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator
import dagger.hilt.android.AndroidEntryPoint
import dev.chrisbanes.insetter.applyInsetter
import dev.teogor.ceres.R
import dev.teogor.ceres.components.navigation.NavigationUI
import dev.teogor.ceres.databinding.ActivityMainBinding
import dev.teogor.ceres.extensions.dpToPx
import dev.teogor.ceres.extensions.isVisible
import dev.teogor.ceres.extensions.setNavigationBarTransparentCompat
import dev.teogor.ceres.m3.app.BaseActivityM3

@AndroidEntryPoint
class MainActivity : BaseActivityM3<ActivityMainBinding, MainViewModel>() {

  // To be checked by splash screen. If true then splash screen will be removed.
  var ready = false

  override fun getContentView(): Int = R.layout.activity_main

  override fun getBindingVariable(): Int = BR.viewModel

  override fun getNavController(): Int = R.id.nav_controller

  override fun getViewModelClass() = MainViewModel::class.java

  override fun drawEdgeToEdge(splashScreen: SplashScreen?) {
    super.drawEdgeToEdge(splashScreen)

    binding.bottomNavigation.applyInsetter {
      type(navigationBars = true) {
        padding()
      }
    }

    val startTime = System.currentTimeMillis()
    val elapsed = System.currentTimeMillis() - startTime
    val keepSplashOnScreen =
      elapsed <= SPLASH_MIN_DURATION || !ready && elapsed <= SPLASH_MAX_DURATION
    // splashScreen?.setKeepOnScreenCondition{keepSplashOnScreen}

    setSplashScreenExitAnimation(splashScreen)
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
    NavigationUI.setupWithNavController(
      binding.bottomNavigation,
      findNavController()
    )
  }

  private fun showBottomAnim() {
//        binding.bottomNavigation.animate()
//            .translationY(0f)
//            .duration = 300
    binding.bottomNavigation.isVisible = true
  }

  private fun hideBottomAnim() {
//        binding.bottomNavigation.animate()
//            .translationY(binding.bottomNavigation.height.toFloat())
//            .duration = 300
    binding.bottomNavigation.isVisible = false
  }

  /**
   * Sets custom splash screen exit animation on devices prior to Android 12.
   *
   * When custom animation is used, status and navigation bar color will be set
   * to transparent and will be restored after the animation is finished.
   */
  private fun setSplashScreenExitAnimation(splashScreen: SplashScreen?) {
    val setNavbarScrim = {
      // Make sure navigation bar is on bottom before we modify it
      ViewCompat.setOnApplyWindowInsetsListener(binding.root) { _, insets ->
        if (insets.getInsets(WindowInsetsCompat.Type.navigationBars()).bottom > 0) {
          val elevation = binding.bottomNavigation.elevation
          window.setNavigationBarTransparentCompat(this@MainActivity, elevation)
        }
        insets
      }
      ViewCompat.requestApplyInsets(binding.root)
    }

    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S && splashScreen != null) {
      val oldStatusColor = window.statusBarColor
      val oldNavigationColor = window.navigationBarColor
      window.statusBarColor = Color.TRANSPARENT
      window.navigationBarColor = Color.TRANSPARENT

      splashScreen.setOnExitAnimationListener { splashProvider ->
        // For some reason the SplashScreen applies (incorrect) Y translation to the iconView
        splashProvider.iconView.translationY = 0F

        val activityAnim = ValueAnimator.ofFloat(1F, 0F).apply {
          interpolator = LinearOutSlowInInterpolator()
          duration = SPLASH_EXIT_ANIM_DURATION
          addUpdateListener { va ->
            val value = va.animatedValue as Float
            binding.root.translationY = value * 16.dpToPx
          }
        }

        val splashAnim = ValueAnimator.ofFloat(1F, 0F).apply {
          interpolator = FastOutSlowInInterpolator()
          duration = SPLASH_EXIT_ANIM_DURATION
          addUpdateListener { va ->
            val value = va.animatedValue as Float
            splashProvider.view.alpha = value
          }
          doOnEnd {
            splashProvider.remove()
            window.statusBarColor = oldStatusColor
            window.navigationBarColor = oldNavigationColor
            setNavbarScrim()
          }
        }

        activityAnim.start()
        splashAnim.start()
      }
    } else {
      setNavbarScrim()
    }
  }

  override fun getSnackbarAnchor(): View {
    return binding.bottomNavigation
  }

  companion object {
    // Splash screen
    private const val SPLASH_MIN_DURATION = 500 // ms
    private const val SPLASH_MAX_DURATION = 5000 // ms
    private const val SPLASH_EXIT_ANIM_DURATION = 400L // ms
  }
}
