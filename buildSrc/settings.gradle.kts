dependencyResolutionManagement {
  versionCatalogs {
    create("libs") {
      from(files("${rootDir.parentFile}/gradle/libs.versions.toml"))
    }
  }
}
