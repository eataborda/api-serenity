plugins {
    id("net.serenity-bdd.serenity-gradle-plugin") version "3.9.8"
    `java-library`
    eclipse
    idea
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
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
    testImplementation("net.serenity-bdd:serenity-core:3.9.8")
    testImplementation("net.serenity-bdd:serenity-junit:3.9.8")
    testImplementation("net.serenity-bdd:serenity-rest-assured:3.9.8")
    testImplementation("junit:junit:4.13.2")
    testImplementation("org.assertj:assertj-core:3.24.2")
    testImplementation("org.slf4j:slf4j-simple:2.0.5")
}
