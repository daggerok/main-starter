import org.gradle.api.JavaVersion

object Globals {
  object Project {
    const val artifactId = "main-starter"
    const val groupId = "com.github.daggerok"
    const val version = "1.3-SNAPSHOT"
  }

  val javaVersion = JavaVersion.VERSION_1_8
  const val vavrVersion = "0.10.0"
  const val lombokVersion = "1.18.8"
  const val junitVersion = "4.13-beta-3"
  const val junitJupiterVersion = "5.5.0"

  object Gradle {
    const val wrapperVersion = "5.5"

    object Plugin {
      const val lombokVersion = "3.1.0"
      const val versionsVersion = "0.21.0"
      const val springBootVersion = "2.2.0.M4"
      const val dependencyManagementVersion = "1.0.8.RELEASE"
    }
  }
}
