# main-starter
Example of java smallrye micro-profile example using Gradle.

_getting started_

```bash
git clone -b mp-smallrye-gradle --depth=1 https://github.com/daggerok/main-starter.git
cd main-starter
rm -rf .git
gradle :wrapper
```

_build fat jar_

```bash
gradle shadowJar
java -jar build/libs/*-all.jar

# or
java -cp build/libs/*-all.jar org.jboss.weld.environment.se.StartMain
```

_debug fat jar_

```bash
java -Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=5005 -jar build/libs/*-all.jar
```

_NOTE_

Use `App.java` class for debug from IDE.

_links_

[YouTube: Using Java EE 8 Dependency Injection in Java SE](https://www.youtube.com/watch?v=lyuU24ZFlY4)
[YouTube: Making the most of Java SE with CDI 2.0 by John Ament](https://www.youtube.com/watch?v=mXoH4DEIcLo)
[YouTube: Combining Serverless Functions with CDI](https://www.youtube.com/watch?v=KzdD5AmQGmk)

NOTE: _This project has been based on [GitHub: daggerok/main-starter](https://github.com/daggerok/main-starter)_
