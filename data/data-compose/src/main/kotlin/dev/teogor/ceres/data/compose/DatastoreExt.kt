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

package dev.teogor.ceres.data.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import kotlinx.coroutines.flow.Flow

/**
 * Remembers the value of a preference flow and updates it whenever the flow emits a new value.
 *
 * This function is useful for composing UIs that need to react to changes in preferences. It
 * ensures that the UI state remains consistent with the latest preference value.
 *
 * TODO Migrate to package .datastore for enhanced data storage capabilities.
 *
 * @param preferenceFlow The flow of preference values to observe.
 * @param initialValue The initial value to use before the flow emits a value.
 * @return The current value of the preference flow.
 */
@Composable
inline fun <reified T> rememberPreference(
  preferenceFlow: Flow<T>,
  initialValue: T,
): T {
  val preferenceState = remember { mutableStateOf(initialValue) }

  LaunchedEffect(preferenceFlow) {
    preferenceFlow.collect { newValue ->
      preferenceState.value = newValue
    }
  }

  return preferenceState.value
}
