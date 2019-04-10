# main-starter
JVM (java / kotlin) starter using Gradle / Maven build tools.

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
JVM (java / kotlin) starter using Gradle / Maven build tools.

## gradle

_fat jar_

```bash
./gradlew build
java -jar build/libs/*-all.jar
```

_installDist_

```bash
./gradlew installDist
bash ./build/install/*/bin/*
```

_project sources archive_

to create archive with all project sources use gradle _sources_ task, like so: 

```bash
./gradlew sources
unzip -d build/sources build/*.zip
```

NOTE: _This project has been based on [GitHub: daggerok/main-starter](https://github.com/daggerok/main-starter)_
