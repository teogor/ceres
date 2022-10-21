# Dagger
-dontwarn com.google.errorprone.annotations.*

# Navigation
-keepnames class androidx.navigation.fragment.NavHostFragment
-keep class * extends androidx.fragment.app.Fragment{}
-keepnames class * extends android.os.Parcelable
-keepnames class * extends java.io.Serializable

-keepattributes Signature
-keep class java.lang.reflect.ParameterizedType

-keep class kotlin.coroutines.Continuation