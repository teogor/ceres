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

package dev.teogor.ceres.m3.app

import android.content.res.Resources
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.ViewDataBinding
import dev.teogor.ceres.components.app.BaseActivity
import dev.teogor.ceres.components.events.UiEvent
import dev.teogor.ceres.extensions.validStringRes
import dev.teogor.ceres.m3.FragmentContainer
import dev.teogor.ceres.m3.R
import dev.teogor.ceres.m3.SnackbarBetaM3
import dev.teogor.ceres.m3.annotation.M3
import dev.teogor.ceres.m3.events.UiEventM3
import dev.teogor.ceres.m3.theme.AppThemeType
import dev.teogor.ceres.m3.theme.IThemeM3
import dev.teogor.ceres.m3.theme.JustBlackThemeType
import dev.teogor.ceres.m3.theme.ThemeM3
import dev.teogor.ceres.m3.widgets.dialog.DialogChoiceBuilderM3

/**
 * MVVM base activity class that extends [BaseActivity]
 * and is [M3] aware.
 *
 * **Developer Guides**
 * > For information about how to implement this class and more, read the documentation.
 *
 * @see [BaseFragmentM3]
 * @see [BaseViewModelM3]
 * @see [ViewDataBinding]
 * @see [BaseMenuSheetDialogFragmentM3]
 *
 * @param B the type of [ViewDataBinding].
 * @param VM the type of [BaseViewModelM3].
 *
 * @author teogor
 * @since 1.0.0
 */
@M3
abstract class BaseActivityM3<B : ViewDataBinding, VM : BaseViewModelM3> :
  BaseActivity<B, VM>(), IThemeM3 {

  override fun onCreate(savedInstanceState: Bundle?) {
    setupTheme()
    super.onCreate(savedInstanceState)
  }

  override fun setupObservers() {
    super.setupObservers()
    viewModel.onThemeChanged.observe(this) {
      onThemeChanged()
    }
    viewModel.uiEventStream.observe(this) { uiEvent -> processUiEvent(uiEvent) }
  }

  override fun processUiEvent(uiEvent: UiEvent) {
    super.processUiEvent(uiEvent)

    when (uiEvent) {
      is UiEventM3.ChoiceDialog -> {
        DialogChoiceBuilderM3(this)
          .setTitle(uiEvent.titleResId)
          .setSingleChoiceItems(
            uiEvent.choices,
            uiEvent.checkedPosition
          ) { dialogInterface, pos ->
            uiEvent.listener.onSelected(dialogInterface, pos)
          }
          .show()
      }
      is UiEventM3.ShowSnackbar -> {
        val title = if (uiEvent.titleResId.validStringRes) {
          resources.getString(uiEvent.titleResId)
        } else if (uiEvent.title.isNotEmpty()) {
          uiEvent.title
        } else {
          ""
        }
        SnackbarBetaM3.toBuilder()
          .message(title)
          .action(uiEvent.action, uiEvent.actionListener)
          .duration(uiEvent.duration)
          .anchor(getSnackbarAnchor())
          .buildAndShow()
      }
    }
  }

  override fun setupUi() {
    super.setupUi()
    viewModel.onThemeChanged.value = true
  }

  private fun setupTheme() {
    setPostSplashScreenTheme(theme, TypedValue())
    when (ThemeM3.justBlackTheme()) {
      JustBlackThemeType.AlwaysOn -> AppCompatDelegate.setDefaultNightMode(
        AppCompatDelegate.MODE_NIGHT_YES
      )
      JustBlackThemeType.FollowSystem -> if (ThemeM3.isNightModeOn()) {
        AppCompatDelegate.setDefaultNightMode(
          AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
        )
      } else {
        setupAppTheme()
      }
      else -> setupAppTheme()
    }
    delegate.applyDayNight()
  }

  private fun setPostSplashScreenTheme(
    currentTheme: Resources.Theme,
    typedValue: TypedValue
  ) {
    if (currentTheme.resolveAttribute(R.attr.afterSplashScreenTheme, typedValue, true)) {
      val finalThemeId = typedValue.resourceId
      if (finalThemeId != 0) {
        setTheme(finalThemeId)
      }
    }
  }

  private fun setupAppTheme() {
    when (ThemeM3.appTheme()) {
      AppThemeType.ClearlyWhite -> AppCompatDelegate.setDefaultNightMode(
        AppCompatDelegate.MODE_NIGHT_NO
      )
      AppThemeType.KindaDark -> AppCompatDelegate.setDefaultNightMode(
        AppCompatDelegate.MODE_NIGHT_YES
      )
      AppThemeType.FollowSystem -> AppCompatDelegate.setDefaultNightMode(
        AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
      )
    }
  }

  override fun onThemeChanged() {
    super.onThemeChanged()

    setupTheme()
    val frgContainer = binding.root
    if (frgContainer is FragmentContainer) {
      frgContainer.onThemeChanged()
    }
  }

  open fun getSnackbarAnchor(): View? = null
}
