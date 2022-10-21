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

package dev.teogor.ceres.presentation.feature.prefs.theme

import android.content.DialogInterface
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.teogor.ceres.R
import dev.teogor.ceres.components.toolbar.ToolbarType
import dev.teogor.ceres.extensions.asColor
import dev.teogor.ceres.m3.app.BaseViewModelM3
import dev.teogor.ceres.m3.events.UiEventM3
import dev.teogor.ceres.m3.events.UiEventM3.ChoiceSelected
import dev.teogor.ceres.m3.extension.applyNewColor
import dev.teogor.ceres.m3.theme.AppThemeType
import dev.teogor.ceres.m3.theme.JustBlackThemeType
import dev.teogor.ceres.m3.theme.ThemeM3
import dev.teogor.ceres.m3.theme.ThemeMemo
import dev.teogor.ceres.m3.util.DynamicColors
import dev.teogor.ceres.m3.widgets.colorpicker.model.IntegerHSLColor
import dev.teogor.ceres.m3.widgets.extension.toHex
import javax.inject.Inject

@HiltViewModel
class ThemePrefsViewModel @Inject constructor(
  val themeMemo: ThemeMemo
) : BaseViewModelM3() {

  /**
   * UI Binding
   */
  val liveSwitchMaterialYou = MutableLiveData(false)
  val liveMaterialYouEnabled = MutableLiveData(DynamicColors.isDynamicColorAvailable())
  val liveSwitchDesaturatedColors = MutableLiveData(true)
  val liveAttentionThemeVisible = MutableLiveData(false)
  val liveAttentionThemeString = MutableLiveData(R.string.empty)
  val liveTextAppTheme = MutableLiveData("")
  val liveTextJustBlackTheme = MutableLiveData("")
  val liveAccentColor = MutableLiveData(0)

  private lateinit var choicesAppTheme: Array<String>
  private lateinit var choicesJustBlack: Array<String>

  override fun onFragmentCreated() {
    super.onFragmentCreated()

    setToolbarType(ToolbarType.BACK_BUTTON)
    setToolbarTitle(R.string.look_and_feel)
    showBottomNavigation(false)

    choicesAppTheme = resources.getStringArray(R.array.app_theme_array)
    choicesJustBlack = resources.getStringArray(R.array.just_black_theme_array)

    liveAccentColor.value = themeMemo.seed.asColor()
    liveSwitchMaterialYou.value =
      themeMemo.materialYouEnabled && DynamicColors.isDynamicColorAvailable()
    liveSwitchDesaturatedColors.value = themeMemo.desaturatedColoursEnabled
    liveTextAppTheme.value = choicesAppTheme[ThemeM3.appTheme().ordinal]
    liveTextJustBlackTheme.value = choicesJustBlack[ThemeM3.justBlackTheme().ordinal]
    handleAttentionJustBlackTheme()
  }

  fun onCheckedChanged(checked: Boolean) {
    liveSwitchMaterialYou.value = checked
    themeMemo.materialYouEnabled = checked
    onThemeChanged.value = true
  }

  fun onCheckedDesaturatedColors(checked: Boolean) {
    liveSwitchDesaturatedColors.value = checked
    themeMemo.desaturatedColoursEnabled = checked
    onThemeChanged.value = true
  }

  fun onAccentColorPick(color: IntegerHSLColor) {
    if (!liveSwitchMaterialYou.value!!) {
      themeMemo.applyNewColor(color.toHex())
      onThemeChanged.value = true
    }
  }

  fun onAppThemeClicked() {
    uiEventStream.value = UiEventM3.ChoiceDialog(
      titleResId = R.string.app_theme,
      choices = choicesAppTheme,
      checkedPosition = ThemeM3.appTheme().ordinal,
      listener = object : ChoiceSelected {
        override fun onSelected(dialogInterface: DialogInterface, pos: Int) {
          val appTheme = AppThemeType.values()[pos]
          liveTextAppTheme.value = choicesAppTheme[pos]
          themeMemo.appTheme = appTheme

          dialogInterface.dismiss()
          onThemeChanged.value = true
        }
      }
    )
  }

  fun onJustBlackThemeClicked() {
    uiEventStream.value = UiEventM3.ChoiceDialog(
      titleResId = R.string.just_black,
      choices = choicesJustBlack,
      checkedPosition = ThemeM3.justBlackTheme().ordinal,
      listener = object : ChoiceSelected {
        override fun onSelected(dialogInterface: DialogInterface, pos: Int) {
          val justBlackTheme = JustBlackThemeType.values()[pos]
          liveTextJustBlackTheme.value = choicesJustBlack[pos]
          themeMemo.justBlackTheme = justBlackTheme

          handleAttentionJustBlackTheme()

          dialogInterface.dismiss()

          onThemeChanged.value = true
        }
      }
    )
  }

  private fun handleAttentionJustBlackTheme() {
    when (ThemeM3.justBlackTheme()) {
      JustBlackThemeType.Off -> {
        liveAttentionThemeVisible.value = false
      }
      JustBlackThemeType.AlwaysOn -> {
        liveAttentionThemeVisible.value = true
        liveAttentionThemeString.value = R.string.home_attention_just_black_always_on
      }
      JustBlackThemeType.FollowSystem -> {
        liveAttentionThemeVisible.value = true
        liveAttentionThemeString.value = R.string.home_attention_just_black_follow_system
      }
    }
  }
}
