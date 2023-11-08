## Libraries Implementation Version Catalog

This catalog provides the implementation details of Ceres libraries, including Build of Materials (BoM) and individual libraries, in TOML format.

```toml
[versions]
ceres-bom = "1.0.0-alpha01"

[libraries]
# Ceres BoM
ceres-bom = { group = "dev.teogor.ceres", name = "bom", version.ref = "ceres-bom" }
# Ceres Libraries
ceres-core = { group = "dev.teogor.ceres", name = "backup-core" }
ceres-ui = { group = "dev.teogor.ceres", name = "backup-ui" }
ceres-network = { group = "dev.teogor.ceres", name = "core-network" }
ceres-notifications = { group = "dev.teogor.ceres", name = "core-notifications" }
ceres-runtime = { group = "dev.teogor.ceres", name = "core-runtime" }
ceres-startup = { group = "dev.teogor.ceres", name = "core-startup" }
ceres-compose = { group = "dev.teogor.ceres", name = "data-compose" }
ceres-database = { group = "dev.teogor.ceres", name = "data-database" }
ceres-datastore = { group = "dev.teogor.ceres", name = "data-datastore" }
ceres-analytics = { group = "dev.teogor.ceres", name = "firebase-analytics" }
ceres-crashlytics = { group = "dev.teogor.ceres", name = "firebase-crashlytics" }
ceres-remoteconfig = { group = "dev.teogor.ceres", name = "firebase-remoteconfig" }
ceres-core = { group = "dev.teogor.ceres", name = "framework-core" }
ceres-ui = { group = "dev.teogor.ceres", name = "framework-ui" }
ceres-admob = { group = "dev.teogor.ceres", name = "monetisation-admob" }
ceres-messaging = { group = "dev.teogor.ceres", name = "monetisation-messaging" }
ceres-common = { group = "dev.teogor.ceres", name = "navigation-common" }
ceres-core = { group = "dev.teogor.ceres", name = "navigation-core" }
ceres-events = { group = "dev.teogor.ceres", name = "navigation-events" }
ceres-screen = { group = "dev.teogor.ceres", name = "navigation-screen" }
ceres-ui = { group = "dev.teogor.ceres", name = "navigation-ui" }
ceres-builder = { group = "dev.teogor.ceres", name = "screen-builder" }
ceres-core = { group = "dev.teogor.ceres", name = "screen-core" }
ceres-ui = { group = "dev.teogor.ceres", name = "screen-ui" }
ceres-compose = { group = "dev.teogor.ceres", name = "ui-compose" }
ceres-design.system = { group = "dev.teogor.ceres", name = "ui-design.system" }
ceres-foundation = { group = "dev.teogor.ceres", name = "ui-foundation" }
ceres-icons = { group = "dev.teogor.ceres", name = "ui-icons" }
ceres-spectrum = { group = "dev.teogor.ceres", name = "ui-spectrum" }
ceres-theme = { group = "dev.teogor.ceres", name = "ui-theme" }
```

## Libraries Implementation build.gradle.kts File

This section presents the implementation dependencies for Ceres libraries in a Kotlin build.gradle.kts file format.

```kotlin
dependencies {
  // Ceres BoM
  implementation(platform(libs.ceres.bom))
  // Ceres Libraries
  implementation(libs.ceres.core)
  implementation(libs.ceres.ui)
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
  implementation(libs.ceres.core)
  implementation(libs.ceres.ui)
  implementation(libs.ceres.admob)
  implementation(libs.ceres.messaging)
  implementation(libs.ceres.common)
  implementation(libs.ceres.core)
  implementation(libs.ceres.events)
  implementation(libs.ceres.screen)
  implementation(libs.ceres.ui)
  implementation(libs.ceres.builder)
  implementation(libs.ceres.core)
  implementation(libs.ceres.ui)
  implementation(libs.ceres.compose)
  implementation(libs.ceres.design.system)
  implementation(libs.ceres.foundation)
  implementation(libs.ceres.icons)
  implementation(libs.ceres.spectrum)
  implementation(libs.ceres.theme)
}
```

