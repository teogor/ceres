package dev.teogor.ceres.models

/**
 * Extension class for configuring Room-related options.
 *
 * @property enableSchemaProvider Set to `true` to enable the RoomSchemaArgProvider.
 * @property schemasPath The relative path to the directory containing Room database schema files.
 *                      This path is used when configuring the RoomSchemaArgProvider and is relative
 *                      to the projectDir. The default value is "schemas."
 */
open class RoomOptionsExtension(
  var enableSchemaProvider: Boolean = false,
  var schemasPath: String = "schemas",
)
