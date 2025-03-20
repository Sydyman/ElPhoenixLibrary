plugins {
    kotlin("jvm") version "1.9.0"
    `maven-publish`
}

group = "com.github.Sydyman"
version = "1.0.0"

repositories {
    mavenCentral()
}

tasks.register("publish") {
    doLast {

        println("JitPack...")
    }
}
