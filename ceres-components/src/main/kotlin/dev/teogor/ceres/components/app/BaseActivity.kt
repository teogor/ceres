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

import android.animation.ValueAnimator
import android.content.Context
import android.content.res.Configuration
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.doOnLayout
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.DialogFragmentNavigator
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.navigation.NavigationBarView
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback
import dev.teogor.ceres.components.events.UiEvent
import dev.teogor.ceres.components.navigation.NavigationUI
import dev.teogor.ceres.components.navigation.NavigationViewModel
import dev.teogor.ceres.components.system.InsetsConfigurator
import dev.teogor.ceres.core.internal.WindowPreferencesManager
import dev.teogor.ceres.extensions.defaultResId
import dev.teogor.ceres.extensions.dpToPx
import dev.teogor.ceres.extensions.findNavController
import dev.teogor.ceres.extensions.hideKeyboard
import dev.teogor.ceres.extensions.safeCall
import java.util.concurrent.TimeUnit

abstract class BaseActivity<B : ViewDataBinding, VM : BaseViewModel> :
  AppCompatActivity(), BaseCommon {

  private var _binding: B? = null
  protected val binding: B
    get() = _binding!!

  lateinit var viewModel: VM
  lateinit var navigationViewModel: NavigationViewModel

  private var previousToast: Toast? = null

  open val splashExitAnimDuration: Long
    get() = TimeUnit.MILLISECONDS.toMillis(400L)

  // todo navController move to a module that contains firebase
  private val listener = NavController.OnDestinationChangedListener { controller, _, _ ->
    val bundle = Bundle()
    val currentFragmentClassName = when (controller.currentDestination) {
      is FragmentNavigator.Destination -> {
        (controller.currentDestination as FragmentNavigator.Destination).className
      }
      is DialogFragmentNavigator.Destination -> {
        (controller.currentDestination as DialogFragmentNavigator.Destination).className
      }
      else -> {
        "N/A"
      }
    }
    // bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, destination.label.toString())
    // bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, currentFragmentClassName)
    // analytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle)
  }

  open val insetsViews: InsetsViews = InsetsViews()

  private var lastInsets: InsetsConfigurator = InsetsConfigurator()
  private var navigationBarHeight: Int = 0
  private var bottomNavHeight: Int = 0
  private var floatingButtonHeight: Int = 0
  private var toolBarHeight: Int = 0
  private var statusBarHeight: Int = 0
  private var navControllerBottomPadding: Int = 0
  private var navControllerTopPadding: Int = 0

  override fun onCreate(savedInstanceState: Bundle?) {
    // Prevent splash screen showing up on configuration changes
    val splashScreen = if (savedInstanceState == null) installSplashScreen() else null

    // Set up shared element transition and disable overlay so views don't show above system bars
    window.requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)
    setExitSharedElementCallback(MaterialContainerTransformSharedElementCallback())
    window.sharedElementsUseOverlay = false

    super.onCreate(savedInstanceState)

    // Do not let the launcher create a new activity http://stackoverflow.com/questions/16283079
    if (!isTaskRoot) {
      finish()
      return
    }

    _binding = DataBindingUtil.setContentView(this, getContentView())
    viewModel = ViewModelProvider(this)[getViewModelClass()]
    navigationViewModel = ViewModelProvider(this)[NavigationViewModel::class.java]

    binding.lifecycleOwner = this
    binding.setVariable(getBindingVariable(), viewModel)

    drawEdgeToEdge()
    setSplashScreenExitAnimation(splashScreen)

    getNavHostFragment()?.apply {
      navController.addOnDestinationChangedListener(listener)
    }

    ViewCompat.setOnApplyWindowInsetsListener(binding.root) { _, insets ->
      val navBars = insets.getInsets(WindowInsetsCompat.Type.navigationBars())
      val statusBars = insets.getInsets(WindowInsetsCompat.Type.statusBars())
      if (navBars.bottom != navigationBarHeight || statusBars.top != statusBarHeight) {
        navigationBarHeight = navBars.bottom
        // todo fixme without /2 the status bar height is doubled
        statusBarHeight = statusBars.top / 2
        processInsets(lastInsets)
      }

      insets
    }
    binding.root.doOnLayout {
      with(insetsViews) {
        navController.safeCall {
          navControllerBottomPadding = paddingBottom
          navControllerTopPadding = paddingTop
        }
        toolBar.safeCall {
          toolBarHeight = height
        }
        bottomNavigation.safeCall {
          bottomNavHeight = height
        }
        floatingButton.safeCall {
          floatingButtonHeight = height
        }
      }

      processInsets(lastInsets)
    }
    setupUi()
    setupObservers()

    viewModel.onActivityCreated()
    viewModel.navigate.observe(this) { direction -> findNavController()?.navigate(direction) }
    viewModel.onBackPressed.observe(this) { findNavController()?.popBackStack() }
    viewModel.uiEventStream.observe(this) { uiEvent -> processUiEvent(uiEvent) }
    viewModel.insetsStream.observe(this) { insets -> processInsets(insets) }
  }

  @LayoutRes
  abstract fun getContentView(): Int

  abstract fun getBindingVariable(): Int

  protected abstract fun getViewModelClass(): Class<VM>

  @IdRes
  open fun getNavController(): Int = defaultResId

  protected open fun drawEdgeToEdge() {
    // Draw edge-to-edge
    WindowPreferencesManager().applyEdgeToEdgePreference(
      window
    )
    WindowCompat.setDecorFitsSystemWindows(window, false)
  }

  protected open fun setupUi() {
  }

  protected open fun setupObservers() {
  }

  open fun processUiEvent(uiEvent: UiEvent) {
    when (uiEvent) {
      is UiEvent.HideKeyboard -> {
        this.hideKeyboard(currentFocus ?: View(this))
      }

      is UiEvent.ShowToast -> {
        previousToast?.cancel()
        previousToast =
          Toast.makeText(this, getString(uiEvent.messageResId), Toast.LENGTH_LONG)
        previousToast!!.show()
      }

      is UiEvent.ShowToastString -> {
        previousToast?.cancel()
        previousToast = Toast.makeText(this, (uiEvent.message), Toast.LENGTH_LONG)
        previousToast!!.show()
      }

      is UiEvent.StartIntent -> {
        uiEvent.intentType.call()
      }
    }
  }

  private fun processInsets(insets: InsetsConfigurator) {
    lastInsets = insets
    with(insets) {
      val bottom = when (bottomInsets) {
        InsetsConfigurator.BottomInsets.None -> {
          navControllerBottomPadding
        }
        InsetsConfigurator.BottomInsets.NavigationBar -> {
          navControllerBottomPadding + navigationBarHeight
        }
        InsetsConfigurator.BottomInsets.BottomBar -> {
          navControllerBottomPadding + bottomNavHeight
        }
        InsetsConfigurator.BottomInsets.FloatingButton -> {
          navControllerBottomPadding + bottomNavHeight + floatingButtonHeight
        }
      }
      val top = when (topInsets) {
        InsetsConfigurator.TopInsets.None -> {
          navControllerTopPadding
        }
        InsetsConfigurator.TopInsets.StatusBar -> {
          navControllerTopPadding + statusBarHeight
        }
        InsetsConfigurator.TopInsets.ToolBar -> {
          navControllerTopPadding + statusBarHeight + toolBarHeight
        }
      }
      setPadding(bottom, top)
    }
  }

  private fun setPadding(bottom: Int, top: Int) {
    with(insetsViews) {
      navController.safeCall {
        setPadding(
          paddingLeft,
          top,
          paddingRight,
          bottom
        )
      }
    }
  }

  override fun onDestroy() {
    findNavController()?.removeOnDestinationChangedListener(listener)
    super.onDestroy()
    _binding?.unbind()
    _binding = null
  }

  override fun attachBaseContext(cxt: Context) {
    val resources = cxt.resources
    val configuration = Configuration(resources.configuration)
    // Required for the day/night theme to work
    configuration.uiMode = Configuration.UI_MODE_NIGHT_UNDEFINED
    val baseContext = cxt.createConfigurationContext(configuration)
    super.attachBaseContext(baseContext)
  }

  /**
   * Sets custom splash screen exit animation on devices prior to Android 12.
   *
   * When custom animation is used, status and navigation bar color will be set
   * to transparent and will be restored after the animation is finished.
   */
  private fun setSplashScreenExitAnimation(splashScreen: SplashScreen?) {
    val root = window.decorView.rootView
    val setNavbarScrim = {
      // Make sure navigation bar is on bottom before we modify it
      ViewCompat.setOnApplyWindowInsetsListener(root) { _, insets ->
        insets
      }
      ViewCompat.requestApplyInsets(root)
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
          duration = splashExitAnimDuration
          addUpdateListener { va ->
            val value = va.animatedValue as Float
            root.translationY = value * 16.dpToPx
          }
        }

        val splashAnim = ValueAnimator.ofFloat(1F, 0F).apply {
          interpolator = FastOutSlowInInterpolator()
          duration = splashExitAnimDuration
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

  /**
   * Navigation
   */
  fun getNavHostFragment() = supportFragmentManager.findFragmentById(
    getNavController()
  ) as NavHostFragment?

  fun findNavController(): NavController? = findNavController(getNavController())

  fun setupWithNavController(navigationBarView: NavigationBarView) {
    val navController = findNavController() ?: throw IllegalArgumentException(
      "NavController not found. Have you called getNavController() " +
        "in your ${this.javaClass.simpleName}?\nPlease add this " +
        "line `override fun getNavController(): Int = R.id.nav_controller`"
    )
    NavigationUI.setupWithNavController(
      navigationBarView,
      navController
    )
  }

  data class InsetsViews(
    val navController: View? = null,
    val bottomNavigation: View? = null,
    val floatingButton: View? = null,
    val toolBar: View? = null
  )
}
