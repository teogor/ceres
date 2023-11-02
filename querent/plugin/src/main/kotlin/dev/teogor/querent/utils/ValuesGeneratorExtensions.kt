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

import org.gradle.api.Project
import org.jdom2.Element
import org.jdom2.input.SAXBuilder
import java.io.File
import java.io.StringReader

typealias ValueFolder = File
typealias StringFile = File

val ValueFolder.strings: StringFile?
  get() {
    val stringsFile = File(this, "strings.xml")
    return if (stringsFile.exists()) {
      stringsFile
    } else {
      null
    }
  }

val ValueFolder.isDefault: Boolean
  get() = name == "values"

val ValueFolder.variant: String
  get() = name.substringAfter("values-")

inline fun StringFile.resources(
  crossinline block: Element.() -> Unit,
) {
  val builder = SAXBuilder()
  val xml = builder.build(StringReader(readText()))
  val rootNode = xml.rootElement
  block(rootNode)
}

fun String.getPlaceholders(): List<Char> {
  val regex = Regex("%(\\d+)\\\$[sdfbt]")
  val matches = regex.findAll(this)

  val placeholderTypes = mutableListOf<Char>()
  matches.forEach { match ->
    placeholderTypes.add(match.value.last())
  }
  return placeholderTypes
}

inline fun <reified T : Resources> Element.bind(): List<T> {
  return when (T::class) {
    StringValue::class -> getChildren("string").map {
      val args = it.getAttributeValue("args")?.split(";") ?: emptyList()
      val placeholders = it.value.getPlaceholders()
      StringValue(id = it.getAttributeValue("name"), placeholders, args) as T
    }

    StringArray::class -> getChildren("string-array").map {
      StringArray(id = it.getAttributeValue("name")) as T
    }

    QuantityStrings::class -> getChildren("plurals").map {
      QuantityStrings(id = it.getAttributeValue("name")) as T
    }

    else -> throw IllegalArgumentException("Unknown resource type")
  }
}

fun Project.values(): List<ValueFolder> {
  val valuesDir = project.layout.projectDirectory.dir("/src/main/res/").asFile
  if (valuesDir.exists() && valuesDir.isDirectory) {
    return valuesDir.listFiles { file ->
      file.isDirectory && file.name.startsWith("values")
    }?.toList() ?: emptyList()
  }

  return emptyList()
}

inline fun Project.values(
  crossinline block: ValueFolder.() -> Unit,
) {
  values().forEach { block(it) }
}
