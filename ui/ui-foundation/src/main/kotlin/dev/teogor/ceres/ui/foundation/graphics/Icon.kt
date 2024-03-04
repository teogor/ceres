/*
 * Copyright 2023 teogor (Teodor Grigor)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dev.teogor.ceres.ui.foundation.graphics

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * A sealed class to make dealing with [ImageVector] and [DrawableRes] icons easier.
 */
sealed class Icon {
  data class ImageVectorIcon internal constructor(
    val imageVector: ImageVector,
  ) : Icon()

  data class DrawableResourceIcon internal constructor(
    @DrawableRes val id: Int,
  ) : Icon()
}

/**
 * Extension function to create an Icon using ImageVector.
 *
 * Example usage:
 * ```kotlin
 * val imageVectorIcon = Icons.Filled.Wallpaper.asImageVectorIcon()
 * ```
 *
 * @receiver The ImageVector you want to use as an Icon.
 * @return An ImageVectorIcon.
 */
fun ImageVector.asImageVectorIcon(): Icon.ImageVectorIcon {
  return Icon.ImageVectorIcon(this)
}

/**
 * Extension function to create an Icon using a DrawableRes.
 *
 * Example usage:
 * ```kotlin
 * val drawableResIcon = R.drawable.ceres_logo.asDrawableResourceIcon()
 * ```
 *
 * @receiver The DrawableRes identifier you want to use as an Icon.
 * @return A DrawableResourceIcon.
 */
fun Int.asDrawableResourceIcon(): Icon.DrawableResourceIcon {
  return Icon.DrawableResourceIcon(this)
}
