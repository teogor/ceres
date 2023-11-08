## Ceres screen

| Status | Library | Gradle dependency |
| ------ | ------- | ----------------- |
| 🧪 | [builder](/screen/builder) | [dev.teogor.ceres:screen-builder:1.0.0-alpha01](#implementation-builder) |
| 🧪 | [core](/screen/core) | [dev.teogor.ceres:screen-core:1.0.0-alpha01](#implementation-core) |
| 🧪 | [ui](/screen/ui) | [dev.teogor.ceres:screen-ui:1.0.0-alpha01](#implementation-ui) |

By referring to the [BoM documentation](/docs/bom/versions.md), you can learn how to integrate the BoM into your project and benefit from this hassle-free approach to library version management. It's a powerful tool for staying up-to-date with the latest Ceres library versions and seamlessly integrating them into your projects.


### Implementation Builder

To use builder in your project, add the following dependency to your module-level Gradle file (usually `app/build.gradle.kts`):

```kotlin
implementation("dev.teogor.ceres:screen-builder:1.0.0-alpha01")
```

#### Gradle Dependency

- **Group ID:** `dev.teogor.ceres`
- **Artifact ID:** `screen-builder`
- **Version:** `1.0.0-alpha01` (not required when using [BoM](/docs/bom/versions.md))

⚠️ Depends on `:screen:core`,`:screen:builder`,`:screen:core`,`:screen:builder`,`:screen:builder`,`:screen:core`,`:screen:builder`

### Implementation Core

To use core in your project, add the following dependency to your module-level Gradle file (usually `app/build.gradle.kts`):

```kotlin
implementation("dev.teogor.ceres:screen-core:1.0.0-alpha01")
```

#### Gradle Dependency

- **Group ID:** `dev.teogor.ceres`
- **Artifact ID:** `screen-core`
- **Version:** `1.0.0-alpha01` (not required when using [BoM](/docs/bom/versions.md))

⚠️ Depends on `:ui:designsystem`,`:ui:theme`,`:ui:compose`,`:navigation:events`,`:screen:core`,`:ui:designsystem`,`:ui:theme`,`:ui:compose`,`:navigation:events`,`:screen:core`,`:screen:core`,`:ui:designsystem`,`:ui:theme`,`:ui:compose`,`:navigation:events`,`:screen:core`

### Implementation UI

To use ui in your project, add the following dependency to your module-level Gradle file (usually `app/build.gradle.kts`):

```kotlin
implementation("dev.teogor.ceres:screen-ui:1.0.0-alpha01")
```

#### Gradle Dependency

- **Group ID:** `dev.teogor.ceres`
- **Artifact ID:** `screen-ui`
- **Version:** `1.0.0-alpha01` (not required when using [BoM](/docs/bom/versions.md))

⚠️ Depends on `:core:runtime`,`:framework:core`,`:ui:designsystem`,`:ui:theme`,`:ui:compose`,`:screen:builder`,`:navigation:events`,`:screen:ui`,`:core:runtime`,`:framework:core`,`:ui:designsystem`,`:ui:theme`,`:ui:compose`,`:screen:builder`,`:navigation:events`,`:screen:ui`,`:screen:ui`,`:core:runtime`,`:framework:core`,`:ui:designsystem`,`:ui:theme`,`:ui:compose`,`:screen:builder`,`:navigation:events`,`:screen:ui`


