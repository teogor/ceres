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

package dev.teogor.ceres.core.internal

import android.content.Context
import android.graphics.PorterDuff
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.view.View.OnAttachStateChangeListener
import android.view.ViewGroup
import android.view.ViewOverlay
import android.view.ViewTreeObserver
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.view.inputmethod.InputMethodManager
import androidx.annotation.Dimension
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.R

/**
 * Utils class for custom views.
 */
object ViewUtils {
  const val EDGE_TO_EDGE_FLAGS =
    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_LAYOUT_STABLE

  @JvmOverloads
  fun showKeyboard(
    view: View,
    useWindowInsetsController: Boolean = true
  ) {
    if (useWindowInsetsController) {
      val windowController = ViewCompat.getWindowInsetsController(view)
      if (windowController != null) {
        windowController.show(WindowInsetsCompat.Type.ime())
        return
      }
    }
    getInputMethodManager(view)!!
      .showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
  }

  @JvmOverloads
  fun hideKeyboard(
    view: View,
    useWindowInsetsController: Boolean = true
  ) {
    if (useWindowInsetsController) {
      val windowController = ViewCompat.getWindowInsetsController(view)
      if (windowController != null) {
        windowController.hide(WindowInsetsCompat.Type.ime())
        return
      }
    }
    val imm = getInputMethodManager(view)
    imm?.hideSoftInputFromWindow(view.windowToken, 0)
  }

  private fun getInputMethodManager(view: View): InputMethodManager? {
    return ContextCompat.getSystemService(view.context, InputMethodManager::class.java)
  }

  fun setBoundsFromRect(view: View, rect: Rect) {
    view.left = rect.left
    view.top = rect.top
    view.right = rect.right
    view.bottom = rect.bottom
  }

  @JvmOverloads
  fun calculateRectFromBounds(view: View, offsetY: Int = 0): Rect {
    return Rect(
      view.left,
      view.top + offsetY,
      view.right,
      view.bottom + offsetY
    )
  }

  fun getChildren(view: View?): List<View> {
    val children: MutableList<View> = ArrayList()
    if (view is ViewGroup) {
      val viewGroup = view
      for (i in 0 until viewGroup.childCount) {
        children.add(viewGroup.getChildAt(i))
      }
    }
    return children
  }

  fun parseTintMode(value: Int, defaultMode: PorterDuff.Mode): PorterDuff.Mode {
    return when (value) {
      3 -> PorterDuff.Mode.SRC_OVER
      5 -> PorterDuff.Mode.SRC_IN
      9 -> PorterDuff.Mode.SRC_ATOP
      14 -> PorterDuff.Mode.MULTIPLY
      15 -> PorterDuff.Mode.SCREEN
      16 -> PorterDuff.Mode.ADD
      else -> defaultMode
    }
  }

  fun isLayoutRtl(view: View?): Boolean {
    return ViewCompat.getLayoutDirection(view!!) == ViewCompat.LAYOUT_DIRECTION_RTL
  }

  fun dpToPx(context: Context, @Dimension(unit = Dimension.DP) dp: Int): Float {
    val r = context.resources
    return TypedValue.applyDimension(
      TypedValue.COMPLEX_UNIT_DIP,
      dp.toFloat(),
      r.displayMetrics
    )
  }

  fun requestFocusAndShowKeyboard(view: View) {
    view.requestFocus()
    view.post {
      val inputMethodManager =
        view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
      inputMethodManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
    }
  }
  /**
   * Wrapper around [androidx.core.view.OnApplyWindowInsetsListener] that can automatically
   * apply inset padding based on view attributes.
   */
  /**
   * Wrapper around [androidx.core.view.OnApplyWindowInsetsListener] that can automatically
   * apply inset padding based on view attributes.
   */
  fun doOnApplyWindowInsets(
    view: View,
    attrs: AttributeSet?,
    defStyleAttr: Int,
    defStyleRes: Int,
    listener: OnApplyWindowInsetsListener? = null
  ) {
    val a = view.context
      .obtainStyledAttributes(attrs, R.styleable.Insets, defStyleAttr, defStyleRes)
    val paddingBottomSystemWindowInsets =
      a.getBoolean(R.styleable.Insets_paddingBottomSystemWindowInsets, false)
    val paddingLeftSystemWindowInsets =
      a.getBoolean(R.styleable.Insets_paddingLeftSystemWindowInsets, false)
    val paddingRightSystemWindowInsets =
      a.getBoolean(R.styleable.Insets_paddingRightSystemWindowInsets, false)
    a.recycle()
    doOnApplyWindowInsets(
      view,
      object : OnApplyWindowInsetsListener {
        override fun onApplyWindowInsets(
          view: View?,
          insets: WindowInsetsCompat,
          initialPadding: RelativePadding
        ): WindowInsetsCompat {
          if (paddingBottomSystemWindowInsets) {
            initialPadding.bottom += insets.systemWindowInsetBottom
          }
          val isRtl = isLayoutRtl(view)
          if (paddingLeftSystemWindowInsets) {
            if (isRtl) {
              initialPadding.end += insets.systemWindowInsetLeft
            } else {
              initialPadding.start += insets.systemWindowInsetLeft
            }
          }
          if (paddingRightSystemWindowInsets) {
            if (isRtl) {
              initialPadding.start += insets.systemWindowInsetRight
            } else {
              initialPadding.end += insets.systemWindowInsetRight
            }
          }
          initialPadding.applyToView(view)
          return listener?.onApplyWindowInsets(view, insets, initialPadding) ?: insets
        }
      }
    )
  }

