import org.gradle.api.JavaVersion

object Globals {
  object Project {
    const val artifactId = "main-starter"
    const val groupId = "com.github.daggerok"
    const val version = "1.3-SNAPSHOT"
  }

  object Spring {
    const val springBootVersion = "2.1.6.RELEASE"
    const val springDataR2dbcVersion = "1.0.0.M1"
    const val springFuJafuVersion = "0.0.5"
  }

  object R2dbc {
    const val r2dbcH2Version = "1.0.0.BUILD-SNAPSHOT"
    const val r2dbcSpiVersion = "1.0.0.M7"
  }

  val javaVersion = JavaVersion.VERSION_1_8
  const val lombokVersion = "1.18.8"
  const val vavrVersion = "0.10.1"

  object Gradle {
    const val wrapperVersion = "5.6-rc-1"

    object Plugin {
      const val lombokVersion = "3.6.1"
      const val versionsVersion = "0.21.0"
      const val dependencyManagementVersion = "1.0.8.RELEASE"
    }
  }
}
