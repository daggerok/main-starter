import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.gradle.api.tasks.testing.logging.TestLogEvent.*

plugins {
  java
  application
  kotlin("jvm") version "1.3.50"
  kotlin("plugin.spring") version "1.3.50"
  id("io.franzbecker.gradle-lombok") version "3.1.0"
  id("com.github.ben-manes.versions") version "0.25.0"
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
java {
  sourceCompatibility = javaVersion
  targetCompatibility = javaVersion
}

repositories {
  mavenCentral()
}

lombok {
  val lombokVersion: String by project
  version = lombokVersion
}

dependencies {
  val weldVersion: String by project
  val slf4jVersion: String by project
  val cdiApiVersion: String by project
  val jandexVersion: String by project
  val junit4Version: String by project
  val lombokVersion: String by project
  val logbackVersion: String by project
  val assertkVersion: String by project
  val assertjVersion: String by project
  val junitJupiterVersion: String by project

  implementation(kotlin("stdlib"))
  implementation(kotlin("reflect"))
  //implementation("io.vavr:vavr:0.10.0")
  implementation("org.jboss.weld.se:weld-se-core:$weldVersion")
  implementation("javax.enterprise:cdi-api:$cdiApiVersion")
  implementation("org.jboss:jandex:$jandexVersion")
  implementation("org.slf4j:slf4j-api:$slf4jVersion")
  implementation("ch.qos.logback:logback-classic:$logbackVersion")
  annotationProcessor("org.projectlombok:lombok:$lombokVersion")

  testImplementation("junit:junit:$junit4Version")
  testImplementation("com.willowtreeapps.assertk:assertk-jvm:$assertkVersion")
  testImplementation("org.assertj:assertj-core:$assertjVersion")
  testImplementation(platform("org.junit:junit-bom:$junitJupiterVersion"))
  testImplementation("org.junit.jupiter:junit-jupiter-api")
  testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
  testRuntimeOnly("org.junit.vintage:junit-vintage-engine")
  testRuntime("org.junit.platform:junit-platform-launcher")
}

application {
  val mainClass: String by project
  mainClassName = mainClass
}

tasks {
  withType<KotlinCompile>().configureEach {
    kotlinOptions {
      freeCompilerArgs += "-Xjsr305=strict"
      jvmTarget = "$javaVersion"
    }
  }
  withType(Wrapper::class.java) {
    val gradleWrapperVersion: String by project
    gradleVersion = gradleWrapperVersion
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
      val mainClass: String by project
      attributes("Main-Class" to mainClass)
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

defaultTasks("clean", "sources", "fatJar", "installDist")
