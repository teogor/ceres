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

package dev.teogor.ceres.ui.designsystem

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.snap
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.selection.triStateToggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathMeasure
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import dev.teogor.ceres.ui.designsystem.TriCheckboxState.Crossed
import dev.teogor.ceres.ui.designsystem.tokens.CheckboxTokens
import dev.teogor.ceres.ui.foundation.clickableSingle
import dev.teogor.ceres.ui.theme.MaterialTheme
import dev.teogor.ceres.ui.theme.fromToken
import kotlin.math.floor
import kotlin.math.max

@Composable
fun CheckboxWithText(
  text: String,
  isChecked: Boolean,
  onCheckedChange: (Boolean) -> Unit,
  modifier: Modifier = Modifier,
  horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
) {
  Row(
    modifier = modifier
      .fillMaxWidth()
      .height(42.dp)
      .clip(shape = RoundedCornerShape(6.dp))
      .clickableSingle { onCheckedChange(!isChecked) },
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = horizontalArrangement,
  ) {
    Checkbox(
      checked = isChecked,
      onCheckedChange = onCheckedChange,
      modifier = Modifier
        .align(Alignment.CenterVertically),
    )
    Text(
      text = text,
      modifier = Modifier.padding(start = 0.dp),
      color = MaterialTheme.colorScheme.onBackground,
      fontSize = 14.sp,
    )
  }
}

@Composable
fun Checkbox(
  checked: Boolean,
  onCheckedChange: ((Boolean) -> Unit)?,
  modifier: Modifier = Modifier,
  enabled: Boolean = true,
  colors: CheckboxColors = CheckboxDefaults.colors(),
  interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
  TriStateCheckbox(
    state = ToggleableState(checked),
    onClick = if (onCheckedChange != null) {
      { onCheckedChange(!checked) }
    } else {
      null
    },
    modifier = modifier,
    enabled = enabled,
    colors = colors,
    interactionSource = interactionSource,
  )
}

@Composable
fun CheckXMarkCheckbox(
  state: ToggleableState,
  onClick: (ToggleableState) -> Unit?,
  modifier: Modifier = Modifier,
  enabled: Boolean = true,
  colors: CheckboxColors = CheckboxDefaults.colors(),
  interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
  val toggleableState = listOf(
    ToggleableState.Off,
    ToggleableState.On,
    ToggleableState.Indeterminate,
  )
  var counter by remember(state) {
    mutableStateOf(toggleableState.indexOf(state))
  }
  TriStateCheckbox(
    state = toggleableState[counter % 3],
    onClick = {
      counter++
      onClick(toggleableState[counter % 3])
    },
    modifier = modifier,
    enabled = enabled,
    colors = colors,
    interactionSource = interactionSource,
  )
}

@Composable
fun TriStateCheckbox(
  state: ToggleableState,
  onClick: (() -> Unit)?,
  modifier: Modifier = Modifier,
  enabled: Boolean = true,
  colors: CheckboxColors = CheckboxDefaults.colors(),
  interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
  val toggleableModifier =
    if (onClick != null) {
      Modifier.triStateToggleable(
        state = state,
        onClick = onClick,
        enabled = enabled,
        role = Role.Checkbox,
        interactionSource = interactionSource,
        indication = rememberRipple(
          bounded = false,
          radius = CheckboxTokens.StateLayerSize / 2,
        ),
      )
    } else {
      Modifier
    }
  TriCheckboxImpl(
    enabled = enabled,
    value = state,
    modifier = modifier
      .then(
        if (onClick != null) {
          Modifier.minimumInteractiveComponentSize()
        } else {
          Modifier
        },
      )
      .then(toggleableModifier)
      .padding(CheckboxDefaultPadding),
    colors = colors,
  )
}

/**
 * Defaults used in [Checkbox] and [TriStateCheckbox].
 */
