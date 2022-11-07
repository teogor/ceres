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
import dev.teogor.ceres.m3.beta.Beta
import dev.teogor.ceres.m3.theme.getBackgroundDrawable

class ContainerM3 constructor(
  context: Context,
  attrs: AttributeSet
) : ContainerBaseM3(context, attrs) {

  override val customBackground: Boolean
    get() = true

  override fun onThemeChanged() {
    super.onThemeChanged()

    background = getBackgroundDrawable(
      backgroundDrawable = Beta.BackgroundDrawable(
        cornerSize = cornerRadius,
        background = Beta.BackgroundData(
          color = backgroundColorM3,
          surfaceTintOverlay = surfaceTintOverlay,
          surfaceTint = surfaceTint
        ),
        rippleEnabled = rippleEnabled
      )
    )
  }
}
