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

package dev.teogor.ceres.m3.widgets.colorpicker.alpha

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Shader
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.util.AttributeSet
import dev.teogor.ceres.m3.R
import dev.teogor.ceres.m3.widgets.colorpicker.model.Color
import dev.teogor.ceres.m3.widgets.colorpicker.model.factory.ColorFactory
import dev.teogor.ceres.m3.widgets.colorpicker.view.picker.ColorSeekBar

// TODO: Minimize resource reads
// TODO: Make bg_transparency_pattern programmatic
// TODO: Think on making that non-abstract
// TODO: Think on adding AlphaColor layer
abstract class AlphaColorPickerSeekBar<C : Color> @JvmOverloads constructor(
  colorFactory: ColorFactory<C>,
  context: Context,
  attrs: AttributeSet? = null,
  defStyle: Int = R.attr.seekBarStyle
) :
  ColorSeekBar<C>(
    colorFactory,
    context,
    attrs,
    defStyle
  ) {

  // TODO: Handle rounded corners
  override fun onSetupProgressDrawableLayers(layers: Array<Drawable>): Array<Drawable> {
    val layerList = layers.toMutableList()

    val options = BitmapFactory.Options().also {
      it.inMutable = true
    }
    val bitmap = BitmapFactory.decodeResource(
      resources,
      R.drawable.bg_transparency_tile_horizontal,
      options
    )

    val patternDrawable = BitmapDrawable(
      resources,
      bitmap
    ).also {
      it.setTileModeXY(
        Shader.TileMode.REPEAT,
        Shader.TileMode.CLAMP
      )
    }

    // val id = ScaleDrawable(
    //  patternDrawable,
    //  Gravity.FILL_HORIZONTAL,
    //  1f,
    //  0.5f
    // )

    layerList.add(
      patternDrawable
    )

    layerList.add(
      GradientDrawable().also {
        it.orientation = GradientDrawable.Orientation.LEFT_RIGHT
        it.cornerRadius =
          resources.getDimensionPixelOffset(R.dimen.acp_seek_progress_corner_radius)
            .toFloat()
        it.shape = GradientDrawable.RECTANGLE
        // TODO: Make stroke configurable
        // it.setStroke(
        //  4,
        //  Color.rgb(
        //    192,
        //    192,
        //    192
        //  )
        // )
      }
    )

    return layerList.toTypedArray()
  }

  override fun onRefreshProgressDrawable(progressDrawable: LayerDrawable) {
    (progressDrawable.getDrawable(1) as GradientDrawable).colors =
      intArrayOf(
        android.graphics.Color.TRANSPARENT,
        colorConverter.convertToOpaqueColorInt(internalPickedColor)
      )
  }

  override fun onRefreshThumb(thumbColoringDrawables: Set<Drawable>) {
    thumbColoringDrawables.forEach {
      when (it) {
        is GradientDrawable -> {
          paintThumbStroke(it)
        }
        is LayerDrawable -> {
          paintThumbStroke(it.getDrawable(0) as GradientDrawable)
        }
      }
    }
  }

  private fun paintThumbStroke(drawable: GradientDrawable) {
    drawable.setStroke(
      thumbStrokeWidthPx,
      colorConverter.convertToColorInt(internalPickedColor)
    )
  }
}
