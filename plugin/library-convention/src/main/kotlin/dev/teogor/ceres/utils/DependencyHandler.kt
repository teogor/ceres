package dev.teogor.ceres.utils

import dev.teogor.ceres.models.LibrarySpec
import java.util.Optional
import org.gradle.api.artifacts.MinimalExternalModuleDependency
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.logging.Logger
import org.gradle.api.provider.Provider
import org.gradle.kotlin.dsl.DependencyHandlerScope

fun VersionCatalog.findLibrary(
  librarySpec: LibrarySpec,
): Optional<Provider<MinimalExternalModuleDependency>> {
  return findLibrary(librarySpec.name)
}

fun DependencyHandlerScope.add(
  dependencies: List<LibrarySpec>,
  logger: Logger,
  libs: VersionCatalog,
) {
  dependencies.forEach { libraryDependency ->
    val library = libs.findLibrary(libraryDependency)
    if (!library.isPresent) {
      logger.error("Library alias named \"${libraryDependency.name}\" not found in the version catalog. Please ensure it's properly defined.")

      libs.resolveLibraryDependency(libraryDependency.name)?.let {
        logger.warn("Found library with name \"${it.first}\". Using recommended alias \"${libraryDependency.name}\" is encouraged for consistency and clarity.")
        add(libraryDependency, it.second)
      }
    } else {
      add(libraryDependency, library.get().get())
    }
  }
}

fun DependencyHandlerScope.add(
  librarySpec: LibrarySpec,
  moduleProvider: MinimalExternalModuleDependency,
) {
  librarySpec.dependencyTypes.forEach {
    if (librarySpec.isBom) {
      add(
        configurationName = it.gradleNotation,
        dependencyNotation = platform(moduleProvider),
      )
    } else {
      add(
        configurationName = it.gradleNotation,
        dependencyNotation = moduleProvider,
      )
    }
  }
}

fun VersionCatalog.resolveLibraryDependency(module: String): Pair<String, MinimalExternalModuleDependency>? {
  val regex = "[._-]".toRegex()
  return libraryAliases
    .map { libraryAlias ->
      Pair(libraryAlias, findLibrary(libraryAlias).get().getOrNull())
    }
    .filter { it.second != null }
    .map { it.first to it.second!! }
    .firstOrNull {
      regex.replace(it.second.module.name, ".") == module
    }
}
