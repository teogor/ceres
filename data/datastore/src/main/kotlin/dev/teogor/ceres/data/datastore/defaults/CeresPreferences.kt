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

package dev.teogor.ceres.data.datastore.defaults

import android.content.Context
import androidx.annotation.Keep
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.google.gson.reflect.TypeToken
import dev.teogor.ceres.core.foundation.models.UserID
import dev.teogor.ceres.core.startup.ApplicationContextProvider
import dev.teogor.ceres.data.datastore.common.DataStoreManager
import dev.teogor.ceres.data.datastore.common.PreferenceDatastore
import dev.teogor.ceres.data.datastore.defaults.AppTheme.FollowSystem
import dev.teogor.ceres.data.datastore.defaults.JustBlackTheme.Off
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import java.lang.reflect.Type

// todo move this to framework
fun ceresPreferences(
  context: Context = ApplicationContextProvider.context,
): CeresPreferences {
  val name = "ceres_preferences"
  synchronized(DataStoreManager.dataStoreMap) {
    val existingDataStore = DataStoreManager.dataStoreMap[name]
    if (existingDataStore != null && existingDataStore is CeresPreferences) {
      return existingDataStore
    }

    val preferences = CeresPreferences(context, name)
    DataStoreManager.dataStoreMap[name] = preferences
    return preferences
  }
}

object CeresPreferencesTypes {
  val UserIdType: Type = object : TypeToken<UserID>() {}.type
}

