package com.levshin.CookingService;

import com.levshin.CookingService.DTO.OrderDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "PIZZA-API")
public interface CookingClient {

    @PutMapping("/pizza-api")
    OrderDTO updateOrder(@RequestBody OrderDTO orderDTO);
}
