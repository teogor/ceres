# Ceres BoM v1.0.0-alpha01 (Bill of Materials)

The Ceres BoM (Bill of Materials) enables you to manage all your Ceres library versions by specifying only one version — the BoM's version.

When you use the Ceres BoM in your app, the BoM automatically pulls in the individual library versions mapped to the BoM's version. All the individual library versions will be compatible. When you update the BoM's version in your app, all the Ceres libraries that you use in your app will update to the versions mapped to that BoM version.

To learn which Ceres library versions are mapped to a specific BoM version, check out the release notes for that BoM version. If you need to compare the library versions mapped to one BoM version compared to another BoM version, use the comparison widget below.

Learn more about [Gradle's support for BoM platforms](https://docs.gradle.org/4.6-rc-1/userguide/managing_transitive_dependencies.html#sec:bom_import).

Here's how to use the Ceres Android BoM to declare dependencies in your module (app-level) Gradle file (usually app/build.gradle.kts). When using the BoM, you don't specify individual library versions in the dependency lines.

```kt
dependencies {
  // Import the BoM for the Ceres platform
  implementation(platform("dev.teogor.ceres:bom:1.0.0-alpha01"))

  // Declare the dependencies for the desired Ceres products without specifying versions
  // For example, declare the dependencies for Ceres Core Runtime and Ceres Core Network
  implementation("dev.teogor.ceres:core-runtime")
  implementation("dev.teogor.ceres:core-network")
}
```

## Latest SDK versions

| Status | Service or Product | Gradle dependency | Latest version |
| ------ | ------------------ | ----------------- | -------------- |
| 🧪 | [Ceres Backup Core](/backup/core) | dev.teogor.ceres:backup-core  | 1.0.0-alpha01 |
| 🧪 | [Ceres Backup UI](/backup/ui) | dev.teogor.ceres:backup-ui  | 1.0.0-alpha01 |
| 🧪 | [Ceres Core Network](/core/network) | dev.teogor.ceres:core-network  | 1.0.0-alpha01 |
| 🧪 | [Ceres Core Notifications](/core/notifications) | dev.teogor.ceres:core-notifications  | 1.0.0-alpha01 |
| 🧪 | [Ceres Core Runtime](/core/runtime) | dev.teogor.ceres:core-runtime  | 1.0.0-alpha01 |
| 🧪 | [Ceres Core Startup](/core/startup) | dev.teogor.ceres:core-startup  | 1.0.0-alpha01 |
| 🧪 | [Ceres Data Compose](/data/compose) | dev.teogor.ceres:data-compose  | 1.0.0-alpha01 |
| 🧪 | [Ceres Data Database](/data/database) | dev.teogor.ceres:data-database  | 1.0.0-alpha01 |
| 🧪 | [Ceres Data Datastore](/data/datastore) | dev.teogor.ceres:data-datastore  | 1.0.0-alpha01 |
| 🧪 | [Ceres Firebase Analytics](/firebase/analytics) | dev.teogor.ceres:firebase-analytics  | 1.0.0-alpha01 |
| 🧪 | [Ceres Firebase Crashlytics](/firebase/crashlytics) | dev.teogor.ceres:firebase-crashlytics  | 1.0.0-alpha01 |
| 🧪 | [Ceres Firebase Remote-Config](/firebase/remote-config) | dev.teogor.ceres:firebase-remote-config  | 1.0.0-alpha01 |
| 🧪 | [Ceres Framework Core](/framework/core) | dev.teogor.ceres:framework-core  | 1.0.0-alpha01 |
| 🧪 | [Ceres Framework UI](/framework/ui) | dev.teogor.ceres:framework-ui  | 1.0.0-alpha01 |
| 🧪 | [Ceres Monetisation AdMob](/monetisation/admob) | dev.teogor.ceres:monetisation-admob  | 1.0.0-alpha01 |
| 🧪 | [Ceres Monetisation Messaging](/monetisation/messaging) | dev.teogor.ceres:monetisation-messaging  | 1.0.0-alpha01 |
| 🧪 | [Ceres Navigation Common](/navigation/common) | dev.teogor.ceres:navigation-common  | 1.0.0-alpha01 |
| 🧪 | [Ceres Navigation Core](/navigation/core) | dev.teogor.ceres:navigation-core  | 1.0.0-alpha01 |
| 🧪 | [Ceres Navigation Events](/navigation/events) | dev.teogor.ceres:navigation-events  | 1.0.0-alpha01 |
| 🧪 | [Ceres Navigation Screen](/navigation/screen) | dev.teogor.ceres:navigation-screen  | 1.0.0-alpha01 |
| 🧪 | [Ceres Navigation UI](/navigation/ui) | dev.teogor.ceres:navigation-ui  | 1.0.0-alpha01 |
| 🧪 | [Ceres Screen Builder](/screen/builder) | dev.teogor.ceres:screen-builder  | 1.0.0-alpha01 |
| 🧪 | [Ceres Screen Core](/screen/core) | dev.teogor.ceres:screen-core  | 1.0.0-alpha01 |
| 🧪 | [Ceres Screen UI](/screen/ui) | dev.teogor.ceres:screen-ui  | 1.0.0-alpha01 |
| 🧪 | [Ceres UI Compose](/ui/compose) | dev.teogor.ceres:ui-compose  | 1.0.0-alpha01 |
| 🧪 | [Ceres UI Design-System](/ui/designsystem) | dev.teogor.ceres:ui-designsystem  | 1.0.0-alpha01 |
| 🧪 | [Ceres UI Foundation](/ui/foundation) | dev.teogor.ceres:ui-foundation  | 1.0.0-alpha01 |
| 🧪 | [Ceres UI Icons](/ui/icons) | dev.teogor.ceres:ui-icons  | 1.0.0-alpha01 |
| 🧪 | [Ceres UI Spectrum](/ui/spectrum) | dev.teogor.ceres:ui-spectrum  | 1.0.0-alpha01 |
| 🧪 | [Ceres UI Theme](/ui/theme) | dev.teogor.ceres:ui-theme  | 1.0.0-alpha01 |

### Explore Further

For the latest updates, in-depth documentation, and a comprehensive overview of the Ceres ecosystem, visit the official [Ceres documentation](/docs/). It's your gateway to a wealth of resources and insights that will elevate your Ceres development journey.

Stay informed, stay current, and embrace the full potential of Ceres.
