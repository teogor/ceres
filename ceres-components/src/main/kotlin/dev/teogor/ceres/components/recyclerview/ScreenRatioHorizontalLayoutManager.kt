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

package dev.teogor.ceres.components.recyclerview

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dev.teogor.ceres.extensions.displayMetrics

class ScreenRatioHorizontalLayoutManager(
  context: Context,
  ratio: Ratio
) : LinearLayoutManager(context, HORIZONTAL, false) {

  private val itemRatioWidth = (context.displayMetrics.widthPixels * ratio.widthRatio).toInt()

  override fun generateLayoutParams(lp: ViewGroup.LayoutParams?): RecyclerView.LayoutParams {
    return calculateItemParams(super.generateLayoutParams(lp))
  }

  override fun generateLayoutParams(
    c: Context?,
    attrs: AttributeSet?
  ): RecyclerView.LayoutParams {
    return calculateItemParams(super.generateLayoutParams(c, attrs))
  }

  override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
    return calculateItemParams(super.generateDefaultLayoutParams())
  }

  private fun calculateItemParams(lp: RecyclerView.LayoutParams): RecyclerView.LayoutParams {
    lp.width = itemRatioWidth

    return lp
  }

  data class Ratio(val widthRatio: Float)
}
