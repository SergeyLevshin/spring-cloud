package com.levshin.OrderService;

import com.levshin.OrderService.DTO.OrderDTO;
import com.levshin.OrderService.clients.CookingClient;
import com.levshin.OrderService.clients.DeliveryClient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class OrderRestClient {

    private final CookingClient cookingClient;
    private final DeliveryClient deliveryClient;

    public OrderDTO sendOrderToCooking(OrderDTO orderDTO) {
        return cookingClient.sendOrder(orderDTO);
    }

    public OrderDTO sendOrderToDelivery(OrderDTO orderDTO) {
        return deliveryClient.sendOrder(orderDTO);
    }
}
