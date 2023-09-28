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

package dev.teogor.ceres.ui.designsystem.modalsheet

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.SpringSpec
import androidx.compose.animation.core.animate
import androidx.compose.foundation.MutatePriority
import androidx.compose.foundation.gestures.DragScope
import androidx.compose.foundation.gestures.DraggableState
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.offset
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import dev.teogor.ceres.ui.designsystem.modalsheet.AnchoredDraggableState.AnchorChangedCallback
import dev.teogor.ceres.ui.designsystem.modalsheet.AnchoredDraggableState.Companion
import dev.teogor.ceres.ui.designsystem.modalsheet.internal.InternalMutatorMutex
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlin.math.abs

/**
 * Enable drag gestures between a set of predefined values.
 *
 * When a drag is detected, the offset of the [AnchoredDraggableState] will be updated with the drag
 * delta. You should use this offset to move your content accordingly (see [Modifier.offset]).
 * When the drag ends, the offset will be animated to one of the anchors and when that anchor is
 * reached, the value of the [AnchoredDraggableState] will also be updated to the value
 * corresponding to the new anchor.
 *
 * Dragging is constrained between the minimum and maximum anchors.
 *
 * @param state The associated [AnchoredDraggableState].
 * @param orientation The orientation in which the [anchoredDraggable] can be dragged.
 * @param enabled Whether this [anchoredDraggable] is enabled and should react to the user's input.
 * @param reverseDirection Whether to reverse the direction of the drag, so a top to bottom
 * drag will behave like bottom to top, and a left to right drag will behave like right to left.
 * @param interactionSource Optional [MutableInteractionSource] that will passed on to
 * the internal [Modifier.draggable].
 */
@ExperimentalMaterialApi
internal fun <T> Modifier.anchoredDraggable(
  state: AnchoredDraggableState<T>,
  orientation: Orientation,
  enabled: Boolean = true,
  reverseDirection: Boolean = false,
  interactionSource: MutableInteractionSource? = null,
) = draggable(
  state = state.draggableState,
  orientation = orientation,
  enabled = enabled,
  interactionSource = interactionSource,
  reverseDirection = reverseDirection,
  startDragImmediately = state.isAnimationRunning,
  onDragStopped = { velocity -> launch { state.settle(velocity) } },
)

/**
 * Scope used for suspending anchored drag blocks. Allows to set [AnchoredDraggableState.offset] to
 * a new value.
 *
 * @see [AnchoredDraggableState.anchoredDrag] to learn how to start the anchored drag and get the
 * access to this scope.
 */
internal interface AnchoredDragScope {
  /**
   * Assign a new value for an offset value for [AnchoredDraggableState].
   *
   * @param newOffset new value for [AnchoredDraggableState.offset].
   * @param lastKnownVelocity last known velocity (if known)
   */
  fun dragTo(
    newOffset: Float,
    lastKnownVelocity: Float = 0f,
  )
}

/**
 * State of the [anchoredDraggable] modifier.
 *
 * This contains necessary information about any ongoing drag or animation and provides methods
 * to change the state either immediately or by starting an animation. To create and remember a
 * [AnchoredDraggableState] use [rememberAnchoredDraggableState].
 *
 * @param initialValue The initial value of the state.
 * @param animationSpec The default animation that will be used to animate to a new state.
 * @param confirmValueChange Optional callback invoked to confirm or veto a pending state change.
 * @param positionalThreshold The positional threshold, in px, to be used when calculating the
 * target state while a drag is in progress and when settling after the drag ends. This is the
 * distance from the start of a transition. It will be, depending on the direction of the
 * interaction, added or subtracted from/to the origin offset. It should always be a positive value.
 * @param velocityThreshold The velocity threshold (in px per second) that the end velocity has to
 * exceed in order to animate to the next state, even if the [positionalThreshold] has not been
 * reached.
 */
