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
| 1.0.0-alpha04 | [changelog 🔗](changelog/1.0.0-alpha04.md) | 22 Nov 2023 |
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
    ceres-core = "1.0.0-alpha03"
    ceres-core = "0.0.0"
    ceres-data = "1.0.0-alpha02"
    ceres-firebase = "1.0.0-alpha03"
    ceres-framework = "1.0.0-alpha03"
    ceres-monetisation = "1.0.0-alpha03"
    ceres-navigation = "1.0.0-alpha02"
    ceres-navigation = "0.0.0"
    ceres-screen = "1.0.0-alpha03"
    ceres-ui = "1.0.0-alpha03"

    [libraries]
    ceres-backup-core = { group = "dev.teogor.ceres", name = "ceres-backup-core", version.ref = "ceres-backup" }
    ceres-backup-ui = { group = "dev.teogor.ceres", name = "ceres-backup-ui", version.ref = "ceres-backup" }
    ceres-core-analytics = { group = "dev.teogor.ceres", name = "ceres-core-analytics", version.ref = "ceres-core" }
    ceres-core-common = { group = "dev.teogor.ceres", name = "ceres-core-common", version.ref = "ceres-core" }
    ceres-core-foundation = { group = "dev.teogor.ceres", name = "ceres-core-foundation", version.ref = "ceres-core" }
    ceres-core-network = { group = "dev.teogor.ceres", name = "ceres-core-network", version.ref = "ceres-core" }
    ceres-core-notifications = { group = "dev.teogor.ceres", name = "ceres-core-notifications", version.ref = "ceres-core" }
    ceres-core-register = { group = "dev.teogor.ceres", name = "ceres-core-register", version.ref = "ceres-core" }
    ceres-core-runtime = { group = "dev.teogor.ceres", name = "ceres-core-runtime", version.ref = "ceres-core" }
    ceres-core-startup = { group = "dev.teogor.ceres", name = "ceres-core-startup", version.ref = "ceres-core" }
    ceres-data-compose = { group = "dev.teogor.ceres", name = "ceres-data-compose", version.ref = "ceres-data" }
    ceres-data-database = { group = "dev.teogor.ceres", name = "ceres-data-database", version.ref = "ceres-data" }
    ceres-data-datastore = { group = "dev.teogor.ceres", name = "ceres-data-datastore", version.ref = "ceres-data" }
    ceres-firebase-analytics = { group = "dev.teogor.ceres", name = "ceres-firebase-analytics", version.ref = "ceres-firebase" }
    ceres-firebase-crashlytics = { group = "dev.teogor.ceres", name = "ceres-firebase-crashlytics", version.ref = "ceres-firebase" }
    ceres-firebase-remote-config = { group = "dev.teogor.ceres", name = "ceres-firebase-remoteconfig", version.ref = "ceres-firebase" }
    ceres-framework-core = { group = "dev.teogor.ceres", name = "ceres-framework-core", version.ref = "ceres-framework" }
    ceres-framework-ui = { group = "dev.teogor.ceres", name = "ceres-framework-ui", version.ref = "ceres-framework" }
    ceres-monetisation-admob = { group = "dev.teogor.ceres", name = "ceres-monetisation-admob", version.ref = "ceres-monetisation" }
    ceres-monetisation-ads = { group = "dev.teogor.ceres", name = "ceres-monetisation-ads", version.ref = "ceres-monetisation" }
    ceres-monetisation-messaging = { group = "dev.teogor.ceres", name = "ceres-monetisation-messaging", version.ref = "ceres-monetisation" }
    ceres-navigation-common = { group = "dev.teogor.ceres", name = "ceres-navigation-common", version.ref = "ceres-navigation" }
    ceres-navigation-core = { group = "dev.teogor.ceres", name = "ceres-navigation-core", version.ref = "ceres-navigation" }
    ceres-navigation-events = { group = "dev.teogor.ceres", name = "ceres-navigation-events", version.ref = "ceres-navigation" }
    ceres-navigation-screen = { group = "dev.teogor.ceres", name = "ceres-navigation-screen", version.ref = "ceres-navigation" }
    ceres-navigation-ui = { group = "dev.teogor.ceres", name = "ceres-navigation-ui", version.ref = "ceres-navigation" }
    ceres-screen-builder = { group = "dev.teogor.ceres", name = "ceres-screen-builder", version.ref = "ceres-screen" }
    ceres-screen-core = { group = "dev.teogor.ceres", name = "ceres-screen-core", version.ref = "ceres-screen" }
    ceres-screen-ui = { group = "dev.teogor.ceres", name = "ceres-screen-ui", version.ref = "ceres-screen" }
    ceres-ui-compose = { group = "dev.teogor.ceres", name = "ceres-ui-compose", version.ref = "ceres-ui" }
    ceres-ui-design-system = { group = "dev.teogor.ceres", name = "ceres-ui-design.system", version.ref = "ceres-ui" }
    ceres-ui-foundation = { group = "dev.teogor.ceres", name = "ceres-ui-foundation", version.ref = "ceres-ui" }
    ceres-ui-icons = { group = "dev.teogor.ceres", name = "ceres-ui-icons", version.ref = "ceres-ui" }
    ceres-ui-spectrum = { group = "dev.teogor.ceres", name = "ceres-ui-spectrum", version.ref = "ceres-ui" }
    ceres-ui-theme = { group = "dev.teogor.ceres", name = "ceres-ui-theme", version.ref = "ceres-ui" }
    ```

=== "Using BoM"

    ```toml title="gradle/libs.versions.toml"
    [versions]
    ceres-bom = "1.0.0-alpha04"

    [libraries]
    ceres-bom = { group = "dev.teogor.ceres", name = "bom", version.ref = "ceres-bom" }
    ceres-backup-core = { group = "dev.teogor.ceres", name = "ceres-backup-core" }
    ceres-backup-ui = { group = "dev.teogor.ceres", name = "ceres-backup-ui" }
    ceres-core-analytics = { group = "dev.teogor.ceres", name = "ceres-core-analytics" }
    ceres-core-common = { group = "dev.teogor.ceres", name = "ceres-core-common" }
    ceres-core-foundation = { group = "dev.teogor.ceres", name = "ceres-core-foundation" }
    ceres-core-network = { group = "dev.teogor.ceres", name = "ceres-core-network" }
    ceres-core-notifications = { group = "dev.teogor.ceres", name = "ceres-core-notifications" }
    ceres-core-register = { group = "dev.teogor.ceres", name = "ceres-core-register" }
    ceres-core-runtime = { group = "dev.teogor.ceres", name = "ceres-core-runtime" }
    ceres-core-startup = { group = "dev.teogor.ceres", name = "ceres-core-startup" }
    ceres-data-compose = { group = "dev.teogor.ceres", name = "ceres-data-compose" }
    ceres-data-database = { group = "dev.teogor.ceres", name = "ceres-data-database" }
    ceres-data-datastore = { group = "dev.teogor.ceres", name = "ceres-data-datastore" }
    ceres-firebase-analytics = { group = "dev.teogor.ceres", name = "ceres-firebase-analytics" }
    ceres-firebase-crashlytics = { group = "dev.teogor.ceres", name = "ceres-firebase-crashlytics" }
    ceres-firebase-remote-config = { group = "dev.teogor.ceres", name = "ceres-firebase-remoteconfig" }
    ceres-framework-core = { group = "dev.teogor.ceres", name = "ceres-framework-core" }
    ceres-framework-ui = { group = "dev.teogor.ceres", name = "ceres-framework-ui" }
    ceres-monetisation-admob = { group = "dev.teogor.ceres", name = "ceres-monetisation-admob" }
    ceres-monetisation-ads = { group = "dev.teogor.ceres", name = "ceres-monetisation-ads" }
    ceres-monetisation-messaging = { group = "dev.teogor.ceres", name = "ceres-monetisation-messaging" }
    ceres-navigation-common = { group = "dev.teogor.ceres", name = "ceres-navigation-common" }
    ceres-navigation-core = { group = "dev.teogor.ceres", name = "ceres-navigation-core" }
    ceres-navigation-events = { group = "dev.teogor.ceres", name = "ceres-navigation-events" }
    ceres-navigation-screen = { group = "dev.teogor.ceres", name = "ceres-navigation-screen" }
    ceres-navigation-ui = { group = "dev.teogor.ceres", name = "ceres-navigation-ui" }
    ceres-screen-builder = { group = "dev.teogor.ceres", name = "ceres-screen-builder" }
    ceres-screen-core = { group = "dev.teogor.ceres", name = "ceres-screen-core" }
    ceres-screen-ui = { group = "dev.teogor.ceres", name = "ceres-screen-ui" }
    ceres-ui-compose = { group = "dev.teogor.ceres", name = "ceres-ui-compose" }
    ceres-ui-design-system = { group = "dev.teogor.ceres", name = "ceres-ui-design.system" }
    ceres-ui-foundation = { group = "dev.teogor.ceres", name = "ceres-ui-foundation" }
    ceres-ui-icons = { group = "dev.teogor.ceres", name = "ceres-ui-icons" }
    ceres-ui-spectrum = { group = "dev.teogor.ceres", name = "ceres-ui-spectrum" }
    ceres-ui-theme = { group = "dev.teogor.ceres", name = "ceres-ui-theme" }
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
