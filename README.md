# main-starter [![Build Status](https://travis-ci.org/daggerok/main-starter.svg?branch=master)](https://travis-ci.org/daggerok/main-starter)
JVM (java / kotlin) starter using Gradle / Maven build tools.

## getting started

```bash
git clone --no-single-branch --depth=1 https://github.com/daggerok/main-starter.git
cd main-starter

git co gradle-kotlin
git co maven-kotlin
git co gradle
git co maven
git co all

rm -rf .git
# ...
```

## other available branches

```bash
git clone -b $branch --depth=1 https://github.com/daggerok/main-starter.git
cd main-starter
rm -rf .git
```

* [akka](https://github.com/daggerok/main-starter/tree/akka/)
* [akka-http](https://github.com/daggerok/main-starter/tree/akka-http/)
* [akka-scala](https://github.com/daggerok/main-starter/tree/akka-scala/)
* [spring](https://github.com/daggerok/main-starter/tree/spring/)
* [spring-all](https://github.com/daggerok/main-starter/tree/spring-all/)
* [spring-boot-all](https://github.com/daggerok/main-starter/tree/spring-boot-all/)
* [spring-boot](https://github.com/daggerok/main-starter/tree/spring-boot/)
* [spring-fu-jafu](https://github.com/daggerok/main-starter/tree/spring-fu-jafu/)
* [cdi-all](https://github.com/daggerok/main-starter/tree/cdi-all/)
* [cdi](https://github.com/daggerok/main-starter/tree/cdi/)
* [all](https://github.com/daggerok/main-starter/tree/all/)
* [gradle-kotlin](https://github.com/daggerok/main-starter/tree/gradle-kotlin/)
* [gradle](https://github.com/daggerok/main-starter/tree/gradle/)
* [maven-kotlin](https://github.com/daggerok/main-starter/tree/maven-kotlin/)
* [maven](https://github.com/daggerok/main-starter/tree/maven/)
* [mp-smallrye-gradle](https://github.com/daggerok/main-starter/tree/mp-smallrye-gradle/)
* [mp-smallrye-maven](https://github.com/daggerok/main-starter/tree/mp-smallrye-maven/)
* [quarkus](https://github.com/daggerok/main-starter/tree/quarkus/)

TODO / FIXME:
* fix and merge together mp-smallrye [maven](https://github.com/daggerok/main-starter/tree/mp-smallrye-maven/) and [gradle](https://github.com/daggerok/main-starter/tree/mp-smallrye-gradle/) branches

NOTE: _This project has been based on [GitHub: daggerok/main-starter](https://github.com/daggerok/main-starter)_

<!--
_update versions_

```bash
./mvnw versions:display-plugin-updates
./gradlew dependencyUpdates -Drevision=release
```
-->
