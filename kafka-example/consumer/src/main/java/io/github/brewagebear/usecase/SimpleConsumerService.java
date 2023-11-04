package io.github.brewagebear.usecase;

import brewagebear.avro.Student;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class SimpleConsumerService {
    @KafkaListener(topics = "test", groupId = "test-consumer-group-1")
    public void listen(ConsumerRecord<Long, Student> record) {
        Student student = record.value();
        System.out.println("Received Avro message:");
        System.out.println(student.toString());
        // 여기에서 비즈니스 로직을 수행합니다.
    }
}
