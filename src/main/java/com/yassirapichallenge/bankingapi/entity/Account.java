package com.yassirapichallenge.bankingapi.entity;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Account {

    public static final double DEFAULT_BALANCE = 100.0;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Customer customer;

    private double balance = DEFAULT_BALANCE;
}