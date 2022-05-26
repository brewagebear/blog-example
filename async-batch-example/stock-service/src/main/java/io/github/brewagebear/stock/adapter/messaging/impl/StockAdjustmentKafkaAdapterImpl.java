package io.github.brewagebear.stock.adapter.messaging.impl;

import static io.github.brewagebear.stock.constants.KafkaConstant.TOPIC_NAME;

import io.github.brewagebear.stock.adapter.messaging.StockAdjustmentChannelAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class StockAdjustmentKafkaAdapterImpl implements StockAdjustmentChannelAdapter {

    private final KafkaTemplate<Long, String> kafkaTemplate;

    public StockAdjustmentKafkaAdapterImpl(KafkaTemplate<Long, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void publish(String message) {
        this.kafkaTemplate.send(TOPIC_NAME, message);
    }
}
