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

package dev.teogor.ceres.presentation.feature.home

import android.content.Intent
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.teogor.ceres.R
import dev.teogor.ceres.ads.formats.AdBinder
import dev.teogor.ceres.ads.formats.NativeAd
import dev.teogor.ceres.ads.showAppOpenAd
import dev.teogor.ceres.components.toolbar.ToolbarType
import dev.teogor.ceres.components.view.ToolBar
import dev.teogor.ceres.core.global.GlobalData
import dev.teogor.ceres.extensions.launchActivity
import dev.teogor.ceres.m3.app.BaseViewModelM3
import dev.teogor.ceres.main.MainActivity
import dev.teogor.ceres.presentation.ads.HomeNativeAd
import dev.teogor.ceres.presentation.ads.HomeNativeAdBinder
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
  homeNativeAd: HomeNativeAd,
  nativeAdBinder: HomeNativeAdBinder
) : BaseViewModelM3() {

  /**
   * UI Binding
   */
  val liveHomeAd = MutableLiveData<NativeAd>(homeNativeAd)
  val liveHomeAdBinder = MutableLiveData<AdBinder>(nativeAdBinder)

  override val toolBarBuilder: ToolBar.Builder
    get() = ToolBar.Builder(
      type = ToolbarType.ONLY_LOGO,
      title = R.string.app_name,
      isTransparent = true
    )

  override fun onFragmentCreated() {
    super.onFragmentCreated()

    showBottomNavigation(true)
  }

  fun launchActivity() {
    // finish activity before relaunching it
    GlobalData.activity.finish()
    launchActivity(
      context = GlobalData.context(),
      activityClass = MainActivity::class,
      flags = Intent.FLAG_ACTIVITY_NEW_TASK
    ) {
      showAppOpenAd()
    }
  }
}
