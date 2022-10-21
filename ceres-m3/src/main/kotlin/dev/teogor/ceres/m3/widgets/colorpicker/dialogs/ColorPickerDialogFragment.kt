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

package dev.teogor.ceres.m3.widgets.colorpicker.dialogs

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import dev.teogor.ceres.m3.R

// TODO: Accept initial color value
// TODO: Accept resources
// TODO: Propagate picked value
// TODO: Support out-of-box color model selection
class ColorPickerDialogFragment : DialogFragment() {

  override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
    return AlertDialog.Builder(requireActivity())
      .setTitle(R.string.title_dialog_pick)
      .setMessage(R.string.title_dialog_pick_message)
      .setView(
        R.layout.layout_dialog_hsla
      )
      .setPositiveButton(
        R.string.action_dialog_pick_positive
      ) { _, _ ->
      }
      .setNegativeButton(
        R.string.action_dialog_pick_negative
      ) { _, _ ->
      }
      .create()
  }
}
