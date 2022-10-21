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

package dev.teogor.ceres.m3.widgets.bar

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.util.AttributeSet
import android.view.View

class NavigationBar constructor(
  context: Context,
  attrs: AttributeSet
) : dev.teogor.ceres.m3.widgets.bar.Bar(context, attrs) {

  private lateinit var barView: View

  init {
    init()
  }

  private fun init() {
    setBackgroundColor(colorSurfaceNormal())
    barView = View(context)
    if (!isInEditMode) {
      barView.layoutParams = LayoutParams(
        LayoutParams.MATCH_PARENT,
        navigationBarHeight
      )
    } else {
      barView.layoutParams = LayoutParams(
        LayoutParams.MATCH_PARENT,
        46
      )
    }
    barView.setBackgroundColor(colorTransparent)
    addView(barView)
  }

  private val navigationBarHeight: Int
    @SuppressLint("InternalInsetResource")
    get() {
      val resources: Resources = context.resources

      val resName =
        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
          "navigation_bar_height"
        } else {
          "navigation_bar_height_landscape"
        }

      val id: Int = resources.getIdentifier(resName, "dimen", "android")

      return if (id > 0) {
        resources.getDimensionPixelSize(id)
      } else {
        0
      }
    }
}
