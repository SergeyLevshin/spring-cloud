package com.levshin.CookingService.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @Column(name = "order_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "system_id", unique = true)
    private Long systemId;

    @Column(name = "recd_time", columnDefinition = "TIMESTAMP")
    private LocalDateTime recdTime;

    @Column(name = "finish_time", columnDefinition = "TIMESTAMP")
    private LocalDateTime finishTime;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    //HashMap<K,V> where K is pizza ID, V quantity of this pizza
    @ElementCollection
    private Map<Long, Integer> pizzas;

    @Column
    private String description;

}
