package com.levshin.CookingService;

import com.levshin.CookingService.DTO.OrderDTO;
import com.levshin.CookingService.domain.Order;
import com.levshin.CookingService.domain.OrderStatus;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/cooking")
@AllArgsConstructor
public class CookingController {

    private final CookingService service;

    @GetMapping
    public List<Order> findAllOrders() {
        return service.findAll();
    }

    @GetMapping("/status/{status}")
    public List<Order> findAllOrdersByStatus(@PathVariable("status") String status) {
        return service.findAllByStatus(status);
    }

    @GetMapping("/{systemId}")
    public Order findOrder(@PathVariable("systemId") long systemId) {
        return service.findBSystemId(systemId).orElseThrow(NoSuchElementException::new);
    }

    @GetMapping("/{systemId}/status")
    public OrderStatus getOrderStatus(@PathVariable("systemId") long systemId) {
        return service.getOrderStatus(systemId);
    }

    @PostMapping
    public Order createOrder(@RequestBody OrderDTO orderDTO) {
        return service.createOrder(orderDTO);
    }

    @PutMapping("/{systemId}/cancel")
    public Order cancelOrder(@PathVariable("systemId") long systemId) {
        return service.cancelOrder(systemId);
    }

}
