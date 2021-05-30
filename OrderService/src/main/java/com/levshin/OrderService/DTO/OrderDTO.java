package com.levshin.OrderService.DTO;

import com.levshin.OrderService.domain.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {

    private Long systemId;

    private OrderStatus status;

    private Map<Long, Integer> pizzas;

    private String description;
}