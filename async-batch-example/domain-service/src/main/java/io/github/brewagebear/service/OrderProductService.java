package io.github.brewagebear.service;

import static io.github.brewagebear.mapper.OrderMapper.INSTANCE;

import io.github.brewagebear.domain.Order;
import io.github.brewagebear.domain.OrderProduct;
import io.github.brewagebear.domain.Product;
import io.github.brewagebear.dto.BuyRequest;
import io.github.brewagebear.dto.OrderRequest;
import io.github.brewagebear.dto.OrderResponse;
import io.github.brewagebear.repository.OrderProductRepository;
import io.github.brewagebear.repository.OrderRepository;
import io.github.brewagebear.repository.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderProductService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final OrderProductRepository orderProductRepository;

    public OrderProductService(OrderRepository orderRepository, ProductRepository productRepository,
        OrderProductRepository orderProductRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.orderProductRepository = orderProductRepository;
    }

    public OrderResponse order(OrderRequest request) {
        Order order = new Order();

        for(BuyRequest buyRequest : request.getBuyRequests()) {
            Product savedProduct = productRepository.findBy(buyRequest.getProductId());

            OrderProduct orderProduct = OrderProduct.of(savedProduct, order, buyRequest.getAmount());
            orderProductRepository.save(orderProduct);
            orderRepository.save(order);
        }

        return INSTANCE.toResponse(order);
    }
}
