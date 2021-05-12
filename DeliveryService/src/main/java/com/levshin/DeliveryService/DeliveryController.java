package com.levshin.DeliveryService;

import com.levshin.DeliveryService.domain.Order;
import com.levshin.DeliveryService.domain.OrderStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/delivery")
public class DeliveryController {

    private final DeliveryService service;

    public DeliveryController(DeliveryService service) {
        this.service = service;
    }

    @GetMapping
    public Iterable<Order> findAllOrders() {
        return service.findAll();
    }

    @GetMapping("/status/{status}")
    public List<Order> findAllOrdersByStatus(@PathVariable String status) {
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
    public Order registerOrder(Order order) {
        return service.receiveOrder(order);
    }
}
