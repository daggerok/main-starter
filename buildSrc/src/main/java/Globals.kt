import org.gradle.api.JavaVersion

object Globals {

  object Project {
    const val version = "1.0-SNAPSHOT"
    const val artifactId = "main-starter"
    const val groupId = "com.github.daggerok"
    const val mainClass = "com.github.daggerok.Main"
  }

  val javaVersion = JavaVersion.VERSION_1_8
  const val scalaBaselineVersion = "2.12"
  const val scalaMinorVersion = "8"
  const val scalaVersion = "$scalaBaselineVersion.$scalaMinorVersion"

  const val akkaVersion = "2.5.22"
  const val junit4Version = "4.12"
  const val slf4jVersion = "1.7.26"
  const val logbackVersion = "1.2.3"
  const val scalatestVersion = "3.0.7"
  const val springVersion = "5.1.6.RELEASE"
  const val junitJupiterVersion = "5.5.0-M1"

  object Gradle {
    const val wrapperVersion = "5.4.1"

    object Plugin {
      const val shadowVersion = "5.0.0"
      const val versionsVersion = "0.21.0"
    }
  }
}
