import com.google.gson.Gson
import dev.teogor.ceres.gradle.plugins.CeresLibraryExtension
import java.io.File
import java.time.Instant
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.gradle.kotlin.dsl.provideDelegate

@Serializable
data class LibraryInfo(
  val name: String,
  val path: String,
  val groupId: String,
  val artifactId: String,
  val version: String,
  val deprecated: Boolean,
) {
  val gradleDependency = "$groupId:$artifactId"
  val coordinates = "$groupId:$artifactId:$version"

  val localPath = path.replace(":", "/")
  val isBom = artifactId.contains("bom")
  val module = path.split(":")[1]
}

@Serializable
data class BomInfo(
  val version: String,
  val date: Long,
) {
  val dateFormatted: String
    get() {
      val instant = Instant.ofEpochSecond(date)
      val zoneId = ZoneId.of("UTC") // You can specify your desired time zone
      val zonedDateTime = instant.atZone(zoneId)
      val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy")
      return formatter.format(zonedDateTime)
    }
}

fun MutableList<LibraryInfo>.bom() = this.find {
  it.isBom
}

open class DependencyInfoGenerator : DefaultTask() {

  init {
    group = "dev.teogor.ceres"
    description =
      "Generates documentation and metadata files for Ceres project libraries and Bill of Materials (BoM) versions."
  }

  private val libraries = mutableListOf<LibraryInfo>()

  // region FOLDERS
  private val docsFolder = File("docs").apply {
    mkdirs()
  }

  private val resFolder = File(".resources").apply {
    mkdirs()
  }

  private val bomFolder = File("$docsFolder\\bom").apply {
    mkdirs()
  }

  private val bomResFolder = File("$resFolder\\bom").apply {
    mkdirs()
  }

  private val bomLibrary by lazy {
    libraries.bom()!!
  }

  private val bomLibraryInfo by lazy {
    File("$bomFolder\\${bomLibrary.version}").apply {
      mkdirs()
    }
  }

  private val bomResLibraryInfo by lazy {
    File("$bomResFolder\\${bomLibrary.version}").apply {
      mkdirs()
    }
  }
  // endregion FOLDERS

  private val alphaEmoji = "\uD83E\uDDEA"
  private val betaEmoji = "\uD83D\uDEE0\uFE0F"
  private val deprecatedEmoji = "\uD83D\uDEA7"

  private val previousBomVersion by lazy {
    val bomInfoList = getBomVersions()
    if(bomInfoList.isNotEmpty()){
      bomInfoList[1]
    } else {
      null
    }
  }

  @TaskAction
  fun generateOutputFile() {
    writeModulesDocs()
    libraries.bom()?.let {
      writeCeresLibrariesCatalog()
      writeBomVersions()
      writeBomMapping()
      writeBomVariants()
    }
  }

  private fun writeCeresLibrariesCatalog() {
    val ceresLibsCatalogContent = buildString {
      appendLine("## Libraries Implementation Version Catalog")
      appendLine()
      appendLine("This catalog provides the implementation details of Ceres libraries, including Build of Materials (BoM) and individual libraries, in TOML format.")
      appendLine()
      appendLine("```toml")
      appendLine("# Ceres BoM")
      libraries
        .filter { it.isBom }
        .forEach { library ->
          appendLine(
            "${
              library.name.lowercase().replace(" ", "-")
            } = { group = \"${library.groupId}\", name = \"${library.artifactId}\", version.ref = \"ceres-bom\" }",
          )
        }

      appendLine("# Ceres Libraries")
      libraries
        .filter { !it.isBom }
        .forEach { library ->
          appendLine(
            "${
              library.name.lowercase().replace(" ", "-")
            } = { group = \"${library.groupId}\", name = \"${library.artifactId}\" }",
          )
        }
      appendLine("```")
      appendLine()
      appendLine("## Libraries Implementation build.gradle.kts File")
      appendLine()
      appendLine("This section presents the implementation dependencies for Ceres libraries in a Kotlin build.gradle.kts file format.")
      appendLine()
      appendLine("```kotlin")
      appendLine("dependencies {")
      appendLine("  // Ceres BoM")
      libraries
        .filter { it.isBom }
        .forEach { library ->
          appendLine(
            "  implementation(platform(libs.${library.name.lowercase().replace(" ", ".")}))",
          )
        }

      appendLine("  // Ceres Libraries")
      libraries
        .filter { !it.isBom }
        .forEach { library ->
          appendLine(
            "  implementation(libs.${library.name.lowercase().replace(" ", ".").replace("-", ".")})",
          )
        }
      appendLine("}")
      appendLine("```")
      appendLine()
    }
    val filePath = "ceres-version-catalog.md"
    File("$docsFolder\\$filePath").bufferedWriter().use { writer ->
      writer.apply {
        write(ceresLibsCatalogContent)
      }
    }
  }