@Stable
@ExperimentalMaterialApi
internal class AnchoredDraggableState<T>(
  initialValue: T,
  internal val positionalThreshold: (totalDistance: Float) -> Float,
  internal val velocityThreshold: () -> Float,
  val animationSpec: AnimationSpec<Float> = AnchoredDraggableDefaults.AnimationSpec,
  internal val confirmValueChange: (newValue: T) -> Boolean = { true },
) {

  private val dragMutex = InternalMutatorMutex()

  internal val draggableState = object : DraggableState {

    private val dragScope = object : DragScope {
      override fun dragBy(pixels: Float) {
        with(anchoredDragScope) {
          dragTo(newOffsetForDelta(pixels))
        }
      }
    }

    override suspend fun drag(
      dragPriority: MutatePriority,
      block: suspend DragScope.() -> Unit,
    ) {
      this@AnchoredDraggableState.anchoredDrag(dragPriority) {
        with(dragScope) { block() }
      }
    }

    override fun dispatchRawDelta(delta: Float) {
      this@AnchoredDraggableState.dispatchRawDelta(delta)
    }
  }

  /**
   * The current value of the [AnchoredDraggableState].
   */
  var currentValue: T by mutableStateOf(initialValue)
    private set

  /**
   * The target value. This is the closest value to the current offset (taking into account
   * positional thresholds). If no interactions like animations or drags are in progress, this
   * will be the current value.
   */
  val targetValue: T by derivedStateOf {
    animationTarget ?: run {
      val currentOffset = offset
      if (!currentOffset.isNaN()) {
        computeTarget(currentOffset, currentValue, velocity = 0f)
      } else {
        currentValue
      }
    }
  }

  /**
   * The current offset, or [Float.NaN] if it has not been initialized yet.
   *
   * The offset will be initialized when the anchors are first set through [updateAnchors].
   *
   * Strongly consider using [requireOffset] which will throw if the offset is read before it is
   * initialized. This helps catch issues early in your workflow.
   */
  var offset: Float by mutableFloatStateOf(Float.NaN)
    private set

  /**
   * Require the current offset.
   *
   * @see offset
   *
   * @throws IllegalStateException If the offset has not been initialized yet
   */
  fun requireOffset(): Float {
    check(!offset.isNaN()) {
      "The offset was read before being initialized. Did you access the offset in a phase " +
        "before layout, like effects or composition?"
    }
    return offset
  }

  /**
   * Whether an animation is currently in progress.
   */
  val isAnimationRunning: Boolean get() = animationTarget != null

  /**
   * The fraction of the progress going from [currentValue] to [targetValue], within [0f..1f]
   * bounds.
   */
  /*@FloatRange(from = 0f, to = 1f)*/
  val progress: Float by derivedStateOf {
    val a = anchors[currentValue] ?: 0f
    val b = anchors[targetValue] ?: 0f
    val distance = abs(b - a)
    if (distance > 1e-6f) {
      val progress = (this.requireOffset() - a) / (b - a)
      // If we are very close to 0f or 1f, we round to the closest
      if (progress < 1e-6f) 0f else if (progress > 1 - 1e-6f) 1f else progress
    } else {
      1f
    }
  }

  /**
   * The velocity of the last known animation. Gets reset to 0f when an animation completes
   * successfully, but does not get reset when an animation gets interrupted.
   * You can use this value to provide smooth reconciliation behavior when re-targeting an
   * animation.
   */
  var lastVelocity: Float by mutableFloatStateOf(0f)
    private set

  /**
   * The minimum offset this state can reach. This will be the smallest anchor, or
   * [Float.NEGATIVE_INFINITY] if the anchors are not initialized yet.
   */
  val minOffset by derivedStateOf { anchors.minOrNull() ?: Float.NEGATIVE_INFINITY }

  /**
   * The maximum offset this state can reach. This will be the biggest anchor, or
   * [Float.POSITIVE_INFINITY] if the anchors are not initialized yet.
   */
  val maxOffset by derivedStateOf { anchors.maxOrNull() ?: Float.POSITIVE_INFINITY }

  private var animationTarget: T? by mutableStateOf(null)

  internal var anchors by mutableStateOf(emptyMap<T, Float>())

  /**
   * Update the anchors.
   * If the previous set of anchors was empty, attempt to update the offset to match the initial
   * value's anchor. If the [newAnchors] are different to the existing anchors, or there is no
   * anchor for the [currentValue], the [onAnchorsChanged] callback will be invoked.
   *
   * <b>If your anchors depend on the size of the layout, updateAnchors should be called in the
   * layout (placement) phase, e.g. through Modifier.onSizeChanged.</b> This ensures that the
   * state is set up within the same frame.
   * For static anchors, or anchors with different data dependencies, updateAnchors is safe to be
   * called any time, for example from a side effect.
   *
   * @param newAnchors The new anchors
   * @param onAnchorsChanged Optional callback to be invoked if the state needs to be updated
   * after updating the anchors, for example if the anchor for the [currentValue] has been removed
   */
  internal fun updateAnchors(
    newAnchors: Map<T, Float>,
    onAnchorsChanged: AnchorChangedCallback<T>? = null,
  ) {
    if (anchors != newAnchors) {
      val previousAnchors = anchors
      val previousTarget = targetValue
      val previousAnchorsEmpty = anchors.isEmpty()
      anchors = newAnchors

      val currentValueHasAnchor = anchors[currentValue] != null
      if (previousAnchorsEmpty && currentValueHasAnchor) {
        trySnapTo(currentValue)
      } else {
        onAnchorsChanged?.onAnchorsChanged(
          previousTargetValue = previousTarget,
          previousAnchors = previousAnchors,
          newAnchors = newAnchors,
        )
      }
    }
  }

  /**
   * Whether the [value] has an anchor associated with it.
   */
  fun hasAnchorForValue(value: T): Boolean = anchors.containsKey(value)

  /**
   * Find the closest anchor taking into account the velocity and settle at it with an animation.
   */
  suspend fun settle(velocity: Float) {
    val previousValue = this.currentValue
    val targetValue = computeTarget(
      offset = requireOffset(),
      currentValue = previousValue,
      velocity = velocity,
    )
    if (confirmValueChange(targetValue)) {
      animateTo(targetValue, velocity)
    } else {
      // If the user vetoed the state change, rollback to the previous state.
      animateTo(previousValue, velocity)
    }
  }

  private fun computeTarget(
    offset: Float,
    currentValue: T,
    velocity: Float,
  ): T {
    val currentAnchors = anchors
    val currentAnchor = currentAnchors[currentValue]
    val velocityThresholdPx = velocityThreshold()
    return if (currentAnchor == offset || currentAnchor == null) {
      currentValue
    } else if (currentAnchor < offset) {
      // Swiping from lower to upper (positive).
      if (velocity >= velocityThresholdPx) {
        currentAnchors.closestAnchor(offset, true)
      } else {
        val upper = currentAnchors.closestAnchor(offset, true)
        val distance = abs(currentAnchors.getValue(upper) - currentAnchor)
        val relativeThreshold = abs(positionalThreshold(distance))
        val absoluteThreshold = abs(currentAnchor + relativeThreshold)
        if (offset < absoluteThreshold) currentValue else upper
      }
    } else {
      // Swiping from upper to lower (negative).
      if (velocity <= -velocityThresholdPx) {
        currentAnchors.closestAnchor(offset, false)
      } else {
        val lower = currentAnchors.closestAnchor(offset, false)
        val distance = abs(currentAnchor - currentAnchors.getValue(lower))
        val relativeThreshold = abs(positionalThreshold(distance))
        val absoluteThreshold = abs(currentAnchor - relativeThreshold)
        if (offset < 0) {
          // For negative offsets, larger absolute thresholds are closer to lower anchors
          // than smaller ones.
          if (abs(offset) < absoluteThreshold) currentValue else lower
        } else {
          if (offset > absoluteThreshold) currentValue else lower
        }
      }
    }
  }

  private val anchoredDragScope: AnchoredDragScope = object : AnchoredDragScope {
    override fun dragTo(newOffset: Float, lastKnownVelocity: Float) {
      offset = newOffset
      lastVelocity = lastKnownVelocity
    }
  }

  /**
   * Call this function to take control of drag logic and perform anchored drag.
   *
   * All actions that change the [offset] of this [AnchoredDraggableState] must be performed
   * within an [anchoredDrag] block (even if they don't call any other methods on this object)
   * in order to guarantee that mutual exclusion is enforced.
   *
   * If [anchoredDrag] is called from elsewhere with the [dragPriority] higher or equal to ongoing
   * drag, ongoing drag will be canceled.
   *
   * @param dragPriority of the drag operation
   * @param block perform anchored drag given the current anchor provided
   */
  suspend fun anchoredDrag(
    dragPriority: MutatePriority = MutatePriority.Default,
    block: suspend AnchoredDragScope.(anchors: Map<T, Float>) -> Unit,
  ): Unit = doAnchoredDrag(null, dragPriority, block)

  /**
   * Call this function to take control of drag logic and perform anchored drag.
   *
   * All actions that change the [offset] of this [AnchoredDraggableState] must be performed
   * within an [anchoredDrag] block (even if they don't call any other methods on this object)
   * in order to guarantee that mutual exclusion is enforced.
   *
   * This overload allows the caller to hint the target value that this [anchoredDrag] is intended
   * to arrive to. This will set [AnchoredDraggableState.targetValue] to provided value so
   * consumers can reflect it in their UIs.
   *
   * If [anchoredDrag] is called from elsewhere with the [dragPriority] higher or equal to ongoing
   * drag, ongoing drag will be canceled.
   *
   * @param targetValue hint the target value that this [anchoredDrag] is intended to arrive to
   * @param dragPriority of the drag operation
   * @param block perform anchored drag given the current anchor provided
   */
  suspend fun anchoredDrag(
    targetValue: T,
    dragPriority: MutatePriority = MutatePriority.Default,
    block: suspend AnchoredDragScope.(anchors: Map<T, Float>) -> Unit,
  ): Unit = doAnchoredDrag(targetValue, dragPriority, block)

  private suspend fun doAnchoredDrag(
    targetValue: T?,
    dragPriority: MutatePriority,
    block: suspend AnchoredDragScope.(anchors: Map<T, Float>) -> Unit,
  ) = coroutineScope {
    if (targetValue == null || anchors.containsKey(targetValue)) {
      try {
        dragMutex.mutate(dragPriority) {
          if (targetValue != null) animationTarget = targetValue
          anchoredDragScope.block(anchors)
        }
      } finally {
        if (targetValue != null) animationTarget = null
        val endState =
          anchors
            .entries
            .firstOrNull { (_, anchorOffset) -> abs(anchorOffset - offset) < 0.5f }
            ?.key

        if (endState != null && confirmValueChange.invoke(endState)) {
          currentValue = endState
        }
      }
    } else if (confirmValueChange(targetValue)) {
      currentValue = targetValue
    }
  }

  internal fun newOffsetForDelta(delta: Float) =
    ((if (offset.isNaN()) 0f else offset) + delta).coerceIn(minOffset, maxOffset)

  /**
   * Drag by the [delta], coerce it in the bounds and dispatch it to the [AnchoredDraggableState].
   *
   * @return The delta the consumed by the [AnchoredDraggableState]
   */
  fun dispatchRawDelta(delta: Float): Float {
    val newOffset = newOffsetForDelta(delta)
    val oldOffset = if (offset.isNaN()) 0f else offset
    offset = newOffset
    return newOffset - oldOffset
  }

  /**
   * Attempt to snap synchronously. Snapping can happen synchronously when there is no other drag
   * transaction like a drag or an animation is progress. If there is another interaction in
   * progress, the suspending [snapTo] overload needs to be used.
   *
   * @return true if the synchronous snap was successful, or false if we couldn't snap synchronous
   */
  internal fun trySnapTo(targetValue: T): Boolean = dragMutex.tryMutate {
    with(anchoredDragScope) {
      val targetOffset = anchors[targetValue]
      if (targetOffset != null) {
        dragTo(targetOffset)
        animationTarget = null
      }
      currentValue = targetValue
    }
  }

  companion object {
    /**
     * The default [Saver] implementation for [AnchoredDraggableState].
     */
    @ExperimentalMaterialApi
    fun <T : Any> Saver(
      animationSpec: AnimationSpec<Float>,
      confirmValueChange: (T) -> Boolean,
      positionalThreshold: (distance: Float) -> Float,
      velocityThreshold: () -> Float,
    ) = Saver<AnchoredDraggableState<T>, T>(
      save = { it.currentValue },
      restore = {
        AnchoredDraggableState(
          initialValue = it,
          animationSpec = animationSpec,
          confirmValueChange = confirmValueChange,
          positionalThreshold = positionalThreshold,
          velocityThreshold = velocityThreshold,
        )
      },
    )
  }

  /**
   * Defines a callback that is invoked when the anchors have changed.
   *
   * Components with custom reconciliation logic should implement this callback, for example to
   * re-target an in-progress animation when the anchors change.
   *
   * @see AnchoredDraggableDefaults.ReconcileAnimationOnAnchorChangedCallback for a default
   * implementation
   */
  @ExperimentalMaterialApi
  fun interface AnchorChangedCallback<T> {

    /**
     * Callback that is invoked when the anchors have changed, after the
     * [AnchoredDraggableState] has been updated with them. Use this hook to re-launch
     * animations or interrupt them if needed.
     *
     * @param previousTargetValue The target value before the anchors were updated
     * @param previousAnchors The previously set anchors
     * @param newAnchors The newly set anchors
     */
    fun onAnchorsChanged(
      previousTargetValue: T,
      previousAnchors: Map<T, Float>,
      newAnchors: Map<T, Float>,
    )
  }
}

