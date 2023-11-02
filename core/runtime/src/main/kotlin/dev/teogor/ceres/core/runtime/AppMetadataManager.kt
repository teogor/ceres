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

package dev.teogor.ceres.core.runtime

import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.util.Base64
import dev.teogor.ceres.core.android.config.BuildConfig
import dev.teogor.ceres.core.foundation.packageManagerUtils
import dev.teogor.ceres.core.startup.ApplicationContextProvider
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset

@Suppress("MemberVisibilityCanBePrivate", "unused")
@Deprecated("use BuildProfiler")
object AppMetadataManager {
  val packageName: String
    get() = ApplicationContextProvider.context.packageName

  val packageManager: PackageManager
    get() = ApplicationContextProvider.context.packageManagerUtils().packageManager

  val packageInfo: PackageInfo
    get() = ApplicationContextProvider.context.packageManagerUtils().packageInfo

  val apkSignature: String
    get() {
      val signatures = ApplicationContextProvider.context.packageManagerUtils().packageSignatures
      val signature = signatures.apkContentsSigners[0]
      val signatureBytes = signature.toByteArray()
      return Base64.encodeToString(signatureBytes, Base64.NO_WRAP)
    }

  // region Deprecated API
  @Deprecated("use LocalBuildProfiler.current.versionName")
  val versionName: String
    get() = ApplicationContextProvider.context.packageManagerUtils().versionName

  @Deprecated("use LocalBuildProfiler.current.versionCode")
  val versionCode: Long
    get() = ApplicationContextProvider.context.packageManagerUtils().versionCode

  @Deprecated("use LocalBuildProfiler.current.systemZoneOffset")
  val zoneOffset: ZoneOffset
    get() = ZoneId.systemDefault().rules.getOffset(LocalDateTime.now())

  @Deprecated("use LocalBuildProfiler.current.buildLocalDateTime")
  val buildDateTime: LocalDateTime
    get() = LocalDateTime.ofEpochSecond(
      BuildConfig.BUILD_DATE_TIME.toLong(),
      0,
      zoneOffset,
    )

  @Deprecated("use LocalBuildProfiler.current.isDebuggable")
  val isDebuggable: Boolean
    get() = packageInfo.applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE != 0

  @Deprecated("not actual value")
  val buildType: String
    get() = BuildConfig.BUILD_TYPE

  // todo BuildConfig.FLAVOR
  @Deprecated("not actual value")
  val flavor: String
    get() = "demo"

  @Deprecated("use LocalBuildProfiler.current.gitCommitHash")
  val gitHash: String
    get() = BuildConfig.GIT_HASH

  @Deprecated("use LocalBuildProfiler.current.ceresBomVersion")
  val ceresFrameworkVersion: String
    get() = BuildConfig.CERES_FRAMEWORK_VERSION

  @Deprecated("use LocalBuildProfiler.current.isDebuggable")
  val debug = isDebuggable

  @Deprecated(
    message = "This property is deprecated. Please use packageName property instead.",
    replaceWith = ReplaceWith("packageName"),
  )
  val sanitizedPackageName: String
    get() {
      var sanitizedPackageName = when (flavor) {
        "demo" -> packageName.removeSuffix(".demo")
        else -> packageName
      }
      sanitizedPackageName = if (isDebuggable) {
        sanitizedPackageName.removeSuffix(".debug")
      } else {
        sanitizedPackageName
      }
      sanitizedPackageName = when (flavor) {
        "demo" -> sanitizedPackageName.removeSuffix(".demo")
        else -> sanitizedPackageName
      }
      return sanitizedPackageName
    }
  // endregion
}
