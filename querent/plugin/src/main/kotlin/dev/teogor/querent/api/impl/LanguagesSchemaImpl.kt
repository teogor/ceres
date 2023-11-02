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

package dev.teogor.querent.api.impl

import com.android.build.gradle.BaseExtension
import dev.teogor.linguistic.Country
import dev.teogor.linguistic.Dialect
import dev.teogor.linguistic.Language
import dev.teogor.linguistic.LanguageFamily
import dev.teogor.linguistic.LanguageTag
import dev.teogor.linguistic.territorialize
import dev.teogor.querent.api.LanguagesSchema
import dev.teogor.querent.api.SupportedLanguages
import org.gradle.api.Project

open class LanguagesSchemaImpl(
  private val project: Project,
) : LanguagesSchema {
  private val _supportedLanguages: MutableList<LanguageFamily> = mutableListOf()

  private val androidBaseExtension: BaseExtension by lazy {
    project.extensions.getByType(BaseExtension::class.java)
  }

  override var unqualifiedResLocale: LanguageFamily =
    Language.English territorialize Country.UnitedStates
    set(value) {
      field = value
      addSupportedLanguages { +value }
    }

  override val supportedLanguages: List<String>
    get() = _supportedLanguages.map { it.asLanguageTag().generateTagWithRegion() }

  override fun addSupportedLanguages(block: SupportedLanguages.() -> Unit) {
    val builder = SupportedLanguagesImpl()
    builder.block()
    val languages = builder.getLanguages()
    _supportedLanguages += languages
    androidBaseExtension.defaultConfig.resourceConfigurations.addAll(
      languages.map {
        it.asLanguageTag().generateTagWithRegion()
      },
    )
  }
}

fun LanguageTag.generateTagWithRegion(): String {
  return when (languageFamily) {
    is Language -> (languageFamily as Language).code
    is Dialect -> "${(languageFamily as Dialect).language.code}-r${(languageFamily as Dialect).country.code}"
    else -> tag
  }
}
