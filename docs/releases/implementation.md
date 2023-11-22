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
    ceres-core = "1.0.0-alpha01"
    ceres-ui = "1.0.0-alpha01"
    ceres-common = "1.0.0-alpha03"
    ceres-foundation = "1.0.0-alpha03"
    ceres-network = "1.0.0-alpha03"
    ceres-notifications = "1.0.0-alpha03"
    ceres-runtime = "1.0.0-alpha03"
    ceres-startup = "1.0.0-alpha03"
    ceres-compose = "1.0.0-alpha02"
    ceres-database = "1.0.0-alpha02"
    ceres-datastore = "1.0.0-alpha02"
    ceres-analytics = "1.0.0-alpha03"
    ceres-crashlytics = "1.0.0-alpha03"
    ceres-remoteconfig = "1.0.0-alpha03"
    ceres-ui = "1.0.0-alpha03"
    ceres-admob = "1.0.0-alpha03"
    ceres-ads = "1.0.0-alpha03"
    ceres-messaging = "1.0.0-alpha03"
    ceres-events = "1.0.0-alpha02"
    ceres-screen = "1.0.0-alpha02"
    ceres-builder = "1.0.0-alpha03"
    ceres-compose = "1.0.0-alpha03"
    ceres-design.system = "1.0.0-alpha03"
    ceres-foundation = "1.0.0-alpha03"
    ceres-icons = "1.0.0-alpha03"
    ceres-spectrum = "1.0.0-alpha03"
    ceres-theme = "1.0.0-alpha03"

    [libraries]
    ceres-core = { group = "dev.teogor.ceres", name = "backup-core", version.ref = "ceres-core" }
    ceres-ui = { group = "dev.teogor.ceres", name = "backup-ui", version.ref = "ceres-ui" }
    ceres-common = { group = "dev.teogor.ceres", name = "common", version.ref = "ceres-common" }
    ceres-foundation = { group = "dev.teogor.ceres", name = "foundation", version.ref = "ceres-foundation" }
    ceres-network = { group = "dev.teogor.ceres", name = "network", version.ref = "ceres-network" }
    ceres-notifications = { group = "dev.teogor.ceres", name = "notifications", version.ref = "ceres-notifications" }
    ceres-runtime = { group = "dev.teogor.ceres", name = "runtime", version.ref = "ceres-runtime" }
    ceres-startup = { group = "dev.teogor.ceres", name = "startup", version.ref = "ceres-startup" }
    ceres-compose = { group = "dev.teogor.ceres", name = "compose", version.ref = "ceres-compose" }
    ceres-database = { group = "dev.teogor.ceres", name = "database", version.ref = "ceres-database" }
    ceres-datastore = { group = "dev.teogor.ceres", name = "datastore", version.ref = "ceres-datastore" }
    ceres-analytics = { group = "dev.teogor.ceres", name = "analytics", version.ref = "ceres-analytics" }
    ceres-crashlytics = { group = "dev.teogor.ceres", name = "crashlytics", version.ref = "ceres-crashlytics" }
    ceres-remoteconfig = { group = "dev.teogor.ceres", name = "remoteconfig", version.ref = "ceres-remoteconfig" }
    ceres-ui = { group = "dev.teogor.ceres", name = "ui", version.ref = "ceres-ui" }
    ceres-admob = { group = "dev.teogor.ceres", name = "admob", version.ref = "ceres-admob" }
    ceres-ads = { group = "dev.teogor.ceres", name = "ads", version.ref = "ceres-ads" }
    ceres-messaging = { group = "dev.teogor.ceres", name = "messaging", version.ref = "ceres-messaging" }
    ceres-events = { group = "dev.teogor.ceres", name = "events", version.ref = "ceres-events" }
    ceres-screen = { group = "dev.teogor.ceres", name = "screen", version.ref = "ceres-screen" }
    ceres-builder = { group = "dev.teogor.ceres", name = "builder", version.ref = "ceres-builder" }
    ceres-compose = { group = "dev.teogor.ceres", name = "ui-compose", version.ref = "ceres-compose" }
    ceres-design.system = { group = "dev.teogor.ceres", name = "ui-design.system", version.ref = "ceres-design.system" }
    ceres-foundation = { group = "dev.teogor.ceres", name = "ui-foundation", version.ref = "ceres-foundation" }
    ceres-icons = { group = "dev.teogor.ceres", name = "ui-icons", version.ref = "ceres-icons" }
    ceres-spectrum = { group = "dev.teogor.ceres", name = "ui-spectrum", version.ref = "ceres-spectrum" }
    ceres-theme = { group = "dev.teogor.ceres", name = "ui-theme", version.ref = "ceres-theme" }
    ```

=== "Using BoM"

    ```toml title="gradle/libs.versions.toml"
    [versions]
    ceres-bom = "1.0.0-alpha03"

    [libraries]
    ceres-bom = { group = "dev.teogor.ceres", name = "bom", version.ref = "ceres-bom" }
    ceres-core = { group = "dev.teogor.ceres", name = "backup-core" }
    ceres-ui = { group = "dev.teogor.ceres", name = "backup-ui" }
    ceres-common = { group = "dev.teogor.ceres", name = "common" }
    ceres-foundation = { group = "dev.teogor.ceres", name = "foundation" }
    ceres-network = { group = "dev.teogor.ceres", name = "network" }
    ceres-notifications = { group = "dev.teogor.ceres", name = "notifications" }
    ceres-runtime = { group = "dev.teogor.ceres", name = "runtime" }
    ceres-startup = { group = "dev.teogor.ceres", name = "startup" }
    ceres-compose = { group = "dev.teogor.ceres", name = "compose" }
    ceres-database = { group = "dev.teogor.ceres", name = "database" }
    ceres-datastore = { group = "dev.teogor.ceres", name = "datastore" }
    ceres-analytics = { group = "dev.teogor.ceres", name = "analytics" }
    ceres-crashlytics = { group = "dev.teogor.ceres", name = "crashlytics" }
    ceres-remoteconfig = { group = "dev.teogor.ceres", name = "remoteconfig" }
    ceres-ui = { group = "dev.teogor.ceres", name = "ui" }
    ceres-admob = { group = "dev.teogor.ceres", name = "admob" }
    ceres-ads = { group = "dev.teogor.ceres", name = "ads" }
    ceres-messaging = { group = "dev.teogor.ceres", name = "messaging" }
    ceres-events = { group = "dev.teogor.ceres", name = "events" }
    ceres-screen = { group = "dev.teogor.ceres", name = "screen" }
    ceres-builder = { group = "dev.teogor.ceres", name = "builder" }
    ceres-compose = { group = "dev.teogor.ceres", name = "ui-compose" }
    ceres-design.system = { group = "dev.teogor.ceres", name = "ui-design.system" }
    ceres-foundation = { group = "dev.teogor.ceres", name = "ui-foundation" }
    ceres-icons = { group = "dev.teogor.ceres", name = "ui-icons" }
    ceres-spectrum = { group = "dev.teogor.ceres", name = "ui-spectrum" }
    ceres-theme = { group = "dev.teogor.ceres", name = "ui-theme" }
    ```

#### Dependencies Implementation

=== "Kotlin"

    ```kotlin title="build.gradle.kts"
    dependencies {
      // When Using Ceres BoM
      implementation(platform(libs.ceres.bom))

      // Ceres Libraries
      implementation(libs.ceres.core)
      implementation(libs.ceres.ui)
      implementation(libs.ceres.common)
      implementation(libs.ceres.foundation)
      implementation(libs.ceres.network)
      implementation(libs.ceres.notifications)
      implementation(libs.ceres.runtime)
      implementation(libs.ceres.startup)
      implementation(libs.ceres.compose)
      implementation(libs.ceres.database)
      implementation(libs.ceres.datastore)
      implementation(libs.ceres.analytics)
      implementation(libs.ceres.crashlytics)
      implementation(libs.ceres.remoteconfig)
      implementation(libs.ceres.ui)
      implementation(libs.ceres.admob)
      implementation(libs.ceres.ads)
      implementation(libs.ceres.messaging)
      implementation(libs.ceres.events)
      implementation(libs.ceres.screen)
      implementation(libs.ceres.builder)
      implementation(libs.ceres.compose)
      implementation(libs.ceres.design.system)
      implementation(libs.ceres.foundation)
      implementation(libs.ceres.icons)
      implementation(libs.ceres.spectrum)
      implementation(libs.ceres.theme)
    }
    ```

=== "Groovy"

    ```groovy title="build.gradle"
    dependencies {
      // When Using Ceres BoM
      implementation platform(libs.ceres.bom)

      // Ceres Libraries
      implementation(libs.ceres.core)
      implementation(libs.ceres.ui)
      implementation(libs.ceres.common)
      implementation(libs.ceres.foundation)
      implementation(libs.ceres.network)
      implementation(libs.ceres.notifications)
      implementation(libs.ceres.runtime)
      implementation(libs.ceres.startup)
      implementation(libs.ceres.compose)
      implementation(libs.ceres.database)
      implementation(libs.ceres.datastore)
      implementation(libs.ceres.analytics)
      implementation(libs.ceres.crashlytics)
      implementation(libs.ceres.remoteconfig)
      implementation(libs.ceres.ui)
      implementation(libs.ceres.admob)
      implementation(libs.ceres.ads)
      implementation(libs.ceres.messaging)
      implementation(libs.ceres.events)
      implementation(libs.ceres.screen)
      implementation(libs.ceres.builder)
      implementation(libs.ceres.compose)
      implementation(libs.ceres.design.system)
      implementation(libs.ceres.foundation)
      implementation(libs.ceres.icons)
      implementation(libs.ceres.spectrum)
      implementation(libs.ceres.theme)
    }
    ```
