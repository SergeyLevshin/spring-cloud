package com.levshin.APIService.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Map;

@Data
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "order_id")
    @JsonIgnore
    private Long id;

    @Column
    private Long systemId;

    @JsonIgnore
    @Column(name = "recd_time", columnDefinition = "TIMESTAMP")
    private LocalDateTime recdTime;

    @JsonIgnore
    @Column(name = "finish_time", columnDefinition = "TIMESTAMP")
    private LocalDateTime finishTime;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @ElementCollection
    private Map<Pizza, Integer> pizzas;

    @ManyToOne
    private Customer customer;

    @Column
    private String description;

}
