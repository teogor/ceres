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

package dev.teogor.ceres

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.ApplicationProductFlavor
import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.ProductFlavor
import dev.teogor.ceres.models.CeresFlavor
import dev.teogor.ceres.models.FlavorDimension
import dev.teogor.ceres.utils.getBooleanProperty
import org.gradle.api.Project

fun Project.configureFlavors(
  commonExtension: CommonExtension<*, *, *, *, *>,
  flavorConfigurationBlock: ProductFlavor.(flavor: CeresFlavor) -> Unit = {},
) {
  val flavoursEnabled = getBooleanProperty(
    key = "ceres.buildfeatures.flavours",
    defaultValue = true,
  )
  if (flavoursEnabled) {
    commonExtension.apply {
      flavorDimensions += FlavorDimension.contentType.name
      productFlavors {
        CeresFlavor.values().forEach {
          create(it.name) {
            dimension = it.dimension.name
            flavorConfigurationBlock(this, it)
            if (this@apply is ApplicationExtension && this is ApplicationProductFlavor) {
              if (it.applicationIdSuffix != null) {
                this.applicationIdSuffix = it.applicationIdSuffix
              }
            }
          }
        }
      }
    }
  }
}
