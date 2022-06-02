package io.github.brewagebear.stock.adapter.messaging.impl;

import static io.github.brewagebear.stock.constants.KafkaConstant.TOPIC_NAME;

import io.github.brewagebear.stock.dto.StockAdjustment;
import io.github.brewagebear.stock.service.StockService;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class StockAdjustmentKafkaConsumerService {

    private final StockService stockService;

    public StockAdjustmentKafkaConsumerService(StockService stockService) {
        this.stockService = stockService;
    }

    @KafkaListener(topics = TOPIC_NAME, containerFactory = "kafkaListenerContainerFactory", groupId = "stock-adjustment-0")
    public void subscribe(final String message, Acknowledgment ack) throws IOException {

        log.info(String.format("Message Received : [%s]", message));

        StockAdjustment stockAdjustment = StockAdjustment.deserializeJSON(message);

        if(stockService.isAlreadyProcessedOrderId(stockAdjustment.getOrderId())) {
            log.info(String.format("AlreadyProcessedOrderId : [%s]", stockAdjustment.getOrderId()));
        } else {
            if(stockAdjustment.getAdjustmentType().equals("REDUCE")) {
                stockService.decreaseStock(stockAdjustment);
            }
        }
        ack.acknowledge();
    }
}
