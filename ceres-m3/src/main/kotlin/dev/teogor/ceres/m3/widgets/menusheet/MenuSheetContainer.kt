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

package dev.teogor.ceres.m3.widgets.menusheet

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.widget.FrameLayout
import androidx.annotation.StyleRes
import com.google.android.material.R

class MenuSheetContainer @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  @StyleRes theme: Int = 0
) : FrameLayout(context, attrs, getThemeResId(context, theme)) {
  private var behavior: dev.teogor.ceres.m3.widgets.menusheet.MenuSheetBehaviour<*>? = null
  fun setBehaviour(behavior: dev.teogor.ceres.m3.widgets.menusheet.MenuSheetBehaviour<*>?) {
    this.behavior = behavior
  }

  companion object {
    private const val TAG = "MenuSheetContainer"

    @SuppressLint("PrivateResource")
    private fun getThemeResId(context: Context, themeId: Int): Int {
      var themeIdCopy = themeId
      if (themeIdCopy == 0) {
        // If the provided theme is 0, then retrieve the dialogTheme from our theme
        val outValue = TypedValue()
        themeIdCopy = if (context.theme.resolveAttribute(
            R.attr.bottomSheetDialogTheme,
            outValue,
            true
          )
        ) {
          outValue.resourceId
        } else {
          // bottomSheetDialogTheme is not provided; we default to our light theme
          R.style.Theme_Design_Light_BottomSheetDialog
        }
      }
      return themeIdCopy
    }
  }
}
