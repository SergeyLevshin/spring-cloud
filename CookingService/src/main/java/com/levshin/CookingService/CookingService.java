package com.levshin.CookingService;

import com.levshin.CookingService.domain.Order;
import com.levshin.CookingService.domain.OrderStatus;
import com.levshin.CookingService.repository.OrderRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
public class CookingService {

    private final OrderRepository repository;
    private final PizzaMaker pizzaMaker;
    private final CookingClient client;

    public CookingService(OrderRepository repository, PizzaMaker pizzaMaker, CookingClient client) {
        this.repository = repository;
        this.pizzaMaker = pizzaMaker;
        this.client = client;
    }

    public List<Order> findAll() {
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

    public Optional<Order> findBSystemId(Long id) {
        return repository.findBySystemId(id);
    }

    public OrderStatus getOrderStatus(long id) {
        return repository.findBySystemId(id)
                .orElseThrow(NoSuchElementException::new)
                .getStatus();
    }

    public Order createOrder(Order order) {
        order.setRecdTime(LocalDateTime.now());
        order.setStatus(OrderStatus.COOKING);
        repository.save(order);
        pizzaMaker.process(order);
        return order;
    }

    public Order cancelOrder(long id) {
        Order order = repository.findBySystemId(id).orElseThrow(NoSuchElementException::new);
        order.setStatus(OrderStatus.CANCELED);
        order.setFinishTime(LocalDateTime.now());
        return repository.save(order);
    }

    public void finishOrder(Order order) {
        client.updateOrder(order);
        System.out.println(order);
        if (!order.getStatus().equals(OrderStatus.CANCELED)) {
            order.setStatus(OrderStatus.COOKED);
        }
        repository.save(order);
    }

}

