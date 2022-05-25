package io.github.brewagebear.service;

import static org.mockito.BDDMockito.given;

import io.github.brewagebear.domain.OrderStatus;
import io.github.brewagebear.domain.Product;
import io.github.brewagebear.dto.BuyRequest;
import io.github.brewagebear.dto.OrderRequest;
import io.github.brewagebear.dto.OrderResponse;
import io.github.brewagebear.repository.ProductRepository;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class OrderProductServiceTest {

    @MockBean
    ProductRepository productRepository;

    @Autowired
    private OrderProductService orderProductService;

    @Test
    @Transactional
    @DisplayName("단건 주문 테스트")
    public void oneProductOrder() {
        //given
        Product product = new Product("연필", BigDecimal.valueOf(1_000));
        given(productRepository.findBy(1L)).willReturn(product);

        List<BuyRequest> buyRequests = new ArrayList<>();
        BuyRequest buyRequest = new BuyRequest(1L, 2);
        buyRequests.add(buyRequest);

        OrderRequest orderRequest = new OrderRequest(buyRequests);

        //when
        OrderResponse order = orderProductService.order(orderRequest);

        //then
        Assertions.assertEquals(order.getId(), 1L);
        Assertions.assertEquals(order.getStatus(), OrderStatus.COMPLETED);
        Assertions.assertEquals(order.getTotalPrice(), BigDecimal.valueOf(2_000));
    }

    @Test
    @Transactional
    @DisplayName("다건 주문 테스트")
    public void multiProductOrder() {
        //given
        Product product = new Product("연필", BigDecimal.valueOf(1_000));
        Product product2 = new Product("샤프", BigDecimal.valueOf(3_000));

        given(productRepository.findBy(1L)).willReturn(product);
        given(productRepository.findBy(2L)).willReturn(product2);

        List<BuyRequest> buyRequests = new ArrayList<>();
        BuyRequest buyRequest = new BuyRequest(1L, 2);
        BuyRequest buyRequest2 = new BuyRequest(2L, 4);
        buyRequests.add(buyRequest);
        buyRequests.add(buyRequest2);

        OrderRequest orderRequest = new OrderRequest(buyRequests);

        //when
        OrderResponse order = orderProductService.order(orderRequest);

        //then
        Assertions.assertEquals(order.getId(), 1L);
        Assertions.assertEquals(order.getStatus(), OrderStatus.COMPLETED);
        Assertions.assertEquals(order.getTotalPrice(), BigDecimal.valueOf(14_000));
    }

}