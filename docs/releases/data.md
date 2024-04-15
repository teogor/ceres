# Ceres Data

Learn more: **[User Guide](../user-guide.md)** and **[Code Samples](../code-samples.md)**

Ceres is a comprehensive Android development framework designed to streamline your app development process. Powered by the latest technologies like Jetpack Compose, Hilt, Coroutines, and Flow, Ceres empowers developers to build modern and efficient Android applications.

[//]: # (REGION-API-REFERENCE)

API Reference  
[`dev.teogor.ceres:data-*`](../html/data)  
[`dev.teogor.ceres:data-compose`](../html/data/data-compose)  
[`dev.teogor.ceres:data-database`](../html/data/database)  
[`dev.teogor.ceres:data-datastore`](../html/data/datastore)

[//]: # (REGION-API-REFERENCE)

[//]: # (REGION-RELEASE-TABLE)

| Latest Update       |  Stable Release  |  Release Candidate  |  Beta Release  |  Alpha Release  |
|:--------------------|:----------------:|:-------------------:|:--------------:|:---------------:|
| November 23, 2023   |        -         |          -          |       -        |  1.0.0-alpha02  |

[//]: # (REGION-RELEASE-TABLE)

[//]: # (REGION-DEPENDENCIES)

## Declaring dependencies

To use Data in your app, add the following dependencies to your app's `build.gradle` file:

=== "Groovy"

    ```groovy title="build.gradle"
    dependencies {
        def teogorCeresData = "1.0.0-alpha02"
        
        implementation "dev.teogor.ceres:data-compose:$teogorCeresData"
        implementation "dev.teogor.ceres:data-database:$teogorCeresData"
        implementation "dev.teogor.ceres:data-datastore:$teogorCeresData"
    }
    ```

=== "Kotlin"

    ```kotlin title="build.gradle.kts"
    dependencies {
        val teogorCeresData = "1.0.0-alpha02"
        
        implementation("dev.teogor.ceres:data-compose:$teogorCeresData")
        implementation("dev.teogor.ceres:data-database:$teogorCeresData")
        implementation("dev.teogor.ceres:data-datastore:$teogorCeresData")
    }
    ```

For comprehensive instructions on adding these dependencies, refer to the [Data documentation](../data/index.md#getting-started-with-data).

[//]: # (REGION-DEPENDENCIES)

[//]: # (REGION-FEEDBACK)

## Feedback

Your feedback helps make Data better. Let us know if you discover new issues or have
ideas for improving this library. Please take a look at the [existing issues on GitHub](https://github.com/teogor/ceres/issues)
for this library before you create a new one.

[Create a new issue](https://github.com/teogor/ceres/issues/new){ .md-button }

[//]: # (REGION-FEEDBACK)

[//]: # (REGION-VERSION-CHANGELOG)



[//]: # (REGION-VERSION-CHANGELOG)