object CheckboxDefaults {
  /**
   * Creates a [CheckboxColors] that will animate between the provided colors according to the
   * Material specification.
   *
   * @param checkedColor the color that will be used for the border and box when checked
   * @param uncheckedColor color that will be used for the border when unchecked
   * @param checkmarkColor color that will be used for the checkmark when checked
   * @param disabledCheckedColor color that will be used for the box and border when disabled and
   * checked
   * @param disabledUncheckedColor color that will be used for the box and border when disabled
   * and not checked
   * @param disabledIndeterminateColor color that will be used for the box and
   * border in a [TriStateCheckbox] when disabled AND in an [ToggleableState.Indeterminate] state.
   */
  @Composable
  fun colors(
    checkedColor: Color =
      MaterialTheme.colorScheme.fromToken(CheckboxTokens.SelectedContainerColor),
    uncheckedColor: Color =
      MaterialTheme.colorScheme.fromToken(CheckboxTokens.UnselectedOutlineColor),
    checkmarkColor: Color =
      MaterialTheme.colorScheme.fromToken(CheckboxTokens.SelectedIconColor),
    disabledCheckedColor: Color =
      MaterialTheme.colorScheme
        .fromToken(CheckboxTokens.SelectedDisabledContainerColor)
        .copy(alpha = CheckboxTokens.SelectedDisabledContainerOpacity),
    disabledUncheckedColor: Color =
      MaterialTheme.colorScheme
        .fromToken(CheckboxTokens.UnselectedDisabledOutlineColor)
        .copy(alpha = CheckboxTokens.UnselectedDisabledContainerOpacity),
    disabledIndeterminateColor: Color = disabledCheckedColor,
  ): CheckboxColors = CheckboxColors(
    checkedBorderColor = checkedColor,
    checkedBoxColor = checkedColor,
    checkedCheckmarkColor = checkmarkColor,
    uncheckedCheckmarkColor = checkmarkColor.copy(alpha = 0f),
    uncheckedBoxColor = checkedColor.copy(alpha = 0f),
    disabledCheckedBoxColor = disabledCheckedColor,
    disabledUncheckedBoxColor = disabledUncheckedColor.copy(alpha = 0f),
    disabledIndeterminateBoxColor = disabledIndeterminateColor,
    uncheckedBorderColor = uncheckedColor,
    disabledBorderColor = disabledCheckedColor,
    disabledIndeterminateBorderColor = disabledIndeterminateColor,
  )
}

/**
 * Represents the colors used by the three different sections (checkmark, box, and border) of a
 * [Checkbox] or [TriStateCheckbox] in different states.
 *
 * See [CheckboxDefaults.colors] for the default implementation that follows Material
 * specifications.
 */
