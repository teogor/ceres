/*
 * Copyright 2023 teogor (Teodor Grigor)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
