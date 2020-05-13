plugins {
  idea
  java
  application
  id("io.franzbecker.gradle-lombok") version "4.0.0"
  id("com.github.ben-manes.versions") version "0.28.0"
}

val mainClass: String by project
val vavrVersion: String by project
val slf4jVersion: String by project
val lombokVersion: String by project
val junit4Version: String by project
val springVersion: String by project
val logbackVersion: String by project
val assertkVersion: String by project
val assertjVersion: String by project
val javaVersion = JavaVersion.VERSION_1_8
val junitJupiterVersion: String by project
val gradleWrapperVersion: String by project

tasks.withType(Wrapper::class.java) {
  gradleVersion = gradleWrapperVersion
  distributionType = Wrapper.DistributionType.BIN
}

java {
  sourceCompatibility = javaVersion
  targetCompatibility = javaVersion
}

repositories {
  mavenCentral()
}

lombok {
  version = lombokVersion
}

dependencies {
  implementation(platform("org.springframework:spring-framework-bom:$springVersion"))
  implementation("org.springframework:spring-context-support")

  implementation("io.vavr:vavr:$vavrVersion")
  implementation("org.slf4j:slf4j-api:$slf4jVersion")
  implementation("ch.qos.logback:logback-classic:$logbackVersion")
  annotationProcessor("org.projectlombok:lombok:$lombokVersion")

  testImplementation("org.assertj:assertj-core:$assertjVersion")
  testImplementation(platform("org.junit:junit-bom:$junitJupiterVersion"))
  testImplementation("org.junit.jupiter:junit-jupiter")
  testRuntimeOnly("org.junit.vintage:junit-vintage-engine")
  testImplementation("junit:junit:$junit4Version")
}

tasks.withType<Test> {
  useJUnitPlatform()
  testLogging {
    showExceptions = true
    showStandardStreams = true
    events(
        org.gradle.api.tasks.testing.logging.TestLogEvent.PASSED,
        org.gradle.api.tasks.testing.logging.TestLogEvent.SKIPPED,
        org.gradle.api.tasks.testing.logging.TestLogEvent.FAILED
    )
  }
}

application {
  mainClassName = "$mainClass"
}

tasks {
  register<Jar>("fatJar") {
    //archiveAppendix.set("all")
    archiveClassifier.set("all")
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    manifest {
      attributes("Main-Class" to mainClass)
    }
    from(configurations.runtimeClasspath.get()
        .onEach { println("add from dependencies: ${it.name}") }
        .map { if (it.isDirectory) it else zipTree(it) })
    val sourcesMain = sourceSets.main.get()
    sourcesMain.allSource.forEach { println("add from sources: ${it.name}") }
    from(sourcesMain.output)
  }

  register<Zip>("sources") {
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

defaultTasks("clean", "sources", "fatJar", "test")
//defaultTasks("clean", "sources", "fatJar", "installDist", "distZip")
