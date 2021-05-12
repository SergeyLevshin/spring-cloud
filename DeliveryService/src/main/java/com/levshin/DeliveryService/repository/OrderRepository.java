package com.levshin.DeliveryService.repository;

import com.levshin.DeliveryService.domain.Order;
import com.levshin.DeliveryService.domain.OrderStatus;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {

    Iterable<Order> findAllByStatus(OrderStatus status);

    Optional<Order> findBySystemId(Long id);
}
