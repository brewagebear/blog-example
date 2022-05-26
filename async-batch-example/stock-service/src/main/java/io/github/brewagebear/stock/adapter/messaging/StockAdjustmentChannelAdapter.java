package io.github.brewagebear.stock.adapter.messaging;

public interface StockAdjustmentChannelAdapter {
    void publish(String message);
}
