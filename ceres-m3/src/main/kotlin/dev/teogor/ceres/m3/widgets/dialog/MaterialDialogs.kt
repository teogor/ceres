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

package dev.teogor.ceres.m3.widgets.dialog

import android.content.Context
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.graphics.drawable.InsetDrawable
import androidx.annotation.AttrRes
import androidx.annotation.RestrictTo
import androidx.core.view.ViewCompat
import com.google.android.material.R
import com.google.android.material.internal.ThemeEnforcement

/**
 * Utility methods for handling Dialog Windows
 *
 * @hide
 */
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
object MaterialDialogs {
  @JvmStatic
  fun insetDrawable(
    drawable: Drawable?,
    backgroundInsets: Rect
  ): InsetDrawable {
    return InsetDrawable(
      drawable,
      backgroundInsets.left,
      backgroundInsets.top,
      backgroundInsets.right,
      backgroundInsets.bottom
    )
  }

  @JvmStatic
  fun getDialogBackgroundInsets(
    context: Context,
    @AttrRes defaultStyleAttribute: Int,
    defaultStyleResource: Int
  ): Rect {
    val attributes = ThemeEnforcement.obtainStyledAttributes(
      context,
      null,
      R.styleable.MaterialAlertDialog,
      defaultStyleAttribute,
      defaultStyleResource
    )
    val backgroundInsetStart = attributes.getDimensionPixelSize(
      R.styleable.MaterialAlertDialog_backgroundInsetStart,
      context
        .resources
        .getDimensionPixelSize(R.dimen.mtrl_alert_dialog_background_inset_start)
    )
    val backgroundInsetTop = attributes.getDimensionPixelSize(
      R.styleable.MaterialAlertDialog_backgroundInsetTop,
      context
        .resources
        .getDimensionPixelSize(R.dimen.mtrl_alert_dialog_background_inset_top)
    )
    val backgroundInsetEnd = attributes.getDimensionPixelSize(
      R.styleable.MaterialAlertDialog_backgroundInsetEnd,
      context
        .resources
        .getDimensionPixelSize(R.dimen.mtrl_alert_dialog_background_inset_end)
    )
    val backgroundInsetBottom = attributes.getDimensionPixelSize(
      R.styleable.MaterialAlertDialog_backgroundInsetBottom,
      context
        .resources
        .getDimensionPixelSize(R.dimen.mtrl_alert_dialog_background_inset_bottom)
    )
    attributes.recycle()
    var backgroundInsetLeft = backgroundInsetStart
    var backgroundInsetRight = backgroundInsetEnd
    val layoutDirection = context.resources.configuration.layoutDirection
    if (layoutDirection == ViewCompat.LAYOUT_DIRECTION_RTL) {
      backgroundInsetLeft = backgroundInsetEnd
      backgroundInsetRight = backgroundInsetStart
    }
    return Rect(
      backgroundInsetLeft,
      backgroundInsetTop,
      backgroundInsetRight,
      backgroundInsetBottom
    )
  }
}
