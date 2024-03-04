import com.diffplug.spotless.LineEnding
import com.vanniktech.maven.publish.SonatypeHost
import dev.teogor.winds.api.MavenPublish
import dev.teogor.winds.api.Winds
import dev.teogor.winds.api.getValue
import dev.teogor.winds.api.model.DependencyType
import dev.teogor.winds.api.model.Developer
import dev.teogor.winds.api.model.LicenseType
import dev.teogor.winds.api.provider.Scm
import dev.teogor.winds.common.utils.hasWindsPlugin
import dev.teogor.winds.gradle.WindsPlugin
import dev.teogor.winds.gradle.utils.attachTo
import org.jetbrains.dokka.gradle.DokkaMultiModuleTask
import org.jetbrains.dokka.gradle.DokkaPlugin

buildscript {
  repositories {
    google()
    mavenCentral()
  }
}

// Lists all plugins used throughout the project without applying them.
plugins {
  alias(libs.plugins.kotlin.jvm) apply false
  alias(libs.plugins.kotlin.serialization) apply false

  alias(libs.plugins.android.application) apply false

  alias(libs.plugins.firebase.crashlytics) apply false
  alias(libs.plugins.firebase.perf) apply false

  alias(libs.plugins.gms) apply false
  alias(libs.plugins.hilt) apply false
  alias(libs.plugins.ksp) apply false

  alias(libs.plugins.querent) apply false
  alias(libs.plugins.winds) apply true

  alias(libs.plugins.about.libraries) apply false

  alias(libs.plugins.vanniktech.maven) apply true
  alias(libs.plugins.dokka) apply true
  alias(libs.plugins.spotless) apply true
  alias(libs.plugins.api.validator) apply true
}

winds {
  buildFeatures {
    mavenPublish = true

    docsGenerator = true
  }

  mavenPublish {
    displayName = "Ceres"
    name = "ceres"

    canBePublished = false

    description =
      "\uD83E\uDE90 Ceres is a comprehensive Android development framework designed to streamline your app development process. Powered by the latest technologies like Jetpack Compose, Hilt, Coroutines, and Flow, Ceres empowers developers to build modern and efficient Android applications."

    groupId = "dev.teogor.ceres"
    url = "https://source.teogor.dev/ceres"
    artifactIdElements = 2

    inceptionYear = 2022

    sourceControlManagement(
      Scm.Git(
        owner = "teogor",
        repo = "ceres",
      ),
    )

    addLicense(LicenseType.APACHE_2_0)

    addDeveloper(TeogorDeveloper())
  }

  docsGenerator {
    name = "Ceres"
    identifier = "ceres"

    excludeModules {
      listOf(
        ":app",
      )
    }

    dependencyGatheringType = DependencyType.LOCAL
  }
}

whenWindsPluginConfigured { winds ->
  val mavenPublish: MavenPublish by winds
  mavenPublish.version?.let {
    version = it.toString()
  }
  if (mavenPublish.canBePublished) {
    mavenPublishing {
      publishToMavenCentral(SonatypeHost.S01)
      signAllPublications()

      @Suppress("UnstableApiUsage")
      pom {
        coordinates(
          groupId = mavenPublish.groupId!!,
          artifactId = mavenPublish.artifactId!!,
          version = mavenPublish.version!!.toString(),
        )
        mavenPublish attachTo this
      }
    }
  }
}

/**
 * Executes the provided action when the Winds plugin is configured for any descendant project.
 *
 * @param action the action to execute for each project with the Winds plugin configured
 *
 * **How to Use:**
 *
 * ```kotlin
 * project.onWindsPluginConfigured { winds ->
 *   // Execute the action for each project with the Winds plugin configured
 *   winds.doSomethingUseful()
 * }
 * ```
 */
fun Project.whenWindsPluginConfigured(action: Project.(Winds) -> Unit) {
  subprojects.toList()
    .filter { hasWindsPlugin() }
    .forEach { project ->
      project.afterEvaluate {
        project.plugins.withType<WindsPlugin> {
          project.afterEvaluate {
            val winds: Winds by project.extensions
            project.action(winds)
          }
        }
      }
    }
}

