package io.github.brewagebear.stock.service.impl;

import static io.github.brewagebear.stock.constants.KafkaConstant.TOPIC_NAME;

import io.github.brewagebear.stock.dto.StockAdjustment;
import io.github.brewagebear.stock.service.StockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class StockKafkaFacade {

    private final StockService stockService;

    public StockKafkaFacade(StockService stockService) {
        this.stockService = stockService;
    }

    @KafkaListener(topics = TOPIC_NAME)
    public void subscribe(final String message, Acknowledgment ack) {
        log.info(String.format("Message Received : [%s]", message));
        try {
            StockAdjustment stockAdjustment = StockAdjustment.deserializeJSON(message);

            if(stockService.isAlreadyProcessedOrderId(stockAdjustment.getOrderId())) {
                log.info(String.format("AlreadyProcessedOrderId : [%s]", stockAdjustment.getOrderId()));
            } else {
                if(stockAdjustment.getAdjustmentType().equals("REDUCE")) {
                    stockService.decreaseStock(stockAdjustment);
                }
            }
            // Kafka Offset Manual Commit
            ack.acknowledge();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
