import com.github.davidmc24.gradle.plugin.avro.GenerateAvroJavaTask

description = "Producer Module"

configurations {
    create(Configurations.additional_schema)
}

dependencies {
    implementation(Dependencies.spring_boot_starter_web)
    implementation(Dependencies.spring_boot_starter_kafka)
    implementation(Dependencies.kafka_avro_serializer)
    implementation(Dependencies.avro)

    Configurations.additional_schema(project(":avro"))
}

tasks.named<GenerateAvroJavaTask>("generateAvroJava") {
    val config = configurations[Configurations.additional_schema]

    dependsOn(config)
    config.forEach {
        schemaJar -> source(zipTree(schemaJar))
    }
}

