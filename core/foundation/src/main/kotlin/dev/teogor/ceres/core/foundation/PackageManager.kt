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

@file:SuppressLint("ObsoleteSdkInt")
@file:Suppress("DEPRECATION")

package dev.teogor.ceres.core.foundation

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.content.pm.Signature
import android.os.Build
import androidx.annotation.RequiresApi

private var Impl: PackageManagerImpl? = null

private fun getPackageManagerImpl(context: Context) =
  Impl ?: if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
    PackageManagerApi33(context)
  } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
    PackageManagerApi28(context)
  } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
    PackageManagerApi21(context)
  } else {
    PackageManagerBase(context)
  }.also { Impl = it }

fun Context.packageManagerUtils(): PackageManagerUtils {
  return PackageManagerUtils(getPackageManagerImpl(this))
}

interface PackageManagerImpl {
  fun getPackageManager(): PackageManager
  fun getPackageInfo(flags: Number = 0): PackageInfo
  fun getPackageName(): String
  fun getVersionName(): String
  fun getVersionCode(): Long
  fun getPackageSignatures(): SigningInfo
}

class PackageManagerBase(private val context: Context) : PackageManagerImpl {
  override fun getPackageManager(): PackageManager {
    return context.packageManager
  }

  override fun getPackageInfo(flags: Number): PackageInfo {
    return context.packageManager.getPackageInfo(context.packageName, flags.toInt())
  }

  override fun getPackageName(): String {
    return context.packageName
  }

  override fun getVersionName(): String {
    return getPackageInfo().versionName
  }

  override fun getVersionCode(): Long {
    return getPackageInfo().versionCode.toLong()
  }

  override fun getPackageSignatures(): SigningInfo {
    val packageInfo = getPackageInfo(PackageManager.GET_SIGNATURES)
    val signatures = packageInfo.signatures ?: emptyArray()

    return SigningInfo(
      hasMultipleSigners = false, // Implement this logic
      hasPastSigningCertificates = false, // Implement this logic
      signingCertificateHistory = signatures.toList(),
      apkContentsSigners = signatures.toList(),
    )
  }
}

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
class PackageManagerApi21(private val context: Context) : PackageManagerImpl {
  override fun getPackageManager(): PackageManager {
    return context.packageManager
  }

  override fun getPackageInfo(flags: Number): PackageInfo {
    return context.packageManager.getPackageInfo(context.packageName, flags.toInt())
  }

  override fun getPackageName(): String {
    return context.packageName
  }

  override fun getVersionName(): String {
    return getPackageInfo().versionName
  }

  override fun getVersionCode(): Long {
    return getPackageInfo().versionCode.toLong()
  }

  override fun getPackageSignatures(): SigningInfo {
    val packageInfo = getPackageInfo(PackageManager.GET_SIGNATURES)
    val signatures = packageInfo.signatures ?: emptyArray()

    return SigningInfo(
      hasMultipleSigners = false, // Implement this logic
      hasPastSigningCertificates = false, // Implement this logic
      signingCertificateHistory = signatures.toList(),
      apkContentsSigners = signatures.toList(),
    )
  }
}

@RequiresApi(api = Build.VERSION_CODES.P)
class PackageManagerApi28(private val context: Context) : PackageManagerImpl {
  override fun getPackageManager(): PackageManager {
    return context.packageManager
  }

  override fun getPackageInfo(flags: Number): PackageInfo {
    return context.packageManager.getPackageInfo(context.packageName, flags.toInt())
  }

  override fun getPackageName(): String {
    return context.packageName
  }

  override fun getVersionName(): String {
    return getPackageInfo().versionName
  }

  override fun getVersionCode(): Long {
    return getPackageInfo().longVersionCode
  }

  override fun getPackageSignatures(): SigningInfo {
    val packageInfo = getPackageInfo(PackageManager.GET_SIGNING_CERTIFICATES)

    // Check if there is signingInfo available in packageInfo
    val signingInfo = packageInfo.signingInfo

    val hasMultipleSigners = signingInfo?.hasMultipleSigners() ?: false
    val hasPastSigningCertificates = signingInfo?.hasPastSigningCertificates() ?: false
    val signingCertificateHistory = signingInfo?.signingCertificateHistory?.toList() ?: emptyList()
    val apkContentsSigners = signingInfo?.apkContentsSigners?.toList() ?: emptyList()

    return SigningInfo(
      hasMultipleSigners,
      hasPastSigningCertificates,
      signingCertificateHistory,
      apkContentsSigners,
    )
  }
}

@RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
class PackageManagerApi33(private val context: Context) : PackageManagerImpl {
  override fun getPackageManager(): PackageManager {
    return context.packageManager
  }

  override fun getPackageInfo(flags: Number): PackageInfo {
    return context.packageManager.getPackageInfo(
      context.packageName,
      PackageManager.PackageInfoFlags.of(flags.toLong()),
    )
  }

  override fun getPackageName(): String {
    return context.packageName
  }

  override fun getVersionName(): String {
    return getPackageInfo().versionName
  }

  override fun getVersionCode(): Long {
    return getPackageInfo().longVersionCode
  }

  override fun getPackageSignatures(): SigningInfo {
    val packageInfo = getPackageInfo(PackageManager.GET_SIGNING_CERTIFICATES)

    // Check if there is signingInfo available in packageInfo
    val signingInfo = packageInfo.signingInfo

    val hasMultipleSigners = signingInfo?.hasMultipleSigners() ?: false
    val hasPastSigningCertificates = signingInfo?.hasPastSigningCertificates() ?: false
    val signingCertificateHistory = signingInfo?.signingCertificateHistory?.toList() ?: emptyList()
    val apkContentsSigners = signingInfo?.apkContentsSigners?.toList() ?: emptyList()

    return SigningInfo(
      hasMultipleSigners,
      hasPastSigningCertificates,
      signingCertificateHistory,
      apkContentsSigners,
    )
  }
}

data class SigningInfo(
  val hasMultipleSigners: Boolean,
  val hasPastSigningCertificates: Boolean,
  val signingCertificateHistory: List<Signature>,
  val apkContentsSigners: List<Signature>,
)

class PackageManagerUtils(
  private val packageManagerImpl: PackageManagerImpl,
) {
  val packageManager: PackageManager
    get() = packageManagerImpl.getPackageManager()

  val packageInfo: PackageInfo
    get() = packageManagerImpl.getPackageInfo()

  fun getPackageInfo(
    flags: Int,
  ) = packageManagerImpl.getPackageInfo(flags)

  val packageSignatures: SigningInfo
    get() = packageManagerImpl.getPackageSignatures()

  val packageName: String
    get() = packageManagerImpl.getPackageName()

  val versionName: String
    get() = packageManagerImpl.getVersionName()

  val versionCode: Long
    get() = packageManagerImpl.getVersionCode()
}
