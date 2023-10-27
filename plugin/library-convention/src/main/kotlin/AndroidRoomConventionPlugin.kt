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

import com.google.devtools.ksp.gradle.KspExtension
import dev.teogor.ceres.models.RoomOptionsExtension
import java.io.File
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.PathSensitive
import org.gradle.api.tasks.PathSensitivity
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.gradle.process.CommandLineArgumentProvider

class AndroidRoomConventionPlugin : Plugin<Project> {

  override fun apply(target: Project) {
    val roomOptions = target.extensions.create<RoomOptionsExtension>(
      name = "roomOptions",
    )

    with(target) {
      pluginManager.apply("com.google.devtools.ksp")

      afterEvaluate {
        extensions.configure<KspExtension> {
          // The schemas directory contains a schema file for each version of the Room database.
          // This is required to enable Room auto migrations.
          // See https://developer.android.com/reference/kotlin/androidx/room/AutoMigration.
          if(roomOptions.enableSchemaProvider) {
            arg(RoomSchemaArgProvider(File(projectDir, roomOptions.schemasPath)))
          }
        }
      }

      val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
      dependencies {
        add("implementation", libs.findLibrary("room.runtime").get())
        add("implementation", libs.findLibrary("room.ktx").get())
        add("ksp", libs.findLibrary("room.compiler").get())
      }
    }
  }

  /**
   * https://issuetracker.google.com/issues/132245929
   * [Export schemas](https://developer.android.com/training/data-storage/room/migrating-db-versions#export-schemas)
   */
  class RoomSchemaArgProvider(
    @get:InputDirectory
    @get:PathSensitive(PathSensitivity.RELATIVE)
    val schemaDir: File,
  ) : CommandLineArgumentProvider {
    init {
      if (!schemaDir.exists()) {
        val created = schemaDir.mkdirs()
        if (created) {
          println("Created directory: ${schemaDir.absolutePath}")
        } else {
          println("Failed to create directory: ${schemaDir.absolutePath}")
        }
      }
    }

    override fun asArguments() = listOf("room.schemaLocation=${schemaDir.path}")
  }
}
