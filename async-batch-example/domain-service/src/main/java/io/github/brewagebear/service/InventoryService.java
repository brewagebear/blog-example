package io.github.brewagebear.service;

import io.github.brewagebear.domain.OrderProduct;
import io.github.brewagebear.dto.OrderResponse;
import io.github.brewagebear.repository.OrderProductRepository;
import java.util.List;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class InventoryService {

    private final OrderProductRepository orderProductRepository;

    private final KafkaTemplate<String, Object> inventoriesTopicMessage;

    public InventoryService(OrderProductRepository orderProductRepository, KafkaTemplate<String, Object> inventoriesTopicMessage) {
        this.orderProductRepository = orderProductRepository;
        this.inventoriesTopicMessage = inventoriesTopicMessage;
    }

    public void sendMessage(OrderResponse response) {
        List<OrderProduct> orderProducts = orderProductRepository.findOrderProductsByOrderId(response.getId());
        this.inventoriesTopicMessage.send("product-inventory", orderProducts);
    }
}
