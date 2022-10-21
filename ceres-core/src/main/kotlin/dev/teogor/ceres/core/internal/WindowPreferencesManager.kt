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

package dev.teogor.ceres.core.internal

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.os.Build
import android.view.View
import android.view.Window
import android.view.WindowInsets
import androidx.core.view.OnApplyWindowInsetsListener
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import dev.teogor.ceres.core.internal.EdgeToEdgeUtils.applyEdgeToEdge

/**
 * Helper that saves the current window preferences for the Catalog.
 */
class WindowPreferencesManager {
  @SuppressLint("WrongConstant")
  private val listener: OnApplyWindowInsetsListener =
    OnApplyWindowInsetsListener { v: View, insets: WindowInsetsCompat ->
      if (v.resources.configuration.orientation
        != Configuration.ORIENTATION_LANDSCAPE
      ) {
        return@OnApplyWindowInsetsListener insets
      }
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        v.setPadding(
          insets.getInsets(WindowInsets.Type.systemBars()).left,
          0,
          insets.getInsets(WindowInsets.Type.systemBars()).right,
          insets.getInsets(WindowInsets.Type.systemBars()).bottom
        )
      } else {
        v.setPadding(
          insets.stableInsetLeft,
          0,
          insets.stableInsetRight,
          insets.stableInsetBottom
        )
      }
      insets
    }

  fun applyEdgeToEdgePreference(window: Window) {
    applyEdgeToEdge(window, true)
    ViewCompat.setOnApplyWindowInsetsListener(
      window.decorView,
      listener
    )
  }
}
