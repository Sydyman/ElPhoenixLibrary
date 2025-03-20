plugins {
    kotlin("jvm") version "1.9.0"
    `maven-publish`
}

group = "com.github.Sydyman"
version = "1.0.0"

repositories {
    mavenCentral()
}
kotlin {
    jvmToolchain(11)  // Указываем JVM 11
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
        }
    }
}



