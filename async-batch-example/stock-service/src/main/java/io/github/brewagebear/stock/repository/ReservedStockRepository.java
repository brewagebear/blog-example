package io.github.brewagebear.stock.repository;

import io.github.brewagebear.stock.entity.ReservedStock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservedStockRepository extends JpaRepository<ReservedStock, Long> {

}
