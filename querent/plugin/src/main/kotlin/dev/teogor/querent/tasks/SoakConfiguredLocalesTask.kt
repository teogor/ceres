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
import org.gradle.api.provider.SetProperty
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction
import java.util.Locale

/**
 * Reads in a Gradle project's 'resourceConfiguration' items and outputs a list of found language tags and
 * their endonyms (the language's display name for those who read/write it). The output is used in further code
 * generation tasks and does not appear in your source files.
 */
abstract class SoakConfiguredLocalesTask : DefaultTask() {
  @get:Input
  abstract val resourceConfigInput: SetProperty<String>

  @get:OutputFile
  abstract val languageTagListOutput: RegularFileProperty

  @TaskAction
  fun taskAction() {
    val supportedLanguageTags = resourceConfigInput.getOrElse(setOf())
      .convertToUnicodeLanguageTags()
      .stripInvalidLanguageTags()
      .map { it.plusEndonym() }
      .toSet()
      .sorted()

    languageTagListOutput.get().asFile.writeText(
      supportedLanguageTags.joinToString("\n"),
    )

    logger.info("Valid supported locales output to ${languageTagListOutput.get()}")
  }

  /**
   * Converts all language tags to Unicode-friendly tags that work more consistently with the java [Locale] class.
   */
  private fun Set<String>.convertToUnicodeLanguageTags(): List<String> =
    map { tag ->
      tag.removePrefix("b+")
        .replace('+', '-')
        .replace("-r", "-")
    }

  /**
   * Checks the validity of each found resource tag
   * @return Only resource configurations that are language tags
   */
  private fun List<String>.stripInvalidLanguageTags(): List<String> =
    filter { it.isIsoValid() }

  /**
   * @return True/false if the provided tag is ISO valid for languages
   *
   * Example - 'fr-FR' is a language tag and is valid
   *
   * Example - 'sw320dp' is a screen dimension tag and is not valid
   */
  private fun String.isIsoValid(): Boolean {
    // prevents stripping of pseudo-locales
    if (this == "en-XA" || this == "ar-XB") {
      return true
    }

    val parts = split('-')
    val one = parts.getOrNull(0)
    val two = parts.getOrNull(1)
    val three = parts.getOrNull(2)

    one?.let {
      if (it !in Locale.getISOLanguages()) {
        return false
      }
    }

    two?.let {
      if ((it.length != 4) &&
        (it !in Locale.getISOCountries())
      ) {
        return false
      }
    }

    three?.let {
      if (it !in Locale.getISOCountries()) {
        return false
      }
    }

    return true
  }

  /**
   * @return language tag with endonym, comma-separated
   */
  private fun String.plusEndonym(): String {
    val locale = Locale.forLanguageTag(this)
    return "$this,${locale.getDisplayName(locale)}"
  }
}
