package io.github.brewagebear.stock.repository;

import io.github.brewagebear.stock.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface StockRepository extends JpaRepository<Stock, Long> {

    Stock findByProductId(String productId);

    @Query("select s from Stock s where exists ( select sh.orderId from s.stockHistories sh where sh.orderId = ?1)")
    Stock findByOrderId(String orderId);
}
