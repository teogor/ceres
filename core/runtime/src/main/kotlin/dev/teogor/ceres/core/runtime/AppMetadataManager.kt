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

import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.util.Base64
import dev.teogor.ceres.core.android.config.BuildConfig
import dev.teogor.ceres.core.runtime.AppMetadataManager.AppMetadataManagerImpl.getPackageInfoCompat
import dev.teogor.ceres.core.startup.ApplicationContextProvider
import java.io.File
import java.io.FileInputStream
import java.security.MessageDigest
import java.time.LocalDateTime
import java.time.ZoneId

@Suppress("MemberVisibilityCanBePrivate", "unused")
object AppMetadataManager {
  val packageName: String
    get() = ApplicationContextProvider.context.packageName

  val sanitizedPackageName: String
    get() {
      var sanitizedPackageName = when (flavor) {
        "demo" -> packageName.removeSuffix(".demo")
        else -> packageName
      }
      sanitizedPackageName = if (debug) {
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

  val packageManager: PackageManager
    get() = ApplicationContextProvider.context.packageManager

  val packageInfo: PackageInfo
    get() = packageManager.getPackageInfoCompat(packageName)

  val versionName: String
    get() = packageInfo.versionName

  val versionCode: Int
    get() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
      packageInfo.longVersionCode.toInt()
    } else {
      @Suppress("DEPRECATION")
      packageInfo.versionCode
    }

  val zoneOffset = ZoneId.systemDefault().rules.getOffset(LocalDateTime.now())
  val buildDateTime = LocalDateTime.ofEpochSecond(
    BuildConfig.BUILD_DATE_TIME.toLong(),
    0,
    zoneOffset,
  )

  val buildType = BuildConfig.BUILD_TYPE
  val flavor = "demo" // todo BuildConfig.FLAVOR
  val debug = BuildConfig.DEBUG
  val gitHash = BuildConfig.GIT_HASH
  val ceresFrameworkVersion = BuildConfig.CERES_FRAMEWORK_VERSION

  val isDebuggable: Boolean
    get() = BuildConfig.DEBUG

  val apkSignature: String?
    get() {
      try {
        val packageInfo = packageInfo
        val sourceDir =
          packageInfo.applicationInfo.sourceDir

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

  private object AppMetadataManagerImpl {
    fun PackageManager.getPackageInfoCompat(
      packageName: String,
      flags: Int = 0,
    ): PackageInfo = if (VERSION.SDK_INT >= VERSION_CODES.TIRAMISU) {
      getPackageInfo(packageName, PackageManager.PackageInfoFlags.of(flags.toLong()))
    } else {
      // @Suppress("DEPRECATION")
      getPackageInfo(packageName, flags)
    }
  }
}
