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

// BoM
include(":bom")

// Backup
include(":backup:core")
include(":backup:ui")

// Core
include(":core:analytics")
include(":core:common")
include(":core:foundation")
include(":core:network")
include(":core:notifications")
include(":core:register")
include(":core:runtime")
include(":core:startup")

// Data
include(":data:compose")
include(":data:database")
include(":data:datastore")

// Firebase
include(":firebase:analytics")
include(":firebase:crashlytics")
include(":firebase:remote-config")

// Framework
include(":framework:core")
include(":framework:ui")

// Monetisation
include(":monetisation:admob")
include(":monetisation:ads")
include(":monetisation:messaging")

// Navigation
include(":navigation:common")
include(":navigation:core")
include(":navigation:events")
include(":navigation:screen")
include(":navigation:ui")

// Screen
include(":screen:builder")
include(":screen:core")
include(":screen:ui")

// UI
include(":ui:compose")
include(":ui:designsystem")
include(":ui:foundation")
include(":ui:icons")
include(":ui:spectrum")
include(":ui:theme")
