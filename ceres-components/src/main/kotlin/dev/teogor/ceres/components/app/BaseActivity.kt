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

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.DialogFragmentNavigator
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback
import dev.teogor.ceres.components.events.UiEvent
import dev.teogor.ceres.components.navigation.NavigationViewModel
import dev.teogor.ceres.core.internal.WindowPreferencesManager
import dev.teogor.ceres.extensions.findNavController
import dev.teogor.ceres.extensions.hideKeyboard

abstract class BaseActivity<B : ViewDataBinding, VM : BaseViewModel> :
  AppCompatActivity(), BaseCommon {

  protected lateinit var binding: B

  lateinit var viewModel: VM
  lateinit var navigationViewModel: NavigationViewModel

  private var previousToast: Toast? = null

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

    binding = DataBindingUtil.setContentView(this, getContentView())
    viewModel = ViewModelProvider(this)[getViewModelClass()]
    navigationViewModel = ViewModelProvider(this)[NavigationViewModel::class.java]

    binding.lifecycleOwner = this
    binding.setVariable(getBindingVariable(), viewModel)

    drawEdgeToEdge(splashScreen)

    val navHostFragment =
      supportFragmentManager.findFragmentById(getNavController()) as NavHostFragment
    val navController = navHostFragment.navController
    navController.addOnDestinationChangedListener(listener)

    setupUi()
    setupObservers()

    viewModel.onActivityCreated()
    viewModel.navigate.observe(this) { direction -> findNavController().navigate(direction) }
    viewModel.onBackPressed.observe(this) { findNavController().popBackStack() }
    viewModel.uiEventStream.observe(this) { uiEvent -> processUiEvent(uiEvent) }
  }

  @LayoutRes
  abstract fun getContentView(): Int

  abstract fun getBindingVariable(): Int

  protected abstract fun getViewModelClass(): Class<VM>

  @IdRes
  open fun getNavController(): Int = -1

  protected open fun drawEdgeToEdge(splashScreen: SplashScreen?) {
    // Draw edge-to-edge
    WindowPreferencesManager().applyEdgeToEdgePreference(
      window
    )
    WindowCompat.setDecorFitsSystemWindows(window, false)
  }

  fun findNavController(): NavController {
    return findNavController(getNavController())
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

  private val listener =
    NavController.OnDestinationChangedListener { controller, _, _ ->
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
//           todo navController move to a module that contains firebase
//            bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, destination.label.toString())
//            bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, currentFragmentClassName)
//            analytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle)
    }

  override fun onDestroy() {
    findNavController().removeOnDestinationChangedListener(listener)
    super.onDestroy()
  }

  override fun attachBaseContext(cxt: Context) {
    val resources = cxt.resources
    val configuration = Configuration(resources.configuration)
    // Required for the day/night theme to work
    configuration.uiMode = Configuration.UI_MODE_NIGHT_UNDEFINED
    val baseContext = cxt.createConfigurationContext(configuration)
    super.attachBaseContext(baseContext)
  }
}
