## Ceres monetisation

| Status | Library | Gradle dependency |
| ------ | ------- | ----------------- |
| 🧪 | [admob](/monetisation/admob) | [dev.teogor.ceres:monetisation-admob:1.0.0-alpha01](#implementation-admob) |
| 🧪 | [messaging](/monetisation/messaging) | [dev.teogor.ceres:monetisation-messaging:1.0.0-alpha01](#implementation-messaging) |

By referring to the [BoM documentation](/docs/bom/versions.md), you can learn how to integrate the BoM into your project and benefit from this hassle-free approach to library version management. It's a powerful tool for staying up-to-date with the latest Ceres library versions and seamlessly integrating them into your projects.


### Implementation AdMob

To use admob in your project, add the following dependency to your module-level Gradle file (usually `app/build.gradle.kts`):

```kotlin
implementation("dev.teogor.ceres:monetisation-admob:1.0.0-alpha01")
```

#### Gradle Dependency

- **Group ID:** `dev.teogor.ceres`
- **Artifact ID:** `monetisation-admob`
- **Version:** `1.0.0-alpha01` (not required when using [BoM](/docs/bom/versions.md))

⚠️ Depends on `:monetisation:admob`,`:core:runtime`,`:core:network`,`:ui:designsystem`,`:monetisation:admob`,`:core:runtime`,`:core:network`,`:ui:designsystem`,`:monetisation:admob`,`:core:runtime`,`:core:network`,`:ui:designsystem`,`:monetisation:admob`

### Implementation Messaging

To use messaging in your project, add the following dependency to your module-level Gradle file (usually `app/build.gradle.kts`):

```kotlin
implementation("dev.teogor.ceres:monetisation-messaging:1.0.0-alpha01")
```

#### Gradle Dependency

- **Group ID:** `dev.teogor.ceres`
- **Artifact ID:** `monetisation-messaging`
- **Version:** `1.0.0-alpha01` (not required when using [BoM](/docs/bom/versions.md))

⚠️ Depends on `:core:runtime`,`:core:startup`,`:monetisation:admob`,`:monetisation:messaging`,`:core:runtime`,`:core:startup`,`:monetisation:admob`,`:monetisation:messaging`,`:monetisation:messaging`,`:core:runtime`,`:core:startup`,`:monetisation:admob`,`:monetisation:messaging`


