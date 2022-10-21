pluginManagement {
  plugins {
    id("com.android.application") version "7.3.1" apply false
    id("com.android.library") version "7.3.1" apply false
    id("org.jetbrains.kotlin.android") version "1.6.21" apply false
    id("org.jetbrains.kotlin.jvm") version "1.6.21" apply false
    id("org.jetbrains.dokka") version "1.7.20"
  }
  repositories {
    gradlePluginPortal()
    google()
    mavenCentral()
  }
}

dependencyResolutionManagement {
  repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
  repositories {
    maven("https://jitpack.io")
    google()
    mavenCentral()
  }
}

rootProject.name = "Ceres"

include(":app")

include(":ceres-core")
include(":ceres-components")
include(":ceres-m3")
include(":ceres-ads")
include(":ceres-extensions")
include(":ceres-binding-adapter")
include(":ceres-firebase")
include(":ceres-widget")
include(":ceres-wear-os")
