[//]: # (This file was automatically generated - do not edit)

## Implementation

### Latest Version

The latest release is [`1.0.0-alpha03`](../releases.md)

### BoM Releases

The BoM (Bill of Materials) is the central repository for managing library versions within the
Ceres project. It streamlines the process of tracking the latest versions of key components and
dependencies, ensuring that your project remains up-to-date and compatible with the latest
advancements.

Here's a summary of the latest BoM versions:

|    Version    |               Release Notes                | Release Date |
|:-------------:|:------------------------------------------:|:------------:|
| 1.0.0-alpha03 | [changelog 🔗](changelog/1.0.0-alpha03.md) | 22 Nov 2023 |
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
    ceres-data = "1.0.0-alpha02"
    ceres-firebase = "1.0.0-alpha03"
    ceres-framework = "1.0.0-alpha03"
    ceres-monetisation = "1.0.0-alpha03"
    ceres-navigation = "1.0.0-alpha02"
    ceres-screen = "1.0.0-alpha03"
    ceres-ui = "1.0.0-alpha03"

    [libraries]
    ceres-backup-core = { group = "dev.teogor.ceres", name = "backup-core", version.ref = "ceres-backup" }
    ceres-backup-ui = { group = "dev.teogor.ceres", name = "backup-ui", version.ref = "ceres-backup" }
    ceres-core-common = { group = "dev.teogor.ceres", name = "common", version.ref = "ceres-core" }
    ceres-core-foundation = { group = "dev.teogor.ceres", name = "foundation", version.ref = "ceres-core" }
    ceres-core-network = { group = "dev.teogor.ceres", name = "network", version.ref = "ceres-core" }
    ceres-core-notifications = { group = "dev.teogor.ceres", name = "notifications", version.ref = "ceres-core" }
    ceres-core-runtime = { group = "dev.teogor.ceres", name = "runtime", version.ref = "ceres-core" }
    ceres-core-startup = { group = "dev.teogor.ceres", name = "startup", version.ref = "ceres-core" }
    ceres-data-compose = { group = "dev.teogor.ceres", name = "compose", version.ref = "ceres-data" }
    ceres-data-database = { group = "dev.teogor.ceres", name = "database", version.ref = "ceres-data" }
    ceres-data-datastore = { group = "dev.teogor.ceres", name = "datastore", version.ref = "ceres-data" }
    ceres-firebase-analytics = { group = "dev.teogor.ceres", name = "analytics", version.ref = "ceres-firebase" }
    ceres-firebase-crashlytics = { group = "dev.teogor.ceres", name = "crashlytics", version.ref = "ceres-firebase" }
    ceres-firebase-remote-config = { group = "dev.teogor.ceres", name = "remoteconfig", version.ref = "ceres-firebase" }
    ceres-framework-ui = { group = "dev.teogor.ceres", name = "ui", version.ref = "ceres-framework" }
    ceres-monetisation-admob = { group = "dev.teogor.ceres", name = "admob", version.ref = "ceres-monetisation" }
    ceres-monetisation-ads = { group = "dev.teogor.ceres", name = "ads", version.ref = "ceres-monetisation" }
    ceres-monetisation-messaging = { group = "dev.teogor.ceres", name = "messaging", version.ref = "ceres-monetisation" }
    ceres-navigation-events = { group = "dev.teogor.ceres", name = "events", version.ref = "ceres-navigation" }
    ceres-navigation-screen = { group = "dev.teogor.ceres", name = "screen", version.ref = "ceres-navigation" }
    ceres-screen-builder = { group = "dev.teogor.ceres", name = "builder", version.ref = "ceres-screen" }
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
    ceres-bom = "1.0.0-alpha03"

    [libraries]
    ceres-bom = { group = "dev.teogor.ceres", name = "bom", version.ref = "ceres-bom" }
    ceres-backup-core = { group = "dev.teogor.ceres", name = "backup-core" }
    ceres-backup-ui = { group = "dev.teogor.ceres", name = "backup-ui" }
    ceres-core-common = { group = "dev.teogor.ceres", name = "common" }
    ceres-core-foundation = { group = "dev.teogor.ceres", name = "foundation" }
    ceres-core-network = { group = "dev.teogor.ceres", name = "network" }
    ceres-core-notifications = { group = "dev.teogor.ceres", name = "notifications" }
    ceres-core-runtime = { group = "dev.teogor.ceres", name = "runtime" }
    ceres-core-startup = { group = "dev.teogor.ceres", name = "startup" }
    ceres-data-compose = { group = "dev.teogor.ceres", name = "compose" }
    ceres-data-database = { group = "dev.teogor.ceres", name = "database" }
    ceres-data-datastore = { group = "dev.teogor.ceres", name = "datastore" }
    ceres-firebase-analytics = { group = "dev.teogor.ceres", name = "analytics" }
    ceres-firebase-crashlytics = { group = "dev.teogor.ceres", name = "crashlytics" }
    ceres-firebase-remote-config = { group = "dev.teogor.ceres", name = "remoteconfig" }
    ceres-framework-ui = { group = "dev.teogor.ceres", name = "ui" }
    ceres-monetisation-admob = { group = "dev.teogor.ceres", name = "admob" }
    ceres-monetisation-ads = { group = "dev.teogor.ceres", name = "ads" }
    ceres-monetisation-messaging = { group = "dev.teogor.ceres", name = "messaging" }
    ceres-navigation-events = { group = "dev.teogor.ceres", name = "events" }
    ceres-navigation-screen = { group = "dev.teogor.ceres", name = "screen" }
    ceres-screen-builder = { group = "dev.teogor.ceres", name = "builder" }
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
      implementation(libs.ceres.core.common)
      implementation(libs.ceres.core.foundation)
      implementation(libs.ceres.core.network)
      implementation(libs.ceres.core.notifications)
      implementation(libs.ceres.core.runtime)
      implementation(libs.ceres.core.startup)
      implementation(libs.ceres.data.compose)
      implementation(libs.ceres.data.database)
      implementation(libs.ceres.data.datastore)
      implementation(libs.ceres.firebase.analytics)
      implementation(libs.ceres.firebase.crashlytics)
      implementation(libs.ceres.firebase.remote.config)
      implementation(libs.ceres.framework.ui)
      implementation(libs.ceres.monetisation.admob)
      implementation(libs.ceres.monetisation.ads)
      implementation(libs.ceres.monetisation.messaging)
      implementation(libs.ceres.navigation.events)
      implementation(libs.ceres.navigation.screen)
      implementation(libs.ceres.screen.builder)
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
      implementation(libs.ceres.core.common)
      implementation(libs.ceres.core.foundation)
      implementation(libs.ceres.core.network)
      implementation(libs.ceres.core.notifications)
      implementation(libs.ceres.core.runtime)
      implementation(libs.ceres.core.startup)
      implementation(libs.ceres.data.compose)
      implementation(libs.ceres.data.database)
      implementation(libs.ceres.data.datastore)
      implementation(libs.ceres.firebase.analytics)
      implementation(libs.ceres.firebase.crashlytics)
      implementation(libs.ceres.firebase.remote.config)
      implementation(libs.ceres.framework.ui)
      implementation(libs.ceres.monetisation.admob)
      implementation(libs.ceres.monetisation.ads)
      implementation(libs.ceres.monetisation.messaging)
      implementation(libs.ceres.navigation.events)
      implementation(libs.ceres.navigation.screen)
      implementation(libs.ceres.screen.builder)
      implementation(libs.ceres.ui.compose)
      implementation(libs.ceres.ui.design.system)
      implementation(libs.ceres.ui.foundation)
      implementation(libs.ceres.ui.icons)
      implementation(libs.ceres.ui.spectrum)
      implementation(libs.ceres.ui.theme)
    }
    ```
