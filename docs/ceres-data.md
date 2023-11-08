## Ceres data

| Status | Library | Gradle dependency |
| ------ | ------- | ----------------- |
| 🧪 | [compose](/data/compose) | [dev.teogor.ceres:data-compose:1.0.0-alpha01](#implementation-compose) |
| 🧪 | [database](/data/database) | [dev.teogor.ceres:data-database:1.0.0-alpha01](#implementation-database) |
| 🧪 | [datastore](/data/datastore) | [dev.teogor.ceres:data-datastore:1.0.0-alpha01](#implementation-datastore) |

By referring to the [BoM documentation](/docs/bom/versions.md), you can learn how to integrate the BoM into your project and benefit from this hassle-free approach to library version management. It's a powerful tool for staying up-to-date with the latest Ceres library versions and seamlessly integrating them into your projects.


### Implementation Compose

To use compose in your project, add the following dependency to your module-level Gradle file (usually `app/build.gradle.kts`):

```kotlin
implementation("dev.teogor.ceres:data-compose:1.0.0-alpha01")
```

#### Gradle Dependency

- **Group ID:** `dev.teogor.ceres`
- **Artifact ID:** `data-compose`
- **Version:** `1.0.0-alpha01` (not required when using [BoM](/docs/bom/versions.md))

⚠️ Depends on `:data:database`,`:data:datastore`,`:data:compose`,`:data:database`,`:data:datastore`,`:data:compose`,`:data:compose`,`:data:database`,`:data:datastore`,`:data:compose`

### Implementation Database

To use database in your project, add the following dependency to your module-level Gradle file (usually `app/build.gradle.kts`):

```kotlin
implementation("dev.teogor.ceres:data-database:1.0.0-alpha01")
```

#### Gradle Dependency

- **Group ID:** `dev.teogor.ceres`
- **Artifact ID:** `data-database`
- **Version:** `1.0.0-alpha01` (not required when using [BoM](/docs/bom/versions.md))

⚠️ Depends on `:data:database`,`:data:database`,`:data:database`,`:data:database`

### Implementation Datastore

To use datastore in your project, add the following dependency to your module-level Gradle file (usually `app/build.gradle.kts`):

```kotlin
implementation("dev.teogor.ceres:data-datastore:1.0.0-alpha01")
```

#### Gradle Dependency

- **Group ID:** `dev.teogor.ceres`
- **Artifact ID:** `data-datastore`
- **Version:** `1.0.0-alpha01` (not required when using [BoM](/docs/bom/versions.md))

⚠️ Depends on `:core:startup`,`:data:datastore`,`:core:startup`,`:data:datastore`,`:data:datastore`,`:core:startup`,`:data:datastore`


