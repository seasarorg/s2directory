# S2Direcotry development information

You need JDK 1.5.x, Maven 2.2.x, and Ant 1.8.x.

## Test
 
```
set JAVA_HOME=C:\Software\Java\jdk-1.5
C:\Software\Java\apache-maven-2.2.1-bin\bin\mvn test
```

## Build

```
set JAVA_HOME=C:\Software\Java\jdk-1.5
C:\Software\Java\apache-maven-2.2.1-bin\bin\mvn -Duser.name="The Seasar Foundation" clean install
```

## Deploy to Maven repository

```
set JAVA_HOME=C:\Software\Java\jdk-1.5
C:\Software\Java\apache-maven-2.2.1-bin\bin\mvn -Duser.name="The Seasar Foundation" clean deploy
```

## Update pom

```
# Edit version in ..\build.xml (s2-directory-project\build.xml)
ant dist
```

## Release

* Release file: target/s2-directory-X.Y.Z.zip

```
set JAVA_HOME=C:\Software\Java\jdk-1.5
C:\Software\Java\apache-maven-2.2.1-bin\bin\mvn -Duser.name="The Seasar Foundation" clean package
ant dist
```
