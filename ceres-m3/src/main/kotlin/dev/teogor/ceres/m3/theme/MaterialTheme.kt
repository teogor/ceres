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

package dev.teogor.ceres.m3.theme

object MaterialTheme {
  val TONE_VALUES = intArrayOf(0, 10, 20, 30, 40, 50, 60, 70, 80, 90, 95, 99, 100)
  private const val TONE_ACCENT_LIGHT = 40
  private const val TONE_ON_ACCENT_LIGHT = 100
  private const val TONE_ACCENT_CONTAINER_LIGHT = 90
  private const val TONE_ON_ACCENT_CONTAINER_LIGHT = 10
  private const val TONE_ACCENT_DARK = 70
  private const val TONE_ON_ACCENT_DARK = 10
  private const val TONE_ACCENT_CONTAINER_DARK = 20
  private const val TONE_ON_ACCENT_CONTAINER_DARK = 80
  private val COLOR_TYPES = arrayOf(
    "Primary",
    "Secondary",
    "Tertiary",
    "Error"
  )
  private val ATTRIBUTE_NAME_FORMAT = arrayOf(
    "color%s",
    "colorOn%s",
    "color%sContainer",
    "colorOn%sContainer"
  )
  private val NAME_FORMAT = arrayOf(
    "%s",
    "on%s",
    "%sContainer",
    "on%sContainer"
  )
  private val TONE_LIGHT = intArrayOf(
    TONE_ACCENT_LIGHT,
    TONE_ON_ACCENT_LIGHT,
    TONE_ACCENT_CONTAINER_LIGHT,
    TONE_ON_ACCENT_CONTAINER_LIGHT
  )
  private val TONE_DARK = intArrayOf(
    TONE_ACCENT_DARK,
    TONE_ON_ACCENT_DARK,
    TONE_ACCENT_CONTAINER_DARK,
    TONE_ON_ACCENT_CONTAINER_DARK
  )
  val COLORS: MutableList<Color> = ArrayList()
  val TEXT_COLORS = arrayOf(
    "colorOnPrimary",
    "colorOnPrimaryContainer",
    "colorOnSecondary",
    "colorOnSecondaryContainer",
    "colorOnTertiary",
    "colorOnTertiaryContainer",
    "colorOnError",
    "colorOnErrorContainer",
    "colorOnBackground",
    "colorOnSurface",
    "colorOnSurfaceVariant"
  )
  val TEXT_COLOR_EMPHASIS = arrayOf(
    "",
    "High",
    "Medium"
  )

  init {
    for (i in ATTRIBUTE_NAME_FORMAT.indices) {
      COLORS.add(
        Color(
          NAME_FORMAT[i],
          ATTRIBUTE_NAME_FORMAT[i],
          TONE_LIGHT[i],
          TONE_DARK[i]
        )
      )
    }
  }

  class Color(
    private val nameFormat: String,
    private val attributeNameFormat: String,
    val toneLight: Int,
    val toneDark: Int
  ) {

    fun getAttributeName(name: String?): String {
      return String.format(attributeNameFormat, name)
    }

    fun getName(name: String?): String {
      return String.format(nameFormat, name)
    }
  }
}
