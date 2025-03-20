plugins {
    kotlin("jvm") version "1.9.0"
    `maven-publish`
}

group = "com.github.Sydyman"
version = "1.0.0"

repositories {
    mavenCentral()
}

publishing {
    publications {
        create<MavenPublication>("release") {
            groupId = "com.github.Sydyman"
            artifactId = "ElPhoenixLibrary"
            version = "1.0.0"

            afterEvaluate {
                from(components["kotlin"])
            }
        }
    }

    repositories {
        maven {
            url = uri("https://jitpack.io")
        }
    }
}
