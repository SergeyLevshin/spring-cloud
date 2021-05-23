package com.levshin.APIService;

import com.levshin.APIService.clients.CookingClient;
import com.levshin.APIService.clients.DeliveryClient;
import com.levshin.APIService.domain.Order;
import com.levshin.APIService.domain.OrderStatus;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class APIRestClient {

    private final CookingClient cookingClient;
    private final DeliveryClient deliveryClient;

    public Order sendOrderToCooking(Order order) {
        order.setStatus(OrderStatus.NEW);
        return cookingClient.sendOrder(order);
    }

    public Order sendOrderToDelivery(Order order) {
        order.setStatus(OrderStatus.NEW);
        return deliveryClient.sendOrder(order);
    }
}
