package com.levshin.CookingService;

import com.levshin.CookingService.domain.Order;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "PIZZA-API", url = "localhost:8900")
public interface CookingClient {

    @PutMapping("/pizza-api")
    Order updateOrder(@RequestBody Order order);
}
