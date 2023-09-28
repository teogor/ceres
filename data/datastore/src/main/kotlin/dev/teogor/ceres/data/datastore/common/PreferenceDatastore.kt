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

package dev.teogor.ceres.data.datastore.common

import android.content.Context
import androidx.annotation.Keep
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.lang.reflect.Type

object DataStoreManager {
  val dataStoreMap = mutableMapOf<String, PreferenceDatastore>()

  inline fun <reified T : PreferenceDatastore> preferences(name: String): T? {
    synchronized(dataStoreMap) {
      val existingDataStore = dataStoreMap[name]
      if (existingDataStore != null && existingDataStore is T) {
        return existingDataStore
      }

      return null
    }
  }
}

// TODO:move the runBlocking inside suspend function...
//  it will discard the use of runBlocking
//  instead of:
//  `get() = runBlocking {
//     getter(
//       key = Keys.userId,
//       default = UserID(),
//       typeToken = object : TypeToken<UserID>() {}.type,
//       defaultInvoked = {
//         userId = it
//       },
//     )
//   }`
//  will be:
//  `get() = getter(
//     key = Keys.userId,
//     default = UserID(),
//     typeToken = object : TypeToken<UserID>() {}.type,
//     defaultInvoked = {
//       userId = it
//     },
//   )`
@Keep
open class PreferenceDatastore(
  context: Context,
  name: String,
) {
  private val Context.datastore: DataStore<Preferences> by preferencesDataStore(
    name,
  )

  val dataStore: DataStore<Preferences> = context.datastore

  private val gson: Gson = Gson()

  open suspend fun <T> getter(
    key: Preferences.Key<String>,
    default: T,
    typeToken: Type,
    defaultInvoked: ((T) -> Unit)? = null,
  ): T {
    return dataStore.data
      .map { preferences -> preferences[key] }
      .map { json ->
        gson.fromJson<T>(json, typeToken)
      }
      .map {
        if (it == null) {
          defaultInvoked?.invoke(default)
          default
        } else {
          it
        }
      }
      .first()
  }

  open suspend fun <T> setter(
    key: Preferences.Key<String>,
    value: T,
  ) {
    dataStore.edit { preferences ->
      preferences[key] = gson.toJson(value)
    }
  }

  open suspend fun getter(
    key: Preferences.Key<String>,
    default: String,
  ): String {
    return dataStore.data
      .map { preferences -> preferences[key] }
      .firstOrNull() ?: default
  }

  open suspend fun setter(
    key: Preferences.Key<String>,
    value: String,
  ) {
    dataStore.edit { preferences ->
      preferences[key] = value
    }
  }

  open suspend fun getter(
    key: Preferences.Key<Int>,
    default: Int,
  ): Int {
    return dataStore.data
      .map { preferences -> preferences[key] }
      .firstOrNull() ?: default
  }

  open suspend fun setter(
    key: Preferences.Key<Int>,
    value: Int,
  ) {
    dataStore.edit { preferences ->
      preferences[key] = value
    }
  }

  open suspend fun getter(
    key: Preferences.Key<Long>,
    default: Long,
  ): Long {
    return dataStore.data
      .map { preferences -> preferences[key] }
      .firstOrNull() ?: default
  }

  open suspend fun setter(
    key: Preferences.Key<Long>,
    value: Long,
  ) {
    dataStore.edit { preferences ->
      preferences[key] = value
    }
  }

  open suspend fun getter(
    key: Preferences.Key<Boolean>,
    default: Boolean,
  ): Boolean {
    return dataStore.data
      .map { preferences -> preferences[key] }
      .firstOrNull() ?: default
  }

  open suspend fun setter(
    key: Preferences.Key<Boolean>,
    value: Boolean,
  ) {
    dataStore.edit { preferences ->
      preferences[key] = value
    }
  }

  open fun getFlow(
    key: Preferences.Key<String>,
    default: String,
  ) = dataStore.data
    .map { preferences -> preferences[key] ?: default }

  open fun getFlow(
    key: Preferences.Key<Int>,
    default: Int,
  ) = dataStore.data
    .map { preferences -> preferences[key] ?: default }

  open fun getFlow(
    key: Preferences.Key<Long>,
    default: Long,
  ) = dataStore.data
    .map { preferences -> preferences[key] ?: default }

  open fun getFlow(
    key: Preferences.Key<Boolean>,
    default: Boolean,
  ) = dataStore.data
    .map { preferences -> preferences[key] ?: default }

  open fun <T> getFlow(
    key: Preferences.Key<String>,
    default: T,
    typeToken: Type,
    defaultInvoked: ((T) -> Unit)? = null,
  ) = dataStore.data
    .map { preferences -> preferences[key] }
    .map { json ->
      gson.fromJson<T>(json, typeToken)
    }
    .map {
      if (it == null) {
        defaultInvoked?.invoke(default)
        default
      } else {
        it
      }
    }
}

fun PreferenceDatastore.launch(
  action: suspend () -> Unit,
) = CoroutineScope(Dispatchers.IO).launch {
  action()
}
