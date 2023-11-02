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

import com.squareup.kotlinpoet.ClassName
import dev.teogor.querent.api.models.PackageDetails

internal val PackageDetails.R: ClassName
  get() = ClassName(namespace, "R")

internal val PackageDetails.Linguistic: ClassName
  get() = ClassName(namespace, "Linguistic")

internal val PackageDetails.LanguageDialect: ClassName
  get() = ClassName(namespace, "LanguageDialect")

internal val PackageDetails.Country: ClassName
  get() = ClassName(namespace, "Country")

internal val PackageDetails.Language: ClassName
  get() = ClassName(namespace, "Language")

internal val Composable by lazy {
  ClassName("androidx.compose.runtime", "Composable")
}

internal val StringResource by lazy {
  ClassName("dev.teogor.ceres.core.foundation.compositions", "stringResource")
}

internal val StringArrayResource by lazy {
  ClassName("dev.teogor.ceres.core.foundation.compositions", "stringArrayResource")
}

internal val QuantityStringResource by lazy {
  ClassName("dev.teogor.ceres.core.foundation.compositions", "quantityStringResource")
}
