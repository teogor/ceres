# Navigation

[//]: # (REGION-DEPENDENCIES)

## Getting Started with Navigation

**Adding Dependencies:**

* **Manual Setup:**  This section guides you through adding Navigation dependencies directly to your project's `build.gradle` files. ([Link to Manual Dependency Setup Section](#adding-navigation-dependencies-manually))
* **Version Catalog (Recommended):** For a more streamlined approach, consider integrating a version catalog. This allows for centralized version management and easier updates. ([Link to Version Catalog Section](#managing-navigation-versions-with-version-catalog-recommended))

**Note:** If you prefer manual dependency setup, follow the instructions in the "Manual Setup" section. Otherwise, jump to the "Version Catalog" section for centralized management.

For information on using the KAPT plugin, see the [KAPT documentation](https://kotlinlang.org/docs/kapt.html).  
For information on using the KSP plugin, see the [KSP quick-start documentation](https://kotlinlang.org/docs/ksp-quickstart.html).  
For more information about dependencies, see [Add Build Dependencies](https://developer.android.com/studio/build/dependencies).  

### Adding Navigation Dependencies Manually

To use Navigation in your app, add the following dependencies to your app's `build.gradle` file:

=== "Groovy"

    ```groovy title="build.gradle"
    dependencies {
        def teogorCeresNavigation = "1.0.0-alpha03"
        
        implementation "dev.teogor.ceres:navigation-common:$teogorCeresNavigation"
        implementation "dev.teogor.ceres:navigation-core:$teogorCeresNavigation"
        implementation "dev.teogor.ceres:navigation-events:$teogorCeresNavigation"
        implementation "dev.teogor.ceres:navigation-screen:$teogorCeresNavigation"
        implementation "dev.teogor.ceres:navigation-ui:$teogorCeresNavigation"
    }
    ```

=== "Kotlin"

    ```kotlin title="build.gradle.kts"
    dependencies {
        val teogorCeresNavigation = "1.0.0-alpha03"
        
        implementation("dev.teogor.ceres:navigation-common:$teogorCeresNavigation")
        implementation("dev.teogor.ceres:navigation-core:$teogorCeresNavigation")
        implementation("dev.teogor.ceres:navigation-events:$teogorCeresNavigation")
        implementation("dev.teogor.ceres:navigation-screen:$teogorCeresNavigation")
        implementation("dev.teogor.ceres:navigation-ui:$teogorCeresNavigation")
    }
    ```

### Managing Navigation Versions with Version Catalog (Recommended)

This section guides you through utilizing a version catalog for centralized management of Navigation dependencies in your project. This approach simplifies updates and ensures consistency.

First, define the dependencies in the `libs.versions.toml` file:

- **Group-Name Based:** This approach is used for declaring libraries referenced by group and artifact name.
- **Module Based:** This approach is used for declaring libraries referenced by their module.

=== "Group-Name Based"

    ```toml title="gradle/libs.versions.toml"
    [versions]
    teogor-ceres-navigation = "1.0.0-alpha03"
    
    [libraries]
    teogor-ceres-navigation-common = { group = "dev.teogor.ceres", name = "navigation-common", version.ref = "teogor-ceres-navigation" }
    teogor-ceres-navigation-core = { group = "dev.teogor.ceres", name = "navigation-core", version.ref = "teogor-ceres-navigation" }
    teogor-ceres-navigation-events = { group = "dev.teogor.ceres", name = "navigation-events", version.ref = "teogor-ceres-navigation" }
    teogor-ceres-navigation-screen = { group = "dev.teogor.ceres", name = "navigation-screen", version.ref = "teogor-ceres-navigation" }
    teogor-ceres-navigation-ui = { group = "dev.teogor.ceres", name = "navigation-ui", version.ref = "teogor-ceres-navigation" }
    ```

=== "Module Based"

    ```toml title="gradle/libs.versions.toml"
    [versions]
    teogor-ceres-navigation = "1.0.0-alpha03"
    
    [libraries]
    teogor-ceres-navigation-common = { module = "dev.teogor.ceres:navigation-common", version.ref = "teogor-ceres-navigation" }
    teogor-ceres-navigation-core = { module = "dev.teogor.ceres:navigation-core", version.ref = "teogor-ceres-navigation" }
    teogor-ceres-navigation-events = { module = "dev.teogor.ceres:navigation-events", version.ref = "teogor-ceres-navigation" }
    teogor-ceres-navigation-screen = { module = "dev.teogor.ceres:navigation-screen", version.ref = "teogor-ceres-navigation" }
    teogor-ceres-navigation-ui = { module = "dev.teogor.ceres:navigation-ui", version.ref = "teogor-ceres-navigation" }
    ```

Then, add these dependencies in your app's `build.gradle` file:

=== "Groovy"

    ```groovy title="build.gradle"
    dependencies {
        implementation libs.teogor.ceres.navigation.common
        implementation libs.teogor.ceres.navigation.core
        implementation libs.teogor.ceres.navigation.events
        implementation libs.teogor.ceres.navigation.screen
        implementation libs.teogor.ceres.navigation.ui
    }
    ```

=== "Kotlin"

    ```kotlin title="build.gradle.kts"
    dependencies {
        implementation(libs.teogor.ceres.navigation.common)
        implementation(libs.teogor.ceres.navigation.core)
        implementation(libs.teogor.ceres.navigation.events)
        implementation(libs.teogor.ceres.navigation.screen)
        implementation(libs.teogor.ceres.navigation.ui)
    }
    ```

[//]: # (REGION-DEPENDENCIES)

