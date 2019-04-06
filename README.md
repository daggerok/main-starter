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
