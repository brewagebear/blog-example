package io.github.brewagebear.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@Entity
@Table(name = "orders")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Order {

    @EqualsAndHashCode.Include
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal totalPrice;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @OneToMany(mappedBy = "order",
        cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private final Set<OrderProduct> orderProducts = new HashSet<>();

    public Order() {
        this.status = OrderStatus.PREPARED;
        this.totalPrice = BigDecimal.ZERO;
    }

    public void processOrder(BigDecimal orderPrice) {
        updateStatus();
        updateOrderPrice(orderPrice);
    }

    private void updateStatus() {
        this.status = OrderStatus.COMPLETED;
    }

    private void updateOrderPrice(BigDecimal orderPrice) {
        if(this.totalPrice.equals(BigDecimal.ZERO)) {
            this.totalPrice = orderPrice;
        } else {
            this.totalPrice = this.totalPrice.add(orderPrice);
        }
    }

    public void cancel() {
        if(!this.status.equals(OrderStatus.PREPARED)) {
            this.status = OrderStatus.CANCELLED;
        }
    }
}
