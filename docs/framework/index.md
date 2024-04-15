# Framework

[//]: # (REGION-DEPENDENCIES)

## Getting Started with Framework

**Adding Dependencies:**

* **Manual Setup:**  This section guides you through adding Framework dependencies directly to your project's `build.gradle` files. ([Link to Manual Dependency Setup Section](#adding-framework-dependencies-manually))
* **Version Catalog (Recommended):** For a more streamlined approach, consider integrating a version catalog. This allows for centralized version management and easier updates. ([Link to Version Catalog Section](#managing-framework-versions-with-version-catalog-recommended))

**Note:** If you prefer manual dependency setup, follow the instructions in the "Manual Setup" section. Otherwise, jump to the "Version Catalog" section for centralized management.

For information on using the KAPT plugin, see the [KAPT documentation](https://kotlinlang.org/docs/kapt.html).  
For information on using the KSP plugin, see the [KSP quick-start documentation](https://kotlinlang.org/docs/ksp-quickstart.html).  
For more information about dependencies, see [Add Build Dependencies](https://developer.android.com/studio/build/dependencies).  

### Adding Framework Dependencies Manually

To use Framework in your app, add the following dependencies to your app's `build.gradle` file:

=== "Groovy"

    ```groovy title="build.gradle"
    dependencies {
        def teogorCeresFramework = "1.0.0-alpha04"
        
        implementation "dev.teogor.ceres:framework-core:$teogorCeresFramework"
        implementation "dev.teogor.ceres:framework-ui:$teogorCeresFramework"
    }
    ```

=== "Kotlin"

    ```kotlin title="build.gradle.kts"
    dependencies {
        val teogorCeresFramework = "1.0.0-alpha04"
        
        implementation("dev.teogor.ceres:framework-core:$teogorCeresFramework")
        implementation("dev.teogor.ceres:framework-ui:$teogorCeresFramework")
    }
    ```

### Managing Framework Versions with Version Catalog (Recommended)

This section guides you through utilizing a version catalog for centralized management of Framework dependencies in your project. This approach simplifies updates and ensures consistency.

First, define the dependencies in the `libs.versions.toml` file:

- **Group-Name Based:** This approach is used for declaring libraries referenced by group and artifact name.
- **Module Based:** This approach is used for declaring libraries referenced by their module.

=== "Group-Name Based"

    ```toml title="gradle/libs.versions.toml"
    [versions]
    teogor-ceres-framework = "1.0.0-alpha04"
    
    [libraries]
    teogor-ceres-framework-core = { group = "dev.teogor.ceres", name = "framework-core", version.ref = "teogor-ceres-framework" }
    teogor-ceres-framework-ui = { group = "dev.teogor.ceres", name = "framework-ui", version.ref = "teogor-ceres-framework" }
    ```

=== "Module Based"

    ```toml title="gradle/libs.versions.toml"
    [versions]
    teogor-ceres-framework = "1.0.0-alpha04"
    
    [libraries]
    teogor-ceres-framework-core = { module = "dev.teogor.ceres:framework-core", version.ref = "teogor-ceres-framework" }
    teogor-ceres-framework-ui = { module = "dev.teogor.ceres:framework-ui", version.ref = "teogor-ceres-framework" }
    ```

Then, add these dependencies in your app's `build.gradle` file:

=== "Groovy"

    ```groovy title="build.gradle"
    dependencies {
        implementation libs.teogor.ceres.framework.core
        implementation libs.teogor.ceres.framework.ui
    }
    ```

=== "Kotlin"

    ```kotlin title="build.gradle.kts"
    dependencies {
        implementation(libs.teogor.ceres.framework.core)
        implementation(libs.teogor.ceres.framework.ui)
    }
    ```

[//]: # (REGION-DEPENDENCIES)

