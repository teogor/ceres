package dev.teogor.ceres.gradle.plugins

open class CeresModuleExtension {
  var artifactIdPrefix: String? = null
  var version: String? = null

  internal var isBomModule: Boolean = false
}

fun CeresModuleExtension.setIsBomModule(
  predicate: Boolean = true,
) = apply {
  isBomModule = predicate
}

fun CeresModuleExtension.setModuleCoordinates(
  artifactIdPrefix: String,
  version: String,
) = apply {
  this.artifactIdPrefix = artifactIdPrefix
  this.version = version
}
