package com.levshin.APIService.clients;

import com.levshin.APIService.domain.Order;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "COOKINGSERVICE")
public interface CookingClient {

    @PostMapping("/cooking")
    Order sendOrder(@RequestBody Order order);
}
