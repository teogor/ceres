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

@file:Suppress("unused")

package dev.teogor.ceres.m3.drawable

import androidx.annotation.Dimension
import com.google.android.material.shape.MaterialShapeDrawable

fun MaterialShapeDrawable.toBuilder(): ShapeDrawableM3.Builder {
  return ShapeDrawableM3.Builder(this)
}

fun ShapeDrawableM3.toBuilder(): ShapeDrawableM3.Builder {
  return ShapeDrawableM3.Builder(this)
}

fun RippleDrawableM3.toBuilder(): RippleDrawableM3.Builder {
  return RippleDrawableM3.Builder(this.builder)
}

fun MaterialShapeDrawable.topCorners(@Dimension size: Float): MaterialShapeDrawable {
  apply {
    val sam = shapeAppearanceModel
    sam.toBuilder()
      .setTopLeftCornerSize(size)
      .setTopRightCornerSize(size)
      .build()
    shapeAppearanceModel = sam
  }
  return this
}

fun MaterialShapeDrawable.bottomCorners(@Dimension size: Float): MaterialShapeDrawable {
  apply {
    val sam = shapeAppearanceModel
    sam.toBuilder()
      .setBottomLeftCornerSize(size)
      .setBottomRightCornerSize(size)
      .build()
    shapeAppearanceModel = sam
  }
  return this
}

fun MaterialShapeDrawable.leftCorners(@Dimension size: Float): MaterialShapeDrawable {
  apply {
    val sam = shapeAppearanceModel
    sam.toBuilder()
      .setTopLeftCornerSize(size)
      .setBottomLeftCornerSize(size)
      .build()
    shapeAppearanceModel = sam
  }
  return this
}

fun MaterialShapeDrawable.rightCorners(@Dimension size: Float): MaterialShapeDrawable {
  apply {
    val sam = shapeAppearanceModel
    sam.toBuilder()
      .setTopRightCornerSize(size)
      .setBottomRightCornerSize(size)
      .build()
    shapeAppearanceModel = sam
  }
  return this
}
