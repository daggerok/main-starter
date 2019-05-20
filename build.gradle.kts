import org.springframework.boot.gradle.tasks.bundling.BootJar
import org.gradle.api.tasks.testing.logging.TestLogEvent.*

plugins {
  idea
  java
  id("io.freefair.lombok") version Globals.Gradle.Plugin.lombokVersion
  id("org.springframework.boot") version Globals.Spring.springBootVersion
  id("com.github.ben-manes.versions") version Globals.Gradle.Plugin.versionsVersion
  id("io.spring.dependency-management") version Globals.Gradle.Plugin.dependencyManagementVersion
}

group = Globals.Project.groupId
version = Globals.Project.version

repositories {
  mavenCentral()
  maven(url = "https://repo.spring.io/milestone")
  maven(url = "https://repo.spring.io/snapshot")
}

java {
  sourceCompatibility = Globals.javaVersion
  targetCompatibility = Globals.javaVersion
}


lombok {
  version.set(Globals.lombokVersion)
}

dependencies {
  implementation("io.r2dbc:r2dbc-h2:${Globals.R2dbc.r2dbcH2Version}")
  implementation("io.r2dbc:r2dbc-spi:${Globals.R2dbc.r2dbcSpiVersion}")
  implementation("org.springframework.data:spring-data-r2dbc:${Globals.Spring.springDataR2dbcVersion}")
  implementation("org.springframework.fu:spring-fu-jafu:${Globals.Spring.springFuJafuVersion}")

  implementation(platform("org.springframework.boot:spring-boot-starter-parent:${Globals.Spring.springBootVersion}"))
  implementation("org.springframework.boot:spring-boot-starter-webflux")
  implementation("org.springframework.boot:spring-boot-starter-json")
  testImplementation("org.springframework.boot:spring-boot-starter-test")
  //annotationProcessor(platform("org.springframework.boot:spring-boot-starter-parent:${Globals.Spring.springBootVersion}"))
  annotationProcessor("org.projectlombok:lombok")
  testCompileOnly("org.projectlombok:lombok")
  implementation("io.vavr:vavr:${Globals.vavrVersion}")
}

tasks {
  withType(Wrapper::class.java) {
    gradleVersion = Globals.Gradle.wrapperVersion
    distributionType = Wrapper.DistributionType.BIN
  }

  named("clean") {
    doLast {
      delete(
          buildDir,
          "$projectDir/out"
      )
    }
  }

  withType<Test> {
    useJUnitPlatform()
    testLogging {
      showExceptions = true
      showStandardStreams = true
      events(PASSED, SKIPPED, FAILED)
    }
  }

  withType(BootJar::class.java) {
    launchScript()
  }

  create<Zip>("sources") {
    dependsOn("clean")
    shouldRunAfter("clean", "assemble")
    description = "Archives sources in a zip file"
    group = "Archive"
    from(".mvn") {
      into(".mvn")
    }
    from("buildSrc/build.gradle.kts") {
      into("buildSrc")
    }
    from("buildSrc/src/main/java") {
      into("buildSrc/src/main/java")
    }
    from("gradle") {
      into("gradle")
    }
    from("src") {
      into("src")
    }
    from(".gitattributes")
    from(".gitignore")
    from("build.gradle.kts")
    from("gradlew")
    from("gradlew.bat")
    from("LICENSE")
    from("maven-version-rules.xml")
    from("mvnw")
    from("mvnw.cmd")
    from("pom.xml")
    from("README.md")
    from("settings.gradle.kts")
    archiveFileName.set("${project.buildDir}/sources-${project.version}.zip")
  }
}

defaultTasks("clean", "sources", "build")
