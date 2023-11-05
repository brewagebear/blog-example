description = "AVRO Schema Module"

val avro_soruce_base_path = "src/main/java/io/github/brewagebear/schemas/avro"
val avro_source_download_path = "${avro_soruce_base_path}/download"

dependencies {
    implementation(Dependencies.avro)
}

tasks.jar {
    enabled = true
}

tasks.bootJar {
    enabled = false
}

sourceSets {
    main {
        resources {
            srcDir(avro_soruce_base_path)
        }
    }
}

avro {
    isCreateSetters.set(false)
    outputCharacterEncoding.set("UTF-8")
    stringType.set("String")
}

schemaRegistry {
    url.set("http://localhost:8085")
    quiet.set(false)

    register.subject(
            "university.avro.Student",
            "avro/${avro_soruce_base_path}/student.avsc",
            "AVRO")

    register.subject(
            "university.avro.Professor",
            "avro/${avro_soruce_base_path}/professor.avsc",
            "AVRO")

    config {
        subject("student", "BACKWARD")
        subject("professor", "FORWARD")
    }
}
