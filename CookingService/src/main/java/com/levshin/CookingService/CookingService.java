package com.levshin.CookingService;

import com.levshin.CookingService.DTO.OrderDTO;
import com.levshin.CookingService.domain.Order;
import com.levshin.CookingService.domain.OrderStatus;
import com.levshin.CookingService.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class CookingService {

    private final OrderRepository repository;
    private final PizzaMaker pizzaMaker;
    private final CookingClient client;
    private final OrderMapper orderMapper;

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

    public Order createOrder(OrderDTO orderDTO) {
        orderDTO.setStatus(OrderStatus.COOKING);

        Order order = orderMapper.toOrder(orderDTO);
        order.setRecdTime(LocalDateTime.now());
        pizzaMaker.process(order);
        return repository.save(order);
    }

    public Order cancelOrder(long id) {
        Order order = repository.findBySystemId(id).orElseThrow(NoSuchElementException::new);
        order.setStatus(OrderStatus.CANCELED);
        order.setFinishTime(LocalDateTime.now());
        repository.save(order);
        return repository.save(order);
    }

    public void finishOrder(Order order) {
        if (!order.getStatus().equals(OrderStatus.CANCELED)) {
            order.setStatus(OrderStatus.COOKED);
        }
        client.updateOrder(orderMapper.toDTO(order));
        repository.save(order);
    }

}

