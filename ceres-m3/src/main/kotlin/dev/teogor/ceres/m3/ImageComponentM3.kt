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
import androidx.annotation.Dimension
import androidx.annotation.DrawableRes
import androidx.annotation.FontRes
import androidx.annotation.Px
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.setPadding
import dev.teogor.ceres.extensions.dpToPx
import dev.teogor.ceres.extensions.hide
import dev.teogor.ceres.extensions.show
import dev.teogor.ceres.extensions.validDrawableId
import dev.teogor.ceres.extensions.validFontId
import dev.teogor.ceres.m3.databinding.Lm3ImageComponentBinding

class ImageComponentM3(
  context: Context,
  attrs: AttributeSet
) : ContainerBaseM3(context, attrs) {

  private val binding: Lm3ImageComponentBinding

  @DrawableRes
  private val imageResId: Int

  @FontRes
  private val fontId: Int

  @Px
  @Dimension
  private val paddingImage: Int

  var onClickedChangeListener: OnClickedChangeListener? = null

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

  init {
    // layout binding
    binding = Lm3ImageComponentBinding.inflate(
      LayoutInflater.from(context),
      this,
      true
    )

    // attrs initialization
    context.theme.obtainStyledAttributes(
      attrs,
      R.styleable.ImageComponentM3,
      0,
      0
    ).apply {
      try {
        fontId = getResourceId(R.styleable.ImageComponentM3_android_fontFamily, -1)
        title = getString(R.styleable.ImageComponentM3_title)
        subtitle = getString(R.styleable.ImageComponentM3_subtitle)
        imageResId = getResourceId(R.styleable.ImageComponentM3_src_image, -1)
        paddingImage = getDimension(
          R.styleable.ImageComponentM3_padding_image,
          10.dpToPx.toFloat()
        ).toInt()
      } finally {
        recycle()
      }
    }

    // font family initialization
    if (fontId.validFontId()) {
      val typeface = ResourcesCompat.getFont(context, fontId)
      binding.textTitle.typeface = typeface
      binding.textSubtitle.typeface = typeface
    }

    setOnClickListener {
      onClicked()
    }

    binding.imageM3.show(imageResId.validDrawableId())
    if (imageResId.validDrawableId()) {
      binding.imageM3.setImageResource(imageResId)
    }
    binding.imageM3.setPadding(paddingImage)

    binding.textSubtitle.alpha = .85f
  }

  //region Public Methods :: API Methods

  //endregion

  private fun onClicked() {
    onClickedChangeListener?.onClicked()
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
