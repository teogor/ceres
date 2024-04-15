# Screen

[//]: # (REGION-DEPENDENCIES)

## Getting Started with Screen

**Adding Dependencies:**

* **Manual Setup:**  This section guides you through adding Screen dependencies directly to your project's `build.gradle` files. ([Link to Manual Dependency Setup Section](#adding-screen-dependencies-manually))
* **Version Catalog (Recommended):** For a more streamlined approach, consider integrating a version catalog. This allows for centralized version management and easier updates. ([Link to Version Catalog Section](#managing-screen-versions-with-version-catalog-recommended))

**Note:** If you prefer manual dependency setup, follow the instructions in the "Manual Setup" section. Otherwise, jump to the "Version Catalog" section for centralized management.

For information on using the KAPT plugin, see the [KAPT documentation](https://kotlinlang.org/docs/kapt.html).  
For information on using the KSP plugin, see the [KSP quick-start documentation](https://kotlinlang.org/docs/ksp-quickstart.html).  
For more information about dependencies, see [Add Build Dependencies](https://developer.android.com/studio/build/dependencies).  

### Adding Screen Dependencies Manually

To use Screen in your app, add the following dependencies to your app's `build.gradle` file:

=== "Groovy"

    ```groovy title="build.gradle"
    dependencies {
        def teogorCeresScreen = "1.0.0-alpha05"
        
        implementation "dev.teogor.ceres:screen-builder:$teogorCeresScreen"
        implementation "dev.teogor.ceres:screen-core:$teogorCeresScreen"
        implementation "dev.teogor.ceres:screen-ui:$teogorCeresScreen"
    }
    ```

=== "Kotlin"

    ```kotlin title="build.gradle.kts"
    dependencies {
        val teogorCeresScreen = "1.0.0-alpha05"
        
        implementation("dev.teogor.ceres:screen-builder:$teogorCeresScreen")
        implementation("dev.teogor.ceres:screen-core:$teogorCeresScreen")
        implementation("dev.teogor.ceres:screen-ui:$teogorCeresScreen")
    }
    ```

### Managing Screen Versions with Version Catalog (Recommended)

This section guides you through utilizing a version catalog for centralized management of Screen dependencies in your project. This approach simplifies updates and ensures consistency.

First, define the dependencies in the `libs.versions.toml` file:

- **Group-Name Based:** This approach is used for declaring libraries referenced by group and artifact name.
- **Module Based:** This approach is used for declaring libraries referenced by their module.

=== "Group-Name Based"

    ```toml title="gradle/libs.versions.toml"
    [versions]
    teogor-ceres-screen = "1.0.0-alpha05"
    
    [libraries]
    teogor-ceres-screen-builder = { group = "dev.teogor.ceres", name = "screen-builder", version.ref = "teogor-ceres-screen" }
    teogor-ceres-screen-core = { group = "dev.teogor.ceres", name = "screen-core", version.ref = "teogor-ceres-screen" }
    teogor-ceres-screen-ui = { group = "dev.teogor.ceres", name = "screen-ui", version.ref = "teogor-ceres-screen" }
    ```

=== "Module Based"

    ```toml title="gradle/libs.versions.toml"
    [versions]
    teogor-ceres-screen = "1.0.0-alpha05"
    
    [libraries]
    teogor-ceres-screen-builder = { module = "dev.teogor.ceres:screen-builder", version.ref = "teogor-ceres-screen" }
    teogor-ceres-screen-core = { module = "dev.teogor.ceres:screen-core", version.ref = "teogor-ceres-screen" }
    teogor-ceres-screen-ui = { module = "dev.teogor.ceres:screen-ui", version.ref = "teogor-ceres-screen" }
    ```

Then, add these dependencies in your app's `build.gradle` file:

=== "Groovy"

    ```groovy title="build.gradle"
    dependencies {
        implementation libs.teogor.ceres.screen.builder
        implementation libs.teogor.ceres.screen.core
        implementation libs.teogor.ceres.screen.ui
    }
    ```

=== "Kotlin"

    ```kotlin title="build.gradle.kts"
    dependencies {
        implementation(libs.teogor.ceres.screen.builder)
        implementation(libs.teogor.ceres.screen.core)
        implementation(libs.teogor.ceres.screen.ui)
    }
    ```

[//]: # (REGION-DEPENDENCIES)

