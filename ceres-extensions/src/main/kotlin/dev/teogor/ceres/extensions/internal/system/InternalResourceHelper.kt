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

package dev.teogor.ceres.extensions.internal.system

import android.content.Context
import android.content.res.Resources

object InternalResourceHelper {
  fun getBoolean(context: Context, resName: String, defaultValue: Boolean): Boolean {
    val id = getResourceId(resName, "bool")
    return if (id != 0) {
      context.createPackageContext("android", 0).resources.getBoolean(id)
    } else {
      defaultValue
    }
  }

  /**
   * Get resource id from system resources
   * @param resName resource name to get
   * @param type resource type of [resName] to get
   * @return 0 if not available
   */
  private fun getResourceId(resName: String, type: String): Int {
    return Resources.getSystem().getIdentifier(resName, type, "android")
  }
}
