package com.levshin.DeliveryService;

import com.levshin.DeliveryService.domain.OrderStatus;
import com.levshin.DeliveryService.domain.Order;
import com.levshin.DeliveryService.repository.OrderRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class DeliveryService {

    private final OrderRepository repository;
    private final DeliveryClient client;

    public DeliveryService(OrderRepository repository, DeliveryClient client) {
        this.repository = repository;
        this.client = client;
    }

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

    @Transactional
    public Order receiveOrder(Order order) {
        order.setRecdTime(LocalDateTime.now());
        order.setStatus(OrderStatus.NEW);
        deliverOrder(order);
        return repository.save(order);
    }

    //very important business logic
    @Transactional
    private void deliverOrder(Order order) {
        order.setStatus(OrderStatus.DELIVERING);
        try {
            for(int i = 0; i < 5; i++) {
                Thread.sleep(500);
                System.out.println("The order is delivering...");
            }
            order.setStatus(OrderStatus.DELIVERED);
        } catch (InterruptedException e) {
            System.out.println("Something wrong was happen.");
        }
        if (order.getStatus().equals(OrderStatus.DELIVERED)) {
            client.updateOrder(order);
            order.setStatus(OrderStatus.CLOSED);
        }
    }
}
