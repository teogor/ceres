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

package dev.teogor.ceres.firebase.crashlytics

import android.content.Context
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.content.res.Resources
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.BatteryManager
import android.os.Build
import android.os.PowerManager
import android.provider.Settings
import com.google.firebase.crashlytics.CustomKeysAndValues.Builder
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import dev.teogor.ceres.core.foundation.packageManagerUtils
import java.util.Locale

internal class CrashlyticsManager(context: Context) {

  private val firebaseCrashlytics = Firebase.crashlytics

  init {
    firebaseCrashlytics.setKeys {
      screen(context)
      displayMetrics(context)
      theme(context)
      locale()
      battery(context)
      network(context)
      device()
      app(context)
      packageInstaller(context)
    }
  }
}

private fun FirebaseCrashlytics.setKeys(block: Builder.() -> Unit) {
  val builder = Builder()
  builder.block()
  builder.build()
  this.setCustomKeys(builder.build())
}

private fun Builder.screen(context: Context) {
  val screenSizeType = context.resources.configuration.screenLayout and
    Configuration.SCREENLAYOUT_SIZE_MASK
  val screenSize = when (screenSizeType) {
    Configuration.SCREENLAYOUT_SIZE_SMALL -> "Small"
    Configuration.SCREENLAYOUT_SIZE_NORMAL -> "Normal"
    Configuration.SCREENLAYOUT_SIZE_LARGE -> "Large"
    Configuration.SCREENLAYOUT_SIZE_XLARGE -> "Extra Large"
    else -> "Unknown"
  }
  putString("screen_size", screenSize)

  val screenBrightness = Settings.System.getInt(
    context.contentResolver,
    Settings.System.SCREEN_BRIGHTNESS,
  )
  putInt("screen_brightness", screenBrightness)
  val screenBrightnessMode = Settings.System.getInt(
    context.contentResolver,
    Settings.System.SCREEN_BRIGHTNESS_MODE,
  )
  putInt("screen_brightness_mode", screenBrightnessMode)
}

private fun Builder.displayMetrics(context: Context) {
  val displayMetrics = context.resources.displayMetrics

  val screenWidth = displayMetrics.widthPixels
  val screenHeight = displayMetrics.heightPixels
  putInt("display_metrics_width", screenWidth)
  putInt("display_metrics_height", screenHeight)

  val dpi = displayMetrics.densityDpi
  val density = displayMetrics.density
  putInt("display_metrics_dpi", dpi)
  putFloat("display_metrics_density", density)
}

private fun Builder.theme(context: Context) {
  // todo
  // val ceresPreferences = ceresPreferences(context)
  // putString("theme_seed", ceresPreferences.themeSeed)
  // putString("theme_app_variant", ceresPreferences.appTheme.toString())
  // putString("theme_just_black_variant", ceresPreferences.justBlack.toString())
  // putBoolean("theme_disable_android_theme", ceresPreferences.disableAndroidTheme)
  // putBoolean("theme_disable_dynamic_theming", ceresPreferences.disableDynamicTheming)
  // putBoolean(
  //   "theme_disable_desaturated_color",
  //   ceresPreferences.disableDesaturatedColor,
  // )
}

private fun Builder.locale() {
  val defaultLocale = Locale.getDefault()
  val preferredLanguage = defaultLocale.language
  val systemLanguage = Resources.getSystem().configuration.locale.language
  putString("locale_preferred_language", preferredLanguage)
  putString("locale_system_language", systemLanguage)

  val preferredCountry = defaultLocale.country
  val systemCountry = Resources.getSystem().configuration.locale.country
  putString("locale_preferred_country", preferredCountry)
  putString("locale_system_country", systemCountry)

  val displayLanguage = defaultLocale.displayLanguage
  val displayName = defaultLocale.displayName
  putString("locale_display_language", displayLanguage)
  putString("locale_display_name", displayName)

  val iso3Language = defaultLocale.isO3Language
  val iso3Country = defaultLocale.isO3Country
  putString("locale_iso3_language", iso3Language)
  putString("locale_iso3_country", iso3Country)

  val languageTag = defaultLocale.toLanguageTag()
  putString("locale_language_tag", languageTag)

  val script = defaultLocale.script
  putString("locale_script", script)

  val variant = defaultLocale.variant
  putString("locale_variant", variant)

  val countryDisplayName = defaultLocale.displayCountry
  putString("locale_display_country", countryDisplayName)

  val countryISO3 = defaultLocale.isO3Country
  putString("locale_iso3_country", countryISO3)
}

private fun Builder.battery(context: Context) {
  val batteryManager = context.getSystemService(Context.BATTERY_SERVICE) as BatteryManager
  val powerManager = context.getSystemService(Context.POWER_SERVICE) as PowerManager
  val batteryLevel = batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)
  val isCharging = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
    batteryManager.isCharging
  } else {
    false
  }
  val isBatterySaverEnabled = powerManager.isPowerSaveMode
  putInt("battery_level", batteryLevel)
  putBoolean("battery_is_charging", isCharging)
  putBoolean("battery_is_saver_enabled", isBatterySaverEnabled)
}

private fun Builder.network(context: Context) {
  val connectivityManager =
    context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
  val networkType: String = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
    val network = connectivityManager.activeNetwork
    val networkCapabilities = connectivityManager.getNetworkCapabilities(network)
    if (networkCapabilities != null) {
      when {
        networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> "WiFi"
        networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> "Cellular"
        networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> "Ethernet"
        else -> "Other"
      }
    } else {
      "No network"
    }
  } else {
    val activeNetworkInfo = connectivityManager.activeNetworkInfo
    if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
      when (activeNetworkInfo.type) {
        ConnectivityManager.TYPE_WIFI -> "WiFi"
        ConnectivityManager.TYPE_MOBILE -> "Cellular"
        ConnectivityManager.TYPE_ETHERNET -> "Ethernet"
        else -> "Other"
      }
    } else {
      "No network"
    }
  }
  putString("network_type", networkType)
}

private fun Builder.device() {
  putString("device_model", Build.MODEL)
  putString("device_manufacturer", Build.MANUFACTURER)
  putString("device_brand", Build.BRAND)
  putString("device_name", Build.DEVICE)
  putString("android_version", Build.VERSION.RELEASE)
  putInt("sdk_version", Build.VERSION.SDK_INT)
}

private fun Builder.packageInstaller(context: Context) {
  val packageInstaller = context.packageManager.packageInstaller
  val sessionParams = packageInstaller.mySessions.firstOrNull()
  val installerPackageName = sessionParams?.installerPackageName ?: "N/A"
  putString("installer_package_name", installerPackageName)
}

private fun Builder.app(context: Context) {
  val packageManagerUtils = context.packageManagerUtils()
  val packageManager = packageManagerUtils.packageManager
  val packageName = packageManagerUtils.packageName
  val packageSignatures = packageManagerUtils.packageSignatures

  val signatures = packageSignatures.apkContentsSigners.joinToString(", ") { signature ->
    signature.toCharsString()
  }
  putString("app_signatures", signatures)

  val packageInfoPermissions = packageManager.getPackageInfo(
    packageName,
    PackageManager.GET_PERMISSIONS,
  )
  val permissions = packageInfoPermissions.requestedPermissions?.joinToString(", ") ?: "N/A"
  putString("app_permissions", permissions)
}

fun crashlyticsLog(content: String) {
  Firebase.crashlytics.log(content)
}

fun recordException(exception: Exception) {
  Firebase.crashlytics.recordException(exception)
}
