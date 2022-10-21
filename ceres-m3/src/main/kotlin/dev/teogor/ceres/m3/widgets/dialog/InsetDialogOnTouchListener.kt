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

import android.app.Dialog
import android.graphics.Rect
import android.graphics.RectF
import android.os.Build
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import androidx.annotation.RestrictTo

/**
 * Ensures that touches within the transparent region of the inset drawable used for Dialogs are
 * processed as touches outside the Dialog.
 *
 * @hide
 */
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
class InsetDialogOnTouchListener(private val dialog: Dialog, insets: Rect) : View.OnTouchListener {
  private val leftInset: Int
  private val topInset: Int
  private val prePieSlop: Int

  init {
    leftInset = insets.left
    topInset = insets.top
    prePieSlop = ViewConfiguration.get(dialog.context).scaledWindowTouchSlop
  }

  override fun onTouch(view: View, event: MotionEvent): Boolean {
    val insetView = view.findViewById<View>(android.R.id.content)
    val insetLeft = leftInset + insetView.left
    val insetRight = insetLeft + insetView.width
    val insetTop = topInset + insetView.top
    val insetBottom = insetTop + insetView.height
    val dialogWindow = RectF(
      insetLeft.toFloat(),
      insetTop.toFloat(),
      insetRight.toFloat(),
      insetBottom.toFloat()
    )
    if (dialogWindow.contains(event.x, event.y)) {
      return false
    }
    val outsideEvent = MotionEvent.obtain(event)
    if (event.action == MotionEvent.ACTION_UP) {
      outsideEvent.action = MotionEvent.ACTION_OUTSIDE
    }
    // Window.shouldCloseOnTouch does not respect MotionEvent.ACTION_OUTSIDE until Pie, so we fix
    // the coordinates outside the view and use MotionEvent.ACTION_DOWN
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.P) {
      outsideEvent.action = MotionEvent.ACTION_DOWN
      outsideEvent.setLocation((-prePieSlop - 1).toFloat(), (-prePieSlop - 1).toFloat())
    }
    view.performClick()
    return dialog.onTouchEvent(outsideEvent)
  }
}
