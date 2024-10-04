plugins {
    kotlin("jvm") version "2.0.10"
}

group = "dev.joseluisgs"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // Para Kotlin
    //https://github.com/michaelbull/kotlin-result
    implementation("com.michael-bull.kotlin-result:kotlin-result:2.0.0")
    // Para java
    implementation("io.vavr:vavr:0.10.4")

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}