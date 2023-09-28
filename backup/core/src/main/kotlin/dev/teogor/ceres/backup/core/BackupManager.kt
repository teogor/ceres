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

@file:OptIn(ExperimentalSerializationApi::class)

package dev.teogor.ceres.backup.core

import android.os.Environment
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.google.gson.GsonBuilder
import com.google.gson.LongSerializationPolicy
import com.google.gson.reflect.TypeToken
import dev.teogor.ceres.backup.core.model.BackupProto
import dev.teogor.ceres.backup.core.model.BackupType.DATASTORE
import dev.teogor.ceres.backup.core.model.BackupType.ROOM_DATABASE
import dev.teogor.ceres.backup.core.model.PreferenceField
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.protobuf.ProtoBuf
import java.io.File
import java.io.FileOutputStream
import java.lang.reflect.Type
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

object BackupManagerTypes {
  val BackupProtoType: Type = object : TypeToken<BackupProto>() {}.type
}

open class BackupManager @Inject constructor() {

  private fun getBackupFilename(
    fileExtension: String,
  ): String {
    val date = SimpleDateFormat("yyyy-MM-dd-HH-mm", Locale.getDefault()).format(Date())
    return "manual-backup-$date.$fileExtension"
  }

  private fun extractExtension(fileName: String): String {
    val regex = Regex("[*.]?(\\w+\\.\\w+)")
    val matchResult = regex.find(fileName.lowercase())
    return matchResult?.groupValues?.get(1) ?: ""
  }

  val parser: ProtoBuf = ProtoBuf

  private val gson = GsonBuilder()
    .setLongSerializationPolicy(LongSerializationPolicy.STRING)
    .setObjectToNumberStrategy(ToNumberPolicy.LONG_OR_DOUBLE)
    .create()

  var databases: Map<String, BackupDao> = emptyMap()
  var preferences: Map<String, DataStore<Preferences>> = emptyMap()

  var fileExtension: String = ""
  var appName: String = ""

  val backupFilesFolder: String
    get() = "Documents/$appName/backups"

  fun configureBackupEnvironment(
    fileExtension: String,
    appName: String,
    preferences: Map<String, DataStore<Preferences>>,
    databases: Map<String, BackupDao>,
  ) {
    this.fileExtension = extractExtension(fileExtension)
    this.appName = appName
    this.preferences = preferences
    this.databases = databases
  }

  suspend fun saveFile(backupBytes: ByteArray) {
    val backupDir = File(Environment.getExternalStorageDirectory(), backupFilesFolder)
    if (!backupDir.exists() && !backupDir.mkdirs()) {
      // Handle error, unable to create directory
    }
    val byteArray = backupBytes
    if (!backupDir.exists()) {
      backupDir.mkdirs()
    }
    val backupFile = File(backupDir, getBackupFilename(fileExtension))
    withContext(Dispatchers.IO) {
      FileOutputStream(backupFile).use { stream ->
        stream.write(byteArray)
      }
    }
  }

  open suspend fun getPreferencesAsList(): MutableList<String> {
    val preferencesList = mutableListOf<String>()
    preferences.forEach { (key, preference) ->
      val data = preference.data.first()
      val dataMap: Map<Preferences.Key<*>, Any> = data.asMap()
      val dataList = dataMap.map { (key, value) ->
        PreferenceField(
          key = key.name,
          value = value,
          type = value.javaClass.name,
        )
      }
      val backupProto = BackupProto(
        name = key,
        backupType = DATASTORE,
        preferenceFields = dataList,
      )
      preferencesList.add(
        gson.toJson(backupProto),
      )
    }
    return preferencesList
  }

  open suspend fun restoreBackup(
    protoBackup: ProtoBackup,
  ): ProtoBackup {
    protoBackup.datastorePreferences.forEach { jsonData ->
      val backupProto: BackupProto = gson.fromJson(jsonData, BackupManagerTypes.BackupProtoType)
      val name = backupProto.name
      when (backupProto.backupType) {
        DATASTORE -> {
          if (preferences.containsKey(name)) {
            val dataList = backupProto.preferenceFields as List<PreferenceField>
            preferences[name]?.edit {
              dataList.forEach { field ->
                when (field.value) {
                  is Int -> {
                    it[intPreferencesKey(field.key)] = field.value
                  }

                  is Float -> {
                    it[floatPreferencesKey(field.key)] = field.value
                  }

                  is Boolean -> {
                    it[booleanPreferencesKey(field.key)] = field.value
                  }

                  is String -> {
                    it[stringPreferencesKey(field.key)] = field.value
                  }

                  is Double -> {
                    it[doublePreferencesKey(field.key)] = field.value
                  }

                  is Long -> {
                    when (field.type) {
                      "java.lang.Integer" -> {
                        it[intPreferencesKey(field.key)] = field.value.toInt()
                      }

                      else -> {
                        it[longPreferencesKey(field.key)] = field.value
                      }
                    }
                  }

                  else -> {
                    println(
                      "${field.value.javaClass.name} -> Key: ${field.key}, Value: ${field.value}",
                    )
                  }
                }
              }
            }
          }
        }

        ROOM_DATABASE -> TODO()
      }
    }
    return protoBackup
  }
}
