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

package dev.teogor.querent.api.codegen

import androidx.annotation.CallSuper
import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.LibraryExtension
import com.android.build.api.variant.ApplicationAndroidComponentsExtension
import com.android.build.api.variant.DynamicFeatureAndroidComponentsExtension
import com.android.build.api.variant.LibraryAndroidComponentsExtension
import com.android.build.api.variant.Variant
import dev.teogor.querent.api.utils.dir
import org.gradle.api.Project
import org.gradle.api.file.Directory
import org.gradle.api.logging.Logger
import org.gradle.api.logging.Logging
import org.gradle.kotlin.dsl.findByType
import java.util.Locale

abstract class Blueprint(
  data: FoundationData,
) {
  protected val tag: String = this::class.java.simpleName
  protected val logger: Logger = Logging.getLogger(this::class.java)

  open val name: String = this::class.java.simpleName.replaceFirstChar {
    it.lowercase(Locale.ENGLISH)
  }

  val project: Project = data.project

  private val codeWriter: CodeWriter = data.codeWriter

  val applicationComponentsExtension: ApplicationAndroidComponentsExtension?
    get() = extension()

  val libraryComponentsExtension: LibraryAndroidComponentsExtension?
    get() = extension()

  val dynamicFeatureComponentsExtension: DynamicFeatureAndroidComponentsExtension?
    get() = extension()

  open fun isEnabled(): Boolean = true

  open val packageNameSuffix: String? = null

  lateinit var packageName: String
  lateinit var namespace: String

  val intermediates: Directory
    get() = codeWriter.intermediatesOutputDir.dir(name)

  lateinit var kotlinSources: Directory
  lateinit var javaSources: Directory
  lateinit var resSources: Directory
  lateinit var resourcesSources: Directory

  fun onCreate() {
    apply()

    applicationComponentsExtension?.apply {
      @Suppress("UnstableApiUsage")
      finalizeDsl {
        it.onFinalizeDsl()
      }

      onVariants {
        if (isEnabled()) {
          prepareDirectories(it.name)
          onVariants(it)
        }
      }
    }

    libraryComponentsExtension?.apply {
      @Suppress("UnstableApiUsage")
      finalizeDsl {
        it.onFinalizeDsl()
      }

      onVariants {
        if (isEnabled()) {
          prepareDirectories(it.name)
          onVariants(it)
        }
      }
    }

    dynamicFeatureComponentsExtension?.apply {
      @Suppress("UnstableApiUsage")
      finalizeDsl {
        it.onFinalizeDsl()
      }

      onVariants {
        if (isEnabled()) {
          prepareDirectories(it.name)
          onVariants(it)
        }
      }
    }
  }

  fun kotlin(variant: String): Directory = codeWriter.sourceOutputDir
    .dir(name)
    .dir(variant)
    .dir("kotlin")

  fun java(variant: String): Directory = codeWriter.sourceOutputDir
    .dir(name)
    .dir(variant)
    .dir("java")

  fun res(variant: String): Directory = codeWriter.sourceOutputDir
    .dir(name)
    .dir(variant)
    .dir("res")

  fun resources(variant: String): Directory = codeWriter.sourceOutputDir
    .dir(name)
    .dir(variant)
    .dir("resources")

  private fun prepareDirectories(variant: String) {
    kotlinSources = kotlin(variant)
    javaSources = java(variant)
    resSources = res(variant)
    resourcesSources = resources(variant)
  }

  private fun CommonExtension<*, *, *, *, *>.onFinalizeDsl() {
    codeWriter.setEnabled(isEnabled())

    logger.quiet("[$tag] ApplicationExtension::${extension<ApplicationExtension>() == null}")
    extension<ApplicationExtension>()?.let { appExtension ->
      val namespace = appExtension.namespace ?: appExtension.defaultConfig.applicationId ?: "dev.teogor.ceres"
      val finalNamespace = if (packageNameSuffix != null) {
        "$namespace.$packageNameSuffix"
      } else {
        namespace
      }
      this@Blueprint.namespace = namespace
      this@Blueprint.packageName = finalNamespace
    }

    logger.quiet("[$tag] LibraryExtension::${extension<LibraryExtension>() == null}")
    extension<LibraryExtension>()?.let { libraryExtension ->
      val namespace = libraryExtension.namespace ?: "dev.teogor.ceres"
      val finalNamespace = if (packageNameSuffix != null) {
        "$namespace.$packageNameSuffix"
      } else {
        namespace
      }
      this@Blueprint.namespace = namespace
      this@Blueprint.packageName = finalNamespace
    }

    sourceSets {
      buildTypes.forEach {
        val variant = it.name
        named(variant) {
          kotlin.srcDirs(kotlin(variant))
          java.srcDirs(java(variant))
          res.srcDirs(res(variant))
          resources.srcDirs(resources(variant))
        }
      }
    }

    finalizeDsl()
  }

  open fun CommonExtension<*, *, *, *, *>.finalizeDsl() {
  }

  open fun apply() {
  }

  @CallSuper
  open fun onVariants(variant: Variant) {
  }

  inline fun <reified T : Any> extension(): T? {
    return project.extensions.findByType<T>()
  }
}