  private fun writeModulesDocs() {

    libraries
      .filter { !it.isBom }
      .groupBy { it.module }
      .forEach { (module, libraries) ->
        val content = StringBuilder()
        content.appendLine("## Ceres $module")
        content.appendLine()
        content.appendLine("| Status | Library | Gradle dependency |")
        content.appendLine("| ------ | ------- | ----------------- |")
        for (library in libraries) {
          val emoji = when {
            library.version.contains("alpha") -> alphaEmoji
            library.version.contains("beta") -> betaEmoji
            library.deprecated -> deprecatedEmoji
            else -> ""
          }
          val link = "#implementation ${library.name}".lowercase().replace(" ", "-")
          content.appendLine("| $emoji | [${library.name}](${library.localPath}) | [${library.coordinates}]($link) |")
        }
        content.appendLine()
        content.appendLine(
          """
          By referring to the [BoM documentation](/docs/bom/versions.md), you can learn how to integrate the BoM into your project and benefit from this hassle-free approach to library version management. It's a powerful tool for staying up-to-date with the latest Ceres library versions and seamlessly integrating them into your projects.
        """.trimIndent(),
        )
        content.appendLine()
        content.appendLine()
        for (library in libraries) {
          content.appendLine(
            """
            ### Implementation ${library.name}

            To use ${library.name} in your Android project, add the following dependency to your module-level Gradle file (usually `app/build.gradle.kts`):

            ```kotlin
            implementation("${library.coordinates}")
            ```

            #### Gradle Dependency

            - **Group ID:** `${library.groupId}`
            - **Artifact ID:** `${library.artifactId}`
            - **Version:** `${library.version}` (not required when using [BoM](/docs/bom/versions.md))
          """.trimIndent(),
          )
          content.appendLine()
        }
        content.appendLine()

        val filePath = "ceres-module-$module.md"
        val bomVersionsFile = File("$docsFolder\\$filePath")
        bomVersionsFile.bufferedWriter().use { writer ->
          writer.apply {
            write(content.toString())
          }
        }
      }
  }

  @OptIn(ExperimentalSerializationApi::class)
  private fun writeBomVersions() {
    val bomInfoList = getBomVersions().toMutableList()
    val hasVersion = bomInfoList.any { it.version == bomLibrary.version }
    if (!hasVersion) {
      val currentUtcTime = Instant.now().atZone(ZoneOffset.UTC)
      bomInfoList.add(
        BomInfo(
          version = bomLibrary.version,
          date = currentUtcTime.toEpochSecond(),
        ),
      )
    }
    val json = Json {
      prettyPrint = true
      prettyPrintIndent = "  "
    }
    val jsonString = json.encodeToString(bomInfoList)

    val filePath = "versions.json"
    val bomVersionsFile = File("$bomResFolder\\$filePath")
    bomVersionsFile.writeText(jsonString)

    // TODO better flow
    writeBomVersionsMd()
  }