/**
 * Snap to a [targetValue] without any animation.
 * If the [targetValue] is not in the set of anchors, the [AnchoredDraggableState.currentValue] will
 * be updated to the [targetValue] without updating the offset.
 *
 * @throws CancellationException if the interaction interrupted by another interaction like a
 * gesture interaction or another programmatic interaction like a [animateTo] or [snapTo] call.
 *
 * @param targetValue The target value of the animation
 */
@ExperimentalMaterialApi
internal suspend fun <T> AnchoredDraggableState<T>.snapTo(targetValue: T) {
  anchoredDrag(targetValue = targetValue) { anchors ->
    val targetOffset = anchors[targetValue]
    if (targetOffset != null) dragTo(targetOffset)
  }
}

/**
 * Animate to a [targetValue].
 * If the [targetValue] is not in the set of anchors, the [AnchoredDraggableState.currentValue] will
 * be updated to the [targetValue] without updating the offset.
 *
 * @throws CancellationException if the interaction interrupted by another interaction like a
 * gesture interaction or another programmatic interaction like a [animateTo] or [snapTo] call.
 *
 * @param targetValue The target value of the animation
 * @param velocity The velocity the animation should start with
 */
@ExperimentalMaterialApi
internal suspend fun <T> AnchoredDraggableState<T>.animateTo(
  targetValue: T,
  velocity: Float = this.lastVelocity,
) {
  anchoredDrag(targetValue = targetValue) { anchors ->
    val targetOffset = anchors[targetValue]
    if (targetOffset != null) {
      var prev = if (offset.isNaN()) 0f else offset
      animate(prev, targetOffset, velocity, animationSpec) { value, velocity ->
        // Our onDrag coerces the value within the bounds, but an animation may
        // overshoot, for example a spring animation or an overshooting interpolator
        // We respect the user's intention and allow the overshoot, but still use
        // DraggableState's drag for its mutex.
        dragTo(value, velocity)
        prev = value
      }
    }
  }
}

