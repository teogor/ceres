# Firebase

[//]: # (REGION-DEPENDENCIES)

## Getting Started with Firebase

**Adding Dependencies:**

* **Manual Setup:**  This section guides you through adding Firebase dependencies directly to your project's `build.gradle` files. ([Link to Manual Dependency Setup Section](#adding-firebase-dependencies-manually))
* **Version Catalog (Recommended):** For a more streamlined approach, consider integrating a version catalog. This allows for centralized version management and easier updates. ([Link to Version Catalog Section](#managing-firebase-versions-with-version-catalog-recommended))

**Note:** If you prefer manual dependency setup, follow the instructions in the "Manual Setup" section. Otherwise, jump to the "Version Catalog" section for centralized management.

For information on using the KAPT plugin, see the [KAPT documentation](https://kotlinlang.org/docs/kapt.html).  
For information on using the KSP plugin, see the [KSP quick-start documentation](https://kotlinlang.org/docs/ksp-quickstart.html).  
For more information about dependencies, see [Add Build Dependencies](https://developer.android.com/studio/build/dependencies).  

### Adding Firebase Dependencies Manually

To use Firebase in your app, add the following dependencies to your app's `build.gradle` file:

=== "Groovy"

    ```groovy title="build.gradle"
    dependencies {
        def teogorCeresFirebase = "1.0.0-alpha04"
        
        implementation "dev.teogor.ceres:firebase-crashlytics:$teogorCeresFirebase"
        implementation "dev.teogor.ceres:firebase-analytics:$teogorCeresFirebase"
        implementation "dev.teogor.ceres:firebase-remote-config:$teogorCeresFirebase"
    }
    ```

=== "Kotlin"

    ```kotlin title="build.gradle.kts"
    dependencies {
        val teogorCeresFirebase = "1.0.0-alpha04"
        
        implementation("dev.teogor.ceres:firebase-crashlytics:$teogorCeresFirebase")
        implementation("dev.teogor.ceres:firebase-analytics:$teogorCeresFirebase")
        implementation("dev.teogor.ceres:firebase-remote-config:$teogorCeresFirebase")
    }
    ```

### Managing Firebase Versions with Version Catalog (Recommended)

This section guides you through utilizing a version catalog for centralized management of Firebase dependencies in your project. This approach simplifies updates and ensures consistency.

First, define the dependencies in the `libs.versions.toml` file:

- **Group-Name Based:** This approach is used for declaring libraries referenced by group and artifact name.
- **Module Based:** This approach is used for declaring libraries referenced by their module.

=== "Group-Name Based"

    ```toml title="gradle/libs.versions.toml"
    [versions]
    teogor-ceres-firebase = "1.0.0-alpha04"
    
    [libraries]
    teogor-ceres-firebase-crashlytics = { group = "dev.teogor.ceres", name = "firebase-crashlytics", version.ref = "teogor-ceres-firebase" }
    teogor-ceres-firebase-analytics = { group = "dev.teogor.ceres", name = "firebase-analytics", version.ref = "teogor-ceres-firebase" }
    teogor-ceres-firebase-remote-config = { group = "dev.teogor.ceres", name = "firebase-remote-config", version.ref = "teogor-ceres-firebase" }
    ```

=== "Module Based"

    ```toml title="gradle/libs.versions.toml"
    [versions]
    teogor-ceres-firebase = "1.0.0-alpha04"
    
    [libraries]
    teogor-ceres-firebase-crashlytics = { module = "dev.teogor.ceres:firebase-crashlytics", version.ref = "teogor-ceres-firebase" }
    teogor-ceres-firebase-analytics = { module = "dev.teogor.ceres:firebase-analytics", version.ref = "teogor-ceres-firebase" }
    teogor-ceres-firebase-remote-config = { module = "dev.teogor.ceres:firebase-remote-config", version.ref = "teogor-ceres-firebase" }
    ```

Then, add these dependencies in your app's `build.gradle` file:

=== "Groovy"

    ```groovy title="build.gradle"
    dependencies {
        implementation libs.teogor.ceres.firebase.crashlytics
        implementation libs.teogor.ceres.firebase.analytics
        implementation libs.teogor.ceres.firebase.remote.config
    }
    ```

=== "Kotlin"

    ```kotlin title="build.gradle.kts"
    dependencies {
        implementation(libs.teogor.ceres.firebase.crashlytics)
        implementation(libs.teogor.ceres.firebase.analytics)
        implementation(libs.teogor.ceres.firebase.remote.config)
    }
    ```

[//]: # (REGION-DEPENDENCIES)

