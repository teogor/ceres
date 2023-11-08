## Ceres framework

| Status | Library | Gradle dependency |
| ------ | ------- | ----------------- |
| 🧪 | [core](/framework/core) | [dev.teogor.ceres:framework-core:1.0.0-alpha01](#implementation-core) |
| 🧪 | [ui](/framework/ui) | [dev.teogor.ceres:framework-ui:1.0.0-alpha01](#implementation-ui) |

By referring to the [BoM documentation](/docs/bom/versions.md), you can learn how to integrate the BoM into your project and benefit from this hassle-free approach to library version management. It's a powerful tool for staying up-to-date with the latest Ceres library versions and seamlessly integrating them into your projects.


### Implementation Core

To use core in your project, add the following dependency to your module-level Gradle file (usually `app/build.gradle.kts`):

```kotlin
implementation("dev.teogor.ceres:framework-core:1.0.0-alpha01")
```

#### Gradle Dependency

- **Group ID:** `dev.teogor.ceres`
- **Artifact ID:** `framework-core`
- **Version:** `1.0.0-alpha01` (not required when using [BoM](/docs/bom/versions.md))

⚠️ Depends on `:core:network`,`:core:runtime`,`:data:compose`,`:data:datastore`,`:navigation:core`,`:ui:designsystem`,`:ui:foundation`,`:ui:theme`,`:ui:compose`,`:firebase:analytics`,`:firebase:crashlytics`,`:framework:core`,`:core:network`,`:core:runtime`,`:data:compose`,`:data:datastore`,`:navigation:core`,`:ui:designsystem`,`:ui:foundation`,`:ui:theme`,`:ui:compose`,`:firebase:analytics`,`:firebase:crashlytics`,`:framework:core`,`:framework:core`,`:core:network`,`:core:runtime`,`:data:compose`,`:data:datastore`,`:navigation:core`,`:ui:designsystem`,`:ui:foundation`,`:ui:theme`,`:ui:compose`,`:firebase:analytics`,`:firebase:crashlytics`,`:framework:core`

### Implementation UI

To use ui in your project, add the following dependency to your module-level Gradle file (usually `app/build.gradle.kts`):

```kotlin
implementation("dev.teogor.ceres:framework-ui:1.0.0-alpha01")
```

#### Gradle Dependency

- **Group ID:** `dev.teogor.ceres`
- **Artifact ID:** `framework-ui`
- **Version:** `1.0.0-alpha01` (not required when using [BoM](/docs/bom/versions.md))

⚠️ Depends on `:framework:ui`,`:framework:ui`,`:framework:ui`,`:framework:ui`


