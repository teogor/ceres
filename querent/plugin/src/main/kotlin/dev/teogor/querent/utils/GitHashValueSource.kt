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

import org.gradle.api.provider.ValueSource
import org.gradle.api.provider.ValueSourceParameters
import org.gradle.process.ExecOperations
import java.io.ByteArrayOutputStream
import java.nio.charset.Charset
import javax.inject.Inject

abstract class GitHashValueSource : ValueSource<String, ValueSourceParameters.None> {

  @get:Inject
  abstract val execOperations: ExecOperations

  // Define a variable to store the Git hash
  private var gitHash: String? = null

  override fun obtain(): String {
    // If gitHash is already calculated, return it
    gitHash?.let { return it }

    val output = ByteArrayOutputStream()
    return try {
      execOperations.exec {
        commandLine("git", "rev-parse", "HEAD")
        standardOutput = output
      }
      // Calculate and store the Git hash
      gitHash = String(output.toByteArray(), Charset.defaultCharset()).trim()
      gitHash ?: "N/A"
    } catch (e: RuntimeException) {
      "N/A"
    }
  }
}
