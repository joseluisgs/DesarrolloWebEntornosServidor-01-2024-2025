plugins {
    id("java")
    // Lombok
    id("io.freefair.lombok") version "8.6"

}

group = "dev.joseluisgs"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // Logger
    implementation("ch.qos.logback:logback-classic:1.4.14")
    implementation("org.slf4j:slf4j-simple:1.7.32")

    // Lombok
    implementation("org.projectlombok:lombok:1.18.28") // usa la última versión de Lombok
    annotationProcessor("org.projectlombok:lombok:1.18.28")
    testAnnotationProcessor("org.projectlombok:lombok:1.18.28")

    // Vavr para programación funcional
    implementation("io.vavr:vavr:0.10.4")

    // PostgreSQL
    implementation("org.postgresql:postgresql:42.7.4")


    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    // Mockito para nuestros test con JUnit 5
    testImplementation("org.mockito:mockito-junit-jupiter:5.12.0")
    testImplementation("org.mockito:mockito-core:5.12.0")

    // Test Containers
    // Testcontainers
    testImplementation("org.testcontainers:testcontainers:1.20.2")
    testImplementation("org.testcontainers:junit-jupiter:1.20.2")
    testImplementation("org.testcontainers:postgresql:1.20.2")

}

tasks.test {
    useJUnitPlatform()
}

// Hacer un Jar ejecutable
tasks.jar {
    manifest {
        // Clase principal
        attributes["Main-Class"] = "dev.joseluisgs.Main"
    }
    // Incluir dependencias
    from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
    // Excluir duplicados
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}