  /**
   * Wrapper around [androidx.core.view.OnApplyWindowInsetsListener] that records the initial
   * padding of the view and requests that insets are applied when attached.
   */
  fun doOnApplyWindowInsets(
    view: View,
    listener: OnApplyWindowInsetsListener
  ) {
    // Create a snapshot of the view's padding state.
    val initialPadding = RelativePadding(
      ViewCompat.getPaddingStart(view),
      view.paddingTop,
      ViewCompat.getPaddingEnd(view),
      view.paddingBottom
    )
    // Set an actual OnApplyWindowInsetsListener which proxies to the given callback, also passing
    // in the original padding state.
    ViewCompat.setOnApplyWindowInsetsListener(
      view
    ) { view, insets ->
      listener.onApplyWindowInsets(
        view,
        insets,
        RelativePadding(initialPadding)
      )
    }
    // Request some insets.
    requestApplyInsetsWhenAttached(view)
  }

  /**
   * Requests that insets should be applied to this view once it is attached.
   */
  fun requestApplyInsetsWhenAttached(view: View) {
    if (ViewCompat.isAttachedToWindow(view)) {
      // We're already attached, just request as normal.
      ViewCompat.requestApplyInsets(view)
    } else {
      // We're not attached to the hierarchy, add a listener to request when we are.
      view.addOnAttachStateChangeListener(
        object : OnAttachStateChangeListener {
          override fun onViewAttachedToWindow(v: View) {
            v.removeOnAttachStateChangeListener(this)
            ViewCompat.requestApplyInsets(v)
          }

          override fun onViewDetachedFromWindow(v: View) {}
        }
      )
    }
  }

  /**
   * Returns the absolute elevation of the parent of the provided `view`, or in other words,
   * the sum of the elevations of all ancestors of the `view`.
   */
  fun getParentAbsoluteElevation(view: View): Float {
    var absoluteElevation = 0f
    var viewParent = view.parent
    while (viewParent is View) {
      absoluteElevation += ViewCompat.getElevation((viewParent as View))
      viewParent = viewParent.getParent()
    }
    return absoluteElevation
  }

  /**
   * Backward-compatible [View.getOverlay]. TODO(b/144937975): Remove and use the official
   * version from androidx when it's available.
   */
  fun getOverlay(view: View?): ViewOverlay? {
    return view?.overlay
  }

  /**
   * Returns the content view that is the parent of the provided view.
   */
  fun getContentView(view: View?): ViewGroup? {
    if (view == null) {
      return null
    }
    val rootView = view.rootView
    val contentView = rootView.findViewById<ViewGroup>(android.R.id.content)
    if (contentView != null) {
      return contentView
    }

    // Account for edge cases: Parent's parent can be null without ever having found
    // android.R.id.content (e.g. if view is in an overlay during a transition).
    // Additionally, sometimes parent's parent is neither a ViewGroup nor a View (e.g. if view
    // is in a PopupWindow).
    return if (rootView !== view && rootView is ViewGroup) {
      rootView
    } else null
  }

  /**
   * Returns the content view overlay that can be used to add drawables on top of all other views.
   */
  fun getContentViewOverlay(view: View): ViewOverlay? {
    return getOverlay(getContentView(view))
  }

  fun addOnGlobalLayoutListener(
    view: View?,
    victim: OnGlobalLayoutListener
  ) {
    view?.viewTreeObserver?.addOnGlobalLayoutListener(victim)
  }

  fun removeOnGlobalLayoutListener(
    view: View?,
    victim: OnGlobalLayoutListener
  ) {
    if (view != null) {
      removeOnGlobalLayoutListener(view.viewTreeObserver, victim)
    }
  }

  fun removeOnGlobalLayoutListener(
    viewTreeObserver: ViewTreeObserver,
    victim: OnGlobalLayoutListener
  ) {
    viewTreeObserver.removeOnGlobalLayoutListener(victim)
  }

  /**
   * Returns the provided view's background color, if it has ColorDrawable as its background, or
   * `null` if the background has a different drawable type.
   */
  fun getBackgroundColor(view: View): Int? {
    return if (view.background is ColorDrawable) (view.background as ColorDrawable).color else null
  }

  /**
   * Wrapper around [androidx.core.view.OnApplyWindowInsetsListener] which also passes the
   * initial padding set on the view. Used with [.doOnApplyWindowInsets].
   */
  interface OnApplyWindowInsetsListener {
    /**
     * When [set][View.setOnApplyWindowInsetsListener] on a
     * View, this listener method will be called instead of the view's own [ ][View.onApplyWindowInsets] method. The `initialPadding` is the view's
     * original padding which can be updated and will be applied to the view automatically. This
     * method should return a new [WindowInsetsCompat] with any insets consumed.
     */
    fun onApplyWindowInsets(
      view: View?,
      insets: WindowInsetsCompat,
      initialPadding: RelativePadding
    ): WindowInsetsCompat
  }

  /**
   * Simple data object to store the initial padding for a view.
   */
  class RelativePadding {
    var start: Int
    var top: Int
    var end: Int
    var bottom: Int

    constructor(start: Int, top: Int, end: Int, bottom: Int) {
      this.start = start
      this.top = top
      this.end = end
      this.bottom = bottom
    }

    constructor(other: RelativePadding) {
      start = other.start
      top = other.top
      end = other.end
      bottom = other.bottom
    }

    /**
     * Applies this relative padding to the view.
     */
    fun applyToView(view: View?) {
      ViewCompat.setPaddingRelative(view!!, start, top, end, bottom)
    }
  }
}
