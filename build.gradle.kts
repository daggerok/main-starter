import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  java
  application
  kotlin("jvm") version "1.3.21"
  kotlin("plugin.spring") version "1.3.21"
  id("io.franzbecker.gradle-lombok") version "2.1"
}

allprojects {
  val applicationGroup: String by project
  val applicationVersion: String by project
  group = applicationGroup
  version = applicationVersion
}

tasks.withType(Wrapper::class.java) {
  val gradleWrapperVersion: String by project
  gradleVersion = gradleWrapperVersion
  distributionType = Wrapper.DistributionType.BIN
}

sourceSets {
  main {
    java.srcDir("src/main/kotlin")
  }
  test {
    java.srcDir("src/test/kotlin")
  }
}

val javaVersion = JavaVersion.VERSION_1_8

tasks.withType<KotlinCompile>().configureEach {
  kotlinOptions {
    freeCompilerArgs += "-Xjsr305=strict"
    jvmTarget = "$javaVersion"
  }
}

java {
  sourceCompatibility = javaVersion
  targetCompatibility = javaVersion
}

repositories {
  mavenCentral()
}

val lombokVersion: String by project

lombok {
  version = lombokVersion
}

dependencies {
  implementation(kotlin("stdlib"))
  implementation(kotlin("reflect"))
  implementation("org.jboss.weld.se:weld-se-core:3.1.0.Final")
  implementation("javax.enterprise:cdi-api:2.0")
  implementation("org.jboss:jandex:2.1.1.Final")
  implementation("org.slf4j:slf4j-api:1.7.26")
  implementation("ch.qos.logback:logback-classic:1.2.3")
  annotationProcessor("org.projectlombok:lombok:$lombokVersion")
}

val mainClass: String by project

application {
  mainClassName = mainClass
}

tasks {
  register("fatJar", Jar::class.java) {
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
}

tasks.create<Zip>("sources") {
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

tasks {
  named("clean") {
    doLast {
      delete(
          project.buildDir,
          "${project.projectDir}/out"
      )
    }
  }
}

defaultTasks("clean", "sources", "fatJar", "installDist")
