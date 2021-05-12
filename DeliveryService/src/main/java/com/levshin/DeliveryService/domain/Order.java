package com.levshin.DeliveryService.domain;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "order_id")
    private Long id;

    @Column
    private Long systemId;

    @Column(name = "recd_time", columnDefinition = "TIMESTAMP")
    private LocalDateTime recdTime;

    @Column(name = "finish_time", columnDefinition = "TIMESTAMP")
    private LocalDateTime finishTime;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column
    private String customerAddress;

    @Column
    private String customerName;

    @Column
    private String customerPhone;

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

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

