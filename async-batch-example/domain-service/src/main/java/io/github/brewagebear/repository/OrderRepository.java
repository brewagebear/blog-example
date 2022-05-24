package io.github.brewagebear.repository;

import io.github.brewagebear.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
