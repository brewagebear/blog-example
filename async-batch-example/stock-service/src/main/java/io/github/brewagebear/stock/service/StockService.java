package io.github.brewagebear.stock.service;


import io.github.brewagebear.stock.dto.StockAdjustment;
import io.github.brewagebear.stock.entity.ReservedStock;

public interface StockService {
    ReservedStock reserveStock(StockAdjustment stockAdjustment);

    void confirmStock(Long id);

    void cancelStock(Long id);

    void decreaseStock(StockAdjustment stockAdjustment);

    boolean isAlreadyProcessedOrderId(String orderId);
}
