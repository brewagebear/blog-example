package io.github.brewagebear.repository;

import io.github.brewagebear.domain.OrderProduct;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {

    List<OrderProduct> findOrderProductsByOrderId(Long orderId);
}