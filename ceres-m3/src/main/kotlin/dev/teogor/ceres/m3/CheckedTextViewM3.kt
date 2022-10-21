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

package dev.teogor.ceres.m3

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatCheckedTextView
import dev.teogor.ceres.extensions.addAlpha
import dev.teogor.ceres.extensions.colorStateList
import dev.teogor.ceres.m3.elevation.SurfaceLevel
import dev.teogor.ceres.m3.theme.ThemeHandler

class CheckedTextViewM3 : AppCompatCheckedTextView, ThemeHandler {

  constructor(context: Context) : this(context, null)

  constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

  constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
    context,
    attrs,
    defStyleAttr
  ) {
    onThemeChanged()
  }

  override fun onThemeChanged() {
    super.onThemeChanged()

    setTextColor(getSchemeColor().onSurface.addAlpha(0.9f))
    supportCompoundDrawablesTintList = getSchemeColor().primary.colorStateList

    background = getRippleDrawable(
      content = getMaterialShapeDrawable(
        SurfaceLevel.Transparent,
        cornerSize = 0f,
        this
      ),
      mask = getMaterialShapeDrawable(
        SurfaceLevel.Lvl0,
        cornerSize = 0f,
        this
      )
    )
  }
}