/**
 * Create and remember a [AnchoredDraggableState].
 *
 * @param initialValue The initial value.
 * @param animationSpec The default animation that will be used to animate to a new value.
 * @param confirmValueChange Optional callback invoked to confirm or veto a pending value change.
 */
@Composable
@ExperimentalMaterialApi
internal fun <T : Any> rememberAnchoredDraggableState(
  initialValue: T,
  animationSpec: AnimationSpec<Float> = AnchoredDraggableDefaults.AnimationSpec,
  confirmValueChange: (newValue: T) -> Boolean = { true },
): AnchoredDraggableState<T> {
  val positionalThreshold = AnchoredDraggableDefaults.positionalThreshold
  val velocityThreshold = AnchoredDraggableDefaults.velocityThreshold
  return rememberSaveable(
    initialValue,
    animationSpec,
    confirmValueChange,
    positionalThreshold,
    velocityThreshold,
    saver = Companion.Saver(
      animationSpec = animationSpec,
      confirmValueChange = confirmValueChange,
      positionalThreshold = positionalThreshold,
      velocityThreshold = velocityThreshold,
    ),
  ) {
    AnchoredDraggableState(
      initialValue = initialValue,
      animationSpec = animationSpec,
      confirmValueChange = confirmValueChange,
      positionalThreshold = positionalThreshold,
      velocityThreshold = velocityThreshold,
    )
  }
}

