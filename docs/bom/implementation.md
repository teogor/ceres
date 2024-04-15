# BoM

[//]: # (REGION-DEPENDENCIES)

## Getting Started with BoM

**Adding Dependencies:**

* **Manual Setup:**  This section guides you through adding BoM dependencies directly to your project's `build.gradle` files. ([Link to Manual Dependency Setup Section](#adding-bom-dependencies-manually))
* **Version Catalog (Recommended):** For a more streamlined approach, consider integrating a version catalog. This allows for centralized version management and easier updates. ([Link to Version Catalog Section](#managing-bom-versions-with-version-catalog-recommended))

**Note:** If you prefer manual dependency setup, follow the instructions in the "Manual Setup" section. Otherwise, jump to the "Version Catalog" section for centralized management.

For information on using the KAPT plugin, see the [KAPT documentation](https://kotlinlang.org/docs/kapt.html).  
For information on using the KSP plugin, see the [KSP quick-start documentation](https://kotlinlang.org/docs/ksp-quickstart.html).  
For more information about dependencies, see [Add Build Dependencies](https://developer.android.com/studio/build/dependencies).  

### Adding BoM Dependencies Manually

To use BoM in your app, add the following dependencies to your app's `build.gradle` file:

=== "Groovy"

    ```groovy title="build.gradle"
    dependencies {
        def teogorCeresBom = "1.0.0-alpha05"
        
        implementation "dev.teogor.ceres:bom:$teogorCeresBom"
        implementation "dev.teogor.ceres:backup-core:$teogorCeresBom"
        implementation "dev.teogor.ceres:backup-ui:$teogorCeresBom"
        implementation "dev.teogor.ceres:core-common:$teogorCeresBom"
        implementation "dev.teogor.ceres:core-analytics:$teogorCeresBom"
        implementation "dev.teogor.ceres:core-foundation:$teogorCeresBom"
        implementation "dev.teogor.ceres:core-network:$teogorCeresBom"
        implementation "dev.teogor.ceres:core-notifications:$teogorCeresBom"
        implementation "dev.teogor.ceres:core-register:$teogorCeresBom"
        implementation "dev.teogor.ceres:core-runtime:$teogorCeresBom"
        implementation "dev.teogor.ceres:core-start-up:$teogorCeresBom"
        implementation "dev.teogor.ceres:data-compose:$teogorCeresBom"
        implementation "dev.teogor.ceres:data-database:$teogorCeresBom"
        implementation "dev.teogor.ceres:data-datastore:$teogorCeresBom"
        implementation "dev.teogor.ceres:firebase-crashlytics:$teogorCeresBom"
        implementation "dev.teogor.ceres:firebase-analytics:$teogorCeresBom"
        implementation "dev.teogor.ceres:firebase-remote-config:$teogorCeresBom"
        implementation "dev.teogor.ceres:framework-core:$teogorCeresBom"
        implementation "dev.teogor.ceres:framework-ui:$teogorCeresBom"
        implementation "dev.teogor.ceres:monetisation-admob:$teogorCeresBom"
        implementation "dev.teogor.ceres:monetisation-ads:$teogorCeresBom"
        implementation "dev.teogor.ceres:monetisation-messaging:$teogorCeresBom"
        implementation "dev.teogor.ceres:navigation-common:$teogorCeresBom"
        implementation "dev.teogor.ceres:navigation-core:$teogorCeresBom"
        implementation "dev.teogor.ceres:navigation-events:$teogorCeresBom"
        implementation "dev.teogor.ceres:navigation-screen:$teogorCeresBom"
        implementation "dev.teogor.ceres:navigation-ui:$teogorCeresBom"
        implementation "dev.teogor.ceres:screen-builder:$teogorCeresBom"
        implementation "dev.teogor.ceres:screen-core:$teogorCeresBom"
        implementation "dev.teogor.ceres:screen-ui:$teogorCeresBom"
        implementation "dev.teogor.ceres:ui-compose:$teogorCeresBom"
        implementation "dev.teogor.ceres:ui-design-system:$teogorCeresBom"
        implementation "dev.teogor.ceres:ui-icons:$teogorCeresBom"
        implementation "dev.teogor.ceres:ui-spectrum:$teogorCeresBom"
        implementation "dev.teogor.ceres:ui-theme:$teogorCeresBom"
        implementation "dev.teogor.ceres:ui-foundation:$teogorCeresBom"
    }
    ```

=== "Kotlin"

    ```kotlin title="build.gradle.kts"
    dependencies {
        val teogorCeresBom = "1.0.0-alpha05"
        
        implementation("dev.teogor.ceres:bom:$teogorCeresBom")
        implementation("dev.teogor.ceres:backup-core:$teogorCeresBom")
        implementation("dev.teogor.ceres:backup-ui:$teogorCeresBom")
        implementation("dev.teogor.ceres:core-common:$teogorCeresBom")
        implementation("dev.teogor.ceres:core-analytics:$teogorCeresBom")
        implementation("dev.teogor.ceres:core-foundation:$teogorCeresBom")
        implementation("dev.teogor.ceres:core-network:$teogorCeresBom")
        implementation("dev.teogor.ceres:core-notifications:$teogorCeresBom")
        implementation("dev.teogor.ceres:core-register:$teogorCeresBom")
        implementation("dev.teogor.ceres:core-runtime:$teogorCeresBom")
        implementation("dev.teogor.ceres:core-start-up:$teogorCeresBom")
        implementation("dev.teogor.ceres:data-compose:$teogorCeresBom")
        implementation("dev.teogor.ceres:data-database:$teogorCeresBom")
        implementation("dev.teogor.ceres:data-datastore:$teogorCeresBom")
        implementation("dev.teogor.ceres:firebase-crashlytics:$teogorCeresBom")
        implementation("dev.teogor.ceres:firebase-analytics:$teogorCeresBom")
        implementation("dev.teogor.ceres:firebase-remote-config:$teogorCeresBom")
        implementation("dev.teogor.ceres:framework-core:$teogorCeresBom")
        implementation("dev.teogor.ceres:framework-ui:$teogorCeresBom")
        implementation("dev.teogor.ceres:monetisation-admob:$teogorCeresBom")
        implementation("dev.teogor.ceres:monetisation-ads:$teogorCeresBom")
        implementation("dev.teogor.ceres:monetisation-messaging:$teogorCeresBom")
        implementation("dev.teogor.ceres:navigation-common:$teogorCeresBom")
        implementation("dev.teogor.ceres:navigation-core:$teogorCeresBom")
        implementation("dev.teogor.ceres:navigation-events:$teogorCeresBom")
        implementation("dev.teogor.ceres:navigation-screen:$teogorCeresBom")
        implementation("dev.teogor.ceres:navigation-ui:$teogorCeresBom")
        implementation("dev.teogor.ceres:screen-builder:$teogorCeresBom")
        implementation("dev.teogor.ceres:screen-core:$teogorCeresBom")
        implementation("dev.teogor.ceres:screen-ui:$teogorCeresBom")
        implementation("dev.teogor.ceres:ui-compose:$teogorCeresBom")
        implementation("dev.teogor.ceres:ui-design-system:$teogorCeresBom")
        implementation("dev.teogor.ceres:ui-icons:$teogorCeresBom")
        implementation("dev.teogor.ceres:ui-spectrum:$teogorCeresBom")
        implementation("dev.teogor.ceres:ui-theme:$teogorCeresBom")
        implementation("dev.teogor.ceres:ui-foundation:$teogorCeresBom")
    }
    ```

### Managing BoM Versions with Version Catalog (Recommended)

This section guides you through utilizing a version catalog for centralized management of BoM dependencies in your project. This approach simplifies updates and ensures consistency.

First, define the dependencies in the `libs.versions.toml` file:

- **Group-Name Based:** This approach is used for declaring libraries referenced by group and artifact name.
- **Module Based:** This approach is used for declaring libraries referenced by their module.

=== "Group-Name Based"

    ```toml title="gradle/libs.versions.toml"
    [versions]
    teogor-ceres-bom = "1.0.0-alpha05"
    
    [libraries]
    teogor-ceres-bom = { group = "dev.teogor.ceres", name = "bom", version.ref = "teogor-ceres-bom" }
    teogor-ceres-backup-core = { group = "dev.teogor.ceres", name = "backup-core" }
    teogor-ceres-backup-ui = { group = "dev.teogor.ceres", name = "backup-ui" }
    teogor-ceres-core-common = { group = "dev.teogor.ceres", name = "core-common" }
    teogor-ceres-core-analytics = { group = "dev.teogor.ceres", name = "core-analytics" }
    teogor-ceres-core-foundation = { group = "dev.teogor.ceres", name = "core-foundation" }
    teogor-ceres-core-network = { group = "dev.teogor.ceres", name = "core-network" }
    teogor-ceres-core-notifications = { group = "dev.teogor.ceres", name = "core-notifications" }
    teogor-ceres-core-register = { group = "dev.teogor.ceres", name = "core-register" }
    teogor-ceres-core-runtime = { group = "dev.teogor.ceres", name = "core-runtime" }
    teogor-ceres-core-start-up = { group = "dev.teogor.ceres", name = "core-start-up" }
    teogor-ceres-data-compose = { group = "dev.teogor.ceres", name = "data-compose" }
    teogor-ceres-data-database = { group = "dev.teogor.ceres", name = "data-database" }
    teogor-ceres-data-datastore = { group = "dev.teogor.ceres", name = "data-datastore" }
    teogor-ceres-firebase-crashlytics = { group = "dev.teogor.ceres", name = "firebase-crashlytics" }
    teogor-ceres-firebase-analytics = { group = "dev.teogor.ceres", name = "firebase-analytics" }
    teogor-ceres-firebase-remote-config = { group = "dev.teogor.ceres", name = "firebase-remote-config" }
    teogor-ceres-framework-core = { group = "dev.teogor.ceres", name = "framework-core" }
    teogor-ceres-framework-ui = { group = "dev.teogor.ceres", name = "framework-ui" }
    teogor-ceres-monetisation-admob = { group = "dev.teogor.ceres", name = "monetisation-admob" }
    teogor-ceres-monetisation-ads = { group = "dev.teogor.ceres", name = "monetisation-ads" }
    teogor-ceres-monetisation-messaging = { group = "dev.teogor.ceres", name = "monetisation-messaging" }
    teogor-ceres-navigation-common = { group = "dev.teogor.ceres", name = "navigation-common" }
    teogor-ceres-navigation-core = { group = "dev.teogor.ceres", name = "navigation-core" }
    teogor-ceres-navigation-events = { group = "dev.teogor.ceres", name = "navigation-events" }
    teogor-ceres-navigation-screen = { group = "dev.teogor.ceres", name = "navigation-screen" }
    teogor-ceres-navigation-ui = { group = "dev.teogor.ceres", name = "navigation-ui" }
    teogor-ceres-screen-builder = { group = "dev.teogor.ceres", name = "screen-builder" }
    teogor-ceres-screen-core = { group = "dev.teogor.ceres", name = "screen-core" }
    teogor-ceres-screen-ui = { group = "dev.teogor.ceres", name = "screen-ui" }
    teogor-ceres-ui-compose = { group = "dev.teogor.ceres", name = "ui-compose" }
    teogor-ceres-ui-design-system = { group = "dev.teogor.ceres", name = "ui-design-system" }
    teogor-ceres-ui-icons = { group = "dev.teogor.ceres", name = "ui-icons" }
    teogor-ceres-ui-spectrum = { group = "dev.teogor.ceres", name = "ui-spectrum" }
    teogor-ceres-ui-theme = { group = "dev.teogor.ceres", name = "ui-theme" }
    teogor-ceres-ui-foundation = { group = "dev.teogor.ceres", name = "ui-foundation" }
    ```

=== "Module Based"

    ```toml title="gradle/libs.versions.toml"
    [versions]
    teogor-ceres-bom = "1.0.0-alpha05"
    
    [libraries]
    teogor-ceres-bom = { module = "dev.teogor.ceres:bom", version.ref = "teogor-ceres-bom" }
    teogor-ceres-backup-core = { module = "dev.teogor.ceres:backup-core" }
    teogor-ceres-backup-ui = { module = "dev.teogor.ceres:backup-ui" }
    teogor-ceres-core-common = { module = "dev.teogor.ceres:core-common" }
    teogor-ceres-core-analytics = { module = "dev.teogor.ceres:core-analytics" }
    teogor-ceres-core-foundation = { module = "dev.teogor.ceres:core-foundation" }
    teogor-ceres-core-network = { module = "dev.teogor.ceres:core-network" }
    teogor-ceres-core-notifications = { module = "dev.teogor.ceres:core-notifications" }
    teogor-ceres-core-register = { module = "dev.teogor.ceres:core-register" }
    teogor-ceres-core-runtime = { module = "dev.teogor.ceres:core-runtime" }
    teogor-ceres-core-start-up = { module = "dev.teogor.ceres:core-start-up" }
    teogor-ceres-data-compose = { module = "dev.teogor.ceres:data-compose" }
    teogor-ceres-data-database = { module = "dev.teogor.ceres:data-database" }
    teogor-ceres-data-datastore = { module = "dev.teogor.ceres:data-datastore" }
    teogor-ceres-firebase-crashlytics = { module = "dev.teogor.ceres:firebase-crashlytics" }
    teogor-ceres-firebase-analytics = { module = "dev.teogor.ceres:firebase-analytics" }
    teogor-ceres-firebase-remote-config = { module = "dev.teogor.ceres:firebase-remote-config" }
    teogor-ceres-framework-core = { module = "dev.teogor.ceres:framework-core" }
    teogor-ceres-framework-ui = { module = "dev.teogor.ceres:framework-ui" }
    teogor-ceres-monetisation-admob = { module = "dev.teogor.ceres:monetisation-admob" }
    teogor-ceres-monetisation-ads = { module = "dev.teogor.ceres:monetisation-ads" }
    teogor-ceres-monetisation-messaging = { module = "dev.teogor.ceres:monetisation-messaging" }
    teogor-ceres-navigation-common = { module = "dev.teogor.ceres:navigation-common" }
    teogor-ceres-navigation-core = { module = "dev.teogor.ceres:navigation-core" }
    teogor-ceres-navigation-events = { module = "dev.teogor.ceres:navigation-events" }
    teogor-ceres-navigation-screen = { module = "dev.teogor.ceres:navigation-screen" }
    teogor-ceres-navigation-ui = { module = "dev.teogor.ceres:navigation-ui" }
    teogor-ceres-screen-builder = { module = "dev.teogor.ceres:screen-builder" }
    teogor-ceres-screen-core = { module = "dev.teogor.ceres:screen-core" }
    teogor-ceres-screen-ui = { module = "dev.teogor.ceres:screen-ui" }
    teogor-ceres-ui-compose = { module = "dev.teogor.ceres:ui-compose" }
    teogor-ceres-ui-design-system = { module = "dev.teogor.ceres:ui-design-system" }
    teogor-ceres-ui-icons = { module = "dev.teogor.ceres:ui-icons" }
    teogor-ceres-ui-spectrum = { module = "dev.teogor.ceres:ui-spectrum" }
    teogor-ceres-ui-theme = { module = "dev.teogor.ceres:ui-theme" }
    teogor-ceres-ui-foundation = { module = "dev.teogor.ceres:ui-foundation" }
    ```

Then, add these dependencies in your app's `build.gradle` file:

=== "Groovy"

    ```groovy title="build.gradle"
    dependencies {
        implementation platform(libs.teogor.ceres.bom)
        implementation libs.teogor.ceres.backup.core
        implementation libs.teogor.ceres.backup.ui
        implementation libs.teogor.ceres.core.common
        implementation libs.teogor.ceres.core.analytics
        implementation libs.teogor.ceres.core.foundation
        implementation libs.teogor.ceres.core.network
        implementation libs.teogor.ceres.core.notifications
        implementation libs.teogor.ceres.core.register
        implementation libs.teogor.ceres.core.runtime
        implementation libs.teogor.ceres.core.start.up
        implementation libs.teogor.ceres.data.compose
        implementation libs.teogor.ceres.data.database
        implementation libs.teogor.ceres.data.datastore
        implementation libs.teogor.ceres.firebase.crashlytics
        implementation libs.teogor.ceres.firebase.analytics
        implementation libs.teogor.ceres.firebase.remote.config
        implementation libs.teogor.ceres.framework.core
        implementation libs.teogor.ceres.framework.ui
        implementation libs.teogor.ceres.monetisation.admob
        implementation libs.teogor.ceres.monetisation.ads
        implementation libs.teogor.ceres.monetisation.messaging
        implementation libs.teogor.ceres.navigation.common
        implementation libs.teogor.ceres.navigation.core
        implementation libs.teogor.ceres.navigation.events
        implementation libs.teogor.ceres.navigation.screen
        implementation libs.teogor.ceres.navigation.ui
        implementation libs.teogor.ceres.screen.builder
        implementation libs.teogor.ceres.screen.core
        implementation libs.teogor.ceres.screen.ui
        implementation libs.teogor.ceres.ui.compose
        implementation libs.teogor.ceres.ui.design.system
        implementation libs.teogor.ceres.ui.icons
        implementation libs.teogor.ceres.ui.spectrum
        implementation libs.teogor.ceres.ui.theme
        implementation libs.teogor.ceres.ui.foundation
    }
    ```

=== "Kotlin"

    ```kotlin title="build.gradle.kts"
    dependencies {
        implementation(platform(libs.teogor.ceres.bom))
        implementation(libs.teogor.ceres.backup.core)
        implementation(libs.teogor.ceres.backup.ui)
        implementation(libs.teogor.ceres.core.common)
        implementation(libs.teogor.ceres.core.analytics)
        implementation(libs.teogor.ceres.core.foundation)
        implementation(libs.teogor.ceres.core.network)
        implementation(libs.teogor.ceres.core.notifications)
        implementation(libs.teogor.ceres.core.register)
        implementation(libs.teogor.ceres.core.runtime)
        implementation(libs.teogor.ceres.core.start.up)
        implementation(libs.teogor.ceres.data.compose)
        implementation(libs.teogor.ceres.data.database)
        implementation(libs.teogor.ceres.data.datastore)
        implementation(libs.teogor.ceres.firebase.crashlytics)
        implementation(libs.teogor.ceres.firebase.analytics)
        implementation(libs.teogor.ceres.firebase.remote.config)
        implementation(libs.teogor.ceres.framework.core)
        implementation(libs.teogor.ceres.framework.ui)
        implementation(libs.teogor.ceres.monetisation.admob)
        implementation(libs.teogor.ceres.monetisation.ads)
        implementation(libs.teogor.ceres.monetisation.messaging)
        implementation(libs.teogor.ceres.navigation.common)
        implementation(libs.teogor.ceres.navigation.core)
        implementation(libs.teogor.ceres.navigation.events)
        implementation(libs.teogor.ceres.navigation.screen)
        implementation(libs.teogor.ceres.navigation.ui)
        implementation(libs.teogor.ceres.screen.builder)
        implementation(libs.teogor.ceres.screen.core)
        implementation(libs.teogor.ceres.screen.ui)
        implementation(libs.teogor.ceres.ui.compose)
        implementation(libs.teogor.ceres.ui.design.system)
        implementation(libs.teogor.ceres.ui.icons)
        implementation(libs.teogor.ceres.ui.spectrum)
        implementation(libs.teogor.ceres.ui.theme)
        implementation(libs.teogor.ceres.ui.foundation)
    }
    ```

[//]: # (REGION-DEPENDENCIES)

