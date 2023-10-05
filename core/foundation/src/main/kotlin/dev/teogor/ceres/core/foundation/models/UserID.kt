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

package dev.teogor.ceres.core.foundation.models

import java.util.UUID
import java.util.regex.Pattern

data class UserID(
  var value: String = "",
) {
  init {
    if (isEmpty()) {
      generate()
    }
  }

  fun isEmpty(): Boolean {
    return value.isEmpty()
  }

  fun generate(): String {
    var userID = UUID.randomUUID().toString() + UUID.randomUUID().toString()
    userID = userID.replace(regex = "-".toRegex(), replacement = "")
    val p = Pattern.compile(
      "(.{" + 4 + "})",
      Pattern.DOTALL,
    )
    val m = p.matcher(userID)
    userID = m.replaceAll("$1" + ":")
    value = userID.substring(0, userID.length - 1)
    return value
  }
}

fun UserID.getPrivacyFormattedValue(elementCount: Int = 4): String {
  val parts = value.split(":")
  val displayedParts = parts.take(elementCount)
  return displayedParts.joinToString(":")
}

fun UserID.getFormattedValueInRows(): String {
  val parts = value.split(":")
  val rows = parts.chunked(4)
  return rows.joinToString("\n") { it.joinToString(":") }
}