/**
 * Contains useful defaults for [anchoredDraggable] and [AnchoredDraggableState].
 */
@Stable
@ExperimentalMaterialApi
internal object AnchoredDraggableDefaults {
  /**
   * The default animation used by [AnchoredDraggableState].
   */
  @get:ExperimentalMaterialApi
  @Suppress("OPT_IN_MARKER_ON_WRONG_TARGET")
  @ExperimentalMaterialApi
  val AnimationSpec = SpringSpec<Float>()

  /**
   * The default velocity threshold (1.8 dp per millisecond) used by
   * [rememberAnchoredDraggableState].
   */
  @get:ExperimentalMaterialApi
  @Suppress("OPT_IN_MARKER_ON_WRONG_TARGET")
  @ExperimentalMaterialApi
  val velocityThreshold: () -> Float
    @Composable get() = with(LocalDensity.current) { { 125.dp.toPx() } }

  /**
   * The default positional threshold (56 dp) used by [rememberAnchoredDraggableState]
   */
  @get:ExperimentalMaterialApi
  @Suppress("OPT_IN_MARKER_ON_WRONG_TARGET")
  @ExperimentalMaterialApi
  val positionalThreshold: (totalDistance: Float) -> Float
    @Composable get() = with(LocalDensity.current) {
      {
        56.dp.toPx()
      }
    }