  private fun writeBomVersionsMd() {
    val content = StringBuilder()
    val bomInfoList = getBomVersions()

    content.appendLine("## Ceres BoM (Bill of Materials)")
    content.appendLine()
    content.appendLine(
      """
      The BOM (Bill of Materials) is the central hub for managing library versions within the Ceres project.
      It enables you to effortlessly keep track of the latest versions of key components and dependencies.
    """.trimIndent(),
    )
    content.appendLine()
    content.appendLine(
      """
      ### Latest Version

      Here is how to declare dependencies using the latest version `${bomLibrary.version}`:

      ```kt
      dependencies {
        // Import the BoM for the Ceres platform using the latest version
        implementation(platform("dev.teogor.ceres:bom:${bomLibrary.version}"))
      }
      ```
    """.trimIndent(),
    )
    content.appendLine()
    content.appendLine("## BoM Versions (Bill of Materials)")
    content.appendLine()
    content.appendLine("Below is a list of the latest versions of the BOM:")
    content.appendLine()
    content.appendLine("| Version | Release Notes | Release Date |")
    content.appendLine("| ------- | ------------- | ------------ |")
    for (bomInfo in bomInfoList) {
      content.appendLine("| ${bomInfo.version} | [changelog \uD83D\uDD17](/docs/bom/${bomInfo.version}/bom-version-${bomInfo.version}.md) | ${bomInfo.dateFormatted} |")
    }
    content.appendLine()
    content.appendLine(
      """
      The **Bill of Materials (BoM)** serves as a cornerstone for maintaining synchronization among various libraries and components in your project. By centralizing version management, it significantly reduces compatibility issues and streamlines the entire dependency management process.

      ### Advantages of Using the BoM

      - **Synchronization:** The BoM guarantees that all libraries within your project are aligned, ensuring seamless compatibility.
      - **Staying Current:** By adopting the BoM, you effortlessly stay updated with the latest advancements within the ever-evolving Ceres ecosystem.

      ### Explore Further

      For in-depth insights, updates, and comprehensive information regarding Ceres, please consult the official [Ceres documentation](/docs/). There, you'll discover a wealth of resources to enhance your Ceres development journey.
    """.trimIndent(),
    )
    content.appendLine()

    val filePath = "versions.md"
    val bomVersionsFile = File("$bomFolder\\$filePath")
    bomVersionsFile.bufferedWriter().use { writer ->
      writer.apply {
        write(content.toString())
      }
    }
  }

  private fun getBomVersions(): List<BomInfo> {
    val filePath = "versions.json"
    val bomVersionsFile = File("$bomResFolder\\$filePath")
    return if (bomVersionsFile.exists()) {
      val jsonString = bomVersionsFile.readText()
      val bomInfoList = Json.decodeFromString<List<BomInfo>>(jsonString)
      bomInfoList.sortedByDescending { it.date }
    } else {
      emptyList()
    }
  }

