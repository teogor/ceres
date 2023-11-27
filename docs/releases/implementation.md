[//]: # (This file was automatically generated - do not edit)

## Implementation

### Latest Version

The latest release is [`1.0.0-alpha04`](../releases.md)

### BoM Releases

The BoM (Bill of Materials) is the central repository for managing library versions within the
Ceres project. It streamlines the process of tracking the latest versions of key components and
dependencies, ensuring that your project remains up-to-date and compatible with the latest
advancements.

Here's a summary of the latest BoM versions:

|    Version    |               Release Notes                | Release Date |
|:-------------:|:------------------------------------------:|:------------:|
| 1.0.0-alpha04 | [changelog 🔗](changelog/1.0.0-alpha04.md) | 23 Nov 2023 |
| 1.0.0-alpha03 | [changelog 🔗](changelog/1.0.0-alpha03.md) | 06 Oct 2023 |
| 1.0.0-alpha02 | [changelog 🔗](changelog/1.0.0-alpha02.md) | 02 Oct 2023 |
| 1.0.0-alpha01 | [changelog 🔗](changelog/1.0.0-alpha01.md) | 28 Sept 2023 |

### Using Version Catalog

#### Declare Components

This catalog provides the implementation details of Ceres libraries, including Build of
Materials (BoM) and individual libraries, in TOML format.

=== "Default"

    ```toml title="gradle/libs.versions.toml"
    [versions]
    ceres-backup = "1.0.0-alpha01"
    ceres-core = "1.0.0-alpha04"
    ceres-data = "1.0.0-alpha02"
    ceres-firebase = "1.0.0-alpha04"
    ceres-framework = "1.0.0-alpha04"
    ceres-monetisation = "1.0.0-alpha04"
    ceres-navigation = "1.0.0-alpha03"
    ceres-navigation = "0.0.0"
    ceres-screen = "1.0.0-alpha04"
    ceres-ui = "1.0.0-alpha04"

    [libraries]
    ceres-backup-core = { group = "dev.teogor.ceres", name = "backup-core", version.ref = "ceres-backup" }
    ceres-backup-ui = { group = "dev.teogor.ceres", name = "backup-ui", version.ref = "ceres-backup" }
    ceres-core-analytics = { group = "dev.teogor.ceres", name = "core-analytics", version.ref = "ceres-core" }
    ceres-core-common = { group = "dev.teogor.ceres", name = "core-common", version.ref = "ceres-core" }
    ceres-core-foundation = { group = "dev.teogor.ceres", name = "core-foundation", version.ref = "ceres-core" }
    ceres-core-network = { group = "dev.teogor.ceres", name = "core-network", version.ref = "ceres-core" }
    ceres-core-notifications = { group = "dev.teogor.ceres", name = "core-notifications", version.ref = "ceres-core" }
    ceres-core-register = { group = "dev.teogor.ceres", name = "core-register", version.ref = "ceres-core" }
    ceres-core-runtime = { group = "dev.teogor.ceres", name = "core-runtime", version.ref = "ceres-core" }
    ceres-core-startup = { group = "dev.teogor.ceres", name = "core-startup", version.ref = "ceres-core" }
    ceres-data-compose = { group = "dev.teogor.ceres", name = "data-compose", version.ref = "ceres-data" }
    ceres-data-database = { group = "dev.teogor.ceres", name = "data-database", version.ref = "ceres-data" }
    ceres-data-datastore = { group = "dev.teogor.ceres", name = "data-datastore", version.ref = "ceres-data" }
    ceres-firebase-analytics = { group = "dev.teogor.ceres", name = "firebase-analytics", version.ref = "ceres-firebase" }
    ceres-firebase-crashlytics = { group = "dev.teogor.ceres", name = "firebase-crashlytics", version.ref = "ceres-firebase" }
    ceres-firebase-remote-config = { group = "dev.teogor.ceres", name = "firebase-remoteconfig", version.ref = "ceres-firebase" }
    ceres-framework-core = { group = "dev.teogor.ceres", name = "framework-core", version.ref = "ceres-framework" }
    ceres-framework-ui = { group = "dev.teogor.ceres", name = "framework-ui", version.ref = "ceres-framework" }
    ceres-monetisation-admob = { group = "dev.teogor.ceres", name = "monetisation-admob", version.ref = "ceres-monetisation" }
    ceres-monetisation-ads = { group = "dev.teogor.ceres", name = "monetisation-ads", version.ref = "ceres-monetisation" }
    ceres-monetisation-messaging = { group = "dev.teogor.ceres", name = "monetisation-messaging", version.ref = "ceres-monetisation" }
    ceres-navigation-common = { group = "dev.teogor.ceres", name = "navigation-common", version.ref = "ceres-navigation" }
    ceres-navigation-core = { group = "dev.teogor.ceres", name = "navigation-core", version.ref = "ceres-navigation" }
    ceres-navigation-events = { group = "dev.teogor.ceres", name = "navigation-events", version.ref = "ceres-navigation" }
    ceres-navigation-screen = { group = "dev.teogor.ceres", name = "navigation-screen", version.ref = "ceres-navigation" }
    ceres-navigation-ui = { group = "dev.teogor.ceres", name = "navigation-ui", version.ref = "ceres-navigation" }
    ceres-screen-builder = { group = "dev.teogor.ceres", name = "screen-builder", version.ref = "ceres-screen" }
    ceres-screen-core = { group = "dev.teogor.ceres", name = "screen-core", version.ref = "ceres-screen" }
    ceres-screen-ui = { group = "dev.teogor.ceres", name = "screen-ui", version.ref = "ceres-screen" }
    ceres-ui-compose = { group = "dev.teogor.ceres", name = "ui-compose", version.ref = "ceres-ui" }
    ceres-ui-design-system = { group = "dev.teogor.ceres", name = "ui-design.system", version.ref = "ceres-ui" }
    ceres-ui-foundation = { group = "dev.teogor.ceres", name = "ui-foundation", version.ref = "ceres-ui" }
    ceres-ui-icons = { group = "dev.teogor.ceres", name = "ui-icons", version.ref = "ceres-ui" }
    ceres-ui-spectrum = { group = "dev.teogor.ceres", name = "ui-spectrum", version.ref = "ceres-ui" }
    ceres-ui-theme = { group = "dev.teogor.ceres", name = "ui-theme", version.ref = "ceres-ui" }
    ```

=== "Using BoM"

    ```toml title="gradle/libs.versions.toml"
    [versions]
    ceres-bom = "1.0.0-alpha04"

    [libraries]
    ceres-bom = { group = "dev.teogor.ceres", name = "bom", version.ref = "ceres-bom" }
    ceres-backup-core = { group = "dev.teogor.ceres", name = "backup-core" }
    ceres-backup-ui = { group = "dev.teogor.ceres", name = "backup-ui" }
    ceres-core-analytics = { group = "dev.teogor.ceres", name = "core-analytics" }
    ceres-core-common = { group = "dev.teogor.ceres", name = "core-common" }
    ceres-core-foundation = { group = "dev.teogor.ceres", name = "core-foundation" }
    ceres-core-network = { group = "dev.teogor.ceres", name = "core-network" }
    ceres-core-notifications = { group = "dev.teogor.ceres", name = "core-notifications" }
    ceres-core-register = { group = "dev.teogor.ceres", name = "core-register" }
    ceres-core-runtime = { group = "dev.teogor.ceres", name = "core-runtime" }
    ceres-core-startup = { group = "dev.teogor.ceres", name = "core-startup" }
    ceres-data-compose = { group = "dev.teogor.ceres", name = "data-compose" }
    ceres-data-database = { group = "dev.teogor.ceres", name = "data-database" }
    ceres-data-datastore = { group = "dev.teogor.ceres", name = "data-datastore" }
    ceres-firebase-analytics = { group = "dev.teogor.ceres", name = "firebase-analytics" }
    ceres-firebase-crashlytics = { group = "dev.teogor.ceres", name = "firebase-crashlytics" }
    ceres-firebase-remote-config = { group = "dev.teogor.ceres", name = "firebase-remoteconfig" }
    ceres-framework-core = { group = "dev.teogor.ceres", name = "framework-core" }
    ceres-framework-ui = { group = "dev.teogor.ceres", name = "framework-ui" }
    ceres-monetisation-admob = { group = "dev.teogor.ceres", name = "monetisation-admob" }
    ceres-monetisation-ads = { group = "dev.teogor.ceres", name = "monetisation-ads" }
    ceres-monetisation-messaging = { group = "dev.teogor.ceres", name = "monetisation-messaging" }
    ceres-navigation-common = { group = "dev.teogor.ceres", name = "navigation-common" }
    ceres-navigation-core = { group = "dev.teogor.ceres", name = "navigation-core" }
    ceres-navigation-events = { group = "dev.teogor.ceres", name = "navigation-events" }
    ceres-navigation-screen = { group = "dev.teogor.ceres", name = "navigation-screen" }
    ceres-navigation-ui = { group = "dev.teogor.ceres", name = "navigation-ui" }
    ceres-screen-builder = { group = "dev.teogor.ceres", name = "screen-builder" }
    ceres-screen-core = { group = "dev.teogor.ceres", name = "screen-core" }
    ceres-screen-ui = { group = "dev.teogor.ceres", name = "screen-ui" }
    ceres-ui-compose = { group = "dev.teogor.ceres", name = "ui-compose" }
    ceres-ui-design-system = { group = "dev.teogor.ceres", name = "ui-design.system" }
    ceres-ui-foundation = { group = "dev.teogor.ceres", name = "ui-foundation" }
    ceres-ui-icons = { group = "dev.teogor.ceres", name = "ui-icons" }
    ceres-ui-spectrum = { group = "dev.teogor.ceres", name = "ui-spectrum" }
    ceres-ui-theme = { group = "dev.teogor.ceres", name = "ui-theme" }
    ```

#### Dependencies Implementation

=== "Kotlin"

    ```kotlin title="build.gradle.kts"
    dependencies {
      // When Using Ceres BoM
      implementation(platform(libs.ceres.bom))

      // Ceres Libraries
      implementation(libs.ceres.backup.core)
      implementation(libs.ceres.backup.ui)
      implementation(libs.ceres.core.analytics)
      implementation(libs.ceres.core.common)
      implementation(libs.ceres.core.foundation)
      implementation(libs.ceres.core.network)
      implementation(libs.ceres.core.notifications)
      implementation(libs.ceres.core.register)
      implementation(libs.ceres.core.runtime)
      implementation(libs.ceres.core.startup)
      implementation(libs.ceres.data.compose)
      implementation(libs.ceres.data.database)
      implementation(libs.ceres.data.datastore)
      implementation(libs.ceres.firebase.analytics)
      implementation(libs.ceres.firebase.crashlytics)
      implementation(libs.ceres.firebase.remote.config)
      implementation(libs.ceres.framework.core)
      implementation(libs.ceres.framework.ui)
      implementation(libs.ceres.monetisation.admob)
      implementation(libs.ceres.monetisation.ads)
      implementation(libs.ceres.monetisation.messaging)
      implementation(libs.ceres.navigation.common)
      implementation(libs.ceres.navigation.core)
      implementation(libs.ceres.navigation.events)
      implementation(libs.ceres.navigation.screen)
      implementation(libs.ceres.navigation.ui)
      implementation(libs.ceres.screen.builder)
      implementation(libs.ceres.screen.core)
      implementation(libs.ceres.screen.ui)
      implementation(libs.ceres.ui.compose)
      implementation(libs.ceres.ui.design.system)
      implementation(libs.ceres.ui.foundation)
      implementation(libs.ceres.ui.icons)
      implementation(libs.ceres.ui.spectrum)
      implementation(libs.ceres.ui.theme)
    }
    ```

=== "Groovy"

    ```groovy title="build.gradle"
    dependencies {
      // When Using Ceres BoM
      implementation platform(libs.ceres.bom)

      // Ceres Libraries
      implementation(libs.ceres.backup.core)
      implementation(libs.ceres.backup.ui)
      implementation(libs.ceres.core.analytics)
      implementation(libs.ceres.core.common)
      implementation(libs.ceres.core.foundation)
      implementation(libs.ceres.core.network)
      implementation(libs.ceres.core.notifications)
      implementation(libs.ceres.core.register)
      implementation(libs.ceres.core.runtime)
      implementation(libs.ceres.core.startup)
      implementation(libs.ceres.data.compose)
      implementation(libs.ceres.data.database)
      implementation(libs.ceres.data.datastore)
      implementation(libs.ceres.firebase.analytics)
      implementation(libs.ceres.firebase.crashlytics)
      implementation(libs.ceres.firebase.remote.config)
      implementation(libs.ceres.framework.core)
      implementation(libs.ceres.framework.ui)
      implementation(libs.ceres.monetisation.admob)
      implementation(libs.ceres.monetisation.ads)
      implementation(libs.ceres.monetisation.messaging)
      implementation(libs.ceres.navigation.common)
      implementation(libs.ceres.navigation.core)
      implementation(libs.ceres.navigation.events)
      implementation(libs.ceres.navigation.screen)
      implementation(libs.ceres.navigation.ui)
      implementation(libs.ceres.screen.builder)
      implementation(libs.ceres.screen.core)
      implementation(libs.ceres.screen.ui)
      implementation(libs.ceres.ui.compose)
      implementation(libs.ceres.ui.design.system)
      implementation(libs.ceres.ui.foundation)
      implementation(libs.ceres.ui.icons)
      implementation(libs.ceres.ui.spectrum)
      implementation(libs.ceres.ui.theme)
    }
    ```
