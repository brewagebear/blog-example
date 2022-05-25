package io.github.brewagebear.domain;

import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderProduct {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne @JoinColumn
    private Order order;

    @ManyToOne @JoinColumn
    private Product product;

    private int amount;

    @Builder
    private OrderProduct(Order order, Product product, int amount) {
        this.order = order;
        this.product = product;
        this.amount = amount;
    }

    public static OrderProduct of(Product product, Order order, int amount) {
        order.processOrder(calculateTotalPrice(product,amount));

        return OrderProduct.builder()
            .product(product)
            .amount(amount)
            .order(order)
            .build();
    }

    private static BigDecimal calculateTotalPrice(Product product, int amount) {
        return product.price().multiply(BigDecimal.valueOf(amount));
    }
}
