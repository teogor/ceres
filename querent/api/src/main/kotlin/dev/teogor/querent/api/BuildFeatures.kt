/*
 * Copyright 2023 teogor (Teodor Grigor)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dev.teogor.querent.api

interface BuildFeatures {

  /**
   * Flag to enable/disable the creation of build profiles.
   *
   * A build profile is a set of configurations that define how a
   * project is built. Enabling this option allows customizing the
   * build process for specific scenarios or purposes.
   *
   * The default value is set to `false`, indicating that build profiles
   * are not created. When set to `true`, build profiles are generated
   * based on the applied configurations.
   *
   * For more details on build profiles, refer to:
   * [https://source.teogor.dev/ceres].
   */
  var buildProfile: Boolean

  /**
   * A flag that enables/disables the utilization of XML resources within
   * the build process.
   *
   * Setting the `xmlResources` property to `true` allows the integration
   * and use of XML resources in the build process. XML resources refer to
   * structured data files in XML format used for various definitions and
   * configurations.
   *
   * The default value for this property is `false`, implying that XML resources
   * are not utilized. Enabling this flag enables access to and utilization of
   * XML-based content within the build process.
   *
   * For further information about the handling and integration of XML resources,
   * see the documentation at [https://source.teogor.dev/ceres].
   */
  var xmlResources: Boolean

  /**
   * A flag that indicates whether the build process supports the definition
   * and management of language schemas.
   *
   * The `languagesSchema` property, when set to `true`, enables the configuration
   * and management of language schemas or structures for the project's build
   * process. Language schemas encompass the definition of language-related data,
   * rules, or structures for use within the project's build. Enabling this flag
   * allows developers to define, configure, or manage language-specific data
   * structures or formats for use within the build process.
   *
   * By default, the `languagesSchema` property is set to `false`, indicating that
   * language schemas are not actively managed or utilized in the build process.
   * Setting this property to `true` allows he incorporation of language-specific
   * structures into the build process.
   *
   * For additional details on configuring language schemas and their integration
   * within the build process, please refer to the documentation at
   * [https://source.teogor.dev/ceres].
   */
  var languagesSchema: Boolean
}
