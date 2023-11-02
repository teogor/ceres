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

package dev.teogor.querent.structures

import com.android.build.api.dsl.CommonExtension
import com.android.build.api.variant.Variant
import dev.teogor.querent.api.codegen.Blueprint
import dev.teogor.querent.api.codegen.FoundationData
import dev.teogor.querent.api.impl.QuerentConfiguratorExtension
import dev.teogor.querent.api.impl.generateTagWithRegion
import dev.teogor.querent.api.models.PackageDetails
import dev.teogor.querent.tasks.GenerateLocaleConfigTask
import dev.teogor.querent.tasks.GenerateSupportedLocalesTask
import dev.teogor.querent.tasks.SoakConfiguredLocalesTask
import dev.teogor.querent.utils.dependencies
import org.gradle.configurationcache.extensions.capitalized
import org.gradle.kotlin.dsl.project
import org.gradle.kotlin.dsl.register

/**
 * https://developer.android.com/guide/topics/resources/app-languages
 */
class LanguagesSchema(data: FoundationData) : Blueprint(data) {

  private val buildOptions by lazy { extension<QuerentConfiguratorExtension>()!! }
  private val buildFeatures by lazy { buildOptions.buildFeatures }
  private val languagesSchemaOptions by lazy { buildOptions.languagesSchemaOptions }
  private var rawResourceConfig = mutableSetOf<String>()

  override val packageNameSuffix: String
    get() = "lang"

  override fun apply() {
    super.apply()

    project.dependencies {
      add("implementation", project(":core:register"))
      add("implementation", project(":querent:languages-schema"))
    }
  }

  override fun isEnabled() = buildFeatures.languagesSchema

  override fun CommonExtension<*, *, *, *, *>.finalizeDsl() {
    rawResourceConfig = defaultConfig.resourceConfigurations.toMutableSet()
  }

  override fun onVariants(variant: Variant) {
    super.onVariants(variant)

    val kotlin = kotlinSources
    val res = resSources
    var variantResourceConfig = rawResourceConfig.toMutableSet()

    val packageDetails = PackageDetails(
      packageName = packageName,
      namespace = namespace,
    )

    if (!variant.pseudoLocalesEnabled.getOrElse(false)) {
      variantResourceConfig = variantResourceConfig.subtract(
        setOf("en-rXA", "ar-rXB"),
      ).toMutableSet()
    }

    val languageListTaskProvider = project.tasks.register<SoakConfiguredLocalesTask>(
      "soakConfiguredLocales${variant.name.capitalized()}",
    ) {
      resourceConfigInput.set(variantResourceConfig)
      languageTagListOutput.set(intermediates.file("${variant.name}/soaked_locale_list.txt"))
    }

    val generateLocaleConfigTaskProvider = project.tasks.register<GenerateLocaleConfigTask>(
      "generateLocaleConfig${variant.name.capitalized()}",
    ) {
      languageListInput.set(languageListTaskProvider.flatMap { it.languageTagListOutput })
      localeConfigOutput.set(
        res.file("/xml/locale_config.xml"),
      )
    }

    val generateSupportedLocalesTaskProvider = project.tasks.register<GenerateSupportedLocalesTask>(
      "generateSupportedLocales${variant.name.capitalized()}",
    ) {
      languageListInput.set(languageListTaskProvider.flatMap { it.languageTagListOutput })
      outputDir.set(kotlin)
      packageName.set(this@LanguagesSchema.packageName)
      defaultLocaleProperty.set(
        languagesSchemaOptions.unqualifiedResLocale.asLanguageTag().generateTagWithRegion(),
      )
      this.packageDetails.set(packageDetails)
    }

    project.afterEvaluate {
      project.tasks.named("pre${variant.name.capitalized()}Build") {
        dependsOn(generateLocaleConfigTaskProvider)
        dependsOn(generateSupportedLocalesTaskProvider)
      }
    }
  }
}
