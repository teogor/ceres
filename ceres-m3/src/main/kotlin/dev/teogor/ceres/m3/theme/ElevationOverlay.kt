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

import android.content.Context
import android.graphics.Color
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.DimenRes
import androidx.annotation.Dimension
import androidx.core.graphics.ColorUtils
import com.google.android.material.color.MaterialColors
import dev.teogor.ceres.core.global.GlobalData
import dev.teogor.ceres.core.internal.ViewUtils
import dev.teogor.ceres.m3.R
import dev.teogor.ceres.m3.elevation.SurfaceLevel
import kotlin.math.ln1p
import kotlin.math.roundToInt

/**
 * Utility for calculating elevation overlay alpha values and colors.
 */
class ElevationOverlay(
  /**
   * Returns the current theme's color int value for `R.attr.elevationOverlayColor`.
   */
  @get:ColorInt @param:ColorInt
  val themeElevationOverlayColor: Int,
  /**
   * Returns the current theme's color int value for `R.attr.colorSurface`.
   */
  @get:ColorInt @param:ColorInt
  val themeSurfaceColor: Int,
  private val displayDensity: Float
) {

  constructor(context: Context, colorScheme: ColorScheme) : this(
    colorScheme.surfaceTint,
    colorScheme.surface,
    context.resources.displayMetrics.density
  )

  /**
   * See [.compositeOverlayWithThemeSurfaceColorIfNeeded].
   *
   *
   * The absolute elevation of the parent of the provided `overlayView` will also be
   * factored in when determining the overlay color.
   */
  @ColorInt
  fun compositeOverlayWithThemeSurfaceColorIfNeeded(
    elevation: Float,
    overlayView: View
  ): Int {
    var elevationFinal = elevation
    elevationFinal += getParentAbsoluteElevation(overlayView)
    return compositeOverlayWithThemeSurfaceColorIfNeeded(elevationFinal)
  }

  /**
   * Blends the calculated elevation overlay color (@see #compositeOverlayIfNeeded(int, float)) with
   * the current theme's color int value for `R.attr.colorSurface` if needed.
   */
  @ColorInt
  fun compositeOverlayWithThemeSurfaceColorIfNeeded(elevation: Float): Int {
    return compositeOverlayIfNeeded(themeSurfaceColor, elevation)
  }

  /**
   * See [.compositeOverlayIfNeeded].
   *
   *
   * The absolute elevation of the parent of the provided `overlayView` will also be
   * factored in when determining the overlay color.
   */
  @ColorInt
  fun compositeOverlayIfNeeded(
    @ColorInt backgroundColor: Int,
    elevation: Float,
    overlayView: View
  ): Int {
    var elevationFinal = elevation
    elevationFinal += getParentAbsoluteElevation(overlayView)
    return compositeOverlayIfNeeded(backgroundColor, elevationFinal)
  }

  /**
   * Blends the calculated elevation overlay color (@see #compositeOverlay(int, float)) with the
   * `backgroundColor`, only if the current theme's `R.attr.elevationOverlayEnabled` is
   * true and the `backgroundColor` matches the theme's surface color (`R.attr.colorSurface`); otherwise returns the `backgroundColor`.
   */
  @ColorInt
  fun compositeOverlayIfNeeded(@ColorInt backgroundColor: Int, elevation: Float): Int {
    return if (isThemeSurfaceColor(backgroundColor)) {
      compositeOverlay(backgroundColor, elevation)
    } else {
      backgroundColor
    }
  }

  /**
   * See [.compositeOverlay].
   */
  @ColorInt
  fun compositeOverlay(
    @ColorInt backgroundColor: Int,
    elevation: Float,
    overlayView: View
  ): Int {
    var elevationFinal = elevation
    elevationFinal += getParentAbsoluteElevation(overlayView)
    return compositeOverlay(backgroundColor, elevationFinal)
  }

  /**
   * Blends the calculated elevation overlay color with the provided `backgroundColor`.
   *
   *
   * An alpha level is applied to the theme's `R.attr.elevationOverlayColor` by using a
   * formula that is based on the provided `elevation` value.
   */
  @ColorInt
  fun compositeOverlay(@ColorInt backgroundColor: Int, elevation: Float): Int {
    val overlayAlphaFraction = calculateOverlayAlphaFraction(elevation)
    val backgroundAlpha = Color.alpha(backgroundColor)
    val backgroundColorOpaque = ColorUtils.setAlphaComponent(backgroundColor, 255)
    var overlayColorOpaque = MaterialColors.layer(
      backgroundColorOpaque,
      themeElevationOverlayColor,
      overlayAlphaFraction
    )
    return ColorUtils.setAlphaComponent(overlayColorOpaque, backgroundAlpha)
  }

  /**
   * Blends the calculated elevation overlay color with the provided `backgroundColor`.
   *
   *
   * An alpha level is applied to the theme's `R.attr.elevationOverlayColor` by using a
   * formula that is based on the provided `elevation` value.
   */
  @ColorInt
  fun compositeOverlay(elevation: Float): Int {
    val backgroundColor = themeSurfaceColor
    val overlayAlphaFraction = calculateOverlayAlphaFraction(elevation)
    val backgroundAlpha = Color.alpha(backgroundColor)
    val backgroundColorOpaque = ColorUtils.setAlphaComponent(backgroundColor, 255)
    var overlayColorOpaque = MaterialColors.layer(
      backgroundColorOpaque,
      themeElevationOverlayColor,
      overlayAlphaFraction
    )
    return ColorUtils.setAlphaComponent(overlayColorOpaque, backgroundAlpha)
  }

  fun compositeOverlay(surfaceLevel: SurfaceLevel): Int {
    return compositeOverlay(getElevationDimension(surfaceLevel))
  }

  /**
   * Calculates the alpha value, between 0 and 255, that should be used with the elevation overlay
   * color, based on the provided `elevation` value.
   */
  fun calculateOverlayAlpha(elevation: Float): Int {
    return (calculateOverlayAlphaFraction(elevation) * 255).roundToInt()
  }

  /**
   * Calculates the alpha fraction, between 0 and 1, that should be used with the elevation overlay
   * color, based on the provided `elevation` value.
   */
  fun calculateOverlayAlphaFraction(elevation: Float): Float {
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

  /**
   * Returns the absolute elevation of the parent of the provided `overlayView`, or in other
   * words, the sum of the elevations of all ancestors of the `overlayView`.
   */
  fun getParentAbsoluteElevation(overlayView: View): Float {
    return ViewUtils.getParentAbsoluteElevation(overlayView)
  }

  private fun isThemeSurfaceColor(@ColorInt color: Int): Boolean {
    return ColorUtils.setAlphaComponent(color, 255) == themeSurfaceColor
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
    private val OVERLAY_ACCENT_COLOR_ALPHA = (0.02 * 255).roundToInt().toInt()
  }
}
