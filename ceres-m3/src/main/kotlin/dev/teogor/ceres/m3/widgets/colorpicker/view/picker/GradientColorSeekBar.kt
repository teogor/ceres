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

package dev.teogor.ceres.m3.widgets.colorpicker.view.picker

import android.content.Context
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import dev.teogor.ceres.m3.R
import dev.teogor.ceres.m3.widgets.colorpicker.model.Color
import dev.teogor.ceres.m3.widgets.colorpicker.model.factory.ColorFactory

abstract class GradientColorSeekBar<C : Color> @JvmOverloads constructor(
  colorFactory: ColorFactory<C>,
  context: Context,
  attrs: AttributeSet? = null,
  defStyle: Int = R.attr.seekBarStyle
) : ColorSeekBar<C>(
  colorFactory,
  context,
  attrs,
  defStyle
) {

  override fun onSetupProgressDrawableLayers(layers: Array<Drawable>): Array<Drawable> {
    return layers.toMutableList().also { list ->
      list.add(
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
    }.toTypedArray()
  }
}
