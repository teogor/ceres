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

package dev.teogor.ceres.core.logger

import android.util.Log

interface Logger {

  /**
   * Send a DEBUG log message with [content].
   *
   * The **tag** will be the calling class simple name in square
   * brackets.
   *
   * Example: `[ClassSimpleName]`
   *
   * @param content The content you would like logged.
   */
  fun log(content: String) {
    Log.d("[${this.javaClass.simpleName}]", content)
  }
}
