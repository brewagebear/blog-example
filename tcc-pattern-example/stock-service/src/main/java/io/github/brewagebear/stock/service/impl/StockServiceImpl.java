package io.github.brewagebear.stock.service.impl;

import io.github.brewagebear.stock.adapter.messaging.StockAdjustmentChannelAdapter;
import io.github.brewagebear.stock.dto.StockAdjustment;
import io.github.brewagebear.stock.entity.ReservedStock;
import io.github.brewagebear.stock.entity.Stock;
import io.github.brewagebear.stock.entity.enumerate.Status;
import io.github.brewagebear.stock.exception.CustomException;
import io.github.brewagebear.stock.repository.ReservedStockRepository;
import io.github.brewagebear.stock.repository.StockRepository;
import io.github.brewagebear.stock.service.StockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.KafkaException;
import org.springframework.kafka.listener.BatchListenerFailedException;
import org.springframework.kafka.listener.ListenerExecutionFailedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class StockServiceImpl implements StockService {

    private final StockAdjustmentChannelAdapter stockAdjustmentChannelAdapter;

    private final StockRepository stockRepository;

    private final ReservedStockRepository reservedStockRepository;

    public StockServiceImpl(StockAdjustmentChannelAdapter stockAdjustmentChannelAdapter,
        StockRepository stockRepository,
        ReservedStockRepository reservedStockRepository) {
        this.stockAdjustmentChannelAdapter = stockAdjustmentChannelAdapter;
        this.stockRepository = stockRepository;
        this.reservedStockRepository = reservedStockRepository;
    }

    @Override
    public ReservedStock reserveStock(StockAdjustment stockAdjustment) {
        ReservedStock reservedStock = new ReservedStock(stockAdjustment);

        reservedStockRepository.save(reservedStock);

        log.info("Reserved Stock :" + reservedStock.getId());
        return reservedStock;
    }

    @Override
    @Transactional
    public void confirmStock(Long id) {
        ReservedStock reservedStock = reservedStockRepository.findById(id)
            .orElseThrow();

//        reservedStock.validate();

        reservedStock.setStatus(Status.COMPLETED);

        reservedStockRepository.save(reservedStock);

        // Messaging Queue 로 전송
        stockAdjustmentChannelAdapter.publish(reservedStock.getResources());
        log.info("Confirm Stock :" + id);
    }

    @Override
    @Transactional
    public void cancelStock(Long id) {
        ReservedStock reservedStock = reservedStockRepository.findById(id)
            .orElseThrow();

        reservedStock.setStatus(Status.CANCELLED);
        reservedStockRepository.save(reservedStock);

        log.info("Cancel Stock :" + id);
    }

    @Override
    @Transactional
    public void decreaseStock(StockAdjustment stockAdjustment) {
        Stock stock = stockRepository.findByProductId(stockAdjustment.getProductId());

        if(stockAdjustment.getQty() > stock.getAvailableStockQty()) {
            throw new CustomException(String.format("Stock decreased Request [decreasedQty: %s][availableQty : %s]", stockAdjustment.getQty(), stock.getAvailableStockQty()));
        }

        stock.decrease(stockAdjustment.getOrderId(), stockAdjustment.getQty());

        stockRepository.save(stock);

        log.info(String.format("Stock decreased [orderId: %s][productId : %s][qty  : %d]",
            stockAdjustment.getOrderId(), stockAdjustment.getProductId(), stockAdjustment.getQty()));
    }

    @Override
    public boolean isAlreadyProcessedOrderId(String orderId) {
        Stock stock = stockRepository.findByOrderId(orderId);
        return stock != null;
    }
}
