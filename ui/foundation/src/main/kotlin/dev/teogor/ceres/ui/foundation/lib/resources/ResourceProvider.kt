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

package dev.teogor.ceres.ui.foundation.lib.resources

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Suppress("MemberVisibilityCanBePrivate", "unused")
object ResourceProvider {

  val statusBarHeight: Dp
    @Composable
    get() {
      val context = LocalContext.current
      val density = LocalDensity.current.density
      val statusBarHeight = remember {
        val resourceId = context.getIdentifier(
          "status_bar_height",
          "dimen",
          "android",
        )
        if (resourceId > 0) {
          context.resources.getDimensionPixelSize(resourceId)
            .toFloat() / density
        } else {
          0f
        }
      }
      return statusBarHeight.dp
    }
}

@SuppressLint("DiscouragedApi")
fun Context.getIdentifier(
  name: String,
  defType: String,
  defPackage: String,
): Int = this.resources.getIdentifier(
  name,
  defType,
  defPackage,
)
