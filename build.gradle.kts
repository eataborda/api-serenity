plugins {
    id("net.serenity-bdd.serenity-gradle-plugin") version "4.1.14"
    `java-library`
    eclipse
    idea
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(22))
    }
}

repositories {
    mavenCentral()
}

defaultTasks ("clean", "test", "aggregate")

tasks.clean {
    delete("target")
}

tasks.test {
    useJUnit()
    testLogging.showStandardStreams = true
    ignoreFailures = true
    dependsOn(tasks.clean)
    finalizedBy(tasks.aggregate)
}

tasks.withType<Test> {
    systemProperty("tags", System.getProperty("tags"))
    systemProperty("USER", System.getProperty("USER"))
    systemProperty("PASSWORD", System.getProperty("PASSWORD"))
    systemProperty("AUTHORIZATION", System.getProperty("AUTHORIZATION"))
}

dependencies {
    testImplementation("net.serenity-bdd:serenity-core:4.1.14")
    testImplementation("net.serenity-bdd:serenity-junit:4.1.14")
    testImplementation("net.serenity-bdd:serenity-rest-assured:4.1.14")
    testImplementation("junit:junit:4.13.2")
    testImplementation("org.assertj:assertj-core:3.26.3")
    testImplementation("org.slf4j:slf4j-simple:2.0.16")
}
