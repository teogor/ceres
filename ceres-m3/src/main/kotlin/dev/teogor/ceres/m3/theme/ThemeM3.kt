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

import com.zeoflow.startup.ktx.ApplicationInitializer
import dev.teogor.ceres.core.util.VersionUtils
import dev.teogor.ceres.extensions.asColor
import dev.teogor.ceres.extensions.asHex
import dev.teogor.ceres.m3.R

@Suppress("unused", "MemberVisibilityCanBePrivate")
object ThemeM3 {

  fun themeStore() = ThemeMemo()

  // ThemeStore
  fun appTheme() = getAppThemeImpl()

  fun justBlackTheme() = getJustBlackThemeImpl()

  fun materialYouEnabled() = getMaterialYouEnabledImpl()

  fun colouredBackgroundEnabled() = getColouredBackgroundEnabledImpl()

  fun isNightModeOn() = getIsNightModeOnImpl()

  // Theme M3
  fun schemeM3() = getSchemeM3Impl()

  fun lightColorScheme() = getLightColorSchemeImpl()

  fun darkColorScheme() = getDarkColorSchemeImpl()

  fun currentColorScheme() = getCurrentColorSchemeImpl()

  // Implementation
  private fun getSchemeM3Impl(): SchemeM3 {
    val colorSeed = if (VersionUtils.hasS()) {
      if (themeStore().materialYouEnabled) {
        ApplicationInitializer.context.asColor(android.R.color.system_accent1_400).asHex()
      } else {
        themeStore().seed
      }
    } else {
      themeStore().seed
    }

    return SchemeM3.Builder()
      .seed(colorSeed)
      .build()
  }

  private fun getAppThemeImpl() = themeStore().appTheme

  private fun getJustBlackThemeImpl() = themeStore().justBlackTheme

  private fun getColouredBackgroundEnabledImpl() = themeStore().colouredBackgroundEnabled

  private fun getIsNightModeOnImpl(): Boolean {
    return ApplicationInitializer.context.resources.getBoolean(R.bool.is_night_mode_on)
  }

  private fun getMaterialYouEnabledImpl() = themeStore().materialYouEnabled

  private fun getLightColorSchemeImpl() = getSchemeM3Impl().lightColorScheme

  private fun getDarkColorSchemeImpl() = getSchemeM3Impl().darkColorScheme

  private fun getJustBlackColorSchemeImpl() = getSchemeM3Impl().justBlackColorScheme

  private fun getCurrentColorSchemeImpl(): ColorScheme {
    val justBlackTheme = justBlackTheme()
    val appTheme = appTheme()
    when {
      justBlackTheme == JustBlackThemeType.AlwaysOn -> {
        return getJustBlackColorSchemeImpl()
      }
      justBlackTheme == JustBlackThemeType.FollowSystem -> {
        return if (isNightModeOn()) {
          getJustBlackColorSchemeImpl()
        } else {
          getLightColorSchemeImpl()
        }
      }
      appTheme == AppThemeType.ClearlyWhite -> {
        return getLightColorSchemeImpl()
      }
      appTheme == AppThemeType.KindaDark -> {
        return getDarkColorSchemeImpl()
      }
      appTheme == AppThemeType.FollowSystem -> {
        return if (isNightModeOn()) {
          getDarkColorSchemeImpl()
        } else {
          getLightColorSchemeImpl()
        }
      }
      else -> return getLightColorSchemeImpl()
    }
  }
}
