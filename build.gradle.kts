import org.gradle.api.tasks.testing.logging.TestLogEvent.*

plugins {
  java
  application
  id("io.freefair.lombok") version Globals.Gradle.Plugin.lombokVersion
  id("com.github.ben-manes.versions") version Globals.Gradle.Plugin.versionsVersion
}

group = Globals.Project.groupId
version = Globals.Project.version

java {
  sourceCompatibility = Globals.javaVersion
  targetCompatibility = Globals.javaVersion
}

repositories {
  mavenCentral()
}

lombok {
  version.set(Globals.lombokVersion)
}

dependencies {
  implementation("org.jboss.weld.se:weld-se-core:${Globals.weldVersion}")
  implementation("javax.enterprise:cdi-api:${Globals.cdiApiVersion}")
  implementation("org.jboss:jandex:${Globals.jandexVersion}")

  implementation("org.slf4j:slf4j-api:${Globals.slf4jVersion}")
  implementation("ch.qos.logback:logback-classic:${Globals.logbackVersion}")
  annotationProcessor("org.projectlombok:lombok:${Globals.lombokVersion}")
  implementation("io.vavr:vavr:${Globals.vavrVersion}")

  testImplementation(platform("org.junit:junit-bom:${Globals.junitJupiterVersion}"))
  testImplementation("org.junit.vintage:junit-vintage-engine")
  testImplementation("org.junit.jupiter:junit-jupiter")
  testImplementation("junit:junit")

  testImplementation("org.assertj:assertj-core:${Globals.assertjVersion}")
}

application {
  mainClassName = Globals.Project.mainClass
}

tasks {
  withType(Wrapper::class.java) {
    gradleVersion = Globals.Gradle.wrapperVersion
    distributionType = Wrapper.DistributionType.BIN
  }

  withType<Test> {
    useJUnitPlatform()
    testLogging {
      showExceptions = true
      showStandardStreams = true
      events(PASSED, SKIPPED, FAILED)
    }
  }

  register("fatJar", Jar::class.java) {
    //archiveAppendix.set("all")
    archiveClassifier.set("all")
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    manifest {
      attributes("Main-Class" to Globals.Project.mainClass)
    }
    from(configurations.runtimeClasspath.get()
        .onEach { println("add from dependencies: ${it.name}") }
        .map { if (it.isDirectory) it else zipTree(it) })
    val sourcesMain = sourceSets.main.get()
    sourcesMain.allSource.forEach { println("add from sources: ${it.name}") }
    from(sourcesMain.output)
  }

  create<Zip>("sources") {
    dependsOn("clean")
    shouldRunAfter("clean", "assemble")
    description = "Archives sources in a zip file"
    group = "Archive"
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
    archiveFileName.set("${project.buildDir}/sources-${Globals.Project.version}.zip")
  }

  named("clean") {
    doLast {
      delete(
          project.buildDir,
          "${project.projectDir}/out"
      )
    }
  }
}

defaultTasks("clean", "sources", "fatJar", "installDist", "test")

//// ./gradlew dependencyUpdates -Drevision=release
//tasks.named<com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask>("dependencyUpdates") {
//  resolutionStrategy {
//    componentSelection {
//      all {
//        val rejected = listOf("alpha", "beta", "rc", "cr", "m", "preview", "b", "ea", "M1", "BUILD-SNAPSHOT", "SNAPSHOT")
//            .map { qualifier -> Regex("(?i).*[.-]$qualifier[.\\d-+]*") }
//            .any { it.matches(candidate.version) }
//        if (rejected) reject("Release candidate")
//      }
//    }
//  }
//  outputFormatter = "plain" // "json"
//}
