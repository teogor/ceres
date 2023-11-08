# Ceres BoM v1.0.0-alpha01 (Bill of Materials)

The Ceres BoM (Bill of Materials) enables you to manage all your Ceres library versions by specifying only one version — the BoM's version.

When you use the Ceres BoM in your app, the BoM automatically pulls in the individual library versions mapped to the BoM's version. All the individual library versions will be compatible. When you update the BoM's version in your app, all the Ceres libraries that you use in your app will update to the versions mapped to that BoM version.

To learn which Ceres library versions are mapped to a specific BoM version, check out the release notes for that BoM version. If you need to compare the library versions mapped to one BoM version compared to another BoM version, use the comparison widget below.

Learn more about [Gradle's support for BoM platforms](https://docs.gradle.org/4.6-rc-1/userguide/managing_transitive_dependencies.html#sec:bom_import).

Here's how to use the Ceres BoM to declare dependencies in your module (app-level) Gradle file (usually app/build.gradle.kts). When using the BoM, you don't specify individual library versions in the dependency lines.

```kt
dependencies {
  // Import the BoM for the Ceres platform
  implementation(platform("dev.teogor.ceres:bom:1.0.0-alpha01"))

  // Declare the dependencies for the desired Ceres products
  // without specifying versions. For example, declare:
  // Ceres Screen Builder
  implementation("dev.teogor.ceres:screen-builder")
  // Ceres Framework Core
  implementation("dev.teogor.ceres:framework-core")
  // Ceres Monetisation AdMob
  implementation("dev.teogor.ceres:monetisation-admob")
}
```

## Latest SDK versions

| Status | Service or Product | Gradle dependency | Latest version |
| ------ | ------------------ | ----------------- | -------------- |
| 🧪 | [core](/backup/core) | dev.teogor.ceres:backup-core | 1.0.0-alpha01 |
| 🧪 | [ui](/backup/ui) | dev.teogor.ceres:backup-ui | 1.0.0-alpha01 |
| 🧪 | [network](/core/network) | dev.teogor.ceres:core-network | 1.0.0-alpha01 |
| 🧪 | [notifications](/core/notifications) | dev.teogor.ceres:core-notifications | 1.0.0-alpha01 |
| 🧪 | [runtime](/core/runtime) | dev.teogor.ceres:core-runtime | 1.0.0-alpha01 |
| 🧪 | [startup](/core/startup) | dev.teogor.ceres:core-startup | 1.0.0-alpha01 |
| 🧪 | [compose](/data/compose) | dev.teogor.ceres:data-compose | 1.0.0-alpha01 |
| 🧪 | [database](/data/database) | dev.teogor.ceres:data-database | 1.0.0-alpha01 |
| 🧪 | [datastore](/data/datastore) | dev.teogor.ceres:data-datastore | 1.0.0-alpha01 |
| 🧪 | [analytics](/firebase/analytics) | dev.teogor.ceres:firebase-analytics | 1.0.0-alpha01 |
| 🧪 | [crashlytics](/firebase/crashlytics) | dev.teogor.ceres:firebase-crashlytics | 1.0.0-alpha01 |
| 🧪 | [remoteconfig](/firebase/remote-config) | dev.teogor.ceres:firebase-remoteconfig | 1.0.0-alpha01 |
| 🧪 | [core](/framework/core) | dev.teogor.ceres:framework-core | 1.0.0-alpha01 |
| 🧪 | [ui](/framework/ui) | dev.teogor.ceres:framework-ui | 1.0.0-alpha01 |
| 🧪 | [admob](/monetisation/admob) | dev.teogor.ceres:monetisation-admob | 1.0.0-alpha01 |
| 🧪 | [messaging](/monetisation/messaging) | dev.teogor.ceres:monetisation-messaging | 1.0.0-alpha01 |
| 🧪 | [common](/navigation/common) | dev.teogor.ceres:navigation-common | 1.0.0-alpha01 |
| 🧪 | [core](/navigation/core) | dev.teogor.ceres:navigation-core | 1.0.0-alpha01 |
| 🧪 | [events](/navigation/events) | dev.teogor.ceres:navigation-events | 1.0.0-alpha01 |
| 🧪 | [screen](/navigation/screen) | dev.teogor.ceres:navigation-screen | 1.0.0-alpha01 |
| 🧪 | [ui](/navigation/ui) | dev.teogor.ceres:navigation-ui | 1.0.0-alpha01 |
| 🧪 | [builder](/screen/builder) | dev.teogor.ceres:screen-builder | 1.0.0-alpha01 |
| 🧪 | [core](/screen/core) | dev.teogor.ceres:screen-core | 1.0.0-alpha01 |
| 🧪 | [ui](/screen/ui) | dev.teogor.ceres:screen-ui | 1.0.0-alpha01 |
| 🧪 | [compose](/ui/compose) | dev.teogor.ceres:ui-compose | 1.0.0-alpha01 |
| 🧪 | [design.system](/ui/designsystem) | dev.teogor.ceres:ui-design.system | 1.0.0-alpha01 |
| 🧪 | [foundation](/ui/foundation) | dev.teogor.ceres:ui-foundation | 1.0.0-alpha01 |
| 🧪 | [icons](/ui/icons) | dev.teogor.ceres:ui-icons | 1.0.0-alpha01 |
| 🧪 | [spectrum](/ui/spectrum) | dev.teogor.ceres:ui-spectrum | 1.0.0-alpha01 |
| 🧪 | [theme](/ui/theme) | dev.teogor.ceres:ui-theme | 1.0.0-alpha01 |

### Explore Further

For the latest updates, in-depth documentation, and a comprehensive overview of the Ceres ecosystem, visit the official [Ceres documentation](/docs/). It's your gateway to a wealth of resources and insights that will elevate your Ceres development journey.

Stay informed, stay current, and embrace the full potential of Ceres.
