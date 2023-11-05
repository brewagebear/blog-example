package io.github.brewagebear.usecase;


import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import university.avro.Student;

@Service
public class StudentProducerService {
    private static final String topic = "student";

    private final KafkaTemplate<Long, Student> kafkaTemplate;

    public StudentProducerService(KafkaTemplate<Long, Student> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(Student student) {
        this.kafkaTemplate.send(topic, student);
    }
}
