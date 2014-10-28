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
# Edit ..\s2directory/pom.xml
# Edit ..\s2directory-tiger/build.xml
# Edit ..\s2directory-tigerpom.xml
# Edit version in ..\build.xml (s2-directory-project\build.xml)
ant update-pom
```

## Deploy to Maven repository

```
set JAVA_HOME=C:\Software\Java\jdk-1.5
C:\Software\Java\apache-maven-2.2.1\bin\mvn -Duser.name="The Seasar Foundation" clean deploy
```

## Release

* Release file: target/s2-directory-x.y.z.zip

```
set JAVA_HOME=C:\Software\Java\jdk-1.5
C:\Software\Java\apache-maven-2.2.1\bin\mvn -Duser.name="The Seasar Foundation" package
ant dist
```
