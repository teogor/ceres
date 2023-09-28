import org.jetbrains.dokka.gradle.DokkaTaskPartial

buildscript {
  repositories {
    google()
    mavenCentral()
  }
}

// Lists all plugins used throughout the project without applying them.
plugins {
  alias(libs.plugins.android.application) apply false
  alias(libs.plugins.kotlin.jvm) apply false
  alias(libs.plugins.kotlin.serialization) apply false
  alias(libs.plugins.firebase.crashlytics) apply false
  alias(libs.plugins.firebase.perf) apply false
  alias(libs.plugins.gms) apply false
  alias(libs.plugins.hilt) apply false
  alias(libs.plugins.ksp) apply false
  alias(libs.plugins.about.libraries) apply false
  alias(libs.plugins.vanniktech.maven) apply false

  alias(libs.plugins.dokka)
  alias(libs.plugins.spotless)
  alias(libs.plugins.api.validator)

  id("dev.teogor.ceres.docs")
}

val ktlintVersion = "0.50.0"

subprojects {
  apply<com.diffplug.gradle.spotless.SpotlessPlugin>()
  configure<com.diffplug.gradle.spotless.SpotlessExtension> {
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

fun Project.addDokka(
  enabled: Boolean,
) {
  if (!enabled) {
    return
  }
  tasks.dokkaHtmlMultiModule.configure {
    outputDirectory.set(rootDir.resolve("docs/dokka"))
  }

  subprojects {
    val excludeModules = listOf(
      ":app",
    )
    if (parent == rootProject) {
      if (!excludeModules.contains(path)) {
        apply(plugin = "org.jetbrains.dokka")
        subprojects {
          apply(plugin = "org.jetbrains.dokka")
        }

        tasks.withType<DokkaTaskPartial>().configureEach {
          dokkaSourceSets {
            configureEach {
              outputDirectory.set(rootDir.resolve("docs/dokka/${this@subprojects.name}"))

              suppressInheritedMembers.set(true)

              // includes.from("Module.md")
              moduleName.set(this@subprojects.name)

              // Used for linking to JDK documentation
              jdkVersion.set(11)

              // Disable linking to online kotlin-stdlib documentation
              noStdlibLink.set(false)

              // Disable linking to online JDK documentation
              noJdkLink.set(false)

              // Disable linking to online Android documentation (only applicable for Android projects)
              noAndroidSdkLink.set(false)

              // Include generated files in documentation
              // By default Dokka will omit all files in folder named generated that is a child of buildDir
              suppressGeneratedFiles.set(false)

              // Do not output deprecated members. Applies globally, can be overridden by packageOptions
              skipDeprecated.set(false)

              // Do not create index pages for empty packages
              skipEmptyPackages.set(false)

              reportUndocumented.set(true) // Report undocumented members
            }
          }
        }
      }
    }
  }
}

addDokka(false)

apiValidation {
  /**
   * Packages that are excluded from public API dumps even if they
   * contain public API.
   */
  ignoredPackages.add("androidx.databinding")

  /**
   * Sub-projects that are excluded from API validation
   */
  ignoredProjects.addAll(listOf("app"))

  /**
   * Flag to programmatically disable compatibility validator
   */
  validationDisabled = false
}

tasks.register<DependencyInfoGenerator>("generateDependenciesDocs") {
  generateBomMapping()
}
