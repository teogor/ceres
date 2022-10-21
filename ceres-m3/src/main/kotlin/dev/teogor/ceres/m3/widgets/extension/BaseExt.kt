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

package dev.teogor.ceres.m3.widgets.extension

import android.content.Context
import android.graphics.Color
import androidx.annotation.AttrRes
import androidx.core.content.res.use

@JvmOverloads
fun resolveColor(context: Context, @AttrRes attr: Int, fallback: Int = 0): Int {
  context.theme.obtainStyledAttributes(intArrayOf(attr)).use {
    return try {
      it.getColor(0, fallback)
    } catch (e: Exception) {
      Color.BLACK
    }
  }
}
