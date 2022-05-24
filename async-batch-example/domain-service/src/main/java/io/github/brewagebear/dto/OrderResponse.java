package io.github.brewagebear.dto;

import io.github.brewagebear.domain.OrderStatus;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderResponse {

    private Long id;

    private BigDecimal totalPrice;

    private OrderStatus status;

    public OrderResponse(Long id, BigDecimal totalPrice, OrderStatus status) {
        this.id = id;
        this.totalPrice = totalPrice;
        this.status = status;
    }
}
