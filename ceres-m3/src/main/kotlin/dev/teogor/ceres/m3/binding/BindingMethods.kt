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

package dev.teogor.ceres.m3.binding

import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import dev.teogor.ceres.binding.BindingAdapterClass
import dev.teogor.ceres.components.toolbar.ToolbarType
import dev.teogor.ceres.m3.ColorsContainerM3
import dev.teogor.ceres.m3.ImageComponentM3
import dev.teogor.ceres.m3.SwitchComponentM3
import dev.teogor.ceres.m3.widgets.bar.ToolBar

// todo handle this in submodule for instance toolbar
@BindingAdapterClass
class BindingMethods {

  companion object {
    @BindingAdapter("type")
    @JvmStatic
    fun toolbarBindingType(toolbar: ToolBar, type: ToolbarType) {
      toolbar.setType(type)
    }

    @BindingAdapter("is_transparent")
    @JvmStatic
    fun toolbarBindingIsTransparent(toolbar: ToolBar, isTransparent: Boolean) {
      toolbar.setIsTransparent(isTransparent)
    }

    @BindingAdapter("onCheckedChange")
    @JvmStatic
    fun onContentElementCheck(
      componentM3: SwitchComponentM3,
      onCheckedChangeListener: SwitchComponentM3.OnCheckedChangeListener
    ) {
      componentM3.onCheckedChangeListener = onCheckedChangeListener
    }

    @BindingAdapter("onClicked")
    @JvmStatic
    fun onContentElementClick(
      componentM3: SwitchComponentM3,
      onClickedChangeListener: SwitchComponentM3.OnClickedChangeListener
    ) {
      componentM3.onClickedChangeListener = onClickedChangeListener
    }

    @BindingAdapter("subtitle")
    @JvmStatic
    fun onContentElementSubtitle(
      componentM3: SwitchComponentM3,
      liveData: MutableLiveData<String>
    ) {
      componentM3.subtitle = liveData.value
    }

    @BindingAdapter("title")
    @JvmStatic
    fun onContentElementTitle(
      componentM3: SwitchComponentM3,
      liveData: MutableLiveData<String>
    ) {
      componentM3.title = liveData.value
    }

    @BindingAdapter("checked")
    @JvmStatic
    fun onContentElementChecked(
      componentM3: SwitchComponentM3,
      liveData: MutableLiveData<Boolean>
    ) {
      componentM3.switchChecked = liveData.value!!
    }

    @BindingAdapter("onColorPick")
    @JvmStatic
    fun onContentElementChecked(
      colorsContainerM3: ColorsContainerM3,
      listener: ColorsContainerM3.OnColorPickListener
    ) {
      colorsContainerM3.onColorPickListener = listener
    }

    @BindingAdapter("pickedColor")
    @JvmStatic
    fun onContentElementChecked(
      colorsContainerM3: ColorsContainerM3,
      liveData: MutableLiveData<Int>
    ) {
      colorsContainerM3.color = liveData.value!!.toInt()
    }

    @BindingAdapter("onClicked")
    @JvmStatic
    fun onContentElementClick(
      componentM3: ImageComponentM3,
      onClickedChangeListener: ImageComponentM3.OnClickedChangeListener
    ) {
      componentM3.onClickedChangeListener = onClickedChangeListener
    }
  }
}
