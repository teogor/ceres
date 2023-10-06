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

package dev.teogor.ceres.core.common.utils

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

/**
 * Provides an [Activity] instance for use within a Composable.
 *
 * @return the [Activity] associated with the current [Context], or null if not found.
 */
val activity: Activity?
  @Composable
  get() = LocalContext.current.getActivity()

/**
 * Helper function to retrieve an [Activity] from a [Context].
 *
 * @return the [Activity] associated with the [Context], or null if not found.
 */
fun Context.getActivity(): Activity? = when (this) {
  is Activity -> this
  is ContextWrapper -> baseContext.getActivity()
  else -> null
}
