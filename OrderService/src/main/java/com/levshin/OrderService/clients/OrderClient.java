package com.levshin.OrderService.clients;

import com.levshin.OrderService.DTO.OrderDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "zuulapigateway")
public interface OrderClient {

    @PostMapping("cookingservice/cooking")
    OrderDTO sendOrderToCooking(@RequestBody OrderDTO orderDTO);

    @PostMapping("deliveryservice/delivery")
    OrderDTO sendOrderTODelivery(@RequestBody OrderDTO orderDTO);
}
