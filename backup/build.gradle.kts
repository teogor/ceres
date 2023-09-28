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
import com.vanniktech.maven.publish.SonatypeHost
import dev.teogor.ceres.gradle.plugins.CeresLibraryExtension
import dev.teogor.ceres.gradle.plugins.setModuleCoordinates

plugins {
  id("dev.teogor.ceres.module")
}

ceresModule {
  setModuleCoordinates(
    artifactIdPrefix = "backup",
    version = "1.0.0-alpha01",
  )
}

subprojects {
  afterEvaluate {
    val ceresLibrary = project.extensions.getByType(CeresLibraryExtension::class.java)
    mavenPublishing {
      publishToMavenCentral(SonatypeHost.S01)
      signAllPublications()

      @Suppress("UnstableApiUsage")
      pom {
        coordinates(
          groupId = ceresLibrary.groupId,
          artifactId = ceresLibrary.artifactId!!,
          version = ceresLibrary.version!!,
        )
        ceresLibrary.applyToMaven(this)
      }
    }
  }
}
