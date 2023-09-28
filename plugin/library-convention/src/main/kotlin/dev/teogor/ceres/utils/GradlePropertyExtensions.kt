package dev.teogor.ceres.utils

import org.gradle.api.Project

fun Project.getBooleanProperty(
  key: String,
  defaultValue: Boolean = true,
): Boolean {
  val propertyValue = findProperty(key)?.toString()?.toBoolean()
  return propertyValue ?: defaultValue
}

fun Project.getStringProperty(
  key: String,
  defaultValue: String = "",
): String {
  return findProperty(key)?.toString() ?: defaultValue
}

fun Project.getIntProperty(
  key: String,
  defaultValue: Int = 0,
): Int {
  val propertyValue = findProperty(key)?.toString()?.toIntOrNull()
  return propertyValue ?: defaultValue
}

fun Project.getLongProperty(
  key: String,
  defaultValue: Long = 0L,
): Long {
  val propertyValue = findProperty(key)?.toString()?.toLongOrNull()
  return propertyValue ?: defaultValue
}
