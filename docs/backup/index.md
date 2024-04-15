# Backup

[//]: # (REGION-DEPENDENCIES)

## Getting Started with Backup

**Adding Dependencies:**

* **Manual Setup:**  This section guides you through adding Backup dependencies directly to your project's `build.gradle` files. ([Link to Manual Dependency Setup Section](#adding-backup-dependencies-manually))
* **Version Catalog (Recommended):** For a more streamlined approach, consider integrating a version catalog. This allows for centralized version management and easier updates. ([Link to Version Catalog Section](#managing-backup-versions-with-version-catalog-recommended))

**Note:** If you prefer manual dependency setup, follow the instructions in the "Manual Setup" section. Otherwise, jump to the "Version Catalog" section for centralized management.

For information on using the KAPT plugin, see the [KAPT documentation](https://kotlinlang.org/docs/kapt.html).  
For information on using the KSP plugin, see the [KSP quick-start documentation](https://kotlinlang.org/docs/ksp-quickstart.html).  
For more information about dependencies, see [Add Build Dependencies](https://developer.android.com/studio/build/dependencies).  

### Adding Backup Dependencies Manually

To use Backup in your app, add the following dependencies to your app's `build.gradle` file:

=== "Groovy"

    ```groovy title="build.gradle"
    dependencies {
        def teogorCeresBackup = "1.0.0-alpha01"
        
        implementation "dev.teogor.ceres:backup-core:$teogorCeresBackup"
        implementation "dev.teogor.ceres:backup-ui:$teogorCeresBackup"
    }
    ```

=== "Kotlin"

    ```kotlin title="build.gradle.kts"
    dependencies {
        val teogorCeresBackup = "1.0.0-alpha01"
        
        implementation("dev.teogor.ceres:backup-core:$teogorCeresBackup")
        implementation("dev.teogor.ceres:backup-ui:$teogorCeresBackup")
    }
    ```

### Managing Backup Versions with Version Catalog (Recommended)

This section guides you through utilizing a version catalog for centralized management of Backup dependencies in your project. This approach simplifies updates and ensures consistency.

First, define the dependencies in the `libs.versions.toml` file:

- **Group-Name Based:** This approach is used for declaring libraries referenced by group and artifact name.
- **Module Based:** This approach is used for declaring libraries referenced by their module.

=== "Group-Name Based"

    ```toml title="gradle/libs.versions.toml"
    [versions]
    teogor-ceres-backup = "1.0.0-alpha01"
    
    [libraries]
    teogor-ceres-backup-core = { group = "dev.teogor.ceres", name = "backup-core", version.ref = "teogor-ceres-backup" }
    teogor-ceres-backup-ui = { group = "dev.teogor.ceres", name = "backup-ui", version.ref = "teogor-ceres-backup" }
    ```

=== "Module Based"

    ```toml title="gradle/libs.versions.toml"
    [versions]
    teogor-ceres-backup = "1.0.0-alpha01"
    
    [libraries]
    teogor-ceres-backup-core = { module = "dev.teogor.ceres:backup-core", version.ref = "teogor-ceres-backup" }
    teogor-ceres-backup-ui = { module = "dev.teogor.ceres:backup-ui", version.ref = "teogor-ceres-backup" }
    ```

Then, add these dependencies in your app's `build.gradle` file:

=== "Groovy"

    ```groovy title="build.gradle"
    dependencies {
        implementation libs.teogor.ceres.backup.core
        implementation libs.teogor.ceres.backup.ui
    }
    ```

=== "Kotlin"

    ```kotlin title="build.gradle.kts"
    dependencies {
        implementation(libs.teogor.ceres.backup.core)
        implementation(libs.teogor.ceres.backup.ui)
    }
    ```

[//]: # (REGION-DEPENDENCIES)

