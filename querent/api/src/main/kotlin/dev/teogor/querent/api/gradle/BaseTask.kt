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

package dev.teogor.querent.api.gradle

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import dev.teogor.querent.api.models.PackageDetails
import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.OutputDirectory

abstract class BaseTask : DefaultTask() {

  @get:Input
  @get:Optional
  abstract val packageName: Property<String>

  @get:Input
  @get:Optional
  abstract val moduleName: Property<String>

  @get:OutputDirectory
  @get:Optional
  abstract val outputDir: DirectoryProperty

  @get:Input
  @get:Optional
  abstract val packageDetails: Property<PackageDetails>

  @get:Internal
  protected val type: PackageDetails
    get() = packageDetails.get()

  inline fun fileSpec(
    fileName: String,
    crossinline block: FileSpec.Builder.() -> Unit,
  ) {
    FileSpec.builder(packageName.get(), fileName)
      .apply(block)
      .build()
      .generate()
  }

  fun FileSpec.generate() {
    writeTo(outputDir.get().asFile)
  }

  infix fun String.type(typeName: String): ClassName {
    return ClassName(this, typeName)
  }
}