class CeresPreferences(context: Context, name: String) : PreferenceDatastore(
  context,
  name,
) {
  // todo disable??
  private val Keys = object {
    val userId = stringPreferencesKey("user_id")
    val onboardingCompleted = booleanPreferencesKey("onboarding_completed")

    val name = stringPreferencesKey("name")
    val coverImage = stringPreferencesKey("cover_image")
    val profileImage = stringPreferencesKey("profile_image")

    val themeSeed = stringPreferencesKey("theme_seed")
    val appTheme = intPreferencesKey("app_theme")
    val justBlack = intPreferencesKey("just_black")
    val disableAndroidTheme = booleanPreferencesKey("disable_android_theme")
    val disableDynamicTheming = booleanPreferencesKey("disable_dynamic_theming")
    val disableDesaturatedColor = booleanPreferencesKey("disable_desaturated_color")

    // feedback
    val disableSoundFeedback = booleanPreferencesKey("disable_sound_feedback")
    val disableVibrationFeedback = booleanPreferencesKey("disable_vibration_feedback")
  }

  var userId: UserID
    get() = runBlocking {
      getter(
        key = Keys.userId,
        default = UserID(),
        typeToken = CeresPreferencesTypes.UserIdType,
        defaultInvoked = {
          userId = it
        },
      )
    }
    set(value) = runBlocking {
      setter(
        key = Keys.userId,
        value = value,
      )
    }

  var onboardingComplete: Boolean
    get() = runBlocking {
      getter(
        key = Keys.onboardingCompleted,
        default = false,
      )
    }
    set(value) = runBlocking {
      setter(
        key = Keys.onboardingCompleted,
        value = value,
      )
    }

  var name: String
    get() = runBlocking {
      getter(
        key = Keys.name,
        default = "",
      )
    }
    set(value) = runBlocking {
      setter(
        key = Keys.name,
        value = value,
      )
    }

  fun getNameFlow() = getFlow(
    key = Keys.name,
    default = "",
  )

  var profileImage: String
    get() = runBlocking {
      getter(
        key = Keys.profileImage,
        default = "",
      )
    }
    set(value) = runBlocking {
      setter(
        key = Keys.profileImage,
        value = value,
      )
    }

  fun getProfileImageFlow() = getFlow(
    key = Keys.profileImage,
    default = "",
  )

  var coverImage: String
    get() = runBlocking {
      getter(
        key = Keys.coverImage,
        default = "",
      )
    }
    set(value) = runBlocking {
      setter(
        key = Keys.coverImage,
        value = value,
      )
    }

  var themeSeed: String
    get() = runBlocking { getThemeSeed() }
    set(value) {
      runBlocking { setThemeSeed(value) }
    }

  private suspend fun getThemeSeed(): String = dataStore.data
    .map { preferences -> preferences[Keys.themeSeed] }
    .firstOrNull() ?: ThemeConfig.seedHex

  private suspend fun setThemeSeed(themeSeed: String) {
    dataStore.edit { preferences ->
      preferences[Keys.themeSeed] = themeSeed
    }
  }

  fun getThemeSeedFlow() = getFlow(
    key = Keys.themeSeed,
    default = ThemeConfig.seedHex,
  )

  var disableAndroidTheme: Boolean
    get() = runBlocking { getDisableAndroidTheme() }
    set(value) {
      runBlocking { setDisableAndroidTheme(value) }
    }

  private suspend fun getDisableAndroidTheme(): Boolean = dataStore.data
    .map { preferences -> preferences[Keys.disableAndroidTheme] }
    .firstOrNull() ?: false

  private suspend fun setDisableAndroidTheme(disable: Boolean) {
    dataStore.edit { preferences ->
      preferences[Keys.disableAndroidTheme] = disable
    }
  }

  fun getDisableAndroidThemeFlow() = getFlow(
    key = Keys.disableAndroidTheme,
    default = false,
  )

  var disableDynamicTheming: Boolean
    get() = runBlocking { getDisableDynamicTheming() }
    set(value) {
      runBlocking { setDisableDynamicTheming(value) }
    }

  private suspend fun getDisableDynamicTheming(): Boolean = dataStore.data
    .map { preferences -> preferences[Keys.disableDynamicTheming] }
    .firstOrNull() ?: false

  private suspend fun setDisableDynamicTheming(disable: Boolean) {
    dataStore.edit { preferences ->
      preferences[Keys.disableDynamicTheming] = disable
    }
  }

  fun getDisableDynamicThemingFlow() = getFlow(
    key = Keys.disableDynamicTheming,
    default = false,
  )

  var disableDesaturatedColor: Boolean
    get() = runBlocking { getDisableDesaturatedColor() }
    set(value) {
      runBlocking { setDisableDesaturatedColor(value) }
    }

  private suspend fun getDisableDesaturatedColor(): Boolean = dataStore.data
    .map { preferences -> preferences[Keys.disableDesaturatedColor] }
    .firstOrNull() ?: true

  private suspend fun setDisableDesaturatedColor(disable: Boolean) {
    dataStore.edit { preferences ->
      preferences[Keys.disableDesaturatedColor] = disable
    }
  }

  var appTheme: AppTheme
    get() = runBlocking { getAppTheme() }
    set(value) {
      runBlocking { setAppTheme(value) }
    }

  private suspend fun getAppTheme(): AppTheme {
    val appThemeValue = dataStore.data
      .map { preferences -> preferences[Keys.appTheme] }
      .firstOrNull() ?: getIntFromAppTheme(FollowSystem)
    return getAppThemeFromInt(appThemeValue)
  }

  fun getAppThemeFromInt(value: Int): AppTheme {
    val enumValues = AppTheme.values()
    return if (value in enumValues.indices) {
      enumValues[value]
    } else {
      FollowSystem
    }
  }

  fun getIntFromAppTheme(appTheme: AppTheme): Int {
    return appTheme.ordinal
  }

  private suspend fun setAppTheme(appTheme: AppTheme) {
    dataStore.edit { preferences ->
      preferences[Keys.appTheme] = appTheme.ordinal
    }
  }

  fun getAppThemeFlow() = dataStore.data
    .map { preferences -> getAppThemeFromInt(preferences[Keys.appTheme] ?: 0) }

  var justBlack: JustBlackTheme
    get() = runBlocking { getJustBlackTheme() }
    set(value) {
      runBlocking { setJustBlackTheme(value) }
    }

  private suspend fun getJustBlackTheme(): JustBlackTheme {
    val justBlackThemeValue = dataStore.data
      .map { preferences -> preferences[Keys.justBlack] }
      .firstOrNull() ?: getIntFromJustBlackTheme(Off)
    return getJustBlackThemeFromInt(justBlackThemeValue)
  }

  fun getJustBlackThemeFromInt(value: Int): JustBlackTheme {
    val enumValues = JustBlackTheme.values()
    return if (value in enumValues.indices) {
      enumValues[value]
    } else {
      Off
    }
  }

  fun getIntFromJustBlackTheme(justBlackTheme: JustBlackTheme): Int {
    return justBlackTheme.ordinal
  }

  private suspend fun setJustBlackTheme(justBlackTheme: JustBlackTheme) {
    dataStore.edit { preferences ->
      preferences[Keys.justBlack] = justBlackTheme.ordinal
    }
  }

  fun getJustBlackThemeFlow() = dataStore.data
    .map { preferences -> getAppThemeFromInt(preferences[Keys.justBlack] ?: 0) }

  var disableSoundFeedback: Boolean
    get() = runBlocking {
      getter(
        key = Keys.disableSoundFeedback,
        default = false,
      )
    }
    set(value) = runBlocking {
      setter(
        key = Keys.disableSoundFeedback,
        value = value,
      )
    }

  fun getDisableSoundFeedbackFlow() = getFlow(
    key = Keys.disableSoundFeedback,
    default = false,
  )

  var disableVibrationFeedback: Boolean
    get() = runBlocking {
      getter(
        key = Keys.disableVibrationFeedback,
        default = false,
      )
    }
    set(value) = runBlocking {
      setter(
        key = Keys.disableVibrationFeedback,
        value = value,
      )
    }

  fun getDisableVibrationFeedbackFlow() = getFlow(
    key = Keys.disableVibrationFeedback,
    default = false,
  )
}

enum class AppTheme {
  @Keep
  FollowSystem,

  @Keep
  ClearlyWhite,

  @Keep
  KindaDark,
}

// todo beta battery saver own option with switch
enum class JustBlackTheme {
  @Keep
  Off,

  @Keep
  AlwaysOn,

  @Keep
  FollowSystem,
}
