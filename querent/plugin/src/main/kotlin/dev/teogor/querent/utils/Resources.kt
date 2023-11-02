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

package dev.teogor.querent.utils

import kotlinx.serialization.Serializable

interface Resources

/**
 * <resources>
 *     <string name="hello">Hello!</string>
 * </resources>
 */
@Serializable
data class StringValue(
  val id: String,
  val placeholders: List<Char> = emptyList(),
  val args: List<String> = emptyList(),
) : Resources {
  override fun toString() =
    "string $id ${placeholders.joinToString("") { it.toString() }} ${args.joinToString(";") { it }}"
}

/**
 * <resources>
 *     <string-array name="planets_array">
 *         <item>Mercury</item>
 *         <item>Venus</item>
 *         <item>Earth</item>
 *         <item>Mars</item>
 *     </string-array>
 * </resources>
 */
@Serializable
data class StringArray(
  val id: String,
) : Resources {
  override fun toString() = "array $id"
}

/**
 * <resources>
 *     <plurals name="numberOfSongsAvailable">
 *         <!--
 *              As a developer, you should always supply "one" and "other"
 *              strings. Your translators will know which strings are actually
 *              needed for their language. Always include %d in "one" because
 *              translators will need to use %d for languages where "one"
 *              doesn't mean 1 (as explained above).
 *           -->
 *         <item quantity="one">%d song found.</item>
 *         <item quantity="other">%d songs found.</item>
 *     </plurals>
 * </resources>
 */
@Serializable
data class QuantityStrings(
  val id: String,
) : Resources {
  override fun toString() = "plurals $id"
}

@Serializable
data class Values(
  val default: Boolean = false,
  val elements: List<Resources>,
  val code: String,
)
