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

package dev.teogor.ceres.m3.drawable

import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.graphics.drawable.RippleDrawable
import com.google.android.material.shape.ShapeAppearanceModel
import dev.teogor.ceres.extensions.colorStateList
import dev.teogor.ceres.m3.ColorM3
import dev.teogor.ceres.m3.theme.ColorScheme
import dev.teogor.ceres.m3.theme.ThemeM3

class RippleDrawableM3 private constructor(
  color: ColorStateList,
  content: Drawable,
  mask: Drawable
) : RippleDrawable(color, content, mask) {

  internal lateinit var builder: Builder

  private constructor(
    color: ColorStateList,
    content: Drawable,
    mask: Drawable,
    builder: Builder
  ) : this(color, content, mask) {
    this.builder = builder
  }

  /** Builder to create instances of [ShapeDrawableM3]s.  */
  data class Builder(
    private var shapeAppearanceModel: ShapeAppearanceModel,
    private var backgroundColor: ColorM3 = ColorM3.Background,
    private var surfaceTint: Float = 0f,
    private var surfaceTintOverlay: ColorM3 = ColorM3.Primary
  ) {

    internal constructor(builder: Builder) : this(
      shapeAppearanceModel = builder.shapeAppearanceModel,
      backgroundColor = builder.backgroundColor,
      surfaceTint = builder.surfaceTint,
      surfaceTintOverlay = builder.surfaceTintOverlay
    )

    private fun getSchemeColor(): ColorScheme {
      return ThemeM3.currentColorScheme()
    }

    fun backgroundColor(colorM3: ColorM3) = apply { this.backgroundColor = colorM3 }

    fun surfaceTint(alpha: Float) = apply { this.surfaceTint = alpha }

    fun surfaceTintOverlay(colorM3: ColorM3) = apply { this.surfaceTintOverlay = colorM3 }

    fun build(): RippleDrawableM3 {
      val content = ShapeDrawableM3(shapeAppearanceModel)
        .toBuilder()
        .backgroundColor(backgroundColor)
        .surfaceTint(surfaceTint)
        .surfaceTintOverlay(surfaceTintOverlay)
        .build()
      val mask = ShapeDrawableM3(shapeAppearanceModel)
        .toBuilder()
        .backgroundColor(ColorM3.Background)
        .build()

      val color = if (content.isBackgroundLight()) {
        getSchemeColor().onSurface
      } else {
        getSchemeColor().surface
      }
      // todo fixme is this the right color for ripple???
      return RippleDrawableM3(
        color = color.colorStateList,
        content = content,
        mask = mask,
        builder = this
      )
    }
  }

  companion object {

    /** Returns a builder with the edges and corners from this [ShapeDrawableM3]  */
    fun toBuilder(shapeAppearanceModel: ShapeAppearanceModel): Builder {
      return Builder(shapeAppearanceModel)
    }
  }
}
