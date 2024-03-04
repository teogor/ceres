package dev.teogor.ceres.models

enum class DependencyType(val gradleNotation: String) {
  KSP("ksp"),
  KSP_ANDROID_TEST("kspAndroidTest"),
  IMPLEMENTATION("implementation"),
  API("api"),
  ANDROID_TEST_IMPLEMENTATION("androidTestImplementation"),
  TEST_IMPLEMENTATION("testImplementation"),
  COMPILE_ONLY("compileOnly"),
}