  /**
   * A [AnchorChangedCallback] implementation that attempts to reconcile an in-progress animation
   * by re-targeting it if necessary or finding the closest new anchor.
   * If the previous anchor is not in the new set of anchors, this implementation will snap to the
   * closest anchor.
   *
   * Consider implementing a custom handler for more complex components like sheets.
   */
  @ExperimentalMaterialApi
  internal fun <T> ReconcileAnimationOnAnchorChangedCallback(
    state: AnchoredDraggableState<T>,
    scope: CoroutineScope,
  ) = AnchorChangedCallback<T> { previousTarget, previousAnchors, newAnchors ->
    val previousTargetOffset = previousAnchors[previousTarget]
    val newTargetOffset = newAnchors[previousTarget]
    if (previousTargetOffset != newTargetOffset) {
      if (newTargetOffset != null) {
        scope.launch {
          state.animateTo(previousTarget, state.lastVelocity)
        }
      } else {
        scope.launch {
          state.snapTo(newAnchors.closestAnchor(offset = state.requireOffset()))
        }
      }
    }
  }
}

private fun <T> Map<T, Float>.closestAnchor(
  offset: Float = 0f,
  searchUpwards: Boolean = false,
): T {
  require(isNotEmpty()) { "The anchors were empty when trying to find the closest anchor" }
  return minBy { (_, anchor) ->
    val delta = if (searchUpwards) anchor - offset else offset - anchor
    if (delta < 0) Float.POSITIVE_INFINITY else delta
  }.key
}

private fun <T> Map<T, Float>.minOrNull() = minOfOrNull { (_, offset) -> offset }
private fun <T> Map<T, Float>.maxOrNull() = maxOfOrNull { (_, offset) -> offset }
