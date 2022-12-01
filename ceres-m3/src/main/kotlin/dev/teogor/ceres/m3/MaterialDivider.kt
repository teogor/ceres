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
import android.content.res.ColorStateList
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.Px
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import com.google.android.material.R
import com.google.android.material.internal.ThemeEnforcement
import com.google.android.material.resources.MaterialResources
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.theme.overlay.MaterialThemeOverlay

/**
 * A Material divider view.
 *
 *
 * The divider will display the correct default Material colors without the use of a style flag
 * in a layout file. Make sure to set `android:layout_height="wrap_content"` to ensure that
 * the correct thickness is set for the divider.
 */
open class MaterialDivider @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = R.attr.materialDividerStyle
) : View(
  MaterialThemeOverlay.wrap(context, attrs, defStyleAttr, DEF_STYLE_RES),
  attrs,
  defStyleAttr
) {

  private val dividerDrawable: MaterialShapeDrawable = MaterialShapeDrawable()
  private var thickness: Int

  /**
   * Returns the divider color.
   *
   * @attr ref com.google.android.material.R.styleable#MaterialDivider_dividerColor
   * @see .setDividerColor
   *
   * Sets the color of the divider.
   *
   * @param color The color to be set.
   * @attr ref com.google.android.material.R.styleable#MaterialDivider_dividerColor
   * @see .getDividerColor
   */
  var dividerColor: Int
    get() = color
    set(color) {
      if (this.color != color) {
        this.color = color
        dividerDrawable.fillColor = ColorStateList.valueOf(color)
        invalidate()
      }
    }

  @ColorInt
  private var color = 0
  /**
   * Returns the divider's start inset.
   *
   * @attr ref com.google.android.material.R.styleable#MaterialDivider_dividerInsetStart
   * @see .setDividerInsetStart
   */
  /**
   * Sets the start inset of the divider.
   *
   * @param insetStart The start inset to be set.
   * @attr ref com.google.android.material.R.styleable#MaterialDivider_dividerInsetStart
   * @see .getDividerInsetStart
   */
  @get:Px
  var dividerInsetStart: Int
  /**
   * Returns the divider's end inset.
   *
   * @attr ref com.google.android.material.R.styleable#MaterialDivider_dividerInsetEnd
   * @see .setDividerInsetEnd
   */
  /**
   * Sets the end inset of the divider.
   *
   * @param insetEnd The end inset to be set.
   * @attr ref com.google.android.material.R.styleable#MaterialDivider_dividerInsetEnd
   * @see .getDividerInsetEnd
   */
  @get:Px
  var dividerInsetEnd: Int

  init {
    val attributes = ThemeEnforcement.obtainStyledAttributes(
      context,
      attrs,
      R.styleable.MaterialDivider,
      defStyleAttr,
      DEF_STYLE_RES
    )
    thickness = attributes.getDimensionPixelSize(
      R.styleable.MaterialDivider_dividerThickness,
      resources.getDimensionPixelSize(R.dimen.material_divider_thickness)
    )
    dividerInsetStart =
      attributes.getDimensionPixelOffset(R.styleable.MaterialDivider_dividerInsetStart, 0)
    dividerInsetEnd =
      attributes.getDimensionPixelOffset(R.styleable.MaterialDivider_dividerInsetEnd, 0)
    dividerColor = MaterialResources.getColorStateList(
      context,
      attributes,
      R.styleable.MaterialDivider_dividerColor
    )!!.defaultColor
    attributes.recycle()
  }

  override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    val heightMode = MeasureSpec.getMode(heightMeasureSpec)
    var newThickness = measuredHeight
    if (heightMode == MeasureSpec.AT_MOST || heightMode == MeasureSpec.UNSPECIFIED) {
      if (thickness > 0 && newThickness != thickness) {
        newThickness = thickness
      }
      setMeasuredDimension(measuredWidth, newThickness)
    }
  }

  override fun onDraw(canvas: Canvas) {
    super.onDraw(canvas)
    // Apply the insets.
    val isRtl = ViewCompat.getLayoutDirection(this) == ViewCompat.LAYOUT_DIRECTION_RTL
    val left = if (isRtl) dividerInsetEnd else dividerInsetStart
    val right = if (isRtl) width - dividerInsetStart else width - dividerInsetEnd
    dividerDrawable.setBounds(left, 0, right, bottom - top)
    dividerDrawable.draw(canvas)
  }

  /**
   * Sets the thickness of the divider. The divider's `android:layout_height` must be set to
   * `wrap_content` in order for this value to be respected.
   *
   * @param thicknessId The id of the thickness dimension resource to be set.
   * @attr ref com.google.android.material.R.styleable#MaterialDivider_dividerThickness
   * @see .getDividerThickness
   */
  fun setDividerThicknessResource(@DimenRes thicknessId: Int) {
    dividerThickness = context.resources.getDimensionPixelSize(thicknessId)
  }
  /**
   * Returns the `app:dividerThickness` set on the divider.
   *
   * @attr ref com.google.android.material.R.styleable#MaterialDivider_dividerThickness
   * @see .setDividerThickness
   */
  /**
   * Sets the thickness of the divider. The divider's `android:layout_height` must be set to
   * `wrap_content` in order for this value to be respected.
   *
   * @param thickness The thickness value to be set.
   * @attr ref com.google.android.material.R.styleable#MaterialDivider_dividerThickness
   * @see .getDividerThickness
   */
  var dividerThickness: Int
    get() = thickness
    set(thickness) {
      if (this.thickness != thickness) {
        this.thickness = thickness
        requestLayout()
      }
    }

  /**
   * Sets the start inset of the divider.
   *
   * @param insetStartId The id of the inset dimension resource to be set.
   * @attr ref com.google.android.material.R.styleable#MaterialDivider_dividerInsetStart
   * @see .getDividerInsetStart
   */
  fun setDividerInsetStartResource(@DimenRes insetStartId: Int) {
    dividerInsetStart = context.resources.getDimensionPixelOffset(insetStartId)
  }

  /**
   * Sets the end inset of the divider.
   *
   * @param insetEndId The id of the inset dimension resource to be set.
   * @attr ref com.google.android.material.R.styleable#MaterialDivider_dividerInsetEnd
   * @see .getDividerInsetEnd
   */
  fun setDividerInsetEndResource(@DimenRes insetEndId: Int) {
    dividerInsetEnd = context.resources.getDimensionPixelOffset(insetEndId)
  }

  /**
   * Sets the color of the divider.
   *
   * @param colorId The id of the color resource to be set.
   * @attr ref com.google.android.material.R.styleable#MaterialDivider_dividerColor
   * @see .getDividerColor
   */
  fun setDividerColorResource(@ColorRes colorId: Int) {
    dividerColor = ContextCompat.getColor(context, colorId)
  }

  companion object {
    private val DEF_STYLE_RES = R.style.Widget_MaterialComponents_MaterialDivider
  }
}
