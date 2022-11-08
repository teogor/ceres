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

import android.util.Log
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import dev.teogor.ceres.bindings.BindingAdapterClass
import dev.teogor.ceres.components.toolbar.ToolbarType
import dev.teogor.ceres.extensions.safeCall
import dev.teogor.ceres.extensions.safeValue
import dev.teogor.ceres.m3.ColorsContainerM3
import dev.teogor.ceres.m3.ImageComponentM3
import dev.teogor.ceres.m3.SwitchComponentM3
import dev.teogor.ceres.m3.widgets.bar.ToolBar

// todo handle this in submodule for instance toolbar
@BindingAdapterClass
class BindingMethods {

  companion object {

    @BindingAdapter(
      value = [
        "type",
        "is_transparent",
        "is_filled"
      ],
      requireAll = true
    )
    @JvmStatic
    fun bindingToolBar(
      toolbar: ToolBar,
      type: ToolbarType,
      isTransparent: Boolean,
      isFilled: Boolean
    ) {
      toolbar.setData(
        dev.teogor.ceres.components.view.ToolBar.Data(
          type,
          isTransparent,
          isFilled
        )
      )
      Log.d("BindingMethods", "is_filled $isFilled")
    }

    @BindingAdapter(
      value = [
        "onCheckedChange",
        "onClicked",
        "title",
        "subtitle",
        "checked"
      ],
      requireAll = false
    )
    @JvmStatic
    fun bindingSwitchComponent(
      switchComponent: SwitchComponentM3,
      onChecked: SwitchComponentM3.OnCheckedChangeListener?,
      onClicked: SwitchComponentM3.OnClickedChangeListener?,
      title: MutableLiveData<String>?,
      subtitle: MutableLiveData<String>?,
      checked: MutableLiveData<Boolean>?
    ) {
      onChecked.safeCall {
        switchComponent.onCheckedChangeListener = this
      }
      onClicked.safeCall {
        switchComponent.onClickedChangeListener = this
      }
      title.safeCall {
        switchComponent.title = safeValue
      }
      subtitle.safeCall {
        switchComponent.subtitle = safeValue
      }
      checked.safeCall {
        switchComponent.switchChecked = safeValue
      }
    }

    @BindingAdapter(
      value = [
        "onColorPick",
        "pickedColor"
      ],
      requireAll = false
    )
    @JvmStatic
    fun bindingColorsContainerM3(
      colorsContainer: ColorsContainerM3,
      onColorPick: ColorsContainerM3.OnColorPickListener?,
      color: MutableLiveData<Int>?
    ) {
      onColorPick.safeCall {
        colorsContainer.onColorPickListener = this
      }
      color.safeCall {
        colorsContainer.color = safeValue
      }
    }

    @BindingAdapter(
      value = [
        "onClicked"
      ],
      requireAll = false
    )
    @JvmStatic
    fun bindingImageComponentM3(
      imageComponent: ImageComponentM3,
      onClicked: ImageComponentM3.OnClickedChangeListener?
    ) {
      onClicked.safeCall {
        imageComponent.onClickedChangeListener = this
      }
    }
  }
}
