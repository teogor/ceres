/*
 * Copyright 2022 Teogor All Rights Reserved.
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
import android.util.TypedValue
import android.view.View
import androidx.annotation.AttrRes
import androidx.annotation.DimenRes
import androidx.annotation.Px
import androidx.annotation.RestrictTo
import com.google.android.material.R

/**
 * Utility methods to work with attributes.
 *
 * @hide
 */
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
object MaterialAttributes {
  /**
   * Returns the [TypedValue] for the provided `attributeResId` or null if the
   * attribute is not present in the current theme.
   */
  @JvmStatic
  fun resolve(context: Context, @AttrRes attributeResId: Int): TypedValue? {
    val typedValue = TypedValue()
    return if (context.theme.resolveAttribute(attributeResId, typedValue, true)) {
      typedValue
    } else null
  }

  fun resolveTypedValueOrThrow(
    componentView: View, @AttrRes attributeResId: Int
  ): TypedValue {
    return resolveTypedValueOrThrow(
      componentView.context,
      attributeResId,
      componentView.javaClass.canonicalName
    )
  }

  @JvmStatic
  fun resolveTypedValueOrThrow(
    context: Context,
    @AttrRes attributeResId: Int,
    errorMessageComponent: String
  ): TypedValue {
    val typedValue = resolve(context, attributeResId)
    if (typedValue == null) {
      val errorMessage =
        ("%1\$s requires a value for the %2\$s attribute to be set in your app theme. "
          + "You can either set the attribute in your theme or "
          + "update your theme to inherit from Theme.MaterialComponents (or a descendant).")
      throw IllegalArgumentException(
        String.format(
          errorMessage,
          errorMessageComponent,
          context.resources.getResourceName(attributeResId)
        )
      )
    }
    return typedValue
  }

  /**
   * Returns the [TypedValue] for the provided `attributeResId`.
   *
   * @throws IllegalArgumentException if the attribute is not present in the current theme.
   */
  fun resolveOrThrow(
    context: Context,
    @AttrRes attributeResId: Int,
    errorMessageComponent: String
  ): Int {
    return resolveTypedValueOrThrow(context, attributeResId, errorMessageComponent).data
  }

  /**
   * Returns the [TypedValue] for the provided `attributeResId`, using the context of
   * the provided `componentView`.
   *
   * @throws IllegalArgumentException if the attribute is not present in the current theme.
   */
  fun resolveOrThrow(componentView: View, @AttrRes attributeResId: Int): Int {
    return resolveTypedValueOrThrow(componentView, attributeResId).data
  }

  /**
   * Returns the boolean value for the provided `attributeResId`.
   *
   * @throws IllegalArgumentException if the attribute is not present in the current theme.
   */
  fun resolveBooleanOrThrow(
    context: Context,
    @AttrRes attributeResId: Int,
    errorMessageComponent: String
  ): Boolean {
    return resolveOrThrow(context, attributeResId, errorMessageComponent) != 0
  }

  /**
   * Returns the boolean value for the provided `attributeResId` or `defaultValue` if
   * the attribute is not a boolean or not present in the current theme.
   */
  fun resolveBoolean(
    context: Context, @AttrRes attributeResId: Int, defaultValue: Boolean
  ): Boolean {
    val typedValue = resolve(context, attributeResId)
    return if (typedValue != null && typedValue.type == TypedValue.TYPE_INT_BOOLEAN) typedValue.data != 0 else defaultValue
  }

  /**
   * Returns the integer value for the provided `attributeResId` or `defaultValue` if
   * the attribute is not a integer or not present in the current theme.
   */
  fun resolveInteger(
    context: Context, @AttrRes attributeResId: Int, defaultValue: Int
  ): Int {
    val typedValue = resolve(context, attributeResId)
    return if (typedValue != null && typedValue.type == TypedValue.TYPE_INT_DEC) typedValue.data else defaultValue
  }

  /**
   * Returns the minimum touch target size, acceptable for accessibility, in pixels.
   */
  @Px
  fun resolveMinimumAccessibleTouchTarget(context: Context): Int {
    return resolveDimension(
      context, R.attr.minTouchTargetSize, R.dimen.mtrl_min_touch_target_size
    )
  }

  /**
   * Returns the pixel value of the dimension specified by `attributeResId`. Defaults to
   * `defaultDimenResId` if `attributeResId` cannot be found or is not a dimension
   * within the given `context`.
   */
  @JvmStatic
  @Px
  fun resolveDimension(
    context: Context,
    @AttrRes attributeResId: Int,
    @DimenRes defaultDimenResId: Int
  ): Int {
    val dimensionValue = resolve(context, attributeResId)
    return if (dimensionValue == null || dimensionValue.type != TypedValue.TYPE_DIMENSION) {
      context.resources.getDimension(defaultDimenResId).toInt()
    } else {
      dimensionValue.getDimension(context.resources.displayMetrics).toInt()
    }
  }
}
