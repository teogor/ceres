## Ceres backup

| Status | Library | Gradle dependency |
| ------ | ------- | ----------------- |
| 🧪 | [core](/backup/core) | [dev.teogor.ceres:backup-core:1.0.0-alpha01](#implementation-core) |
| 🧪 | [ui](/backup/ui) | [dev.teogor.ceres:backup-ui:1.0.0-alpha01](#implementation-ui) |

By referring to the [BoM documentation](/docs/bom/versions.md), you can learn how to integrate the BoM into your project and benefit from this hassle-free approach to library version management. It's a powerful tool for staying up-to-date with the latest Ceres library versions and seamlessly integrating them into your projects.


### Implementation Core

To use core in your project, add the following dependency to your module-level Gradle file (usually `app/build.gradle.kts`):

```kotlin
implementation("dev.teogor.ceres:backup-core:1.0.0-alpha01")
```

#### Gradle Dependency

- **Group ID:** `dev.teogor.ceres`
- **Artifact ID:** `backup-core`
- **Version:** `1.0.0-alpha01` (not required when using [BoM](/docs/bom/versions.md))

⚠️ Depends on `:data:database`,`:data:datastore`,`:backup:core`,`:data:database`,`:data:datastore`,`:backup:core`,`:backup:core`,`:data:database`,`:data:datastore`,`:backup:core`

### Implementation UI

To use ui in your project, add the following dependency to your module-level Gradle file (usually `app/build.gradle.kts`):

```kotlin
implementation("dev.teogor.ceres:backup-ui:1.0.0-alpha01")
```

#### Gradle Dependency

- **Group ID:** `dev.teogor.ceres`
- **Artifact ID:** `backup-ui`
- **Version:** `1.0.0-alpha01` (not required when using [BoM](/docs/bom/versions.md))

⚠️ Depends on `:backup:ui`,`:backup:ui`,`:backup:ui`,`:backup:ui`


