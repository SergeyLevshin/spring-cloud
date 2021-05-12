package com.levshin.CookingService;

import com.levshin.CookingService.domain.Order;
import com.levshin.CookingService.domain.OrderStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/cooking")
public class CookingController {

    private final CookingService service;

    public CookingController(CookingService service) {
        this.service = service;
    }

    @GetMapping
    public List<Order> findAllOrders() {
        return service.findAll();
    }

    @GetMapping("/status/{status}")
    public List<Order> findAllOrdersByStatus(@PathVariable("status") String status) {
        return service.findAllByStatus(status);
    }

    @GetMapping("/{id}")
    public Order findOrder(@PathVariable("id") long id) {
        return service.findBSystemId(id).orElseThrow(NoSuchElementException::new);
    }

    @GetMapping("/{id}/status")
    public OrderStatus getOrderStatus(@PathVariable("id") long id) {
        return service.getOrderStatus(id);
    }

    @PostMapping
    public Order createOrder(Order order) {
        return service.createOrder(order);
    }

    @PutMapping("/{id}/cancel")
    public Order cancelOrder(@PathVariable("id") long id) {
        return service.cancelOrder(id);
    }

}
