package com.levshin.OrderService;

import com.levshin.OrderService.DTO.OrderDTO;
import com.levshin.OrderService.clients.OrderClient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class OrderRestClient {

    private final OrderClient orderClient;

    public OrderDTO sendOrderToCooking(OrderDTO orderDTO) {
        return orderClient.sendOrderToCooking(orderDTO);
    }

    public OrderDTO sendOrderToDelivery(OrderDTO orderDTO) {
        return orderClient.sendOrderTODelivery(orderDTO);
    }
}
