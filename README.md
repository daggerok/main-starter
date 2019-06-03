# main-starter
Scala starter using Gradle / Maven build tools.

## getting started

```bash
git clone -b scala --depth=1 https://github.com/daggerok/main-starter.git
cd main-starter
rm -rf .git
```

## maven

_fat jar_

```bash
./mvnw package
java -jar target/*-all.jar
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
./gradlew
java -jar build/libs/*-all.jar
# or: bash build/install/*/bin/*
```

_project sources archive_

to create archive with all project sources use gradle _sources_ task, like so:

```bash
./gradlew sources
unzip -d build/sources build/*.zip
```

NOTE: _This project has been based on [GitHub: daggerok/main-starter](https://github.com/daggerok/main-starter)_

_resources_

* [Functional Programming Principles in Scala](https://www.youtube.com/channel/UC606CODOUaA3-E5LcC5yKAQ)
* [Principles of Reactive Programming in Scala](https://www.youtube.com/playlist?list=PLMhMDErmC1TdBMxd3KnRfYiBV2ELvLyxN)

<!--
_update versions_

```bash
./mvnw versions:display-property-updates
./gradlew dependencyUpdates -Drevision=release
```
-->
