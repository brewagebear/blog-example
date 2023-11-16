description = "Kafka Connector Module"

plugins {
    id(Plugins.gradle_shadow) version Plugins.Versions.gradle_shadow
}

dependencies {
    implementation(Dependencies.kafka_connect_api)
    implementation(Dependencies.google_api_client)
    implementation(Dependencies.google_sheet_api)
    implementation(Dependencies.google_oauth_client)
    implementation(Dependencies.jackson)

    implementation(Dependencies.slf4j)

    compileOnly(Dependencies.lombok)
    annotationProcessor(Dependencies.lombok)
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
