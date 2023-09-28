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
import androidx.compose.foundation.lazy.LazyListItemInfo
import androidx.compose.foundation.lazy.LazyListState
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
import kotlin.math.floor

@Composable
fun LazyColumnScrollbar(
  scrollbarCustomization: ScrollbarCustomization,
  content: @Composable () -> Unit,
) {
  LazyColumnScrollbar(
    listState = scrollbarCustomization.state as LazyListState,
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
 * Scrollbar for LazyColumn
 *
 * @param rightSide true -> right,  false -> left
 * @param thickness Thickness of the scrollbar thumb
 * @param padding Padding of the scrollbar
 * @param thumbMinHeight Thumb minimum height proportional to total scrollbar's height (eg: 0.1 -> 10% of total)
 */
@Composable
fun LazyColumnScrollbar(
  listState: LazyListState,
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
  enabled: Boolean = true,
  hasScrollbarBackground: Boolean = true,
  indicatorContent: (@Composable (index: Int, isThumbSelected: Boolean) -> Unit)? = null,
  content: @Composable () -> Unit,
) {
  if (!enabled) {
    content()
  } else {
    Box {
      content()
      InternalLazyColumnScrollbar(
        listState = listState,
        rightSide = rightSide,
        thickness = thickness,
        padding = padding,
        thumbMinHeight = thumbMinHeight,
        thumbColor = thumbColor,
        thumbSelectedColor = thumbSelectedColor,
        thumbShape = thumbShape,
        selectionMode = selectionMode,
        hasScrollbarBackground = hasScrollbarBackground,
        indicatorContent = indicatorContent,
      )
    }
  }
}

/**
 * Scrollbar for LazyColumn
 * Use this variation if you want to place the scrollbar independently of the LazyColumn position
 *
 * @param rightSide true -> right,  false -> left
 * @param thickness Thickness of the scrollbar thumb
 * @param padding Padding of the scrollbar
 * @param thumbMinHeight Thumb minimum height proportional to total scrollbar's height (eg: 0.1 -> 10% of total)
 */
@Composable
fun InternalLazyColumnScrollbar(
  listState: LazyListState,
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
  indicatorContent: (@Composable (index: Int, isThumbSelected: Boolean) -> Unit)? = null,
) {
  val firstVisibleItemIndex = remember { derivedStateOf { listState.firstVisibleItemIndex } }

  val coroutineScope = rememberCoroutineScope()

  var isSelected by remember { mutableStateOf(false) }

  var dragOffset by remember { mutableStateOf(0f) }

  val reverseLayout by remember { derivedStateOf { listState.layoutInfo.reverseLayout } }

  val realFirstVisibleItem by remember {
    derivedStateOf {
      listState.layoutInfo.visibleItemsInfo.firstOrNull {
        it.index == listState.firstVisibleItemIndex
      }
    }
  }

  val isStickyHeaderInAction by remember {
    derivedStateOf {
      val realIndex = realFirstVisibleItem?.index ?: return@derivedStateOf false
      val firstVisibleIndex = listState.layoutInfo.visibleItemsInfo.firstOrNull()?.index
        ?: return@derivedStateOf false
      realIndex != firstVisibleIndex
    }
  }

  fun LazyListItemInfo.fractionHiddenTop() =
    if (size == 0) 0f else -offset.toFloat() / size.toFloat()

  fun LazyListItemInfo.fractionVisibleBottom(viewportEndOffset: Int) =
    if (size == 0) 0f else (viewportEndOffset - offset).toFloat() / size.toFloat()

  // todo bottom-offset???
  val normalizedThumbSizeReal by remember {
    derivedStateOf {
      listState.layoutInfo.let {
        if (it.totalItemsCount == 0) {
          return@let 0f
        }

        val firstItem = realFirstVisibleItem ?: return@let 0f

        val isLastItemVisible = it.visibleItemsInfo.last().index == it.totalItemsCount - 1

        // todo do we truly need this stuff?
        //  since it always adds up to 1f
        val firstPartial = firstItem.fractionHiddenTop()
        val lastPartial = if (isLastItemVisible) {
          1f - firstPartial
        } else {
          (1f - it.visibleItemsInfo.last().fractionVisibleBottom(it.viewportEndOffset))
            .coerceAtLeast(0f)
        }

        val realSize = it.visibleItemsInfo.size - if (isStickyHeaderInAction) 1 else 0
        val realVisibleSize = realSize.toFloat() - firstPartial - lastPartial

        realVisibleSize / it.totalItemsCount.toFloat()
      }
    }
  }

  val normalizedThumbSize by remember {
    derivedStateOf {
      normalizedThumbSizeReal.coerceAtLeast(thumbMinHeight)
    }
  }
  val normalizedThumbSizeUpdated by rememberUpdatedState(newValue = normalizedThumbSize)

  fun offsetCorrection(top: Float): Float {
    // todo bottom-offset???
    val topRealMax = (1f - normalizedThumbSizeUpdated).coerceIn(0f, 1f)
    if (normalizedThumbSizeUpdated >= thumbMinHeight) {
      return when {
        reverseLayout -> topRealMax - top
        else -> top
      }
    }

    val topMax = 1f - thumbMinHeight
    return when {
      reverseLayout -> (topRealMax - top) * topMax / topRealMax
      else -> top * topMax / topRealMax
    }
  }

  fun offsetCorrectionInverse(top: Float): Float {
    // todo bottom-offset???
    if (normalizedThumbSizeUpdated >= thumbMinHeight) {
      return top
    }
    val topRealMax = 1f - normalizedThumbSizeUpdated
    val topMax = 1f - thumbMinHeight
    return top * topRealMax / topMax
  }

  val normalizedOffsetPosition by remember {
    derivedStateOf {
      listState.layoutInfo.let {
        if (it.totalItemsCount == 0 || it.visibleItemsInfo.isEmpty()) {
          return@let 0f
        }

        val firstItem = realFirstVisibleItem ?: return@let 0f
        val top = firstItem.run {
          index.toFloat() + fractionHiddenTop()
        } / it.totalItemsCount.toFloat()

        val isLastItemVisible = it.visibleItemsInfo.last().index == it.totalItemsCount - 1
        val lastPartial = if (isLastItemVisible) {
          it.visibleItemsInfo.last().fractionVisibleBottom(it.viewportEndOffset) / 5f * 3f
        } else {
          0f
        } / it.totalItemsCount.toFloat()
        offsetCorrection(top + lastPartial)
      }
    }
  }

  fun setDragOffset(value: Float) {
    val maxValue = (1f - normalizedThumbSize).coerceAtLeast(0f)
    dragOffset = value.coerceIn(0f, maxValue)
  }

  fun setScrollOffset(newOffset: Float) {
    setDragOffset(newOffset)
    val totalItemsCount = listState.layoutInfo.totalItemsCount.toFloat()
    val exactIndex = offsetCorrectionInverse(totalItemsCount * dragOffset)
    val index: Int = floor(exactIndex).toInt()
    val remainder: Float = exactIndex - floor(exactIndex)

    coroutineScope.launch {
      listState.scrollToItem(index = index, scrollOffset = 0)
      val offset = realFirstVisibleItem
        ?.size
        ?.let { it.toFloat() * remainder }
        ?.toInt() ?: 0
      listState.scrollToItem(index = index, scrollOffset = offset)
    }
  }

  val isInAction = listState.isScrollInProgress || isSelected

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
          .graphicsLayer(
            translationX = with(LocalDensity.current) {
              (if (rightSide) displacement.dp else -displacement.dp).toPx()
            },
            translationY = constraints.maxHeight.toFloat() * normalizedOffsetPosition,
          ),
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
              index = firstVisibleItemIndex.value,
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
            val displace = if (reverseLayout) -delta else delta // side effect ?
            if (isSelected) {
              setScrollOffset(dragOffset + displace / constraints.maxHeight.toFloat())
            }
          },
          orientation = Orientation.Vertical,
          enabled = selectionMode != Disabled,
          startDragImmediately = true,
          onDragStarted = onDragStarted@{ offset ->
            val maxHeight = constraints.maxHeight.toFloat()
            if (maxHeight <= 0f) return@onDragStarted
            val newOffset = when {
              reverseLayout -> (maxHeight - offset.y) / maxHeight
              else -> offset.y / maxHeight
            }
            val currentOffset = when {
              reverseLayout -> 1f - normalizedOffsetPosition - normalizedThumbSize
              else -> normalizedOffsetPosition
            }
            when (selectionMode) {
              Full -> {
                if (newOffset in currentOffset..(currentOffset + normalizedThumbSize)) {
                  setDragOffset(currentOffset)
                } else {
                  setScrollOffset(newOffset)
                }
                isSelected = true
              }

              Thumb -> {
                if (newOffset in currentOffset..(currentOffset + normalizedThumbSize)) {
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
        .graphicsLayer(
          translationX = with(LocalDensity.current) {
            (if (rightSide) displacement.dp else -displacement.dp).toPx()
          },
        )
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
