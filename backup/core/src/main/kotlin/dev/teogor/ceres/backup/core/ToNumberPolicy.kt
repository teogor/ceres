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

package dev.teogor.ceres.backup.core

import com.google.gson.JsonParseException
import com.google.gson.ToNumberStrategy
import com.google.gson.internal.LazilyParsedNumber
import com.google.gson.stream.JsonReader
import com.google.gson.stream.MalformedJsonException
import java.io.IOException
import java.math.BigDecimal

/**
 * An enumeration that defines two standard number reading strategies and a couple of
 * strategies to overcome some historical Gson limitations while deserializing numbers as
 * [Object] and [Number].
 *
 * @see ToNumberStrategy
 */
enum class ToNumberPolicy : ToNumberStrategy {
  /**
   * Using this policy will ensure that numbers will be read as [Double] values.
   * This is the default strategy used during deserialization of numbers as [Object].
   */
  DOUBLE {
    @Throws(IOException::class)
    override fun readNumber(`in`: JsonReader): Double {
      return `in`.nextDouble()
    }
  },

  /**
   * Using this policy will ensure that numbers will be read as a lazily parsed number backed
   * by a string. This is the default strategy used during deserialization of numbers as
   * [Number].
   */
  LAZILY_PARSED_NUMBER {
    @Throws(IOException::class)
    override fun readNumber(`in`: JsonReader): Number {
      return LazilyParsedNumber(`in`.nextString())
    }
  },

  /**
   * Using this policy will ensure that numbers will be read as [Long] or [Double]
   * values depending on how JSON numbers are represented: `Long` if the JSON number can
   * be parsed as a `Long` value, or otherwise `Double` if it can be parsed as a
   * `Double` value. If the parsed double-precision number results in a positive or negative
   * infinity ([Double.isInfinite]) or a NaN ([Double.isNaN]) value and the
   * `JsonReader` is not [lenient][JsonReader.isLenient], a [MalformedJsonException]
   * is thrown.
   */
  LONG_OR_DOUBLE {
    @Throws(IOException::class, JsonParseException::class)
    override fun readNumber(`in`: JsonReader): Number {
      val value = `in`.nextString()
      return try {
        value.toLong()
      } catch (longE: NumberFormatException) {
        try {
          val d = java.lang.Double.valueOf(value)
          if ((d.isInfinite() || d.isNaN()) && !`in`.isLenient) {
            throw MalformedJsonException(
              "JSON forbids NaN and infinities: " + d + "; at path " + `in`.previousPath,
            )
          }
          d
        } catch (doubleE: NumberFormatException) {
          throw JsonParseException(
            "Cannot parse " + value + "; at path " + `in`.previousPath,
            doubleE,
          )
        }
      }
    }
  },

  /**
   * Using this policy will ensure that numbers will be read as numbers of arbitrary length
   * using [BigDecimal].
   */
  BIG_DECIMAL {
    @Throws(IOException::class)
    override fun readNumber(`in`: JsonReader): BigDecimal {
      val value = `in`.nextString()
      return try {
        BigDecimal(value)
      } catch (e: NumberFormatException) {
        throw JsonParseException("Cannot parse " + value + "; at path " + `in`.previousPath, e)
      }
    }
  },
}
