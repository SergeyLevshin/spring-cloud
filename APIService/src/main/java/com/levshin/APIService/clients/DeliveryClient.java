package com.levshin.APIService.clients;

import com.levshin.APIService.domain.Order;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "DELIVERYSERVICE")
public interface DeliveryClient {

    @PostMapping("/delivery")
    Order sendOrder(@RequestBody Order order);
}

