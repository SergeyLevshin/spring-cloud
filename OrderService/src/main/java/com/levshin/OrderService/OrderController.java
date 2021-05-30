package com.levshin.OrderService;

import com.levshin.OrderService.DTO.OrderDTO;
import com.levshin.OrderService.domain.Order;
import com.levshin.OrderService.domain.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/pizza-api")
@AllArgsConstructor
public class OrderController {

    private final OrderService service;

    @GetMapping
    public List<Order> getAllOrders() {
        return service.findAll();
    }

    @GetMapping("/{id}/status")
    public OrderStatus checkOrderStatus(@PathVariable("id") long id){
        return service.findById(id).getStatus();
    }

    @PostMapping
    public Order createOrder(@RequestBody OrderDTO orderDTO ) {
        log.info("POST request {}", orderDTO);
        return service.create(orderDTO);
    }

    @PutMapping
    public Order updateOrder(@RequestBody OrderDTO orderDTO) {
        return service.update(orderDTO);
    }

    @PostMapping("/cancel/{id}")
    public String cancelOrder(@PathVariable("id") long id) {
        return service.cancelOrder(id);
    }

}
