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

package dev.teogor.ceres.ui.designsystem.scrollbar

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import dev.teogor.ceres.ui.designsystem.scrollbar.ScrollbarSelectionMode.Disabled
import dev.teogor.ceres.ui.designsystem.scrollbar.ScrollbarSelectionMode.Full
import dev.teogor.ceres.ui.designsystem.scrollbar.ScrollbarSelectionMode.Thumb
import kotlinx.coroutines.launch

@Composable
fun ColumnScrollbar(
  scrollbarCustomization: ScrollbarCustomization,
  content: @Composable () -> Unit,
) {
  ColumnScrollbar(
    state = scrollbarCustomization.state as ScrollState,
    rightSide = scrollbarCustomization.rightSide,
    thickness = scrollbarCustomization.thickness,
    padding = scrollbarCustomization.padding,
    thumbMinHeight = scrollbarCustomization.thumbMinHeight,
    thumbColor = ScrollbarDefaults.thumbColor(scrollbarCustomization.thumbColor),
    thumbSelectedColor = ScrollbarDefaults.thumbSelectedColor(
      scrollbarCustomization.thumbSelectedColor,
    ),
    thumbShape = scrollbarCustomization.thumbShape,
    enabled = scrollbarCustomization.enabled,
    selectionMode = scrollbarCustomization.selectionMode,
    hasScrollbarBackground = scrollbarCustomization.hasScrollbarBackground,
    indicatorContent = scrollbarCustomization.indicatorContent,
    content = content,
  )
}

/**
 * Scrollbar for Column
 *
 * @param rightSide true -> right,  false -> left
 * @param thickness Thickness of the scrollbar thumb
 * @param padding   Padding of the scrollbar
 * @param thumbMinHeight Thumb minimum height proportional to total scrollbar's height (eg: 0.1 -> 10% of total)
 */
@Composable
fun ColumnScrollbar(
  state: ScrollState,
  rightSide: Boolean = true,
  thickness: Dp = ScrollbarTokens.Thickness,
  padding: Dp = ScrollbarTokens.Padding,
  thumbMinHeight: Float = ScrollbarTokens.ThumbMinHeight,
  thumbColor: Color = ScrollbarDefaults.thumbColor(ScrollbarTokens.ThumbColor),
  thumbSelectedColor: Color = ScrollbarDefaults.thumbSelectedColor(
    ScrollbarTokens.ThumbSelectedColor,
  ),
  thumbShape: Shape = ScrollbarTokens.ThumbShape,
  enabled: Boolean = true,
  selectionMode: ScrollbarSelectionMode = Thumb,
  hasScrollbarBackground: Boolean = true,
  indicatorContent: (@Composable (normalizedOffset: Int, isThumbSelected: Boolean) -> Unit)? = null,
  content: @Composable () -> Unit,
) {
  if (!enabled) {
    content()
  } else {
    BoxWithConstraints {
      content()
      InternalColumnScrollbar(
        state = state,
        rightSide = rightSide,
        thickness = thickness,
        padding = padding,
        thumbMinHeight = thumbMinHeight,
        thumbColor = thumbColor,
        thumbSelectedColor = thumbSelectedColor,
        thumbShape = thumbShape,
        visibleHeightDp = with(LocalDensity.current) { constraints.maxHeight.toDp() },
        indicatorContent = indicatorContent,
        hasScrollbarBackground = hasScrollbarBackground,
        selectionMode = selectionMode,
      )
    }
  }
}

/**
 * Scrollbar for Column
 * Use this variation if you want to place the scrollbar independently of the Column position
 *
 * @param rightSide true -> right,  false -> left
 * @param thickness Thickness of the scrollbar thumb
 * @param padding   Padding of the scrollbar
 * @param thumbMinHeight Thumb minimum height proportional to total scrollbar's height (eg: 0.1 -> 10% of total)
 * @param visibleHeightDp Visible height of column view
 */
