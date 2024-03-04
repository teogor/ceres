# Ceres Firebase

Learn more: **[User Guide](../user-guide.md)** and **[Code Samples](../code-samples.md)**

Ceres is a comprehensive Android development framework designed to streamline your app development process. Powered by the latest technologies like Jetpack Compose, Hilt, Coroutines, and Flow, Ceres empowers developers to build modern and efficient Android applications.

[//]: # (REGION-API-REFERENCE)

API Reference  
[`dev.teogor.ceres:firebase-*`](../html/firebase)  
[`dev.teogor.ceres:firebase-crashlytics`](../html/firebase/crashlytics)  
[`dev.teogor.ceres:firebase-analytics`](../html/firebase/firebase-analytics)  
[`dev.teogor.ceres:firebase-remote-config`](../html/firebase/remote-config)

[//]: # (REGION-API-REFERENCE)

[//]: # (REGION-RELEASE-TABLE)

| Latest Update       |  Stable Release  |  Release Candidate  |  Beta Release  |  Alpha Release  |
|:--------------------|:----------------:|:-------------------:|:--------------:|:---------------:|
| November 23, 2023   |        -         |          -          |       -        |  1.0.0-alpha04  |

[//]: # (REGION-RELEASE-TABLE)

[//]: # (REGION-DEPENDENCIES)

## Declaring dependencies

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

For comprehensive instructions on adding these dependencies, refer to the [Firebase documentation](../firebase/index.md#getting-started-with-firebase).

[//]: # (REGION-DEPENDENCIES)

[//]: # (REGION-FEEDBACK)

## Feedback

Your feedback helps make Firebase better. Let us know if you discover new issues or have
ideas for improving this library. Please take a look at the [existing issues on GitHub](https://github.com/teogor/ceres/issues)
for this library before you create a new one.

[Create a new issue](https://github.com/teogor/ceres/issues/new){ .md-button }

[//]: # (REGION-FEEDBACK)

[//]: # (REGION-VERSION-CHANGELOG)



[//]: # (REGION-VERSION-CHANGELOG)

