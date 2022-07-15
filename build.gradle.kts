plugins {
    kotlin("jvm") version "1.6.20"
    java
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.antlr:antlr4:4.10.1")

    implementation("org.graalvm.truffle:truffle-api:22.1.0.1")
    annotationProcessor("org.graalvm.truffle:truffle-dsl-processor:22.1.0.1")

    implementation(kotlin("stdlib"))

    testImplementation(platform("org.junit:junit-bom:5.8.2"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}