  private fun writeBomMapping() {
    val filePath = "bom-version-${bomLibrary.version}.md"
    val bomMappingFile = File("$bomLibraryInfo\\$filePath")

    bomMappingFile.bufferedWriter().use { writer ->
      libraries.bom()?.let {
        val textToAdd = """
          # Ceres BoM v${it.version} (Bill of Materials)

          The Ceres BoM (Bill of Materials) enables you to manage all your Ceres library versions by specifying only one version — the BoM's version.

          When you use the Ceres BoM in your app, the BoM automatically pulls in the individual library versions mapped to the BoM's version. All the individual library versions will be compatible. When you update the BoM's version in your app, all the Ceres libraries that you use in your app will update to the versions mapped to that BoM version.

          To learn which Ceres library versions are mapped to a specific BoM version, check out the release notes for that BoM version. If you need to compare the library versions mapped to one BoM version compared to another BoM version, use the comparison widget below.

          Learn more about [Gradle's support for BoM platforms](https://docs.gradle.org/4.6-rc-1/userguide/managing_transitive_dependencies.html#sec:bom_import).

          Here's how to use the Ceres Android BoM to declare dependencies in your module (app-level) Gradle file (usually app/build.gradle.kts). When using the BoM, you don't specify individual library versions in the dependency lines.

          ```kt
          dependencies {
            // Import the BoM for the Ceres platform
            implementation(platform("dev.teogor.ceres:bom:${it.version}"))

            // Declare the dependencies for the desired Ceres products without specifying versions
            // For example, declare the dependencies for Ceres Core Runtime and Ceres Core Network
            implementation("dev.teogor.ceres:core-runtime")
            implementation("dev.teogor.ceres:core-network")
          }
          ```
        """.trimIndent()

        writer.apply {
          write(textToAdd)
          newLine()
        }
      }

      val moduleContent = buildString {
        appendLine()
        appendLine("## Latest SDK versions")
        appendLine()
        appendLine("| Status | Service or Product | Gradle dependency | Latest version |")
        appendLine("| ------ | ------------------ | ----------------- | -------------- |")

        println("Previous BoM ${previousBomVersion?.version} released on ${previousBomVersion?.dateFormatted}")
        val previousBomDependencies = if (previousBomVersion != null) {
          getDependenciesByVersion(previousBomVersion!!.version)
        } else {
          emptyList()
        }
        previousBomDependencies.forEach {
          println("$it")
        }
        libraries
          .filter { !it.isBom }
          .forEach { library ->
            val emoji = when {
              library.version.contains("alpha") -> alphaEmoji
              library.version.contains("beta") -> betaEmoji
              library.deprecated -> deprecatedEmoji
              else -> ""
            }
            val previousVersionData = previousBomDependencies.firstOrNull {
              it.artifactId == library.artifactId
            }
            val previousVersion = previousVersionData?.version ?: "N/A"
            val versionData = if (previousVersionData?.version != null) {
              val previousVersionDataD = previousVersionData.version
              if (previousVersionDataD != library.version) {
                "$previousVersionDataD -> ${library.version}"
              } else {
                library.version
              }
            } else {
              library.version
            }
            appendLine("| $emoji | [${library.name}](${library.localPath}) | ${library.gradleDependency} | ${library.version} |")
          }
        appendLine()
        appendLine(
          """
          ### Explore Further

          For the latest updates, in-depth documentation, and a comprehensive overview of the Ceres ecosystem, visit the official [Ceres documentation](/docs/). It's your gateway to a wealth of resources and insights that will elevate your Ceres development journey.

          Stay informed, stay current, and embrace the full potential of Ceres.
        """.trimIndent(),
        )
      }

      writer.apply {
        write(moduleContent)
      }
    }
  }

  @OptIn(ExperimentalSerializationApi::class)
  private fun writeBomVariants() {
    val filePath = "dependencies-${bomLibrary.version}.json"
    val bomMappingFile = File("$bomResLibraryInfo\\$filePath")

    val libraryInfoList = libraries.filter { !it.isBom }

    val json = Json {
      prettyPrint = true
      prettyPrintIndent = "  "
    }
    val jsonString = json.encodeToString(libraryInfoList)

    bomMappingFile.writeText(jsonString)
  }

  private fun getDependenciesByVersion(version: String): List<LibraryInfo> {
    val filePath = "dependencies-$version.json"
    val bomMappingFile = File("$bomResFolder\\$version\\$filePath")

    if (!bomMappingFile.exists()) {
      return emptyList()
    }

    return try {
      val jsonString = bomMappingFile.readText()
      val gson = Gson()
      val libraryInfoList = gson.fromJson(jsonString, Array<LibraryInfo>::class.java)
      println("libraryInfoList::${libraryInfoList.toList().size}")
      return libraryInfoList.toList()
    } catch (e: Exception) {
      emptyList()
    }
  }

  fun generateBomMapping() {
    val excludeModules = listOf(":app")
    project.subprojects {
      if (!excludeModules.contains(path)) {
        if (parent != project) {
          val ceresLibraryExtension = extensions.findByType(CeresLibraryExtension::class.java)
          ceresLibraryExtension?.let {
            libraries.add(
              LibraryInfo(
                name = it.name!!,
                path = path,
                groupId = it.groupId,
                artifactId = it.artifactId!!,
                version = it.version!!,
                deprecated = it.deprecated,
              ),
            )
          }
        }
      }
    }
  }
}
