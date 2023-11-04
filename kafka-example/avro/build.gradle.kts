description = "AVRO Schema Module"

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
            srcDir("src/main/java/io/github/brewagebear/schemas")
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
            "student",
            "avro/src/main/java/io/github/brewagebear/schemas/avro/student.avsc",
            "AVRO"
    )

    config {
        subject("student", "FULL")
    }
}
