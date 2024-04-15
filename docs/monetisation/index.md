# Monetisation

[//]: # (REGION-DEPENDENCIES)

## Getting Started with Monetisation

**Adding Dependencies:**

* **Manual Setup:**  This section guides you through adding Monetisation dependencies directly to your project's `build.gradle` files. ([Link to Manual Dependency Setup Section](#adding-monetisation-dependencies-manually))
* **Version Catalog (Recommended):** For a more streamlined approach, consider integrating a version catalog. This allows for centralized version management and easier updates. ([Link to Version Catalog Section](#managing-monetisation-versions-with-version-catalog-recommended))

**Note:** If you prefer manual dependency setup, follow the instructions in the "Manual Setup" section. Otherwise, jump to the "Version Catalog" section for centralized management.

For information on using the KAPT plugin, see the [KAPT documentation](https://kotlinlang.org/docs/kapt.html).  
For information on using the KSP plugin, see the [KSP quick-start documentation](https://kotlinlang.org/docs/ksp-quickstart.html).  
For more information about dependencies, see [Add Build Dependencies](https://developer.android.com/studio/build/dependencies).  

### Adding Monetisation Dependencies Manually

To use Monetisation in your app, add the following dependencies to your app's `build.gradle` file:

=== "Groovy"

    ```groovy title="build.gradle"
    dependencies {
        def teogorCeresMonetisation = "1.0.0-alpha05"
        
        implementation "dev.teogor.ceres:monetisation-admob:$teogorCeresMonetisation"
        implementation "dev.teogor.ceres:monetisation-ads:$teogorCeresMonetisation"
        implementation "dev.teogor.ceres:monetisation-messaging:$teogorCeresMonetisation"
    }
    ```

=== "Kotlin"

    ```kotlin title="build.gradle.kts"
    dependencies {
        val teogorCeresMonetisation = "1.0.0-alpha05"
        
        implementation("dev.teogor.ceres:monetisation-admob:$teogorCeresMonetisation")
        implementation("dev.teogor.ceres:monetisation-ads:$teogorCeresMonetisation")
        implementation("dev.teogor.ceres:monetisation-messaging:$teogorCeresMonetisation")
    }
    ```

### Managing Monetisation Versions with Version Catalog (Recommended)

This section guides you through utilizing a version catalog for centralized management of Monetisation dependencies in your project. This approach simplifies updates and ensures consistency.

First, define the dependencies in the `libs.versions.toml` file:

- **Group-Name Based:** This approach is used for declaring libraries referenced by group and artifact name.
- **Module Based:** This approach is used for declaring libraries referenced by their module.

=== "Group-Name Based"

    ```toml title="gradle/libs.versions.toml"
    [versions]
    teogor-ceres-monetisation = "1.0.0-alpha05"
    
    [libraries]
    teogor-ceres-monetisation-admob = { group = "dev.teogor.ceres", name = "monetisation-admob", version.ref = "teogor-ceres-monetisation" }
    teogor-ceres-monetisation-ads = { group = "dev.teogor.ceres", name = "monetisation-ads", version.ref = "teogor-ceres-monetisation" }
    teogor-ceres-monetisation-messaging = { group = "dev.teogor.ceres", name = "monetisation-messaging", version.ref = "teogor-ceres-monetisation" }
    ```

=== "Module Based"

    ```toml title="gradle/libs.versions.toml"
    [versions]
    teogor-ceres-monetisation = "1.0.0-alpha05"
    
    [libraries]
    teogor-ceres-monetisation-admob = { module = "dev.teogor.ceres:monetisation-admob", version.ref = "teogor-ceres-monetisation" }
    teogor-ceres-monetisation-ads = { module = "dev.teogor.ceres:monetisation-ads", version.ref = "teogor-ceres-monetisation" }
    teogor-ceres-monetisation-messaging = { module = "dev.teogor.ceres:monetisation-messaging", version.ref = "teogor-ceres-monetisation" }
    ```

Then, add these dependencies in your app's `build.gradle` file:

=== "Groovy"

    ```groovy title="build.gradle"
    dependencies {
        implementation libs.teogor.ceres.monetisation.admob
        implementation libs.teogor.ceres.monetisation.ads
        implementation libs.teogor.ceres.monetisation.messaging
    }
    ```

=== "Kotlin"

    ```kotlin title="build.gradle.kts"
    dependencies {
        implementation(libs.teogor.ceres.monetisation.admob)
        implementation(libs.teogor.ceres.monetisation.ads)
        implementation(libs.teogor.ceres.monetisation.messaging)
    }
    ```

[//]: # (REGION-DEPENDENCIES)

