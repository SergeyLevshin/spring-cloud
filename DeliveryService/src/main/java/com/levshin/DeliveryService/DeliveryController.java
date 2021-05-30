package com.levshin.DeliveryService;

import com.levshin.DeliveryService.DTO.OrderDTO;
import com.levshin.DeliveryService.domain.Order;
import com.levshin.DeliveryService.domain.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@RestController
@RequestMapping("/delivery")
@AllArgsConstructor
public class DeliveryController {

    private final DeliveryService service;

    @GetMapping
    public Iterable<Order> findAllOrders() {
        return service.findAll();
    }

    @GetMapping("/status/{status}")
    public List<Order> findAllOrdersByStatus(@PathVariable String status) {
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
    public Order registerOrder(@RequestBody OrderDTO orderDTO) {
        log.info("POST request {}", orderDTO);
        return service.receiveOrder(orderDTO);
    }
}
