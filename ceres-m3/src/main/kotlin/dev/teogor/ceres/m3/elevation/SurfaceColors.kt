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

package dev.teogor.ceres.m3.elevation

import android.content.Context
import androidx.annotation.ColorInt
import androidx.annotation.DimenRes
import androidx.annotation.Dimension
import dev.teogor.ceres.core.global.GlobalData.context
import dev.teogor.ceres.core.global.GlobalData.themedContext
import dev.teogor.ceres.m3.R

// todo color surface static for global elements
//  for instance ```
//  object SurfaceColorsConstants {
//    @ColorInt
//    val rootBackground = getRootBackgroundImpl()
//    @ColorInt
//    fun getRootBackgroundImpl() : Int {
//        return SurfaceColors(SurfaceLevel.Lvl0).color
//    }
//  }
//  ```

/**
 * Provides a convenient way to get color values of tonal variations of `R.attr.colorSurface`.
 */
class SurfaceColors(private val level: SurfaceLevel) {

  @ColorInt
  val color: Int = getColorImpl()

  private fun getColorImpl(): Int {
    return getColorForElevationLegacy(
      themedContext,
      getElevationDimension(level)
    )
  }

  @Dimension
  private fun getElevationDimension(level: SurfaceLevel): Float {
    return context.resources.getDimension(
      getElevationDimensionRes(level)
    )
  }

  @DimenRes
  private fun getElevationDimensionRes(level: SurfaceLevel): Int {
    return when (level) {
      SurfaceLevel.Lvl0 -> R.dimen.m3_elevation_level0
      SurfaceLevel.Lvl1 -> R.dimen.m3_elevation_level1
      SurfaceLevel.Lvl2 -> R.dimen.m3_elevation_level2
      SurfaceLevel.Lvl3 -> R.dimen.m3_elevation_level3
      SurfaceLevel.Lvl4 -> R.dimen.m3_elevation_level4
      SurfaceLevel.Lvl5 -> R.dimen.m3_elevation_level5
      SurfaceLevel.Lvl6 -> R.dimen.m3_elevation_level6
      SurfaceLevel.Lvl7 -> R.dimen.m3_elevation_level7
      SurfaceLevel.Lvl8 -> R.dimen.m3_elevation_level8
      SurfaceLevel.Transparent -> R.dimen.m3_elevation_level8
      SurfaceLevel.Unknown -> R.dimen.m3_elevation_level8
    }
  }

  @ColorInt
  private fun getColorForElevation(context: Context, @Dimension elevation: Float): Int {
    return ElevationOverlayProvider(context).compositeOverlay(
      dev.teogor.ceres.m3.theme.ThemeM3.currentColorScheme().surfaceTint,
      elevation
    )
  }

  @ColorInt
  @Deprecated("It breaks the theming")
  private fun getColorForElevationLegacy(context: Context, @Dimension elevation: Float): Int {
    // todo ThemeStoreMemo().primaryColor breaks app
    return ElevationOverlayProvider(context).compositeOverlay(
//            MaterialColors.getColor(
//                context,
//                R.attr.colorSurface,
//                Color.TRANSPARENT
//            ),
      elevation
    )
  }
}
