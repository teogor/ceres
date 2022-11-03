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

package dev.teogor.ceres.m3.resources

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.util.TypedValue
import androidx.annotation.RestrictTo
import androidx.annotation.StyleRes
import androidx.annotation.StyleableRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.TintTypedArray
import com.google.android.material.R
import com.google.android.material.resources.TextAppearance

/**
 * Utility methods to resolve resources for components.
 *
 * @hide
 */
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
object MaterialResources {
  /**
   * Value of the system's x1.3 font scale size.
   */
  private const val FONT_SCALE_1_3 = 1.3f

  /**
   * Value of the system's x2 font scale size.
   */
  private const val FONT_SCALE_2_0 = 2f

  /**
   * Returns the [ColorStateList] from the given [TypedArray] attributes. The resource
   * can include themeable attributes, regardless of API level.
   */
  fun getColorStateList(
    context: Context,
    attributes: TypedArray,
    @StyleableRes index: Int
  ): ColorStateList? {
    if (attributes.hasValue(index)) {
      val resourceId = attributes.getResourceId(index, 0)
      if (resourceId != 0) {
        val value = AppCompatResources.getColorStateList(context, resourceId)
        if (value != null) {
          return value
        }
      }
    }

    // Reading a single color with getColorStateList() on API 15 and below doesn't always
    // correctly
    // read the value. Instead we'll first try to read the color directly here.
    return attributes.getColorStateList(index)
  }

  /**
   * Returns the [ColorStateList] from the given [TintTypedArray] attributes. The
   * resource can include themeable attributes, regardless of API level.
   */
  fun getColorStateList(
    context: Context,
    attributes: TintTypedArray,
    @StyleableRes index: Int
  ): ColorStateList? {
    if (attributes.hasValue(index)) {
      val resourceId = attributes.getResourceId(index, 0)
      if (resourceId != 0) {
        val value = AppCompatResources.getColorStateList(context, resourceId)
        if (value != null) {
          return value
        }
      }
    }

    // Reading a single color with getColorStateList() on API 15 and below doesn't always
    // correctly
    // read the value. Instead we'll first try to read the color directly here.
    return attributes.getColorStateList(index)
  }

  /**
   * Returns the drawable object from the given attributes.
   *
   *
   * This method supports inflation of `<vector>` and `<animated-vector>` resources
   * on devices where platform support is not available.
   */
  fun getDrawable(
    context: Context,
    attributes: TypedArray,
    @StyleableRes index: Int
  ): Drawable? {
    if (attributes.hasValue(index)) {
      val resourceId = attributes.getResourceId(index, 0)
      if (resourceId != 0) {
        val value = AppCompatResources.getDrawable(context, resourceId)
        if (value != null) {
          return value
        }
      }
    }
    return attributes.getDrawable(index)
  }

  /**
   * Returns a TextAppearanceSpan object from the given attributes.
   *
   *
   * You only need this if you are drawing text manually. Normally, TextView takes care of
   * this.
   */
  fun getTextAppearance(
    context: Context,
    attributes: TypedArray,
    @StyleableRes index: Int
  ): TextAppearance? {
    if (attributes.hasValue(index)) {
      val resourceId = attributes.getResourceId(index, 0)
      if (resourceId != 0) {
        return TextAppearance(context, resourceId)
      }
    }
    return null
  }

