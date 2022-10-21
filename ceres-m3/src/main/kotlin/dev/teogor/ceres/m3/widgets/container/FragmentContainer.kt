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

package dev.teogor.ceres.m3.widgets.container

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.allViews
import dev.teogor.ceres.m3.R
import dev.teogor.ceres.m3.elevation.SurfaceLevel
import dev.teogor.ceres.m3.theme.ThemeHandler

class FragmentContainer constructor(
  context: Context,
  attrs: AttributeSet
) : ConstraintLayout(context, attrs), ThemeHandler {

  private val toolbarHeight = resources.getDimension(R.dimen.toolbar_height).toInt()

  private val consumeTop: Boolean
  private val initialPaddingTop: Int

  init {
    context.theme.obtainStyledAttributes(
      attrs,
      R.styleable.FragmentContainer,
      0,
      0
    ).apply {
      try {
        consumeTop = getBoolean(R.styleable.FragmentContainer_consumeTop, false)
        initialPaddingTop = getDimension(
          R.styleable.FragmentContainer_android_paddingTop,
          0f
        ).toInt()
      } finally {
        recycle()
      }
    }
    if (consumeTop) {
      ViewCompat.setOnApplyWindowInsetsListener(this) { _, insets ->
        val statusBarSize = insets.getInsets(WindowInsetsCompat.Type.statusBars()).top
        setPadding(
          paddingLeft,
          initialPaddingTop + statusBarSize + toolbarHeight,
          paddingRight,
          paddingBottom
        )
        insets
      }
    }
    onThemeChanged()
  }

  override fun onThemeChanged() {
    super.onThemeChanged()

    setBackgroundColor(getBackgroundColorM3(this, SurfaceLevel.Lvl1))

    val themedViews = allViews.filter { view -> view is ThemeHandler }.filter { it != this }
    themedViews.forEach { view ->
      (view as ThemeHandler).onThemeChanged()
    }
  }
}
