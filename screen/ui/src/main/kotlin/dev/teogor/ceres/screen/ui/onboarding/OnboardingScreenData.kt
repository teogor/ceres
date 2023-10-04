package dev.teogor.ceres.screen.ui.onboarding

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

data class Permission(
  val title: String = "%%TITLE%%",
  val description: String = "%%DESCRIPTION%%",
  val manifestPermission: String = "%%MANIFEST_PERMISSION%%",
  val permissionRequired: Boolean = false,
  val rationaleContent: String? = null,
) {
  var isGranted: Boolean = false
}

data class PermissionCategory(
  val name: String = "%%NAME%%",
  val permissions: List<Permission> = listOf(Permission()),
)
