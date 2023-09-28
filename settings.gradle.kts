pluginManagement {
    includeBuild("plugin/")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
@Suppress("UnstableApiUsage")
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Ceres"

// App
include(":app")

// BOM
include(":bom")

// Navigation
include(":navigation:common")
include(":navigation:core")
include(":navigation:events")
include(":navigation:screen")
include(":navigation:ui")

// Firebase
include(":firebase:analytics")
include(":firebase:crashlytics")
include(":firebase:remote-config")

// Backup
include(":backup:core")
include(":backup:ui")

// Data
include(":data:compose")
include(":data:database")
include(":data:datastore")

// UI
include(":ui:compose")
include(":ui:designsystem")
include(":ui:foundation")
include(":ui:icons")
include(":ui:spectrum")
include(":ui:theme")

// Core
include(":core:runtime")
include(":core:network")
include(":core:notifications")
include(":core:startup")

// Framework
include(":framework:core")
include(":framework:ui")

// Screen
include(":screen:core")
include(":screen:builder")
include(":screen:ui")

// Monetisation
include(":monetisation:admob")
include(":monetisation:messaging")
