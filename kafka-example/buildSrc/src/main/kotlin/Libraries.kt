object Configurations {
    const val additional_schema = "additionalSchema"
}

object Repositories {
    const val confluent_repo = "https://packages.confluent.io/maven/"
    const val third_party_repo = "https://jitpack.io"
}

object Plugins {
    const val spring_boot = "org.springframework.boot"
    const val spring_dependency_management = "io.spring.dependency-management"
    const val gradle_schema_registry = "com.github.imflog.kafka-schema-registry-gradle-plugin"
    const val gradle_avro = "com.github.davidmc24.gradle.plugin.avro"

    const val jvm = "jvm"
    const val java_library = "java-library"

    object Versions {
        const val spring_boot = "3.0.4"
        const val spring_dependency_management = "1.1.0"
        const val kotlin = "1.8.10"
        const val gradle_schema_registry = "1.11.1"
        const val gradle_avro = "1.9.1"
    }
}


object Dependencies {
    const val spring_boot_starter_web = "org.springframework.boot:spring-boot-starter-web"
    const val spring_boot_starter_kafka = "org.springframework.kafka:spring-kafka"

    const val avro = "org.apache.avro:avro:${Versions.avro_version}"
    const val kafka_avro_serializer = "io.confluent:kafka-avro-serializer:${Versions.kafka_avro_serializer}"

    const val junit_bom = "org.junit:junit-bom:${Versions.junit_bom}"
    const val junit_jupiter = "org.junit.jupiter:junit-jupiter"

    object Versions {
        const val junit_bom = "5.9.1"
        const val avro_version = "1.11.3"
        const val kafka_avro_serializer = "7.5.1"
    }
}
