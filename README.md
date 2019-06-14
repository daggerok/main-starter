# main-starter [![Build Status](https://travis-ci.org/daggerok/main-starter.svg?branch=spring-fu-jafu)](https://travis-ci.org/daggerok/main-starter)
Spring Fu JaFu starter using Gradle / Maven build tools.

## getting started

```bash
git clone -b spring-fu-jafu --depth=1 https://github.com/daggerok/main-starter.git
cd main-starter
rm -rf .git
```

## maven

_fat jar_

```bash
./mvnw package
java -jar target/*.jar

http :8080
http :8080/count
http delete :8080
http :8080/count
http :8080 login=ololo firstName=trololo lastName=ohoho
http :8080/ololo
http put :8080/ololo login=trololo firstName=trololo lastName=trololo
http :8080/trololo
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

http :8080
http :8080/count
http delete :8080
http :8080/count
http :8080 login=ololo firstName=trololo lastName=ohoho
http :8080/ololo
http put :8080/ololo login=trololo firstName=trololo lastName=trololo
http :8080/trololo
```

_project sources archive_

to create archive with all project sources use gradle _sources_ task, like so:

```bash
./gradlew sources
unzip -d build/sources build/*.zip
```

## resources

This project was cloned originally from [GitHub: daggerok/spring-fu-jafu-example](https://github.com/daggerok/spring-fu-jafu-example)

NOTE: _This project has been based on [GitHub: daggerok/main-starter](https://github.com/daggerok/main-starter)_

see also [samples](https://github.com/spring-projects/spring-fu/tree/master/samples)
