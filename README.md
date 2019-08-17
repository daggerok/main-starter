# main-starter [![Build Status](https://travis-ci.org/daggerok/main-starter.svg?branch=maven)](https://travis-ci.org/daggerok/main-starter)
Java maven starter using Weld SE CDI.

## getting started

```bash
git clone -b maven --depth=1 https://github.com/daggerok/main-starter.git
```

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

NOTE: _This project has been based on [GitHub: daggerok/main-starter](https://github.com/daggerok/main-starter/tree/maven)_
