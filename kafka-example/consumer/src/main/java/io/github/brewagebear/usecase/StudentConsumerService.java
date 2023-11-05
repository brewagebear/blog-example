package io.github.brewagebear.usecase;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import university.avro.Student;

@Service
public class StudentConsumerService {
    @KafkaListener(topics = "student", groupId = "student-consumer-group-1")
    public void listen(ConsumerRecord<Long, Student> record) {
        Student student = record.value();
        System.out.println("Received Avro message:");
        System.out.println(student.toString());
    }
}
