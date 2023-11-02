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

import com.android.build.api.variant.Variant
import dev.teogor.querent.api.codegen.Blueprint
import dev.teogor.querent.api.codegen.FoundationData
import dev.teogor.querent.api.impl.QuerentConfiguratorExtension
import dev.teogor.querent.api.models.PackageDetails
import dev.teogor.querent.tasks.GenerateValuesTask
import dev.teogor.querent.tasks.SoakConfiguredValuesTask
import dev.teogor.querent.utils.QuantityStrings
import dev.teogor.querent.utils.StringArray
import dev.teogor.querent.utils.StringValue
import dev.teogor.querent.utils.Values
import dev.teogor.querent.utils.bind
import dev.teogor.querent.utils.isDefault
import dev.teogor.querent.utils.resources
import dev.teogor.querent.utils.strings
import dev.teogor.querent.utils.values
import dev.teogor.querent.utils.variant
import org.gradle.configurationcache.extensions.capitalized
import org.gradle.kotlin.dsl.register

/**
 * https://developer.android.com/guide/topics/resources/string-resource
 * https://developer.android.com/guide/topics/resources/available-resources
 */
class XmlResources(data: FoundationData) : Blueprint(data) {

  private val buildOptions by lazy { extension<QuerentConfiguratorExtension>()!! }
  private val buildFeatures by lazy { buildOptions.buildFeatures }

  override val packageNameSuffix: String
    get() = "res"

  override fun isEnabled() = buildFeatures.xmlResources

  override fun onVariants(variant: Variant) {
    super.onVariants(variant)

    val kotlin = kotlinSources

    val packageDetails = PackageDetails(
      packageName = packageName,
      namespace = namespace,
    )

    val languageListTaskProvider = project.tasks.register<SoakConfiguredValuesTask>(
      "soakConfiguredValues${variant.name.capitalized()}",
    ) {
      val resValues = mutableListOf<Values>()
      project.values {
        strings?.resources {
          val resources = bind<StringValue>() + bind<StringArray>() + bind<QuantityStrings>()
          resValues.add(
            Values(
              default = isDefault,
              elements = resources,
              code = this@values.variant,
            ),
          )
        }
      }
      val defaultResourceValue = resValues.firstOrNull { it.default }
      val elements = defaultResourceValue?.elements ?: emptyList()
      val finalResourceValues = elements.map { it.toString() }

      resourceValues.set(finalResourceValues)
      languageTagListOutput.set(intermediates.file("${variant.name}/soaked_values_list.txt"))
    }

    val generateSupportedLocalesTaskProvider = project.tasks.register<GenerateValuesTask>(
      "generateValues${variant.name.capitalized()}",
    ) {
      valuesListInput.set(languageListTaskProvider.flatMap { it.languageTagListOutput })
      outputDir.set(kotlin)
      moduleName.set(project.name)
      packageName.set(this@XmlResources.packageName)
      this.packageDetails.set(packageDetails)
    }

    project.afterEvaluate {
      project.tasks.named("pre${variant.name.capitalized()}Build") {
        dependsOn(generateSupportedLocalesTaskProvider)
      }
    }
  }
}
