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
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  `kotlin-dsl`
}

java {
  sourceCompatibility = JavaVersion.VERSION_11
  targetCompatibility = JavaVersion.VERSION_11
}

val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
  languageVersion = "1.9"
  jvmTarget = JavaVersion.VERSION_11.toString()
}

dependencies {
  api(libs.androidx.annotation)
  api(libs.android.gradlePlugin)
  api(libs.kotlin.gradlePlugin)
  api(libs.kotlin.poet)
}