@Composable
fun InternalColumnScrollbar(
  state: ScrollState,
  rightSide: Boolean = true,
  thickness: Dp = ScrollbarTokens.Thickness,
  padding: Dp = ScrollbarTokens.Padding,
  thumbMinHeight: Float = ScrollbarTokens.ThumbMinHeight,
  thumbColor: Color = ScrollbarDefaults.thumbColor(ScrollbarTokens.ThumbColor),
  thumbSelectedColor: Color = ScrollbarDefaults.thumbSelectedColor(
    ScrollbarTokens.ThumbSelectedColor,
  ),
  thumbShape: Shape = ScrollbarTokens.ThumbShape,
  selectionMode: ScrollbarSelectionMode = Thumb,
  hasScrollbarBackground: Boolean = true,
  indicatorContent: (@Composable (normalizedOffset: Int, isThumbSelected: Boolean) -> Unit)? = null,
  visibleHeightDp: Dp,
) {
  val coroutineScope = rememberCoroutineScope()

  var isSelected by remember { mutableStateOf(false) }

  var dragOffset by remember { mutableStateOf(0f) }

  val fullHeightDp = with(LocalDensity.current) { state.maxValue.toDp() + visibleHeightDp }

  val normalizedThumbSizeReal by remember(visibleHeightDp, state.maxValue) {
    derivedStateOf {
      if (fullHeightDp == 0.dp) {
        1f
      } else {
        val normalizedDp = visibleHeightDp / fullHeightDp
        normalizedDp.coerceIn(0f, 1f)
      }
    }
  }

  val normalizedThumbSize by remember(normalizedThumbSizeReal) {
    derivedStateOf {
      normalizedThumbSizeReal.coerceAtLeast(thumbMinHeight)
    }
  }

  val normalizedThumbSizeUpdated by rememberUpdatedState(newValue = normalizedThumbSize)

  fun offsetCorrection(top: Float): Float {
    val topRealMax = 1f
    val topMax = (1f - normalizedThumbSizeUpdated).coerceIn(0f, 1f)
    return top * topMax / topRealMax
  }

  fun offsetCorrectionInverse(top: Float): Float {
    val topRealMax = 1f
    val topMax = 1f - normalizedThumbSizeUpdated
    if (topMax == 0f) return top
    return (top * topRealMax / topMax).coerceAtLeast(0f)
  }

  val normalizedOffsetPosition by remember {
    derivedStateOf {
      if (state.maxValue == 0) return@derivedStateOf 0f
      val normalized = state.value.toFloat() / state.maxValue.toFloat()
      offsetCorrection(normalized)
    }
  }

  fun setDragOffset(value: Float) {
    val maxValue = (1f - normalizedThumbSize).coerceAtLeast(0f)
    dragOffset = value.coerceIn(0f, maxValue)
  }

  fun setScrollOffset(newOffset: Float) {
    setDragOffset(newOffset)
    val exactIndex = offsetCorrectionInverse(state.maxValue * dragOffset).toInt()
    coroutineScope.launch {
      state.scrollTo(exactIndex)
    }
  }

  val isInAction = state.isScrollInProgress || isSelected

  val alpha by animateFloatAsState(
    targetValue = if (isInAction) 1f else 0f,
    animationSpec = tween(
      durationMillis = if (isInAction) 75 else 500,
      delayMillis = if (isInAction) 0 else 500,
    ),
    label = "alpha",
  )

  val displacement by animateFloatAsState(
    targetValue = if (isInAction) 0f else 14f,
    animationSpec = tween(
      durationMillis = if (isInAction) 75 else 500,
      delayMillis = if (isInAction) 0 else 500,
    ),
    label = "displacement",
  )

  BoxWithConstraints(
    Modifier
      .alpha(alpha)
      .fillMaxWidth(),
  ) {
    if (indicatorContent != null) {
      BoxWithConstraints(
        Modifier
          .align(if (rightSide) Alignment.TopEnd else Alignment.TopStart)
          .fillMaxHeight()
          .graphicsLayer {
            translationX = (if (rightSide) displacement.dp else -displacement.dp).toPx()
            translationY = constraints.maxHeight.toFloat() * normalizedOffsetPosition
          },
      ) {
        ConstraintLayout(
          Modifier.align(Alignment.TopEnd),
        ) {
          val (box, content) = createRefs()
          Box(
            modifier = Modifier
              .fillMaxHeight(normalizedThumbSize)
              .padding(
                start = if (rightSide) 0.dp else padding,
                end = if (!rightSide) 0.dp else padding,
              )
              .width(thickness)
              .constrainAs(box) {
                if (rightSide) {
                  end.linkTo(parent.end)
                } else {
                  start.linkTo(parent.start)
                }
              },
          )

          Box(
            modifier = Modifier
              .constrainAs(content) {
                top.linkTo(box.top)
                bottom.linkTo(box.bottom)
                if (rightSide) {
                  end.linkTo(box.start)
                } else {
                  start.linkTo(box.end)
                }
              }
              .testTag(TestTagsScrollbar.scrollbarIndicator),
          ) {
            indicatorContent(
              normalizedOffset = offsetCorrectionInverse(normalizedOffsetPosition).toInt(),
              isThumbSelected = isSelected,
            )
          }
        }
      }
    }

    BoxWithConstraints(
      Modifier
        .align(if (rightSide) Alignment.TopEnd else Alignment.TopStart)
        .fillMaxHeight()
        .background(
          color = if (hasScrollbarBackground) {
            ScrollbarDefaults.background()
          } else {
            Color.Unspecified
          },
        )
        .draggable(
          state = rememberDraggableState { delta ->
            if (isSelected) {
              setScrollOffset(dragOffset + delta / constraints.maxHeight.toFloat())
            }
          },
          orientation = Orientation.Vertical,
          enabled = selectionMode != Disabled,
          startDragImmediately = true,
          onDragStarted = { offset ->
            val newOffset = offset.y / constraints.maxHeight.toFloat()
            val currentOffset = normalizedOffsetPosition
            when (selectionMode) {
              Full -> {
                if (newOffset in currentOffset..(currentOffset + normalizedThumbSizeUpdated)) {
                  setDragOffset(currentOffset)
                } else {
                  setScrollOffset(newOffset)
                }
                isSelected = true
              }

              Thumb -> {
                if (newOffset in currentOffset..(currentOffset + normalizedThumbSizeUpdated)) {
                  setDragOffset(currentOffset)
                  isSelected = true
                }
              }

              Disabled -> Unit
            }
          },
          onDragStopped = {
            isSelected = false
          },
        )
        .graphicsLayer {
          translationX = (if (rightSide) displacement.dp else -displacement.dp).toPx()
        }
        .testTag(TestTagsScrollbar.scrollbarContainer),
    ) {
      Box(
        modifier = Modifier
          .align(Alignment.TopEnd)
          .graphicsLayer(translationY = constraints.maxHeight.toFloat() * normalizedOffsetPosition)
          .padding(horizontal = padding)
          .width(thickness)
          .clip(thumbShape)
          .background(if (isSelected) thumbSelectedColor else thumbColor)
          .fillMaxHeight(normalizedThumbSize)
          .testTag(TestTagsScrollbar.scrollbar),
      )
    }
  }
}
