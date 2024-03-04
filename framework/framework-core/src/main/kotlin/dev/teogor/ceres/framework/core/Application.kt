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

import dev.teogor.ceres.data.datastore.defaults.ThemeConfig
import dev.teogor.ceres.framework.core.model.ThemeBuilder

/**
 * Base Android Application class for the Ceres application.
 *
 * This class extends the Android [android.app.Application] class and provides the foundation
 * for initializing the application's theme and other configurations.
 */
open class Application : android.app.Application() {

  /**
   * The [ThemeBuilder] instance used to configure the theme for the Ceres application.
   *
   * This property initializes the theme configuration using [configureTheme].
   */
  open val themeBuilder: ThemeBuilder = ThemeBuilder(
    themeSeed = "#0B57D0",
  )

  /**
   * Called when the application is starting.
   */
  override fun onCreate() {
    super.onCreate()

    // Configure the application's theme
    configureTheme()
  }

  /**
   * Configures the theme for the application using the provided [themeBuilder].
   *
   * This function initializes the theme configuration for the Ceres application.
   */
  private fun configureTheme() {
    with(ThemeConfig) {
      seedHex = themeBuilder.themeSeed
    }
  }
}
