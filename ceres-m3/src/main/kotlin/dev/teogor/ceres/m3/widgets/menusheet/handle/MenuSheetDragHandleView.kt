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

package dev.teogor.ceres.m3.widgets.menusheet.handle

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityManager
import androidx.appcompat.widget.AppCompatImageView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.AccessibilityDelegateCompat
import androidx.core.view.ViewCompat
import androidx.core.view.accessibility.AccessibilityEventCompat
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityActionCompat
import androidx.core.view.accessibility.AccessibilityViewCommand.CommandArguments
import com.google.android.material.R
import com.google.android.material.theme.overlay.MaterialThemeOverlay
import dev.teogor.ceres.m3.widgets.menusheet.MenuSheetBehaviour
import dev.teogor.ceres.m3.widgets.menusheet.MenuSheetBehaviour.MenuSheetCallback

/**
 * A drag handle view that can be added to bottom sheets associated with [ ]. This view will automatically handle the accessibility interaction when the
 * accessibility service is enabled. When you add a drag handle to a bottom sheet and the user
 * enables the accessibility service, the drag handle will become important for accessibility and
 * clickable. Clicking the drag handle will toggle the bottom sheet between its collapsed and
 * expanded states.
 */
class MenuSheetDragHandleView @JvmOverloads constructor(
  var wrapContext: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = R.attr.bottomSheetDragHandleStyle
) : AppCompatImageView(
  MaterialThemeOverlay.wrap(wrapContext, attrs, defStyleAttr, DEF_STYLE_RES),
  attrs,
  defStyleAttr
),
  AccessibilityManager.AccessibilityStateChangeListener {
  private val accessibilityManager: AccessibilityManager?
  private var bottomSheetBehavior: dev.teogor.ceres.m3.widgets.menusheet.MenuSheetBehaviour<*>? = null
  private var accessibilityServiceEnabled = false
  private var interactable = false
  private var clickToExpand = false
  private val clickToExpandActionLabel = resources.getString(R.string.bottomsheet_action_expand)
  private val clickToCollapseActionLabel =
    resources.getString(R.string.bottomsheet_action_collapse)
  private val clickFeedback = resources.getString(R.string.bottomsheet_drag_handle_clicked)
  private val menuSheetCallback: MenuSheetCallback = object : MenuSheetCallback() {
    override fun onStateChanged(
      bottomSheet: View,
      @dev.teogor.ceres.m3.widgets.menusheet.MenuSheetBehaviour.State newState: Int
    ) {
      onBottomSheetStateChanged(newState)
    }

    override fun onSlide(bottomSheet: View, slideOffset: Float) {
      // todo animation
    }
  }

  init {
    // Override the provided context with the wrapped one to prevent it from being used.
    wrapContext = context
    accessibilityManager =
      wrapContext.getSystemService(Context.ACCESSIBILITY_SERVICE) as AccessibilityManager
    updateInteractableState()
    ViewCompat.setAccessibilityDelegate(
      this,
      object : AccessibilityDelegateCompat() {
        override fun onPopulateAccessibilityEvent(host: View, event: AccessibilityEvent) {
          super.onPopulateAccessibilityEvent(host, event)
          if (event.eventType == AccessibilityEvent.TYPE_VIEW_CLICKED) {
            expandOrCollapseBottomSheetIfPossible()
          }
        }
      }
    )
  }

  override fun onAttachedToWindow() {
    super.onAttachedToWindow()
    setBottomSheetBehavior(findParentBottomSheetBehavior())
    if (accessibilityManager != null) {
      accessibilityManager.addAccessibilityStateChangeListener(this)
      onAccessibilityStateChanged(accessibilityManager.isEnabled)
    }
  }

  override fun onDetachedFromWindow() {
    accessibilityManager?.removeAccessibilityStateChangeListener(this)
    setBottomSheetBehavior(null)
    super.onDetachedFromWindow()
  }

  override fun onAccessibilityStateChanged(enabled: Boolean) {
    accessibilityServiceEnabled = enabled
    updateInteractableState()
  }

  private fun setBottomSheetBehavior(behavior: dev.teogor.ceres.m3.widgets.menusheet.MenuSheetBehaviour<*>?) {
    if (bottomSheetBehavior != null) {
      bottomSheetBehavior!!.removeBottomSheetCallback(menuSheetCallback)
    }
    bottomSheetBehavior = behavior
    if (bottomSheetBehavior != null) {
      onBottomSheetStateChanged(bottomSheetBehavior!!.state)
      bottomSheetBehavior!!.addBottomSheetCallback(menuSheetCallback)
    }
    updateInteractableState()
  }

  private fun onBottomSheetStateChanged(@dev.teogor.ceres.m3.widgets.menusheet.MenuSheetBehaviour.State state: Int) {
    if (state == dev.teogor.ceres.m3.widgets.menusheet.MenuSheetBehaviour.STATE_COLLAPSED) {
      clickToExpand = true
    } else if (state == dev.teogor.ceres.m3.widgets.menusheet.MenuSheetBehaviour.STATE_EXPANDED) {
      clickToExpand = false
    } // Else keep the original settings
    ViewCompat.replaceAccessibilityAction(
      this,
      AccessibilityActionCompat.ACTION_CLICK,
      if (clickToExpand) clickToExpandActionLabel else clickToCollapseActionLabel
    ) { _: View?, _: CommandArguments? -> expandOrCollapseBottomSheetIfPossible() }
  }

  private fun updateInteractableState() {
    interactable = accessibilityServiceEnabled && bottomSheetBehavior != null
    ViewCompat.setImportantForAccessibility(
      this,
      if (bottomSheetBehavior != null) ViewCompat.IMPORTANT_FOR_ACCESSIBILITY_YES else ViewCompat.IMPORTANT_FOR_ACCESSIBILITY_NO
    )
    isClickable = interactable
  }

  /**
   * Expands or collapses the associated bottom sheet according to the current state and the
   * previous state when the drag handle is interactable, .
   *
   *
   * If the current state is COLLAPSED or EXPANDED and the bottom sheet can be half-expanded, it
   * will make the bottom sheet HALF_EXPANDED; if the bottom sheet cannot be half-expanded, it will
   * be EXPANDED (when it's COLLAPSED) or COLLAPSED (when it's EXPANDED) instead. On the other hand
   * when the bottom sheet is HALF_EXPANDED, it will make the bottom sheet either COLLAPSED (when
   * the previous state was EXPANDED) or EXPANDED (when the previous state was COLLAPSED.)
   */
  private fun expandOrCollapseBottomSheetIfPossible(): Boolean {
    if (!interactable) {
      return false
    }
    announceAccessibilityEvent(clickFeedback)
    val canHalfExpand = (
      !bottomSheetBehavior!!.isFitToContents &&
        !bottomSheetBehavior!!.shouldSkipHalfExpandedStateWhenDragging()
      )
    val currentState = bottomSheetBehavior!!.state
    val nextState: Int
    nextState = if (currentState == dev.teogor.ceres.m3.widgets.menusheet.MenuSheetBehaviour.STATE_COLLAPSED) {
      if (canHalfExpand) dev.teogor.ceres.m3.widgets.menusheet.MenuSheetBehaviour.STATE_HALF_EXPANDED else dev.teogor.ceres.m3.widgets.menusheet.MenuSheetBehaviour.STATE_EXPANDED
    } else if (currentState == dev.teogor.ceres.m3.widgets.menusheet.MenuSheetBehaviour.STATE_EXPANDED) {
      if (canHalfExpand) dev.teogor.ceres.m3.widgets.menusheet.MenuSheetBehaviour.STATE_HALF_EXPANDED else dev.teogor.ceres.m3.widgets.menusheet.MenuSheetBehaviour.STATE_COLLAPSED
    } else {
      if (clickToExpand) dev.teogor.ceres.m3.widgets.menusheet.MenuSheetBehaviour.STATE_EXPANDED else dev.teogor.ceres.m3.widgets.menusheet.MenuSheetBehaviour.STATE_COLLAPSED
    }
    bottomSheetBehavior!!.state = nextState
    return true
  }

  private fun announceAccessibilityEvent(announcement: String) {
    if (accessibilityManager == null) {
      return
    }
    val announce = AccessibilityEvent.obtain(AccessibilityEventCompat.TYPE_ANNOUNCEMENT)
    announce.text.add(announcement)
    accessibilityManager.sendAccessibilityEvent(announce)
  }

  /**
   * Finds the first ancestor associated with a [MenuSheetBehaviour]. If none is found,
   * returns `null`.
   */
  private fun findParentBottomSheetBehavior(): dev.teogor.ceres.m3.widgets.menusheet.MenuSheetBehaviour<*>? {
    var parent: View = this
    while (getParentView(parent).also { parent = it!! } != null) {
      val layoutParams = parent.layoutParams
      if (layoutParams is CoordinatorLayout.LayoutParams) {
        val behavior = layoutParams.behavior
        if (behavior is dev.teogor.ceres.m3.widgets.menusheet.MenuSheetBehaviour<*>) {
          return behavior
        }
      }
    }
    return null
  }

  companion object {
    private val DEF_STYLE_RES = R.style.Widget_Material3_BottomSheet_DragHandle
    private fun getParentView(view: View): View? {
      val parent = view.parent
      return if (parent is View) parent else null
    }
  }
}
