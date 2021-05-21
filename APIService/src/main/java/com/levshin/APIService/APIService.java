package com.levshin.APIService;

import com.levshin.APIService.domain.Order;
import com.levshin.APIService.domain.OrderStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class APIService {

    private final OrderRepository repository;
    private final APIRestClient client;

    public APIService(OrderRepository repository, APIRestClient APIRestClient) {
        this.repository = repository;
        this.client = APIRestClient;
    }

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

    public Order create(Order order) {
        registerOrder(order);
        order = client.sendOrderToCooking(order);
        return repository.save(order);
    }

    private void registerOrder(Order order) {
        order.setRecdTime(LocalDateTime.now());
        order.setStatus(OrderStatus.NEW);
        repository.save(order);
    }

    public Order update(Order receivedOrder) {
        Order order = repository.findBySystemId(receivedOrder.getSystemId()).orElseThrow(NoSuchElementException::new);
        if (receivedOrder.getStatus().equals(OrderStatus.COOKED)) {
            order.setStatus(OrderStatus.DELIVERING);
            client.sendOrderToDelivery(order);
            return repository.save(order);
        }
        if (receivedOrder.getStatus().equals(OrderStatus.DELIVERED)) {
            order.setStatus(OrderStatus.CLOSED);
            order.setFinishTime(LocalDateTime.now());
            System.out.println("The order was delivered and closed. \n" + order);
            return repository.save(order);
        }
        if (receivedOrder.getStatus().equals(OrderStatus.SUSPEND)) {
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
