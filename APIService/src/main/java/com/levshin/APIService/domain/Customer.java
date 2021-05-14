package com.levshin.APIService.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "customer_name")
    private String name;

    @Column(name = "customer_phone")
    private String phone;

    @Column(name = "customer_address")
    private String address;

    @ElementCollection
    private List<Order> orders;

}
