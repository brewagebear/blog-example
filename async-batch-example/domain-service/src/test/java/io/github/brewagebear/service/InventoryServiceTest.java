package io.github.brewagebear.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import io.github.brewagebear.domain.Order;
import io.github.brewagebear.domain.OrderProduct;
import io.github.brewagebear.domain.OrderStatus;
import io.github.brewagebear.domain.Product;
import io.github.brewagebear.dto.OrderResponse;
import io.github.brewagebear.repository.OrderProductRepository;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class InventoryServiceTest {

    @MockBean
    private OrderProductRepository orderProductRepository;

    @Autowired
    private InventoryService inventoryService;

    @Test
    @DisplayName("카프카 토픽이 발급되어야한다.")
    public void test() {
        //given
        OrderResponse orderResponse = new OrderResponse(1L, BigDecimal.ZERO, OrderStatus.COMPLETED);

        List<OrderProduct> orderProducts = new ArrayList<>();

        Order order = new Order();
        Product product = new Product("연필", BigDecimal.valueOf(2_000));

        OrderProduct orderProduct = OrderProduct.builder()
            .order(order)
            .amount(2)
            .product(product)
            .build();

        orderProducts.add(orderProduct);

        given(orderProductRepository.findOrderProductsByOrderId(1L)).willReturn(orderProducts);

        //when
        inventoryService.sendMessage(orderResponse);

    }

}