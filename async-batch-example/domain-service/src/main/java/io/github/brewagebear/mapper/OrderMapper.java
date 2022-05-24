package io.github.brewagebear.mapper;

import io.github.brewagebear.domain.Order;
import io.github.brewagebear.dto.OrderRequest;
import io.github.brewagebear.dto.OrderResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    OrderResponse toResponse(Order domain);
}
