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

package dev.teogor.querent.tasks

import org.gradle.api.DefaultTask
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction
import org.redundent.kotlin.xml.Namespace
import org.redundent.kotlin.xml.PrintOptions
import org.redundent.kotlin.xml.xml

/**
 * Generates a `locale_config.xml` file to be referenced in an Android project Manifest for
 * configuring application locale support. Debug and Release versions are added to their respective
 * source sets.
 */
abstract class GenerateLocaleConfigTask : DefaultTask() {
  @get:InputFile
  abstract val languageListInput: RegularFileProperty

  @get:OutputFile
  abstract val localeConfigOutput: RegularFileProperty

  @TaskAction
  fun taskAction() {
    val langList = languageListInput.get().asFile.reader()

    val xml = xml("locale-config") {
      globalProcessingInstruction("xml", "version" to "1.0", "encoding" to "utf-8")

      val namespace = Namespace("android", "http://schemas.android.com/apk/res/android")
      namespace(namespace)

      langList.forEachLine {
        val line = it.split(',')

        comment(line.last())
        element("locale") {
          attribute("name", line.first(), namespace)
        }
      }
    }

    localeConfigOutput.get().asFile.writeText(
      xml.toString(PrintOptions(pretty = true, singleLineTextElements = true)),
    )

    logger.info("`locale_config.xml` output to ${localeConfigOutput.get()}")
  }
}
