# main-starter
Java starter using Gradle / Maven build tools.

## getting started

```bash
git clone https://github.com/daggerok/main-starter
cd main-starter
rm -rf .git
# ...
```

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
