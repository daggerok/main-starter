import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.gradle.api.tasks.testing.logging.TestLogEvent.*

plugins {
  java
  application
  kotlin("jvm") version Globals.kotlinVersion
  kotlin("plugin.spring") version Globals.kotlinVersion
  id("io.freefair.lombok") version Globals.Gradle.Plugin.lombokVersion
}

group = Globals.Project.groupId
version = Globals.Project.version

sourceSets {
  main {
    java.srcDir("src/main/kotlin")
  }
  test {
    java.srcDir("src/test/kotlin")
  }
}

java {
  sourceCompatibility = Globals.javaVersion
  targetCompatibility = Globals.javaVersion
}

repositories {
  jcenter()
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

  implementation(kotlin("stdlib"))
  implementation(kotlin("reflect"))
  implementation(kotlin("test-junit"))
  //implementation("org.jetbrains.kotlin:kotlin-test-junit5:${Globals.kotlinVersion}") { ... }
  implementation(kotlin("test-junit5") as String) {
    exclude(group = "org.junit.vintage", module = "*")
  }
  testImplementation("com.willowtreeapps.assertk:assertk-jvm:${Globals.assertkVersion}") {
    exclude(group = "org.jetbrains.kotlin", module = "*")
  }

  testImplementation(platform("org.junit:junit-bom:${Globals.junitJupiterVersion}"))
  testImplementation("org.junit.vintage:junit-vintage-engine")
  testImplementation("org.junit.jupiter:junit-jupiter")

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

  withType<KotlinCompile>().configureEach {
    kotlinOptions {
      freeCompilerArgs += "-Xjsr305=strict"
      jvmTarget = "${Globals.javaVersion}"
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
