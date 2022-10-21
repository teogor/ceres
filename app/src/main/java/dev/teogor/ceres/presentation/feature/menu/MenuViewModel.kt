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

package dev.teogor.ceres.presentation.feature.menu

import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.teogor.ceres.components.events.SingleLiveEvent
import dev.teogor.ceres.core.construct.AppData
import dev.teogor.ceres.m3.app.BaseViewModelM3
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor() : BaseViewModelM3() {

  /**
   * UI
   * */
  val liveVersion = MutableLiveData("v${AppData.VersionName}")
  val liveDeveloperOptionsEnabled = MutableLiveData(false)

  /**
   * Fragment
   * */
  val uiOnClose = SingleLiveEvent<Boolean>()

  fun onCloseClicked() {
    uiOnClose.value = true
  }

  fun onSettingsClicked() {
    navigate.value = MenuFragmentDirections.actionFromMenuToPrefsTheme()
  }

  fun onAboutClicked() {
    navigate.value = MenuFragmentDirections.actionFromMenuToAbout()
  }

  fun onDeveloperOptionsClicked() {
    navigate.value = MenuFragmentDirections.actionFromMenuToDeveloperOptions()
  }

  fun onNotificationsClicked() {
    navigate.value = MenuFragmentDirections.actionFromMenuToNotifications()
  }
}
