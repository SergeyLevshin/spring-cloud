package com.levshin.CookingService;

import com.levshin.CookingService.domain.Order;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(value = "PIZZA-API")
public interface CookingClient {

    @PutMapping("/pizza-api")
    Order updateOrder(Order order);
}
