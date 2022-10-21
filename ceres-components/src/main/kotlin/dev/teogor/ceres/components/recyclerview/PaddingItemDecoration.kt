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

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class PaddingItemDecoration(
  private val startPaddingInPx: Int = 0,
  private val betweenCellsPaddingInPx: Int = 0,
  private val endPaddingInPx: Int = 0
) : RecyclerView.ItemDecoration() {

  override fun getItemOffsets(
    outRect: Rect,
    view: View,
    parent: RecyclerView,
    state: RecyclerView.State
  ) {
    when {
      view.isFirstItem(parent) -> {
        outRect.set(startPaddingInPx, 0, betweenCellsPaddingInPx, 0)
      }
      view.isLastItem(parent, state) -> {
        outRect.set(0, 0, endPaddingInPx, 0)
      }
      else -> {
        outRect.set(0, 0, betweenCellsPaddingInPx, 0)
      }
    }
  }

  private fun View.isFirstItem(recyclerView: RecyclerView) =
    recyclerView.getChildAdapterPosition(this) == 0

  private fun View.isLastItem(recyclerView: RecyclerView, state: RecyclerView.State) =
    recyclerView.getChildAdapterPosition(this) == state.itemCount - 1
}
