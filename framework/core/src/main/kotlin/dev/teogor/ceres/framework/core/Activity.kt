/*
 * Copyright 2023 teogor (Teodor Grigor)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dev.teogor.ceres.framework.core

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.ProvidedValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.metrics.performance.JankStats
import dev.teogor.ceres.core.foundation.DefaultResources
import dev.teogor.ceres.core.foundation.NetworkMonitorUtility
import dev.teogor.ceres.core.foundation.audioManagerUtils
import dev.teogor.ceres.core.foundation.compositions.LocalAudioManager
import dev.teogor.ceres.core.foundation.compositions.LocalMediaPlayer
import dev.teogor.ceres.core.foundation.compositions.LocalResources
import dev.teogor.ceres.core.foundation.mediaPlayerUtils
import dev.teogor.ceres.data.compose.rememberPreference
import dev.teogor.ceres.data.datastore.defaults.AppTheme
import dev.teogor.ceres.data.datastore.defaults.ceresPreferences
import dev.teogor.ceres.firebase.crashlytics.CrashlyticsHelper
import dev.teogor.ceres.firebase.crashlytics.LocalCrashlyticsHelper
import dev.teogor.ceres.framework.core.app.CeresApp
import dev.teogor.ceres.framework.core.model.MenuConfig
import dev.teogor.ceres.framework.core.model.NavGraphOptions
import dev.teogor.ceres.monetisation.ads.AdsControlProvider
import dev.teogor.ceres.monetisation.ads.ExperimentalAdsControlApi
import dev.teogor.ceres.monetisation.ads.LocalAdsControl
import dev.teogor.ceres.navigation.core.LocalNavigationParameters
import dev.teogor.ceres.navigation.core.NavigationParameters
import dev.teogor.ceres.navigation.core.ScreenRoute
import dev.teogor.ceres.navigation.core.models.NavigationItem
import dev.teogor.ceres.ui.foundation.config.FeedbackConfig
import dev.teogor.ceres.ui.theme.core.Theme
import javax.inject.Inject

open class Activity : AppCompatActivity() {

  private var navigationParameters by mutableStateOf(NavigationParameters())

  /**
   * API
   */
  @Inject
  lateinit var networkMonitor: NetworkMonitorUtility

  /**
   * Lazily inject [JankStats], which is used to track jank throughout the app.
   */
  @Inject
  lateinit var lazyStats: dagger.Lazy<JankStats>

  @Inject
  lateinit var crashlyticsHelper: CrashlyticsHelper

  open val navigationItems: List<NavigationItem> = emptyList()

  open fun handleUriVariants(uri: Uri): ScreenRoute? = null

  @Composable
  open fun NavGraphOptions.BuildNavGraph() = Unit

  @Composable
  open fun MenuConfig.buildMenu() = this

  /**
   *
   */
  @OptIn(ExperimentalMaterial3WindowSizeClassApi::class, ExperimentalAdsControlApi::class)
  override fun onCreate(savedInstanceState: Bundle?) {
    val splashScreen = installSplashScreen()
    super.onCreate(savedInstanceState)

    // Turn off the decor fitting system windows, which allows us to handle insets,
    // including IME animations, and go edge-to-edge
    // This also sets up the initial system bar style based on the platform theme
    enableEdgeToEdge()

    handleSplashScreen(splashScreen)

    val context = this as Context
    val audioManagerUtils = context.audioManagerUtils()
    val mediaPlayerUtils = context.mediaPlayerUtils()
    val resources = DefaultResources(resources)
    val adsControl = AdsControlProvider.adsControl

    setContent {
      val darkTheme = isSystemInDarkTheme()

      val ceresPreferences = remember {
        ceresPreferences()
      }

      val appTheme = rememberPreference(
        ceresPreferences.getAppThemeFlow(),
        ceresPreferences.appTheme,
      )

      val justBlackTheme = rememberPreference(
        ceresPreferences.getJustBlackThemeFlow(),
        ceresPreferences.justBlack,
      )

      val disableDynamicTheming = rememberPreference(
        ceresPreferences.getDisableDynamicThemingFlow(),
        ceresPreferences.disableDynamicTheming,
      )

      val disableAndroidTheme = rememberPreference(
        ceresPreferences.getDisableAndroidThemeFlow(),
        ceresPreferences.disableAndroidTheme,
      )

      val themeSeed = rememberPreference(
        ceresPreferences.getThemeSeedFlow(),
        ceresPreferences.themeSeed,
      )

      val disableAudioFeedback = rememberPreference(
        ceresPreferences.getDisableSoundFeedbackFlow(),
        ceresPreferences.disableSoundFeedback,
      )

      val disableVibrationFeedback = rememberPreference(
        ceresPreferences.getDisableVibrationFeedbackFlow(),
        ceresPreferences.disableVibrationFeedback,
      )

      LaunchedEffect(disableAudioFeedback, disableVibrationFeedback) {
        FeedbackConfig.disableVibrationFeedback = disableVibrationFeedback
        FeedbackConfig.disableAudioFeedback = disableAudioFeedback
      }

      val darkThemeActivated = remember(appTheme, justBlackTheme, darkTheme) {
        when (appTheme) {
          AppTheme.FollowSystem -> {
            darkTheme
          }

          AppTheme.ClearlyWhite -> {
            false
          }

          AppTheme.KindaDark -> {
            true
          }

          else -> {
            false
          }
        }
      }

      // Update the dark content of the system bars to match the theme
      DisposableEffect(darkThemeActivated) {
        enableEdgeToEdge(
          statusBarStyle = SystemBarStyle.auto(
            android.graphics.Color.TRANSPARENT,
            android.graphics.Color.TRANSPARENT,
          ) { darkThemeActivated },
          navigationBarStyle = SystemBarStyle.auto(
            lightScrim,
            darkScrim,
          ) { darkThemeActivated },
        )
        onDispose {}
      }

      Theme(
        darkTheme = darkThemeActivated,
        androidTheme = disableAndroidTheme,
        disableDynamicTheming = disableDynamicTheming,
        themeSeed = themeSeed,
      ) {
        CompositionLocalProvider(
          LocalNavigationParameters provides navigationParameters,
          // LocalAnalyticsHelper provides analyticsHelper,
          LocalCrashlyticsHelper provides crashlyticsHelper,

          // Ceres Core Foundation - Composition Provider
          LocalAudioManager provides audioManagerUtils,
          LocalMediaPlayer provides mediaPlayerUtils,
          LocalResources provides resources,

          // Ceres Monetization
          LocalAdsControl provides adsControl,

          *compositionProviders().toTypedArray(),
        ) {
          val menuConfig = MenuConfig().apply { buildMenu() }
          val menuConfigHeader =
            remember(menuConfig.headerContent ?: {}) { menuConfig.headerContent!! }
          val menuConfigContent =
            remember(menuConfig.menuContent ?: {}) { menuConfig.menuContent!! }

          CeresApp(
            windowSizeClass = calculateWindowSizeClass(this),
            networkMonitor = networkMonitor,
            navigationItems = navigationItems,
            menuSheetContent = menuConfigContent,
            headerContent = menuConfigHeader,
          ) { windowSizeClass, ceresAppState, baseActions, padding ->
            NavGraphOptions(
              windowSizeClass = windowSizeClass,
              ceresAppState = ceresAppState,
              baseActions = baseActions,
              padding = padding,
            ).BuildNavGraph()
          }
        }
      }
    }

    handleIntent(intent)
  }

  /**
   * Provides a list of [ProvidedValue] instances that can be used to initialize CompositionLocal values.
   *
   * @return A list of [ProvidedValue] instances.
   */
  @Composable
  open fun compositionProviders(): List<ProvidedValue<*>> = emptyList()

  private fun handleSplashScreen(splashScreen: SplashScreen) {
    // Keep the splash screen on-screen until the UI state is loaded. This condition is
    // evaluated each time the app needs to be redrawn so it should be fast to avoid blocking
    // the UI.
    splashScreen.setKeepOnScreenCondition {
      setKeepOnScreenCondition()
    }
  }

  open fun setKeepOnScreenCondition(): Boolean = false

  override fun onNewIntent(intent: Intent?) {
    super.onNewIntent(intent)
    handleIntent(intent)
  }

  private fun handleIntent(intent: Intent?) {
    intent?.data?.let {
      navigationParameters.uri = it
    }
  }

  fun showMenu(isVisible: Boolean) {
    if (isVisible) {
      navigationParameters.showMenu()
    } else {
      navigationParameters.hideMenu()
    }
  }

  fun navigateTo(screenRoute: ScreenRoute) {
    navigationParameters.screenRoute = screenRoute
  }

  override fun onResume() {
    super.onResume()
    lazyStats.get().isTrackingEnabled = true
  }

  override fun onPause() {
    super.onPause()
    lazyStats.get().isTrackingEnabled = false
  }
}

/**
 * The default light scrim, as defined by androidx and the platform:
 * https://cs.android.com/androidx/platform/frameworks/support/+/androidx-main:activity/activity/src/main/java/androidx/activity/EdgeToEdge.kt;l=35-38;drc=27e7d52e8604a080133e8b842db10c89b4482598
 */
private val lightScrim = android.graphics.Color.argb(0xe6, 0xFF, 0xFF, 0xFF)

/**
 * The default dark scrim, as defined by androidx and the platform:
 * https://cs.android.com/androidx/platform/frameworks/support/+/androidx-main:activity/activity/src/main/java/androidx/activity/EdgeToEdge.kt;l=40-44;drc=27e7d52e8604a080133e8b842db10c89b4482598
 */
private val darkScrim = android.graphics.Color.argb(0x80, 0x1b, 0x1b, 0x1b)
