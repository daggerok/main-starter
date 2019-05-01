# main-starter
Example of java smallrye micro-profile example using Maven.

_getting started_

```bash
git clone -b mp-smallrye-maven --depth=1 https://github.com/daggerok/main-starter.git
cd main-starter
rm -rf .git
mvn -N io.takari:maven:wrapper -Dmaven=3.6.0
```

_build fat jar_

```bash
mvn package
java -jar target/*-all.jar
# or
java -cp target/*-all.jar org.jboss.weld.environment.se.StartMain
```

_run with maven_

```bash
mvn clean compile exec:java -Dexec.mainClass=org.jboss.weld.environment.se.StartMain
```

_debug with maven_

```bash
mvn clean compile exec:exec -Dexec.executable="java" \
  -Dexec.args="-classpath %classpath -Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=5005 org.jboss.weld.environment.se.StartMain"
```

_debug fat jar_

```bash
java -cp target/*-all.jar -Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=5005 org.jboss.weld.environment.se.StartMain
```

_NOTE_

Use `App.java` class for debug from IDE.

_links_

[YouTube: Using Java EE 8 Dependency Injection in Java SE](https://www.youtube.com/watch?v=lyuU24ZFlY4)
[YouTube: Making the most of Java SE with CDI 2.0 by John Ament](https://www.youtube.com/watch?v=mXoH4DEIcLo)
[YouTube: Combining Serverless Functions with CDI](https://www.youtube.com/watch?v=KzdD5AmQGmk)

NOTE: _This project has been based on [GitHub: daggerok/main-starter](https://github.com/daggerok/main-starter)_
