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
import java.io.File
import java.io.FileInputStream
import java.security.MessageDigest
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset

@Suppress("MemberVisibilityCanBePrivate", "unused")
object AppMetadataManager {
  val packageName: String
    get() = ApplicationContextProvider.context.packageName

  val packageManager: PackageManager
    get() = ApplicationContextProvider.context.packageManagerUtils().packageManager

  val packageInfo: PackageInfo
    get() = ApplicationContextProvider.context.packageManagerUtils().packageInfo

  val versionName: String
    get() = ApplicationContextProvider.context.packageManagerUtils().versionName

  val versionCode: Long
    get() = ApplicationContextProvider.context.packageManagerUtils().versionCode

  val zoneOffset: ZoneOffset
    get() = ZoneId.systemDefault().rules.getOffset(LocalDateTime.now())

  val buildDateTime: LocalDateTime
    get() = LocalDateTime.ofEpochSecond(
      BuildConfig.BUILD_DATE_TIME.toLong(),
      0,
      zoneOffset,
    )

  val isDebuggable: Boolean
    get() = packageInfo.applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE != 0

  val buildType: String
    get() = BuildConfig.BUILD_TYPE

  // todo BuildConfig.FLAVOR
  val flavor: String
    get() = "demo"

  val gitHash: String
    get() = BuildConfig.GIT_HASH

  val ceresFrameworkVersion: String
    get() = BuildConfig.CERES_FRAMEWORK_VERSION

  val apkSignature: String?
    get() {
      try {
        val sourceDir = packageInfo.applicationInfo.sourceDir
        val file = File(sourceDir)
        val md = MessageDigest.getInstance("SHA-256")
        val fis = FileInputStream(file)
        val buffer = ByteArray(8192)
        var read: Int
        while (fis.read(buffer).also { read = it } != -1) {
          md.update(buffer, 0, read)
        }
        fis.close()
        val hashBytes = md.digest()
        return Base64.encodeToString(hashBytes, Base64.NO_WRAP)
      } catch (e: Exception) {
        e.printStackTrace()
      }
      return null
    }

  // region Deprecated API
  @Deprecated("use isDebuggable")
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
