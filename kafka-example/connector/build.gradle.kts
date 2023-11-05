import com.github.davidmc24.gradle.plugin.avro.GenerateAvroJavaTask

description = "Kafka Connector Module"

plugins {
    id("com.github.johnrengelman.shadow") version "6.1.0"
}

dependencies {
    implementation(Dependencies.kafka_connect_api)
    implementation(Dependencies.google_api_client)
    implementation(Dependencies.google_sheet_api)
    implementation(Dependencies.google_oauth_client)

    implementation("org.slf4j:slf4j-api:1.7.30")

    compileOnly("org.projectlombok:lombok:1.18.30")
    annotationProcessor("org.projectlombok:lombok:1.18.30")
}

tasks.test {
    useJUnitPlatform()
}

java {
    toolchain {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}