  /**
   * Retrieve a dimensional unit attribute at <var>index</var> for use as a size in raw pixels. A
   * size conversion involves rounding the base value, and ensuring that a non-zero base value is
   * at least one pixel in size.
   *
   *
   * This method will throw an exception if the attribute is defined but is not a dimension.
   *
   * @param context      The Context the view is running in, through which the current theme,
   * resources, etc can be accessed.
   * @param attributes   array of typed attributes from which the dimension unit must be read.
   * @param index        Index of attribute to retrieve.
   * @param defaultValue Value to return if the attribute is not defined or not a resource.
   * @return Attribute dimension value multiplied by the appropriate metric and truncated to
   * integer pixels, or defaultValue if not defined.
   * @throws UnsupportedOperationException if the attribute is defined but is not a dimension.
   * @see TypedArray.getDimensionPixelSize
   */
  fun getDimensionPixelSize(
    context: Context,
    attributes: TypedArray,
    @StyleableRes index: Int,
    defaultValue: Int
  ): Int {
    val value = TypedValue()
    if (!attributes.getValue(index, value) || value.type != TypedValue.TYPE_ATTRIBUTE) {
      return attributes.getDimensionPixelSize(index, defaultValue)
    }
    val styledAttrs = context.theme.obtainStyledAttributes(intArrayOf(value.data))
    val dimension = styledAttrs.getDimensionPixelSize(0, defaultValue)
    styledAttrs.recycle()
    return dimension
  }

  /**
   * Returns whether the font scale size is at least [.FONT_SCALE_1_3].
   */
  fun isFontScaleAtLeast1_3(context: Context): Boolean {
    return context.resources.configuration.fontScale >= FONT_SCALE_1_3
  }

  /**
   * Returns whether the font scale size is at least [.FONT_SCALE_2_0].
   */
  fun isFontScaleAtLeast2_0(context: Context): Boolean {
    return context.resources.configuration.fontScale >= FONT_SCALE_2_0
  }

  /**
   * Return the `R.styleable.TextAppearance_android_textSize` value from a text appearance
   * style at its density scaled value only.
   *
   *
   * If the text size from the text appearance is using dp, the density scaled value will be
   * returned. If the text size is using sp, the raw value will be scaled according to display
   * density but not font scale, as if it were a dp value.
   *
   *
   * This is used for components that do not scale their text due to being space constrained
   * and instead offer alternative methods of showing scaled text.
   */
  fun getUnscaledTextSize(
    context: Context,
    @StyleRes textAppearance: Int,
    defValue: Int
  ): Int {
    if (textAppearance == 0) {
      return defValue
    }
    val a = context.obtainStyledAttributes(textAppearance, R.styleable.TextAppearance)
    val v = TypedValue()
    val available = a.getValue(R.styleable.TextAppearance_android_textSize, v)
    a.recycle()
    if (!available) {
      return defValue
    }

    // If the resource is in scaled pixels (sp) manually unpack the resource and scale to
    // density
    // but not font scale.
    return if (getComplexUnit(v) == TypedValue.COMPLEX_UNIT_SP) {
      // Get the raw value. If text size is set to 14sp in the dimen file, this will return
      // 14.
      // Scale the raw value using density and round to avoid truncating.
      Math.round(
        TypedValue.complexToFloat(v.data) *
          context.resources.displayMetrics.density
      )
    } else TypedValue.complexToDimensionPixelSize(
      v.data,
      context.resources.displayMetrics
    )

    // If the resource is not is sp, return with regular resource system scaling.
  }

  /**
   * Return the complex unit type for the given value.
   *
   *
   * This is a compat method of [TypedValue.getComplexUnit], which is only available on
   * API 22 and above.
   */
  private fun getComplexUnit(tv: TypedValue): Int {
    return if (VERSION.SDK_INT >= VERSION_CODES.LOLLIPOP_MR1) {
      tv.complexUnit
    } else {
      TypedValue.COMPLEX_UNIT_MASK and (tv.data shr TypedValue.COMPLEX_UNIT_SHIFT)
    }
  }

  /**
   * Returns the @StyleableRes index that contains value in the attributes array. If both indices
   * contain values, the first given index takes precedence and is returned.
   */
  @StyleableRes
  fun getIndexWithValue(
    attributes: TypedArray,
    @StyleableRes a: Int,
    @StyleableRes b: Int
  ): Int {
    return if (attributes.hasValue(a)) {
      a
    } else b
  }
}
