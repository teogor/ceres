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

package dev.teogor.ceres.m3.widgets.colorpicker.cmyk

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.util.AttributeSet
import androidx.core.graphics.ColorUtils
import dev.teogor.ceres.m3.R
import dev.teogor.ceres.m3.widgets.colorpicker.converter.IntegerCMYKColorConverter
import dev.teogor.ceres.m3.widgets.colorpicker.model.IntegerCMYKColor
import dev.teogor.ceres.m3.widgets.colorpicker.model.factory.CMYKColorFactory
import dev.teogor.ceres.m3.widgets.colorpicker.view.picker.ColorSeekBar
import dev.teogor.ceres.m3.widgets.colorpicker.view.picker.GradientColorSeekBar

class CMYKColorPickerSeekBar @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyle: Int = R.attr.seekBarStyle
) :
  GradientColorSeekBar<IntegerCMYKColor>(
    CMYKColorFactory(),
    context,
    attrs,
    defStyle
  ) {

  override val colorConverter: IntegerCMYKColorConverter
    get() = super.colorConverter as IntegerCMYKColorConverter

  private var modeInitialized = false
  private var _mode: Mode? = null
  var mode: Mode
    get() {
      return requireNotNull(_mode) { "Mode is not initialized yet" }
    }
    set(value) {
      modeInitialized = true
      if (_mode == value) {
        return
      }
      _mode = value
      refreshProperties()
      refreshProgressFromCurrentColor()
      refreshProgressDrawable()
      refreshThumb()
    }

  private var coloringModeInitialized = false
  private var _coloringMode: ColoringMode? = null
  var coloringMode: ColoringMode
    get() {
      return requireNotNull(_coloringMode) { "Coloring mode is not initialized yet" }
    }
    set(value) {
      coloringModeInitialized = true
      if (_coloringMode == value) {
        return
      }
      _coloringMode = value
      refreshProgressDrawable()
      refreshThumb()
    }

  init {
    init(attrs)
  }

  private fun init(attrs: AttributeSet? = null) {
    val typedArray = context.theme.obtainStyledAttributes(
      attrs,
      R.styleable.CMYKColorPickerSeekBar,
      0,
      0
    )

    mode = Mode.values()[
      typedArray.getInteger(
        R.styleable.CMYKColorPickerSeekBar_cmykMode,
        DEFAULT_MODE.ordinal
      )
    ]
    coloringMode = ColoringMode.values()[
      typedArray.getInteger(
        R.styleable.CMYKColorPickerSeekBar_cmykColoringMode,
        DEFAULT_COLORING_MODE.ordinal
      )
    ]

    typedArray.recycle()
  }

  override fun setMax(max: Int) {
    if (modeInitialized && max != mode.absoluteProgress) {
      throw IllegalArgumentException("Current mode supports ${mode.absoluteProgress} max value only, was $max")
    }
    super.setMax(max)
  }

  override fun onUpdateColorFrom(color: IntegerCMYKColor, value: IntegerCMYKColor) {
    color.setFrom(value)
  }

  override fun onRefreshProperties() {
    if (!modeInitialized) {
      return
    }
    max = mode.absoluteProgress
  }

  override fun onRefreshProgressFromColor(color: IntegerCMYKColor): Int? {
    if (!modeInitialized) {
      return null
    }

    return -mode.minProgress + when (mode) {
      Mode.MODE_C -> {
        internalPickedColor.intC
      }
      Mode.MODE_M -> {
        internalPickedColor.intM
      }
      Mode.MODE_Y -> {
        internalPickedColor.intY
      }
      Mode.MODE_K -> {
        internalPickedColor.intK
      }
    }
  }

  override fun onRefreshProgressDrawable(progressDrawable: LayerDrawable) {
    if (!coloringModeInitialized || !modeInitialized) {
      return
    }

    (progressDrawable.getDrawable(0) as GradientDrawable).colors = when (mode) {
      Mode.MODE_C -> {
        when (coloringMode) {
          ColoringMode.PURE_COLOR -> Mode.MODE_C.checkpoints
          ColoringMode.OUTPUT_COLOR -> TODO()
        }
      }
      Mode.MODE_M -> {
        when (coloringMode) {
          ColoringMode.PURE_COLOR -> Mode.MODE_M.checkpoints
          ColoringMode.OUTPUT_COLOR -> TODO()
        }
      }
      Mode.MODE_Y -> {
        when (coloringMode) {
          ColoringMode.PURE_COLOR -> Mode.MODE_Y.checkpoints
          ColoringMode.OUTPUT_COLOR -> TODO()
        }
      }
      Mode.MODE_K -> {
        when (coloringMode) {
          ColoringMode.PURE_COLOR -> Mode.MODE_K.checkpoints
          ColoringMode.OUTPUT_COLOR -> TODO()
        }
      }
    }
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

  override fun onRefreshColorFromProgress(color: IntegerCMYKColor, progress: Int): Boolean {
    if (!modeInitialized) {
      return false
    }

    val unmaskedProgress = mode.minProgress + progress
    return when (mode) {
      Mode.MODE_C -> {
        val currentH = color.intC
        if (currentH != unmaskedProgress) {
          color.intC = unmaskedProgress
          true
        } else {
          false
        }
      }
      Mode.MODE_M -> {
        val currentS = color.intM
        if (currentS != unmaskedProgress) {
          color.intM = unmaskedProgress
          true
        } else {
          false
        }
      }
      Mode.MODE_Y -> {
        val currentL = color.intY
        if (currentL != unmaskedProgress) {
          color.intY = unmaskedProgress
          true
        } else {
          false
        }
      }
      Mode.MODE_K -> {
        val currentL = color.intK
        if (currentL != unmaskedProgress) {
          color.intK = unmaskedProgress
          true
        } else {
          false
        }
      }
    }
  }

  // TODO: Refactor
  private fun paintThumbStroke(drawable: GradientDrawable) {
    if (!coloringModeInitialized || !modeInitialized) {
      return
    }

    val currentProgress = progress
    drawable.setStroke(
      thumbStrokeWidthPx,
      when (mode) {
        Mode.MODE_C -> {
          when (coloringMode) {
            ColoringMode.PURE_COLOR -> ColorUtils.blendARGB(
              Color.WHITE,
              Color.CYAN,
              currentProgress.coerceAtLeast(COERCE_AT_LEAST_COMPONENT) / mode.maxProgress.toFloat()
            )
            ColoringMode.OUTPUT_COLOR -> TODO()
          }
        }
        Mode.MODE_M -> {
          when (coloringMode) {
            ColoringMode.PURE_COLOR -> ColorUtils.blendARGB(
              Color.WHITE,
              Color.MAGENTA,
              currentProgress.coerceAtLeast(COERCE_AT_LEAST_COMPONENT) / mode.maxProgress.toFloat()
            )
            ColoringMode.OUTPUT_COLOR -> TODO()
          }
        }
        Mode.MODE_Y -> {
          when (coloringMode) {
            ColoringMode.PURE_COLOR -> ColorUtils.blendARGB(
              Color.WHITE,
              Color.YELLOW,
              currentProgress.coerceAtLeast(COERCE_AT_LEAST_COMPONENT) / mode.maxProgress.toFloat()
            )
            ColoringMode.OUTPUT_COLOR -> TODO()
          }
        }
        Mode.MODE_K -> {
          when (coloringMode) {
            ColoringMode.PURE_COLOR -> ColorUtils.blendARGB(
              Color.WHITE,
              Color.BLACK,
              currentProgress.coerceAtLeast(COERCE_AT_LEAST_COMPONENT) / mode.maxProgress.toFloat()
            )
            ColoringMode.OUTPUT_COLOR -> TODO()
          }
        }
      }
    )
  }

  enum class ColoringMode {
    PURE_COLOR,
    OUTPUT_COLOR
  }

  enum class Mode(
    override val minProgress: Int,
    override val maxProgress: Int,
    val checkpoints: IntArray
  ) : ColorSeekBar.Mode {
    MODE_C(
      IntegerCMYKColor.Component.C.minValue,
      IntegerCMYKColor.Component.C.maxValue,
      intArrayOf(
        Color.WHITE,
        Color.CYAN
      )
    ),
    MODE_M(
      IntegerCMYKColor.Component.M.minValue,
      IntegerCMYKColor.Component.M.maxValue,
      intArrayOf(
        Color.WHITE,
        Color.MAGENTA
      )
    ),
    MODE_Y(
      IntegerCMYKColor.Component.Y.minValue,
      IntegerCMYKColor.Component.Y.maxValue,
      intArrayOf(
        Color.WHITE,
        Color.YELLOW
      )
    ),
    MODE_K(
      IntegerCMYKColor.Component.K.minValue,
      IntegerCMYKColor.Component.K.maxValue,
      intArrayOf(
        Color.WHITE,
        Color.BLACK
      )
    ),
  }

  companion object {
    private val DEFAULT_MODE = Mode.MODE_C
    private val DEFAULT_COLORING_MODE = ColoringMode.PURE_COLOR

    // TODO: Make configurable
    private const val COERCE_AT_LEAST_COMPONENT = 15
  }
}
