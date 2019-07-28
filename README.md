# main-starter [![Build Status](https://travis-ci.org/daggerok/main-starter.svg?branch=spring-boot-scala)](https://travis-ci.org/daggerok/main-starter)
Spring Boot Scala starter using Gradle / Maven build tools to build reactive apps.

## getting started

```bash
git clone --no-single-branch --depth=1 https://github.com/daggerok/main-starter.git -b spring-boot-all
cd main-starter
rm -rf .git
```

## maven

_fat jar_

```bash
cp -Rf src/main/resources/logback-maven.xml src/main/resources/logback.xml
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
cp -Rf src/main/resources/logback-gradle.xml src/main/resources/logback.xml
./gradlew build
java -jar build/libs/*.jar
```

_project sources archive_

to create archive with all project sources use gradle _sources_ task, like so:

```bash
./gradlew sources
unzip -d build/sources build/*.zip
```

## test

```bash
http :8080
```

## spring mvc

```bash
http :8080/api/mvc # or: http :8080
http :8080/api/mvc body="hello mvc"
http :8080/api/mvc/cd8063ca-4ce7-4b34-81e5-21fe3c066e1d
http :8080/api/mvc/tags
```

## webflux router function

```bash
http :8080/api/fn
http :8080/api/fn body="hello function"
http :8080/api/fn/40156273-a6dc-4f6c-a218-1b84d5c30929
```

## router function builder

```bash
http :8080/api/fnb
http :8080/api/fnb body="hello builder"
http :8080/api/fnb/94b6f180-1724-49f9-8b62-8d322f4f8f63
```

NOTE: _This project has been based on [GitHub: daggerok/main-starter (spring-boot-scala branch)](https://github.com/daggerok/main-starter/tree/spring-boot-scala)_

<!--
_update versions_

```bash
./mvnw versions:display-property-updates
./gradlew dependencyUpdates -Drevision=release
```
-->
