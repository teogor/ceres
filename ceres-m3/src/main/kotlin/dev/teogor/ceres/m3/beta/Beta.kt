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

package dev.teogor.ceres.m3.beta

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.RippleDrawable
import androidx.annotation.Dimension
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.ShapeAppearanceModel
import dev.teogor.ceres.components.view.Shape
import dev.teogor.ceres.extensions.colorStateList
import dev.teogor.ceres.m3.ColorM3
import dev.teogor.ceres.m3.extension.getColorM3
import dev.teogor.ceres.m3.theme.ColorScheme
import dev.teogor.ceres.m3.theme.SurfaceOverlay
import dev.teogor.ceres.m3.theme.ThemeM3
import dev.teogor.ceres.m3.theme.inverse

fun Beta.BackgroundDrawable.currentBackground(isBackground: Boolean): Beta.BackgroundData {
  return if (isBackground) {
    this.background
  } else {
    this.foreground
  }
}

fun Beta.BackgroundDrawable.hasAutoRippleDrawable(): Boolean {
  return foreground == Beta.BackgroundData.Transparent && rippleEnabled
}

fun Beta.BackgroundDrawable.toRippleScheme() {
  val color = this.background.color
  val surfaceTintOverlay = this.background.surfaceTintOverlay
  foreground = Beta.BackgroundData(
    color = color.inverse(),
    surfaceTintOverlay = surfaceTintOverlay.inverse(),
    surfaceTint = 0.7f
  )
}

class Beta {

  data class BackgroundDrawable(
    @Dimension val cornerSize: Float = 0f,
    val background: BackgroundData = BackgroundData.Transparent,
    var foreground: BackgroundData = BackgroundData.Transparent,
    val rippleEnabled: Boolean = false,
    val shape: Shape = Shape.Rectangle
  ) {

    companion object {
      val Default = BackgroundDrawable()
    }
  }

  data class BackgroundData(
    val color: ColorM3 = ColorM3.Primary,
    val surfaceTint: Float = 0f,
    val surfaceTintOverlay: ColorM3 = ColorM3.Surface,
    val isTransparent: Boolean = false
  ) {

    val fillColor = if (isTransparent || color == ColorM3.OnBackground) {
      Color.TRANSPARENT
    } else {
      SurfaceOverlay(
        themeOverlayColor = getColorM3(color),
        themeSurfaceColor = getColorM3(surfaceTintOverlay)
      ).forAlpha(surfaceTint)
    }

    companion object {
      val Transparent = BackgroundData(isTransparent = true)
    }
  }

  class ShapeDrawableM3 : MaterialShapeDrawable {

    private lateinit var backgroundDrawable: BackgroundDrawable
    private var isBackground: Boolean = true

    constructor(shapeAppearanceModel: ShapeAppearanceModel) : super(shapeAppearanceModel)

    private constructor(
      shapeAppearanceModel: ShapeAppearanceModel,
      backgroundDrawable: BackgroundDrawable,
      isBackground: Boolean = true
    ) : super(shapeAppearanceModel) {
      this.backgroundDrawable = backgroundDrawable
      this.isBackground = isBackground

      computeBackgroundColor()
    }

    private fun computeBackgroundColor() {
      val backgroundData = backgroundDrawable.currentBackground(isBackground)
      fillColor = backgroundData.fillColor.colorStateList
    }

    fun isBackgroundLight(): Boolean {
      return false
    }

    fun toBuilder() = Builder(this)

    /** Builder to create instances of [ShapeDrawableM3]s.  */
    data class Builder(
      private var shapeAppearanceModel: ShapeAppearanceModel,
      private var backgroundDrawable: BackgroundDrawable? = null,
      private var isBackground: Boolean = true
    ) {

      internal constructor(shapeDrawable: MaterialShapeDrawable) : this(shapeDrawable.shapeAppearanceModel)

      fun backgroundDrawable(backgroundDrawable: BackgroundDrawable) = apply {
        this.backgroundDrawable = backgroundDrawable
      }

      fun isBackground(isBackground: Boolean) = apply {
        this.isBackground = isBackground
      }

      fun build() = ShapeDrawableM3(
        shapeAppearanceModel,
        backgroundDrawable!!,
        isBackground
      )
    }
  }

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
      private var backgroundDrawable: BackgroundDrawable = BackgroundDrawable.Default
    ) {

      internal constructor(builder: Builder) : this(
        shapeAppearanceModel = builder.shapeAppearanceModel,
        backgroundDrawable = builder.backgroundDrawable
      )

      private fun getSchemeColor(): ColorScheme {
        return ThemeM3.currentColorScheme()
      }

      fun backgroundDrawable(backgroundDrawable: BackgroundDrawable) = apply {
        this.backgroundDrawable = backgroundDrawable
      }

      fun build(): RippleDrawableM3 {
        if (backgroundDrawable.hasAutoRippleDrawable()) {
          backgroundDrawable.apply {
            toRippleScheme()
          }
        }
        val content = ShapeDrawableM3(shapeAppearanceModel)
          .toBuilder()
          .backgroundDrawable(backgroundDrawable)
          .build()
        val mask = ShapeDrawableM3(shapeAppearanceModel)
          .toBuilder()
          .backgroundDrawable(backgroundDrawable)
          .isBackground(false)
          .build()

        // todo fixme is this the right color for ripple???
        return RippleDrawableM3(
          color = mask.fillColor!!,
          content = content,
          mask = mask,
          builder = this
        )
      }
    }

    companion object {

      /** Returns a builder with the edges and corners from this [ShapeAppearanceModel]  */
      fun toBuilder(shapeAppearanceModel: ShapeAppearanceModel): Builder {
        return Builder(shapeAppearanceModel)
      }
    }
  }
}
