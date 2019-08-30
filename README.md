# main-starter [![Build Status](https://travis-ci.org/daggerok/main-starter.svg?branch=quarkus-gradle)](https://travis-ci.org/daggerok/main-starter)
Quarkus micro-profile starter using Gradle build tool.

NOTE: tests are works if quarkus gradle plugin is declared with old way!

see:

* [that issue](https://github.com/quarkusio/quarkus/issues/3552#issuecomment-524225607)
* [and that issue](https://github.com/quarkusio/quarkus-quickstarts/issues/258)

_getting started_

```bash
git clone -b quarkus-gradle --depth=1 https://github.com/daggerok/main-starter.git
cd main-starter
rm -rf .git
```

_dev mode_

```bash
./gradlew quarkusDev
http :8080/api/v1/hello
```

_thin jar build_

```bash
./gradlew clean quarkusBuild
java -cp build/lib -jar build/*-runner.jar
http :8080/api/v1/hello/max
```

_fat (uber) jar build_

```bash
./gradlew clean quarkusBuild --uber-jar # ./gradlew quarkusBuild --uber-jar --ignored-entry=META-INF/file1.txt
java -jar build/*-runner.jar
http :8080/api/v1/hello/max
```

_docker native_

```bash
./gradlew buildNative --docker-build=true
docker build -f src/main/docker/Dockerfile.native -t quarkus/quarkus-example-native .
docker run -i --rm --name app -p 8080:8080 quarkus/quarkus-example-native
http :8080/api/v1/hello/native
docker rm -f -v app
```

_docker compose_

```bash
./gradlew composeUp
http :8080/api/v1/hello/compose
./gradlew composeUp
```

NOTE: _This project has been based on [GitHub: daggerok/main-starter (quarkus-gradle)](https://github.com/daggerok/main-starter/tree/quarkus-gradle)_