data class TeogorDeveloper(
  override val id: String = "teogor",
  override val name: String = "Teodor Grigor",
  override val email: String = "open-source@teogor.dev",
  override val url: String = "https://teogor.dev",
  override val roles: List<String> = listOf("Code Owner", "Developer", "Designer", "Maintainer"),
  override val timezone: String = "UTC+2",
  override val organization: String = "Teogor",
  override val organizationUrl: String = "https://github.com/teogor",
) : Developer

val ktlintVersion = "0.50.0"

val excludedProjects = listOf(
  project.name,
  "app",
)

// Spotless
subprojects {
  if (!excludedProjects.contains(this.name)) {
    apply<com.diffplug.gradle.spotless.SpotlessPlugin>()
    configure<com.diffplug.gradle.spotless.SpotlessExtension> {
      lineEndings = LineEnding.UNIX

      kotlin {
        target("**/*.kt")
        targetExclude("**/build/**/*.kt")
        ktlint(ktlintVersion)
          .userData(
            mapOf(
              "android" to "true",
              "ktlint_code_style" to "android",
              "ij_kotlin_allow_trailing_comma" to "true",
              // These rules were introduced in ktlint 0.46.0 and should not be
              // enabled without further discussion. They are disabled for now.
              // See: https://github.com/pinterest/ktlint/releases/tag/0.46.0
              "disabled_rules" to
                "filename," +
                "annotation,annotation-spacing," +
                "argument-list-wrapping," +
                "double-colon-spacing," +
                "enum-entry-name-case," +
                "multiline-if-else," +
                "no-empty-first-line-in-method-block," +
                "package-name," +
                "trailing-comma," +
                "spacing-around-angle-brackets," +
                "spacing-between-declarations-with-annotations," +
                "spacing-between-declarations-with-comments," +
                "unary-op-spacing," +
                "no-trailing-spaces," +
                "no-wildcard-imports," +
                "max-line-length",
            ),
          )
        licenseHeaderFile(rootProject.file("spotless/copyright.kt"))
        trimTrailingWhitespace()
        endWithNewline()
      }
      format("kts") {
        target("**/*.kts")
        targetExclude("**/build/**/*.kts")
        // Look for the first line that doesn't have a block comment (assumed to be the license)
        licenseHeaderFile(rootProject.file("spotless/copyright.kts"), "(^(?![\\/ ]\\*).*$)")
      }
      format("xml") {
        target("**/*.xml")
        targetExclude("**/build/**/*.xml")
        // Look for the first XML tag that isn't a comment (<!--) or the xml declaration (<?xml)
        licenseHeaderFile(rootProject.file("spotless/copyright.xml"), "(<[^!?])")
      }
    }
  }
}

// API Validation
apiValidation {
  /**
   * Packages that are excluded from public API dumps even if they
   * contain public API.
   */
  ignoredPackages.add("androidx.databinding")

  /**
   * Sub-projects that are excluded from API validation
   */
  ignoredProjects.addAll(excludedProjects)

  /**
   * Flag to programmatically disable compatibility validator
   */
  validationDisabled = false
}

// Dokka
subprojects {
  if (!excludedProjects.contains(project.name)) {
    afterEvaluate {
      val winds: Winds by extensions
      val mavenPublish: MavenPublish by winds
      apply<DokkaPlugin>()
      tasks.withType<DokkaMultiModuleTask>().configureEach {
        failOnWarning.set(false)
        suppressInheritedMembers.set(true)
        moduleName.set(mavenPublish.name)
        moduleVersion.set(mavenPublish.version.toString())
        val paths = project.path.split(":")
        val pathRef = paths.joinToString(separator = "/")
        outputDirectory.set(rootProject.projectDir.resolve("build/reference/${pathRef}"))
      }
    }
  }
}


tasks.dokkaHtmlMultiModule {
  dependsOn(":backup:dokkaHtmlMultiModule")
  dependsOn(":core:dokkaHtmlMultiModule")
  dependsOn(":data:dokkaHtmlMultiModule")
  dependsOn(":firebase:dokkaHtmlMultiModule")
  dependsOn(":framework:dokkaHtmlMultiModule")
  dependsOn(":monetisation:dokkaHtmlMultiModule")
  dependsOn(":navigation:dokkaHtmlMultiModule")
  dependsOn(":screen:dokkaHtmlMultiModule")
  dependsOn(":ui:dokkaHtmlMultiModule")
}
