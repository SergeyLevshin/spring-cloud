package com.levshin.APIService;

import com.levshin.APIService.domain.Order;
import com.levshin.APIService.domain.OrderStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pizza-api")
public class APIController {

    private final APIService service;

    public APIController(APIService service) {
        this.service = service;
    }

    @GetMapping
    public List<Order> getAllOrders() {
        return service.findAll();
    }

    @GetMapping("/{id}/status")
    public OrderStatus checkOrderStatus(@PathVariable("id") long id){
        return service.findById(id).getStatus();
    }

    @PostMapping
    public Order createOrder(Order order) {
        return service.create(order);
    }

    @PutMapping
    public Order updateOrder(Order order) {
        return service.update(order);
    }

    @PostMapping("/cancel/{id}")
    public String cancelOrder(@PathVariable("id") long id) {
        return service.cancelOrder(id);
    }

}