import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.gradle.api.tasks.testing.logging.TestLogEvent.*

plugins {
  idea
  java
  application
  kotlin("jvm") version "1.3.31"
  kotlin("plugin.spring") version "1.3.31"
  id("io.franzbecker.gradle-lombok") version "3.0.0"
  id("com.github.ben-manes.versions") version "0.21.0"
  id("com.github.johnrengelman.shadow") version "5.0.0"
  // gradle dependencyUpdates -Drevision=release --parallel
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

val weldVersion: String by project
val javaxJson: String by project
val yassonVersion: String by project
val slf4jVersion: String by project
val logbackVersion: String by project
val junit4Version: String by project
val assertkVersion: String by project
val assertjVersion: String by project
val junitJupiterVersion: String by project
val smallryeMessagingVersin: String by project
val smallryeReactiveStreamsVersin: String by project

dependencies {
  implementation(kotlin("stdlib"))
  implementation(kotlin("reflect"))

  implementation("io.smallrye.reactive:smallrye-reactive-streams-operators:$smallryeReactiveStreamsVersin")
  implementation("io.smallrye.reactive:smallrye-reactive-messaging-provider:$smallryeMessagingVersin")
  implementation("io.smallrye.reactive:smallrye-reactive-messaging-kafka:$smallryeMessagingVersin")

  implementation("org.jboss.weld.se:weld-se-core:$weldVersion")
  implementation("org.glassfish:javax.json:$javaxJson")
  implementation("org.eclipse:yasson:$yassonVersion")

  implementation("io.vavr:vavr:0.10.0")
  //implementation("org.slf4j:slf4j-log4j12:4slf4jVersion")
  implementation("ch.qos.logback:logback-classic:$logbackVersion")
  annotationProcessor("org.projectlombok:lombok:$lombokVersion")
  testCompileOnly("org.projectlombok:lombok:$lombokVersion")

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
  withType<Test> {
    useJUnitPlatform()
    testLogging {
      showExceptions = true
      showStandardStreams = true
      events(PASSED, SKIPPED, FAILED)
    }
  }

  named("clean") {
    doLast {
      delete(
          project.buildDir,
          "${project.projectDir}/out"
      )
    }
  }

  /*register("javaDoc", Javadoc::class.java) {
    source = sourceSets["main"].allJava
  }*/

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
    val version = if (project.version == "unspecified") "SNAPSHOT" else project.version
    archiveFileName.set("${project.buildDir}/sources-$version.zip")
  }
}

defaultTasks("clean", "sources", "javadoc", "shadowJar", "installDist", "distZip")
