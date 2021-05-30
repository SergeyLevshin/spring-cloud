package com.levshin.OrderService;

import com.levshin.OrderService.DTO.OrderDTO;
import com.levshin.OrderService.domain.Order;
import com.levshin.OrderService.domain.OrderStatus;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class OrderService {

    private final OrderRepository repository;
    private final OrderRestClient client;
    private final OrderMapper orderMapper;

    public Order findById(long id) {
        return repository.findBySystemId(id).orElseThrow(NoSuchElementException::new);
    }

    public String cancelOrder(long id) {
        Order order = repository.findBySystemId(id).orElseThrow(NoSuchElementException::new);
        OrderStatus status = order.getStatus();
        if (status.equals(OrderStatus.NEW)
                || status.equals(OrderStatus.COOKING)
                || status.equals(OrderStatus.SUSPEND)) {
            //TODO some logic to return money
            order.setStatus(OrderStatus.CANCELED);
            order.setFinishTime(LocalDateTime.now());
            repository.save(order);
            return "Your order has been canceled.";
        }
        if (status.equals(OrderStatus.CLOSED)
                ||status.equals(OrderStatus.DELIVERED)) {
            return "The order has been closed. You can't cancel it.";
        }
        if (status.equals(OrderStatus.DELIVERING)
                || status.equals(OrderStatus.COOKED)) {
            return "The order is being delivered now. You can't cancel it.";
        }
        return "Oops. Something went wrong.";
    }

    public Order create(OrderDTO orderDTO) {
        Order order = orderMapper.toOrder(orderDTO);
        registerOrder(order);
        orderDTO.setStatus(OrderStatus.NEW);
        orderDTO = client.sendOrderToCooking(orderDTO);
        order = repository.findBySystemId(orderDTO.getSystemId()).orElseThrow();
        return repository.save(order);
    }

    private void registerOrder(Order order) {
        order.setRecdTime(LocalDateTime.now());
        order.setStatus(OrderStatus.NEW);
        repository.save(order);
    }

    public Order update(OrderDTO orderDTO) {
        Order order = repository.findBySystemId(orderDTO.getSystemId()).orElseThrow(NoSuchElementException::new);
        if (orderDTO.getStatus().equals(OrderStatus.COOKED)) {
            order.setStatus(OrderStatus.DELIVERING);
            orderDTO.setStatus(OrderStatus.NEW);
            client.sendOrderToDelivery(orderDTO);
            return repository.save(order);
        }
        if (orderDTO.getStatus().equals(OrderStatus.DELIVERED)) {
            order.setStatus(OrderStatus.CLOSED);
            order.setFinishTime(LocalDateTime.now());
            System.out.println("The order was delivered and closed. \n" + order);
            return repository.save(order);
        }
        if (orderDTO.getStatus().equals(OrderStatus.SUSPEND)) {
            //TODO need some logic, haven't decided yet
            return repository.save(order);
        }
        return repository.save(order);
    }

    public List<Order> findAll() {
        List<Order> orders = new ArrayList<>();
        repository.findAll().forEach(orders::add);
        return orders;
    }


}
