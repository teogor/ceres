# Using the Bill of Materials

The BoM Bill of Materials (BOM) lets you manage all of your BoM library versions by
specifying only the BOM’s version. The BOM itself has links to the stable versions of the different
BoM libraries, in such a way that they work well together. When using the BOM in your app, you
don't need to add any version to the BoM library dependencies themselves. When you update the
BOM version, all the libraries that you're using are automatically updated to their new versions.

To find out which BoM library versions are mapped to a specific BOM version, check out
the [BOM to library version mapping](bom-mapping.md).

[//]: # (REGION-DIFFERENT-LIBRARY-VERSION-USAGE)

### How do I use a different library version than what's designated in the BOM?

In the `build.gradle` dependencies section, keep the import of the BOM platform. On the library
dependency import, specify the desired version. For example, here's how to declare dependencies if
you want to use a different version of Compose, no matter what version is designated
in the BOM:

```groovy
dependencies {
  // Import the BoM BOM
  implementation platform('dev.teogor.ceres:bom:1.0.0-alpha05')

  // Import Compose library
  implementation 'dev.teogor.ceres:ui-compose:1.0.0-alpha05'

  // Import other BoM libraries without version numbers
  // ..
  implementation 'dev.teogor.ceres:monetisation-messaging'
}
```

[//]: # (REGION-DIFFERENT-LIBRARY-VERSION-USAGE)

### Does the BOM automatically add all the BoM libraries to my app?

No. To actually add and use BoM libraries in your app, you must declare each library as a
separate dependency line in your module (app-level) Gradle file (usually `app/build.gradle`).

Using the BOM ensures that the versions of any BoM libraries in your app are compatible, but the
BOM doesn't actually add those BoM libraries to your app.

### Why is the BOM the recommended way to manage BoM library versions?

Going forward, BoM libraries will be versioned independently, which means version numbers will
start to be incremented at their own pace. The latest stable releases of each library are tested and
guaranteed to work nicely together. However, finding the latest stable versions of each library can
be difficult, and the BOM helps you to automatically use these latest versions.

### Am I forced to use the BOM?

No. You can still choose to add each dependency version manually. However, we recommend using the
BOM as it will make it easier to use all of the latest stable versions at the same time.

[//]: # (REGION-BOM-WITH-VERSION-CATALOG)

### Does the BOM work with version catalogs?

Yes. You can include the BOM itself in the version catalog, and omit the other BoM library versions:

```toml
[libraries]
teogor-ceres-bom = { group = "dev.teogor.ceres", name = "bom", version.ref = "teogor-ceres-bom" }
teogor-ceres-monetisation-messaging = { group = "dev.teogor.ceres", name = "monetisation-messaging" }
```

Don’t forget to import the BOM in your module’s `build.gradle`:

```groovy
dependencies {
    val teogorCeresBom = platform(libs.teogor.ceres.bom)
    implementation(teogorCeresBom)
    androidTestImplementation(teogorCeresBom)

    // import BoM dependencies as usual
}
```

[//]: # (REGION-BOM-WITH-VERSION-CATALOG)

[//]: # (REGION-REPORT-ISSUE-FEEDBACK)

### How do I report an issue or offer feedback on the BOM?

You can file issues on our [issue tracker 🔗](https://github.com/teogor/ceres/issues).

[//]: # (REGION-REPORT-ISSUE-FEEDBACK)
