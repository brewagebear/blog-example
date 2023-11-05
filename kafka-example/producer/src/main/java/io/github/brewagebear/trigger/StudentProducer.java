package io.github.brewagebear.trigger;


import io.github.brewagebear.usecase.StudentProducerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import university.avro.Student;

@RestController
public class StudentProducer {
    private final StudentProducerService studentProducerService;

    public StudentProducer(StudentProducerService studentProducerService) {
        this.studentProducerService = studentProducerService;
    }

    @GetMapping("/backward")
    public String send() {
        Student student = Student.newBuilder()
                .setName("bear")
                .build();

        studentProducerService.sendMessage(student);
        return "Avro Message sent to the Kafka Topic successfully";
    }
}
