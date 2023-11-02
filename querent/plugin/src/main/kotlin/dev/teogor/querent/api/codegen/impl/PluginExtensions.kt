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

package dev.teogor.querent.api.codegen.impl

import dev.teogor.querent.api.codegen.Blueprint
import dev.teogor.querent.api.codegen.FoundationData
import org.gradle.api.Project
import kotlin.reflect.full.primaryConstructor

inline fun <reified Plugin : Blueprint> Project.initializePlugin() {
  val primaryConstructor = Plugin::class.primaryConstructor
  val validPlugin = if (primaryConstructor != null) {
    primaryConstructor.parameters.size == 1 && primaryConstructor.parameters[0].type.classifier == FoundationData::class
  } else {
    false
  }
  if (validPlugin && primaryConstructor != null) {
    val codeWriterImpl = CodeWriterImpl(this)
    val initializationData = FoundationData(
      project = project,
      codeWriter = codeWriterImpl,
    )
    val pluginInstance = primaryConstructor.call(initializationData)
    pluginInstance.onCreate()
  } else {
    val expectedSignature =
      "class ${Plugin::class.simpleName}(data: FoundationData) : Blueprint(data)"
    error("Invalid plugin declaration. Expected: $expectedSignature")
  }
}
