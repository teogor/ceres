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

package dev.teogor.ceres.ui.foundation

import android.media.AudioManager
import androidx.compose.foundation.Indication
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.platform.inspectable
import androidx.compose.ui.semantics.Role
import dev.teogor.ceres.core.foundation.HapticEffect
import dev.teogor.ceres.core.foundation.audioManagerUtils
import dev.teogor.ceres.core.foundation.vibratorUtils
import dev.teogor.ceres.ui.foundation.config.FeedbackConfig

fun Modifier.clickable(
  enabled: Boolean = true,
  onClickLabel: String? = null,
  role: Role? = null,
  effectType: Int = AudioManager.FX_KEY_CLICK,
  onClick: () -> Unit,
) = composed {
  val context = LocalContext.current
  clickable(
    enabled = enabled,
    onClickLabel = onClickLabel,
    role = role,
    onClick = {
      onClick()

      if (!FeedbackConfig.disableAudioFeedback) {
        context.audioManagerUtils().playSoundEffect(effectType)
      }

      if (!FeedbackConfig.disableVibrationFeedback) {
        context.vibratorUtils().vibrate(
          milliseconds = FeedbackConfig.vibrationFeedbackIntensity,
          effect = HapticEffect.DEFAULT_AMPLITUDE,
        )
      }
    },
  )
}

fun Modifier.clickable(
  interactionSource: MutableInteractionSource,
  indication: Indication?,
  enabled: Boolean = true,
  onClickLabel: String? = null,
  role: Role? = null,
  effectType: Int = AudioManager.FX_KEY_CLICK,
  onClick: () -> Unit,
) = composed {
  val context = LocalContext.current
  clickable(
    interactionSource = interactionSource,
    indication = indication,
    enabled = enabled,
    onClickLabel = onClickLabel,
    role = role,
    onClick = {
      onClick()

      if (!FeedbackConfig.disableAudioFeedback) {
        context.audioManagerUtils().playSoundEffect(effectType)
      }

      if (!FeedbackConfig.disableVibrationFeedback) {
        context.vibratorUtils().vibrate(
          milliseconds = FeedbackConfig.vibrationFeedbackIntensity,
          effect = HapticEffect.DEFAULT_AMPLITUDE,
        )
      }
    },
  )
}

fun Modifier.rippleClickable(
  rippleColor: Color? = null,
  onClick: () -> Unit,
) = composed(
  inspectorInfo = debugInspectorInfo {
    name = "clickable"
    properties["rippleColor"] = rippleColor
    properties["onClick"] = onClick
  },
) {
  clickable(
    onClick = onClick,
    indication = rippleColor?.let {
      rememberRipple(
        color = it,
      )
    } ?: LocalIndication.current,
    interactionSource = remember { MutableInteractionSource() },
  )
}

fun Modifier.sideClickable(
  onClick: (Boolean) -> Unit,
) = inspectable(
  inspectorInfo = debugInspectorInfo {
    name = "sideClickable"
    properties["onClick"] = onClick
  },
) {
  Modifier.pointerInput(Unit) {
    detectTapGestures { offset ->
      val centerX = size.width / 2
      val isLeft = offset.x < centerX
      onClick(isLeft)
    }
  }
}

fun Modifier.applyIf(condition: Boolean, block: Modifier.() -> Modifier): Modifier {
  return if (condition) {
    this.then(block())
  } else {
    this
  }
}
