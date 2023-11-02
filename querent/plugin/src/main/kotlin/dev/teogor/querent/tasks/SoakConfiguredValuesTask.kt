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

import dev.teogor.querent.api.gradle.BaseTask
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.ListProperty
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction

abstract class SoakConfiguredValuesTask : BaseTask() {
  @get:Input
  abstract val resourceValues: ListProperty<String>

  @get:OutputFile
  abstract val languageTagListOutput: RegularFileProperty

  @TaskAction
  fun taskAction() {
    val file = languageTagListOutput.get().asFile
    file.bufferedWriter().use { writer ->
      writer.appendLine("R_DEF: Internal format may change without notice.")
      resourceValues.get().sorted().forEach {
        writer.appendLine(it)
      }
    }

    logger.info("Valid supported locales output to ${languageTagListOutput.get()}")
  }
}
