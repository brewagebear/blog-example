buildscript {
    repositories {
        gradlePluginPortal()
        maven(Repositories.confluent_repo)
        maven(Repositories.third_party_repo)
    }
}

plugins {
    id(Plugins.spring_boot) version Plugins.Versions.spring_boot
    id(Plugins.spring_dependency_management) version Plugins.Versions.spring_dependency_management apply false
    id(Plugins.gradle_schema_registry) version Plugins.Versions.gradle_schema_registry apply false
    id(Plugins.gradle_avro) version  Plugins.Versions.gradle_avro apply false

    kotlin(Plugins.jvm) version Plugins.Versions.kotlin
}

group = "io.github.brewagebear"
version = "1.0-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

tasks.jar {
    enabled = true
}

tasks.bootJar {
    enabled = false
}

allprojects {
    repositories {
        mavenCentral()
        maven(Repositories.confluent_repo)
    }
}

subprojects {
    apply {
        plugin(Plugins.spring_boot)
        plugin(Plugins.spring_dependency_management)
        plugin(Plugins.java_library)
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }

    configurations {
        compileOnly {
            extendsFrom(configurations.annotationProcessor.get())
        }
    }
}

project(":avro") {
    apply {
        plugin(Plugins.gradle_avro)
        plugin(Plugins.gradle_schema_registry)
    }
}

project(":consumer") {
    apply {
        plugin(Plugins.gradle_avro)
        plugin(Plugins.gradle_schema_registry)

    }
}

project(":producer") {
    apply {
        plugin(Plugins.gradle_avro)
        plugin(Plugins.gradle_schema_registry)
    }
}
