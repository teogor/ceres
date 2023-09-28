package dev.teogor.ceres.models

// The content for the app can either come from local static data which is useful for demo
// purposes, or from a production backend server which supplies up-to-date, real content.
// These two product flavors reflect this behaviour.
@Suppress("EnumEntryName")
enum class CeresFlavor(val dimension: FlavorDimension, val applicationIdSuffix: String? = null) {
  demo(FlavorDimension.contentType, applicationIdSuffix = ".demo"),
  prod(FlavorDimension.contentType),
}
