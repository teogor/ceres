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

package dev.teogor.ceres.extensions

import android.app.Activity
import android.os.Bundle
import androidx.annotation.IdRes
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment

fun FragmentActivity.findNavController(
  @IdRes viewId: Int
): NavController? {
  return if (viewId.validIdRes) {
    val navHostFragment = this.supportFragmentManager.findFragmentById(viewId) as NavHostFragment
    navHostFragment.navController
  } else {
    null
  }
}

fun Activity.hasIntentExtras(
  extrasRun: Bundle.() -> Unit = {}
) {
  this.intent.extras?.let {
    extrasRun(it)
  }
}

fun Activity.extrasBoolean(
  key: String,
  defaultValue: Boolean = false,
  extrasRun: Boolean.() -> Unit = {}
) {
  this.hasIntentExtras {
    getBoolean(key, defaultValue).apply {
      extrasRun()
    }
  }
}
