import com.diffplug.spotless.LineEnding
import com.vanniktech.maven.publish.SonatypeHost
import dev.teogor.winds.api.ArtifactIdFormat
import dev.teogor.winds.api.License
import dev.teogor.winds.api.NameFormat
import dev.teogor.winds.api.Person
import dev.teogor.winds.api.Scm
import dev.teogor.winds.api.TicketSystem
import dev.teogor.winds.api.Winds
import dev.teogor.winds.api.model.DependencyType
import dev.teogor.winds.ktx.person
import dev.teogor.winds.ktx.scm
import dev.teogor.winds.ktx.ticketSystem
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
  alias(libs.plugins.jetbrains.kotlin.jvm) apply false
  alias(libs.plugins.jetbrains.kotlin.serialization) apply false

  alias(libs.plugins.android.application) apply false

  alias(libs.plugins.firebase.crashlytics) apply false
  alias(libs.plugins.firebase.perf) apply false

  alias(libs.plugins.gms) apply false
  alias(libs.plugins.hilt) apply false
  alias(libs.plugins.ksp) apply false

  alias(libs.plugins.querent) apply false
  alias(libs.plugins.teogor.winds) apply true

  alias(libs.plugins.about.libraries) apply false

  alias(libs.plugins.vanniktech.maven) apply true
  alias(libs.plugins.jetbrains.dokka) apply true
  alias(libs.plugins.spotless) apply true
  alias(libs.plugins.jetbrains.binary.compatibility.validator) apply true

  alias(libs.plugins.ben.manes.versions) apply true
  alias(libs.plugins.littlerobots.version.catalog.update) apply true
}

winds {
  windsFeatures {
    mavenPublishing = true
    docsGenerator = true
    workflowSynthesizer = true
  }

  moduleMetadata {
    name = "Ceres"
    description = """
    |Ceres is a comprehensive Android development framework designed to streamline your app development process. Powered by the latest technologies like Jetpack Compose, Hilt, Coroutines, and Flow, Ceres empowers developers to build modern and efficient Android applications.
    """.trimMargin()

    yearCreated = 2022

    websiteUrl = "https://source.teogor.dev/ceres/"
    apiDocsUrl = "https://source.teogor.dev/ceres/html/"

    artifactDescriptor {
      group = "dev.teogor.ceres"
      name = "ceres"
      nameFormat = NameFormat.FULL
      artifactIdFormat = ArtifactIdFormat.MODULE_NAME_ONLY
    }

    // Providing SCM (Source Control Management)
    scm<Scm.GitHub> {
      owner = "teogor"
      repository = "ceres"
    }

    // Providing Ticket System
    ticketSystem<TicketSystem.GitHub> {
      owner = "teogor"
      repository = "ceres"
    }

    // Providing Licenses
    licensedUnder(License.Apache2())

    // Providing Persons
    person<Person.DeveloperContributor> {
      id = "teogor"
      name = "Teodor Grigor"
      email = "open-source@teogor.dev"
      url = "https://teogor.dev"
      roles = listOf("Code Owner", "Developer", "Designer", "Maintainer")
      timezone = "UTC+2"
      organization = "Teogor"
      organizationUrl = "https://github.com/teogor"
    }
  }

  publishingOptions {
    publish = false
    enablePublicationSigning = true
    optInForVanniktechPlugin = true
    cascadePublish = true
    sonatypeHost = SonatypeHost.S01
  }

  documentationBuilder {
    htmlPath = "html/"
    markdownNewlineSeparator = "  "
    dependencyGatheringType = DependencyType.NONE
  }
}

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
          .editorConfigOverride(
            mapOf(
              "android" to "true",
              "ktlint_code_style" to "intellij_idea",
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
      val moduleMetadata = winds.moduleMetadata
      apply<DokkaPlugin>()
      tasks.withType<DokkaMultiModuleTask>().configureEach {
        failOnWarning.set(false)
        suppressInheritedMembers.set(true)
        moduleName.set(moduleMetadata.name)
        moduleVersion.set(moduleMetadata.artifactDescriptor?.version.toString())
        val paths = project.path.split(":")
        val pathRef = paths.joinToString(separator = "/")
        outputDirectory.set(rootProject.projectDir.resolve("build/reference/${pathRef}"))
      }
    }
  }
}

tasks.dokkaHtmlMultiModule {
  childProjects.values.forEach {
    dependsOn(":${it.name}:dokkaHtmlMultiModule")
  }
}

versionCatalogUpdate {
  keep {
    // keep versions without any library or plugin reference
    keepUnusedVersions = true
    // keep all libraries that aren't used in the project
    keepUnusedLibraries = true
    // keep all plugins that aren't used in the project
    keepUnusedPlugins = true
  }
}
