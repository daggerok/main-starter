import org.gradle.api.JavaVersion

object Globals {
  object Project {
    const val artifactId = "main-starter"
    const val groupId = "com.github.daggerok"
    const val version = "1.1-SNAPSHOT"
    const val mainClass = "com.github.daggerok.App"
  }

  object Spring {
    const val springBootVersion = "2.1.5.RELEASE"
    const val springDataR2dbcVersion = "1.0.0.M1"
    const val springFuJafuVersion = "0.0.5"
  }

  object R2dbc {
    const val r2dbcH2Version = "1.0.0.BUILD-SNAPSHOT"
    const val r2dbcSpiVersion = "1.0.0.M7"
  }

  val javaVersion = JavaVersion.VERSION_1_8

  const val kotlinVersion = "1.3.31"
  const val lombokVersion = "1.18.8"
  const val vavrVersion = "0.10.0"
  const val weldVersion = "3.1.0.Final"
  const val cdiApiVersion = "2.0"
  const val jandexVersion = "2.1.1.Final"
  const val slf4jVersion = "1.7.26"
  const val logbackVersion = "1.2.3"
  const val assertkVersion = "0.13"
  const val assertjVersion = "3.12.2"
  const val junitJupiterVersion = "5.5.0-M1"
  const val junitPlatformVersion = "1.5.0-M1"

  object Gradle {
    const val wrapperVersion = "5.4.1"

    object Plugin {
      const val lombokVersion = "3.6.1"
      const val versionsVersion = "0.21.0"
      const val dependencyManagementVersion = "1.0.7.RELEASE"
    }
  }
}
