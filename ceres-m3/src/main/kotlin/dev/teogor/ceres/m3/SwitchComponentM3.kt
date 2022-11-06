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
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.annotation.FontRes
import androidx.core.content.res.ResourcesCompat
import dev.teogor.ceres.extensions.hide
import dev.teogor.ceres.extensions.invalidResId
import dev.teogor.ceres.extensions.show
import dev.teogor.ceres.extensions.validFontRes
import dev.teogor.ceres.m3.databinding.Lm3SwitchComponentBinding

class SwitchComponentM3(
  context: Context,
  attrs: AttributeSet
) : ContainerBaseM3(context, attrs) {

  private val binding: Lm3SwitchComponentBinding

  private val hasSwitch: Boolean
  private val initialCallbackEnabled: Boolean

  @FontRes
  private val fontId: Int

  private var initializationCompleted = false

  var onCheckedChangeListener: OnCheckedChangeListener? = null
  var onClickedChangeListener: OnClickedChangeListener? = null

  var switchChecked: Boolean = false
    set(value) {
      field = value
      setSwitch()
    }
  var title: String? = null
    set(value) {
      field = value
      setTitle()
    }
  var subtitle: String? = null
    set(value) {
      field = value
      setSubtitle()
    }

  private fun setTitle() {
    binding.textTitle.show(title != null)
    binding.textTitle.text = title
  }

  private fun setSubtitle() {
    binding.textSubtitle.hide(subtitle.isNullOrEmpty())
    binding.textSubtitle.text = subtitle
  }

  private fun setSwitch() {
    binding.switchM3.isChecked = switchChecked
  }

  init {
    // layout binding
    binding = Lm3SwitchComponentBinding.inflate(
      LayoutInflater.from(context),
      this,
      true
    )

    // attrs initialization
    context.theme.obtainStyledAttributes(
      attrs,
      R.styleable.SwitchComponentM3,
      0,
      0
    ).apply {
      try {
        fontId = getResourceId(R.styleable.SwitchComponentM3_android_fontFamily, invalidResId)
        title = getString(R.styleable.SwitchComponentM3_title)
        subtitle = getString(R.styleable.SwitchComponentM3_subtitle)
        hasSwitch = getBoolean(R.styleable.SwitchComponentM3_has_switch, false)
        initialCallbackEnabled = getBoolean(
          R.styleable.SwitchComponentM3_initial_callback,
          false
        )
        switchChecked = getBoolean(R.styleable.SwitchComponentM3_checked, false)
      } finally {
        recycle()
      }
    }

    initializationCompleted = initialCallbackEnabled

    // font family initialization
    fontId.validFontRes {
      val typeface = ResourcesCompat.getFont(context, this)
      binding.textTitle.typeface = typeface
      binding.textSubtitle.typeface = typeface
    }

    // bind listeners
    binding.switchM3.setOnCheckedChangeListener { _, isChecked ->
      onCheckedToggled(isChecked)
    }
    setOnClickListener {
      onClicked()
    }

    binding.textSubtitle.alpha = .85f

    // switch initialization
    setSwitch()
    showSwitch(hasSwitch)
    initializationCompleted = true
  }

  //region Public Methods :: API Methods
  fun onChecked(isChecked: Boolean) {
    binding.switchM3.isChecked = isChecked
  }

  fun showSwitch(isVisible: Boolean) {
    binding.switchM3.show(isVisible)
  }
  //endregion

  private fun onClicked() {
    if (hasSwitch) {
      binding.switchM3.isChecked = !binding.switchM3.isChecked
    } else {
      onClickedChangeListener?.onClicked()
    }
  }

  private fun onCheckedToggled(isChecked: Boolean) {
    if (initializationCompleted) {
      onCheckedChangeListener?.onCheckedChanged(isChecked)
    }
  }

  /**
   * Interface definition for a callback to be invoked when the checked state
   * of a switch button changed.
   */
  interface OnCheckedChangeListener {
    /**
     * Called when the checked state of a compound button has changed.
     *
     * @param isChecked  The new checked state of buttonView.
     */
    fun onCheckedChanged(isChecked: Boolean)
  }

  /**
   * Interface definition for a callback to be invoked when the element was
   * clicked.
   */
  interface OnClickedChangeListener {
    /**
     * Called when the element was clicked.
     */
    fun onClicked()
  }
}
