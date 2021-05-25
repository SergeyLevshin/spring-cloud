package com.levshin.OrderService.clients;

import com.levshin.OrderService.DTO.OrderDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "DELIVERYSERVICE")
public interface DeliveryClient {

    @PostMapping("/delivery")
    OrderDTO sendOrder(@RequestBody OrderDTO orderDTO);
}

