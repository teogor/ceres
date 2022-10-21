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

package dev.teogor.ceres.m3.theme

import androidx.annotation.ColorInt
import androidx.annotation.DimenRes
import androidx.annotation.Dimension
import androidx.annotation.FloatRange
import androidx.core.graphics.ColorUtils
import com.google.android.material.color.MaterialColors
import com.zeoflow.startup.ktx.ApplicationInitializer
import dev.teogor.ceres.core.global.GlobalData
import dev.teogor.ceres.core.logger.Logger
import dev.teogor.ceres.extensions.blendColors
import dev.teogor.ceres.m3.R
import dev.teogor.ceres.m3.elevation.SurfaceLevel
import kotlin.math.ln1p

class SurfaceOverlay(
  @get:ColorInt @param:ColorInt
  val themeOverlayColor: Int,
  @get:ColorInt @param:ColorInt
  val themeSurfaceColor: Int
) : Logger {

  private val displayDensity: Float
    get() = ApplicationInitializer.context.resources.displayMetrics.density

  /**
   * Surface Colors Composition
   */
  fun forSurface(level: SurfaceLevel): Int {
    val backgroundColor = themeOverlayColor
    val overlayAlphaFraction = calculateOverlayAlphaFraction(
      getElevationDimension(level)
    )
    val backgroundColorOpaque = ColorUtils.setAlphaComponent(backgroundColor, 255)
    return MaterialColors.layer(
      backgroundColorOpaque,
      themeSurfaceColor,
      overlayAlphaFraction
    )
  }

  fun forAlpha(
    @FloatRange(from = 0.0, to = 1.0) overlayAlpha: Float
  ): Int {
    // return MaterialColors.layer(
    //     themeOverlayColor, themeSurfaceColor, overlayAlpha
    // )
    return blendColors(
      themeOverlayColor,
      themeSurfaceColor,
      overlayAlpha
    )
  }

  /**
   * Calculates the alpha fraction, between 0 and 1, that should be used with the elevation overlay
   * color, based on the provided `elevation` value.
   */
  private fun calculateOverlayAlphaFraction(elevation: Float): Float {
    if (displayDensity <= 0 || elevation <= 0) {
      return 0f
    }
    val elevationDp = elevation / displayDensity
    val alphaFraction = (
      FORMULA_MULTIPLIER * ln1p(elevationDp.toDouble())
        .toFloat() + FORMULA_OFFSET
      ) / 100
    return alphaFraction.coerceAtMost(1f)
  }

  @Dimension
  private fun getElevationDimension(level: SurfaceLevel): Float {
    return GlobalData.context().resources.getDimension(
      getElevationDimensionRes(level)
    )
  }

  @DimenRes
  private fun getElevationDimensionRes(level: SurfaceLevel): Int {
    return when (level) {
      // we are not supposed to reach here
      SurfaceLevel.Transparent -> -1
      SurfaceLevel.Lvl0 -> R.dimen.m3_elevation_level0
      SurfaceLevel.Lvl1 -> R.dimen.m3_elevation_level1
      SurfaceLevel.Lvl2 -> R.dimen.m3_elevation_level2
      SurfaceLevel.Lvl3 -> R.dimen.m3_elevation_level3
      SurfaceLevel.Lvl4 -> R.dimen.m3_elevation_level4
      SurfaceLevel.Lvl5 -> R.dimen.m3_elevation_level5
      SurfaceLevel.Lvl6 -> R.dimen.m3_elevation_level6
      SurfaceLevel.Lvl7 -> R.dimen.m3_elevation_level7
      SurfaceLevel.Lvl8 -> R.dimen.m3_elevation_level8
      SurfaceLevel.Unknown -> R.dimen.m3_elevation_level8
    }
  }

  companion object {
    private const val FORMULA_MULTIPLIER = 4.5f
    private const val FORMULA_OFFSET = 2f
  }
}
