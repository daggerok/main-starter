import org.gradle.api.JavaVersion

object Globals {
  object Project {
    const val artifactId = "main-starter"
    const val groupId = "com.github.daggerok"
    const val version = "1.2-SNAPSHOT"
    const val mainClass = "com.github.daggerok.App"
  }

  val javaVersion = JavaVersion.VERSION_1_8

  const val kotlinVersion = "1.3.31"
  const val lombokVersion = "1.18.8"
  const val vavrVersion = "1.0.0-alpha-2"
  const val weldVersion = "3.1.1.Final"
  const val cdiApiVersion = "2.0"
  const val jandexVersion = "2.1.1.Final"
  const val slf4jVersion = "1.8.0-beta4"
  const val logbackVersion = "1.3.0-alpha4"
  const val assertkVersion = "0.16"
  const val assertjVersion = "3.12.2"
  const val junitJupiterVersion = "5.5.0-M1"
  const val junitPlatformVersion = "1.5.0-M1"

  object Gradle {
    const val wrapperVersion = "5.4.1"

    object Plugin {
      const val lombokVersion = "3.6.4"
      const val versionsVersion = "0.21.0"
    }
  }
}
