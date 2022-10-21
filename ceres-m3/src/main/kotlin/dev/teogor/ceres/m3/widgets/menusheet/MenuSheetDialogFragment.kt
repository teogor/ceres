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

package dev.teogor.ceres.m3.widgets.menusheet

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.Dimension
import androidx.annotation.FloatRange
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatDialogFragment
import dev.teogor.ceres.core.internal.WindowPreferencesManager

/**
 * Modal bottom sheet. This is a version of [androidx.fragment.app.DialogFragment] that shows
 * a bottom sheet using [MenuSheetDialog] instead of a floating dialog.
 */
@Suppress("unused")
open class MenuSheetDialogFragment : AppCompatDialogFragment {
  /**
   * Tracks if we are waiting for a dismissAllowingStateLoss or a regular dismiss once the
   * BottomSheet is hidden and onStateChanged() is called.
   */
  private var waitingForDismissAllowingStateLoss = false

  constructor()

  @SuppressLint("ValidFragment")
  constructor(@LayoutRes contentLayoutId: Int) : super(contentLayoutId)

  override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
    return dev.teogor.ceres.m3.widgets.menusheet.MenuSheetDialog(requireContext(), theme)
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    menuSheetDialog.setContentView(layoutRes)
    return inflater.inflate(
      layoutRes,
      container,
      false
    )
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    WindowPreferencesManager().applyEdgeToEdgePreference(
      dialog?.window!!
    )
    menuSheetDialog.setTopCornersSize(topCornersSize)
    menuSheetDialog.setHorizontalMargin(horizontalMargin)
    behaviour.isFitToContents = isFitToContent
    behaviour.setHalfExpandedRatio(halfExpandedRatio)

    onViewCreated(savedInstanceState)
  }

  override fun dismiss() {
    if (!tryDismissWithAnimation(false)) {
      super.dismiss()
    }
  }

  override fun dismissAllowingStateLoss() {
    if (!tryDismissWithAnimation(true)) {
      super.dismissAllowingStateLoss()
    }
  }

  /**
   * Tries to dismiss the dialog fragment with the bottom sheet animation. Returns true if possible,
   * false otherwise.
   */
  private fun tryDismissWithAnimation(allowingStateLoss: Boolean): Boolean {
    val baseDialog = dialog
    if (baseDialog is dev.teogor.ceres.m3.widgets.menusheet.MenuSheetDialog) {
      val behavior: dev.teogor.ceres.m3.widgets.menusheet.MenuSheetBehaviour<*> = baseDialog.behavior
      if (behavior.isHideable && baseDialog.getDismissWithAnimation()) {
        dismissWithAnimation(behavior, allowingStateLoss)
        return true
      }
    }
    return false
  }

  private fun dismissWithAnimation(
    behavior: dev.teogor.ceres.m3.widgets.menusheet.MenuSheetBehaviour<*>,
    allowingStateLoss: Boolean
  ) {
    waitingForDismissAllowingStateLoss = allowingStateLoss
    if (behavior.getState() == dev.teogor.ceres.m3.widgets.menusheet.MenuSheetBehaviour.STATE_HIDDEN) {
      dismissAfterAnimation()
    } else {
      if (dialog is dev.teogor.ceres.m3.widgets.menusheet.MenuSheetDialog) {
        (dialog as dev.teogor.ceres.m3.widgets.menusheet.MenuSheetDialog?)!!.removeDefaultCallback()
      }
      behavior.addBottomSheetCallback(MenuSheetDismissCallback())
      behavior.setState(dev.teogor.ceres.m3.widgets.menusheet.MenuSheetBehaviour.STATE_HIDDEN)
    }
  }

  private fun dismissAfterAnimation() {
    if (waitingForDismissAllowingStateLoss) {
      super.dismissAllowingStateLoss()
    } else {
      super.dismiss()
    }
  }

  private inner class MenuSheetDismissCallback : dev.teogor.ceres.m3.widgets.menusheet.MenuSheetBehaviour.MenuSheetCallback() {
    override fun onStateChanged(bottomSheet: View, newState: Int) {
      if (newState == dev.teogor.ceres.m3.widgets.menusheet.MenuSheetBehaviour.STATE_HIDDEN) {
        dismissAfterAnimation()
      }
    }

    override fun onSlide(bottomSheet: View, slideOffset: Float) {}
  }

  open fun onViewCreated(savedInstanceState: Bundle?) {
  }

  val menuSheetDialog: dev.teogor.ceres.m3.widgets.menusheet.MenuSheetDialog
    get() = dialog as dev.teogor.ceres.m3.widgets.menusheet.MenuSheetDialog

  val behaviour: dev.teogor.ceres.m3.widgets.menusheet.MenuSheetBehaviour<FrameLayout>
    get() = menuSheetDialog.behavior

  @get:Dimension
  open val topCornersSize: Float
    get() = 0f

  @get:Dimension
  open val horizontalMargin: Float
    get() = 0f

  @get:LayoutRes
  open val layoutRes: Int
    get() = -1

  open val isFitToContent: Boolean
    get() = true

  @get:FloatRange(from = 0.0, to = 1.0, fromInclusive = false, toInclusive = false)
  open val halfExpandedRatio: Float
    get() = 0.5f
}
