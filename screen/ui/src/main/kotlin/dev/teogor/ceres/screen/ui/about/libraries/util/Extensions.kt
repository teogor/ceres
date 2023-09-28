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

package dev.teogor.ceres.screen.ui.about.libraries.util

import androidx.compose.runtime.Immutable
import com.mikepenz.aboutlibraries.entity.Library
import com.mikepenz.aboutlibraries.entity.License
import kotlinx.collections.immutable.toImmutableList

@JvmInline
@Immutable
value class StableLibrary(val library: Library)

val Library.stable get() = StableLibrary(this)

val List<Library>.stable get() = map { it.stable }.toImmutableList()

val Library.author: String
  get() = developers.takeIf {
    it.isNotEmpty()
  }?.map { it.name }?.joinToString(", ") ?: organization?.name ?: ""

val License.htmlReadyLicenseContent: String?
  get() = licenseContent?.replace("\n", "<br />")

val License.strippedLicenseContent: String?
  get() = licenseContent?.replace("<br />", "\n")?.replace("<br/>", "\n")
