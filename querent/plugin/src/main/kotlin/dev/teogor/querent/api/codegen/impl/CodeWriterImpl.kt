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

import dev.teogor.querent.api.codegen.CodeWriter
import dev.teogor.querent.api.utils.dir
import org.gradle.api.Project
import org.gradle.api.file.Directory
import org.gradle.api.provider.Provider

private const val libName: String = "querent"

data class CodeWriterImpl(
  override val project: Project,
) : CodeWriter {
  override val sourceOutputDir: Provider<Directory>
    get() = project.layout.buildDirectory.dir("generated/$libName/")
  override val intermediatesOutputDir: Provider<Directory>
    get() = project.layout.buildDirectory.dir("intermediates/$libName/")

  override fun getOutputDir(
    name: String,
  ): Directory = sourceOutputDir.dir(name)

  override fun setEnabled(enabled: Boolean) {
    if (!enabled) {
      sourceOutputDir.get().asFile.deleteRecursively()
    }
  }
}
