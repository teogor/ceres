package dev.teogor.ceres.gradle.plugins

import org.gradle.api.publish.maven.MavenPom

open class CeresLibraryExtension {
  val groupId: String = "dev.teogor.ceres"
  var artifactId: String? = null
  var version: String? = null
  var name: String? = null
  var deprecated: Boolean = false

  private fun MavenPom.info() {
    name.set(
      if (this@CeresLibraryExtension.name != null) {
        this@CeresLibraryExtension.name
      } else if (artifactId != null) {
        if (artifactId!!.startsWith("ceres-")) {
          artifactId
        } else {
          "ceres-$artifactId"
        }
      } else {
        groupId
      },
    )
    description.set(
      "Streamline Android Development with Compose, Hilt, Coroutines, and Flow Integration.",
    )
    inceptionYear.set("2022")
    url.set("https://github.com/teogor/ceres/")
  }

  private fun MavenPom.license() {
    licenses {
      license {
        name.set("The Apache License, Version 2.0")
        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
        distribution.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
      }
    }
  }

  private fun MavenPom.developers() {
    developers {
      developer {
        id.set("teogor")
        name.set("Teodor Grigor")
        url.set("https://teogor.dev")
        roles.set(listOf("Code Owner", "Developer", "Designer", "Maintainer"))
        timezone.set("UTC+2")
        organization.set("Teogor")
        organizationUrl.set("https://github.com/teogor")
      }
    }
  }

  private fun MavenPom.scm() {
    scm {
      url.set("https://github.com/teogor/ceres/")
      connection.set("scm:git:git://github.com/teogor/ceres.git")
      developerConnection.set("scm:git:ssh://git@github.com/teogor/ceres.git")
    }
  }

  fun applyToMaven(pom: MavenPom) {
    pom.apply {
      info()
      license()
      developers()
      scm()
    }
  }
}