@Immutable
class CheckboxColors internal constructor(
  private val checkedCheckmarkColor: Color,
  private val uncheckedCheckmarkColor: Color,
  private val checkedBoxColor: Color,
  private val uncheckedBoxColor: Color,
  private val disabledCheckedBoxColor: Color,
  private val disabledUncheckedBoxColor: Color,
  private val disabledIndeterminateBoxColor: Color,
  private val checkedBorderColor: Color,
  private val uncheckedBorderColor: Color,
  private val disabledBorderColor: Color,
  private val disabledIndeterminateBorderColor: Color,
) {
  /**
   * Represents the color used for the checkmark inside the checkbox, depending on [state].
   *
   * @param state the [ToggleableState] of the checkbox
   */
  @Composable
  internal fun checkmarkColor(state: ToggleableState): State<Color> {
    val target = if (state == ToggleableState.Off) {
      uncheckedCheckmarkColor
    } else {
      checkedCheckmarkColor
    }

    val duration = if (state == ToggleableState.Off) BoxOutDuration else BoxInDuration
    return animateColorAsState(target, tween(durationMillis = duration))
  }

  /**
   * Represents the color used for the checkmark inside the x, depending on [state].
   *
   * @param state the [ToggleableState] of the checkbox
   */
  @Composable
  internal fun checkmarkColor(state: TriCheckboxState): State<Color> {
    val target = if (state == TriCheckboxState.Off) {
      uncheckedCheckmarkColor
    } else {
      checkedCheckmarkColor
    }

    val duration = if (state == TriCheckboxState.Off) BoxOutDuration else BoxInDuration
    return animateColorAsState(target, tween(durationMillis = duration))
  }

  /**
   * Represents the color used for the box (background) of the checkbox, depending on [enabled]
   * and [state].
   *
   * @param enabled whether the checkbox is enabled or not
   * @param state the [ToggleableState] of the checkbox
   */
  @Composable
  internal fun boxColor(enabled: Boolean, state: ToggleableState): State<Color> {
    val target = if (enabled) {
      when (state) {
        ToggleableState.On, ToggleableState.Indeterminate -> checkedBoxColor
        ToggleableState.Off -> uncheckedBoxColor
      }
    } else {
      when (state) {
        ToggleableState.On -> disabledCheckedBoxColor
        ToggleableState.Indeterminate -> disabledIndeterminateBoxColor
        ToggleableState.Off -> disabledUncheckedBoxColor
      }
    }

    // If not enabled 'snap' to the disabled state, as there should be no animations between
    // enabled / disabled.
    return if (enabled) {
      val duration = if (state == ToggleableState.Off) BoxOutDuration else BoxInDuration
      animateColorAsState(target, tween(durationMillis = duration))
    } else {
      rememberUpdatedState(target)
    }
  }

  /**
   * Represents the color used for the box (background) of the checkbox, depending on [enabled]
   * and [state].
   *
   * @param enabled whether the checkbox is enabled or not
   * @param state the [ToggleableState] of the checkbox
   */
  @Composable
  internal fun boxColor(enabled: Boolean, state: TriCheckboxState): State<Color> {
    val target = if (enabled) {
      when (state) {
        TriCheckboxState.Crossed, TriCheckboxState.Indeterminate -> checkedBoxColor
        TriCheckboxState.Checked, TriCheckboxState.Indeterminate -> checkedBoxColor
        TriCheckboxState.Off -> uncheckedBoxColor
      }
    } else {
      when (state) {
        TriCheckboxState.Crossed -> disabledCheckedBoxColor
        TriCheckboxState.Checked -> disabledCheckedBoxColor
        TriCheckboxState.Indeterminate -> disabledIndeterminateBoxColor
        TriCheckboxState.Off -> disabledUncheckedBoxColor
      }
    }

    // If not enabled 'snap' to the disabled state, as there should be no animations between
    // enabled / disabled.
    return if (enabled) {
      val duration = if (state == TriCheckboxState.Off) BoxOutDuration else BoxInDuration
      animateColorAsState(target, tween(durationMillis = duration))
    } else {
      rememberUpdatedState(target)
    }
  }

  /**
   * Represents the color used for the border of the checkbox, depending on [enabled] and [state].
   *
   * @param enabled whether the checkbox is enabled or not
   * @param state the [ToggleableState] of the checkbox
   */
  @Composable
  internal fun borderColor(enabled: Boolean, state: ToggleableState): State<Color> {
    val target = if (enabled) {
      when (state) {
        ToggleableState.On, ToggleableState.Indeterminate -> checkedBorderColor
        ToggleableState.Off -> uncheckedBorderColor
      }
    } else {
      when (state) {
        ToggleableState.Indeterminate -> disabledIndeterminateBorderColor
        ToggleableState.On, ToggleableState.Off -> disabledBorderColor
      }
    }

    // If not enabled 'snap' to the disabled state, as there should be no animations between
    // enabled / disabled.
    return if (enabled) {
      val duration = if (state == ToggleableState.Off) BoxOutDuration else BoxInDuration
      animateColorAsState(target, tween(durationMillis = duration))
    } else {
      rememberUpdatedState(target)
    }
  }

  /**
   * Represents the color used for the border of the checkbox, depending on [enabled] and [state].
   *
   * @param enabled whether the checkbox is enabled or not
   * @param state the [ToggleableState] of the checkbox
   */
  @Composable
  internal fun borderColor(enabled: Boolean, state: TriCheckboxState): State<Color> {
    val target = if (enabled) {
      when (state) {
        TriCheckboxState.Checked, TriCheckboxState.Indeterminate -> checkedBorderColor
        TriCheckboxState.Crossed, TriCheckboxState.Indeterminate -> checkedBorderColor
        TriCheckboxState.Off -> uncheckedBorderColor
      }
    } else {
      when (state) {
        TriCheckboxState.Indeterminate -> disabledIndeterminateBorderColor
        TriCheckboxState.Checked, Crossed, TriCheckboxState.Off -> disabledBorderColor
      }
    }

    // If not enabled 'snap' to the disabled state, as there should be no animations between
    // enabled / disabled.
    return if (enabled) {
      val duration = if (state == TriCheckboxState.Off) BoxOutDuration else BoxInDuration
      animateColorAsState(target, tween(durationMillis = duration))
    } else {
      rememberUpdatedState(target)
    }
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (other == null || other !is CheckboxColors) return false

    if (checkedCheckmarkColor != other.checkedCheckmarkColor) return false
    if (uncheckedCheckmarkColor != other.uncheckedCheckmarkColor) return false
    if (checkedBoxColor != other.checkedBoxColor) return false
    if (uncheckedBoxColor != other.uncheckedBoxColor) return false
    if (disabledCheckedBoxColor != other.disabledCheckedBoxColor) return false
    if (disabledUncheckedBoxColor != other.disabledUncheckedBoxColor) return false
    if (disabledIndeterminateBoxColor != other.disabledIndeterminateBoxColor) return false
    if (checkedBorderColor != other.checkedBorderColor) return false
    if (uncheckedBorderColor != other.uncheckedBorderColor) return false
    if (disabledBorderColor != other.disabledBorderColor) return false
    if (disabledIndeterminateBorderColor != other.disabledIndeterminateBorderColor) return false

    return true
  }

  override fun hashCode(): Int {
    var result = checkedCheckmarkColor.hashCode()
    result = 31 * result + uncheckedCheckmarkColor.hashCode()
    result = 31 * result + checkedBoxColor.hashCode()
    result = 31 * result + uncheckedBoxColor.hashCode()
    result = 31 * result + disabledCheckedBoxColor.hashCode()
    result = 31 * result + disabledUncheckedBoxColor.hashCode()
    result = 31 * result + disabledIndeterminateBoxColor.hashCode()
    result = 31 * result + checkedBorderColor.hashCode()
    result = 31 * result + uncheckedBorderColor.hashCode()
    result = 31 * result + disabledBorderColor.hashCode()
    result = 31 * result + disabledIndeterminateBorderColor.hashCode()
    return result
  }
}

