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
plugins {
  id("dev.teogor.ceres.android.library")
  id("dev.teogor.ceres.android.library.jacoco")
  id("dev.teogor.ceres.android.hilt")
  id("kotlinx-serialization")
  alias(libs.plugins.winds)

  alias(libs.plugins.protobuf)
}

android {
  namespace = "dev.teogor.ceres.data.datastore"
  defaultConfig {
    consumerProguardFiles("proguard-rules.pro")
  }
}

// Setup protobuf configuration, generating lite Java and Kotlin classes
protobuf {
  protoc {
    artifact = libs.protobuf.protoc.get().toString()
  }
  generateProtoTasks {
    all().forEach { task ->
      task.builtins {
        register("java") {
          option("lite")
        }
        register("kotlin") {
          option("lite")
        }
      }
    }
  }
}

dependencies {
  api(project(":core:startup"))
  api(project(":core:foundation"))

  api(libs.androidx.dataStore.preferences)
  api(libs.androidx.dataStore.core)
  api(libs.androidx.lifecycle.livedata.ktx)
  api(libs.protobuf.kotlin.lite)

  implementation(libs.kotlinx.coroutines.android)
  implementation(libs.gson)
}

winds {
  mavenPublish {
    displayName = "Datastore"
    name = "datastore"
  }
}
