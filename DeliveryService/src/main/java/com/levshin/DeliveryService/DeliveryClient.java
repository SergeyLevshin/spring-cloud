package com.levshin.DeliveryService;

import com.levshin.DeliveryService.domain.Order;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "PIZZA-API")
public interface DeliveryClient {

    @PutMapping("/pizza-api")
    Order updateOrder(@RequestBody Order order);
}
