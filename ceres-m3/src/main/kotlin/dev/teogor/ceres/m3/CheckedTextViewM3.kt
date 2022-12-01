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
import dev.teogor.ceres.m3.beta.Beta
import dev.teogor.ceres.m3.extension.compoundDrawablesTintList
import dev.teogor.ceres.m3.theme.IThemeM3
import dev.teogor.ceres.m3.theme.getBackgroundDrawable

open class CheckedTextViewM3 : AppCompatCheckedTextView, IThemeM3 {

  constructor(context: Context) : this(context, null)

  constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

  constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
    context,
    attrs,
    defStyleAttr
  )

  init {
    if (!isInEditMode) {
      applyTheme()
    }
  }

  override fun onThemeChanged() {
    super.onThemeChanged()
    applyTheme()
  }

  private fun applyTheme() {
    setTextColor(colorScheme().onSurface.addAlpha(0.9f))
    compoundDrawablesTintList = colorScheme().primary

    background = getBackgroundDrawable(
      backgroundDrawable = Beta.BackgroundDrawable(
        cornerSize = 10f,
        background = Beta.BackgroundData.Transparent,
        rippleEnabled = true
      )
    )
  }
}
