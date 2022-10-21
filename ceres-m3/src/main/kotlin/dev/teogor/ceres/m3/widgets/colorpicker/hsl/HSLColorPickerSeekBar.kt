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

package dev.teogor.ceres.m3.widgets.colorpicker.hsl

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.util.AttributeSet
import androidx.core.graphics.ColorUtils
import dev.teogor.ceres.m3.R
import dev.teogor.ceres.m3.widgets.colorpicker.converter.IntegerHSLColorConverter
import dev.teogor.ceres.m3.widgets.colorpicker.model.IntegerHSLColor
import dev.teogor.ceres.m3.widgets.colorpicker.model.factory.HSLColorFactory
import dev.teogor.ceres.m3.widgets.colorpicker.view.picker.ColorSeekBar
import dev.teogor.ceres.m3.widgets.colorpicker.view.picker.GradientColorSeekBar

// TODO: Minimize resource reads
// TODO: Add logger solution
// TODO: Add call flow diagram
// TODO: Add checks and reduce calls count
// TODO: Limit used SDK properties usage
class HSLColorPickerSeekBar @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyle: Int = R.attr.seekBarStyle
) :
  GradientColorSeekBar<IntegerHSLColor>(
    HSLColorFactory(),
    context,
    attrs,
    defStyle
  ) {

  override val colorConverter: IntegerHSLColorConverter
    get() = super.colorConverter as IntegerHSLColorConverter

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

  private val paintDrawableStrokeSaturationHSLCache by lazy { IntegerHSLColor() }
  private val paintDrawableStrokeLightnessHSLCache by lazy { IntegerHSLColor() }

  private val progressDrawableSaturationColorsCache by lazy { IntArray(2) }
  private val progressDrawableLightnessColorsCache by lazy { IntArray(3) }

  private val zeroSaturationOutputColorHSLCache by lazy { ZERO_SATURATION_COLOR_HSL.clone() }

  private val createHueOutputColorCheckpointsHSLCache by lazy {
    FloatArray(3)
  }

  init {
    init(attrs)
  }

  private fun init(attrs: AttributeSet? = null) {
    val typedArray = context.theme.obtainStyledAttributes(
      attrs,
      R.styleable.HSLColorPickerSeekBar,
      0,
      0
    )

    mode = Mode.values()[
      typedArray.getInteger(
        R.styleable.HSLColorPickerSeekBar_hslMode,
        DEFAULT_MODE.ordinal
      )
    ]
    coloringMode = ColoringMode.values()[
      typedArray.getInteger(
        R.styleable.HSLColorPickerSeekBar_hslColoringMode,
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

  override fun onUpdateColorFrom(color: IntegerHSLColor, value: IntegerHSLColor) {
    color.setFrom(value)
  }

  override fun onRefreshProperties() {
    if (!modeInitialized) {
      return
    }
    max = mode.absoluteProgress
  }

  override fun onRefreshProgressFromColor(color: IntegerHSLColor): Int? {
    if (!modeInitialized) {
      return null
    }

    return -mode.minProgress + when (mode) {
      Mode.MODE_HUE -> {
        internalPickedColor.intH
      }
      Mode.MODE_SATURATION -> {
        internalPickedColor.intS
      }
      Mode.MODE_LIGHTNESS -> {
        internalPickedColor.intL
      }
    }
  }

  // TODO: Get rid of toIntArray allocations
  private fun createHueOutputColorCheckpoints(): IntArray {
    return HUE_COLOR_CHECKPOINTS
      .map {
        ColorUtils.colorToHSL(
          it,
          createHueOutputColorCheckpointsHSLCache
        )
        createHueOutputColorCheckpointsHSLCache[IntegerHSLColor.Component.S.index] =
          internalPickedColor.floatS
        createHueOutputColorCheckpointsHSLCache[IntegerHSLColor.Component.L.index] =
          internalPickedColor.floatL
        ColorUtils.HSLToColor(createHueOutputColorCheckpointsHSLCache)
      }.toIntArray()
  }

  private fun refreshZeroSaturationOutputColorHSLCache() {
    zeroSaturationOutputColorHSLCache[2] = internalPickedColor.floatL
  }

  override fun onRefreshProgressDrawable(progressDrawable: LayerDrawable) {
    if (!coloringModeInitialized || !modeInitialized) {
      return
    }

    (progressDrawable.getDrawable(0) as GradientDrawable).colors = when (mode) {
      Mode.MODE_HUE -> {
        when (coloringMode) {
          ColoringMode.PURE_COLOR -> HUE_COLOR_CHECKPOINTS
          ColoringMode.OUTPUT_COLOR -> createHueOutputColorCheckpoints()
        }
      }
      Mode.MODE_SATURATION -> {
        when (coloringMode) {
          ColoringMode.PURE_COLOR -> {
            progressDrawableSaturationColorsCache.also {
              it[0] =
                ZERO_SATURATION_COLOR_INT
              it[1] = colorConverter.convertToPureHueColorInt(internalPickedColor)
            }
          }
          ColoringMode.OUTPUT_COLOR -> {
            refreshZeroSaturationOutputColorHSLCache()

            progressDrawableSaturationColorsCache.also {
              it[0] =
                ColorUtils.HSLToColor(zeroSaturationOutputColorHSLCache)
              it[1] = colorConverter.convertToColorInt(internalPickedColor)
            }
          }
        }
      }
      Mode.MODE_LIGHTNESS -> {
        progressDrawableLightnessColorsCache.also {
          it[0] = Color.BLACK
          it[1] = when (coloringMode) {
            ColoringMode.PURE_COLOR -> colorConverter.convertToPureHueColorInt(
              internalPickedColor
            )
            ColoringMode.OUTPUT_COLOR -> colorConverter.convertToDefaultLightness(
              internalPickedColor
            )
          }
          it[2] = Color.WHITE
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

  override fun onRefreshColorFromProgress(color: IntegerHSLColor, progress: Int): Boolean {
    if (!modeInitialized) {
      return false
    }

    val unmaskedProgress = mode.minProgress + progress
    return when (mode) {
      Mode.MODE_HUE -> {
        val currentH = color.intH
        if (currentH != unmaskedProgress) {
          color.intH = unmaskedProgress
          true
        } else {
          false
        }
      }
      Mode.MODE_SATURATION -> {
        val currentS = color.intS
        if (currentS != unmaskedProgress) {
          color.intS = unmaskedProgress
          true
        } else {
          false
        }
      }
      Mode.MODE_LIGHTNESS -> {
        val currentL = color.intL
        if (currentL != unmaskedProgress) {
          color.intL = unmaskedProgress
          true
        } else {
          false
        }
      }
    }
  }

  // TODO: Deal with int arrays
  private fun paintThumbStroke(drawable: GradientDrawable) {
    if (!coloringModeInitialized || !modeInitialized) {
      return
    }

    drawable.setStroke(
      thumbStrokeWidthPx,
      when (mode) {
        Mode.MODE_HUE -> {
          when (coloringMode) {
            ColoringMode.PURE_COLOR -> {
              colorConverter.convertToPureHueColorInt(internalPickedColor)
            }
            ColoringMode.OUTPUT_COLOR -> {
              colorConverter.convertToColorInt(internalPickedColor)
            }
          }
        }
        Mode.MODE_SATURATION -> {
          when (coloringMode) {
            ColoringMode.PURE_COLOR -> {
              colorConverter.convertToColorInt(
                paintDrawableStrokeSaturationHSLCache.also {
                  it.copyValuesFrom(
                    intArrayOf(
                      internalPickedColor.intH,
                      internalPickedColor.intS,
                      IntegerHSLColor.Component.L.defaultValue
                    )
                  )
                }
              )
            }
            ColoringMode.OUTPUT_COLOR -> {
              colorConverter.convertToColorInt(internalPickedColor)
            }
          }
        }
        Mode.MODE_LIGHTNESS -> {
          when (coloringMode) {
            ColoringMode.PURE_COLOR -> {
              colorConverter.convertToColorInt(
                paintDrawableStrokeLightnessHSLCache.also {
                  it.copyValuesFrom(
                    intArrayOf(
                      internalPickedColor.intH,
                      IntegerHSLColor.Component.S.defaultValue,
                      internalPickedColor.intL.coerceAtMost(
                        COERCE_AT_MOST_LIGHTNING
                      )
                    )
                  )
                }
              )
            }
            ColoringMode.OUTPUT_COLOR -> {
              colorConverter.convertToColorInt(
                paintDrawableStrokeLightnessHSLCache.also {
                  it.copyValuesFrom(
                    intArrayOf(
                      internalPickedColor.intH,
                      internalPickedColor.intS,
                      internalPickedColor.intL.coerceAtMost(
                        COERCE_AT_MOST_LIGHTNING
                      )
                    )
                  )
                }
              )
            }
          }
        }
      }
    )
  }

  override fun toString(): String {
    return "HSLColorPickerSeekBar(tag = $tag, _mode=${if (modeInitialized) mode else null}, _currentColor=$internalPickedColor)"
  }

  enum class ColoringMode {
    PURE_COLOR,
    OUTPUT_COLOR
  }

  enum class Mode(
    override val minProgress: Int,
    override val maxProgress: Int
  ) : ColorSeekBar.Mode {
    // H from HSV/HSL/HSI/HSB
    MODE_HUE(
      IntegerHSLColor.Component.H.minValue,
      IntegerHSLColor.Component.H.maxValue
    ),

    // S from HSV/HSL/HSI/HSB
    MODE_SATURATION(
      IntegerHSLColor.Component.S.minValue,
      IntegerHSLColor.Component.S.maxValue
    ),

    // INTENSITY, L/I from HSL/HSI
    MODE_LIGHTNESS(
      IntegerHSLColor.Component.L.minValue,
      IntegerHSLColor.Component.L.maxValue
    )
  }

  companion object {
    private val DEFAULT_MODE = Mode.MODE_HUE
    private val DEFAULT_COLORING_MODE = ColoringMode.PURE_COLOR

    // TODO: Make configurable
    private const val COERCE_AT_MOST_LIGHTNING = 90
    private val HUE_COLOR_CHECKPOINTS = intArrayOf(
      Color.RED,
      Color.YELLOW,
      Color.GREEN,
      Color.CYAN,
      Color.BLUE,
      Color.MAGENTA,
      Color.RED
    )
    private val ZERO_SATURATION_COLOR_INT = Color.rgb(
      128,
      128,
      128
    )
    private val ZERO_SATURATION_COLOR_HSL = FloatArray(3).also {
      ColorUtils.colorToHSL(
        ZERO_SATURATION_COLOR_INT,
        it
      )
    }
  }
}
