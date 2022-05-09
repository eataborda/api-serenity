[![Gradle](https://img.shields.io/badge/Gradle-7.4.2-blue)](https://gradle.org/releases/)
[![Gradle Plugin Portal](https://img.shields.io/badge/serenity.gralde.plugin-3.2.4-blue)](https://plugins.gradle.org/plugin/net.serenity-bdd.serenity-gradle-plugin/3.2.4)

# Basic API automation 
A basic API automation test using Java + Junit + Serenity + Gradle
based on the Restful-booker service that introduce intentionally errors
on the behavior of the service for practice purposes

Contains:
- Basic build.gradle config
- Basic API automation with general validations made for the different request methods: POST, GET, PUT, PATCH, DELETE

## Use sample project locally
- Verify that you have `Git`
- Verify that you have `Java` installed, also that you already setup the following environment variables: `$PATH` and `$JAVA_HOME`
- Clone the repository and move inside that path:
```shellscript
$ gh repo clone eataborda/serenity-gradle-java-junit
$ cd ./serenity-gradle-java-junit
```

## Run automation
- Run all tests:
```
$ ./gradlew
```
- Run specific tests:
```
$ ./gradlew tags
```