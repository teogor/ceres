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
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.semantics.Role

/**
 * A modifier that adds a click listener to a composable, but limits the clicks
 * to a single click within a specified time frame.
 *
 * @param enabled whether the click listener is enabled or not.
 * @param onClickLabel a label for the click action.
 * @param role a role for the composable.
 * @param onClick the action to be performed on click.
 *
 * @return a composed modifier with a clickable listener that is limited to a
 *         single click within a specified time frame.
 */
fun Modifier.clickableSingle(
  enabled: Boolean = true,
  onClickLabel: String? = null,
  role: Role? = null,
  effectType: Int = AudioManager.FX_KEY_CLICK,
  onClick: () -> Unit,
) = composed(
  inspectorInfo = debugInspectorInfo {
    name = "clickable"
    properties["enabled"] = enabled
    properties["onClickLabel"] = onClickLabel
    properties["role"] = role
    properties["onClick"] = onClick
  },
) {
  val multipleEventsCutter = remember { MultipleEventsCutterImpl() }
  Modifier.clickable(
    enabled = enabled,
    onClickLabel = onClickLabel,
    onClick = {
      multipleEventsCutter.processEvent {
        onClick()
      }
    },
    role = role,
    indication = LocalIndication.current,
    interactionSource = remember { MutableInteractionSource() },
    effectType = effectType,
  )
}

/**
 * An interface for classes that implement event processing for multiple events.
 */
internal interface MultipleEventsCutter {

  /**
   * Processes a given event if it has been long enough since the last event was processed.
   *
   * @param event the event to be processed.
   */
  fun processEvent(event: () -> Unit)
}

/**
 * An implementation of the MultipleEventsCutter interface that limits the events to a single event within a specified time frame.
 */
internal class MultipleEventsCutterImpl : MultipleEventsCutter {

  /**
   * Gets the current system time in nanoseconds.
   *
   * @return the current system time in nanoseconds.
   */
  private val nanoTime: Long
    get() = System.nanoTime()

  private var lastEventTimeNano: Long = 0

  /**
   * Processes a given event if it has been long enough since the last event was processed.
   *
   * @param event the event to be processed.
   */
  override fun processEvent(event: () -> Unit) {
    val now = nanoTime
    if (now - lastEventTimeNano >= 300_000_000L) {
      event.invoke()
    }
    lastEventTimeNano = now
  }
}
