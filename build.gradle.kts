import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import org.gradle.api.tasks.testing.logging.TestLogEvent.*
import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
  java
  scala
  id("org.springframework.boot") version Globals.Gradle.Plugin.springBootVersion
  id("io.franzbecker.gradle-lombok") version Globals.Gradle.Plugin.lombokVersion
  // gradle dependencyUpdates -Drevision=release
  id("com.github.ben-manes.versions") version Globals.Gradle.Plugin.versionsVersion
  id("io.spring.dependency-management") version Globals.Gradle.Plugin.dependencyManagementVersion
}

extra["scala.version"] = Globals.scalaVersion
extra["junit.version"] = Globals.junitVersion
extra["lombok.version"] = Globals.lombokVersion
extra["junit-jupiter.version"] = Globals.junitJupiterVersion

group = Globals.Project.groupId
version = Globals.Project.version

java {
  sourceCompatibility = Globals.javaVersion
  targetCompatibility = Globals.javaVersion
}

sourceSets {
  main {
    java.srcDir("src/main/scala")
  }
  test {
    java.srcDir("src/test/scala")
  }
}

repositories {
  mavenCentral()
  maven { url = uri("https://repo.spring.io/snapshot/") }
  maven { url = uri("https://repo.spring.io/milestone/") }
}

lombok {
  version = Globals.lombokVersion
}

dependencies {
  implementation(platform("org.springframework.boot:spring-boot-dependencies:${Globals.Gradle.Plugin.springBootVersion}"))
  implementation(platform("org.reactivestreams:reactive-streams:${Globals.reactiveStreamsVersion}"))
  implementation("com.typesafe.akka:akka-stream_${Globals.scalaMajorVersion}:${Globals.akkaStreamVersion}")
  implementation("org.scala-lang:scala-library:${Globals.scalaVersion}")

  implementation("org.springframework.boot:spring-boot-starter")
  implementation("org.springframework.boot:spring-boot-starter-webflux")
  implementation("org.springframework.boot:spring-boot-starter-actuator")
  implementation("org.springframework.boot:spring-boot-starter-data-mongodb-reactive")
  implementation("de.flapdoodle.embed:de.flapdoodle.embed.mongo")
  implementation("io.vavr:vavr:${Globals.vavrVersion}")

  annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
  testAnnotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
  annotationProcessor("org.projectlombok:lombok")

  testImplementation("org.springframework.boot:spring-boot-starter-test")
  testImplementation("io.projectreactor:reactor-test")

  testImplementation(platform("org.junit:junit-bom:${Globals.junitJupiterVersion}"))
  testImplementation("org.junit.jupiter:junit-jupiter")
  testImplementation("junit:junit")
  testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
  testRuntimeOnly("org.junit.vintage:junit-vintage-engine")
  testRuntime("org.junit.platform:junit-platform-launcher")
}

tasks {
  withType(Wrapper::class.java) {
    gradleVersion = Globals.Gradle.wrapperVersion
    distributionType = Wrapper.DistributionType.BIN
  }

  withType<ScalaCompile> {
    scalaCompileOptions.additionalParameters = listOf("-deprecation")
  }

  withType<BootJar>().configureEach {
    launchScript()
  }

  withType<Test> {
    useJUnitPlatform()
    testLogging {
      showExceptions = true
      showStandardStreams = true
      events(PASSED, SKIPPED, FAILED)
    }
  }

  create<Zip>("sources") {
    dependsOn("clean")
    shouldRunAfter("clean", "assemble")
    description = "Archives sources in a zip file"
    group = "Archive"
    from("buildSrc") {
      into("buildSrc")
    }
    from("src") {
      into("src")
    }
    from(".gitignore")
    from(".java-version")
    from(".travis.yml")
    from("build.gradle.kts")
    from("pom.xml")
    from("README.md")
    from("settings.gradle.kts")
    archiveFileName.set("${project.buildDir}/sources-${project.version}.zip")
  }

  named("clean") {
    doLast {
      delete(
          project.buildDir,
          "${project.projectDir}/out"
      )
    }
  }

  // gradle dependencyUpdates -Drevision=release --parallel
  named<DependencyUpdatesTask>("dependencyUpdates") {
    resolutionStrategy {
      componentSelection {
        all {
          val rejected = listOf("alpha", "beta", "rc", "cr", "m", "preview", "b", "ea", "SNAPSHOT")
              .map { qualifier -> Regex("(?i).*[.-]$qualifier[.\\d-+]*") }
              .any { it.matches(candidate.version) }
          if (rejected) reject("Release candidate")
        }
      }
    }
  }
}

defaultTasks("clean", "sources", "build")
