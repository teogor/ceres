/*
 * Copyright 2022 teogor (Teodor Grigor) All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dev.teogor.ceres.core.construct

import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.content.pm.PackageInfoCompat
import com.zeoflow.startup.ktx.ApplicationInitializer

object AppData {

  const val IsRelease = dev.teogor.ceres.core.BuildConfig.IS_RELEASE_VERSION

  val IsDebuggable = isDebuggableImpl()

  val PackageName = packageNameImpl()

  val VersionName = versionNameImpl()

  val VersionCode = versionCodeImpl()

  private fun isDebuggableImpl(): Boolean {
    val context = ApplicationInitializer.context
    val applicationInfo = context.applicationInfo
    val isDebuggable = 0 != ApplicationInfo.FLAG_DEBUGGABLE.let {
      applicationInfo.flags = applicationInfo.flags and it; applicationInfo.flags
    }
    return isDebuggable
  }

  private fun packageNameImpl(): String {
    val context = ApplicationInitializer.context
    return context.packageName
  }

  private fun versionNameImpl(): String {
    val context = ApplicationInitializer.context
    return try {
      val pInfo = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        context.packageManager.getPackageInfo(
          context.packageName,
          PackageManager.PackageInfoFlags.of(0)
        )
      } else {
        context.packageManager.getPackageInfo(context.packageName, 0)
      }
      pInfo.versionName
    } catch (e: PackageManager.NameNotFoundException) {
      "N/A"
    }
  }

  private fun versionCodeImpl(): Long {
    val context = ApplicationInitializer.context
    return try {
      val pInfo = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        context.packageManager.getPackageInfo(
          context.packageName,
          PackageManager.PackageInfoFlags.of(0)
        )
      } else {
        context.packageManager.getPackageInfo(context.packageName, 0)
      }
      return PackageInfoCompat.getLongVersionCode(pInfo)
    } catch (e: PackageManager.NameNotFoundException) {
      -1
    }
  }
}
