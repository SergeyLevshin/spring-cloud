package com.levshin.CookingService.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Map;

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

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSystemId() {
        return systemId;
    }

    public void setSystemId(Long systemId) {
        this.systemId = systemId;
    }

    public LocalDateTime getRecdTime() {
        return recdTime;
    }

    public void setRecdTime(LocalDateTime recdTime) {
        this.recdTime = recdTime;
    }

    public LocalDateTime getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(LocalDateTime finishTime) {
        this.finishTime = finishTime;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Map<Pizza, Integer> getPizzas() {
        return pizzas;
    }

    public void setPizzas(Map<Pizza, Integer> pizzas) {
        this.pizzas = pizzas;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order)) return false;

        Order order = (Order) o;

        if (!getId().equals(order.getId())) return false;
        return getSystemId().equals(order.getSystemId());
    }

    @Override
    public int hashCode() {
        int result = getId().hashCode();
        result = 31 * result + getSystemId().hashCode();
        return result;
    }
}
