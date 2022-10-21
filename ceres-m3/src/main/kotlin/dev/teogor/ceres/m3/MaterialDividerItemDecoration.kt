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
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.graphics.drawable.ShapeDrawable
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.Px
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.google.android.material.R
import com.google.android.material.internal.ThemeEnforcement
import com.google.android.material.resources.MaterialResources

/**
 * MaterialDividerItemDecoration is a [ItemDecoration], similar to a [ ], that can be used as a divider between items of
 * a [LinearLayoutManager]. It supports both [.HORIZONTAL] and [.VERTICAL]
 * orientations.
 *
 * <pre>
 * dividerItemDecoration = new MaterialDividerItemDecoration(recyclerView.getContext(),
 * layoutManager.getOrientation());
 * recyclerView.addItemDecoration(dividerItemDecoration);
</pre> *
 */
class MaterialDividerItemDecoration(
  context: Context,
  attrs: AttributeSet?,
  defStyleAttr: Int = R.attr.materialDividerStyle,
  orientation: Int
) : ItemDecoration() {
  private var dividerDrawable: Drawable
  /**
   * Returns the thickness set on the divider.
   *
   * @attr ref com.google.android.material.R.styleable#MaterialDivider_dividerThickness
   * @see .setDividerThickness
   */
  /**
   * Sets the thickness of the divider.
   *
   * @param thickness The thickness value to be set.
   * @attr ref com.google.android.material.R.styleable#MaterialDivider_dividerThickness
   * @see .getDividerThickness
   */
  @get:Px
  var dividerThickness: Int

  @ColorInt
  private var color: Int
  private var orientation = 0
  /**
   * Returns the divider's start inset.
   *
   * @attr ref com.google.android.material.R.styleable#MaterialDivider_dividerInsetStart
   * @see .setDividerInsetStart
   */
  /**
   * Sets the start inset of the divider.
   *
   * @param insetStart The start inset to be set.
   * @attr ref com.google.android.material.R.styleable#MaterialDivider_dividerInsetStart
   * @see .getDividerInsetStart
   */
  @get:Px
  var dividerInsetStart: Int
  /**
   * Returns the divider's end inset.
   *
   * @attr ref com.google.android.material.R.styleable#MaterialDivider_dividerInsetEnd
   * @see .setDividerInsetEnd
   */
  /**
   * Sets the end inset of the divider.
   *
   * @param insetEnd The end inset to be set.
   * @attr ref com.google.android.material.R.styleable#MaterialDivider_dividerInsetEnd
   * @see .getDividerInsetEnd
   */
  @get:Px
  var dividerInsetEnd: Int
  /**
   * Whether there's a divider after the last item of a [RecyclerView].
   *
   * @attr ref com.google.android.material.R.styleable#MaterialDivider_shouldDecorateLastItem
   * @see .setLastItemDecorated
   */
  /**
   * Sets whether the class should draw a divider after the last item of a [RecyclerView].
   *
   * @param lastItemDecorated whether there's a divider after the last item of a recycler view.
   * @attr ref com.google.android.material.R.styleable#MaterialDivider_lastItemDecorated
   * @see .isLastItemDecorated
   */
  var isLastItemDecorated: Boolean
  private val tempRect = Rect()

  /**
   * Returns the divider color.
   *
   * @attr ref com.google.android.material.R.styleable#MaterialDivider_dividerColor
   * @see .setDividerColor
   */
  /**
   * Sets the color of the divider.
   *
   * @param color The color to be set.
   * @attr ref com.google.android.material.R.styleable#MaterialDivider_dividerColor
   * @see .getDividerColor
   */
  @get:ColorInt
  var dividerColor: Int
    get() = color
    set(color) {
      this.color = color
      dividerDrawable = DrawableCompat.wrap(dividerDrawable)
      DrawableCompat.setTint(dividerDrawable, color)
    }

  init {
    val attributes = ThemeEnforcement.obtainStyledAttributes(
      context,
      attrs,
      R.styleable.MaterialDivider,
      defStyleAttr,
      DEF_STYLE_RES
    )
    color = MaterialResources.getColorStateList(
      context,
      attributes,
      R.styleable.MaterialDivider_dividerColor
    )!!.defaultColor

    dividerThickness = attributes.getDimensionPixelSize(
      R.styleable.MaterialDivider_dividerThickness,
      context.resources.getDimensionPixelSize(R.dimen.material_divider_thickness)
    )
    dividerInsetStart =
      attributes.getDimensionPixelOffset(R.styleable.MaterialDivider_dividerInsetStart, 0)
    dividerInsetEnd =
      attributes.getDimensionPixelOffset(R.styleable.MaterialDivider_dividerInsetEnd, 0)
    isLastItemDecorated =
      attributes.getBoolean(R.styleable.MaterialDivider_lastItemDecorated, true)
    attributes.recycle()
    dividerDrawable = ShapeDrawable()
    dividerColor = color
    setOrientation(orientation)
  }

  /**
   * Sets the orientation for this divider. This should be called if [ ] changes orientation.
   *
   *
   * A [.HORIZONTAL] orientation will draw a vertical divider, and a [.VERTICAL]
   * orientation a horizontal divider.
   *
   * @param orientation The orientation of the [RecyclerView] this divider is associated with:
   * [.HORIZONTAL] or [.VERTICAL]
   */
  fun setOrientation(orientation: Int) {
    require(!(orientation != HORIZONTAL && orientation != VERTICAL)) { "Invalid orientation: $orientation. It should be either HORIZONTAL or VERTICAL" }
    this.orientation = orientation
  }

  fun getOrientation(): Int {
    return orientation
  }

  /**
   * Sets the thickness of the divider.
   *
   * @param thicknessId The id of the thickness dimension resource to be set.
   * @attr ref com.google.android.material.R.styleable#MaterialDivider_dividerThickness
   * @see .getDividerThickness
   */
  fun setDividerThicknessResource(context: Context, @DimenRes thicknessId: Int) {
    dividerThickness = context.resources.getDimensionPixelSize(thicknessId)
  }

  /**
   * Sets the color of the divider.
   *
   * @param colorId The id of the color resource to be set.
   * @attr ref com.google.android.material.R.styleable#MaterialDivider_dividerColor
   * @see .getDividerColor
   */
  fun setDividerColorResource(context: Context, @ColorRes colorId: Int) {
    dividerColor = ContextCompat.getColor(context, colorId)
  }

  /**
   * Sets the start inset of the divider.
   *
   * @param insetStartId The id of the inset dimension resource to be set.
   * @attr ref com.google.android.material.R.styleable#MaterialDivider_dividerInsetStart
   * @see .getDividerInsetStart
   */
  fun setDividerInsetStartResource(context: Context, @DimenRes insetStartId: Int) {
    dividerInsetStart = context.resources.getDimensionPixelOffset(insetStartId)
  }

  /**
   * Sets the end inset of the divider.
   *
   * @param insetEndId The id of the inset dimension resource to be set.
   * @attr ref com.google.android.material.R.styleable#MaterialDivider_dividerInsetEnd
   * @see .getDividerInsetEnd
   */
  fun setDividerInsetEndResource(context: Context, @DimenRes insetEndId: Int) {
    dividerInsetEnd = context.resources.getDimensionPixelOffset(insetEndId)
  }

  override fun onDraw(
    canvas: Canvas,
    parent: RecyclerView,
    state: RecyclerView.State
  ) {
    if (parent.layoutManager == null) {
      return
    }
    if (orientation == VERTICAL) {
      drawForVerticalOrientation(canvas, parent)
    } else {
      drawForHorizontalOrientation(canvas, parent)
    }
  }

  /**
   * Draws a divider for the vertical orientation of the recycler view. The divider itself will be
   * horizontal.
   */
  private fun drawForVerticalOrientation(canvas: Canvas, parent: RecyclerView) {
    canvas.save()
    var left: Int
    var right: Int
    if (parent.clipToPadding) {
      left = parent.paddingLeft
      right = parent.width - parent.paddingRight
      canvas.clipRect(
        left,
        parent.paddingTop,
        right,
        parent.height - parent.paddingBottom
      )
    } else {
      left = 0
      right = parent.width
    }
    val isRtl = ViewCompat.getLayoutDirection(parent) == ViewCompat.LAYOUT_DIRECTION_RTL
    left += if (isRtl) dividerInsetEnd else dividerInsetStart
    right -= if (isRtl) dividerInsetStart else dividerInsetEnd
    val childCount = parent.childCount
    for (i in 0 until childCount) {
      val child = parent.getChildAt(i)
      if (shouldDrawDivider(parent, child)) {
        parent.getDecoratedBoundsWithMargins(child, tempRect)
        // Take into consideration any translationY added to the view.
        val bottom = tempRect.bottom + Math.round(child.translationY)
        val top = bottom - dividerThickness
        dividerDrawable.setBounds(left, top, right, bottom)
        dividerDrawable.draw(canvas)
      }
    }
    canvas.restore()
  }

  /**
   * Draws a divider for the horizontal orientation of the recycler view. The divider itself will be
   * vertical.
   */
  private fun drawForHorizontalOrientation(canvas: Canvas, parent: RecyclerView) {
    canvas.save()
    var top: Int
    var bottom: Int
    if (parent.clipToPadding) {
      top = parent.paddingTop
      bottom = parent.height - parent.paddingBottom
      canvas.clipRect(
        parent.paddingLeft,
        top,
        parent.width - parent.paddingRight,
        bottom
      )
    } else {
      top = 0
      bottom = parent.height
    }
    top += dividerInsetStart
    bottom -= dividerInsetEnd
    val childCount = parent.childCount
    for (i in 0 until childCount) {
      val child = parent.getChildAt(i)
      if (shouldDrawDivider(parent, child)) {
        parent.getDecoratedBoundsWithMargins(child, tempRect)
        // Take into consideration any translationX added to the view.
        val right = tempRect.right + Math.round(child.translationX)
        val left = right - dividerThickness
        dividerDrawable.setBounds(left, top, right, bottom)
        dividerDrawable.draw(canvas)
      }
    }
    canvas.restore()
  }

  override fun getItemOffsets(
    outRect: Rect,
    view: View,
    parent: RecyclerView,
    state: RecyclerView.State
  ) {
    outRect[0, 0, 0] = 0
    if (shouldAddOffset(parent, view, state)) {
      if (orientation == VERTICAL) {
        outRect.bottom = dividerThickness
      } else {
        outRect.right = dividerThickness
      }
    }
  }

  private fun shouldDrawDivider(parent: RecyclerView, child: View): Boolean {
    if (isLastItemDecorated) {
      return true
    }
    val position = parent.getChildAdapterPosition(child)
    val adapter = parent.adapter
    return position != RecyclerView.NO_POSITION && adapter != null && position != adapter.itemCount - 1
  }

  private fun shouldAddOffset(
    parent: RecyclerView,
    child: View,
    state: RecyclerView.State
  ): Boolean {
    if (isLastItemDecorated) {
      return true
    }
    val position = parent.getChildAdapterPosition(child)
    return position != RecyclerView.NO_POSITION && position != state.itemCount - 1
  }

  companion object {
    const val HORIZONTAL = LinearLayout.HORIZONTAL
    const val VERTICAL = LinearLayout.VERTICAL
    private val DEF_STYLE_RES = R.style.Widget_MaterialComponents_MaterialDivider
  }
}
