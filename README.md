# Ceres

🪐 Streamline Android Development with Compose, Hilt, Coroutines, and Flow Integration.

## Download
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
[![Maven Central](https://img.shields.io/maven-central/v/dev.teogor.ceres/bom.svg?label=Maven%20Central)](https://central.sonatype.com/search?q=g%3Adev.teogor.ceres+a%3Abom&smo=true)
[![API](https://img.shields.io/badge/API-21%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=21)
[![Profile](https://source.teogor.dev/badges/teogor-github.svg)](https://github.com/teogor)
[![Portfolio](https://source.teogor.dev/badges/teogor-dev.svg)](https://teogor.dev)

## Implementation

To streamline the implementation of Ceres libraries, use the following Gradle setup with the BoM (Bill of Materials) for version management.

1. Add the BoM for Ceres in your **module**'s `build.gradle` or `build.gradle.kts` file:

```kotlin
dependencies {
  implementation(platform("dev.teogor.ceres:bom:1.0.0-alpha01"))
}
```

2. Then, include the specific Ceres libraries you need as dependencies:

```kotlin
dependencies {
  // Ceres BoM
  implementation(platform("dev.teogor.ceres:bom:1.0.0-alpha01"))

  // Include individual Ceres libraries here as needed
  implementation("dev.teogor.ceres:backup-core")
  implementation("dev.teogor.ceres:core-foundation")
  implementation("dev.teogor.ceres:firebase-analytics")
  // ... Add more libraries here
}
```

This setup simplifies library version management and ensures compatibility among the Ceres libraries in your project. The BoM (Bill of Materials) achieves this by centralizing version management, significantly reducing compatibility issues, and streamlining the entire dependency management process. Customize the dependencies based on your project's requirements by including only the necessary Ceres libraries.

### Ceres BoM (Bill of Materials)

The BOM (Bill of Materials) is the central hub for managing library versions within the Ceres project.
It enables you to effortlessly keep track of the latest versions of key components and dependencies.

For more implementation options and detailed information, refer to the [Ceres BoM (Bill of Materials) documentation](docs/bom/versions.md).

### BoM Versions (Bill of Materials)

For a list of the latest BOM (Bill of Materials) versions, including release notes and release dates, please refer to the [Ceres Version Catalog](/docs/ceres-version-catalog.md). This catalog provides comprehensive information about Ceres libraries and BOM versions in TOML format.

Explore further to access the full catalog and detailed implementation information.

> **Note**: This library has more modules, so include only the ones that you want to use.

## Documentation

Explore the comprehensive documentation for Ceres to get started:

- [Read the Full Documentation](docs/index.md)
- [Versions](docs/bom/versions.md)
- [Library Versions Catalog](docs/ceres-version-catalog.md)

> Explore the documentation for more details on each module:
> - [Backup](docs/ceres-module-backup.md)
> - [Core](docs/ceres-module-core.md)
> - [Data](docs/ceres-module-data.md)
> - [Firebase](docs/ceres-module-firebase.md)
> - [Framework](docs/ceres-module-framework.md)
> - [Monetisation](docs/ceres-module-monetisation.md)
> - [Navigation](docs/ceres-module-navigation.md)
> - [Screen](docs/ceres-module-screen.md)
> - [UI](docs/ceres-module-ui.md)

## Find this repository useful? :heart:
Support it by joining __[stargazers](https://github.com/teogor/ceres/stargazers)__ for this repository. :star: <br>
Also, __[follow me](https://github.com/teogor)__ on GitHub for my next creations! 🤩

# License
```xml
  Designed and developed by 2022 teogor (Teodor Grigor)

  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
```