private const val BoxInDuration = 50
private const val BoxOutDuration = 100
private const val CheckAnimationDuration = 100

// TODO(b/188529841): Update the padding and size when the Checkbox spec is finalized.
private val CheckboxDefaultPadding = 2.dp
private val CheckboxSize = 20.dp
private val StrokeWidth = 2.dp
private val RadiusSize = 2.dp

/**
 * Enum that represents possible toggleable states.
 */
enum class TriCheckboxState {
  /**
   * State that means a component is checked
   */
  Checked,

  /**
   * State that means a component is crossed
   */
  Crossed,

  /**
   * State that means a component is off
   */
  Off,

  /**
   * State that means that on/off value of a component cannot be determined
   */
  Indeterminate,
}

@Composable
private fun TriCheckboxImpl(
  enabled: Boolean,
  value: ToggleableState,
  modifier: Modifier,
  colors: CheckboxColors,
) {
  val transition = updateTransition(value, label = "checkbox::updateTransition")
  val checkDrawFraction = transition.animateFloat(
    transitionSpec = {
      when {
        initialState == ToggleableState.Off -> tween(CheckAnimationDuration)
        targetState == ToggleableState.Off -> snap(BoxOutDuration)
        else -> spring()
      }
    },
    label = "checkbox::animateFloat::checkDrawFraction",
  ) {
    when (it) {
      ToggleableState.On -> 1f
      ToggleableState.Off -> 0f
      ToggleableState.Indeterminate -> 1f
    }
  }

  val checkCenterGravitationShiftFraction = transition.animateFloat(
    transitionSpec = {
      when {
        initialState == ToggleableState.Off -> snap()
        targetState == ToggleableState.Off -> snap(BoxOutDuration)
        else -> tween(durationMillis = CheckAnimationDuration)
      }
    },
    label = "checkbox::animateFloat::checkCenterGravitationShiftFraction",
  ) {
    when (it) {
      ToggleableState.On -> 0f
      ToggleableState.Off -> 0f
      ToggleableState.Indeterminate -> 1f
    }
  }
  val checkCache = remember { CheckDrawingCache() }
  val xMarkCache = remember { XMarkDrawingCache() }
  val checkColor = colors.checkmarkColor(value)
  val boxColor = colors.boxColor(enabled, value)
  val borderColor = colors.borderColor(enabled, value)
  Canvas(
    modifier
      .wrapContentSize(Alignment.Center)
      .requiredSize(CheckboxSize),
  ) {
    val strokeWidthPx = floor(StrokeWidth.toPx())
    drawBox(
      boxColor = boxColor.value,
      borderColor = borderColor.value,
      radius = RadiusSize.toPx(),
      strokeWidth = strokeWidthPx,
    )
    if (checkCenterGravitationShiftFraction.value < .5f) {
      drawCheck(
        color = checkColor.value,
        fraction = checkDrawFraction.value,
        centerGravitation = checkCenterGravitationShiftFraction.value,
        strokeWidthPx = strokeWidthPx,
        drawingCache = checkCache,
      )
    } else {
      drawXMark(
        color = checkColor.value,
        fraction = checkDrawFraction.value,
        centerGravitation = checkCenterGravitationShiftFraction.value,
        strokeWidthPx = strokeWidthPx,
        drawingCache = xMarkCache,
      )
    }
  }
}

