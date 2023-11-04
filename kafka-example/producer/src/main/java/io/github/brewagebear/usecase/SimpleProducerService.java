package io.github.brewagebear.usecase;

import brewagebear.avro.Student;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class SimpleProducerService {
    private static final String topic = "test";

    private final KafkaTemplate<String, Student> kafkaTemplate;

    public SimpleProducerService(KafkaTemplate<String, Student> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(Student student) {
        this.kafkaTemplate.send(topic, student);
    }
}
