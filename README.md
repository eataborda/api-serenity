[![automation-api-test-run](https://github.com/eataborda/serenity-gradle-java-junit/actions/workflows/automation-api-test-run.yml/badge.svg)](https://github.com/eataborda/serenity-gradle-java-junit/actions/workflows/automation-api-test-run.yml)
[![Gradle](https://img.shields.io/badge/Gradle-7.4.2-blue)](https://gradle.org/releases/)
[![Gradle Plugin Portal](https://img.shields.io/badge/serenity.gralde.plugin-3.2.4-blue)](https://plugins.gradle.org/plugin/net.serenity-bdd.serenity-gradle-plugin/3.2.4)

# Basic API automation 
A basic API automation test using Java + Junit + Serenity + Gradle
based on the Restful-booker service that introduce intentionally errors
on the behavior of the service for practice purposes

Contains:
- Basic build.gradle config
- Basic API automation with general validations for the following request methods: POST, GET, PUT, PATCH, DELETE

## Use sample project locally
- Verify that you have `Git`
- Verify that you have `Java` installed, also that you already setup the following environment variables: `$PATH` and `$JAVA_HOME`
- Clone the repository and move inside that path:
```shellscript
$ gh repo clone eataborda/serenity-gradle-java-junit
$ cd ./serenity-gradle-java-junit
```

## Run automation
- Run all tests on the src:
```
$ ./gradlew
```
- Run all tests inside class using junit tags (@WithTagValuesOf):
```
$ ./gradlew -Dtags="regression"
```
- Run an specific test method (@Test) inside class using junit tags (@WithTag):
```
$ ./gradlew -Dtags="status_code:200"
```
In this way you can use the following tags depending on the tests you need to run:

smoke, workflow, delete_method, get_method, patch_method, post_method, put_method, health_check,
status_code:all, status_code:200, status_code:201, status_code:400, status_code:403, status_code:404,
status_code:405, status_code:500