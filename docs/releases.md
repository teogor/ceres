[//]: # (This file was automatically generated - do not edit)

# Ceres Releases

Ceres seamlessly masters languages and locales, empowering developers to integrate multilingual
capabilities into their applications with ease.

---

### API Reference

* [`dev.teogor.ceres`](../reference)
* [`dev.teogor.ceres:backup`](../reference/backup)
* [`dev.teogor.ceres:core`](../reference/core)
* [`dev.teogor.ceres:data`](../reference/data)
* [`dev.teogor.ceres:firebase`](../reference/firebase)
* [`dev.teogor.ceres:framework`](../reference/framework)
* [`dev.teogor.ceres:monetisation`](../reference/monetisation)
* [`dev.teogor.ceres:navigation`](../reference/navigation)
* [`dev.teogor.ceres:screen`](../reference/screen)
* [`dev.teogor.ceres:ui`](../reference/ui)

### Release

|   Latest Update   | Stable Release | Beta Release | Alpha Release |
|:-----------------:|:--------------:|:------------:|:-------------:|
| November 23, 2023 |       -        |      -       | 1.0.0-alpha04 |

### Declaring dependencies

To add a dependency on Ceres, you must add the Maven repository to your project.
Read [Maven's repository for more information](https://repo.maven.apache.org/maven2/).

Add the dependencies for the artifacts you need in the `build.gradle` file for your app or module:

=== "Kotlin"

    ```kotlin
    dependencies {
      val ceres_version = "1.0.0-alpha04"

      implementation("dev.teogor.ceres:core-common:$ceres_version")
      implementation("dev.teogor.ceres:ui-designsystem:$ceres_version")

      // or using bom

      implementation(platform("dev.teogor.ceres:bom:$ceres_version"))
      implementation("dev.teogor.ceres:core-common")
      implementation("dev.teogor.ceres:ui-designsystem")
    }
    ```

=== "Groovy"

    ```groovy
    dependencies {
      def ceres_version = "1.0.0-alpha04"

      implementation("dev.teogor.ceres:core-common:${ceres_version}")
      implementation("dev.teogor.ceres:ui-designsystem:${ceres_version}")

      // or using bom

      implementation(platform("dev.teogor.ceres:bom:${ceres_version}")) {
        // Specify the artifact to resolve and re-export the dependencies from the platform BOM
        implementation("dev.teogor.ceres:core-common")
        implementation("dev.teogor.ceres:ui-designsystem")
      }
    }
    ```

### Feedback

Your feedback helps make Ceres better. We want to know if you discover new issues or have ideas
for improving this library. Before creating a new issue, please take a look at
the [existing ones](https://github.com/teogor/ceres) in this library. You can add your vote to an
existing issue by clicking the star button.

[Create a new issue](https://github.com/teogor/ceres/issues/new){ .md-button }

### Version 1.0.0

#### Version 1.0.0-alpha04

November 23, 2023

`dev.teogor.ceres:ceres-*:1.0.0-alpha04` is
released. [Version 1.0.0-alpha04 contains these commits.](https://github.com/teogor/ceres/compare/1.0.0-alpha03...1.0.0-alpha04)

**Enhancement**

* Add Language Schema Support for Supported Languages ([#174](https://github.com/teogor/ceres/pull/174)) by [@teogor](https://github.com/teogor)
* Incorporate Querent Plugin into Ceres ([#173](https://github.com/teogor/ceres/pull/173)) by [@teogor](https://github.com/teogor)
* Flexible Configuration and Dependency Version Checking ([#172](https://github.com/teogor/ceres/pull/172)) by [@teogor](https://github.com/teogor)
* Enhance About Components and Singleton Functions in About Module ([#170](https://github.com/teogor/ceres/pull/170)) by [@teogor](https://github.com/teogor)
* Analytics Integration for Enhanced Event Tracking ([#168](https://github.com/teogor/ceres/pull/168)) by [@teogor](https://github.com/teogor)
* Improved Resources Handling ([#167](https://github.com/teogor/ceres/pull/167)) by [@teogor](https://github.com/teogor)
* Enhancements for Network Monitoring ([#166](https://github.com/teogor/ceres/pull/166)) by [@teogor](https://github.com/teogor)
* Comprehensive Native Ad Enhancements in Compose ([#165](https://github.com/teogor/ceres/pull/165)) by [@teogor](https://github.com/teogor)
* Enhance AdRequestOptions Extension Functions ([#161](https://github.com/teogor/ceres/pull/161)) by [@teogor](https://github.com/teogor)
* Enhance Color Utilities and Customization ([#158](https://github.com/teogor/ceres/pull/158)) by [@teogor](https://github.com/teogor)

**Bug Fixes**

* Ad Caching and Loading Enhancements ([#163](https://github.com/teogor/ceres/pull/163)) by [@teogor](https://github.com/teogor)
* Full-Screen Layout Background Color Override ([#157](https://github.com/teogor/ceres/pull/157)) by [@teogor](https://github.com/teogor)
* Namespace Conflict Issue ([#156](https://github.com/teogor/ceres/pull/156)) by [@teogor](https://github.com/teogor)

**Others**

* Enhance Room Plugin with Schema Export Support ([#160](https://github.com/teogor/ceres/pull/160)) by [@teogor](https://github.com/teogor)

#### Version 1.0.0-alpha03

October 3, 2023

`dev.teogor.ceres:ceres-*:1.0.0-alpha03` is
released. [Version 1.0.0-alpha03 contains these commits.](https://github.com/teogor/ceres/compare/1.0.0-alpha02...1.0.0-alpha03)

**Enhancement**

* Allow users to specify a custom Ceres Framework version in aboutCeresFramework ([#148](https://github.com/teogor/ceres/pull/148)) by [@teogor](https://github.com/teogor)
* Add Lifecycle Observation Utilities for Activity, Application, and Composable Components ([#146](https://github.com/teogor/ceres/pull/146)) by [@teogor](https://github.com/teogor)
* Creation of core-common module and addition of KeepScreenOnKt and ContextUtilsKt functions ([#144](https://github.com/teogor/ceres/pull/144)) by [@teogor](https://github.com/teogor)
* Improved Logging and Expiration Handling for App Open Ads ([#139](https://github.com/teogor/ceres/pull/139)) by [@teogor](https://github.com/teogor)
* Added Privacy Navigation and Screen ([#138](https://github.com/teogor/ceres/pull/138)) by [@teogor](https://github.com/teogor)
* CompositionLocal Configuration with compositionProviders ([#137](https://github.com/teogor/ceres/pull/137)) by [@teogor](https://github.com/teogor)
* Add NavigationItem class for Representing Navigation Items ([#134](https://github.com/teogor/ceres/pull/134)) by [@teogor](https://github.com/teogor)
* Add Onboarding Screen for Improved User Experience ([#131](https://github.com/teogor/ceres/pull/131)) by [@teogor](https://github.com/teogor)
* Implement Monetisation Ads Control Module ([#130](https://github.com/teogor/ceres/pull/130)) by [@teogor](https://github.com/teogor)
* Icon Extension Functions for Simplified Icon Handling ([#129](https://github.com/teogor/ceres/pull/129)) by [@teogor](https://github.com/teogor)
* Enhance Compose Modifier with Conditional Application ([#127](https://github.com/teogor/ceres/pull/127)) by [@teogor](https://github.com/teogor)

**Bug Fixes**

* Update AdMob SDK Initialization Based on Consent Status ([#142](https://github.com/teogor/ceres/pull/142)) by [@teogor](https://github.com/teogor)
* Fixed App Open Ad Display on App Resume ([#136](https://github.com/teogor/ceres/pull/136)) by [@teogor](https://github.com/teogor)
* Bottom Navigation Bar Title Handling in TopLevelDestination ([#133](https://github.com/teogor/ceres/pull/133)) by [@teogor](https://github.com/teogor)
* Consent Form Display Control and AtomicBoolean Usage ([#132](https://github.com/teogor/ceres/pull/132)) by [@teogor](https://github.com/teogor)
* Full-Screen Layout Status Bar Padding Issue ([#128](https://github.com/teogor/ceres/pull/128)) by [@teogor](https://github.com/teogor)

**Others**

* Move `UserID` class to `core.foundation.models` ([#140](https://github.com/teogor/ceres/pull/140)) by [@teogor](https://github.com/teogor)

#### Version 1.0.0-alpha02

October 3, 2023

`dev.teogor.ceres:ceres-*:1.0.0-alpha02` is
released. [Version 1.0.0-alpha02 contains these commits.](https://github.com/teogor/ceres/compare/1.0.0-alpha01...1.0.0-alpha02)

**Enhancement**

* Documentation Generation for BOM 1.0.0alpha-02 ([#117](https://github.com/teogor/ceres/pull/117)) by [@teogor](https://github.com/teogor)
* Migrate Utilities and Integrate Ceres Core Foundation Composition Provider ([#115](https://github.com/teogor/ceres/pull/115)) by [@teogor](https://github.com/teogor)
* About Ceres Framework UI and GitHub Link ([#112](https://github.com/teogor/ceres/pull/112)) by [@teogor](https://github.com/teogor)
* Update Android SDK and Introduce Core Library Desugaring Control ([#110](https://github.com/teogor/ceres/pull/110)) by [@teogor](https://github.com/teogor)

**Bug Fixes**

* Remove 'about-libraries' Plugin Configuration ([#111](https://github.com/teogor/ceres/pull/111)) by [@teogor](https://github.com/teogor)
* Optional Status Bar Padding in FullScreenLayoutBase ([#109](https://github.com/teogor/ceres/pull/109)) by [@teogor](https://github.com/teogor)

**Others**

* UI Enhancements and Refactoring for Screens ([#108](https://github.com/teogor/ceres/pull/108)) by [@teogor](https://github.com/teogor)

#### Version 1.0.0-alpha01

September 28, 2023

`dev.teogor.ceres:ceres-*:1.0.0-alpha01` is
released. [Version 1.0.0-alpha01 contains these commits.](https://github.com/teogor/ceres/compare/6179ac776758e1905c36093a803fec7af99176b7...1.0.0-alpha01)

**Initial Release** 🎊
