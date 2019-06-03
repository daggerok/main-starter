import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.gradle.api.tasks.testing.logging.TestLogEvent.*

plugins {
  java
  scala
  application
  id("com.github.ben-manes.versions") version Globals.Gradle.Plugin.versionsVersion
  id("com.github.johnrengelman.shadow") version Globals.Gradle.Plugin.shadowVersion
}

group = Globals.Project.groupId
version = Globals.Project.version

sourceSets {
  main {
    withConvention(ScalaSourceSet::class) {
      scala {
        setSrcDirs(listOf("src/main/scala"))
      }
    }
  }
  test {
    withConvention(ScalaSourceSet::class) {
      scala {
        setSrcDirs(listOf("src/test/scala"))
      }
    }
  }
}

java {
  sourceCompatibility = Globals.javaVersion
  targetCompatibility = Globals.javaVersion
}

repositories {
  mavenCentral()
}

dependencies {
  implementation("org.springframework:spring-context-support:${Globals.springVersion}")

  implementation("ch.qos.logback:logback-classic:${Globals.logbackVersion}")
  implementation("org.slf4j:slf4j-api:${Globals.slf4jVersion}")

  implementation("org.scala-lang:scala-library:${Globals.scalaVersion}")

  testImplementation(platform("org.junit:junit-bom:${Globals.junitJupiterVersion}"))
  testImplementation("org.junit.jupiter:junit-jupiter")
  testImplementation("junit:junit:${Globals.junitVersion}")
  testRuntimeOnly("org.junit.vintage:junit-vintage-engine")
}

tasks.withType<Test> {
  useJUnitPlatform()
  testLogging {
    showExceptions = true
    showStandardStreams = true
    events(PASSED, SKIPPED, FAILED)
  }
}

application {
  mainClassName = Globals.Project.mainClass
}

tasks {
  withType(Wrapper::class.java) {
    gradleVersion = Globals.Gradle.wrapperVersion
    distributionType = Wrapper.DistributionType.BIN
  }

  withType<ShadowJar> {
    transform(com.github.jengelman.gradle.plugins.shadow.transformers.AppendingTransformer::class.java) {
      resource = "reference.conf"
    }
  }

  register<Zip>("sources") {
    dependsOn("clean")
    shouldRunAfter("clean", "assemble")
    description = "Archives sources in a zip file"
    group = "Archive"
    from("src") {
      into("src")
    }
    from("buildSrc/build.gradle.kts") {
      into("buildSrc")
    }
    from("buildSrc/src/main/java") {
      into("buildSrc/main/java")
    }
    from(".gitignore")
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
}

defaultTasks("clean", "sources", "shadowJar", "installDist", "distZip", "build")
