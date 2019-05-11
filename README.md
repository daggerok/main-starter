# main-starter
Java Akka starter using Gradle / Maven build tools.

## getting started

```bash
git clone -b akka --depth=1 https://github.com/daggerok/main-starter.git
cd main-starter
rm -rf .git

gradle :wrapper
mvn -N io.takari:maven:wrapper -Dmaven=3.6.0
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
./gradlew build
java -jar build/libs/*-all.jar
```

_project sources archive_

to create archive with all project sources use gradle _sources_ task, like so:

```bash
./gradlew sources
unzip -d build/sources build/*.zip
```

NOTE: _This project has been based on [GitHub: daggerok/main-starter](https://github.com/daggerok/main-starter)_

_resources_

* [Akka tutorial](https://doc.akka.io/docs/akka/current/guide/tutorial_1.html)
* [Akka colored logging: SLF4J + logback](https://doc.akka.io/docs/akka/current/logging.html)
