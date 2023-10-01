## Libraries Implementation Version Catalog

This catalog provides the implementation details of Ceres libraries, including Build of Materials (BoM) and individual libraries, in TOML format.

```toml
# Ceres BoM
ceres-bom = { group = "dev.teogor.ceres", name = "bom", version.ref = "ceres-bom" }
# Ceres Libraries
ceres-backup-core = { group = "dev.teogor.ceres", name = "backup-core" }
ceres-backup-ui = { group = "dev.teogor.ceres", name = "backup-ui" }
ceres-core-network = { group = "dev.teogor.ceres", name = "core-network" }
ceres-core-notifications = { group = "dev.teogor.ceres", name = "core-notifications" }
ceres-core-runtime = { group = "dev.teogor.ceres", name = "core-runtime" }
ceres-core-startup = { group = "dev.teogor.ceres", name = "core-startup" }
ceres-data-compose = { group = "dev.teogor.ceres", name = "data-compose" }
ceres-data-database = { group = "dev.teogor.ceres", name = "data-database" }
ceres-data-datastore = { group = "dev.teogor.ceres", name = "data-datastore" }
ceres-firebase-analytics = { group = "dev.teogor.ceres", name = "firebase-analytics" }
ceres-firebase-crashlytics = { group = "dev.teogor.ceres", name = "firebase-crashlytics" }
ceres-firebase-remote-config = { group = "dev.teogor.ceres", name = "firebase-remote-config" }
ceres-framework-core = { group = "dev.teogor.ceres", name = "framework-core" }
ceres-framework-ui = { group = "dev.teogor.ceres", name = "framework-ui" }
ceres-monetisation-admob = { group = "dev.teogor.ceres", name = "monetisation-admob" }
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
ceres-ui-design-system = { group = "dev.teogor.ceres", name = "ui-designsystem" }
ceres-ui-foundation = { group = "dev.teogor.ceres", name = "ui-foundation" }
ceres-ui-icons = { group = "dev.teogor.ceres", name = "ui-icons" }
ceres-ui-spectrum = { group = "dev.teogor.ceres", name = "ui-spectrum" }
ceres-ui-theme = { group = "dev.teogor.ceres", name = "ui-theme" }
```

## Libraries Implementation build.gradle.kts File

This section presents the implementation dependencies for Ceres libraries in a Kotlin build.gradle.kts file format.

```kotlin
dependencies {
  // Ceres BoM
  implementation(platform(libs.ceres.bom))
  // Ceres Libraries
  implementation(libs.ceres.backup.core)
  implementation(libs.ceres.backup.ui)
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
  implementation(libs.ceres.framework.core)
  implementation(libs.ceres.framework.ui)
  implementation(libs.ceres.monetisation.admob)
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

