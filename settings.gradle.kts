rootProject.name = "kafka-spring"

buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath("com.avast.gradle:gradle-docker-compose-plugin:0.16.9")
    }
}

