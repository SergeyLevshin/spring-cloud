package com.levshin.DeliveryService;

import com.levshin.DeliveryService.domain.Order;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "apiservice", url = "http://APISERVICE/pizza-app")
public interface DeliveryClient {

    @RequestMapping(method = RequestMethod.PUT, produces = "application/json")
    Order updateOrder(Order order);
}
