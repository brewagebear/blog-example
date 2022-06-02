package io.github.brewagebear.order.controller;

import io.github.brewagebear.order.Order;
import io.github.brewagebear.order.service.OrderService;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderHttpController {

    private final OrderService orderService;

    public OrderHttpController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<Void> placeOrder(@RequestBody final Order order) {
        final String orderId = generateOrderId();

        order.setOrderId(orderId);

        orderService.placeOrder(order);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    private String generateOrderId() {
        return UUID.randomUUID()
            .toString()
            .toUpperCase();
    }

}
