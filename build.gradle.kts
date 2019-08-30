import com.avast.gradle.dockercompose.RemoveImages
import org.gradle.api.tasks.testing.logging.TestLogEvent

buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath("io.quarkus:quarkus-gradle-plugin:0.21.1")
    }
}

plugins {
    idea
    java
    id("com.github.ben-manes.versions") version "0.23.0"
    id("com.avast.gradle.docker-compose") version "0.9.4"
    // // for some reasons, test wont work with new style quarkus plugin declaration:
    // https://github.com/quarkusio/quarkus/issues/3552#issuecomment-524225607
    // id("io.quarkus") version "0.21.1"
}

apply(plugin = "io.quarkus")

allprojects {
    version = "1.0.1-SNAPSHOT"
    group = "com.github.daggerok"
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    // targetCompatibility = JavaVersion.VERSION_1_8
}

repositories {
    mavenCentral()
    //// not needed, all dependencies should be in central
    //maven(url = uri("https://repository.jboss.org/nexus/content/groups/public"))
}

dependencies {
    implementation(enforcedPlatform("io.quarkus:quarkus-bom:0.21.1"))
    implementation("io.quarkus:quarkus-resteasy")
    implementation("io.quarkus:quarkus-jsonp")
    implementation("io.quarkus:quarkus-jsonb")
    implementation("io.quarkus:quarkus-smallrye-health")
    implementation("io.quarkus:quarkus-smallrye-metrics")
    implementation("io.quarkus:quarkus-smallrye-openapi")
    testImplementation("io.quarkus:quarkus-junit5")
    testImplementation("io.rest-assured:rest-assured")
}

tasks {
    test {
        testLogging {
            showExceptions = true
            showStandardStreams = true
            events(TestLogEvent.PASSED, TestLogEvent.SKIPPED, TestLogEvent.FAILED)
        }
        useJUnitPlatform()
        // systemProperty("quarkus.test.profile", "foo")
    }

    withType(Wrapper::class.java) {
        gradleVersion = "5.6.1"
        distributionType = Wrapper.DistributionType.BIN
    }
}

defaultTasks("clean", "build")

dockerCompose {
    useComposeFiles = listOf("src/main/docker/docker-compose.yaml")
    projectName = "quarkus"
    removeImages = RemoveImages.Local
    isRemoveContainers = true
    isRemoveOrphans = true
    isRemoveVolumes = true
    isForceRecreate = true
    isBuildBeforeUp = true
    isIgnorePushFailure = true
}

tasks["composeUp"].dependsOn("quarkusBuild")
