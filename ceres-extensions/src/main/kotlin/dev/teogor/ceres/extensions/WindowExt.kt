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

package dev.teogor.ceres.extensions

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.view.Window
import android.view.WindowManager
import com.google.android.material.elevation.ElevationOverlayProvider
import dev.teogor.ceres.extensions.internal.system.InternalResourceHelper

/**
 * Sets navigation bar color to transparent if system's config_navBarNeedsScrim is false,
 * otherwise it will use the theme navigationBarColor with 70% opacity.
 */
fun Window.setNavigationBarTransparentCompat(context: Context, elevation: Float = 0F) {
  navigationBarColor = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q &&
    !InternalResourceHelper.getBoolean(context, "config_navBarNeedsScrim", true)
  ) {
    Color.TRANSPARENT
  } else {
    // Set navbar scrim 70% of navigationBarColor
    ElevationOverlayProvider(context).compositeOverlayIfNeeded(
      context.getResourceColor(android.R.attr.navigationBarColor, 0.7F),
      elevation
    )
  }
}

fun Window.setSecureScreen(enabled: Boolean) {
  if (enabled) {
    setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE)
  } else {
    clearFlags(WindowManager.LayoutParams.FLAG_SECURE)
  }
}
