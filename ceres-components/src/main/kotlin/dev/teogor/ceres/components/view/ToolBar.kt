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

import android.graphics.Color
import androidx.annotation.ColorInt
import androidx.annotation.Dimension
import androidx.annotation.Px
import androidx.annotation.StringRes
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.ShapeAppearanceModel
import dev.teogor.ceres.components.toolbar.ToolbarType
import dev.teogor.ceres.extensions.defaultResId
import dev.teogor.ceres.extensions.dpToPx

class ToolBar {

  data class Builder(
    val type: ToolbarType = ToolbarType.NOT_SET,
    @StringRes val title: Int = defaultResId,
    val isTransparent: Boolean = false
  )

  class Background {
    val cornersSize = 60.dpToPx.toFloat()
    private val shapeAppearanceModel = ShapeAppearanceModel()
      .toBuilder()
      .setAllCorners(CornerFamily.ROUNDED, cornersSize)
      .build()
    val toolbarBackground = MaterialShapeDrawable(shapeAppearanceModel)
  }

  data class Data(
    val type: ToolbarType,
    val isTransparent: Boolean,
    val isFilled: Boolean
  ) {
    val rippleEnabled: Boolean = !isTransparent

    var isHidden: Boolean = false
    var isRounded: Boolean = false
    var isExpanded: Boolean = false

    @Px
    @Dimension
    var cornerSize: Float = 0f

    @ColorInt
    var color: Int = Color.TRANSPARENT

    @Px
    @Dimension
    var verticalMargins: Int = 0

    @Px
    @Dimension
    var horizontalMargins: Int = 0

    val increasingAnimator: Boolean
      get() = Impl().increasingAnimatorImpl(this)

    internal class Impl {
      fun increasingAnimatorImpl(data: Data): Boolean {
        return data.isExpanded
      }
    }
  }
}
