import org.gradle.api.JavaVersion

object Globals {
  object Project {
    const val artifactId = "main-starter"
    const val groupId = "com.github.daggerok"
    const val version = "1.3-SNAPSHOT"
    const val mainClass = "com.github.daggerok.App"
  }

  val javaVersion = JavaVersion.VERSION_1_8

  const val cdiApiVersion = "2.0"
  const val vavrVersion = "0.10.0"
  const val kotlinVersion = "1.3.31"
  const val lombokVersion = "1.18.8"
  const val assertjVersion = "3.12.2"
  const val weldVersion = "3.1.1.Final"
  const val slf4jVersion = "1.8.0-beta4"
  const val jandexVersion = "2.1.1.Final"
  const val logbackVersion = "1.3.0-alpha4"
  const val junitJupiterVersion = "5.5.0-RC1"

  object Gradle {
    const val wrapperVersion = "5.5-rc-2"

    object Plugin {
      const val lombokVersion = "3.6.6"
      const val versionsVersion = "0.21.0"
    }
  }
}
