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

package dev.teogor.ceres.components.view

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import androidx.appcompat.widget.AppCompatTextView

// <style name="Widget.Material.Button">
// <item name="background">@drawable/btn_default_material</item>
// <item name="textAppearance">?attr/textAppearanceButton</item>
// <item name="minHeight">48dip</item>
// <item name="minWidth">88dip</item>
// <item name="stateListAnimator">@anim/button_state_list_anim_material</item>
// <item name="focusable">true</item>
// <item name="clickable">true</item>
// <item name="gravity">center_vertical|center_horizontal</item>
// </style>

// <item name="textColor">?textColorPrimary</item>
// <item name="textColorHighlight">?textColorHighlight</item>
// <item name="textColorHint">?textColorHint</item>
// <item name="textColorLink">?textColorLink</item>
// <item name="textSize">16sp</item>
// <item name="textStyle">normal</item>
open class Button @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null
) : AppCompatTextView(context, attrs) {

  init {
    gravity = Gravity.CENTER
    isFocusable = true
    isClickable = true
  }
}
