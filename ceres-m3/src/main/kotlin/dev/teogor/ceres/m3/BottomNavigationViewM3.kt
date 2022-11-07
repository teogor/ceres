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

package dev.teogor.ceres.m3

import android.content.Context
import android.util.AttributeSet
import com.google.android.material.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import dev.teogor.ceres.extensions.blendColors
import dev.teogor.ceres.extensions.colorStateList
import dev.teogor.ceres.m3.elevation.SurfaceLevel
import dev.teogor.ceres.m3.theme.IThemeM3
import dev.teogor.ceres.m3.theme.SurfaceOverlay
import dev.teogor.ceres.m3.widgets.extension.setItemColors

class BottomNavigationViewM3 @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = R.attr.bottomNavigationStyle,
  defStyleRes: Int = R.style.Widget_Design_BottomNavigationView
) : BottomNavigationView(context, attrs, defStyleAttr, defStyleRes), IThemeM3 {

  init {
    if (!isInEditMode) {
      onThemeChanged()
    }
  }

  override fun onThemeChanged() {
    super.onThemeChanged()

    val normalColor = blendColors(
      colorScheme().secondary,
      colorScheme().onBackground,
      0.9f
    )
    val selectedColor = colorScheme().secondary
    setItemColors(normalColor, selectedColor)
    setBackgroundColor(
      SurfaceOverlay(
        themeOverlayColor = colorScheme().surface,
        themeSurfaceColor = colorScheme().surfaceTint
      ).forSurface(SurfaceLevel.Lvl3)
    )
    itemRippleColor = colorScheme().secondaryContainer.colorStateList
    itemActiveIndicatorColor = colorScheme().secondaryContainer.colorStateList
  }
}
