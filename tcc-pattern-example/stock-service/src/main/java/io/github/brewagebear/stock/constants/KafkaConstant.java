package io.github.brewagebear.stock.constants;

import java.util.function.BiFunction;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.TopicPartition;

@Slf4j
public final class KafkaConstant {

    private KafkaConstant() {
    }

    public static final String TOPIC_NAME = "stock-adjustment";

    public static final BiFunction<ConsumerRecord<?, ?>, Exception, TopicPartition>
        DEAD_TOPIC_DESTINATION_RESOLVER = (cr, e) -> {
            log.error("[Send to dead letter topic]: {} - [Exception message] : {}" , cr.topic(), e);
            return new TopicPartition("dlt-" + cr.topic(), cr.partition());
        };
}
