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
import androidx.core.widget.NestedScrollView
import dev.teogor.ceres.m3.beta.Beta
import dev.teogor.ceres.m3.theme.IThemeM3
import dev.teogor.ceres.m3.theme.getBackgroundDrawable

class NestedScrollViewM3 constructor(
  context: Context,
  attrs: AttributeSet
) : NestedScrollView(context, attrs), IThemeM3 {

  private val backgroundColorM3: ColorM3
  private val backgroundTintColorM3: ColorM3
  private val backgroundTintOverlay: Float

  init {

    context.theme.obtainStyledAttributes(
      attrs,
      R.styleable.NestedScrollViewM3,
      0,
      0
    ).apply {
      try {
        backgroundColorM3 = ColorM3.values()[
          getInt(
            R.styleable.NestedScrollViewM3_background_m3,
            0
          )
        ]
        backgroundTintColorM3 = ColorM3.values()[
          getInt(
            R.styleable.NestedScrollViewM3_background_m3_tint_overlay,
            0
          )
        ]
        backgroundTintOverlay = getFloat(R.styleable.NestedScrollViewM3_background_m3_tint, 0f)
      } finally {
        recycle()
      }
    }
    if (!isInEditMode) {
      onThemeChanged()
    }
  }

  override fun onThemeChanged() {
    super.onThemeChanged()

    background = getBackgroundDrawable(
      backgroundDrawable = Beta.BackgroundDrawable(
        background = Beta.BackgroundData(
          color = backgroundColorM3,
          surfaceTintOverlay = backgroundTintColorM3,
          surfaceTint = backgroundTintOverlay
        )
      )
    )
  }
}
