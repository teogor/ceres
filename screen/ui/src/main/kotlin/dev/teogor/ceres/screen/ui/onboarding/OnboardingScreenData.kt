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

package dev.teogor.ceres.screen.ui.onboarding

import dev.teogor.ceres.screen.ui.api.ExperimentalOnboardingScreenApi

@ExperimentalOnboardingScreenApi
data class OnboardingScreenData(
  val appName: String = "%%APP_NAME%%",
  val description: String = "%%DESCRIPTION%%",
  val supportEmail: String = "%%SUPPORT_EMAIL%%",
  val privacyPolicyLink: String = "%%PRIVACY_POLICY_LINK%%",
  val termsOfServiceLink: String = "%%TERMS_OF_SERVICE_LINK%%",
  val permissionCategories: List<PermissionCategory> = listOf(PermissionCategory()),
) {
  fun areAllPermissionsGranted(): Boolean {
    return permissionCategories.all { category ->
      category.permissions
        .filter { it.permissionRequired } // Filter only required permissions
        .all { permission ->
          permission.isGranted
        }
    }
  }
}

@ExperimentalOnboardingScreenApi
data class Permission(
  val title: String = "%%TITLE%%",
  val description: String = "%%DESCRIPTION%%",
  val manifestPermission: String = "%%MANIFEST_PERMISSION%%",
  val permissionRequired: Boolean = false,
  val rationaleContent: String? = null,
) {
  var isGranted: Boolean = false
}

@ExperimentalOnboardingScreenApi
data class PermissionCategory(
  val name: String = "%%NAME%%",
  val permissions: List<Permission> = listOf(Permission()),
)
