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
import androidx.appcompat.widget.AppCompatRatingBar
import dev.teogor.ceres.extensions.colorStateList
import dev.teogor.ceres.m3.theme.ThemeHandler
import dev.teogor.ceres.m3.theme.ThemeM3

class RatingBarM3(
  context: Context,
  attrs: AttributeSet
) : AppCompatRatingBar(context, attrs), ThemeHandler {

  private val starsColorM3: ColorM3

  init {
    context.theme.obtainStyledAttributes(
      attrs,
      R.styleable.RatingBarM3,
      0,
      0
    ).apply {
      try {
        starsColorM3 = ColorM3.values()[
          getInt(
            R.styleable.RatingBarM3_color_m3,
            0
          )
        ]
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

    val colorScheme = ThemeM3.currentColorScheme()
    progressTintList = getColorM3(colorM3 = starsColorM3).colorStateList
    progressBackgroundTintList = colorScheme.secondary.colorStateList
  }
}
