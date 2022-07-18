plugins {
    id("java")
    application
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

val jvmArgsList = listOf(
    "-Djavafx.embed.singleThread=true",
    "--add-exports=org.graalvm.truffle/com.oracle.truffle.api=ALL-UNNAMED",
    "--add-exports=org.graalvm.truffle/com.oracle.truffle.api.nodes=ALL-UNNAMED",
    "--add-exports=org.graalvm.truffle/com.oracle.truffle.api.dsl=ALL-UNNAMED"
)

application {
    mainClass.set("truffle.TruffleMain")
}

dependencies {
    implementation("org.graalvm.truffle:truffle-api:22.1.0.1")
    annotationProcessor("org.graalvm.truffle:truffle-dsl-processor:22.1.0.1")
    compileOnly("org.graalvm.truffle:truffle-tck:22.1.0.1")
    implementation("org.graalvm.sdk:graal-sdk:22.1.0.1")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.2")

    implementation("org.antlr:antlr4:4.10.1")
}

tasks.getByName<JavaExec>("run") {
    jvmArgs("-Dgraalvm.locatorDisabled=true")
}

tasks.getByName<Test>("test") {
    jvmArgs("-Dgraalvm.locatorDisabled=true")
    useJUnitPlatform()
}