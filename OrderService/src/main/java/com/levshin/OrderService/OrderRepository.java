package com.levshin.OrderService;

import com.levshin.OrderService.domain.Order;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends PagingAndSortingRepository<Order, Long> {

    Optional<Order> findBySystemId(long id);
}
