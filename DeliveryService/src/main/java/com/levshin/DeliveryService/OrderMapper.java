package com.levshin.DeliveryService;

import com.levshin.DeliveryService.DTO.OrderDTO;
import com.levshin.DeliveryService.domain.Order;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@AllArgsConstructor
public class OrderMapper {

    private final ModelMapper mapper;

    public Order toOrder(OrderDTO dto) {
        return Objects.isNull(dto) ? null : mapper.map(dto, Order.class);
    }

    public OrderDTO toDTO(Order order) {
        return Objects.isNull(order) ? null : mapper.map(order, OrderDTO.class);
    }
}
