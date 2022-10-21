import dev.teogor.ceres.Configuration
import org.jetbrains.dokka.gradle.DokkaTaskPartial
import com.vanniktech.maven.publish.SonatypeHost

plugins {
  id("org.jetbrains.dokka") version "1.7.20"
  id("com.vanniktech.maven.publish") version "0.18.0"
}

buildscript {
  repositories {
    google()
    mavenCentral()
    maven("https://plugins.gradle.org/m2/")
  }
  dependencies {
    classpath("com.google.dagger:hilt-android-gradle-plugin:2.40.5")
    classpath("com.google.gms:google-services:4.3.14")
    classpath("com.google.firebase:firebase-crashlytics-gradle:2.9.2")
    classpath("com.google.firebase:firebase-appdistribution-gradle:3.0.3")
    classpath("com.google.firebase:perf-plugin:1.4.2")
    classpath("com.google.android.gms:oss-licenses-plugin:0.10.5")
    classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.5.2")
    classpath("com.android.tools.build:gradle:7.3.1")
    classpath(dev.teogor.ceres.Dependencies.spotlessGradlePlugin)
  }
}

tasks.dokkaHtmlMultiModule.configure {
  outputDirectory.set(rootDir.resolve("docs/api"))
  moduleVersion.set(Configuration.versionName)
}

subprojects {
  plugins.withId("com.android.application") {

  }
  plugins.withId("com.android.library") {
    apply(plugin = "org.jetbrains.dokka")
    apply(plugin = "com.vanniktech.maven.publish")

    // Define versions in a single place
    extra.apply{
      val moduleName = this@subprojects.name.replace("ceres-", "")
      set("moduleName", moduleName.replace("-", "."))
      val publishGroupId = Configuration.artifactGroup
      val publishArtifactId = "${Configuration.baseArtifactId}-${extra.get("moduleName")}"
      val publishVersion = Configuration.versionName

      set("PUBLISH_GROUP_ID", publishGroupId)
      set("PUBLISH_ARTIFACT_ID", publishArtifactId)
      set("VERSION_NAME", publishVersion)
    }

    tasks.withType<DokkaTaskPartial>().configureEach {
      dokkaSourceSets {
        configureEach {
          suppressInheritedMembers.set(true)

          includes.from("Module.md")
          moduleName.set(this@subprojects.name.replace("ceres-", ""))

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
        }
      }
    }
  }

  apply(plugin = "com.diffplug.spotless")

  tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().all {
    kotlinOptions.jvmTarget = JavaVersion.VERSION_11.toString()
    kotlinOptions.freeCompilerArgs += listOf(
      "-Xopt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
      "-Xopt-in=kotlin.time.ExperimentalTime",
    )
  }

  // disabled_rules.from(https://android-arsenal.com/details/1/4278)
  val disabledRulesParams = listOf(
    "filename",
    "no-wildcard-imports",
    "experimental:package-name",
    "experimental:annotation-spacing",
    "experimental:spacing-between-declarations-with-annotations"
  )
  extensions.configure<com.diffplug.gradle.spotless.SpotlessExtension> {
    kotlin {
      target("**/*.kt")
      targetExclude("$buildDir/**/*.kt", "$rootDir/buildSrc/**/*.kt")
      ktlint().setUseExperimental(true).editorConfigOverride(
        mapOf(
          "indent_size" to "2",
          "continuation_indent_size" to "2",
        )
      ).userData(
        mapOf(
          "disabled_rules" to disabledRulesParams.joinToString(separator = ","),
        )
      )
      licenseHeaderFile(rootProject.file("spotless/license.kt"))
      trimTrailingWhitespace()
      endWithNewline()
    }
    format("kts") {
      target("**/*.kts")
      targetExclude("$buildDir/**/*.kts")
      licenseHeaderFile(rootProject.file("spotless/license.kts"), "(^(?![\\/ ]\\*).*$)")
    }
    format("xml") {
      target("**/*.xml")
      targetExclude("**/build/**/*.xml")
      licenseHeaderFile(rootProject.file("spotless/license.xml"), "(<[^!?])")
    }
  }
}

group = Configuration.artifactGroup
version = Configuration.versionName
mavenPublishing {
  publishToMavenCentral(SonatypeHost.S01)
  signAllPublications()

  pom {
    name.set(Configuration.artifactGroup)
    description.set("A description of what my library does.")
    inceptionYear.set("2022")
    url.set("https://github.com/teogor/ceres/")
    licenses {
      license {
        name.set("The Apache License, Version 2.0")
        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
        distribution.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
      }
    }
    developers {
      developer {
        id.set("teogor")
        name.set("Teodor Grigor")
        url.set("https://github.com/teogor/")
      }
    }
    scm {
      url.set("https://github.com/teogor/ceres/")
      connection.set("scm:git:git://github.com/teogor/ceres.git")
      developerConnection.set("scm:git:ssh://git@github.com/teogor/ceres.git")
    }
  }
}

// todo conflicts with maven
//  tasks.register("clean", Delete::class) {
//    delete(rootProject.buildDir)
//  }
