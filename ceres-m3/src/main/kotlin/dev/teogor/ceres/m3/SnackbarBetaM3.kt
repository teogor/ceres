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

import android.app.Activity
import android.view.View
import com.google.android.material.snackbar.BaseTransientBottomBar
import dev.teogor.ceres.core.global.GlobalData
import dev.teogor.ceres.extensions.colorStateList
import dev.teogor.ceres.m3.snackbar.Snackbar
import dev.teogor.ceres.m3.theme.IThemeM3
import dev.teogor.ceres.m3.theme.SurfaceOverlay

class SnackbarBetaM3 private constructor(
  message: String,
  action: String,
  listener: View.OnClickListener?,
  duration: Int,
  anchor: View?
) : IThemeM3 {

  private val snackbar: Snackbar

  init {
    snackbar = buildSnackbar(
      message = message,
      action = action,
      actionListener = listener,
      duration = duration
    )
    anchor?.let {
      snackbar.anchorView = anchor
    }
  }

  fun show() {
    snackbar.show()
  }

  private fun buildSnackbar(
    activity: Activity = GlobalData.activity,
    message: String,
    action: String? = null,
    actionListener: View.OnClickListener? = null,
    duration: Int = Snackbar.LENGTH_SHORT
  ): Snackbar {
    val snackBar = Snackbar.make(
      activity.findViewById(android.R.id.content),
      message,
      duration
    )
    if (action != null && actionListener != null) {
      snackBar.setAction(action, actionListener)
    }
    snackBar.setBackgroundTintList(
      SurfaceOverlay(
        themeOverlayColor = getColorM3(ColorM3.Secondary),
        themeSurfaceColor = getColorM3(ColorM3.Surface)
      ).forAlpha(.14f).colorStateList
    )
    return snackBar
  }

  /** Builder to create instances of [SnackbarBetaM3.Builder]s.  */
  data class Builder(
    private var message: String = "",
    private var action: String = "",
    private var listener: View.OnClickListener? = null,
    private var duration: Int = Snackbar.LENGTH_SHORT,
    private var anchor: View? = null
  ) {

    fun message(content: String) = apply { this.message = content }

    fun action(
      content: String,
      listener: View.OnClickListener?
    ) = apply {
      this.action = content
      this.listener = listener
    }

    fun duration(@BaseTransientBottomBar.Duration value: Int) = apply { this.duration = value }

    fun anchor(view: View?) = apply { this.anchor = view }

    fun build(): SnackbarBetaM3 {
      return prepareSnackbar()
    }

    fun buildAndShow(): SnackbarBetaM3 {
      val snackbar = prepareSnackbar()
      snackbar.show()
      return snackbar
    }

    private fun prepareSnackbar() = SnackbarBetaM3(
      message,
      action,
      listener,
      duration,
      anchor
    )
  }

  companion object {
    /** Returns a builder for [SnackbarBetaM3.Builder]  */
    fun toBuilder(): Builder {
      return Builder()
    }
  }
}
