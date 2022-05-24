package io.github.brewagebear.mapper;

import io.github.brewagebear.domain.Order;
import io.github.brewagebear.domain.OrderStatus;
import io.github.brewagebear.dto.OrderResponse;
import java.math.BigDecimal;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-05-24T16:33:20+0900",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.4.jar, environment: Java 11.0.14 (Azul Systems, Inc.)"
)
@Component
public class OrderMapperImpl implements OrderMapper {

    @Override
    public OrderResponse toResponse(Order domain) {
        if ( domain == null ) {
            return null;
        }

        Long id = null;
        BigDecimal totalPrice = null;
        OrderStatus status = null;

        id = domain.getId();
        totalPrice = domain.getTotalPrice();
        status = domain.getStatus();

        OrderResponse orderResponse = new OrderResponse( id, totalPrice, status );

        return orderResponse;
    }
}
