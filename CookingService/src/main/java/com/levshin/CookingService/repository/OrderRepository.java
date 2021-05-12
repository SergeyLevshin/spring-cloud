package com.levshin.CookingService.repository;

import com.levshin.CookingService.domain.Order;
import com.levshin.CookingService.domain.OrderStatus;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends PagingAndSortingRepository<Order,Long> {

    Iterable<Order> findAllByStatus(OrderStatus status);

    Optional<Order> findBySystemId(Long id);
}