private fun DrawScope.drawBox(
  boxColor: Color,
  borderColor: Color,
  radius: Float,
  strokeWidth: Float,
) {
  val halfStrokeWidth = strokeWidth / 2.0f
  val stroke = Stroke(strokeWidth)
  val checkboxSize = size.width
  if (boxColor == borderColor) {
    drawRoundRect(
      boxColor,
      size = Size(checkboxSize, checkboxSize),
      cornerRadius = CornerRadius(radius),
      style = Fill,
    )
  } else {
    drawRoundRect(
      boxColor,
      topLeft = Offset(strokeWidth, strokeWidth),
      size = Size(checkboxSize - strokeWidth * 2, checkboxSize - strokeWidth * 2),
      cornerRadius = CornerRadius(max(0f, radius - strokeWidth)),
      style = Fill,
    )
    drawRoundRect(
      borderColor,
      topLeft = Offset(halfStrokeWidth, halfStrokeWidth),
      size = Size(checkboxSize - strokeWidth, checkboxSize - strokeWidth),
      cornerRadius = CornerRadius(radius - halfStrokeWidth),
      style = stroke,
    )
  }
}

private fun DrawScope.drawCheck(
  color: Color,
  fraction: Float,
  centerGravitation: Float,
  strokeWidthPx: Float,
  drawingCache: CheckDrawingCache,
) {
  val stroke = Stroke(width = strokeWidthPx, cap = StrokeCap.Square)
  val width = size.width
  val checkCrossX = 0.4f
  val checkCrossY = 0.7f
  val leftX = 0.2f
  val leftY = 0.5f
  val rightX = 0.8f
  val rightY = 0.3f

  val gravitatedCrossX = lerp(checkCrossX, 0.5f, centerGravitation)
  val gravitatedCrossY = lerp(checkCrossY, 0.5f, centerGravitation)
  // gravitate only Y for end to achieve center line
  val gravitatedLeftY = lerp(leftY, 0.5f, centerGravitation)
  val gravitatedRightY = lerp(rightY, 0.5f, centerGravitation)

  with(drawingCache) {
    checkPath.reset()
    checkPath.moveTo(width * leftX, width * gravitatedLeftY)
    checkPath.lineTo(width * gravitatedCrossX, width * gravitatedCrossY)
    checkPath.lineTo(width * rightX, width * gravitatedRightY)
    // TODO: replace with proper declarative non-android alternative when ready (b/158188351)
    pathMeasure.setPath(checkPath, false)
    pathToDraw.reset()
    pathMeasure.getSegment(
      0f,
      pathMeasure.length * fraction,
      pathToDraw,
      true,
    )
  }
  drawPath(drawingCache.pathToDraw, color, style = stroke)
}

private fun DrawScope.drawXMark(
  color: Color,
  fraction: Float,
  centerGravitation: Float,
  strokeWidthPx: Float,
  drawingCache: XMarkDrawingCache,
) {
  val stroke = Stroke(width = strokeWidthPx, cap = StrokeCap.Butt)
  val width = size.width
  val height = size.height
  val centerX = width / 2
  val centerY = height / 2
  val offsetFraction = 0.3f

  with(drawingCache) {
    xPath.reset()
    xPath.moveTo(centerX - width * offsetFraction, centerY - height * offsetFraction)
    xPath.lineTo(centerX + width * offsetFraction, centerY + height * offsetFraction)

    pathMeasure.setPath(xPath, false)
    pathToDraw.reset()
    pathMeasure.getSegment(
      0f,
      pathMeasure.length * fraction,
      pathToDraw,
      true,
    )
  }
  drawPath(drawingCache.pathToDraw, color, style = stroke)

  with(drawingCache) {
    xPath.reset()
    xPath.moveTo(centerX + width * offsetFraction, centerY - height * offsetFraction)
    xPath.lineTo(centerX - width * offsetFraction, centerY + height * offsetFraction)

    pathMeasure.setPath(xPath, false)
    pathToDraw.reset()
    pathMeasure.getSegment(
      0f,
      pathMeasure.length * fraction,
      pathToDraw,
      true,
    )
  }
  drawPath(drawingCache.pathToDraw, color, style = stroke)
}

@Immutable
private class CheckDrawingCache(
  val checkPath: Path = Path(),
  val pathMeasure: PathMeasure = PathMeasure(),
  val pathToDraw: Path = Path(),
)

@Immutable
private class XMarkDrawingCache(
  val xPath: Path = Path(),
  val pathMeasure: PathMeasure = PathMeasure(),
  val pathToDraw: Path = Path(),
)
