# main-starter [![Build Status](https://travis-ci.org/daggerok/main-starter.svg?branch=spring-boot-all)](https://travis-ci.org/daggerok/main-starter)
Spring Boot Java starter using Gradle / Maven build tools.

## getting started

```bash
git clone --no-single-branch --depth=1 https://github.com/daggerok/main-starter.git -b spring-boot-all
cd main-starter
rm -rf .git
```

## maven

_fat jar_

```bash
./mvnw package
java -jar target/*.jar
```

_project sources archive_

find archive with all project sources in target folder too:

```bash
./mvnw assembly:single -Dassembly.ignoreMissingDescriptor
unzip -d target/sources target/*-sources.zip
unzip -d target/default target/*-src.zip
```

## gradle

_fat jar_

```bash
./gradlew build
java -jar build/libs/*.jar
```

_project sources archive_

to create archive with all project sources use gradle _sources_ task, like so:

```bash
./gradlew sources
unzip -d build/sources build/*.zip
```

NOTE: _This project has been based on [GitHub: daggerok/main-starter](https://github.com/daggerok/main-starter)_

<!--
_update versions_

```bash
./mvnw versions:display-property-updates
./gradlew dependencyUpdates -Drevision=release
```
-->
