# S2Direcotry development information

You need JDK 1.5.x, Maven 2.2.x, and Ant 1.8.x.

## Test
 
```
set JAVA_HOME=C:\Software\Java\jdk-1.5
C:\Software\Java\apache-maven-2.2.1\bin\mvn test
```

## Build

```
set JAVA_HOME=C:\Software\Java\jdk-1.5
C:\Software\Java\apache-maven-2.2.1\bin\mvn -Duser.name="The Seasar Foundation" clean install
```

## Update pom version

```
# Edit version in ..\build.xml (s2-directory-project\build.xml)
ant update-pom
```

## Release

* Release file: target/s2-directory-x.y.z.zip

```
set JAVA_HOME=C:\Software\Java\jdk-1.5
C:\Software\Java\apache-maven-2.2.1\bin\mvn -Duser.name="The Seasar Foundation" clean package
ant dist
```

## Deploy to Maven repository

```
set JAVA_HOME=C:\Software\Java\jdk-1.5
C:\Software\Java\apache-maven-2.2.1\bin\mvn -Duser.name="The Seasar Foundation" deploy
```
