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
import dev.teogor.ceres.extensions.defaultResId
import dev.teogor.ceres.extensions.dpToPx
import dev.teogor.ceres.extensions.hide
import dev.teogor.ceres.extensions.show
import dev.teogor.ceres.extensions.validDrawableRes
import dev.teogor.ceres.extensions.validFontRes
import dev.teogor.ceres.m3.databinding.Lm3ImageComponentBinding

open class ImageComponentM3(
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
    binding = bindLayout()

    // attrs initialization
    context.theme.obtainStyledAttributes(
      attrs,
      R.styleable.ImageComponentM3,
      0,
      0
    ).apply {
      try {
        fontId = getResourceId(R.styleable.ImageComponentM3_android_fontFamily, defaultResId)
        title = getString(R.styleable.ImageComponentM3_title)
        subtitle = getString(R.styleable.ImageComponentM3_subtitle)
        imageResId = getResourceId(R.styleable.ImageComponentM3_src_image, defaultResId)
        paddingImage = getDimension(
          R.styleable.ImageComponentM3_padding_image,
          10.dpToPx.toFloat()
        ).toInt()
      } finally {
        recycle()
      }
    }

    // font family initialization
    fontId.validFontRes {
      val typeface = ResourcesCompat.getFont(context, this)
      binding.textTitle.typeface = typeface
      binding.textSubtitle.typeface = typeface
    }

    initOnClickListener()

    binding.imageM3.show(imageResId.validDrawableRes)
    imageResId.validDrawableRes {
      binding.imageM3.setImageResource(this)
    }
    binding.imageM3.setPadding(paddingImage)

    binding.textSubtitle.alpha = .85f
  }

  private fun bindLayout() = Lm3ImageComponentBinding.inflate(
    LayoutInflater.from(context),
    this@ImageComponentM3,
    true
  )

  private fun initOnClickListener() {
    setOnClickListener {
      onClicked()
    }
  }

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
