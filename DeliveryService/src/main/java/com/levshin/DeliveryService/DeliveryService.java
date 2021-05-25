package com.levshin.DeliveryService;

import com.levshin.DeliveryService.DTO.OrderDTO;
import com.levshin.DeliveryService.domain.Order;
import com.levshin.DeliveryService.domain.OrderStatus;
import com.levshin.DeliveryService.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DeliveryService {

    private final OrderRepository repository;
    private final DeliveryClient client;
    private final OrderMapper orderMapper;

    public Iterable<Order> findAll() {
        List<Order> orders = new ArrayList<>();
        repository.findAll().forEach(orders::add);
        return orders;
    }

    public List<Order> findAllByStatus(String status) {
        List<Order> orders = new ArrayList<>();
        OrderStatus orderStatus = OrderStatus.valueOf(status);
        repository.findAllByStatus(orderStatus).forEach(orders::add);
        return orders;
    }

    public Optional<Order> findBSystemId(long id) {
        return repository.findBySystemId(id);
    }

    public OrderStatus getOrderStatus(long id) {
        return repository.findById(id)
                .orElseThrow(NoSuchElementException::new)
                .getStatus();
    }

    public Order receiveOrder(OrderDTO orderDTO) {
        Order order = orderMapper.toOrder(orderDTO);
        order.setRecdTime(LocalDateTime.now());
        order.setStatus(OrderStatus.DELIVERING);
        deliverOrder(order);
        if (order.getStatus().equals(OrderStatus.DELIVERED)) {
            order.setFinishTime(LocalDateTime.now());
            client.updateOrder(orderMapper.toDTO(order));
        }
        return repository.save(order);
    }

    //very important business logic
    private void deliverOrder(Order order) {
        System.out.println("The order is delivering...");
        order.setStatus(OrderStatus.DELIVERED);
    }
}
