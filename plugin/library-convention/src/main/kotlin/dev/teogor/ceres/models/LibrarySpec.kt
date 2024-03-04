package dev.teogor.ceres.models

data class LibrarySpec(
  val name: String,
  val module: String,
  val dependencyTypes: List<DependencyType> = emptyList(),
  val isBom: Boolean = false,
)

val roomRuntime = LibrarySpec(
  name = "room.runtime",
  module = "androidx.room:room-runtime",
  dependencyTypes = listOf(
    DependencyType.IMPLEMENTATION,
  ),
)
val roomKtx = LibrarySpec(
  name = "room.ktx",
  module = "androidx.room:room-ktx",
  dependencyTypes = listOf(
    DependencyType.IMPLEMENTATION,
  ),
)
val roomCompiler = LibrarySpec(
  name = "room.compiler",
  module = "androidx.room:room-compiler",
  dependencyTypes = listOf(
    DependencyType.KSP,
  ),
)
val hiltAndroid = LibrarySpec(
  name = "hilt.android",
  module = "com.google.dagger:hilt-android",
  dependencyTypes = listOf(
    DependencyType.IMPLEMENTATION,
  ),
)
val hiltAndroidCompiler = LibrarySpec(
  name = "hilt.android.compiler",
  module = "com.google.dagger:hilt-android-compiler",
  dependencyTypes = listOf(
    DependencyType.KSP,
    DependencyType.KSP_ANDROID_TEST,
  ),
)
val androidxComposeBom = LibrarySpec(
  name = "androidx.compose.bom",
  module = "androidx.compose:compose-bom",
  dependencyTypes = listOf(
    DependencyType.IMPLEMENTATION,
    DependencyType.ANDROID_TEST_IMPLEMENTATION,
  ),
  isBom = true,
)
