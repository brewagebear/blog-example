package io.github.brewagebear.common;

import io.confluent.kafka.serializers.AbstractKafkaSchemaSerDeConfig;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import io.confluent.kafka.serializers.subject.RecordNameStrategy;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.LongSerializer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import university.avro.Student;

import java.util.Map;

@Configuration
public class KafkaConfig {
    private final KafkaProperties properties;

    public KafkaConfig(KafkaProperties properties) {
        this.properties = properties;
    }

    private ProducerFactory<Long, Student> studentProducerFactory(KafkaProperties properties) {
        Map<String, Object> studentProducerProps = properties.buildProducerProperties();
        studentProducerProps.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true);
        studentProducerProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class);
        studentProducerProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class);
        studentProducerProps.put(AbstractKafkaSchemaSerDeConfig.VALUE_SUBJECT_NAME_STRATEGY, RecordNameStrategy.class.getName());
        // https://github.com/confluentinc/schema-registry/issues/1352
        studentProducerProps.put(AbstractKafkaSchemaSerDeConfig.AUTO_REGISTER_SCHEMAS, false);
        studentProducerProps.put(AbstractKafkaSchemaSerDeConfig.USE_LATEST_VERSION, true);

        return new DefaultKafkaProducerFactory<>(studentProducerProps);
    }

    @Bean
    public KafkaTemplate<Long, Student> studentKafkaTemplate(KafkaProperties properties) {
        return new KafkaTemplate<>(studentProducerFactory(properties));
    }
}
