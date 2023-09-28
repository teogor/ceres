package dev.teogor.ceres.gradle.plugins

import org.gradle.api.Plugin
import org.gradle.api.Project
import java.io.File
import java.nio.file.Files

class CeresModulePlugin : Plugin<Project> {
  override fun apply(target: Project) {
    with(target) {
      // Create an extension to allow users to configure the "ceresModule" block
      val ceresModuleExtension = project.extensions.create(
        "ceresModule", CeresModuleExtension::class.java,
      )
      pluginManager.apply("com.vanniktech.maven.publish")

      // Configure the "ceresModule" block based on the extension properties
      project.afterEvaluate {
        val artifactIdPrefix = ceresModuleExtension.artifactIdPrefix!!
        val version = ceresModuleExtension.version!!
        val isBomModule = ceresModuleExtension.isBomModule

        if (!isBomModule) {
          subprojects {
            pluginManager.apply("dev.teogor.ceres.library.publish")
            val libraryPublish = project.extensions.getByType(CeresLibraryExtension::class.java)

            val moduleName = this.name

            libraryPublish.apply {
              this.artifactId = "$artifactIdPrefix-$moduleName"
              this.version = version
            }
          }
        } else {
          pluginManager.apply("dev.teogor.ceres.library.publish")
          val libraryPublish = project.extensions.getByType(CeresLibraryExtension::class.java)

          libraryPublish.apply {
            this.artifactId = artifactIdPrefix
            this.version = version
          }
        }
        val artifactIdPrefixTitlecase = artifactIdPrefix.replaceFirstChar { it.titlecase() }
        val taskName = "publish${artifactIdPrefixTitlecase}LibrariesToMavenCentral"
        project.tasks.create(taskName) {
          if (isBomModule) {
            dependsOn("${this@with.path}:publish")
          }
          if (!isBomModule) {
            subprojects {
              dependsOn("$path:publish")
            }
          }
        }
        if (!isBomModule) {
          val rootProjectDir = project.rootDir
          project.tasks.create("generateCeresDocs") {
            doLast {
              val fileName = "module-$artifactIdPrefix.md"

              val docsDir = File(rootProjectDir, "docs")
              docsDir.mkdirs()

              val outputFile = File(docsDir, fileName)
              outputFile.writeText(
                generateMarkdownContent(
                  subprojects.toList(),
                  group = "dev.teogor.ceres",
                  artifactIdPrefix = artifactIdPrefix,
                  version = ceresModuleExtension.version!!,
                ),
              )
            }
          }
        }

        prepareWorkflow(
          outputFile = rootProject.file(".github/workflows/publish-$name.yml"),
          path = name,
          artifactIdPrefixTitlecase = artifactIdPrefixTitlecase,
        )
      }
    }
  }

  private fun prepareWorkflow(
    outputFile: File,
    path: String,
    artifactIdPrefixTitlecase: String,
  ) {
    val dollar = "$"
    val workflowContent = """
        name: Publish $artifactIdPrefixTitlecase

        on:
          workflow_dispatch:

        jobs:
          publish:
            name: Snapshot build and publish
            runs-on: ubuntu-latest
            environment: PRODUCTION
            timeout-minutes: 120
            env:
              ORG_GRADLE_PROJECT_mavenCentralUsername: $dollar{{ secrets.OSSRH_USERNAME }}
              ORG_GRADLE_PROJECT_mavenCentralPassword: $dollar{{ secrets.OSSRH_PASSWORD }}
              ORG_GRADLE_PROJECT_signingInMemoryKeyId: $dollar{{ secrets.SIGNING_KEY_ID }}
              ORG_GRADLE_PROJECT_signingInMemoryKeyPassword: $dollar{{ secrets.SIGNING_PASSWORD }}
              ORG_GRADLE_PROJECT_signingInMemoryKey: $dollar{{ secrets.SIGNING_KEY }}
              SONATYPE_CONNECT_TIMEOUT_SECONDS: 120
              SONATYPE_CLOSE_TIMEOUT_SECONDS: 1800

            steps:
              - name: Check out code
                uses: actions/checkout@v3.1.0

              - name: Set up JDK 17
                uses: actions/setup-java@v3.5.1
                with:
                  distribution: 'zulu'
                  java-version: 17

              - name: Grant Permission to Execute Gradle
                run: chmod +x gradlew

              - name: Publish to MavenCentral
                run: |
                  ./gradlew :$path:publish${artifactIdPrefixTitlecase}LibrariesToMavenCentral --no-configuration-cache
    """.trimIndent()

    Files.write(outputFile.toPath(), workflowContent.toByteArray())
  }

  private fun generateMarkdownContent(
    componentModules: List<Project>,
    group: String,
    artifactIdPrefix: String,
    version: String,
  ): String {
    val markdownBuilder = StringBuilder()
    markdownBuilder.appendLine("# ${capitalizeAndReplace(artifactIdPrefix)} Modules")
    markdownBuilder.appendLine("")
    componentModules.forEach { module ->
      val moduleNameCapitalized = capitalizeAndReplace(module.name)
      markdownBuilder.appendLine("## $moduleNameCapitalized Module")
      markdownBuilder.appendLine("- **Description:** This module provides `${module.name}` functionality.")
      markdownBuilder.appendLine(
        "- **Source Code:** [View Source](..${
          module.path.replace(
            ":",
            "/",
          )
        })",
      )
      markdownBuilder.appendLine("")
      markdownBuilder.appendLine("### Implementation")
      markdownBuilder.appendLine("```kotlin")
      markdownBuilder.appendLine("implementation(\"$group:${artifactIdPrefix}-${module.name}:$version\")")
      markdownBuilder.appendLine("```")
      markdownBuilder.appendLine("")
    }
    return markdownBuilder.toString()
  }

  private fun capitalizeAndReplace(input: String): String {
    val words = input.split("-")
    val capitalizedWords = words.map { it.replaceFirstChar { char -> char.titlecase() } }
    return capitalizedWords.joinToString(" ")
  }
}
