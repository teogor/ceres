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

package dev.teogor.ceres.core.util

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build
import android.provider.Settings
import android.telephony.TelephonyManager
import com.zeoflow.startup.ktx.ApplicationInitializer
import java.lang.reflect.InvocationTargetException
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.Locale

object AppUtils {
  val appPackageInfo: PackageInfo
    get() = try {
      ApplicationInitializer.context.packageManager.getPackageInfo(
        ApplicationInitializer.context.packageName,
        0
      )
    } catch (ignored: PackageManager.NameNotFoundException) {
      PackageInfo()
    }

  fun quitToLauncher() {
    val homeIntent = Intent(Intent.ACTION_MAIN)
    homeIntent.addCategory(Intent.CATEGORY_HOME)
    //        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    ApplicationInitializer.context.startActivity(homeIntent)
  } // For 3G devices (with SIM) query getNetworkCountryIso()

  // If network country not available (tablets maybe), get country code from Locale class

  // General fallback to "us"
  // Special case for CDMA Devices
  // Query first getSimCountryIso()
  // Try to get country code from TelephonyManager service
  val deviceCountryCode: String
    get() {
      var countryCode: String?
      val context = ApplicationInitializer.context

      // Try to get country code from TelephonyManager service
      val tm = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
      // Query first getSimCountryIso()
      countryCode = tm.simCountryIso
      if (countryCode != null && countryCode.length == 2) return countryCode.lowercase(
        Locale.getDefault()
      )
      countryCode = if (tm.phoneType == TelephonyManager.PHONE_TYPE_CDMA) {
        // Special case for CDMA Devices
        cDMACountryIso
      } else {
        // For 3G devices (with SIM) query getNetworkCountryIso()
        tm.networkCountryIso
      }
      if (countryCode != null && countryCode.length == 2) return countryCode.lowercase(
        Locale.getDefault()
      )

      // If network country not available (tablets maybe), get country code from Locale class
      countryCode = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        context.resources.configuration.locales[0].country
      } else {
        context.resources.configuration.locale.country
      }
      return if (countryCode.length == 2) countryCode.lowercase(Locale.getDefault()) else "N/A"
    }

  // Try to get country code from SystemProperties private class
  @get:SuppressLint("PrivateApi")
  private val cDMACountryIso: String?
    // Get homeOperator that contain MCC + MNC
    // First three characters (MCC) from homeOperator represents the country code
    get() {
      try {
        // Try to get country code from SystemProperties private class
        val systemProperties = Class.forName("android.os.SystemProperties")
        val get = systemProperties.getMethod("get", String::class.java)

        // Get homeOperator that contain MCC + MNC
        val homeOperator = get.invoke(
          systemProperties,
          "ro.cdma.home.operator.numeric"
        ) as String ?: return "US"
        // First three characters (MCC) from homeOperator represents the country code
        when (homeOperator.substring(0, 3).toInt()) {
          330 -> return "PR"
          310, 311, 312, 316 -> return "US"
          283 -> return "AM"
          460 -> return "CN"
          455 -> return "MO"
          414 -> return "MM"
          619 -> return "SL"
          450 -> return "KR"
          634 -> return "SD"
          434 -> return "UZ"
          232 -> return "AT"
          204 -> return "NL"
          262 -> return "DE"
          247 -> return "LV"
          255 -> return "UA"
        }
      } catch (ignored: ClassNotFoundException) {
      } catch (ignored: NoSuchMethodException) {
      } catch (ignored: IllegalAccessException) {
      } catch (ignored: InvocationTargetException) {
      } catch (ignored: NullPointerException) {
      }
      return null
    }

  // Create MD5 Hash
  @get:SuppressLint("HardwareIds")
  val deviceID: String
    // Create Hex String
    get() {
      val deviceID = Settings.Secure.getString(
        ApplicationInitializer.context.contentResolver,
        Settings.Secure.ANDROID_ID
      )
      try {
        // Create MD5 Hash
        val digest = MessageDigest
          .getInstance("MD5")
        digest.update(deviceID.toByteArray())
        val messageDigest = digest.digest()

        // Create Hex String
        val hexString = StringBuilder()
        for (b in messageDigest) {
          val h = StringBuilder(Integer.toHexString(0xFF and b.toInt()))
          while (h.length < 2) h.insert(0, "0")
          hexString.append(h)
        }
        return hexString.toString().uppercase(Locale.getDefault())
      } catch (ignored: NoSuchAlgorithmException) {
      }
      return ""
    }
}
