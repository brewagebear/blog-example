package io.github.brewagebear.order.service.impl;

import io.github.brewagebear.order.Order;
import io.github.brewagebear.order.adapter.Body;
import io.github.brewagebear.order.adapter.HttpAdapter;
import io.github.brewagebear.order.adapter.HttpApiLink;
import io.github.brewagebear.order.adapter.HttpApiMessage;
import io.github.brewagebear.order.service.OrderService;
import java.util.List;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    private final HttpAdapter adapter;

    public OrderServiceImpl(HttpAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public void placeOrder(Order order) {

        HttpApiMessage stockReductionRequest = reduceStock(order);

        List<HttpApiLink> httpApiLinks = adapter.doTry(List.of(stockReductionRequest));

        adapter.confirmAll(httpApiLinks);
    }


    private HttpApiMessage reduceStock(final Order order) {
        final String requestURL = "http://localhost:8888/api/v1/stocks";
        Body requestBody = new Body();
        requestBody.put("orderId", order.getOrderId());
        requestBody.put("adjustmentType", "REDUCE");
        requestBody.put("productId", order.getProductId());
        requestBody.put("qty", order.getQty());

        return new HttpApiMessage(requestURL, requestBody);
    }

    private void waitCurrentThread(int seconds) throws InterruptedException {
        Thread.sleep(TimeUnit.SECONDS.toMillis(seconds));
    }
}
