plugins {
    id("net.serenity-bdd.serenity-gradle-plugin") version "3.2.5"
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

defaultTasks ("clean", "test", "aggregate")

dependencies {
    testImplementation("net.serenity-bdd:serenity-core:3.2.5")
    testImplementation("net.serenity-bdd:serenity-junit:3.2.5")
    testImplementation("net.serenity-bdd:serenity-rest-assured:3.2.5")
    testImplementation("junit:junit:4.13.1")
    testImplementation("org.assertj:assertj-core:3.22.0")
    testImplementation("org.slf4j:slf4j-simple:1.7.36")
}
