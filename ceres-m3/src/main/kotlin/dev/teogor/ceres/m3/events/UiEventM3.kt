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

package dev.teogor.ceres.m3.events

import android.content.DialogInterface
import android.view.View
import androidx.annotation.StringRes
import com.google.android.material.snackbar.BaseTransientBottomBar
import dev.teogor.ceres.components.events.UiEvent

open class UiEventM3 {

  open class Dialog(
    @StringRes val titleResId: Int
  ) : UiEvent()

  class ShowSnackbar(
    @StringRes val titleResId: Int = -1,
    val title: String = "",
    @BaseTransientBottomBar.Duration val duration: Int = dev.teogor.ceres.m3.snackbar.Snackbar.LENGTH_SHORT,
    val action: String = "",
    val actionListener: View.OnClickListener? = null
  ) : UiEvent()

  class ChoiceDialog(
    @StringRes titleResId: Int,
    val choices: Array<String>,
    val checkedPosition: Int,
    val listener: ChoiceSelected
  ) : Dialog(titleResId)

  interface ChoiceSelected {
    fun onSelected(dialogInterface: DialogInterface, pos: Int)
  }
}
