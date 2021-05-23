package com.levshin.CookingService.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {

    private Long systemID;

    private OrderStatus status;

    private Map<Long, Integer> pizzas;

    private String description;
}
