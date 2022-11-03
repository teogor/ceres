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

package dev.teogor.ceres.m3.snackbar

import android.animation.TimeInterpolator
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import androidx.annotation.RestrictTo
import androidx.core.view.ViewCompat
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import com.google.android.material.R
import com.google.android.material.color.MaterialColors
import com.google.android.material.motion.MotionUtils
import dev.teogor.ceres.m3.ButtonM3
import dev.teogor.ceres.m3.TextViewM3

/**
 * @hide
 */
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
class SnackbarContentLayout @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null
) : LinearLayout(context, attrs), ContentViewCallback {
  private val contentInterpolator: TimeInterpolator
  private var maxInlineActionWidth = 0

  lateinit var messageView: TextViewM3
    private set
  lateinit var actionView: ButtonM3
    private set

  init {
    contentInterpolator = MotionUtils.resolveThemeInterpolator(
      context,
      R.attr.motionEasingEmphasizedInterpolator,
      FastOutSlowInInterpolator() // AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR
    )
  }

  override fun onFinishInflate() {
    super.onFinishInflate()
    messageView = findViewById(R.id.snackbar_text)
    actionView = findViewById(R.id.snackbar_action)
  }

  fun updateActionTextColorAlphaIfNeeded(actionTextColorAlpha: Float) {
    if (actionTextColorAlpha != 1f) {
      val originalActionTextColor = actionView.currentTextColor
      val colorSurface = MaterialColors.getColor(this, R.attr.colorSurface)
      val actionTextColor = MaterialColors.layer(
        colorSurface,
        originalActionTextColor,
        actionTextColorAlpha
      )
      actionView.setTextColor(actionTextColor)
    }
  }

  override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    if (orientation == VERTICAL) {
      // The layout is by default HORIZONTAL. We only change it to VERTICAL when the action
      // view
      // is too wide and ellipsizes the message text. When the condition is met, we should
      // keep the
      // layout as VERTICAL.
      return
    }
    val multiLineVPadding = resources
      .getDimensionPixelSize(R.dimen.design_snackbar_padding_vertical_2lines)
    val singleLineVPadding =
      resources.getDimensionPixelSize(R.dimen.design_snackbar_padding_vertical)
    val messageLayout = messageView.layout
    val isMultiLine = messageLayout != null && messageLayout.lineCount > 1
    var remeasure = false
    if (isMultiLine &&
      maxInlineActionWidth > 0 && actionView.measuredWidth > maxInlineActionWidth
    ) {
      if (updateViewsWithinLayout(
          VERTICAL,
          multiLineVPadding,
          multiLineVPadding - singleLineVPadding
        )
      ) {
        remeasure = true
      }
    } else {
      val messagePadding = if (isMultiLine) multiLineVPadding else singleLineVPadding
      if (updateViewsWithinLayout(HORIZONTAL, messagePadding, messagePadding)) {
        remeasure = true
      }
    }
    if (remeasure) {
      super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }
  }

  private fun updateViewsWithinLayout(
    orientation: Int,
    messagePadTop: Int,
    messagePadBottom: Int
  ): Boolean {
    var changed = false
    if (orientation != getOrientation()) {
      setOrientation(orientation)
      changed = true
    }
    if (messageView.paddingTop != messagePadTop ||
      messageView.paddingBottom != messagePadBottom
    ) {
      updateTopBottomPadding(
        messageView,
        messagePadTop,
        messagePadBottom
      )
      changed = true
    }
    return changed
  }

  override fun animateContentIn(delay: Int, duration: Int) {
    messageView.alpha = 0f
    messageView
      .animate()
      .alpha(1f)
      .setDuration(duration.toLong())
      .setInterpolator(contentInterpolator)
      .setStartDelay(delay.toLong())
      .start()
    if (actionView.visibility == VISIBLE) {
      actionView.alpha = 0f
      actionView
        .animate()
        .alpha(1f)
        .setDuration(duration.toLong())
        .setInterpolator(contentInterpolator)
        .setStartDelay(delay.toLong())
        .start()
    }
  }

  override fun animateContentOut(delay: Int, duration: Int) {
    messageView.alpha = 1f
    messageView
      .animate()
      .alpha(0f)
      .setDuration(duration.toLong())
      .setInterpolator(contentInterpolator)
      .setStartDelay(delay.toLong())
      .start()
    if (actionView.visibility == VISIBLE) {
      actionView.alpha = 1f
      actionView
        .animate()
        .alpha(0f)
        .setDuration(duration.toLong())
        .setInterpolator(contentInterpolator)
        .setStartDelay(delay.toLong())
        .start()
    }
  }

  fun setMaxInlineActionWidth(width: Int) {
    maxInlineActionWidth = width
  }

  companion object {
    private fun updateTopBottomPadding(
      view: View,
      topPadding: Int,
      bottomPadding: Int
    ) {
      if (ViewCompat.isPaddingRelative(view)) {
        ViewCompat.setPaddingRelative(
          view,
          ViewCompat.getPaddingStart(view),
          topPadding,
          ViewCompat.getPaddingEnd(view),
          bottomPadding
        )
      } else {
        view.setPadding(
          view.paddingLeft,
          topPadding,
          view.paddingRight,
          bottomPadding
        )
      }
    }
  }
}
