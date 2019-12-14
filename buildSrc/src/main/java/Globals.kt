import org.gradle.api.JavaVersion

object Globals {
  object Project {
    const val groupId = "com.github.daggerok"
    const val version = "1.7-SNAPSHOT"
    const val mainClass = "com.github.daggerok.App"
  }

  val javaVersion = JavaVersion.VERSION_1_8

  const val cdiApiVersion = "2.0"
  const val vavrVersion = "0.10.2"
  const val kotlinVersion = "1.3.50"
  const val lombokVersion = "1.18.10"
  const val assertjVersion = "3.13.2"
  const val weldVersion = "3.1.2.Final"
  const val slf4jVersion = "2.0.0-alpha0"
  const val jandexVersion = "2.1.1.Final"
  const val logbackVersion = "1.3.0-alpha4"
  const val junitJupiterVersion = "5.5.2"

  object Gradle {
    const val wrapperVersion = "5.6.2"

    object Plugin {
      const val lombokVersion = "3.1.0"
      const val versionsVersion = "0.25.0"
    }
  }
}
