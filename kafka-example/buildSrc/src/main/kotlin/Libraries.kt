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
    const val gradle_kafka_connect = "com.github.sandonjacobs.gradle-kafka-connect"
    const val gradle_shadow = "com.github.johnrengelman.shadow"

    const val jvm = "jvm"
    const val java_library = "java-library"

    object Versions {
        const val spring_boot = "3.0.4"
        const val spring_dependency_management = "1.1.0"
        const val kotlin = "1.8.10"
        const val gradle_schema_registry = "1.11.1"
        const val gradle_avro = "1.9.1"
        const val gradle_kafka_connect = "0.9.1"
        const val gradle_shadow = "6.1.0"
    }
}


object Dependencies {
    const val spring_boot_starter_web = "org.springframework.boot:spring-boot-starter-web"
    const val spring_boot_starter_kafka = "org.springframework.kafka:spring-kafka"

    const val avro = "org.apache.avro:avro:${Versions.avro_version}"
    const val kafka_avro_serializer = "io.confluent:kafka-avro-serializer:${Versions.kafka_avro_serializer}"
    const val kafka_connect_api = "org.apache.kafka:connect-api:${Versions.kafka_connect_api}"
    const val google_sheet_api = "com.google.apis:google-api-services-sheets:${Versions.google_sheet_api}"
    const val google_api_client = "com.google.api-client:google-api-client:${Versions.google_api_client}"
    const val google_oauth_client = "com.google.auth:google-auth-library-oauth2-http:${Versions.google_oauth_client}"
    const val slf4j = "org.slf4j:slf4j-api:${Versions.slf4j}"
    const val lombok = "org.projectlombok:lombok:${Versions.lombok}"
    const val jackson = "com.fasterxml.jackson.core:jackson-databind:${Versions.jackson}"

    const val junit_bom = "org.junit:junit-bom:${Versions.junit_bom}"
    const val junit_jupiter = "org.junit.jupiter:junit-jupiter"

    object Versions {
        const val kafka_connect_api = "3.6.0"
        const val junit_bom = "5.9.1"
        const val avro_version = "1.11.3"
        const val kafka_avro_serializer = "7.5.1"
        const val google_api_client = "2.2.0"
        const val google_sheet_api = "v4-rev20230815-2.0.0"
        const val google_oauth_client = "1.19.0"
        const val slf4j = "1.7.30"
        const val lombok = "1.18.30"
        const val jackson = "2.15.3"
    }
}
