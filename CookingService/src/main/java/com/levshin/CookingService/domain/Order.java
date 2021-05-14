package com.levshin.CookingService.domain;

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

    @JsonIgnore
    @Id
    @Column(name = "order_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "system_id")
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

    @Column
    private String description;

    public Long getId() {
        return id;
    }
}
