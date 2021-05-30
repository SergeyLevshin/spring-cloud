package com.levshin.CookingService;

import com.levshin.CookingService.DTO.OrderDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "zuulapigateway")
public interface CookingClient {

    @PutMapping("orderservice/pizza-api")
    OrderDTO updateOrder(@RequestBody OrderDTO orderDTO);
}
