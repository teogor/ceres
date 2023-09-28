# GSON
-keep class com.google.gson.Gson { *; }
-keep class com.google.gson.TypeAdapter { *; }
-keep class com.google.gson.JsonDeserializer { *; }
-keep class com.google.gson.JsonSerializer { *; }
-keep class com.google.gson.InstanceCreator { *; }
-keep class com.google.gson.reflect.TypeToken { *; }

# Preference
-keep class dev.teogor.ceres.data.datastore.common.DataStoreManager { *; }
-keep class dev.teogor.ceres.data.datastore.common.PreferenceDatastore { *; }

# Ceres Preferences
-keep class dev.teogor.ceres.data.datastore.defaults.CeresPreferencesTypes*$* { *; }
